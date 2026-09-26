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
package sysmlinjava.states;

import java.util.Optional;

/**
 * SysMLinJava representation of the SysMLinJava state machine's pseudoState.
 * The {@code SysMLPseudoState} is used solely as a base class for SysML
 * pseudo-state classes for junction, choice, initial state, etc. Modelers
 * should have no need to use this class.
 * 
 * @author ModelerOne
 */
public abstract class SysMLPseudoState extends SysMLVertex
{
	/**
	 * Constructor
	 * 
	 * @param context in whose context this pseudo-state resides
	 * @param name    unique name of the pseudo-state.
	 */
	public SysMLPseudoState(Optional<? extends StateBehaviorContext> context, String name)
	{
		super(context, name);
	}

}
