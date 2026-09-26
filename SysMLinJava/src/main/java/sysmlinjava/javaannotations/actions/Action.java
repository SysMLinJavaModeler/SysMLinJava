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
package sysmlinjava.javaannotations.actions;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field or method that follows represents a SysML action.
 * The annotated field should contain a variable that is an instance of the {@code SysMLAction} or
 * extension thereof.  The action instance is created/initialized in a
 * {@code createActions()} method of the part or port in which the
 * field is declared.
 * <p>
 * The annotated method should perform an action that uses and/or operates on other features
 * of the part or port, such at its attributes, items, parts, or ports, uses
 * input parameters, and/or invokes other actions, optionally returning a
 * result, i.e. executes a Java method. The annotated method should return a
 * void or a instance value. The method's lifetime occurrence(s) will be assumed
 * to be the same as that of the part or port in which it performs.
 * 
 * @author ModelerOne
 * @see sysmlinjava.actions.SysMLAction
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD})
public @interface Action
{

}
