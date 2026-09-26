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

import sysmlinjava.views.common.UDPTransmitter2;

/**
 * The {@code ScatterPlotsTransmitter} is a specialization of the
 * {@code UDPTransmitter2} to transmit graph data to a
 * {@code ScatterPlotsReceiver} for the display of graph data. This class simply
 * uses the {@code UDPTransmitter2}'s {@code transmit0(Object)} and
 * {@code transmit1(Object)} operations to transmit the
 * {@code ScatterPlotDefinition} and {@code ScatterPlotData} objects via the
 * {@code UDPTransmitter2}'s generic transmit operations.
 * 
 * @author ModelerOne
 * @see sysmlinjava.views.scatterplots.ScatterPlotData
 * @see sysmlinjava.views.scatterplots.ScatterPlotDefinition
 */
public class ScatterPlotsTransmitter extends UDPTransmitter2<ScatterPlotDefinition, ScatterPlotData>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port which to transmit plot data to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public ScatterPlotsTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "ScatterPlotsTransmitter");
	}

	/**
	 * Transmits a {@code ScatterPlotDefintion} to a {@code ScatterPlotsReceiver} of
	 * the {@code ScatterPlotsDisplay}
	 * 
	 * @param plotDefinition definition/specification of the scatter plots to be
	 *                       displayed
	 */
	public void transmitScatterPlotDefinition(ScatterPlotDefinition plotDefinition)
	{
		super.transmit0(plotDefinition);
	}

	/**
	 * Transmits a {@code ScatterPlotData} to a {@code ScatterPlotsReceiver} of the
	 * {@code ScatterPlotsDisplay}
	 * 
	 * @param plotData plot data to be displayed in the scatter plot
	 */
	public void transmitScatterPlotData(ScatterPlotData plotData)
	{
		super.transmit1(plotData);
	}
}
