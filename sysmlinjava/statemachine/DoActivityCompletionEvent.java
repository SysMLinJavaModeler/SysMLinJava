package sysmlinjava.statemachine;

import sysmlinjava.events.SysMLCompletionEvent;

/**
 * SysMLinJava's representation of the SysML completion event that is submitted
 * by a behavior in state, i.e. by the {@code SysMLDoActivity} of the
 * {@code SysMLState}. The do-Activity submits the completion event to the state
 * machine upon completion of the behavior. Transitions out of the state may/may
 * not be triggered by the completion event, as specified by the state machine's
 * model.
 * 
 * @author ModelerOne
 *
 */
public class DoActivityCompletionEvent extends SysMLCompletionEvent
{
	/**
	 * Constructor
	 * @param id unique identifier of the event
	 */
	public DoActivityCompletionEvent(Long id)
	{
		super("DoActivity", id);
	}

	@Override
	public void createCompletionExpression()
	{
		completionExpression = String.valueOf(id);
	}

	@Override
	public String toString()
	{
		return String.format("DoActivityCompletionEvent [completionExpression=%s, name=%s, id=%s]", completionExpression, name, id);
	}
}
