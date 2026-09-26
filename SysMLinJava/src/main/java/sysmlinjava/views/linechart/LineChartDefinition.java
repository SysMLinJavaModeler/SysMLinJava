/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.linechart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringJoiner;

import sysmlinjava.views.common.Axis;
import sysmlinjava.views.common.AxisFixedRange;

/**
 * Definition of the line chart display, i.e. its x axis and y axes, and it ID
 * 
 * @author ModelerOne
 *
 */
public class LineChartDefinition implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8945635974734237468L;

	/**
	 * ID of the chart (used to associate data received with the chart)
	 */
	public String chartID;
	/**
	 * List of y axes
	 */
	public ArrayList<AxisFixedRange> yAxes;
	/**
	 * X axis
	 */
	public Axis xAxis;

	/**
	 * Constructor
	 * 
	 * @param chartID ID of the chart
	 * @param yAxes   list of the y axes, each of which has a fixed range
	 * @param xAxis   the x axis
	 */
	public LineChartDefinition(String chartID, ArrayList<AxisFixedRange> yAxes, Axis xAxis)
	{
		super();
		this.chartID = chartID;
		this.yAxes = yAxes;
		this.xAxis = xAxis;
	}
	

	/**
	 * Log-type representation of the chart definition
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[LC][graphID=%s%nxAxis=%s%n%s]", chartID, xAxis.toString(), yAxesToLogString());
	}

	@Override
	public String toString()
	{
		return String.format("Graph [graphID=%s, xAxis=%s, %s]", chartID, xAxis.toString(), yAxesToString());
	}

	/**
	 * Log-type representation of the y-axes definitions
	 * 
	 * @return log-type string
	 */
	private String yAxesToLogString()
	{
		StringJoiner joiner = new StringJoiner("\n");
		yAxes.forEach(axis -> joiner.add("yAxis=" + axis.toString()));
		return joiner.toString();
	}

	/**
	 * toString() representation of the y-axes definitions
	 * 
	 * @return string representation of y-axes defs
	 */
	private String yAxesToString()
	{
		StringJoiner joiner = new StringJoiner(", ", "yAxes=", "");
		yAxes.forEach(axis -> joiner.add(axis.toString()));
		return joiner.toString();
	}
}
