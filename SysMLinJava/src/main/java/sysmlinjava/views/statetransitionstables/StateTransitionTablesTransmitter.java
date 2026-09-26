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

import sysmlinjava.views.common.UDPTransmitter;

/**
 * The {@code StateTransitionStringsTransmitter} is a specialization of the
 * {@code UDPTransmitter} to transmit {@code StateTransitionStrings} to a
 * {@code StateTransitionStringsReceiver} for the display of state transition
 * tables. The {@code StateTransitionStringsTransmitter} is simply an extension
 * of the generic {@code UDPTransmitter} for the transmission of
 * {@code StateTransitionStrings}.
 * 
 * @author ModelerOne
 * @see sysmlinjava.views.statetransitionstables.StateTransitionStrings
 */
public class StateTransitionTablesTransmitter extends UDPTransmitter<StateTransitionStrings>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port to which transmit state transition data is to be
	 *                     transmitted to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public StateTransitionTablesTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "StateTransitionTablesTransmitter");
	}
}
