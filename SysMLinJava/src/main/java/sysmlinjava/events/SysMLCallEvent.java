/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.events;

import sysmlinjava.javaannotations.events.CallExpression;

/**
 * SysMLinJava's representation of the SysML call event. The
 * {@code SysMLCallEvent} is the class used for all call events and is used
 * extensively in state machine operations and state transition definition. It
 * contains a single attribute, i.e. a string-based expression of the operation
 * call that spawns the change event.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.parts.SysMLPart#createEvents
 */
public final class SysMLCallEvent extends SysMLEvent
{
	/**
	 * String expression for the behavior invocation associated with this event
	 */
	@CallExpression
	public String callExpression;

	/**
	 * Constructor for specified operation call and name
	 * 
	 * @param callExpression expression of the call that spawned this event
	 * @param name           unique name of event
	 * @param id             unique ID of event
	 */
	public SysMLCallEvent(String callExpression, String name, Long id)
	{
		super(name, id);
		this.callExpression = callExpression;
	}

	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLCallEvent [callExpression=%s, name=%s, id=%s]", callExpression, name, id);
	}
}
