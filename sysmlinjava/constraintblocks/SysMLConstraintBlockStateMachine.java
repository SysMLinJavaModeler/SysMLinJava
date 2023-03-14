
package sysmlinjava.constraintblocks;

import java.util.Optional;
import sysmlinjava.annotations.statemachines.Effect;
import sysmlinjava.annotations.statemachines.EffectActivity;
import sysmlinjava.annotations.statemachines.OnEnterActivity;
import sysmlinjava.annotations.statemachines.OnExitActivity;
import sysmlinjava.annotations.statemachines.State;
import sysmlinjava.annotations.statemachines.Transition;
import sysmlinjava.events.SysMLTimeEvent;
import sysmlinjava.statemachine.FinalTransition;
import sysmlinjava.statemachine.InitialTransition;
import sysmlinjava.statemachine.SysMLEffect;
import sysmlinjava.statemachine.SysMLEffectActivity;
import sysmlinjava.statemachine.SysMLOnEnterActivity;
import sysmlinjava.statemachine.SysMLOnExitActivity;
import sysmlinjava.statemachine.SysMLState;
import sysmlinjava.statemachine.SysMLStateMachine;
import sysmlinjava.statemachine.SysMLTransition;
import sysmlinjava.statemachine.SysMLTransitionKind;
import sysmlinjava.valuetypes.DurationMilliseconds;

/**
 * Implementation of the {@code SysMLStateMachine} for the
 * {@code SysMLConstraintBlock}. The {@code SysMLConstraintBlockStateMachine} is
 * a concrete implementation of a state machine for a constraint block that
 * requires and/or provides constraint parameters from/to blocks whose bound
 * values are changed in different threads. The
 * {@code SysMLConstraintBlockStateMachine} enables the parametric analysis of
 * constraint parameters that originate in different threads of execution.
 * <p>
 * The state machine afforded by this class consist of two states - an
 * initializing state and an operational state. The initializing state is void
 * of any operation and simply transitions to the operational state, but
 * activities can be assigned to the state as desired.
 * <p>
 * The operational state responds to the two types of events accepted by the
 * context constraint block - the timer event and the parameter change event. If
 * the state machine was constructed with a timer specification, then a timer is
 * created to generate time events as specified. Upon exiting the operational
 * state, the timer is destroyed. The operational state will respond to
 * parameter change events as they are received from the parameter ports that
 * are notified of the parameter changes.
 * <p>
 * While in the operational state, timer events and parameter change events are
 * responded to internally to the state, i.e. there are two internal transitions
 * invoked by the two types of events and effects are provided to invoke
 * constraint block operations in response to the time and change events.
 * <p>
 * The {@code SysMLConstraintBlockStateMachine} implementation should satisfy
 * most, if not all event-driven needs of implementations of the
 * {@code SysMLConstraintBlock}. Use of the
 * {@code SysMLConstraintBlockStateMachine} should be limited, however, to
 * SysMLinJava models that involve multi-threaded executions where the
 * constraint parameters are changed and retrieved by blocks operating in
 * multiple threads, i.e. in concurrent block models. If all bound parameters
 * are in blocks that are synchronous with the constraint block, then there is
 * no need to create the state machine for the constraint block.
 * 
 * @author ModelerOne
 *
 * @see SysMLConstraintBlock
 */
public class SysMLConstraintBlockStateMachine extends SysMLStateMachine
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
	 * Activity performed upon entry to operational state
	 */
	@OnEnterActivity
	public SysMLOnEnterActivity onEnterOperationalActivity;
	/**
	 * Activity performed upon exit from operational state
	 */
	@OnExitActivity
	public SysMLOnExitActivity onExitOperationalActivity;

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
	 * Activity to perform upon occurrence of a time event while in operational
	 * state
	 */
	@EffectActivity
	public SysMLEffectActivity onTimeEventTransitionEffectActivity;
	/**
	 * Activity to perform upon occurrence of a parameter change event while in
	 * operational state
	 */
	@EffectActivity
	public SysMLEffectActivity onParameterChangeEventTransitionEffectActivity;

	/**
	 * Effect that performs the activity to perform upon occurrence of a time event
	 * while in operational state
	 */
	@Effect
	public SysMLEffect onTimeEventTransitionEffect;
	/**
	 * Effect that performs the activity to perform upon occurrence of a parameter
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
	 * @param constraintBlock constraint block in whose context this state machine
	 *                        is to operate
	 * @param name            unique name
	 */
	public SysMLConstraintBlockStateMachine(SysMLConstraintBlock constraintBlock, String name)
	{
		super(Optional.of(constraintBlock), true, name);
		timerInitialDelay = Optional.empty();
		timerPeriod = Optional.empty();
	}

	/**
	 * Constructor for timer
	 * 
	 * @param constraintBlock   constraint block in whose context this state machine
	 *                          is to operate
	 * @param name              unique name
	 * @param timerInitialDelay Optional time of timer's initial delay period, i.e.
	 *                          time until first time event
	 * @param timerPeriod       Optional time of timer's repeat period, i.e. time
	 *                          between repeat time events
	 */
	public SysMLConstraintBlockStateMachine(SysMLConstraintBlock constraintBlock, String name, DurationMilliseconds timerInitialDelay, DurationMilliseconds timerPeriod)
	{
		super(Optional.of(constraintBlock), true, name);
		this.timerInitialDelay = Optional.of(timerInitialDelay);
		this.timerPeriod = Optional.of(timerPeriod);
	}

	@Override
	protected void createStateOnEnterActivities()
	{
		super.createStateOnEnterActivities();
		onEnterOperationalActivity = (contextBlock) ->
		{
			SysMLConstraintBlock constraintBlock = (SysMLConstraintBlock)contextBlock.get();
			SysMLConstraintBlockStateMachine stateMachine = (SysMLConstraintBlockStateMachine)constraintBlock.stateMachine.get();
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
	protected void createStateOnExitActivities()
	{
		super.createStateOnExitActivities();
		onExitOperationalActivity = (contextBlock) ->
		{
			SysMLConstraintBlock constraintBlock = (SysMLConstraintBlock)contextBlock.get();
			SysMLConstraintBlockStateMachine stateMachine = (SysMLConstraintBlockStateMachine)constraintBlock.stateMachine.get();
			if (stateMachine.timerPeriod.isPresent())
				stateMachine.stopTimer(timerID);
		};
	}

	@Override
	protected void createStates()
	{
		super.createStates();
		initializingState = new SysMLState(contextBlock, Optional.empty(), Optional.empty(), Optional.empty(), "Initializing");
		operationalState = new SysMLState(contextBlock, Optional.of(onEnterOperationalActivity), Optional.empty(), Optional.of(onExitOperationalActivity), "Operational");
	}

	@Override
	protected void createEffectActivities()
	{
		super.createEffectActivities();
		onParameterChangeEventTransitionEffectActivity = (event, contextBlock) ->
		{
			if (event.isPresent() && event.get() instanceof SysMLParameterChangeEvent)
			{
				SysMLConstraintBlock constraintBlock = (SysMLConstraintBlock)contextBlock.get();
				constraintBlock.onParameterChange(((SysMLParameterChangeEvent)event.get()).changeExpression);
				constraintBlock.performConstraints();
				constraintBlock.notifyValueChangeObservers();
			}
		};
		onTimeEventTransitionEffectActivity = (event, contextBlock) ->
		{
			if (event.isPresent() && event.get() instanceof SysMLTimeEvent)
			{
				if (((SysMLTimeEvent)event.get()).timerID.equals(timerID))
				{
					SysMLConstraintBlock constraintBlock = (SysMLConstraintBlock)contextBlock.get();
					constraintBlock.onTimeEvent();
					constraintBlock.performConstraints();
					constraintBlock.notifyValueChangeObservers();
				}
			}
			else
				logger.warning("missing/unexpected event for operationalOnTimeEventTransitionEffect: " + event.getClass().getSimpleName());
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		onParameterChangeEventTransitionEffect = new SysMLEffect(contextBlock, onParameterChangeEventTransitionEffectActivity, "operationalOnParameterChangeEventTransition");
		onTimeEventTransitionEffect = new SysMLEffect(contextBlock, onTimeEventTransitionEffectActivity, "operationalOnTimeEventTransition");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "IntialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		onTimeEventTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(SysMLTimeEvent.class), Optional.empty(), Optional.of(onTimeEventTransitionEffect),
			"OperationalOnTimeEvent", SysMLTransitionKind.internal);
		onParameterChangeEventTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(SysMLParameterChangeEvent.class), Optional.empty(),
			Optional.of(onParameterChangeEventTransitionEffect), "OperationalOnParameterChangeEvent", SysMLTransitionKind.internal);
		toFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "OperationalToFinal");
	}
}
