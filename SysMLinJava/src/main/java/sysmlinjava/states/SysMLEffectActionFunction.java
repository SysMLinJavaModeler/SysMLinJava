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
package sysmlinjava.states;

import java.util.Optional;

import sysmlinjava.actions.SysMLActionFunction;
import sysmlinjava.events.SysMLEvent;

/**
 * Functional interface specifying the action performed as the effect of a state
 * transition. That is, the action to be performed by the transition's
 * {@code SysMLEffect} element.<br>
 * The {@code SysMLEffectAction} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code EffectAction} annotation. It should then be implemented as an instance
 * of a Lambda function in the override of the {@code SysMLStateMachine}'s
 * {@code createEffectActions()} operation to provide the function with access
 * to the state machine's properties. An example follows.
 * 
 * <pre>
		:
	&#64;EffectAction
	private SysMLEffectAction onEthernetIPPacketEventEffectAction;
	&#64;EffectAction
	private SysMLEffectAction onDataLinkFrameEventEffectAction;
	&#64;EffectAction
	private SysMLEffectAction onGPSMessageEventEffectAction;
		:
	&#64;Override
	protected void createEffectActions()
	{
		super.createEffectActivities();
		onEthernetIPPacketEventEffectAction = (event, context) ->
		{
			IPPacket ipPacket = ((IPPacketEvent)event.get()).getPacket();
			ModemRadio modemRadio = (ModemRadio)context.get();
			modemRadio.processIPPacketFromEthernet(ipPacket);
		};
		onDataLinkFrameEventEffectAction = (event, context) ->
		{
			DataLinkFrame datalinkFrame = ((DataLinkFrameEvent)event.get()).getFrame();
			ModemRadio modemRadio = (ModemRadio)context.get();
			modemRadio.processIPPacketFromDataLink(datalinkFrame);
		};

		onGPSMessageEventEffectAction = (event, context) ->
		{
			GPSMessage gpsMessage = ((GPSMessageEvent)event.get()).getMessage();
			ModemRadio modemRadio = (ModemRadio)context.get();
			modemRadio.processTDMASlotTimeFromGPS(gpsMessage);
		};
	}
		:
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 * @see SysMLStateMachine#createEffectActionFunctions
 */
@FunctionalInterface
public interface SysMLEffectActionFunction extends SysMLActionFunction
{
	/**
	 * Specification of the action to be performed as the effect of an associated
	 * state transition, i.e. the action to be performed by the transition's
	 * {@code SysMLEffect} element. This function must be realized by an instance of
	 * a lambda expression.
	 * 
	 * @param event   The SysMLEvent that invoked the state transition
	 * @param context The state behavior in whose context the state transtion's associated
	 *                state machine executes.
	 */
	void perform(Optional<? extends SysMLEvent> event, Optional<? extends StateBehaviorContext> context);
}