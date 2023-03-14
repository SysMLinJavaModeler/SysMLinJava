package sysmlinjava.requirements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import sysmlinjava.annotations.Containment;
import sysmlinjava.annotations.dependencies.Dependency;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.tests.SysMLTestCase;

/**
 * SysMLinJava representation of the SysML requirement. as required, the
 * {@code SysMLRequirement} is an extension of the
 * {@code SysMLAbstractRequirement}, adding dependencies for requirement
 * containment, decomposition, copying, and tracing.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLRequirement extends SysMLAbstractRequirement
{
	/**
	 * Optional requirement that this requirement is contained by
	 */
	@Containment
	public Optional<SysMLRequirement> containedBy;
	/**
	 * List of requirements that are contained by this requirement
	 */
	@Containment
	public List<SysMLRequirement> contained;
	/**
	 * List of requirements that decompose this requirement
	 */
	@Dependency
	public List<SysMLRequirement> decomposedBy;
	/**
	 * List of requirements that this requirement is a decomposition of
	 */
	@Dependency
	public List<SysMLRequirement> decompositionOf;
	/**
	 * List of requirements that copy this requirement
	 */
	@Dependency
	public List<SysMLRequirement> copiedBy;
	/**
	 * Optional requirement of which this requirement is a copy. Note, per SysML,
	 * this dependency is redundant to the {@code master} dependency. Either or both
	 * may be specified.
	 */
	@Dependency
	public Optional<SysMLRequirement> copyOf;
	/**
	 * List of extended classes that are traced from this requirement
	 */
	@Dependency
	public List<Class<? extends SysMLClass>> tracedFrom;
	/**
	 * Requirement of which this requirement is a specialization
	 */
	@Dependency
	public Optional<SysMLRequirement> specializationOf;

	/**
	 * Constructor for default initializations
	 */
	public SysMLRequirement()
	{
		super();
		containedBy = Optional.empty();
		contained = new ArrayList<>();
		decomposedBy = new ArrayList<>();
		decompositionOf = new ArrayList<>();
		copiedBy = new ArrayList<>();
		copyOf = Optional.empty();
		tracedFrom = new ArrayList<>();
		specializationOf = Optional.empty();
	}

	/**
	 * Constructor for all requirement attributes (dependencies initialized empty)
	 * 
	 * @param identifier   requirement's unique identifier
	 * @param title        requirement's unique title
	 * @param text         requirement's textual specification
	 * @param category     requirement's category
	 * @param verification requirement's verification method
	 * @param risk         requirement's risk
	 * @param status       requirement's status
	 * @param priority     requirement's priority
	 * @param originator   requirement's originator
	 * @param owner        requirement's owner
	 * @param userDefined  requirement's user-defined attributes
	 */
	public SysMLRequirement(String identifier, String title, String text, RequirementCategoryEnum category, List<SysMLVerificationMethodKind> verification, SysMLRiskKind risk, RequirementStatusEnum status, Integer priority, String originator, String owner, Map<String, String> userDefined)
	{
		super(identifier, title, text, category, verification, risk, status, priority, originator, owner, userDefined);
		containedBy = Optional.empty();
		contained = new ArrayList<>();
		decomposedBy = new ArrayList<>();
		decompositionOf = new ArrayList<>();
		copiedBy = new ArrayList<>();
		copyOf = Optional.empty();
		tracedFrom = new ArrayList<>();
		specializationOf = Optional.empty();
	}

	/**
	 * Constructor for all requirement attributes and dependencies
	 * 
	 * @param identifier                 requirement's unique identifier
	 * @param title                      requirement's unique title
	 * @param text                       requirement's textual specification
	 * @param category                   requirement's category
	 * @param verification               requirement's verification method
	 * @param risk                       requirement's risk
	 * @param status                     requirement's status
	 * @param priority                   requirement's priority
	 * @param originator                 requirement's originator
	 * @param owner                      requirement's ownder
	 * @param userDefined                requirement's user-defined attributes as
	 *                                   map of name-value pairs
	 * @param derivedBy                  requirements derived from this requirement
	 * @param derivedFrom                requirements from which this requirement is
	 *                                   derived
	 * @param satisfiedBy                objects that satisfy this requirement
	 * @param refinedBy                  requirements that refine this requirement
	 * @param tracedTo                   objects that trace to this requirement
	 * @param verifiedBy                 test cases that verify this requirement
	 * @param master                     requirement that is the master of this
	 *                                   requirement
	 * @param supportingInformationLinks URIs to supporting information for this
	 *                                   requirement
	 * @param containedBy                optional requirement that contains this
	 *                                   requirement
	 * @param contained                  requirements that are contained by this
	 *                                   requirement
	 * @param decomposedBy               requirements that decompose this
	 *                                   requirement
	 * @param decompositionOf            requirements that this requirement
	 *                                   decomposes
	 * @param copiedBy                   requirements that are a copy of this
	 *                                   requirement
	 * @param copyOf                     optional requirement that this requirement
	 *                                   is a copy of
	 * @param tracedFrom                 objects that trace from this requirement
	 * @param specializationOf           Optional requirement that this requirement
	 *                                   is a specialization of
	 */
	public SysMLRequirement(String identifier, String title, String text, RequirementCategoryEnum category, List<SysMLVerificationMethodKind> verification, SysMLRiskKind risk, RequirementStatusEnum status, Integer priority, String originator, String owner, Map<String, String> userDefined, List<SysMLAbstractRequirement> derivedBy, List<SysMLAbstractRequirement> derivedFrom, List<Class<? extends SysMLClass>> satisfiedBy, List<Class<? extends SysMLClass>> refinedBy, List<Class<? extends SysMLClass>> tracedTo, List<Class<? extends SysMLTestCase>> verifiedBy, Optional<SysMLAbstractRequirement> master, List<SysMLHyperlink> supportingInformationLinks, Optional<SysMLRequirement> containedBy, List<SysMLRequirement> contained, List<SysMLRequirement> decomposedBy, List<SysMLRequirement> decompositionOf, List<SysMLRequirement> copiedBy, Optional<SysMLRequirement> copyOf, List<Class<? extends SysMLClass>> tracedFrom, Optional<SysMLRequirement> specializationOf)
	{
		super(identifier, title, text, category, verification, risk, status, priority, originator, owner, userDefined, derivedBy, derivedFrom, satisfiedBy, refinedBy, tracedTo, verifiedBy, master, supportingInformationLinks);
		this.containedBy = containedBy;
		this.contained = contained;
		this.decomposedBy = decomposedBy;
		this.decompositionOf = decompositionOf;
		this.copiedBy = copiedBy;
		this.copyOf = copyOf;
		this.tracedFrom = tracedFrom;
		this.specializationOf = specializationOf;
	}

	/**
	 * Adds reference to requirement that contains this requirement
	 * 
	 * @param contained requirement that contains this one
	 */
	public void addContained(SysMLRequirement contained)
	{
		this.contained.add(contained);
		contained.containedBy = Optional.of(this);
	}

	/**
	 * Adds reference to requirement that decomposes this requirement
	 * 
	 * @param decomposedBy requirement that decomposes this one
	 */
	public void addDecomposedBy(SysMLRequirement decomposedBy)
	{
		this.decomposedBy.add(decomposedBy);
		decomposedBy.decompositionOf.add(this);
	}

	/**
	 * Adds reference to requirement that is a copy of this requirement
	 * 
	 * @param copiedBy requirement that is a copy of this one
	 */
	public void addCopiedBy(SysMLRequirement copiedBy)
	{
		this.copiedBy.add(copiedBy);
		copiedBy.copyOf = Optional.of(this);
		copiedBy.master = Optional.of(this);
	}

	/**
	 * Convenience operation to recursively set the identifiers of a tree of
	 * decomposed requirements
	 */
	public void idRecursive()
	{
		identifier = "0";
		if (!decomposedBy.isEmpty())
		{
			List<Integer> levelNumbers = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			for (SysMLRequirement requirement : decomposedBy)
				requirement.setIDs(levelNumbers, 0);
		}
		else
			logger.severe("no requirements found, i.e. \"decomposedBys\" is empty");
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
		this.identifier = joiner.toString();
		if (!decomposedBy.isEmpty())
		{
			levelNumbers.set(level + 1, 0);
			decomposedBy.forEach((decomp -> decomp.setIDs(levelNumbers, level + 1)));
		}
	}

	/**
	 * Convenience operation that recursively prints to System.out a formatted
	 * string representation of all the requirements in a decomposition tree
	 */
	public void printRecursive()
	{
		StringJoiner verificationString = new StringJoiner(", ");
		verification.forEach(value -> verificationString.add(value.toString()));
		System.out.println(String.format("%-10s %s%n%-20s %-20s %-8s%n%s", identifier, htmlRemove(title), category, verificationString.toString(), risk, htmlRemove(text)));
		System.out.println();
		decomposedBy.forEach(decomp -> decomp.printRecursive());
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

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SysMLRequirement [containedBy=");
		builder.append(containedBy);
		builder.append(", contained=");
		builder.append(contained);
		builder.append(", decomposedBy=");
		builder.append(decomposedBy);
		builder.append(", decompositionOf=");
		builder.append(decompositionOf);
		builder.append(", copiedBy=");
		builder.append(copiedBy);
		builder.append(", copyOf=");
		builder.append(copyOf);
		builder.append(", tracedFrom=");
		builder.append(tracedFrom);
		builder.append(", specializationOf=");
		builder.append(specializationOf);
		builder.append(", identifier=");
		builder.append(identifier);
		builder.append(", title=");
		builder.append(title);
		builder.append(", text=");
		builder.append(text);
		builder.append(", category=");
		builder.append(category);
		builder.append(", verification=");
		builder.append(verification);
		builder.append(", risk=");
		builder.append(risk);
		builder.append(", status=");
		builder.append(status);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", originator=");
		builder.append(originator);
		builder.append(", owner=");
		builder.append(owner);
		builder.append(", userDefined=");
		builder.append(userDefined);
		builder.append(", derivedBy=");
		builder.append(derivedBy);
		builder.append(", derivedFrom=");
		builder.append(derivedFrom);
		builder.append(", satisfiedBy=");
		builder.append(satisfiedBy);
		builder.append(", refinedBy=");
		builder.append(refinedBy);
		builder.append(", tracedTo=");
		builder.append(tracedTo);
		builder.append(", verifiedBy=");
		builder.append(verifiedBy);
		builder.append(", master=");
		builder.append(master);
		builder.append(", supportingInformationLinks=");
		builder.append(supportingInformationLinks);
		builder.append(", logger=");
		builder.append(logger);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

}
