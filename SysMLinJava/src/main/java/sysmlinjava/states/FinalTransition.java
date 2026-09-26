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
import sysmlinjava.events.SysMLSignalEvent;

/**
 * SysMLinJava representation of a transition to the final state of a state
 * machine. The {@code FinalTransition}(s) must be the last transition(s)
 * specified for all state machines. The {@code SysMLStateMachine} execution
 * terminates with the performance of this transition to the {@code finalState}
 * declared in the state machine. The {@code FinalTransition} is triggered by
 * the submission of a {@code FinalEvent} to the state machine when the
 * transition is "active" for the current state. It's initialization must
 * specify the instance of the {@code FinalState} declared in
 * {@code SysMLStateMachine} and an instance of a previous state (or
 * pseudo-state) to transition from.
 * <p>
 * <b>Note</b> this is a convenience class for transition to the final state. It
 * is optionally triggered by the occurance of the {@code FinalEvent}. If a
 * different triggering event and/or guard and/or effect are needed for this
 * transition to the {@code FinalState}, an instance of a
 * {@code SysMLTransition} should be used for the transition to the final state
 * instead.
 * 
 * @author ModelerOne
 *
 */
public final class FinalTransition extends SysMLTransition
{
	/**
	 * Constructor for transition from a state to the final state
	 * 
	 * @param context Optional {@code SysMLPart} which provides the context of
	 *                     the current state machine that contains this transition.
	 * @param prevState    The {@code SysMLState} instance which the
	 *                     {@code FinalTransition} is to transition from.
	 *                     <b>Note</b> this is a convenience class for transition to
	 *                     the final state. It is triggered by the occurance of the
	 *                     {@code FinalEvent}. If a different triggering event
	 *                     and/or guard and/or effect are needed for this transition
	 *                     to the {@code FinalState}, an instance of a
	 *                     {@code SysMLTransition} should be used instead.
	 * @param finalState   The {@code FinalState} instance to which this transition
	 *                     is to transition to, e.g. {@code finalState} in
	 *                     {@code SysMLStateMachine} or other instantiation of
	 *                     {@code FinalState}.
	 * @param name         Name of this transition, e.g. {@code OperationalToFinal}
	 */
	public FinalTransition(Optional<? extends StateBehaviorContext> context, SysMLState prevState, SysMLFinalState finalState, String name)
	{
		super(context, prevState, finalState, Optional.of(FinalEvent.class), Optional.empty(), Optional.empty(), name, SysMLTransitionKind.external);
	}

	/**
	 * Constructor for transition from a junction pseudo-state to the final state
	 * 
	 * @param context Optional {@code SysMLPart} which provides the context of
	 *                     the current state machine that contains this transition.
	 * @param prevState    The {@code SysMLJunctionPseudoState} instance which the
	 *                     {@code FinalTransition} is to transition from.
	 *                     <b>Note</b> this is a convenience class for transition to
	 *                     the final state from a junction. It assumes the junction
	 *                     is of multiple in-transitions to a single out-transition
	 *                     in which there is no guard condition on the
	 *                     out-transition - a common method of transitioning to the
	 *                     final state. If a guard and/or effect are needed on the
	 *                     transition to the final state, an instance of a
	 *                     {@code SysMLTransition} that specifies these items should
	 *                     be used instead.
	 * @param finalState   The {@code FinalState} instance to which this transition
	 *                     is to transition to, e.g. {@code finalState} in
	 *                     {@code SysMLStateMachine} or other instantiation of
	 *                     {@code FinalState}.
	 * @param name         Name of this transition, e.g. {@code JunctionToFinal} or
	 *                     {@code ChoiceToFinal}
	 */
	public FinalTransition(Optional<? extends StateBehaviorContext> context, SysMLJunctionPseudoState prevState, SysMLFinalState finalState, String name)
	{
		super(context, prevState, finalState, Optional.empty(), Optional.empty(), name);
	}

	@Override
	public boolean guardConditionSatisfied(Optional<? extends SysMLSignalEvent> currentEvent)
	{
		return true;
	}
}