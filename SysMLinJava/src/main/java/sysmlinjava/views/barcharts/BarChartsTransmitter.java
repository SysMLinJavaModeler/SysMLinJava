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
package sysmlinjava.views.barcharts;

import sysmlinjava.views.common.UDPTransmitter2;

/**
 * The {@code BarChartsTransmitter} is a specialization of the
 * {@code UDPTransmitter2} to transmit bar graph data to a
 * {@code BarChartsReceiver} for the display of a bar graph. This class simply
 * uses the {@code UDPTransmitter2}'s {@code transmit0(Object)} and
 * {@code transmit1(Object)} operations to transmit the
 * {@code BarChartDefinition} and {@code BarChartData} objects via the
 * {@code UDPTransmitter2}'s generic transmit operations.
 * 
 * @author ModelerOne
 * @see sysmlinjava.views.barcharts.BarChartData
 * @see sysmlinjava.views.barcharts.BarChartDefinition
 */
public class BarChartsTransmitter extends UDPTransmitter2<BarChartDefinition, BarChartData>
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
	public BarChartsTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "BarChartsTransmitter");
	}

	/**
	 * Transmits a {@code BarChartDefintion} to a {@code BarChartsReceiver} of the
	 * {@code BarChartsDisplay}
	 * 
	 * @param barChartDefinition definition/specification of the bar chart to be
	 *                           displayed
	 */
	public void transmitBarChartDefinition(BarChartDefinition barChartDefinition)
	{
		super.transmit0(barChartDefinition);
	}

	/**
	 * Transmits a {@code BarChartData} to a {@code BarChartsReceiver} of the
	 * {@code BarChartsDisplay}
	 * 
	 * @param chartData category and y-value data for the bars that are displayed in
	 *                  the bar chart
	 */
	public void transmitBarChartData(BarChartData chartData)
	{
		super.transmit1(chartData);
	}
}
