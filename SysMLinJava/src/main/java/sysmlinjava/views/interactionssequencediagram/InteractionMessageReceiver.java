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
package sysmlinjava.views.interactionssequencediagram;

import sysmlinjava.views.common.UDPReceiver;

/**
 * Specialization of the UDPReceiver to receive interaction message data for
 * disply via the InteractionSequenceDisplay (sequence diagram). This class
 * simply implements the UDPReceiver's receive(Object) operation for the
 * interaction message objects by displaying the objects as toString()s in log
 * entries.
 * 
 * @author ModelerOne
 * @see sysmlinjava.views.interactionssequencediagram.InteractionMessageStrings
 */
public class InteractionMessageReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive interaction message data
	 */
	public InteractionMessageReceiver(int udpPort)
	{
		super(udpPort, "InteractionMessageReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
//TODO
//		if (data instanceof InteractionMessageStrings messageStrings)
//			logger.info(messageStrings.toString());
		if (data instanceof InteractionMessageStrings)
			logger.info(((InteractionMessageStrings)data).toString());
		else
			logger.warning("unrecognized data type: " + data.getClass().getSimpleName());
		return false;
	}
}
