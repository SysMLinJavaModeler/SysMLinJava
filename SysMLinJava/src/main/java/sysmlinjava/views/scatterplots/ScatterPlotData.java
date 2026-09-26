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
package sysmlinjava.views.scatterplots;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringJoiner;

import sysmlinjava.views.common.XY;

/**
 * Data (x-y values) to be displayed on a scatter plot display
 * 
 * @author ModelerOne
 *
 */
public class ScatterPlotData implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -7567118199087274202L;

	/**
	 * ID of the plot
	 */
	public String scatterPlotID;
	/**
	 * List of the x-y data points to be "plotted"
	 */
	public ArrayList<XY> xyData;

	/**
	 * Constructor
	 * 
	 * @param scatterPlotID ID of the plot
	 * @param xyData        list of the x-y data points to be "plotted"
	 */
	public ScatterPlotData(String scatterPlotID, ArrayList<XY> xyData)
	{
		super();
		this.scatterPlotID = scatterPlotID;
		this.xyData = xyData;
	}

	/**
	 * String representation of the x-y point values
	 * 
	 * @return string of x-y values
	 */
	public String xyDataString()
	{
		StringJoiner joiner = new StringJoiner(" ");
		xyData.forEach(value ->
		{
			joiner.add(String.format("[%3.3f, %3.3f]", value.xValue, value.yValue));
		});
		return joiner.toString();
	}

	/**
	 * Log-type representation of the data
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[SC] scatterPlotID=%s, xyData=%s]", scatterPlotID, xyDataString());
	}

	@Override
	public String toString()
	{
		return String.format("ScatterPlotData [scatterPlotID=%s, xyData=%s]", scatterPlotID, xyDataString());
	}
}