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
package sysmlinjava.views.htmldisplay;

import sysmlinjava.views.common.UDPTransmitter;

/**
 * The {@code HTMLStringTransmitter} is a specialization of the
 * {@code UDPTransmitter} to transmit HTML strings to a
 * {@code HTMLStringReceiver} for the display of HTML. This class simply extends
 * the generic {@code UDPTransmitter} for the {@code HTMLString}.
 * 
 * @author ModelerOne
 * 
 * @see sysmlinjava.views.htmldisplay.HTMLString
 */
public class HTMLStringTransmitter extends UDPTransmitter<HTMLString>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port which to transmit HTML strings data to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public HTMLStringTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "HTMLStringTransmitter");
	}
}
