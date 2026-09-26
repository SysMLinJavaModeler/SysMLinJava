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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import sysmlinjava.events.SysMLCompletionEvent;

/**
 * The SysMLinJava representation of the state in SysML's state machine.
 * {@code SysMLState} should be used as the type of all states in the state
 * machine and should not be extended for further specialization.
 * {@code SysMLState} contains the enter, do, and exit activities. In lieu of
 * the do action, the state can contain sub-state machines. As a vertex, the
 * {@code SysMLState} includes state transitions which are added by
 * {@code SysMLTransition} initializers. An example follows.
 * 
 * <pre>
		:
	&#64;State
	public SysMLState ice;
	&#64;State
	public SysMLState liquid;
	&#64;State
	public SysMLState gas;
	&#64;State
	public SysMLState decomposed;
		:
	&#64;Override
	protected void createStates()
	{
		super.createStates();
		ice = new SysMLState(context, "Ice");
		liquid = new SysMLState(context, "Liquid");
		gas = new SysMLState(context, "Gas");
		decomposed = new SysMLState(context, Optional.of(decomposedOnEnterAction), Optional.empty(), Optional.empty(), "Decomposed");
	}
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 * @see SysMLStateMachine#createStates
 */
public class SysMLState extends SysMLVertex
{
	/**
	 * Optional action to be performed upon entry into the state.
	 */
	public Optional<SysMLEntryActionFunction> onEnterAction;
	/**
	 * Optional action to be performed while in the state. If set, the
	 * {@code doAction} is executed in lieu of the {@code subStateMachines}.
	 */
	public Optional<SysMLDoActionFunction> doAction;
	/**
	 * Optional action to be performed upon exiting from the state.
	 */
	public Optional<SysMLExitActionFunction> onExitAction;
	/**
	 * List collection of state machines to be executed while in the (composite)
	 * state. Multiple sub-state machines are executed in parallel, i.e. as
	 * concurrent state machines within the context of the current state. The
	 * {@code subStateMachines} execute in lieu of the {@code doAction}, if set.
	 */
	public List<? extends SysMLStateMachine> subStateMachines;
	/**
	 * List collection of IDs of sub-state machines that have submitted
	 * {@code StateMachineCompletionEvent}s to be executed while in the (composite)
	 * state. Used to determine when all sub-state machines have completed, i.e.
	 * have transitioned to their final states.
	 */
	public List<Long> completedSubStateMachineIDs;
	/**
	 * Optional state machine that contains this state. The
	 * {@code containingStateMachine} is required if there are any
	 * {@code subStateMachines} and the {@code stateMachine} of {@code context}
	 * will be used by default, but this value can be set to another stateMachine as
	 * needed.
	 */
	public Optional<? extends SysMLStateMachine> containingStateMachine;
	/**
	 * Optional Future for the {@code doAction} which runs in a separate thread
	 * while in the current state. This future is used to stop the
	 * {@code doAction} if/when exit out of the current state is triggered.
	 */
	public Optional<Future<?>> doActionFuture;
	/**
	 * Optional {@code ScheduledThreadPoolExecutor} for the {@code doAction} which
	 * runs the separate thread while in the current state.
	 */
	public Optional<ScheduledThreadPoolExecutor> doActionExecutor;

	/**
	 * Constructor for specifying enter, do, exit activities of the state.
	 * 
	 * @param context    Optional SysMLPart based context in which the state
	 *                        must operate.
	 * @param onEnterFunction Optional action to be performed upon entry into the
	 *                        state.
	 * @param doAction      Optional action to be performed while in the state.
	 * @param onExitFunction  Optional action to be performed upon exit from the
	 *                        state.
	 * @param name            Optional name of the state.
	 */
	public SysMLState(Optional<? extends StateBehaviorContext> context, Optional<SysMLEntryActionFunction> onEnterFunction, Optional<SysMLDoActionFunction> doAction, Optional<SysMLExitActionFunction> onExitFunction, String name)
	{
		super(context, name);
		this.transitions = new ArrayList<>();
		this.onEnterAction = onEnterFunction;
		this.doAction = doAction;
		this.onExitAction = onExitFunction;
		if (doAction.isPresent())
		{
			if (!context.isPresent())
				doActionExecutor = Optional.of(new ScheduledThreadPoolExecutor(1));
		}
		this.doActionFuture = Optional.empty();
		this.subStateMachines = new ArrayList<>();
		this.completedSubStateMachineIDs = new ArrayList<>();
		this.containingStateMachine = Optional.empty();
	}

	/**
	 * Constructor for specifying enter and exit activities of the state as well as
	 * a list of sub-state machines that are to execute while in the state.
	 * 
	 * @param context     Optional SysMLPart based context in which the state
	 *                         must operate.
	 * @param onEnterFunction  Optional action to be performed upon entry into the
	 *                         state.
	 * @param subStateMachines List of behaviors (state machines) to be executed
	 *                         while in the state.
	 * @param onExitFunction   Optional action to be performed upon exit from the
	 *                         state.
	 * @param name             Optional name of the state.
	 */
	public SysMLState(StateBehaviorContext context, Optional<SysMLEntryActionFunction> onEnterFunction, List<? extends SysMLStateMachine> subStateMachines, Optional<SysMLExitActionFunction> onExitFunction, String name)
	{
		super(Optional.of(context), name);
		this.transitions = new ArrayList<>();
		this.onEnterAction = onEnterFunction;
		this.doAction = Optional.empty();
		this.onExitAction = onExitFunction;
		this.doActionFuture = Optional.empty();
		this.subStateMachines = subStateMachines;
		this.completedSubStateMachineIDs = new ArrayList<>();
		this.containingStateMachine = Optional.empty();
	}

	/**
	 * Constructor for specifying state with no enter, do, exit activities and no
	 * sub-state machines.
	 * 
	 * @param context Optional {@code SysMLPart}-based context in which the
	 *                     state must operate.
	 * @param name         Name of the state.
	 */
	public SysMLState(Optional<? extends StateBehaviorContext> context, String name)
	{
		this(context, Optional.empty(), Optional.empty(), Optional.empty(), name);
	}

	/**
	 * Returns whether or not receipt of the specified completion event means that
	 * all sub-state machines have now completed.
	 * 
	 * @param completionEvent completion event presumably just received from one of
	 *                        the sub-state machines
	 * @return true if all sub-state machines are now completed, false otherwise
	 */
	public boolean subStateMachinesCompleted(SysMLCompletionEvent completionEvent)
	{
		completedSubStateMachineIDs.add(Long.valueOf(completionEvent.id));
		boolean allCompleted = true;
		if (!subStateMachines.isEmpty())
		{
			ListIterator<? extends SysMLStateMachine> iterator = subStateMachines.listIterator();
			while (iterator.hasNext() && allCompleted)
			{
				SysMLStateMachine nextSM = iterator.next();
				boolean smCompleted = completedSubStateMachineIDs.contains(nextSM.id);
				boolean smFinal = nextSM.currentState.isPresent() && nextSM.currentState.get() == nextSM.finalState;
				if (!smCompleted || !smFinal)
					allCompleted = false;
			}
			if (allCompleted)
				completedSubStateMachineIDs.clear();
		}
		else
			allCompleted = false;
		return allCompleted;
	}

	/**
	 * Operation called upon entry into the state. {@code onEnter()} invokes the
	 * specified ({@code onEnterAction}, if provided. Also, if they are present,
	 * all sub-state machines are executed (started)
	 */
	public void onEnter()
	{
		if (onEnterAction.isPresent())
			onEnterAction.get().perform(context);
		if (!subStateMachines.isEmpty())
			executeSubStateMachines();
	}

	/**
	 * Action to be performed while in the state. {@code doWhileInState()} invokes
	 * the specified {@code doAction}, if provided, performing the action in a
	 * separate thread of execution.
	 */
	public void doWhileInState()
	{
		if (doAction.isPresent())
		{
			Runnable runner = new Runnable()
			{
				@Override
				public void run()
				{
					doAction.get().perform(context);
				}
			};
			if (context.isPresent())
				doActionFuture = Optional.of(context.get().getExecutionThreads().submit(runner));
			else
				doActionFuture = Optional.of(doActionExecutor.get().submit(runner));
		}
	}

	/**
	 * Operation called upon exit from the state. {@code onExit()} invokes the
	 * specified {@code onExitAction}, if provided, but only after it terminates
	 * the {@code doAction}, if present and started, or terminates the sub-state
	 * machines, if present.
	 */
	public void onExit()
	{
		if (!subStateMachines.isEmpty())
			terminateSubStateMachines();
		else if (doActionFuture.isPresent() && !doActionFuture.get().isDone())
			doActionFuture.get().cancel(true);
		if (onExitAction.isPresent())
			onExitAction.get().perform(context);
	}

	/**
	 * Executes (starts) all of the sub-state machines that have been added to this
	 * state
	 */
	private void executeSubStateMachines()
	{
		completedSubStateMachineIDs.clear();
		subStateMachines.forEach(subStateMachine ->
		{
			if (containingStateMachine.isEmpty() && context.isPresent())
				containingStateMachine = context.get().getStateMachine();
			subStateMachine.containingStateMachine = containingStateMachine;
			subStateMachine.start();
		});
	}

	/**
	 * Terminates (stops) all of the sub-state machines that have been added to this
	 * state.
	 */
	private void terminateSubStateMachines()
	{
		subStateMachines.forEach(subStateMachine -> subStateMachine.stop());
	}
}
