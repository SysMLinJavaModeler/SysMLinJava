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
package sysmlinjava.javaannotations.verifications;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field or method that follows represents a SysML
 * verification case. The annotated field variable should be an instance of an
 * extension of the {@code SysMLVerificationCase} that is created/initialized in
 * a {@code createVerificationCases()} method. The annotated method should be
 * one that performs a verification case action.
 * 
 * @author ModelerOne
 * @see sysmlinjava.verifications.SysMLVerificationCase
 */
@Documented
@Retention(SOURCE)
@Target({ FIELD, METHOD })
public @interface VerificationCase
{
}
