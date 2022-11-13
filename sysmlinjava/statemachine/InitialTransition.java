package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.events.SysMLSignalEvent;

/**
 * SysMLinJava representation of the initial transition of a SysML state
 * machine, i.e. of the transition from the initial state to a state of the
 * state machine. The {@code InitialTransition} must be the first transition to
 * occur for all state machines.
 * <p>
 * The {@code SysMLStateMachine} execution commences with the performance of
 * this transition to a state of the specialized state machine. The
 * {@code InitialTransition} is triggered by the {@code InitialEvent} and has no
 * guard nor effect. It's initialization must specify the instance of the
 * {@code InitialState} specified in the {@code SysMLStateMachine} and a
 * specified next state to transition to. An example follows.
 * 
 * <pre>
    public void createTransitions()
    {
            :
        initialToPowerOff = new InitialTransition(contextBlock, initialState, powerOffState, initialToPowerOffEffect, "InitialToPowerOff");
            :
    }
 * </pre>
 * 
 * @author ModelerOne
 *
 */
public final class InitialTransition extends SysMLTransition
{
	/**
	 * Constructor for base initial transition to state and no effect.
	 * 
	 * @param contextBlock Optional {@code SysMLBlock} which provides the context of
	 *                     the current state machine
	 * @param initialState The {@code InitialState} instance from which this
	 *                     transition is to transition from, e.g.
	 *                     {@code initialState} in {@code
	 *                     SysMLStateMachine}
	 * @param nextState    The {@code SysMLState} instance which this transition is
	 *                     to transition to
	 * @param name         Name of this transition, e.g. "InitialToOperational"
	 */
	public InitialTransition(Optional<? extends SysMLBlock> contextBlock, SysMLInitialState initialState, SysMLState nextState, String name)
	{
		super(contextBlock, initialState, nextState, Optional.empty(), name);
	}

	/**
	 * Constructor for base initial transition to pseudo-state and no effect.
	 * 
	 * @param contextBlock Optional {@code SysMLBlock} which provides the context of
	 *                     the current state machine
	 * @param initialState The {@code InitialState} instance from which this
	 *                     transition is to transition from, e.g.
	 *                     {@code initialState} in {@code
	 *                     SysMLStateMachine}
	 * @param nextState    The {@code SysMLPseudoState} instance which this
	 *                     transition is to transition to
	 * @param name         Name of this transition, e.g.
	 *                     "InitialToOperationalChoice"
	 */
	public InitialTransition(Optional<? extends SysMLBlock> contextBlock, SysMLInitialState initialState, SysMLPseudoState nextState, String name)
	{
		super(contextBlock, initialState, nextState, Optional.empty(), name);
	}

	/**
	 * Constructor for initial transition to a state with an effect.
	 * 
	 * @param contextBlock Optional {@code SysMLBlock} which provides the context of
	 *                     the current state machine
	 * @param initialState The {@code InitialState} instance from which this
	 *                     transition is to transition from, e.g.
	 *                     {@code initialState} in {@code
	 *                     SysMLStateMachine}
	 * @param nextState    The {@code SysMLState} instance which this transition is
	 *                     to transition to
	 * @param effect       The {@code SysMLEffect} which this initial transition is
	 *                     to perform just before transition to the next (first)
	 *                     state
	 * @param name         Name of this transition, e.g. "InitialToOperational"
	 */
	public InitialTransition(Optional<? extends SysMLBlock> contextBlock, SysMLInitialState initialState, SysMLState nextState, SysMLEffect effect, String name)
	{
		super(contextBlock, initialState, nextState, Optional.of(effect), name);
	}

	/**
	 * Constructor for initial transition to a pseudo-state with an effect.
	 * 
	 * @param contextBlock Optional {@code SysMLBlock} which provides the context of
	 *                     the current state machine
	 * @param initialState The {@code InitialState} instance from which this
	 *                     transition is to transition from, e.g.
	 *                     {@code initialState} in {@code
	 *                     SysMLStateMachine}
	 * @param nextState    The {@code SysMLPseudoState} instance which this
	 *                     transition is to transition to
	 * @param effect       The {@code SysMLEffect} which this initial transition is
	 *                     to perform just before transition to the next (first)
	 *                     pseudo-state
	 * @param name         Name of this transition, e.g. "InitialToOperational"
	 */
	public InitialTransition(Optional<? extends SysMLBlock> contextBlock, SysMLInitialState initialState, SysMLPseudoState nextState, SysMLEffect effect, String name)
	{
		super(contextBlock, initialState, nextState, Optional.of(effect), name);
	}

	/**
	 * Boolean operation that is finally overridden to force satisfaction of guard
	 * condition. (IAW SysML, initial transition cannot have a guard)
	 */
	@Override
	public final boolean guardConditionSatisfied(Optional<? extends SysMLSignalEvent> currentEvent)
	{
		return true;
	}
}