package sysmlinjava.events;

import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava's representation of the SysML event.
 * <h2>Basic event class</h2>The {@code SysMLEvent} is
 * the base class of all events (signal, call, change, completion, time, etc.)
 * and is used extensively in state machine operations and state transition
 * definition.
 * <h3>Prioritizing events</h3>
 * The event queue of the {@code SysMLStateMachine} is a priority queue that can
 * optionally be configured to prioritize events in the queue (by defining an
 * event {@code Comparator} class). If the priority queue is so configured, then
 * events that are subject to comparison must override the {@code compareTo}
 * method for the desired comparison logic.
 * 
 * @author ModelerOne
 *
 *@see sysmlinjava.statemachine.SysMLStateMachine#eventComparator
 *@see sysmlinjava.statemachine.SysMLStateMachine#createEventComparator
 */
public abstract class SysMLEvent extends SysMLClass implements Comparable<SysMLEvent>
{
	/**
	 * Constructor - default
	 */
	public SysMLEvent()
	{
		super();
	}

	/**
	 * Constructor - name initialization
	 * 
	 * @param name name of the event
	 */
	public SysMLEvent(String name)
	{
		super(name);
	}

	/**
	 * Returns whether or not the specified SysMLEvent is of the same type (class)
	 * as this {@code SysMLEvent}. Used primarily by {@code SysMLStateMachine} to
	 * compare the current event to a transition's trigger event.
	 * 
	 * @param eventClass class to compare to this event
	 * @return true if of same class, false otherwise
	 */
	public boolean isOfEventType(Class<? extends SysMLEvent> eventClass)
	{
		boolean result = false;
		Class<?> hierarchyClass = this.getClass();
		while (result == false && !hierarchyClass.equals(SysMLClass.class))
		{
			if (hierarchyClass.equals(eventClass))
				result = true;
			else
				hierarchyClass = hierarchyClass.getSuperclass();
		}
		return result;
	}

	@Override
	protected void createAttributes()
	{
		super.createAttributes();
	}

	@Override
	public int compareTo(SysMLEvent o)
	{
		return 0;
	}
}