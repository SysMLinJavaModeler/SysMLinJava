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

import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for an
 * interface. The annotation should be on a field whose variable is of the
 * {@code SysMLFlowConnector} type. The connector represented by the variable
 * defines the interface between the &#64;RequirementSystem system and another
 * &#64;RequirementSystem system or &#64;RequirementEnvironment environment.
 * <p>
 * The variable that represents the interface connector should be initialized in
 * the {@code createFlowConnectors()} operation by an object
 * creation/initialization statement. The connector type should be specified as
 * "virtual", to indicate it represents an interface.
 * 
 * @author ModelerOne
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementInterface
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
