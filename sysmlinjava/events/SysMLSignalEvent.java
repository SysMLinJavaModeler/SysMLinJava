package sysmlinjava.events;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;

/**
 * SysMLinJava's representation of the SysML signal event. The
 * {@code SysMLSignalEvent} is the base class of all signal events and is used
 * extensively in state machine operations and state transition definition.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLSignalEvent extends SysMLEvent
{
	/**
	 * Signal that is associated with this signal event
	 */
	@Attribute
	public SysMLSignal signal;

	/**
	 * Index into array of ports for port on which this signal was received
	 */
	@Attribute
	public int index;

	/**
	 * Constructor for specified signal and name
	 * 
	 * @param name unique name of the event
	 */
	public SysMLSignalEvent(String name)
	{
		super(name);

		createSignal();
	}

	/**
	 * Mandatory overridable operation to create the signal event's signal. This
	 * operation performs an initialization only of the signal as specializations of
	 * the {@code SysMLSignalEvent} may set the signal after initialization in a
	 * different way, i.e. via specialized constructor, specialized {@code setter()}
	 * method, or via direct assignment/modification of the signal. An example of
	 * the operation is as follows:
	 * 
	 * <pre>
	 * signal = new ThrustSignal(new Thrust(0));
	 * </pre>
	 */
	public abstract void createSignal();

	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	protected void createAttributes()
	{
		super.createAttributes();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLSignalEvent [signal=%s, name=%s, id=%s]", signal, name, id);
	}

	/**
	 * Name of method to create signal, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createSignalMethodName = "createSignal";
	/**
	 * Name of variable signal, used by SysMLinJava tools, typically not needed for
	 * modeling
	 */
	public static final String signalAttributeName = "signal";
}
