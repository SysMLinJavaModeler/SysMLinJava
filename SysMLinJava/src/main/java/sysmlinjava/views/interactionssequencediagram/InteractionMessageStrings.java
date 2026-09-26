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

import java.io.Serializable;
import java.time.Instant;

import sysmlinjava.items.SysMLSignal;
import sysmlinjava.ports.SysMLPort;
import sysmlinjava.ports.SysMLProxyPort;
import sysmlinjava.states.StateBehaviorContext;

/**
 * Strings that define a message between lifelines in a sequence diagram, i.e.
 * the source lifeline, destination lifeline, and the message from the source to
 * the destination.
 * 
 * @author ModelerOne
 */
public class InteractionMessageStrings implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = -3032907429853237196L;

	/**
	 * Time of the message occurance
	 */
	public Instant time;
	/**
	 * Title/name for the message to be displayed between the source and destination
	 */
	public String messageTitle;
	/**
	 * Title/name of the source lifeline
	 */
	public String sourceLifelineTitle;
	/**
	 * Title/name of the destination lifeline
	 */
	public String destinationLifelineTitle;

	/**
	 * Constructor from actual string values
	 * 
	 * @param time                     time of the message occurance
	 * @param messageTitle             title/name for the message to be displayed
	 *                                 between the source and destination
	 * @param sourceLifelineTitle      title/name of the source lifeline
	 * @param destinationLifelineTitle title/name of the destination lifeline
	 */
	public InteractionMessageStrings(Instant time, String messageTitle, String sourceLifelineTitle, String destinationLifelineTitle)
	{
		super();
		this.time = time;
		this.messageTitle = messageTitle;
		this.sourceLifelineTitle = sourceLifelineTitle;
		this.destinationLifelineTitle = destinationLifelineTitle;
	}

	/**
	 * Constructor for interaction message from a part or port (context) to a port
	 * 
	 * @param time          time of the message occurance
	 * @param context       part or port that contained the port that was the source
	 *                      of the message
	 * @param messageSignal signal that carried the message transmitted from source
	 *                      to destination
	 * @param peerPort      full port that received the message for the part that
	 *                      was the destination of the message
	 */
	public InteractionMessageStrings(Instant time, StateBehaviorContext context, SysMLSignal messageSignal, SysMLPort peerPort)
	{
		super();
		this.time = time;
		sourceLifelineTitle = context.getIdentityString();
		destinationLifelineTitle = peerPort.context.get().getIdentityString();
		messageTitle = messageSignal.stackNamesString();
	}

	/**
	 * Constructor for interaction message from a part or port (context) to a proxy
	 * port
	 * 
	 * @param time      time of the message occurance
	 * @param context   part or port that contains the port that was the source of
	 *                  the message
	 * @param message   string representation of the message used for the
	 *                  interaction, i.e. the message transmitted from source to
	 *                  destination
	 * @param proxyPort proxy port that receives the message for the part or port
	 *                  that was the destination of the message
	 */
	public InteractionMessageStrings(Instant time, StateBehaviorContext context, String message, SysMLProxyPort proxyPort)
	{
		super();
		this.time = time;
		sourceLifelineTitle = context.getIdentityString();
		if (proxyPort.implementingContext.isPresent())
			destinationLifelineTitle = proxyPort.implementingContext.get().getIdentityString();
		messageTitle = message;
	}

	/**
	 * Constructor for interaction message from a proxy port to a part or port
	 * (context)
	 * 
	 * @param time      time of the message occurance
	 * @param proxyPort proxy port that is the source of the message
	 * @param message   string representation of the message used for the
	 *                  interaction, i.e. the message/call transmitted from source
	 *                  to destination
	 * @param context   part or port that received the message
	 */
	public InteractionMessageStrings(Instant time, SysMLProxyPort proxyPort, String message, StateBehaviorContext context)
	{
		super();
		this.time = time;
		if (proxyPort.implementingContext.isPresent())
			sourceLifelineTitle = proxyPort.context.getIdentityString();
		destinationLifelineTitle = context.getIdentityString();
		messageTitle = message;
	}

	/**
	 * Returns a log-like string representation of the message where it depicts the
	 * message display on a sequence diagram, i.e. labeled line from source to
	 * destination
	 * 
	 * @return sequence diagram-like string for use in a log output
	 */
	public String logString()
	{
		return String.format("[SEQ] %s --- %s ---> %s", sourceLifelineTitle, messageTitle, destinationLifelineTitle);
	}

	@Override
	public String toString()
	{
		return String.format("InteractionMessageStrings [time=%s, message=%s, source=%s, destination=%s]", time, messageTitle, sourceLifelineTitle, destinationLifelineTitle);
	}
}