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
package sysmlinjava.states;

import java.util.Optional;

import sysmlinjava.actions.SysMLAction;
import sysmlinjava.events.SysMLEvent;

/**
 * SysMLinJava's representation of the SysML effect that specifies the optional
 * behavior to be performed during transition between states or within a state.
 * The {@code SysMLEffect} is performed within the context of the state machine
 * in which it is declared, but has parameteric access to a specified context,
 * which is typically the part for which the state machine operates.
 * <p>
 * Note the {@code SysMLEffect} cannot be extended and must be used as-is to
 * specify a SysML effect for a transition. An example follows.
 * 
 * <pre>
	&#64;Effect
	private SysMLEffectAction onEthernetIPPacketEffect;
	&#64;Effect
	private SysMLEffectAction onDataLinkFrameEffect;
	&#64;Effect
	private SysMLEffectAction onGPSMessageEffect;

		:
	&#64;Override
	protected void createEffects()
	{
		super.createEffects();
		onGPSMessageEffect = new SysMLEffect(context, onGPSMessageEffectActivity, "onGPSMessage");
		onEthernetIPPacketEffect = new SysMLEffect(context, onEthernetIPPacketEffectActivity, "onEthernetIPPacket");
		onDataLinkFrameEffect = new SysMLEffect(context, onDataLinkFrameEffectActivity, "onDataLinkFrame");
	}
 * </pre>
 * 
 * @author ModelerOne
 */
public final class SysMLEffect extends SysMLAction
{
	/**
	 * Optional context within whose context this effect is to be performed.
	 */
	public Optional<? extends StateBehaviorContext> context;

	/**
	 * Constructor for specifying context, activity to be performed, and object name
	 * 
	 * @param context        in whose context this effect's activity is to be
	 *                       performed
	 * @param function function (behavior) to be performed for this effect
	 * @param name           unique name
	 */
	public SysMLEffect(Optional<? extends StateBehaviorContext> context, SysMLEffectActionFunction function, String name)
	{
		super(function, name, 0L);
		this.context = context;
	}

	/**
	 * Invokes performance of the activity specified for this effect, i.e. invokes
	 * the {@code perform()} operation of the functional interface
	 * {@code SysMLEffectActivity}
	 * 
	 * @param event   Event that invoked the transition with which this effect is
	 *                associated
	 * @param context state behavior in whose context this effect's activity is to be
	 *                performed
	 */
	public void perform(Optional<? extends SysMLEvent> event, Optional<? extends StateBehaviorContext> context)
	{
		((SysMLEffectActionFunction)function).perform(event, context);
	}

	@Override
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}
}
