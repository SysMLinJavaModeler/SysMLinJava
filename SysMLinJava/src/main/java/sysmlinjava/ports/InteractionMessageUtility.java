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
package sysmlinjava.ports;

import java.time.Instant;
import java.util.logging.Logger;

import sysmlinjava.items.SysMLSignal;
import sysmlinjava.states.StateBehaviorContext;

/**
 * {@code MessagesUtility} is an interface for an implementation of a utility
 * for accessing the message information (source part, message type,
 * destination part, etc.) of a part interaction. These part interactions are
 * typically performed as signal transmissions or operation calls between parts
 * via flow or proxy ports. These interactions occur during model execution
 * forming an interaction sequence, which can be displayed in the form of a
 * SysML sequence diagram. Use of a message utility can enable the generation of
 * the sequence diagram during model execution.
 * <p>
 * The interface's operations are invoked by the {@code SysMLFullPort} during
 * performance of full port transmissions, i.e. during the {@code transmit()}
 * operation. The implementation of the {@code MessagesUtiliy} should be created
 * in an override of the {@code createMessagessUtility()} operation by
 * initializing the {@code messagesUtility} variable, e.g.
 * 
 * <pre>
        messagesUtility = new MyMessagesUtility(int arg);
 * </pre>
 * 
 * where the constructor is for a class that implements the
 * {@code MessagesUtility} interface. The referenced class below from the
 * SysMLinJava tools package is an example implementation of this interface for
 * transmitting interaction sequence message information to sequence diagram
 * display applications.
 * 
 * @author ModelerOne
 * @see sysmlinjava.views.interactionssequencediagram.InteractionMessageTransmitters
 */
public interface InteractionMessageUtility
{
	/**
	 * Performs the actual transmission of an interaction message (in the form a a
	 * {@code SysMLSignal} between the specified context (part or port) and the
	 * specified peer port to a sequence diagram display. The {@code perform()}
	 * operation is invoked by the {@code SysMLPort}'s {@code transmit()} operation,
	 * immediately before transmitting the {@code SysMLSignal}-based message to the
	 * peer port thereby enabling real-time display of the interaction in the
	 * sequence diagram.
	 * 
	 * @param time          time of the interaction (message transmission)
	 * @param context       SysMLPart or SysMLPort in whose context this port
	 *                      resides, i.e. in which it is a port
	 * @param messageSignal signal which contains the message
	 * @param peerPort      peer full port to which the message is transmitted
	 * @param logger        this full port's logger to be used for logging as needed
	 */
	void perform(Instant time, StateBehaviorContext context, SysMLSignal messageSignal, SysMLPort peerPort, Logger logger);

	/**
	 * /** Performs the actual transmission of an interaction message (in the form a
	 * a {@code String} between the specified context and the specified peer proxy
	 * port to a sequence diagram display. The {@code perform()} operation is
	 * invoked by the class that invokes/calls the {@code SysMLProxyPort} operation,
	 * immediately before invoking the operation with the name specified by a
	 * text-based message in the peer proxy port thereby enabling real-time display
	 * of the interaction in the sequence diagram.
	 * 
	 * @param time      time of the interaction (operation call)
	 * @param context   SysMLPart or SysMLPort in whose context the proxy port call
	 *                  is invoked
	 * @param message   textual representation of the operation call (name,
	 *                  arguments, etc.)
	 * @param proxyPort proxy port called.
	 * @param logger    this proxy port's logger to be used for logging as needed.
	 */
	void perform(Instant time, StateBehaviorContext context, String message, SysMLProxyPort proxyPort, Logger logger);

	/**
	 * /** Performs the actual transmission of an interaction message (in the form a
	 * a {@code String} between the specified proxy port and the specified context
	 * part called to a sequence diagram display. The {@code perform()} operation is
	 * invoked by the {@code SysMLProxyPort} that invokes/calls the context part
	 * operation, immediately before invoking the operation with the name specified
	 * by a text-based message thereby enabling real-time display of the interaction
	 * in the sequence diagram.
	 * 
	 * @param time      time of the interaction (operation call)
	 * @param proxyPort SysMLProxyPort calling the behavior context (part).
	 * @param message   textual representation of the operation call (name,
	 *                  arguments, etc.)
	 * @param part      context part called.
	 * @param logger    this proxy port's logger to be used for logging as needed.
	 */
	void perform(Instant time, SysMLProxyPort proxyPort, String message, StateBehaviorContext part, Logger logger);
}