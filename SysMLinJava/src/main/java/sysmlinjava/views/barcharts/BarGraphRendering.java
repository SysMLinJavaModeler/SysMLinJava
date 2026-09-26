package sysmlinjava.views.barcharts;
/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import sysmlinjava.views.SysMLRendering;

/**
 * The {@code BarGraphRendering} is a simple console application that receives
 * {@code BarChartData} via a UDP socket and displays the data as text on the
 * console.
 * <p>
 * <b>Note:</b> This simple text-based display is part of set of basic views
 * provided by SysMLinJava. More capable graphics-based displays are available
 * commercially. See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 */
public class BarGraphRendering extends SysMLRendering
{
	/**
	 * UDP port via which the bar chart data is to be received
	 */
	public static final int udpPort = 8890;
	/**
	 * Bar Charts Receiver that receives the data for the display via the UDP port
	 */
	BarChartsReceiver receiver;

	/**
	 * Constructor
	 */
	public BarGraphRendering()
	{
		super();
		receiver = new BarChartsReceiver(udpPort);
	}

	@Override
	public void start()
	{
		receiver.run();
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the bar chart data and displays them as
	 * {@code toString()}s to the console.<br>
	 * 
	 * @param args null arguments
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		BarChartsDisplay display = new BarChartsDisplay();
		Runtime.getRuntime().exit(0);
	}
}
