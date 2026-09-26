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
package sysmlinjava.javaannotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML state or "state
 * machine", i.e. an instance of a {@code SysMLStateMachine} within the context
 * of the current {@code SysMLPart}, {@code SysMLItem}, or {@code SysMLPort}
 * that conforms to the SysML state machine that is typically defined by states
 * and transtions. The state machine is initialized in a
 * {@code createStateMachine()} method.
 * <p>
 * <b>Note:</b>The {@code SysMLItem} contains a field variable name
 * {@code stateMachine} of type {@code SysMLStateMachine} which should be used
 * for defining the part or item's state machine. However, if additional state
 * machines must be used by the part or item and/or by the part or item's
 * primary state machine (as part of compound states, sub-state machines, etc.),
 * then this annotation may be used to denote such declarations accordingly.
 * 
 * @author ModelerOne
 * @see sysmlinjava.states.SysMLStateMachine
 * @see sysmlinjava.parts.SysMLPart#stateMachine
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface StateMachine
{
}
