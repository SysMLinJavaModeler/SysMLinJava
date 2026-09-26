
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
package sysmlinjava.analysis;

import java.util.Optional;

import sysmlinjava.attributetypes.DurationMilliseconds;
import sysmlinjava.events.SysMLChangeEvent;
import sysmlinjava.events.SysMLTimeEvent;
import sysmlinjava.javaannotations.statemachines.Effect;
import sysmlinjava.javaannotations.statemachines.EffectActionFunction;
import sysmlinjava.javaannotations.statemachines.EntryActionFunction;
import sysmlinjava.javaannotations.statemachines.ExitActionFunction;
import sysmlinjava.javaannotations.statemachines.State;
import sysmlinjava.javaannotations.statemachines.Transition;
import sysmlinjava.states.FinalTransition;
import sysmlinjava.states.InitialTransition;
import sysmlinjava.states.SysMLEffect;
import sysmlinjava.states.SysMLEffectActionFunction;
import sysmlinjava.states.SysMLEntryActionFunction;
import sysmlinjava.states.SysMLExitActionFunction;
import sysmlinjava.states.SysMLState;
import sysmlinjava.states.SysMLStateMachine;
import sysmlinjava.states.SysMLTransition;
import sysmlinjava.states.SysMLTransitionKind;

/**
 * Implementation of the {@code SysMLStateMachine} for the
 * {@code ParametricAnalysisCase}. The {@code ParametricAnalysisStateMachine} is
 * a concrete implementation of a state machine for an analysis case that
 * requires and/or provides analysis parameters from/to parts whose bound
 * attribute values are changed in different threads. The
 * {@code ParametricAnalysisStateMachine} enables the parametric analysis of
 * analysis parameters that originate in different threads of execution.
 * <p>
 * The state machine afforded by this class consist of two states - an
 * initializing state and an operational state. The initializing state is void
 * of any operation and simply transitions to the operational state, but actions
 * can be assigned to the state as desired.
 * <p>
 * The operational state responds to the two types of events accepted by the
 * context parametric analysis case - the timer event and the parameter change
 * event. If the state machine was constructed with a timer specification, then
 * a timer is created to generate time events as specified. Upon exiting the
 * operational state, the timer is destroyed. The operational state will respond
 * to parameter change events as they are received from the parameter ports that
 * are notified of the parameter changes.
 * <p>
 * While in the operational state, timer events and parameter change events are
 * responded to internally to the state, i.e. there are two internal transitions
 * invoked by the two types of events and effects are provided to invoke
 * analysis case operations in response to the time and change events.
 * <p>
 * The {@code ParametricAnalysisStateMachine} implementation should satisfy
 * most, if not all event-driven needs of implementations of the
 * {@code ParametricAnalysisCase}. Use of the
 * {@code ParametricAnalysisStateMachine} should be limited, however, to
 * SysMLinJava models that involve multi-threaded executions where the
 * constraint parameters are changed by parts operating in multiple threads,
 * i.e. in concurrent part models. If all bound parameters are in parts that are
 * synchronous with the analysis case, then there is no need to create the state
 * machine for the analysis case.
 * 
 * @author ModelerOne
 * @see ParametricAnalysisCase
 */
public final class ParametricAnalysisStateMachine extends SysMLStateMachine
{
	/**
	 * State that performs initializing, if any, prior to becoming operations
	 */
	@State
	public SysMLState initializingState;
	/**
	 * State that performs operational event handling, i.e. responds to parameter
	 * change events and time events
	 */
	@State
	public SysMLState operationalState;

	/**
	 * Action performed upon entry to operational state
	 */
	@EntryActionFunction
	public SysMLEntryActionFunction onEnterOperationalAction;
	/**
	 * Action performed upon exit from operational state
	 */
	@ExitActionFunction
	public SysMLExitActionFunction onExitOperationalAction;

	/**
	 * Transition from initial to initializing state
	 */
	@Transition
	public InitialTransition initialToInitializingTransition;
	/**
	 * Transition from initializing to operational state
	 */
	@Transition
	public SysMLTransition initializingToOperationalTransition;
	/**
	 * Transition (internal) for occurence of a time event
	 */
	@Transition
	public SysMLTransition onTimeEventTransition;
	/**
	 * Transition (internal) for occurence of a parameter change event
	 */
	@Transition
	public SysMLTransition onParameterChangeEventTransition;
	/**
	 * Transition from operational to final state
	 */
	@Transition
	public FinalTransition toFinalTransition;

	/**
	 * Action to perform upon occurrence of a time event while in operational state
	 */
	@EffectActionFunction
	public SysMLEffectActionFunction onTimeEventTransitionEffectAction;
	/**
	 * Action to perform upon occurrence of a parameter change event while in
	 * operational state
	 */
	@EffectActionFunction
	public SysMLEffectActionFunction onParameterChangeEventTransitionEffectAction;

	/**
	 * Effect that performs the action to perform upon occurrence of a time event
	 * while in operational state
	 */
	@Effect
	public SysMLEffect onTimeEventTransitionEffect;
	/**
	 * Effect that performs the action to perform upon occurrence of a parameter
	 * change event while in operational state
	 */
	@Effect
	public SysMLEffect onParameterChangeEventTransitionEffect;

	/**
	 * String ID of the timer used to generate time events
	 */
	public final static String timerID = "periodTimer";

	/**
	 * Optional time of timer's initial delay period, i.e. time until first time
	 * event
	 */
	public Optional<DurationMilliseconds> timerInitialDelay;
	/**
	 * Optional time of timer's repeat period, i.e. time between repeat time events
	 */
	public Optional<DurationMilliseconds> timerPeriod;

	/**
	 * Constructor for no timer
	 * 
	 * @param analysisCase {@code ParametricAnalysisCase} in whose context this
	 *                     state machine is to operate
	 * @param name         unique name
	 */
	public ParametricAnalysisStateMachine(ParametricAnalysisCase analysisCase, String name)
	{
		super(Optional.of(analysisCase), true, name);
		timerInitialDelay = Optional.empty();
		timerPeriod = Optional.empty();
	}

	/**
	 * Constructor for timer
	 * 
	 * @param analysisCase      {@code ParametricAnalysisCase} in whose context this
	 *                          state machine is to operate
	 * @param name              unique name
	 * @param timerInitialDelay Optional time of timer's initial delay period, i.e.
	 *                          time until first time event
	 * @param timerPeriod       Optional time of timer's repeat period, i.e. time
	 *                          between repeat time events
	 */
	public ParametricAnalysisStateMachine(ParametricAnalysisCase analysisCase, String name, DurationMilliseconds timerInitialDelay, DurationMilliseconds timerPeriod)
	{
		super(Optional.of(analysisCase), true, name);
		this.timerInitialDelay = Optional.of(timerInitialDelay);
		this.timerPeriod = Optional.of(timerPeriod);
	}

	@Override
	protected void createStateEntryActionFunctions()
	{
		super.createStateEntryActionFunctions();
		onEnterOperationalAction = (SysMLEntryActionFunction) (context) ->
		{
			ParametricAnalysisCase analysisCase = (ParametricAnalysisCase) context.get();
			ParametricAnalysisStateMachine stateMachine = analysisCase.stateMachine.get();
			if (stateMachine.timerPeriod.isPresent())
			{
				if (stateMachine.timerInitialDelay.isPresent())
					stateMachine.startTimer(timerID, stateMachine.timerInitialDelay.get(), stateMachine.timerPeriod.get());
				else
					stateMachine.startTimer(timerID, DurationMilliseconds.ZERO, stateMachine.timerPeriod.get());
			}
		};
	}

	@Override
	protected void createStateExitActionFunctions()
	{
		super.createStateExitActionFunctions();
		onExitOperationalAction = (context) ->
		{
			ParametricAnalysisCase analysisCase = (ParametricAnalysisCase) context.get();
			ParametricAnalysisStateMachine stateMachine = analysisCase.stateMachine.get();
			if (stateMachine.timerPeriod.isPresent())
				stateMachine.stopTimer(timerID);
		};
	}

	@Override
	protected void createStates()
	{
		super.createStates();
		initializingState = new SysMLState(context, Optional.empty(), Optional.empty(), Optional.empty(), "Initializing");
		operationalState = new SysMLState(context, Optional.of(onEnterOperationalAction), Optional.empty(), Optional.of(onExitOperationalAction), "Operational");
	}

	@Override
	protected void createEffectActionFunctions()
	{
		onParameterChangeEventTransitionEffectAction = (event, context) ->
		{
			if (event.isPresent() && event.get() instanceof SysMLChangeEvent changeEvent)
			{
				ParametricAnalysisCase analysisCase = (ParametricAnalysisCase) context.get();
				analysisCase.onParameterChange(changeEvent.name.isPresent() ? changeEvent.name.get() : "not specified");
				analysisCase.perform();
				analysisCase.notifyAttributeObservers();
			}
			else
				logger.warning("missing/unexpected event for onParameterChangeEventTransitionEffectAction: " + event.getClass().getSimpleName());
		};
		onTimeEventTransitionEffectAction = (event, context) ->
		{
			if (event.isPresent() && event.get() instanceof SysMLTimeEvent)
			{
				if (((SysMLTimeEvent) event.get()).timerID.equals(timerID))
				{
					ParametricAnalysisCase analysisCase = (ParametricAnalysisCase) context.get();
					analysisCase.onTimeEvent();
					analysisCase.perform();
					analysisCase.notifyAttributeObservers();
				}
			}
			else
				logger.warning("missing/unexpected event for operationalOnTimeEventTransitionEffect: " + event.getClass().getSimpleName());
		};
	}

	@Override
	protected void createEffects()
	{
		onParameterChangeEventTransitionEffect = new SysMLEffect(context, onParameterChangeEventTransitionEffectAction, "operationalOnParameterChangeEventTransition");
		onTimeEventTransitionEffect = new SysMLEffect(context, onTimeEventTransitionEffectAction, "operationalOnTimeEventTransition");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(context, initialState, initializingState, "IntialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(context, initializingState, operationalState, "InitializingToOperational");
		onTimeEventTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLTimeEvent.class), Optional.empty(),
		Optional.of(onTimeEventTransitionEffect), "OperationalOnTimeEvent", SysMLTransitionKind.internal);
		onParameterChangeEventTransition = new SysMLTransition(context, operationalState, operationalState, Optional.of(SysMLChangeEvent.class), Optional.empty(),
		Optional.of(onParameterChangeEventTransitionEffect), "OperationalOnParameterChangeEvent", SysMLTransitionKind.internal);
		toFinalTransition = new FinalTransition(context, operationalState, finalState, "OperationalToFinal");
	}
}
