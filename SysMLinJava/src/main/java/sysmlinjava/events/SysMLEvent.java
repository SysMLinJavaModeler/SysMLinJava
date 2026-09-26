/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.events;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.occurrences.SysMLOccurrence;

/**
 * SysMLinJava's representation of the SysML event.
 * <h2>Basic event class</h2>The {@code SysMLEvent} is the base class of all
 * events (signal, call, change, completion, time, etc.) and is used extensively
 * in state machine operations and state transition definition. Specialized
 * events can be defined by declaring classes that extend the {@code SysMLEvent}
 * class, but SysML defines, and SysMLinJava provides specialized events that
 * can satisfy most, if not all needs for specialized events, e.g.
 * {@code SysMLSignalEvent}, {@code SysMLChangeEvent}, {@code SysMLTimeEvent},
 * etc.
 * <h3>Prioritizing events</h3> The event queue of the {@code SysMLStateMachine}
 * is a priority queue that can optionally be configured to prioritize events in
 * the queue (by defining an event {@code Comparator} class). If the priority
 * queue is so configured, then events that are subject to comparison must
 * override the {@code compareTo} method for the desired comparison logic.
 * 
 * @author ModelerOne
 * @see sysmlinjava.events.SysMLSignalEvent
 * @see sysmlinjava.events.SysMLTimeEvent
 * @see sysmlinjava.events.SysMLCallEvent
 * @see sysmlinjava.events.SysMLChangeEvent
 * @see sysmlinjava.events.SysMLCompletionEvent
 * @see sysmlinjava.parts.SysMLPart#createEvents
 * @see sysmlinjava.states.SysMLStateMachine#eventComparator
 * @see sysmlinjava.states.SysMLStateMachine#createEventComparator
 */
public abstract class SysMLEvent extends SysMLOccurrence implements Comparable<SysMLEvent>
{
	/**
	 * Constructor for unspecified occurrence
	 * 
	 * @param name name of the event
	 * @param id   unique ID for the event
	 */
	public SysMLEvent(String name, Long id)
	{
		super(name, id);
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
		while (result == false && !hierarchyClass.equals(SysMLAnything.class))
			if (hierarchyClass.equals(eventClass))
				result = true;
			else
				hierarchyClass = hierarchyClass.getSuperclass();
		return result;
	}

	@Override
	public int compareTo(SysMLEvent o)
	{
		return 0;
	}
}