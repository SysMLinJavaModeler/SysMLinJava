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
 * SysMLinJava's representation of the SysML final state of a state machine. The
 * {@code SysMLFinalState} extends the {@code SysMLState} requiring
 * specification of the context and a name. Transitions to the
 * {@code SysMLFinalState} must be declared in the state machine's transition
 * fields and initialized with the {@code SysMLFinalState} as the nextState in
 * the state machines {@code createTransitions()} method.
 * <p>
 * Note that transitions out of and within the {@code SysMLState} as well as
 * elements such as the {@code onEnterActivity}, {@code doActivity}, and
 * {@code onExitActivity}, are not applicable for the FinalState.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLFinalState extends SysMLState
{
	/**
	 * Constructor
	 * 
	 * @param context the part in whose context the final state is to reside
	 * @param name         unique name for the final state
	 */
	public SysMLFinalState(Optional<? extends StateBehaviorContext> context, String name)
	{
		super(context, name);
	}

	@Override
	public void addTransition(SysMLTransition transition)
	{
		logger.warning("illegal attempt to add transition from final state; ignored: " + transition.identityString());
	}
}