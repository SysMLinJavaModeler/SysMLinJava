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
 * Functional interface specifying the action performed upon exit from the
 * state. That is, the action to be performed by the SysMLState's
 * {@code onExitAction()} operation.
 * <p>
 * The {@code SysMLOnExitAction} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code OnExitAction} annotation. It should then be implemented as an
 * instance of a Lambda function in the override of the
 * {@code SysMLStateMachine}'s {@code createStateOnExitActions()} operation
 * to provide the function with access to the state machine's properties. An example
 * follows.
 * 
 * <pre>
		:
	&#64;@OnExitAction
	public SysMLOnExitAction f2t2ScanningStateOnExitAction;
	&#64;@OnExitAction
	public SysMLOnExitAction eaScanningStateOnExitAction;
		:
	&#64;Override
	protected void createStateOnExitActions()
	{
		super.createStateOnExitActions();
		f2t2ScanningStateOnExitAction = (context) ->
		{
			RadarSystem radarSystem = (RadarSystem)context.get();
			radarSystem.stopF2T2Scanning();
		};
		eaScanningStateOnExitAction = (context) ->
		{
			RadarSystem radarSystem = (RadarSystem)context.get();
			radarSystem.stopEAScanning();
		};
	}
 * </pre>

 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.states.SysMLStateMachine#createStateExitActionFunctions
 */
@FunctionalInterface
public interface SysMLExitActionFunction extends SysMLActionFunction
{
	/**
	 * Specification of the action to be performed upon exiting an associated
	 * state, i.e. the action to be performed by the state's
	 * {@code onExitAction} element. This function must be realized by an instance
	 * of a lambda expression.
	 * 
	 * @param context Optional part in whose context the state's associated
	 *                     state machine executes.
	 */
	void perform(Optional<? extends StateBehaviorContext> context);
}