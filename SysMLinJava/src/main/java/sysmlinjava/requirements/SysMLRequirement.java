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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.logging.Logger;

import sysmlinjava.annotations.SysMLDocumentation;
import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.constraint.SysMLConstraint;
import sysmlinjava.javaannotations.annotations.Hyperlink;
import sysmlinjava.javaannotations.constraint.Constraint;
import sysmlinjava.javaannotations.occurrences.Subject;
import sysmlinjava.javaannotations.parts.Actor;
import sysmlinjava.javaannotations.parts.Stakeholder;
import sysmlinjava.javaannotations.requirements.AllocatedTo;
import sysmlinjava.javaannotations.requirements.Category;
import sysmlinjava.javaannotations.requirements.ComposedOf;
import sysmlinjava.javaannotations.requirements.Concern;
import sysmlinjava.javaannotations.requirements.Derived;
import sysmlinjava.javaannotations.requirements.DerivedFrom;
import sysmlinjava.javaannotations.requirements.Identifier;
import sysmlinjava.javaannotations.requirements.IsAssumption;
import sysmlinjava.javaannotations.requirements.Title;
import sysmlinjava.javaannotations.requirements.VerificationMethod;
import sysmlinjava.javaannotations.requirements.VerifiedBy;
import sysmlinjava.occurrences.SysMLOccurrence;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.parts.SysMLStakeholder;
import sysmlinjava.verifications.SysMLVerificationCase;

/**
 * SysMLinJava representation of the SysML requirement. This
 * {@code SysMLRequirement} is an extension of the {@code SysMLConstraint}, so
 * the actual requirement specification is contained in the features of the
 * {@code SysMLConstraint}, i.e. in the {@code function} (formal constraint as
 * code) or {@code text} (informal constraint as text) variables.
 * <p>
 * Requirements are created/specified as instances of the
 * {@code SysMLRequirement} in an extension of the
 * {@code SysMLRequirementsCollection} class. These requirement instances can
 * then be used in references to requirements in parts, ports, items, etc.
 * 
 * @author ModelerOne
 * @see sysmlinjava.requirements.SysMLRequirementsCollection
 * @see sysmlinjava.parts.SysMLPart#createRequirements
 */
public class SysMLRequirement extends SysMLConstraint
{
	static Logger logger = Logger.getLogger(SysMLRequirement.class.getSimpleName());

	/**
	 * Unique identifier of the requirement, i.e. numerical ID such as "2.1.4.2"
	 */
	@Identifier
	public String identifier;

	/**
	 * Unique title of the requirement
	 */
	@Title
	public String title;

	/**
	 * Subject of the requirement, i.e. the name of any class that is of the
	 * type/extension of the {@code SysMLPart}, {@code SysMLPort}, or
	 * {@code SysMLAction}.
	 */
	@Subject
	public Optional<Class<? extends SysMLOccurrence>> subject;

	/**
	 * List of the classes of the stakeholders for this requirement. Each entry in
	 * the list is a class that is an extension of the {@code SysMLStakeholder}
	 */
	@Stakeholder
	public List<Class<? extends SysMLStakeholder>> stakeholders;

	/**
	 * List of the object names of concerns addressed by this requirement. Each
	 * entry in the list is the name of an instance of a {@code SysMLConcern}
	 * declared in a {@code SysMLConcernsCollection}
	 */
	@Concern
	public List<SysMLConcern> concerns;

	/**
	 * List of classes of the actors that interact with the subject of this
	 * requirement. Each entry in the list is the name of a class that is an
	 * extension of the {@code SysMLPart}
	 */
	@Actor
	public List<Class<? extends SysMLPart>> actors;

	/**
	 * True if this is an assumption requirement, false otherwise
	 */
	@IsAssumption
	public Boolean isAssumption;
	/**
	 * List of assumption constraints that must be true for the requirement to be
	 * applicable. Each entry in the list is the name of a {@code SysMLConstraint}
	 * instance declared in the same scope of the requirement definition.
	 */
	@Constraint
	public List<SysMLConstraint> assumed;

	/**
	 * Category of the requirement
	 */
	@Category
	public RequirementCategoryEnum category;
	/**
	 * List of object names of the requirements that this (composite) requirement is
	 * composed of. Each entry in the list is the name of a {@code SysMLRequirement}
	 * instance in the scope of the requirement definition.
	 */
	@ComposedOf
	public List<SysMLRequirement> composedOf;

	/**
	 * List of classes of type {@code SysMLItem}, {@code SysMLPart},
	 * {@code SysMLPort}, or {@code SysMLAction} that this requirement is allocated
	 * to
	 */
	@AllocatedTo
	public List<Class<? extends SysMLOccurrence>> allocatedTo;

	/**
	 * List of methods of verification of the requirement
	 */
	@VerificationMethod
	public List<SysMLVerificationMethodKind> verificationMethods;

	/**
	 * List of classes of verification cases that verify the requirement
	 */
	@VerifiedBy
	public List<Class<? extends SysMLVerificationCase>> verifiedBy;

	/**
	 * URI's to supporting information for this requirement
	 */
	@Hyperlink
	public List<SysMLHyperlink> supportingInformationLinks;

	/**
	 * Requirements derived from this requirement
	 */
	@Derived
	public List<SysMLRequirement> deriveds;

	/**
	 * Optional original requirement from which this requirement is derived
	 */
	@DerivedFrom
	public Optional<SysMLRequirement> derivedFrom;

	/**
	 * Constructor for constraint-defined requirement. Has no specific occurrence
	 * so, if needed, occurrence must be set via explicit assignment after
	 * constructor. Arguments: Arguments:
	 * 
	 * @param identifier                 requirement's unique identifier
	 * @param title                      requirement's unique title
	 * @param constraintText             requirement specified with simple text
	 *                                   documentation as informal non-executable
	 *                                   constraint
	 * @param constraintFunction         requirement specified as formal executable
	 *                                   constraint as lambda function
	 * @param subject                    optional class of subject of this
	 *                                   requirement
	 * @param actors                     list of classes of actors involved in this
	 *                                   requirement, if any
	 * @param stakeholders               list of classes of stakeholders for this
	 *                                   requirement, if any
	 * @param concerns                   list of names of instances of concerns
	 *                                   addressed by this requirement, if any
	 * @param assumed                    list of names of instances of
	 *                                   assumptions/constraints for this
	 *                                   requirement, if any
	 * @param category                   requirement's category
	 * @param verificationMethods        List of verification methods used for
	 *                                   verification of this requirement
	 * @param allocatedTo                list of class of parts, ports, actions,
	 *                                   items to which this requirement is
	 *                                   allocated
	 * @param verifiedBy                 list of class names of verification cases
	 *                                   that verify this requirement was satisfied
	 * @param isAssumption               whether this requirement is an assumption
	 *                                   for another requirement
	 * @param supportingInformationLinks list of names of instances of
	 *                                   {@code SysMLHyperlink}s to information that
	 *                                   supports this requirement, e.g. link(s) to
	 *                                   another document(s) that specify the
	 *                                   requirements that compose this requirement.
	 */
	public SysMLRequirement(String identifier, String title, String constraintText, Optional<SysMLRequirementConstraintFunction> constraintFunction, Optional<Class<? extends SysMLOccurrence>> subject, List<Class<? extends SysMLStakeholder>> stakeholders, List<SysMLConcern> concerns, List<Class<? extends SysMLPart>> actors, Boolean isAssumption, List<SysMLConstraint> assumed, RequirementCategoryEnum category, List<Class<? extends SysMLOccurrence>> allocatedTo, List<SysMLVerificationMethodKind> verificationMethods, List<Class<? extends SysMLVerificationCase>> verifiedBy, List<SysMLHyperlink> supportingInformationLinks)
	{
		super(constraintFunction, new SysMLDocumentation(constraintText), identifier, 0L);
		this.title = title;
		this.subject = subject;
		this.stakeholders = stakeholders;
		this.concerns = concerns;
		this.actors = actors;
		this.isAssumption = isAssumption;
		this.assumed = assumed;
		this.category = category;
		this.composedOf = new ArrayList<>();
		this.allocatedTo = allocatedTo;
		this.verificationMethods = verificationMethods;
		this.verifiedBy = verifiedBy;
		this.supportingInformationLinks = supportingInformationLinks;
		this.deriveds = new ArrayList<>();
		this.derivedFrom = Optional.empty();
	}

	/**
	 * Constructor for empty requirement with name and id, typically used in
	 * constructor of extensions/specializations of the {@code SysMLRequirement}
	 * 
	 * @param name name for the requirement (typically same as requirement's
	 *             {@code identifier}
	 * @param id   unique identifier of the requirement, or {@code 0L} if none
	 */
	public SysMLRequirement(String name, Long id)
	{
		super(name, id);
		subject = Optional.empty();
		stakeholders = new ArrayList<>();
		concerns = new ArrayList<>();
		actors = new ArrayList<>();
		isAssumption = false;
		assumed = new ArrayList<>();
		category = RequirementCategoryEnum.Functional;
		composedOf = new ArrayList<>();
		allocatedTo = new ArrayList<>();
		verificationMethods = new ArrayList<>();
		verifiedBy = new ArrayList<>();
		supportingInformationLinks = new ArrayList<>();
		deriveds = new ArrayList<>();
		derivedFrom = Optional.empty();
	}

	/**
	 * Convenience operation to recursively set the identifiers of a tree of
	 * requirements composed of requirements
	 */
	public void idRecursive()
	{
		identifier = "0";
		if (!composedOf.isEmpty())
		{
			List<Integer> levelNumbers = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			for (SysMLRequirement requirement : composedOf)
				requirement.setIDs(levelNumbers, 0);
		}
	}

	/**
	 * Sets the identifier of this requirement in accordance with its location in a
	 * decomposition tree
	 * 
	 * @param levelNumbers the numbers associated with the levels of the
	 *                     decomposition
	 * @param level        the level to be used for the identifier
	 */
	public void setIDs(List<Integer> levelNumbers, int level)
	{
		levelNumbers.set(level, levelNumbers.get(level) + 1);
		StringJoiner joiner = new StringJoiner(".");
		for (int i = 0; i <= level; i++)
		{
			String string = levelNumbers.get(i).toString();
			joiner.add(string);
		}
		identifier = joiner.toString();
		if (!composedOf.isEmpty())
		{
			levelNumbers.set(level + 1, 0);
			composedOf.forEach((decomp -> decomp.setIDs(levelNumbers, level + 1)));
		}
	}

	/**
	 * Overridable operation to create the requirement's . Typically only used by
	 * extensions/specializations of the {@code SysMLRequirement}. An example
	 * follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createIdentifier()
				{
					identifier = "1.3";
				}
					:
			}}</pre>
	 */
	protected void createIdentifier()
	{

	}

	/**
	 * Overridable operation to create the requirement's title. Typically only used
	 * by extensions/specializations of the {@code SysMLRequirement}. An example
	 * follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createTitle()
				{
					title = "System Access";
				}
					:
			}}</pre>
	 */
	protected void createTitle()
	{

	}

	/**
	 * Overridable operation to create the requirement's subject. Typically only
	 * used by extensions/specializations of the {@code SysMLRequirement}. An
	 * example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createSubject()
				{
					subject = Optional.of(SurveillanceSystem.class);
				}
					:
			}}</pre>
	 */
	protected void createSubject()
	{

	}

	/**
	 * Overridable operation to create the requirement's stakeholders. Typically
	 * only used by extensions/specializations of the {@code SysMLRequirement}. An
	 * example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createStakeholders()
				{
					stakeholders = List.of(Operator.class, MaintenanceTech.class, ...);
				}
					:
			}}</pre>
	 */
	protected void createStakeholders()
	{

	}

	/**
	 * Overridable operation to create the requirement's concerns. Typically only
	 * used by extensions/specializations of the {@code SysMLRequirement}. An
	 * example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createConcerns()
				{
					concerns = List.of(AllConcerns.tooMuchAccess, AllConcerns.tooLittleAccess);
				}
					:
			}}</pre>
	 */
	protected void createConcerns()
	{

	}

	/**
	 * Overridable operation to create the requirement's actors. Typically only used
	 * by extensions/specializations of the {@code SysMLRequirement}. An example
	 * follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createActors()
				{
					actors = List.of(Operator.class, MaintenanceTech.class, ...);
				}
					:
			}}</pre>
	 */
	protected void createActors()
	{

	}

	/**
	 * Overridable operation to create the requirement's assumption status.
	 * Typically only used by extensions/specializations of the
	 * {@code SysMLRequirement}. An example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createIsAssumption()
				{
					isAssumption = false;
				}
					:
			}}</pre>
	 */
	protected void createIsAssumption()
	{

	}

	/**
	 * Overridable operation to create the requirement's assumptions. Typically only
	 * used by extensions/specializations of the {@code SysMLRequirement}. An
	 * example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createssumeds()
				{
					assumed = List.of(milspecCompliance, clearedActors, ...);
				}
					:
			}}</pre>
	 */
	protected void createAssumeds()
	{

	}

	/**
	 * Overridable operation to create the requirement's category. Typically only
	 * used by extensions/specializations of the {@code SysMLRequirement}. An
	 * example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createCategory()
				{
					category = RequirementCategoryEnum.security;
				}
					:
			}}</pre>
	 */
	protected void createCategory()
	{

	}

	/**
	 * Overridable operation to create the requirement's allocation. Typically only
	 * used by extensions/specializations of the {@code SysMLRequirement}. An
	 * example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createAllocatedTo()
				{
					allocatedTo = List.of(SurveillanceSystem.class);
				}
					:
			}}</pre>
	 */
	protected void createAllocatedTo()
	{

	}

	/**
	 * Overridable operation to create the requirement's verification methods.
	 * Typically only used by extensions/specializations of the
	 * {@code SysMLRequirement}. An example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createVerificationMethods()
				{
					verificationMethods = List.of(SysMLVerificationMethodKind.analysis, SysMLVerificationMethodKind.test);
				}
					:
			}}</pre>
	 */
	protected void createVerificationMethods()
	{

	}

	/**
	 * Overridable operation to create the requirement's verfication case. Typically
	 * only used by extensions/specializations of the {@code SysMLRequirement}. An
	 * example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createVerifiedBys()
				{
					verifiedBy = List.of(SystemAccessVerification.class);
				}
					:
			}}</pre>
	 */
	protected void createVerifiedBy()
	{

	}

	/**
	 * Overridable operation to create the requirement's supporting info links.
	 * Typically only used by extensions/specializations of the
	 * {@code SysMLRequirement}. An example follows.
	 * 
	 * <pre>{@code
			public class AccessRequirement extends SysMLRequirement
			{
					:
				&#64;Override
				protected void createSupportingInformationLinks()
				{
					supportingInformationLinks = List.of(AllHyperlinks.securityPolicySpec);
				}
					:
			}}</pre>
	 */
	@Override
	protected void createSupportingInformationLinks()
	{

	}

	/**
	 * Convenience operation that recursively prints to System.out a formatted
	 * string representation of all the requirements in a decomposition tree
	 */
	public void printRecursive()
	{
		String asText = text.text;
		StringJoiner stakeholdersString = new StringJoiner(", ");
		stakeholders.forEach(value -> stakeholdersString.add(value.getClass().getSimpleName()));
		StringJoiner concernsString = new StringJoiner(", ");
		concerns.forEach(value -> concernsString.add(value.toString()));
		StringJoiner actorsString = new StringJoiner(", ");
		actors.forEach(value -> actorsString.add(value.getClass().getSimpleName()));
		StringJoiner verificationMethodsString = new StringJoiner(", ");
		verificationMethods.forEach(value -> verificationMethodsString.add(value.toString()));
		StringJoiner verifiedBysString = new StringJoiner(", ");
		verifiedBy.forEach(value -> verifiedBysString.add(value.getClass().getSimpleName()));
		StringJoiner supportingInfoString = new StringJoiner(", ");
		supportingInformationLinks.forEach(value -> supportingInfoString.add(value.uri()));
		System.out.println(String.format("%-10s %s%n%s%n %s %s %s %s%n %s %s %s%n", identifier, htmlRemove(title), htmlRemove(asText), category, stakeholdersString, concernsString, actorsString, verificationMethodsString, verifiedBysString.toString(), supportingInfoString));
		composedOf.forEach(composed -> composed.printRecursive());
	}

	/**
	 * HTML tag for span start
	 */
	public static final String spanStart = "<span>";
	/**
	 * HTML tag for span end
	 */
	public static final String spanEnd = "</span>";
	/**
	 * HTML tag for pre start
	 */
	public static final String preStart = "<pre>";
	/**
	 * HTML tag for pre end
	 */
	public static final String preEnd = "</pre>";
	/**
	 * Empty string representation
	 */
	public static final String emptyString = "";

	/**
	 * Removes HTML tags from specified string
	 * 
	 * @param string string from which tags are to be removed
	 * @return tag-free string
	 */
	public static String htmlRemove(String string)
	{
		return string.replaceAll(spanStart, emptyString).replaceAll(spanEnd, emptyString).replaceAll(preStart, emptyString).replaceAll(preEnd, emptyString);
	}

	/**
	 * String format for CSV import of requirement. Requirement is defined
	 * completely in single line of text. Line is CSV (using vertical bars instead
	 * of commas) of single instance attributes, followed by kev-value strings for
	 * user-defined attributes and for each dependencies list. Example CSV for a
	 * requirements is as follows.
	 * 
	 * <pre>
	 * {@code
		1.2.3.4|Title of the requirement|Text spec of the requirement|Functional|Demonstration|Medium|Proposed|1|R Lee|U Grant|myAtt1=[This is my firstAttribute],myAtt2=[This is my secondAttribute]|derivedFrom=[1.1, 4.2.1],verifiedBy=[FunctionalTestCase,PerformanceTestCase,InterfaceTestCase];
	 }
	 * </pre>
	 */
	public static final String csvFormatAttributes = "%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s";
	/**
	 * String format for CSV import of requirement user-defined attributes that is
	 * in a bar-separated field in the CSV line. Field consists of a key-value
	 * string pair where the key is the attribute name and the value is a
	 * brackets-enclosed string. An example follows.
	 * 
	 * <pre>
	 * {@code
		userAtt1=[This is a user-defined attribute],userAtt2=[This is another user-defined attribute]
	}
	 * </pre>
	 */
	public static final String csvFormatUserDefined = "%s=[%s]";

	/**
	 * String format for CSV import of requirement dependency list that is in a
	 * bar-separated field in the CSV line. Field consists of a key-value string
	 * pair where the key is the dependency name and the value is a comma-separated
	 * set of names of the dependency target. An example follows.
	 * 
	 * <pre>
	 * {@code
		derivedFrom=[1.1, 4.2.1],verifiedBy=[FunctionalTestCase,PerformanceTestCase,InterfaceTestCase]
	}
	 * </pre>
	 */
	public static final String csvFormatDependency = "%s=[%s]";

	/**
	 * Name of variable for assumptions, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String assumedVariableName = "assumed";
	/**
	 * Name of variable for stakeholders, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String stakeholdersVariableName = "stakeholders";
	/**
	 * Name of variable for composed-of requirements, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String composedOfVariableName = "composedOf";
	/**
	 * Name of variable for assumption indication, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String isAssumptionVariableName = "isAssumption";
	/**
	 * Name of variable for derived requirements, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String derivedsVariableName = "derivedRequirements";
	/**
	 * Name of variable for derived-from requirement, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String derivedFromsVariableName = "derivedFromRequirement";
	/**
	 * Name of method to create identifier, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createIdentifierMethodName = "createIdentifier";
	/**
	 * Name of method to create title, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createTitleMethodName = "createTitle";
	/**
	 * Name of method to create subject, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createSubjectMethodName = "createSubject";
	/**
	 * Name of method to create stakeholders, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createStakeholdersMethodName = "createStakeholders";
	/**
	 * Name of method to create concerns, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createConcernsMethodName = "createConcerns";
	/**
	 * Name of method to create actors, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createActorsMethodName = "createActors";
	/**
	 * Name of method to create assumption indication, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createIsAssumptionMethodName = "createIsAssumption";
	/**
	 * Name of method to create assumptions, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createAssumedsMethodName = "createAssumeds";
	/**
	 * Name of method to create requirement category, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createCategoryMethodName = "createCategory";
	/**
	 * Name of method to create elements allocated to, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createAllocatedToMethodName = "createAllocatedTo";
	/**
	 * Name of method to create verification methods, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createVerificationMethodsMethodName = "createVerificationMethods";
	/**
	 * Name of method to create verification cases, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createVerifiedByMethodName = "createVerifiedBy";
	/**
	 * Name of method to create supporting information links, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createSupportingInforationLinksMethodName = "createSupportingInforationLinks";
}
