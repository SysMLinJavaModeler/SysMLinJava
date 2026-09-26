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

import sysmlinjava.views.common.UDPTransmitter2;

/**
 * The {@code TimingDiagramTransmitter} is a specialization of the
 * {@code UDPTransmitter2} to transmit timing diagram data to a
 * {@code TimingDiagramReceiver} for the display of timing diagram data. This
 * class simply implements the {@code UDPTransmitter2}'s
 * {@code transmit(StateTime)} and {@code transmit(TimingDiagram)} operations
 * for the timing diagram data objects by transmitting objects via the
 * {@code UDPTransmitter2}'s generic transmit operations.
 * 
 * @author ModelerOne
 * @see sysmlinjava.views.timingdiagrams.TimingDiagramDefinition
 * @see sysmlinjava.views.timingdiagrams.StateTransitionTiming
 */
public class TimingDiagramsTransmitter extends UDPTransmitter2<StateTransitionTiming, TimingDiagramDefinition>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port which to transmit StateTime data to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public TimingDiagramsTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "TimingDiagramsTransmitter");
	}

	/**
	 * Transmits a {@code StateTime} specification to a
	 * {@code TimingDiagramReceiver} of the {@code TimingDiagramDisplay}
	 * 
	 * @param StateTime the specification of the StateTime to be displayed
	 */
	public void transmitStateTime(StateTransitionTiming StateTime)
	{
		super.transmit0(StateTime);
	}

	/**
	 * Transmits a {@code StateTime} specification to a
	 * {@code TimingDiagramReceiver} of the {@code TimingDiagramDisplay}
	 * 
	 * @param TimingDiagram the data to be added to the displayed StateTime
	 */
	public void transmitTimingDiagram(TimingDiagramDefinition TimingDiagram)
	{
		super.transmit1(TimingDiagram);
	}

	public void stop()
	{
		// TODO Auto-generated method stub

	}
}
