package sysmlinjava.statemachine;

import sysmlinjava.events.SysMLEvent;

/**
 * Event indicating transition to the {@code FinalState} is triggered. The
 * {@code FinalEvent} is used by transitions to trigger transition to the state
 * machine's final state.
 * 
 * @author ModelerOne
 *
 */
public final class FinalEvent extends SysMLEvent
{
	/**
	 * Constructor
	 */
	public FinalEvent()
	{
		super();
	}
}