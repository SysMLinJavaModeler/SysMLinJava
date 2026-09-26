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
package sysmlinjava.items;

import sysmlinjava.views.common.StackedProtocolObject;

/**
 * SysMLinJava's represention of the SysMLv1 signal. {@code SysMLSignal} is
 * abstract and can be used as the base class for all signal types used for
 * communicating energy, matter, or information between {@code SysMLPort}s.
 * The {@code SysMLSignal} also implements the {@code StackedProtocolObject}
 * interface which provides services for protocol objects in a protocol stack.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLSignal extends SysMLItem implements StackedProtocolObject
{
	/**
	 * Constructor for signal name
	 * 
	 * @param name name of the signal
	 * @param id unique ID for the signal
	 */
	protected SysMLSignal(String name, long id)
	{
		super(name, id);
	}

	/**
	 * Constructor
	 */
	protected SysMLSignal()
	{
		super();
	}
}
