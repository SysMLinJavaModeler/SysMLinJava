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

import sysmlinjava.views.common.Axis;

/**
 * Definition of the scatter plot display, i.e. its x and y axes, and it ID
 * 
 * @author ModelerOne
 *
 */
public class ScatterPlotDefinition implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8945635974734237468L;

	/**
	 * ID of the plot
	 */
	public String scatterPlotID;
	/**
	 * Axis for the x values
	 */
	public Axis xAxis;
	/**
	 * Axis for the y values
	 */
	public Axis yAxis;

	/**
	 * Constructor
	 * 
	 * @param plotID ID of the plot
	 * @param xAxis  axis for the x values
	 * @param yAxis  axis for the y values
	 */
	public ScatterPlotDefinition(String plotID, Axis xAxis, Axis yAxis)
	{
		super();
		this.scatterPlotID = plotID;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	/**
	 * Log-type representation of the definition
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[SC] [scatterPlotID=%s, xAxis=%s, yAxis=%s]", scatterPlotID, xAxis.toString(), yAxis.toString());
	}

	@Override
	public String toString()
	{
		return String.format("ScatterPlotDefinition [scatterPlotID=%s, xAxis=%s, yAxis=%s]", scatterPlotID, xAxis, yAxis);
	}
}
