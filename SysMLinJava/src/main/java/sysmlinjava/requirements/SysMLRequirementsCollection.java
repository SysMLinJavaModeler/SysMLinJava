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
import sysmlinjava.javaannotations.requirements.Requirement;
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
 * SysMLinJava representation of a collection of SysML requirements. The
 * collection is declared as public static (class-level) instances of
 * {@code SysMLRequirement}s that are created/initialized in their variable
 * declarations. The requirements are optionally collected into a list of
 * requirements and optionally linked via their decomposition dependencies. The
 * list and dependencies are created in overrides of the
 * {@code createRequirementsList()} and {@code setComposedOfs()} methods,
 * respectively.
 * <p>
 * The type of the field variable must be {@code SysMLRequirement} or extension
 * thereof. The field should be annotated with the &#64;{@code Requirement}
 * annotation.
 * <p>
 * The SysML requirement contains a number of attributes, including the
 * stakeholders, concerns, actors, and links to supporting information. These
 * model elements can be shared amongst multiple requirements. As such, the
 * stakeholders, concerns, actors, and supporting information links must be
 * declared outside of the declarations of the requirements. An example of how
 * these shared elements are declared and used in the requirement declaration is
 * as follows.
 * 
 * <pre>
 * public class HFDataLinkedSystemRequirements extends SysMLRequirementsCollection
 * {
 * 	&#64;ConstraintFunction
 * 	public static SysMLRequirementConstraintFunction idrootFunction = (SysMLRequirementConstraintFunction) (subject) ->
 * 	{
 * 		double x = 1, y = 2.5, m = 0.5, b = 2;
 * 		boolean result = y == m * x + b;
 * 		return result;
 * 	};
 * 	&#64;ConstraintFunction
 * 	public static SysMLRequirementConstraintFunction id1Function = (SysMLRequirementConstraintFunction) (subject) ->
 * 	{
 * 		double x = 1, y = 2.5, m = 0.5, b = 2;
 * 		boolean result = y == m * x + b;
 * 		return result;
 * 	};
 * 	&#64;ConstraintFunction
 * 	public static SysMLRequirementConstraintFunction id2Function = (SysMLRequirementConstraintFunction) (subject) ->
 * 	{
 * 		return true;
 * 	};
 * 	&#64;ConstraintFunction
 * 	public static SysMLRequirementConstraintFunction id3Function = (SysMLRequirementConstraintFunction) (subject) ->
 * 	{
 * 		return true;
 * 	};
 * 
 * 	&#64;ConstraintText
 * 	public static SysMLDocumentation subsystemAvailableForInclusion = new SysMLDocumentation("Specified subsystem is available in time for inclusion");
 * 
 * 	&#64;Constraint
 * 	public static SysMLConstraint subsystemAvailable = new SysMLConstraint(Optional.empty(), subsystemAvailableForInclusion, "Availability", 0L);
 * 
 * 	&#64;ConstraintText
 * 	public static SysMLDocumentation specifiedStructureBehavior = new SysMLDocumentation("HF Datalink System shall exhibit structure and behavior specified as follows");
 * 
 * 	&#64;Requirement
 * 	public static SysMLRequirement idroot = new SysMLRequirement("root", "HF Datalink System Requirements", Optional.of(idrootFunction), specifiedStructureBehavior,
 * 	Optional.of(HFLinkDomain.class), List.of(OperatorStakeholder.class, DeveloperStakeholder.class), List.of(HFDataLinkedSystemConcerns.tbdSubsystem), List.of(GPS.class), false,
 * 	List.of(), RequirementCategoryEnum.System, List.of(HFLinkDomain.class), List.of(SysMLVerificationMethodKind.Analysis), List.of(HFDataLinkDomainTestCase.class),
 * 	List.of(HFDataLinkedSystemSupportingInformationLinks.c2SubsystemSpec, HFDataLinkedSystemSupportingInformationLinks.deployedSubsystemSpec));
 * 
 * 	&#64;ConstraintText
 * 	public static SysMLDocumentation utilizeC2System = new SysMLDocumentation("HF Datalink System shall include/utilize the Command and Control Subsystem");
 * 
 * 	&#64;Requirement
 * 	public static SysMLRequirement id1 = new SysMLRequirement("1", "Command and Control Subsystem", Optional.of(id1Function), utilizeC2System, Optional.of(HFLinkDomain.class),
 * 	List.of(DeveloperStakeholder.class), List.of(HFDataLinkedSystemConcerns.tbdSubsystem), List.of(), false, List.of(subsystemAvailable), RequirementCategoryEnum.Subsystem,
 * 	List.of(HFLinkDomain.class), List.of(SysMLVerificationMethodKind.Inspection), List.of(HFDataLinkDomainTestCase.class),
 * 	List.of(HFDataLinkedSystemSupportingInformationLinks.c2SubsystemSpec));
 * 
 * 	&#64;ConstraintText
 * 	public static SysMLDocumentation utilizeDeployedSystem = new SysMLDocumentation("HF Datalink System shall include/utilize the Deployed Subsystem");
 * 
 * 	&#64;Requirement
 * 	public static SysMLRequirement id2 = new SysMLRequirement("2", "Deployed Subsystem", Optional.of(id2Function), utilizeDeployedSystem, Optional.of(HFLinkDomain.class),
 * 	List.of(DeveloperStakeholder.class), List.of(HFDataLinkedSystemConcerns.tbdSubsystem), List.of(), false, List.of(subsystemAvailable), RequirementCategoryEnum.Subsystem,
 * 	List.of(HFLinkDomain.class), List.of(SysMLVerificationMethodKind.Inspection), List.of(HFDataLinkDomainTestCase.class),
 * 	List.of(HFDataLinkedSystemSupportingInformationLinks.deployedSubsystemSpec));
 * 
 * 	&#64;ConstraintText
 * 	public static SysMLDocumentation specifiedCapabilities = new SysMLDocumentation("HF Datalink System shall provide capabilities specified as follows");
 * 
 * 	&#64;Requirement
 * 	public static SysMLRequirement id3 = new SysMLRequirement("3", "System Capabilities", Optional.of(id3Function), specifiedCapabilities, Optional.of(HFLinkDomain.class),
 * 	List.of(OperatorStakeholder.class, DeveloperStakeholder.class), List.of(), List.of(GPS.class), false, List.of(), RequirementCategoryEnum.Capability,
 * 	List.of(HFLinkDomain.class), List.of(SysMLVerificationMethodKind.Analysis, SysMLVerificationMethodKind.Test), List.of(HFDataLinkDomainTestCase.class), List.of());
 * 
 * 	&#64;ConstraintText
 * 	public static SysMLDocumentation waitTimeAnalysis = new SysMLDocumentation("System shall include analysis of frequency distribution of TDMA protocol's wait times");
 * 
 * 	&#64;Requirement
 * 	public static SysMLRequirement waitTimeFrequencyAnalysis = new SysMLRequirement("1", "TDMA Wait-time frequency", waitTimeAnalysis, RequirementCategoryEnum.Performance,
 * 	List.of(SysMLVerificationMethodKind.Analysis), List.of());
 * 
 * 	static
 * 	{
 * 		createRequirementsList();
 * 		setComposedOfs();
 * 	}
 * 
 * 	protected static void createRequirementsList()
 * 	{
 * 		requirementsList = List.of(idroot, id1, id2, id3, waitTimeFrequencyAnalysis);
 * 	}
 * 
 * 	protected static void setComposedOfs()
 * 	{
 * 		idroot.composedOf = List.of(id1, id2, id3, waitTimeFrequencyAnalysis);
 * 		id1.composedOf = List.of(id1_1, id1_2);
 * 		id2.composedOf = List.of(id2_1, id2_2);
 * 		id3.composedOf = List.of(id3_1, id3_2);
 * 	}
 * }
 * </pre>
 * <p>
 * As shown, the decomposition ({@code composedOf}) dependencies are created by
 * the {@code setComposedOfs()} method.
 * <p>
 * Also shown, the list of requirements is created by the
 * {@code createRequirementsList()} method. Specializations of
 * {@code SysMLRequirementsCollection} should override/hide this static method
 * to add each of the {@code SysMLRequirement} instances to the list.
 * <p>
 * Note that tools are commercially available that can automatically generate
 * the {@code SysMLRequirements}. Requirements can be auto-generated from a
 * SysMLinJava model and/or imported into the tool from other requirement
 * specification formats such as CSV, SQL, etc. These auto-generated or imported
 * requirements can then be formed into a specialization of the
 * {@code SysMLRequriementsCollection}. See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 * @see sysmlinjava.requirements.SysMLRequirement
 */
public abstract class SysMLRequirementsCollection extends SysMLAnything
{
	/**
	 * Operation that may be overridden/hidden to set the value of the
	 * {@code composedOfs} variable of each of the declared requirements. This
	 * operation must be used to set the {@code composedOfRequirements} value in the
	 * {@code SysMLRequirement}s due to the liklihood that the declarations of the
	 * composedOf requirements are not yet visible to the constructor. code. An
	 * example follows:
	 *
	 * <pre>
		public class MyRequirementsCollection extends SysMLRequirementCollection
		{
				:
			&#64;Requirement
			public static SysMLRequirement req3_1 = new SysMLRequirement(...);
			&#64;Requirement
			public static SysMLRequirement req3_1_1 = new SysMLRequirement(...);
			&#64;Requirement
			public static SysMLRequirement req3_1_2 = new SysMLRequirement(...);
			&#64;Requirement
			public static SysMLRequirement req3_1_3 = new SysMLRequirement(...);
				:
	
			protected static void setComposedOfs
			{
					:
				req3_1.composedOfs = List.of(req3_1_1, req3_1_2, req3_1_3);
					:
			}
				:
			static
			{
				setComposedOfs();
				setDeriveds();
				setDerivedFrom();
			}
		}
	 * </pre>
	 */
	protected static void setComposedOfs()
	{
	}

	/**
	 * Operation that may be overridden/hidden to set the value of the
	 * {@code deriveds} variable of each of the declared requirements. This
	 * operation must be used to set the {@code deriveds} value in the
	 * {@code SysMLRequirement}s due to the liklihood that the declarations of the
	 * derived requirements are not yet visible to the constructor. code. An example
	 * follows:
	 *
	 * <pre>
		public class MyRequirementsCollection extends SysMLRequirementCollection
		{
				:
			&#64;Requirement
			public static SysMLRequirement req3_1 = new SysMLRequirement(...);
			&#64;Requirement
			public static SysMLRequirement req4_5 = new SysMLRequirement(...);
			&#64;Requirement
			public static SysMLRequirement req5_1 = new SysMLRequirement(...);
				:
	
			protected static void setDeriveds
			{
					:
				req3_1.deriveds = List.of(req4-5, req5-1);
					:
			}
				:
			static
			{
				setComposedOfs();
				setDeriveds();
				setDerivedFroms();
			}
		}
	 * </pre>
	 */
	protected static void setDeriveds()
	{
	}

	/**
	 * Static operation that may be overridden/hidden to set the value of the
	 * {@code derivedFrom} variable of each of the declared requirements. This
	 * operation may be used in lieu of setting the {@code derivedFrom} value in the
	 * {@code SysMLRequirement}'s constructor when declarations of the derived
	 * requirements are not yet visible in code. An example follows:
	 *
	 * <pre>
		public class MyRequirementsCollection extends SysMLRequirementCollection
		{
				:
			&#64;Requirement
			public static SysMLRequirement req2 = new SysMLRequirement(...);
			&#64;Requirement
			public static SysMLRequirement req2_1 = new SysMLRequirement(...);
			&#64;Requirement
			public static SysMLRequirement req3 = new SysMLRequirement(...);
			&#64;Requirement
			public static SysMLRequirement req3_1 = new SysMLRequirement(...);
				:
			
			protected static void setDerivedFroms()
			{
					:
				req2_1.derivedFrom = Optional.of(req3_1);
					:
			}
				:
			static
			{
				setComposedOfs();
				setDeriveds();
				setDerivedFroms();
			}
		}
	 * </pre>
	 */
	protected static void setDerivedFroms()
	{
	}

	/**
	 * Static operation that should be overridden/hidden to invoke the operation to
	 * set the {@code setComposedOfs} method:
	 *
	 * <pre>
		public class MyRequirementsCollection extends SysMLRequirementCollection
		{
				:
				:
	
			static void setComposedOfs()
			{
				:
			}
	
			static void setDeriveds()
			{
				:
			}
	
			static void setDerivedFroms()
			{
				:
			}
	
			static
			{
				setComposedOfs();
				setDeriveds();
				setDerivedFroms();
			}
		}
	 * </pre>
	 */
	static
	{
		setComposedOfs();
		setDeriveds();
		setDerivedFroms();
	}

	/**
	 * Validates that inheriting requirements collection declares only recognized
	 * types. If used, this method should be invoked in the {@code static} block of
	 * the inheriting class with the inheriting class as the argument. An example
	 * follows:
	 * 
	 * <pre>
		public class MyRequirementsCollection extends SysMLRequirementCollection
		{
				:
				:
	
			static void setComposedOfs()
			{
				:
			}
	
			static void setDeriveds()
			{
				:
			}
	
			static void setDerivedFroms()
			{
				:
			}
	
			static
			{
				validate(MyRequirementsCollection.class);
				
				setComposedOfs();
				setDeriveds();
				setDerivedFroms();
			}
		}
	 * </pre>
	 */
	public static void validate(Class<? extends SysMLRequirementsCollection> subClass)
	{
		Logger logger = Logger.getLogger(SysMLRequirementsCollection.class.getSimpleName());

		final List<String> recognizedAnnoNames = List.of(Requirement.class.getSimpleName(), ConstraintFunction.class.getSimpleName(), ConstraintText.class.getSimpleName(), Constraint.class.getSimpleName(), Hyperlink.class.getSimpleName(), Comment.class.getSimpleName(), Documentation.class.getSimpleName(), Issue.class.getSimpleName(), Risk.class.getSimpleName(), Image.class.getSimpleName(), Icon.class.getSimpleName(), StatusInfo.class.getSimpleName(), Causation.class.getSimpleName(), Refine.class.getSimpleName(), Rationale.class.getSimpleName(), ElementFilter.class.getSimpleName(), TextualRepresentation.class.getSimpleName());
		final List<String> recognizedTypeNames = List.of(SysMLRequirement.class.getSimpleName(), SysMLRequirementConstraintFunction.class.getSimpleName(), SysMLDocumentation.class.getSimpleName(), SysMLConstraint.class.getSimpleName(), SysMLHyperlink.class.getSimpleName(), SysMLComment.class.getSimpleName(), SysMLDocumentation.class.getSimpleName(), SysMLIssue.class.getSimpleName(), SysMLRisk.class.getSimpleName(), SysMLImage.class.getSimpleName(), SysMLIcon.class.getSimpleName(), SysMLStatusInfo.class.getSimpleName(), SysMLCausation.class.getSimpleName(), SysMLRefinement.class.getSimpleName(), SysMLRationale.class.getSimpleName(), SysMLElementGroup.class.getSimpleName(), SysMLTextualRepresentation.class.getSimpleName());

		ListIterator<Field> fields = List.of(subClass.getDeclaredFields()).listIterator();
		while (fields.hasNext())
		{
			Field nextType = fields.next();
			if (!recognizedTypeNames.contains(nextType.getType().getSimpleName()))
			{
				StringJoiner joiner = new StringJoiner(", ");
				recognizedTypeNames.forEach(name -> joiner.add(name));
				logger.warning("unrecognized type for requirements collection: %s , i.e. not %s".formatted(nextType.getType().getSimpleName(), joiner.toString()));
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
				logger.warning("unrecognized annotation for requirements collection: %s , i.e. not %s".formatted(nextAnno.annotationType().getSimpleName(), joiner.toString()));
			}
		}
	}

	/**
	 * Name of method to set the {@code composedOfs} variables of the requirement
	 * instances since they cannot be set in the constructor before they are
	 * created/initialized.
	 */
	public static final String setComposedOfsMethodName = "setComposedOfs";
	/**
	 * Name of method to set the {@code deriveds} variables of the requirement
	 * instances since they cannot be set in the constructor before they are
	 * created/initialized.
	 */
	public static final String setDerivedsMethodName = "setDeriveds";
	/**
	 * Name of method to set the {@code derivedFrom} variables of the requirement
	 * instances since they cannot be set in the constructor before they are
	 * created/initialized.
	 */
	public static final String setDerivedFromsMethodName = "setDerivedFroms";
}
