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

import sysmlinjava.javaannotations.events.ChangeExpression;

/**
 * SysMLinJava's representation of the SysML change event. The
 * {@code SysMLChangeEvent} is the class used for all change events and is used
 * extensively in state machine operations and state transition definition. It
 * contains a single attribute, i.e. a string-based expression of the change
 * that spawned the change event.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.parts.SysMLPart#createEvents
 */
public final class SysMLChangeEvent extends SysMLEvent
{
	/**
	 * String expression specifying the change associated with this event
	 */
	@ChangeExpression
	public String changeExpression;

	/**
	 * Constructor
	 * 
	 * @param changeExpression expression of the change that spawned the event
	 * @param name             unique name of event
	 * @param id               unique ID of event
	 */
	public SysMLChangeEvent(String changeExpression, String name, Long id)
	{
		super(name, id);
		this.changeExpression = changeExpression;
	}

	@Override
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLChangeEvent [changeExpression=%s, name=%s, id=%s]", changeExpression, name, id);
	}
}
