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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sysmlinjava.analysis.ParametricAnalysisCase;
import sysmlinjava.attributetypes.Point2D;
import sysmlinjava.connectors.SysMLBindingConnector;
import sysmlinjava.javaannotations.analysis.parametrics.Parameter;
import sysmlinjava.views.common.XY;

/**
 * {@code ScatterPlotAnalysisCase} is a SysMLinJava model of a constraint
 * part that produces a scatter plot chart representation of the a sequence of
 * X-Y values which are the sequence of values of the constraint parameters over
 * time, i.e. the constraint produces the scatter plot from the constraint
 * parameter values.
 * 
 * @author ModelerOne
 *
 */
public abstract class ScatterPlotAnalysisCase extends ParametricAnalysisCase
{
	/**
	 * Constraint parameter for the point value to be plotted
	 */
	@Parameter
	public Point2D point;

	/**
	 * Data for the plot display
	 */
	public ScatterPlotData xyPlot;
	/**
	 * Definition of the plot display
	 */
	public ScatterPlotDefinition plotDefinition;
	/**
	 * Transmitter of the plot definition and data to the display
	 */
	private ScatterPlotsTransmitter plotsTransmitter;

	/**
	 * Constructor - initialization
	 * 
	 * @param plotDefinition definition of the plot display
	 * @param udpPort        UDP port to which the plot definition and data are to
	 *                       be sent
	 * @param logToConsole   whether to send all {@code transmit()} logs to console.
	 *                       Note: {@code transmit()} operation can log every
	 *                       object's {@code toString()} string to the console. The
	 *                       more frequently log messages are sent to console, the
	 *                       greater the CPU resources are needed. This can cause
	 *                       noticable slowing of the console display and related
	 *                       applications.
	 */
	public ScatterPlotAnalysisCase(ScatterPlotDefinition plotDefinition, int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "ScatterPlot", 0L);
		this.plotDefinition = plotDefinition;
		this.plotsTransmitter = new ScatterPlotsTransmitter(udpPort, logToConsole);
		this.plotsTransmitter.transmitScatterPlotDefinition(plotDefinition);
	}

	/**
	 * Sets the x and y values of the next scatter plot point from the changed
	 * parameter value
	 */
	@Override
	protected void onParameterChange(String paramID)
	{
		SysMLBindingConnector waypointErrorConnector = paramConnectors.get(paramID);
		if(waypointErrorConnector != null)
		{
			Point2D temp = (Point2D)waypointErrorConnector.getAttribute();
			point.xValue = temp.xValue;
			point.yValue = temp.yValue;
		}
	}

	/**
	 * Performs the analysis by setting the new scatter plot data and transmitting
	 * it to the scatter plot display
	 */
	@Override
	public void perform()
	{
		ArrayList<XY> xyData = new ArrayList<>();
		xyData.addAll(List.of(new XY(point.xValue, point.yValue)));
		xyPlot = new ScatterPlotData(plotDefinition.scatterPlotID, xyData);
		plotsTransmitter.transmitScatterPlotData(xyPlot);
	}

	@Override
	protected void createParameters()
	{
		point = new Point2D(0, 0);
	}
}
