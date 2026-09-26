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
package sysmlinjava.requirements;

import java.util.Optional;

/**
 * SysMLinJava representation of SysML's requirement categories with additional
 * categories frequently needed for formal system requirements specifications.
 * Limiting requirement categorization to the SysML standard should use
 * functional, performance, interface, and design categories only, while other
 * popular standard's based specifications may use the other categories as well.
 * 
 * @author ModelerOne
 *
 */
public enum RequirementCategoryEnum
{
	/** System requirement */
	System,
	/** Subsystem requirement */
	Subsystem,
	/** Component requirement */
	Component,
	/** Environment requirement */
	Environment,
	/** Attribute requirement */
	Attribute,
	/** States and Transitions requirement */
	StatesTransitions,
	/** Capability requirement */
	Capability,
	/** Functional requirement */
	Functional,
	/** Performance requirement */
	Performance,
	/** Interface requirement */
	Interface,
	/** Internal Interface requirement */
	InterfaceInternal,
	/** Physical requirement */
	Physical,
	/** Design requirement */
	Design,
	/** Availability requirement */
	Availability,
	/** Adaptability requirement */
	Adaptability,
	/** Security requirement */
	Security,
	/** Safety requirement */
	Safety,
	/** Resource requirement */
	Resource,
	/** User requirement */
	User,
	/** Training requirement */
	Training,
	/** Verification requirement */
	Verification,
	/** Effectiveness requirement */
	Effectiveness,
	/** Suitability requirement */
	Suitability,
	/** Personnel requirement */
	Personnel,
	/** Packaging requirement */
	Packaging,
	/** Constraint requirement */
	Constraint,
	/** Cost requirement */
	Cost;

	/**
	 * Returns the requirement category enumeration value that maps with the
	 * specified annotation name
	 * 
	 * @param annotationName string name of a requirement annotation, e.g.
	 *                       RequirementSystem, RequirementComponent,
	 *                       RequirementFunctional, etc.
	 * @return the {@code RequirementCategoryEnum} value associated with the
	 *         annotation name
	 */
	public static Optional<RequirementCategoryEnum> valueOfAnnotationName(String annotationName)
	{
		Optional<RequirementCategoryEnum> result = Optional.empty();
		final String requirementPrefix = "Requirement";
		if (annotationName.contains(requirementPrefix))
		{
			String categoryName = annotationName.replace(requirementPrefix, "");
			RequirementCategoryEnum categoryEnum = RequirementCategoryEnum.valueOf(categoryName);
			if (categoryEnum != null)
				result = Optional.of(categoryEnum);
		}
		return result;
	}
}
