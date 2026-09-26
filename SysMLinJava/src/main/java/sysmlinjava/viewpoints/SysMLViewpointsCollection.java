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
import sysmlinjava.constraint.SysMLConstraint;
import sysmlinjava.javaannotations.annotations.Comment;
import sysmlinjava.javaannotations.annotations.Documentation;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.annotations.TextualRepresentation;
import sysmlinjava.javaannotations.constraint.Constraint;
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
import sysmlinjava.javaannotations.viewpoints.Viewpoint;
import sysmlinjava.metadata.SysMLCausation;
import sysmlinjava.metadata.SysMLElementGroup;
import sysmlinjava.metadata.SysMLIcon;
import sysmlinjava.metadata.SysMLImage;
import sysmlinjava.metadata.SysMLIssue;
import sysmlinjava.metadata.SysMLRationale;
import sysmlinjava.metadata.SysMLRefinement;
import sysmlinjava.metadata.SysMLRisk;
import sysmlinjava.metadata.SysMLStatusInfo;
import sysmlinjava.requirements.SysMLRequirementConstraintFunction;

/**
 * SysMLinJava representation of a collection of SysML viewpoints. The
 * collection is declared as public static (class-level) instances of
 * {@code SysMLViewpoint}s. The instances are created/initialized in
 * overrides/hides of the {@code createViewpoints()} method. The viewpoints are
 * collected into a list of viewpoints and linked via their decomposition
 * dependencies. The list and dependencies are created in overrides/hides of the
 * {@code createViewpointsList()} and {@code setComposedOfs()} methods,
 * respectively.
 * <p>
 * The type of the field variable for each viewpoint instance must be
 * {@code SysMLViewpoint}. The field should include the &#64;{@code Viewpoint}
 * annotation.
 * <p>
 * The SysML viewpoint contains a number of attributes, including the
 * stakeholders, concerns, actors, and links to supporting information. These
 * model elements can be shared amongst multiple viewpoints. As such, the
 * stakeholders, actors, and supporting information links must be declared
 * before or outside of the declarations of the viewpoints. An example of how
 * these shared elements are declared and used in the viewpoint declaration is
 * as follows.
 * 
 * <pre>{@code
	public class DeltaSystemViewpoints extends SysMLViewpointsCollection
	{
			:
		&#64;Documentation
		public static SysMLDocumentation rootDoc = new SysMLDocumentation("HF Datalink System Viewpoint shall provide view of structure and behavior specified as follows"),
		&#64;Documentation
		public static SysMLDocumentation c2ViewsDoc = new SysMLDocumentation("HF Datalink System Viewpoint shall include views of the Command and Control Subsystem"),
		&#64;Documentation
		public static SysMLDocumentation deployedViewsDoc = new SysMLDocumentation("HF Datalink System Viewpoint shall include views of the Deployed Subsystem"),
			:

			:
		&#64;Viewpoint
		public static SysMLViewpoint idroot = new SysMLViewpoint(
			"root",
			"HF Datalink System Viewpoint",
			rootDoc,
			Optional.empty(),
			Optional.of(HFLinkDomain.class),
			List.of(UserStakeholder.class, DeveloperStakeholder.class),
			List.of(SystemConcerns.tbdSubsystem),
			List.of(GPS.class),
			false,
			List.of(),
			RequirementCategoryEnum.System,
			List.of(),
			List.of(HFLinkDomain.class),
			List.of(SysMLVerificationMethodKind.Analysis),
			List.of(HFDataLinkDomainTestCase.class),
			List.of(ProgramSpecs.c2SubsystemSpec, ProgramSpecs.deployedSubsystemSpec));
		&#64;Viewpoint
		public static SysMLViewpoint c2Viewpoint = new SysMLViewpoint(
			"1",
			"Command and Control Subsystem Viewpoint",
			c2Doc,
			Optional.empty(),
			Optional.of(HFLinkDomain.class),
			List.of(DeveloperStakeholder.class),
			List.of(SystemConcerns.tbdSubsystem),
			List.of(),
			false,
			List.of(subsystemAvailable),
			RequirementCategoryEnum.Subsystem,
			List.of(),
			List.of(HFLinkDomain.class),
			List.of(SysMLVerificationMethodKind.Inspection),
			List.of(HFDataLinkDomainTestCase.class),
			List.of(ProgramSpecs.c2SubsystemSpec));
		&#64;Viewpoint
		public static SysMLViewpoint deploedViewpoint = new SysMLViewpoint(
			"2",
			"Deployed Subsystem Viewpoint",
			deployedDoc,
			Optional.empty(),
			Optional.of(HFLinkDomain.class),
			List.of(DeveloperStakeholder.class),
			List.of(SystemConcerns.tbdSubsystem),
			List.of(),
			false,
			List.of(subsystemAvailable),
			RequirementCategoryEnum.Subsystem,
			List.of(),
			List.of(HFLinkDomain.class),
			List.of(SysMLVerificationMethodKind.Inspection),
			List.of(HFDataLinkDomainTestCase.class),
			List.of(ProgramSpecs.deployedSubsystemSpec));
				:

		protected static void setComposedOfs()
		{
			idroot.composedOf = List.of(id1, id2, id3);
			c2Viewpoint.composedOf = List.of(id1_1, id1_2);
			deployedViewpoint.composedOf = List.of(id2_1, id2_2, id2_3);
				:
		}
	}}</pre>
 * <p>
 * As shown, the decomposition dependencies are created by the
 * {@code setComposedOfs()} method. Specializations of
 * {@code SysMLViewpointsCollection} should override/hide this method to create
 * the dependencies.
 * 
 * @author ModelerOne
 */
public abstract class SysMLViewpointsCollection extends SysMLAnything
{
	/**
	 * Operation that should be overridden/hidden to set the value of the
	 * {@code composedOfs} variable of each of the declared viewpoints. This
	 * operation may be used in lieu of setting the {@code composedOf} value in the
	 * {@code SysMLViewpoint}'s constructor or {@code createComposedOf()} method
	 * when declarations of the composed-of requirements are not yet visible in
	 * code. An example follows:
	 *
	 * <pre>
		public class MyViewpointtsCollection extends SysMLViewpointsCollection
		{
				:
				:

			protected static void setComposedOfs
			{
					:
				vwpt3_1.composedOfs = List.of(vwpt3_1_1, vwpt3_1_2, vwpt3_1_3);
					:
			}
		}
	 * </pre>
	 */
	public static void setComposedOfs()
	{
	}

	/**
	 * Static block of operations that should be overridden/hidden to invoke the
	 * operations that create the members of the collection. An example is as
	 * follows:
	 *
	 * <pre>
		public class MyViewpointsCollection extends SysMLViewpointsCollection
		{
				:
				:

			static void setComposedOfs()
			{
				:
			}
				:
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
	 * Validates that inheriting viewpoints collection declares only recognized
	 * types. If used, this method should be invoked in the {@code static} block of
	 * the inheriting class with the inheriting class as the argument. An example
	 * follows:
	 * 
	 * <pre>
		public class MyViewpointsCollection extends SysMLRequirementCollection
		{
				:
				:
	
			static void setComposedOfs()
			{
				:
			}
	
			static
			{
				validate(MyViewpointsCollection.class);
				
				setComposedOfs();
			}
		}
	 * </pre>
	 */
	public static void validate(Class<? extends SysMLViewpointsCollection> subClass)
	{
		Logger logger = Logger.getLogger(SysMLViewpointsCollection.class.getSimpleName());

		final List<String> recognizedAnnoNames = List.of(Viewpoint.class.getSimpleName(), ConstraintFunction.class.getSimpleName(), ConstraintText.class.getSimpleName(), Constraint.class.getSimpleName(), Hyperlink.class.getSimpleName(), Comment.class.getSimpleName(), Documentation.class.getSimpleName(), Issue.class.getSimpleName(), Risk.class.getSimpleName(), Image.class.getSimpleName(), Icon.class.getSimpleName(), StatusInfo.class.getSimpleName(), Causation.class.getSimpleName(), Refine.class.getSimpleName(), Rationale.class.getSimpleName(), ElementFilter.class.getSimpleName(), TextualRepresentation.class.getSimpleName());
		final List<String> recognizedTypeNames = List.of(SysMLViewpoint.class.getSimpleName(), SysMLRequirementConstraintFunction.class.getSimpleName(), SysMLDocumentation.class.getSimpleName(), SysMLConstraint.class.getSimpleName(), SysMLHyperlink.class.getSimpleName(), SysMLComment.class.getSimpleName(), SysMLDocumentation.class.getSimpleName(), SysMLIssue.class.getSimpleName(), SysMLRisk.class.getSimpleName(), SysMLImage.class.getSimpleName(), SysMLIcon.class.getSimpleName(), SysMLStatusInfo.class.getSimpleName(), SysMLCausation.class.getSimpleName(), SysMLRefinement.class.getSimpleName(), SysMLRationale.class.getSimpleName(), SysMLElementGroup.class.getSimpleName(), SysMLTextualRepresentation.class.getSimpleName());

		ListIterator<Field> fields = List.of(subClass.getDeclaredFields()).listIterator();
		while (fields.hasNext())
		{
			Field nextType = fields.next();
			if (!recognizedTypeNames.contains(nextType.getType().getSimpleName()))
			{
				StringJoiner joiner = new StringJoiner(", ");
				recognizedTypeNames.forEach(name -> joiner.add(name));
				logger.warning("unrecognized type for viewpoints collection: %s , i.e. not %s".formatted(nextType.getType().getSimpleName(), joiner.toString()));
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
				logger.warning("unrecognized annotation for viewpoints collection: %s , i.e. not %s".formatted(nextAnno.annotationType().getSimpleName(), joiner.toString()));
			}
		}
	}

	/**
	 * Name of method to set the {@code composedOfs} variables of the viewpoint
	 * instances since they cannot be set in the constructor before they are
	 * created/initialized.
	 */
	public static final String setComposedOfsMethodName = "setComposedOfs";
}
