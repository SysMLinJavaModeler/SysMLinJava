package sysmlinjava.events;

import sysmlinjava.annotations.Attribute;

/**
 * SysMLinJava's representation of the SysML call event. The
 * {@code SysMLCallEvent} is the base class of all call events and is used
 * extensively in state machine operations and state transition definition. It
 * contains a single attribute, i.e. a string-based expression of the change
 * that spawns the change event.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLCallEvent extends SysMLEvent
{
	/**
	 * String expression for the behavior invocation associated with this event
	 */
	@Attribute
	public String callExpression;

	/**
	 * Constructor for specified operation call and name
	 * 
	 * @param name unique name of event
	 */
	public SysMLCallEvent(String name)
	{
		super(name);

		createCallExpression();
	}

	/**
	 * Mandatory overridable operation to create the call event's callExpression, a
	 * simple string value which identifies/specifies/invokes the behavior that is
	 * to be associated with this event. An exampe operation is as follows:
	 * 
	 * <pre>
	 	callExpression = "calibrate(level=278.97, precision=0.01)";
	 * </pre>
	 */
	public abstract void createCallExpression();

	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLCallEvent [callExpression=%s, name=%s, id=%s]", callExpression, name, id);
	}

	/**
	 * Name of method to create the call expression, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createCallExpressionMethodName = "createCallExpression";
	/**
	 * Name of attribute for call expression, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String callExpressionAttributeName = "callExpression";
}
