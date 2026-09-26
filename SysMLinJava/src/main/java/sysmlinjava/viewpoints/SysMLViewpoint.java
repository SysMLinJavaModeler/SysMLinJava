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
package sysmlinjava.viewpoints;

import java.util.List;
import java.util.Optional;

import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.constraint.SysMLConstraint;
import sysmlinjava.javaannotations.views.View;
import sysmlinjava.occurrences.SysMLOccurrence;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.parts.SysMLStakeholder;
import sysmlinjava.requirements.RequirementCategoryEnum;
import sysmlinjava.requirements.SysMLConcern;
import sysmlinjava.requirements.SysMLRequirement;
import sysmlinjava.requirements.SysMLRequirementConstraintFunction;
import sysmlinjava.requirements.SysMLVerificationMethodKind;
import sysmlinjava.verifications.SysMLVerificationCase;
import sysmlinjava.views.SysMLView;

/**
 * SysMLinJava's representation of the SysML viewpoint, which is essentially a
 * SysML requirement with a list of the types of SysML views that satisfy the
 * viewpoint/requirement. Instances of the {@code SysMLViewpoint} should be
 * declared in an extension of the {@code SysMLViewpointsCollection} class.
 * 
 * @author ModelerOne
 * @see sysmlinjava.viewpoints.SysMLViewpointsCollection
 */
public final class SysMLViewpoint extends SysMLRequirement
{
	/**
	 * List of types of views that satisfy this viewpoint
	 */
	@View
	public List<Class<? extends SysMLView>> satisfyingViews;

	/**
	 * Constructor for viewpoint as type of requirement with satisfying views.
	 * Arguments:
	 * 
	 * <pre>{@code
	 * String identifier,
	 * String title,
	 * Optional<RequirementConstraintFunction> constraintFunction,
	 * String constraintText,
	 * Optional<Class<? extends SysMLOccurrence>> subject,
	 * List<Class<? extends SysMLStakeholder>> stakeholders,
	 * List<Class<? extends SysMLPart>> actors,
	 * List<SysMLConstraint> assumed,
	 * RequirementCategoryEnum category,
	 * List<Class<? extends SysMLView>> satisfyingViews,
	 * List<SysMLVerificationMethodKind> verificationMethods,
	 * List<Class<? extends SysMLVerificationCase>> verifiedBy,
	 * List<SysMLHyperlink> supportingInformationLinks
	 * 
	 * }</pre>
	 * 
	 * @param identifier                 viewpoint's unique identifier
	 * @param title                      viewpoint's unique title
	 * @param constraintFunction         viewpoint specified as formal constraint
	 * @param constraintText             viewpoint specified as simple text-based constraint
	 * @param subject                    viewpoint's subject
	 * @param actors                     list of actors involved in this viewpoint,
	 *                                   if any
	 * @param stakeholders               list of stakeholders for this viewpoint, if
	 *                                   any
	 * @param assumed                    list of assumptions for this viewpoint
	 *                                   expressed as assertion constraints
	 * @param category                   viewpoint's category
	 * @param satisfyingViews            views that satisfy this viewpoint
	 * @param verificationMethods        viewpoint's verification methods
	 * @param verifiedBy                 test cases that verify this viewpoint
	 * @param supportingInformationLinks hyperlinks to information that supports
	 *                                   this viewpoint, e.g. link(s) to another
	 *                                   document(s) that further specify the
	 *                                   viewpoint.
	 */
	public SysMLViewpoint(String identifier, String title, String constraintText, Optional<SysMLRequirementConstraintFunction> constraintFunction, Optional<Class<? extends SysMLOccurrence>> subject, List<Class<? extends SysMLStakeholder>> stakeholders, List<SysMLConcern> concerns, List<Class<? extends SysMLPart>> actors, List<SysMLConstraint> assumed, RequirementCategoryEnum category, List<Class<? extends SysMLView>> satisfyingViews, List<SysMLVerificationMethodKind> verificationMethods, List<Class<? extends SysMLVerificationCase>> verifiedBy, List<SysMLHyperlink> supportingInformationLinks)
	{
		super(identifier, title, constraintText, constraintFunction, subject, stakeholders, concerns, actors, false, assumed, category, List.of(), verificationMethods, verifiedBy,
		supportingInformationLinks);
		this.satisfyingViews = satisfyingViews;
	}
}
