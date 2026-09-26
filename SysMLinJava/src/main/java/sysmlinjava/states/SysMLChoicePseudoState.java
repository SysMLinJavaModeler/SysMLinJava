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

/**
 * SysMLinJava's representation of the SysML choice pseudo-state. As an
 * extension of the {@code SysMLPseudoState} which is an extension of the
 * {@code SysMLVertex} it is assigned its transitions into and out of the "choice"
 * pseudo-state by {@code SysMLTransition} constructors invoked in an override
 * of the {@code SysMLStateMachine}'s {@code createTransitions()} operation.
 * 
 * @author ModelerOne
 * @see SysMLTransition
 * @see SysMLStateMachine#createTransitions()
 */
public final class SysMLChoicePseudoState extends SysMLPseudoState
{
	/**
	 * Constructor
	 * 
	 * @param context in whose context the state machine executes
	 * @param name         name of the "choice" pseudo-state
	 */
	public SysMLChoicePseudoState(Optional<? extends StateBehaviorContext> context, String name)
	{
		super(context, name);
	}
}
