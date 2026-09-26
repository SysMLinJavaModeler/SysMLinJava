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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sysmlinjava.actions.SysMLAction;

/**
 * SysMLinJava representation of the SysMLinJava state machine's vertex. The
 * {@code SysMLVertex} is used solely as a base class for state and
 * pseudo-state classes. Modelers should have no use for this class.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLVertex extends SysMLAction
{
	/**
	 * Optional context for which the state machine, and hence the state,
	 * executes. Activities performed while in the state may access the context
	 * part or port's properties as needed.
	 */
	public Optional<? extends StateBehaviorContext> context;
	/**
	 * Transitions out of and/or within the vertex.
	 */
	public List<SysMLTransition> transitions;

	/**
	 * Constructor of the SysMLVertex object.
	 * 
	 * @param context Optional {@code StateBehaviorContext} in which the vertex must
	 *                     operate.
	 * @param name         Optional name of the vertex.
	 */
	public SysMLVertex(Optional<? extends StateBehaviorContext> context, String name)
	{
		super(name, 0L);
		this.context = context;
		this.transitions = new ArrayList<>();
		this.name = Optional.of(name);
	}

	/**
	 * Adds the specified transition to the set of transitions out of and within the
	 * vertex
	 * 
	 * @param transition transition to be added
	 */
	public void addTransition(SysMLTransition transition)
	{
		transitions.add(transition);
	}

	/**
	 * Provides the identity string for this vertex, either the specified name, if
	 * provided, or the simple class name - SysMLVertex, if not.
	 * 
	 * @return the vertex's identity string.
	 */
	@Override
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}
}
