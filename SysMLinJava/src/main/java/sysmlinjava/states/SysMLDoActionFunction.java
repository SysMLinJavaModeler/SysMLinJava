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
 * Functional interface specifying the action performed while in the state. That
 * is, the action to perform by the {@code SysMLState}'s {@code doAction()}
 * operation.<br>
 * <p>
 * The {@code SysMLDoAction} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code &#64;DoAction} annotation. It should then be implemented as an
 * instance of a functional interface - a lambda function - in the override of
 * the {@code SysMLStateMachine}'s {@code createStateDoActions()} operation to
 * provide the function with access to the state machine's properties. An
 * example follows.
 * 
 * <pre>
 * {@code
     public SysMLDoAction developPlan;
     :
     public void createStateDoActions()
     {
          :
          developPlan = (context) ->
          {
               gatherIntelligence();
               developOptions();
               selectOption();
               optionIntoPlan();
               reviewPlan()
          };
          :
     }
    }
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 * @see SysMLStateMachine#createStateDoActionFunctions
 */
@FunctionalInterface
public interface SysMLDoActionFunction extends SysMLActionFunction
{
	/**
	 * Specification of the action to be performed while state machine is in the
	 * associated state, i.e. the action to be performed by the state's
	 * {@code doAction} element. This function must be realized by an instance of a
	 * lambda expression.
	 * 
	 * @param context Optional part in whose context the state's associated state
	 *                machine executes.
	 */
	void perform(Optional<? extends StateBehaviorContext> context);
}