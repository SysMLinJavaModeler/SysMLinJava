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
package sysmlinjava.views.statetransitionstables;

import sysmlinjava.views.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive state transition table
 * data for disply via the {@code StateTransitionsDisplay}. This class simply
 * implements the {@code UDPReceiver}'s {@code receive(Object)} operation for
 * the state transition table data objects by displaying the objects as
 * {@code toString()}s in log entries.
 * 
 * @author ModelerOne
 * @see sysmlinjava.views.statetransitionstables.StateTransitionStrings
 */
public class StateTransitionStringsReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive state transitions data
	 */
	public StateTransitionStringsReceiver(int udpPort)
	{
		super(udpPort, "StateTransitionStringsReceiver");
	}

//	@Override
	public boolean receive(Object data)
	{
		if (data instanceof StateTransitionStrings)
			logger.info(((StateTransitionStrings)data).toString());
		else
			logger.warning("unrecognized data type: " + data.getClass().getSimpleName());
		return false;
	}
}
