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

/**
 * Action specialization for the action performed upon entry into the state.
 * That is, the action to perform by the SysMLState's {@code onEnterAction()}
 * method.<br>
 * <p>
 * The {@code SysMLEntryAction} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code OnEnterAction} annotation. It should then be implemented as an
 * instance of a lambda function for the initializer of a new
 * {@code SysMLEntryAction} in the override of the {@code SysMLStateMachine}'s
 * {@code createStateOnEnterActions()} operation to provide the function with
 * access to the state machine's properties. An example follows.
 * 
 * <pre>
		:
	&#64;OnEnterAction
	public SysMLOnEnterAction onEnterAction;
		:
	&#64;Override
	protected void createStateOnEnterActions()
	{
		super.createStateOnEnterActions();
		onEnterAction = new SysMLEntryAction((SysMLEntryAction)(context) ->
		{
			((Vehicle)context.get()).transmitWeights();
		};
	}
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.states.SysMLStateMachine#createStateEntryActionFunctions
 */
@FunctionalInterface
public interface SysMLEntryActionFunction extends SysMLActionFunction
{
	/**
	 * Specification of the action to be performed upon entering an associated
	 * state, i.e. the action to be performed by the state's {@code onEnterAction}
	 * element. This function must be realized by an instance of a lambda
	 * expression.
	 * 
	 * @param context Optional part in whose context the state's associated state
	 *                machine executes.
	 */
	public void perform(Optional<? extends StateBehaviorContext> context);
}