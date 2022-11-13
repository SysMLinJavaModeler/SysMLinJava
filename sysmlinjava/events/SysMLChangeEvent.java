package sysmlinjava.events;

import sysmlinjava.annotations.Attribute;

/**
 * SysMLinJava's representation of the SysML change event. The
 * {@code SysMLChangeEvent} is the base class of all change events and is used
 * extensively in state machine operations and state transition definition. It
 * contains a single attribute, i.e. a string-based expression of the change
 * that spawns the change event.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLChangeEvent extends SysMLEvent
{
	/**
	 * String expression specifying the change associated with this event
	 */
	@Attribute
	public String changeExpression;

	/**
	 * Constructor
	 * 
	 * @param name unique name of event
	 */
	public SysMLChangeEvent(String name)
	{
		super(name);
		createChangeExpression();
	}

	/**
	 * Mandatory overrideable operation to create the change expression. An example
	 * follows.
	 * 
	 * <pre>
	 * changeExpression = "target status change";
	 * </pre>
	 */
	protected abstract void createChangeExpression();

	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLChangeEvent [changeExpression=%s, name=%s, id=%s]", changeExpression, name, id);
	}

	/**
	 * Name of method to create the change expression, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createChangeExpressionMethodName = "createChangeExpression";
	/**
	 * Name of attribute for the change expression, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String changeExpressionAttributeName = "changeExpression";
}
