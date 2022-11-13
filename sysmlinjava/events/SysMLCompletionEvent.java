package sysmlinjava.events;

import sysmlinjava.annotations.Attribute;

/**
 * SysMLinJava's representation of the SysML completion event. The
 * {@code SysMLCompletionEvent} is the base class of all completion events and
 * is used extensively in state machine operations and state transition
 * definitions to indicate completion of some behavior.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLCompletionEvent extends SysMLEvent
{
	/**
	 * String expression specifying the completion of a behavior associated with
	 * this event
	 */
	@Attribute
	public String completionExpression;

	/**
	 * Constructor
	 * 
	 * @param name unique name of the event
	 * @param id   unique id associated with the event
	 * 
	 */
	public SysMLCompletionEvent(String name, Long id)
	{
		super(name);
		this.id = id;

		createCompletionExpression();
	}

	/**
	 * Mandatory overridable operation to create the completion event's
	 * completionExpression, which identifies/specifies the completion of a behavior
	 * that is to be associated with this event. An exaple of this operation is as
	 * follows:
	 * 
	 * <pre>
	 * completionExpression = "stateMachine#2Completed";
	 * </pre>
	 */
	public abstract void createCompletionExpression();

	@Override
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLCompletionEvent [name=%s, id=%s, completionExpression=%s]", name, id, completionExpression);
	}

	/**
	 * Name of method to create the completion expression, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createCompletionExpressionMethodName = "createCompletionExpression";
	/**
	 * Name of attribute for the completion expression, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String completionExpressionVariableName = "completionExpression";
}
