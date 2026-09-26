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

import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.items.Signal;

/**
 * SysMLinJava's representation of the SysML signal event. The
 * {@code SysMLSignalEvent} is the class used for all signal events and is used
 * extensively in state machine operations and state transition definition.
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.SysMLPart#createEvents
 */
public final class SysMLSignalEvent extends SysMLEvent
{
	/**
	 * Signal that is associated with this signal event
	 */
	@Signal
	public SysMLSignal signal;

	/**
	 * Index into array of ports for port on which this signal was received
	 */
	@Attribute
	public int index;

	/**
	 * Constructor for specified signal and name
	 * 
	 * @param signal signal that spawned this event
	 * @param name   unique name of the event
	 * @param id     unique ID of event
	 */
	public SysMLSignalEvent(SysMLSignal signal, String name, Long id)
	{
		super(name, id);
		this.signal = signal;
	}

	@Override
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLSignalEvent [signal=%s, name=%s, id=%s]", signal, name, id);
	}

	/**
	 * Name of variable signal, used by SysMLinJava tools, typically not needed for
	 * modeling
	 */
	public static final String signalAttributeName = "signal";
}
