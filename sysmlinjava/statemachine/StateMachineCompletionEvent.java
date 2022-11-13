package sysmlinjava.statemachine;

import sysmlinjava.events.SysMLCompletionEvent;

/**
 * SysMLinJava representation of the SysML completion event that is submitted by
 * sub-state machines (state machines that execute within a state) to the parent
 * state machine to invoke its reaction to the completion of the sub-state
 * machine.
 * 
 * @author ModelerOne
 *
 */
public class StateMachineCompletionEvent extends SysMLCompletionEvent
{
	/**
	 * Constructor
	 * 
	 * @param stateMachineID identifier of the sub-state machine that has completed
	 *                       its execution, i.e. the sub-state machine that has
	 *                       achieved its final state.
	 */

	public StateMachineCompletionEvent(Long stateMachineID)
	{
		super(String.format("StateMachine%d", stateMachineID), stateMachineID);
	}

	@Override
	public String toString()
	{
		return String.format("StateMachineCompletionEvent [completionExpression=%s, name=%s, id=%s]", completionExpression, name, id);
	}

	@Override
	public void createCompletionExpression()
	{
		completionExpression = String.valueOf(id);
	}

}
