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
package sysmlinjava.views.timingdiagrams;

import sysmlinjava.views.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive timing diagram data for
 * disply via the {@code TimingDiagramsDisplay}. This class simply implements
 * the {@code UDPReceiver}'s {@code receive(Object)} operation for the timing
 * diagram data objects by displaying the objects as log-type strings similar to
 * {@code toString()}s.
 * 
 * @author ModelerOne
 * @see TimingDiagramDefinition
 * @see StateTransitionTiming
 */
public class TimingDiagramsReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive graph data
	 */
	public TimingDiagramsReceiver(int udpPort)
	{
		super(udpPort, "TimingDiagramsReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof TimingDiagramDefinition)
			logger.info(((TimingDiagramDefinition)data).toLogString());
		else if (data instanceof StateTransitionTiming)
			logger.info(((StateTransitionTiming)data).toLogString());
		return false;
	}
}
