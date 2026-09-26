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
package sysmlinjava.views.interactionssequencediagram;

import java.time.Instant;
import java.util.logging.Logger;

import sysmlinjava.items.SysMLSignal;
import sysmlinjava.ports.InteractionMessageUtility;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.ports.SysMLProxyPort;
import sysmlinjava.states.StateBehaviorContext;

/**
 * {@code InteractionMessageTransmitters} enable the transmission from the
 * simulation to an interaction sequence display (sequence diagram) of the
 * interaction messages that occur during the simulation. This implmentation of
 * the {@code SysMLPort's} {@code InteractionMessageUtility} enables a grapical
 * display of the real-time sequence diagram of the interactions between the
 * specified parts of the model throughout the simulation.
 * <p>
 * The {@code InteractionMessageTransmitters} must be created and assigned to
 * the {@code messageUtility} variable of the {@code SysMLPort} to perform its
 * function. This creation/assignment should be performed in an overridden
 * version of the {@code SysMLPort}'s {@code
 * createInteractionMessageUtility()} operation.
 * 
 * @author ModelerOne
 * @see sysmlinjava.ports.SysMLPort#messageUtility
 * @see sysmlinjava.ports.SysMLPort#createInteractionMessageUtility()
 */
public class InteractionMessageTransmitters implements InteractionMessageUtility
{
	/**
	 * A transmitter of interaction message text to UDP port for possible display
	 * and/or storage of this port's transmissions occuring during model execution.
	 */
	private InteractionMessageTransmitter interactionMessageTransmitter;
	/**
	 * Indication if the strings that represent the state transitions are to be
	 * logged to the console. If true, strings are logged to console, otherwise they
	 * are not.
	 */
	private boolean logToConsole;

	/**
	 * Constructor
	 * 
	 * @param interactionMessageTransmitter the
	 *                                      {@code InteractionMessageTransmitter}
	 *                                      that is to perform the transmission of
	 *                                      the interaction message to an
	 *                                      {@code InteractionMessageReceiver}.
	 * @param logToConsole                  indication if the strings that represent
	 *                                      the state transitions are to be logged
	 *                                      to the console. If true, strings are
	 *                                      logged to console, otherwise they are
	 *                                      not.
	 */
	public InteractionMessageTransmitters(InteractionMessageTransmitter interactionMessageTransmitter, boolean logToConsole)
	{
		super();
		this.interactionMessageTransmitter = interactionMessageTransmitter;
		this.logToConsole = logToConsole;
	}

	/**
	 * Performs a transmission of interaction message data for a flow port
	 * transmission. Note this operation is automatically invoked by the
	 * {@code SysMLPort} if 1) the port's {@code messageUtility} is set to an
	 * instance of this class, and 2) the port is connected to another
	 * {@code SysMLPort}.
	 */
	@Override
	public void perform(Instant time, StateBehaviorContext context, SysMLSignal messageSignal, SysMLPort peerPort, Logger logger)
	{
		InteractionMessageStrings strings = new InteractionMessageStrings(time, context, messageSignal, peerPort);
		if (logToConsole)
			logger.info(strings.logString());
		interactionMessageTransmitter.transmit(strings);
	}

	/**
	 * Performs a transmission of interaction message data for a proxy port
	 * transmission. Note this operation is automatically invoked by the
	 * {@code SysMLProxyPort} if 1) the proxy port's {@code messageUtility} is set to an
	 * instance of this class, and 2) the proxy port is connected to another
	 * {@code SysMLProxyPort}.
	 */
	@Override
	public void perform(Instant time, StateBehaviorContext context, String message, SysMLProxyPort peerPort, Logger logger)
	{
		InteractionMessageStrings strings = new InteractionMessageStrings(time, context, message, peerPort);
		if (logToConsole)
			logger.info(strings.logString());
		interactionMessageTransmitter.transmit(strings);
	}

	@Override
	public void perform(Instant time, SysMLProxyPort proxyPort, String message, StateBehaviorContext part, Logger logger)
	{
		InteractionMessageStrings strings = new InteractionMessageStrings(time, proxyPort, message, part);
		if (logToConsole)
			logger.info(strings.logString());
		interactionMessageTransmitter.transmit(strings);
	}

	/**
	 * Stops the transmission of sequence diagram information (interaction messages)
	 * to the sequence diagram display.
	 */
	public void stop()
	{
		interactionMessageTransmitter.stop();
	}
}
