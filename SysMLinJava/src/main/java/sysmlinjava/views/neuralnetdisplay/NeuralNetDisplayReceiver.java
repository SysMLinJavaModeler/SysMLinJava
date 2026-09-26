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
package sysmlinjava.views.neuralnetdisplay;

import sysmlinjava.views.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive neural network data for
 * display via the {@code NeuralNetDisplay}. This class simply implements the
 * {@code UDPReceiver}'s {@code receive(Object)} operation for the neural net
 * data objects by displaying the objects as {@code toString()}s as log entries.
 * 
 * @author ModelerOne
 * @see NeuralNetDisplayData
 * @see NeuralNetDisplayDefinition
 */
public class NeuralNetDisplayReceiver extends UDPReceiver
{
	/**
	 * Definition of the display
	 */
	NeuralNetDisplayDefinition displayDef;

	/**
	 * Constructur - initial value of UDP port
	 * 
	 * @param udpPort UDP port on which to receive neural net data
	 */
	public NeuralNetDisplayReceiver(int udpPort)
	{
		super(udpPort, "NeuralNetDisplayReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof NeuralNetDisplayData)
		{
			logger.info(((NeuralNetDisplayData)data).toDisplayString(displayDef));
		}
		else if (data instanceof NeuralNetDisplayDefinition)
		{
			this.displayDef = ((NeuralNetDisplayDefinition)data);
			logger.info(displayDef.toString());
		}
		return false;
	}
}
