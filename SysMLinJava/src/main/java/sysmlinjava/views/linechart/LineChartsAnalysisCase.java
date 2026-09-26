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

import java.util.List;
import java.util.Optional;

import sysmlinjava.actions.PerformedActionFunction;
import sysmlinjava.analysis.ParametricAnalysisCase;
import sysmlinjava.attributetypes.KeyValueMap;
import sysmlinjava.attributetypes.Point2D;

/**
 * {@code LineChartAnalysisCase} is a SysMLinJava model of a parametric analysis case
 * that produces a line chart representation of up to 4 constraint parameters.
 * The lines on the chart each have their own y axis with each set of y values
 * sharing the same x axis. Specializations of the
 * {@code LineChartAnalysisCase} need only define the constraint parameter
 * port functions that implement the "binding" connectors of the constraint
 * parameters to be displayed in the line chart, i.e. they need only to
 * override/implement the {@code createConstraintParameterPortFunction()}
 * method. All other activities needed to display the constraint paramter values
 * in the line chart are provided by this context part.
 * 
 * @author ModelerOne
 *
 */
public abstract class LineChartsAnalysisCase extends ParametricAnalysisCase
{
	/**
	 * Enumeration of the lines in the line chart display. The enum is of the 4
	 * lines available in the display, but only the enums for the corresponding
	 * number of lines specfied in the ({@code chartDefinition} will be used for the
	 * constraint parameters, ports, and port functions. Specializations of this
	 * parametric analysis case should bind constraint parameters accordingly, i.e. bind
	 * constraint parameter {@code line0} to the parameter that is to be displayed
	 * as line 0 (right-most y-axis), constraint parameter {@code line1} to the
	 * parameter that is to be displayed as line 1 (2nd y-axis), etc. as required by
	 * the number of constraint parameters to be displayed as lines in the line
	 * chart display.
	 * 
	 */
	public enum LineEnum
	{
		/**
		 * First line on the chart
		 */
		line0,
		/**
		 * Second line on the chart
		 */
		line1,
		/**
		 * Third line on the chart
		 */
		line2,
		/**
		 * Fourth line on the chart
		 */
		line3
	}

	/**
	 * Definition of the bar chart to be built/constrained and displayed. The chart
	 * definition is provided via the constructor.
	 */
	public LineChartDefinition chartDefinition;

	/**
	 * Data for update of the line chart display. Chart data is calculated by the
	 * constraint that is defined by specializations of this parametric analysis case.
	 */
	public LineChartData chartData;

	/**
	 * Transmitter of the chart data to the chart display
	 */
	private LineChartsTransmitter chartTransmitter;

	/**
	 * Constructor
	 * 
	 * @param chartDefinition definition of the line chart in terms of its axes,
	 *                        lines, title, etc.
	 * @param udpPort         UDP port to which the line chart data is to be
	 *                        transmitted
	 * @param logToConsole    whether to send all {@code transmit()} logs to
	 *                        console. Note: {@code transmit()} operation can log
	 *                        every object's {@code toString()} string to the
	 *                        console. The more frequently log messages are sent to
	 *                        console, the greater the CPU resources are needed.
	 *                        This can cause noticable slowing of the console
	 *                        display and related applications.
	 */
	public LineChartsAnalysisCase(LineChartDefinition chartDefinition, int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "LineCharts", 0L);
		this.chartDefinition = chartDefinition;
		this.chartData = new LineChartData(chartDefinition);
		this.chartTransmitter = new LineChartsTransmitter(udpPort, logToConsole);
		this.chartTransmitter.transmitGraph(chartDefinition);
	}

	/**
	 * Creates the action function to perform the analysis on the current values of
	 * the set of analysis parameters. The action function updates the line chart
	 * data with the new point parameter and transmits the line chart data to the
	 * line chart display.
	 */
	@Override
	protected void createFunction()
	{
		function = (PerformedActionFunction)() ->
		{
			if (currentParamID.isPresent())
			{
				Point2D pointParam = (Point2D)params.get(currentParamID.get());
				int lineIndex = LineEnum.valueOf(currentParamID.get()).ordinal();
				for (int i = 0; i < chartData.linesPoints.size(); i++)
				{
					chartData.linesPoints.get(i).clear();
					if (i == lineIndex)
						chartData.linesPoints.get(lineIndex).add(pointParam);
				}
			}
		};
	}

	/**
	 * Transmits the chart data for the line chart display
	 */
	protected void transmitChartData()
	{
		chartTransmitter.transmit1(chartData);
	}

	/**
	 * Overridable operation that simply instantiates the map of parameters.
	 * Overrides should invoke this operation and then "put()" the needed parameters
	 * into the map for access by the {@code performConstraints()} operation.
	 */
	@Override
	protected void createParameters()
	{
		params = KeyValueMap.of(
		 List.of(LineEnum.line0.toString(), LineEnum.line1.toString(), LineEnum.line2.toString(), LineEnum.line3.toString()),
		 List.of(new Point2D(), new Point2D(), new Point2D(), new Point2D()));
	}

	/**
	 * Overridable operation that simply instantiates the map of parameter port
	 * functions. Overrides should invoke this operation and then "put()" the needed
	 * function instances (lambda expressions) into the map for access by the
	 * overridden {@code createConstraintParameterPorts()} operation, i.e. as an
	 * argument to the {@code SysMLConstraintParameterPort} constructor.
	 */
	//	@Override
	//	protected void createBindingConnectorFunctions()
	//	{
	//	}

	/**
	 * Overridable operation that simply instantiates the map of parameter ports.
	 * Overrides should invoke this operation and then "put()" the needed port
	 * instances into the map for access by the {@code retrieveParameters()}
	 * operation.
	 */
	//	@Override
	//	protected void createBindingConnectors()
	//	{
	//		paramConnectors = new HashMap<>();
	//		for (LineEnum lineEnum : LineEnum.values())
	//		{
	//			SysMLBindingConnectorFunction portFunction = paramConnectorFunctions.get(lineEnum.toString());
	//			if (portFunction != null)
	//			{
	//				SysMLBindingConnector paramPort = new SysMLBindingConnector(this, portFunction, lineEnum.toString(), 0L);
	//				paramConnectors.put(lineEnum.toString(), paramPort);
	//			}
	//		}
	//	}

	@Override
	public void stop()
	{
		chartTransmitter.stop();
		super.stop();
	}
}
