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
package sysmlinjava.common;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * SysMLinJava representation of the base class of all SysML model elements, the
 * "Anything" class.
 * <h2>Base class for all SysML model element classes</h2>The
 * {@code SysMLAnything} is an abstract class that provides the basic
 * SysMLinJava class attributes such as a name and ID number as well as a
 * logger. Virtually all of the other SysMLinJava elements such as
 * {@code SysMLPart}, {@code SysMLAttributeValue}, {@code SysMLSignal}, and all
 * of the {@code SysMLStateMachine}-related elements are extensions of the
 * {@code SysMLAnything}.
 * <h3>Create/initialize methods</h3> The {@code SysMLAnything} provides a
 * series of overrideable method calls to create any annotations or metadata of
 * the represented SysML class. The {@code SysMLAnything} constructor
 * automatically invokes these methods to create the annotations and metadata
 * (problems, rationales, hyperlinks, etc) so that extensions to the
 * {@code SysMLAnything} need only override the {@code createXxxx()} methods for
 * the applicable annotation or metadata fields declared in the class to ensure
 * the features are created/initialized in the correct and complete sequence
 * needed. The overridable {@code createXxxx()} methods provide a "reminder" to
 * create and initialize the class's annoations and metadata as well as a
 * framework for their complete definition.
 *
 * @author ModelerOne
 */
public abstract class SysMLAnything
{
	/**
	 * Logger for this and inheriting classes
	 */
	protected Logger logger;
	/**
	 * Optional name for this SysMLinJava object to be used in displays, logs,
	 * genrated code, etc. as desired
	 */
	public Optional<String> name;
	/**
	 * ID of this SysMLinJava object to be used to identify it as desired, e.g. as
	 * the index for this SysMLinJava object's location in an array of objects.
	 * Defaults to 0 if not specified or set.
	 */
	public Long id;

	/**
	 * Constructor for logger only initialization, i.e. no name nor id
	 */
	public SysMLAnything()
	{
		super();
		logger = Logger.getLogger(this.getClass().getSimpleName());
		name = Optional.empty();
		id = 0L;

		createComments();
		createTextualRepresentations();
		createDocumentations();
		createIssues();
		createCausations();
		createRefinements();
		createRationales();
		createRisk();
		createSupportingInformationLinks();
		createIcon();
		createImage();
		createStatusInfo();
	}

	/**
	 * Constructor for logger and name only initialization, i.e. no id
	 * 
	 * @param name name for the class
	 */
	protected SysMLAnything(String name)
	{
		this();
		this.name = Optional.of(name);
		id = 0L;

		createComments();
		createTextualRepresentations();
		createDocumentations();
		createIssues();
		createCausations();
		createRefinements();
		createRationales();
		createRisk();
		createSupportingInformationLinks();
		createIcon();
		createImage();
		createStatusInfo();
	}

	/**
	 * Constructor for logger, name, and id initialization.
	 * 
	 * @param name name for the class
	 * @param id   unique identifier of the class
	 */
	protected SysMLAnything(String name, Long id)
	{
		this(name);
		this.id = id;

		createComments();
		createTextualRepresentations();
		createDocumentations();
		createIssues();
		createCausations();
		createRefinements();
		createRationales();
		createRisk();
		createSupportingInformationLinks();
		createIcon();
		createImage();
		createStatusInfo();
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance this instance is to be copy of
	 */
	protected SysMLAnything(SysMLAnything copied)
	{
		this();
		this.name = copied.name;
		this.id = copied.id;
	}

	/**
	 * Sets the name field that may or may not be unique, as needed
	 * 
	 * @param name name of the SysMLAnything instance
	 */
	public void setName(String name)
	{
		this.name = Optional.of(name);
	}

	/**
	 * Gets the name of the class instance
	 * 
	 * @return name of the SysMLAnything instance, if present, name of the class if
	 *         not.
	 */
	public String getName()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	/**
	 * Sets the id field
	 * 
	 * @param id Long number that may or may not be unique, as needed
	 */
	public void setID(Long id)
	{
		this.id = id;
	}

	/**
	 * Overridable operation for the identity String for this object for use in
	 * logging and other display outputs. This default implementation returns the
	 * object's {@code name}, if present, or the class's name if not. If the
	 * {@code id} is set to a value greater than 0, then the id is concatenated. (If
	 * the {@code id} is to be concatenated to the identity string, then set the
	 * {@code id} to an integer value greater than 0). If an array {@code index}
	 * value is present, the index (in square brackets) is concatenated to the
	 * string.
	 * <p>
	 * Extensions of the {@code SysMLAnything} can override this operation to
	 * provide an arbitrarily formatted identity string as desired.
	 * 
	 * @return the identity string for this {@code SysMLAnything} object
	 */
	public String identityString()
	{
		String result;
		if (name.isPresent())
		{
			if (id > 0)
				result = String.format("%s%d", name.get(), id);
			else
				result = String.format("%s", name.get());
		}
		else if (id > 0)
			result = String.format("%s%d", getClass().getSimpleName(), id);
		else
			result = String.format("%s", getClass().getSimpleName());
		return result;
	}

	/**
	 * Overridable operation that creates and initializes the class's comments. An
	 * example follows:
	 * 
	 * <pre>{@code
		{
			&#64;Comment
			SysMLComment workInProgress;
				:
			&#64;Override
			protected void createComments()
			{
				workInProgress = new SysMLComment("Component model is still a work in progress, i.e. performed action is TBD");
			}
		}}</pre>
	 */
	protected void createComments()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's documentation.
	 * An example follows:
	 * 
	 * <pre>{@code
		{
			&#64;Documentation
			SysMLDocumentation notionalRequirement;
				:
			&#64;Override
			protected void createDocumentations()
			{
				notionalRequirement = new SysMLDcoumentation("Subsystem performs all sensor inputs.");
			}
		}}</pre>
	 */
	protected void createDocumentations()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's textual
	 * representations. An example follows:
	 * 
	 * <pre>{@code
		{
			&#64;TextualRepresentation
			SysMLTextualRepresentation preflightCondition;
				:
			&#64;Override4
			protected void createTextualRepresentations()
			{
				preflightCondition = new SysMLTextualRepresentation("Java", "if(inspectionOK()) okToFly = true;");
			}
		}}</pre>
	 */
	protected void createTextualRepresentations()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's hyperlink
	 * annotations. An example follows:
	 * 
	 * <pre>{@code
		{
			&#64;Hyperlink
			SysMLHyperlink specHyperlink;
			&#64;Hyperlink
			SysMLHyperlink manualHyperlink;
				:
			&#64;Override
			protected void createSupportingInformationLinks()
			{
				specHyperlink = new SysMLHyperlink("System Specification v2.2", "https://AlphaCo.com/DocServer/System_Spec_2.2.pdf");
				manualHyperlink = new SysMLHyperlink("System Operations Manual v2.2", "https://AlphaCo.com/DocServer/System_Ops_Manual_2.2.pdf");
			}
		}}</pre>
	 */
	protected void createSupportingInformationLinks()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's issues. An
	 * example follows:
	 * 
	 * <pre>{@code
		{
			&#64;Issue
			SysMLIssue heatIssue;
			&#64;Issue
			SysMLIssue powerIssue;
				:
			&#64;Override
			protected void createIssues()
			{
				heatIssue = new SysMLIssue("Convective heat exceeds requirements");
				powerIssue = new SysMLIssue("Electrical power voltage likely to be unstable");
			}
		}}</pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field
	 * annotated with the {@code &#64;Issue} annotation and of type
	 * {@code SysMLIssue}. The assigned value is the {@code SysMLIssue}'s
	 * constructor with initialization parameters as appropriate.
	 */
	protected void createIssues()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's refinements.
	 * An example follows:
	 * 
	 * <pre>{@code
		{
			&#64;Refinement
			SysMLRefinement refinement;
				:
			&#64;Override
			protected void createRefinements()
			{
				refinement = new SysMLRefinement("Convective heat exceeds requirements");
				powerIssue = new SysMLIssue("Electrical power voltage likely to be unstable");
			}
		}}</pre>
	 */
	protected void createRefinements()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's rationale
	 * comments. An example follows:
	 * 
	 * <pre>{@code
		{
			&#64;Rationale
			SysMLRationale sizeRationale;
			&#64;Rationale
			SysMLRationale costRationale;
				:
			&#64;Override
			protected void createRationales()
			{
				heatRationale = new SysMLRationale("Size permits better fit in storage container");
				costRationale = new SysMLRationale("Higer initial cost will provide for lower lifecycle cost");
			}
		}}</pre>
	 */
	protected void createRationales()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's rationale
	 * comments. An example follows:
	 * 
	 * <pre>{@code
		{
			&#64;Risk
			SysMLRisk risk;
				:
			&#64;Override
			protected void createRisk()
			{
				risk = new SysMLRisk(new SysMLRiskLevel(0.5,0.5), new SysMLRiskLevel(0.5,0.5), new SysMLRiskLevel(0.5,0.5), new SysMLRiskLevel(0.5,0.5));
			}
		}}</pre>
	 */
	protected void createRiskLevels()
	{
	}

	/**
	 * Overridable operation that creates and initializes the class's rationale
	 * comments. An example follows:
	 * 
	 * <pre>{@code
		{
			&#64;Risk
			SysMLRisk risk;
				:
			&#64;Override
			protected void createRisk()
			{
				risk = new SysMLRisk(new SysMLRiskLevel(0.5,0.5), new SysMLRiskLevel(0.5,0.5), new SysMLRiskLevel(0.5,0.5), new SysMLRiskLevel(0.5,0.5));
			}
		}}</pre>
	 */
	protected void createRisk()
	{
	}

	protected void createImage()
	{
	}

	protected void createIcon()
	{
	}

	protected void createStatusInfo()
	{
	}

	protected void createCausations()
	{
	}

	protected void createElementFilters()
	{
	}

	/**
	 * Name of method to create comments, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createCommentsMethodName = "createComments";
	/**
	 * Name of method to create documentations, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createDocumentationsMethodName = "createDocumentations";
	/**
	 * Name of method to create textual representations, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createTextualRepresentationsMethodName = "createTextualRepresentations";
	/**
	 * Name of method to create hyperlinks to supporting information, used by
	 * SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createSupportingInformationLinksMethodName = "createSupportingInformationLinks";
	/**
	 * Name of method to create issues, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createIssuesMethodName = "createIssues";
	/**
	 * Name of method to create rationales, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createRationalesMethodName = "createRationales";
	/**
	 * Name of method to create risk levels, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createRiskLevelsMethodName = "createRiskLevels";
	/**
	 * Name of method to create risk, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createRiskMethodName = "createRisk";
	/**
	 * Name of method to create the element's image, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createImageMethodName = "createImage";
	/**
	 * Name of method to create the element's icon, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createIconMethodName = "createIcon";
	/**
	 * Name of method to create the element's status information, used by
	 * SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createStatusInfoMethodName = "createStatusInfo";
	/**
	 * Name of method to create the element's causation information, used by
	 * SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createCausationsMethodName = "createCausations";
	/**
	 * Name of method to create the element filters, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createElementFiltersMethodName = "createElementFilters";
	/**
	 * Name of method to create the element's refinmments, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createRefinementsMethodName = "createRefinements";
}
