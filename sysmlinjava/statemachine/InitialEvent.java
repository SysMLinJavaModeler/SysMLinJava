package sysmlinjava.statemachine;

import sysmlinjava.events.SysMLEvent;

/**
 * Event indicating transition from the {@code InitialState} is triggered. The
 * {@code InitialEvent} is used by transitions to trigger transition from the
 * state machine's initial state.
 * 
 * @author ModelerOne
 *
 */
public final class InitialEvent extends SysMLEvent
{
	/**
	 * Constructor
	 */
	public InitialEvent()
	{
		super("InitialEvent");
	}
}