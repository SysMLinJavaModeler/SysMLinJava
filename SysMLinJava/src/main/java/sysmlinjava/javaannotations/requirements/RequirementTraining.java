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
package sysmlinjava.javaannotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field or method that follows represents a SysML
 * requirement for training. A field variable-based definition of the training
 * requirement should be an instance of an extension of the {@code SysMLPart},
 * where the training is modeled as a part with attributes, parts, methods,
 * functions, and/or state machines (behaviors), as needed. This is the prefered
 * method of modeling the training requirement.
 * <p>
 * The method-based definition of the training should be an operation(s) that
 * contains the logic and objects for the process or procedures for the
 * training.
 * <p>
 * Of course, the training requirements could be modeled as one or more part
 * instances and/or one or more methods. In any case, each property with this
 * annotation will generate a corresponding training requirement.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD})
public @interface RequirementTraining
{
	/**
	 * Returns a set of kinds of verification methods that apply to the annotated
	 * requirement
	 * 
	 * @return a set of kinds of verification methods that apply to the annotated
	 *         requirement
	 */
	SysMLVerificationMethodKind[] requirementVerificationMethod() default SysMLVerificationMethodKind.Demonstration;
}
