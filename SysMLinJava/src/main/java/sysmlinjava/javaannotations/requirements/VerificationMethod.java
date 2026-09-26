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
 * Indicates that the field that follows represents a SysML verification method.
 * The field variable should be an instance of a
 * {@code List<SysMLVerificationMethodKind>} and should be created/initialized
 * in the declaration of the field variable or in the
 * {@code createVerificationMethods()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.requirements.SysMLRequirement
 * @see sysmlinjava.requirements.SysMLVerificationMethodKind
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface VerificationMethod
{
}
