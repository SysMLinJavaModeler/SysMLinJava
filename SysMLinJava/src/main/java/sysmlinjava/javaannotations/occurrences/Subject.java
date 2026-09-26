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
package sysmlinjava.javaannotations.occurrences;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML subject. The
 * annotation is used for the {@code subject} field variable that is of type
 * {@code SysMLOccurence} in the {@code SysMLRequirement} and {@code SysMLCase}
 * classes. The variable should be created/initialized in these class's
 * {@code createSubject()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.occurrences.SysMLOccurrence
 * @see sysmlinjava.requirements.SysMLRequirement
 * @see sysmlinjava.actions.SysMLCase
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Subject
{

}
