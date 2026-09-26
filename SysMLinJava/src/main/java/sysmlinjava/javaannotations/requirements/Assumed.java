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
package sysmlinjava.javaannotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows is a assumed constraint, i.e. an
 * assumption for the requirement. The field variable is an instance of a
 * {@code SysMLConstraint} and should be created/initialized in the
 * {@code createAssumeds()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.requirements.SysMLRequirement
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface Assumed
{
}
