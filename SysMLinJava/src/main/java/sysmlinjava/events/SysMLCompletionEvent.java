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

import sysmlinjava.javaannotations.events.CompletionExpression;

/**
 * SysMLinJava's representation of the SysML completion event. The
 * {@code SysMLCompletionEvent} is the class used for all completion events and
 * is used extensively in state machine operations and state transition
 * definitions to indicate completion of some behavior.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.parts.SysMLPart#createEvents
 */
public final class SysMLCompletionEvent extends SysMLEvent
{
	/**
	 * String expression specifying the completion of a behavior associated with
	 * this event
	 */
	@CompletionExpression
	public String completionExpression;

	/**
	 * Constructor
	 * 
	 * @param completionExpression expression of the completion that spawned this
	 *                             event
	 * @param name                 unique name of the event
	 * @param id                   unique id associated with the event
	 * 
	 */
	public SysMLCompletionEvent(String completionExpression, String name, Long id)
	{
		super(name, id);
		this.completionExpression = completionExpression;
	}

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
	 * Name of attribute for the completion expression, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String completionExpressionVariableName = "completionExpression";
}
