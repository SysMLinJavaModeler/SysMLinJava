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

import sysmlinjava.views.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive plot data for disply
 * via the {@code ScatterPlotsDisplay}. This class simply implements the
 * {@code UDPReceiver}'s {@code receive(Object)} operation for the plot data
 * objects by displaying the objects as {@code toString()}s as log entries.
 * 
 * @author ModelerOne
 * @see ScatterPlotData
 * @see ScatterPlotDefinition
 */
public class ScatterPlotsReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive plot data
	 */
	public ScatterPlotsReceiver(int udpPort)
	{
		super(udpPort, "ScatterPlotsReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof ScatterPlotData)
			logger.info(((ScatterPlotData)data).toString());
		else if (data instanceof ScatterPlotDefinition)
			logger.info(((ScatterPlotDefinition)data).toString());
		return false;
	}
}
