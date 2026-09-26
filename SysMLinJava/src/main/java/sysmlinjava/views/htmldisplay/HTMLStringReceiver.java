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

import sysmlinjava.views.common.UDPReceiver;

/**
 * The {@code HTMLStringReceiver} is an extension of the {@code UDPReceiver} to
 * receive HTML strings for disply via the {@code HTMLDisplay}. This class
 * simply implements the {@code UDPReceiver}'s {@code receive(Object)} operation
 * for the HTML strings by displaying them as log messages via
 * {@code logger.info()} calls.
 * 
 * @author ModelerOne
 * @see HTMLString
 */
public class HTMLStringReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive HTML strings
	 */
	public HTMLStringReceiver(int udpPort)
	{
		super(udpPort, "HTMLStringReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof HTMLString htmlString)
			logger.info(htmlString.logString());
		else
			logger.warning("unrecognized object type received: " + data.getClass().getSimpleName());
		return false;
	}
}
