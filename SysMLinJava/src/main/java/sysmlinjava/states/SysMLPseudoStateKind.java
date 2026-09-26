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

/**
 * SysMLinJava representation of the SysML enumeration of the kinds of
 * pseudo-states that can be used in a state machine. SysMLinJava currently only
 * supports the initial, choice, and junction types of pseudo-states, as
 * reflected in this enumeration. To the extend that SysMLinJava's state machine
 * classes have no need for this enumeration, it is not used anywhere in
 * SysMLinJava and is provided only as a convenience for customized modeling.
 *
 * @author ModelerOne
 * @see sysmlinjava.states.SysMLInitialState
 * @see sysmlinjava.states.SysMLChoicePseudoState
 * @see sysmlinjava.states.SysMLJunctionPseudoState
 */
public enum SysMLPseudoStateKind
{
	/** Initial pseudo-state */
	initial,
	/** Choice pseudo-state */
	choice,
	/** Junction pseudo-state */
	junction;
}
