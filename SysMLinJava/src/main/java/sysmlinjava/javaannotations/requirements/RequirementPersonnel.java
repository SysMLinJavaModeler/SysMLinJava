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
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for the
 * personnel using/operating the system. The annotation should be on a field
 * whose variable is an extension of the {@code SysMLPart}. The
 * {@code SysMLPart} extension should be the model of the personnel
 * using/operating the system. In essence, the personnel are other types of
 * "systems" with which the &#64;RequirementSystem system must interface. So the
 * SysMLinJava model should model the system users/operators similarly to how it
 * models systems, subsystems, and components, modeling interfaces between these
 * elements and the personnel model.
 * <p>
 * Note that the &#64;RequirementPersonnel annotation may be considered to be
 * redundant to the &#64;RequirementUser annotation to the extent that both
 * annotations can be used to identify model elements as system users.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD})
public @interface RequirementPersonnel
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
