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
package sysmlinjava.requirements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;
import java.util.StringJoiner;
import java.util.logging.Logger;

import sysmlinjava.annotations.SysMLComment;
import sysmlinjava.annotations.SysMLDocumentation;
import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.annotations.SysMLTextualRepresentation;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.annotations.Comment;
import sysmlinjava.javaannotations.annotations.Documentation;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.annotations.TextualRepresentation;
import sysmlinjava.javaannotations.constraint.ConstraintFunction;
import sysmlinjava.javaannotations.constraint.ConstraintText;
import sysmlinjava.javaannotations.dependencies.Refine;
import sysmlinjava.javaannotations.metadata.Causation;
import sysmlinjava.javaannotations.metadata.ElementFilter;
import sysmlinjava.javaannotations.metadata.Icon;
import sysmlinjava.javaannotations.metadata.Image;
import sysmlinjava.javaannotations.metadata.Issue;
import sysmlinjava.javaannotations.metadata.Rationale;
import sysmlinjava.javaannotations.metadata.Risk;
import sysmlinjava.javaannotations.metadata.StatusInfo;
import sysmlinjava.javaannotations.requirements.Concern;
import sysmlinjava.metadata.SysMLCausation;
import sysmlinjava.metadata.SysMLElementGroup;
import sysmlinjava.metadata.SysMLIcon;
import sysmlinjava.metadata.SysMLImage;
import sysmlinjava.metadata.SysMLIssue;
import sysmlinjava.metadata.SysMLRationale;
import sysmlinjava.metadata.SysMLRefinement;
import sysmlinjava.metadata.SysMLRisk;
import sysmlinjava.metadata.SysMLStatusInfo;

/**
 * SysMLinJava representation of a collection of SysML concerns. The collection
 * is declared as public static instances of {@code SysMLConcern}s that are
 * created/initialized in a static {@code createConcerns()} method. The concerns
 * are optionally collected into a list of concerns and optionally linked via
 * their composedOf dependencies.
 * <p>
 * Specializations/extensions of this class must declare each of the
 * {@code SysMLConcern}s in the set as a static (class level) field variable.
 * The type of the field variable must be {@code SysMLConcern}. The field should
 * include the &#64;{@code Concern} annotation.
 * <p>
 * The SysML concern contains a number of attributes, including the
 * stakeholders, concerns, actors, and links to supporting information. These
 * model elements can be shared amongst multiple concerns. As such, the
 * constraints, stakeholders, actors, and supporting information links can be
 * declared before the declarations of the concerns or can be declared in their
 * own collection classes. An example of how these shared elements are declared
 * and used in the concern declaration is as follows.
 * 
 * <pre>{@code
	public class DeltaSystemConcerns extends SysMLConcernsCollection
	{
		
			:
		&#64;Documentation
		public static String availabilityConstraintText = new SysMLDocumentation("System will be able to meet minimum availability");
		&#64;Documentation
		public static String costConstraintText = new SysMLDocumentation("System will be able to meet maximum cost");
			:
		&#64;ConstraintFunction
		public static SysMLRequirementConstraintFunction availabilityConstraintFunction = (SysMLRequirementConstraintFunction) (subject) ->
		{
			return subject.availability.isGreaterThanEqualTo(subject.minimumAvailability);
		};
		&#64;ConstraintFunction
		public static SysMLRequirementConstraintFunction costConstraintFunction = (SysMLRequirementConstraintFunction) (subject) ->
		{
			return subject.cost.isLessThanEqualTo(subject.maximumCost);
		};
			:
		&#64;Concern
		public static SysMLConcern availabilityConcern = new SysMLConcern(
		"availabilityConcern",
		"Realistic System Availability Concerns",
		Optional.of(availabilityConstraintFunction),
		availabilityConstraintText,
		Optional.of(DeltaSystem.class),
		List.of(<b>UserStakeholder.class, DeveloperStakeholder.class</b>),
		List.of(<b>SystemOperator.class, Platform.class</b>),
		List.of(),
		RequirementCategoryEnum.Availability,
		List.of(),
		List.of(System.class),
		List.of(SysMLVerificationMethodKind.Analysis),
		List.of(SystemAvailabilityAnalysis.class),
		List.of(DeltaSystemSupportingInfoLinksCollection.availabilitySpec));
		:
		&#64;Concern
		public static SysMLConcern costConcern = new SysMLConcern(
		"costConcern",
		"Realistic System Cost Concerns",
		Optional.of(costConstraintFunction),
		costConstraintText,
		Optional.of(DeltaSystem.class),
		List.of(<b>UserStakeholder.class, DeveloperStakeholder.class</b>),
		List.of(<b>SystemOperator.class, Platform.class</b>),
		List.of(),
		RequriementCategoryEnum.Cost,
		List.of(),
		List.of(System.class),
		List.of(SysMLVerificationMethodKind.Analysis),
		List.of(SystemCostAnalysis.class),
		List.of(DeltaSystemSupportingInfoLinksCollection.costSpec));
			:
	}}</pre>
 * <p>
 * The list of concerns is created by the {@code createConcernsList()} method.
 * Specializations of {@code SysMLConcernsCollection} should override/hide this
 * method to add each of the {@code SysMLConcern} instances to the list.
 * 
 * @author ModelerOne
 * @see sysmlinjava.requirements.SysMLConcern
 */
public abstract class SysMLConcernsCollection extends SysMLAnything
{

	/**
	 * Operation that should be overridden/hidden to set the value of the
	 * {@code composedOfs} variable of each of the declared concerns. This operation
	 * must be used to set the {@code composedOfConcerns} value in the
	 * {@code SysMLConcern}s due to the liklihood that the declarations of the
	 * {@code composedOf} concerns are not yet visible to the constructor code. An
	 * example follows:
	 *
	 * <pre>
		public class MyConcernsCollection extends SysMLConcernCollection
		{
			protected static void setComposedOfs
			{
					:
				availabilityConcern.composedOfs = List.of();
				costConcern.composedOfs = List.of(subsystemACostConcern, interfaceBCostConcern, ...);
					:
			}
				:
			static
			{
				createConcernsList();
				setComposedOfs();
			}
		}
	 * </pre>
	 */
	protected static void setComposedOfs()
	{
	}

	/**
	 * Static operation that should be overridden/hidden to invoke the
	 * {@code setComposedOfs} and {@code createConcernsList} methods.
	 *
	 * <pre>
		public class MyConcernsCollection extends SysMLConcernCollection
		{
				:
			static void setComposedOfs()
			{
				:
			}
	
			static
			{
				setComposedOfs();
			}
		}
	 * </pre>
	 */
	static
	{
		setComposedOfs();
	}

	/**
	 * Validates that inheriting concerns collection declares only recognized
	 * types. If used, this method should be invoked in the {@code static} block of
	 * the inheriting class with the inheriting class as the argument. An example
	 * follows:
	 * 
	 * <pre>
		public class MyConcernsCollection extends SysMLConcernsCollection
		{
				:
				:
	
			static void setComposedOfs()
			{
				:
			}
	
	
			static
			{
				validate(MyConcernsCollection.class);
				
				setComposedOfs();
			}
		}
	 * </pre>
	 */
	public static void validate(Class<? extends SysMLConcernsCollection> subClass)
	{
		Logger logger = Logger.getLogger(SysMLConcernsCollection.class.getSimpleName());

		final List<String> recognizedAnnoNames = List.of(Concern.class.getSimpleName(), ConstraintFunction.class.getSimpleName(), ConstraintText.class.getSimpleName(), Hyperlink.class.getSimpleName(), Comment.class.getSimpleName(), Documentation.class.getSimpleName(), Issue.class.getSimpleName(), Risk.class.getSimpleName(), Image.class.getSimpleName(), Icon.class.getSimpleName(), StatusInfo.class.getSimpleName(), Causation.class.getSimpleName(), Refine.class.getSimpleName(), Rationale.class.getSimpleName(), ElementFilter.class.getSimpleName(), TextualRepresentation.class.getSimpleName());
		final List<String> recognizedTypeNames = List.of(SysMLConcern.class.getSimpleName(), SysMLRequirementConstraintFunction.class.getSimpleName(), SysMLDocumentation.class.getSimpleName(), SysMLHyperlink.class.getSimpleName(), SysMLComment.class.getSimpleName(), SysMLDocumentation.class.getSimpleName(), SysMLIssue.class.getSimpleName(), SysMLRisk.class.getSimpleName(), SysMLImage.class.getSimpleName(), SysMLIcon.class.getSimpleName(), SysMLStatusInfo.class.getSimpleName(), SysMLCausation.class.getSimpleName(), SysMLRefinement.class.getSimpleName(), SysMLRationale.class.getSimpleName(), SysMLElementGroup.class.getSimpleName(), SysMLTextualRepresentation.class.getSimpleName());

		ListIterator<Field> fields = List.of(subClass.getDeclaredFields()).listIterator();
		while (fields.hasNext())
		{
			Field nextType = fields.next();
			if (!recognizedTypeNames.contains(nextType.getType().getSimpleName()))
			{
				StringJoiner joiner = new StringJoiner(", ");
				recognizedTypeNames.forEach(name -> joiner.add(name));
				logger.warning("unrecognized type for concerns collection: %s , i.e. not %s".formatted(nextType.getType().getSimpleName(), joiner.toString()));
			}
		}
		ListIterator<Annotation> annos = List.of(subClass.getAnnotations()).listIterator();
		while (annos.hasNext())
		{
			Annotation nextAnno = annos.next();
			if (!recognizedAnnoNames.contains(nextAnno.annotationType().getSimpleName()))
			{
				StringJoiner joiner = new StringJoiner(", @", "@", "");
				recognizedAnnoNames.forEach(name -> joiner.add(name));
				logger.warning("unrecognized annotation for concerns collection: %s , i.e. not %s".formatted(nextAnno.annotationType().getSimpleName(), joiner.toString()));
			}
		}
	}

	/**
	 * Name of method to set the {@code composedOfs} variables of the concern
	 * instances, used by SysMLinJava tools, typically not needed for modeling.
	 */
	public static final String setComposedOfsMethodName = "setComposedOfs";
}
