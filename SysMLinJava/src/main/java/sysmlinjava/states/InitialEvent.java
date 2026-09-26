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
package sysmlinjava.states;

import sysmlinjava.events.SysMLEvent;

/**
 * Event indicating transition from the {@code InitialState} is triggered. The
 * {@code InitialEvent} is used by transitions to trigger transition from the
 * state machine's initial state.
 * 
 * @author ModelerOne
 *
 */
public final class InitialEvent extends SysMLEvent
{
	/**
	 * Constructor
	 */
	public InitialEvent()
	{
		super("InitialEvent", 0L);
	}
}