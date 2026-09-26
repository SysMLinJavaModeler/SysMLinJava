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

import java.util.Optional;

/**
 * {@code SysMLInitialState} is SysMLinJava's representation of the SysML
 * initial state of a state machine. The {@code SysMLInitialState} extends the
 * {@code SysMLPseudoState} requiring specification of the context and a
 * name.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLInitialState extends SysMLPseudoState
{
	/**
	 * Constructor
	 * 
	 * @param context in whose context the initial state is to reside
	 * @param name         unique name of the initial state
	 */
	public SysMLInitialState(Optional<? extends StateBehaviorContext> context, String name)
	{
		super(context, name);
	}

	@Override
	public void addTransition(SysMLTransition transition)
	{
		if (transitions.size() <= 1)
			super.addTransition(transition);
		else
			logger.warning("illegal attempt to add more than one transition from initial state; ignored: " + transition.getClass().getSimpleName());
	}
}