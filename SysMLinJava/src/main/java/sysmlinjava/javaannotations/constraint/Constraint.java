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
package sysmlinjava.javaannotations.constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field or method that follows represents a SysML
 * constraint. In the case of a field, the field variable should be an instance
 * of the {@code SysMLConstraint} or of an extension of same that is
 * created/initialized in a {@code createConstraints()} method. In the case of a
 * method, the method should perform a logical predicate on other features of
 * the part, port, or item, returning a boolean value. Note that in the case of
 * a method-based constraint, its occurrence will be assumed to be the same as
 * that of the part, port, or item in which it performs.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.constraint.SysMLConstraint
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD})
public @interface Constraint
{
}
