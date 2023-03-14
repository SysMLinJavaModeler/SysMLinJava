package sysmlinjava.requirements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import sysmlinjava.annotations.dependencies.Dependency;
import sysmlinjava.comments.SysMLHyperlink;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.tests.SysMLTestCase;

/**
 * SysMLinJava representation of the SysML AbstractRequirement. This abstract
 * implementation includes the attributes of the SysML abstract requirement as
 * well as additional attributes required by a typical requirement
 * specification. The {@code SysMLAbstractRequirement} must be inherited by
 * actual requirement objects and, in this regard, SysMLinJava includes the
 * {@code SysMLRequirement} class as a representation of the SysML Requirement.
 * 
 * 
 * @author ModelerOne
 *
 * @see SysMLRequirement
 */
public abstract class SysMLAbstractRequirement extends SysMLClass
{
	/**
	 * Unique identifier for the requirement
	 */
	public String identifier;
	/**
	 * Unique title of the requirement
	 */
	public String title;
	/**
	 * Textual representation of the requirement
	 */
	public String text;
	/**
	 * Category of the reqwuirement
	 */
	public RequirementCategoryEnum category;
	/**
	 * Method of verification of the requirement
	 */
	public List<SysMLVerificationMethodKind> verification;
	/**
	 * Risk associated with the requirement
	 */
	public SysMLRiskKind risk;
	/**
	 * Status of the requirement
	 */
	public RequirementStatusEnum status;
	/**
	 * Priority of the requirement (lower number is higher priority)
	 */
	public Integer priority;
	/**
	 * Name of originator of the requirement
	 */
	public String originator;
	/**
	 * Name of owner of the requirement
	 */
	public String owner;
	/**
	 * Map of name-values for user-defined attributes of the requirement
	 */
	public Map<String, String> userDefined;

	/**
	 * List of requirements that are derived from this requirement
	 */
	@Dependency
	public List<? extends SysMLAbstractRequirement> derivedBy;
	/**
	 * List of requirements that this requirement is derived from
	 */
	@Dependency
	public List<? extends SysMLAbstractRequirement> derivedFrom;
	/**
	 * List of extended classes that are designated to satisfy this requirement
	 */
	@Dependency
	public List<Class<? extends SysMLClass>> satisfiedBy;
	/**
	 * List of extended classes that refine this requirement
	 */
	@Dependency
	public List<Class<? extends SysMLClass>> refinedBy;
	/**
	 * List of extended classes that the requirement is traced to
	 */
	@Dependency
	public List<Class<? extends SysMLClass>> tracedTo;
	/**
	 * List of extended classes (tests, etc.) that verify this requirement
	 */
	@Dependency
	public List<Class<? extends SysMLTestCase>> verifiedBy;
	/**
	 * The "master" requirement of which this requirement is a copy
	 */
	@Dependency
	public Optional<? extends SysMLAbstractRequirement> master;
	/**
	 * URI's to supporting information for this requirement
	 */
	@Dependency
	public List<SysMLHyperlink> supportingInformationLinks;

	/**
	 * String value for elements of the requirement that are not yet specified
	 */
	public static final String notSpecified = "not specified";

	/**
	 * Constructor for default (empty, unspecified, or "least" value) initialization
	 * of the requirement
	 */
	public SysMLAbstractRequirement()
	{
		this.identifier = "0";
		this.title = notSpecified;
		this.text = notSpecified;
		this.category = RequirementCategoryEnum.Functional;
		this.verification = List.of(SysMLVerificationMethodKind.Demonstration);
		this.risk = SysMLRiskKind.Low;
		this.status = RequirementStatusEnum.Proposed;
		this.priority = 5;
		this.originator = notSpecified;
		this.owner = notSpecified;
		this.userDefined = new HashMap<>();
		this.derivedBy = new ArrayList<>();
		this.derivedFrom = new ArrayList<>();
		this.satisfiedBy = new ArrayList<>();
		this.refinedBy = new ArrayList<>();
		this.tracedTo = new ArrayList<>();
		this.verifiedBy = new ArrayList<>();
		this.master = Optional.empty();
		this.supportingInformationLinks = new ArrayList<>();
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
	 * @param satisfiedBy                classes (blocks, statemachines, interfaces,
	 *                                   etc) that satisfy this requirement
	 * @param refinedBy                  classes (blocks, statemachines, interfaces,
	 *                                   etc) that refine this requiremenet
	 * @param tracedTo                   classes to which this requirement is traced
	 * @param verifiedBy                 classes (tests, etc) that verify this
	 *                                   requirement
	 * @param master                     requirement of which this requirement is a
	 *                                   copy
	 * @param supportingInformationLinks URIs to supporting information of the
	 *                                   requirement
	 */
	public SysMLAbstractRequirement(String identifier, String title, String text, RequirementCategoryEnum category, List<SysMLVerificationMethodKind> verification, SysMLRiskKind risk, RequirementStatusEnum status, Integer priority, String originator, String owner, Map<String, String> userDefined, List<SysMLAbstractRequirement> derivedBy, List<SysMLAbstractRequirement> derivedFrom, List<Class<? extends SysMLClass>> satisfiedBy, List<Class<? extends SysMLClass>> refinedBy, List<Class<? extends SysMLClass>> tracedTo, List<Class<? extends SysMLTestCase>> verifiedBy, Optional<SysMLAbstractRequirement> master, List<SysMLHyperlink> supportingInformationLinks)
	{
		super();
		this.identifier = identifier;
		this.title = title;
		this.text = text;
		this.category = category;
		this.verification = verification;
		this.risk = risk;
		this.status = status;
		this.priority = priority;
		this.originator = originator;
		this.owner = owner;
		this.userDefined = userDefined;
		this.derivedBy = derivedBy;
		this.derivedFrom = derivedFrom;
		this.satisfiedBy = satisfiedBy;
		this.refinedBy = refinedBy;
		this.tracedTo = tracedTo;
		this.verifiedBy = verifiedBy;
		this.master = master;
		this.supportingInformationLinks = supportingInformationLinks;
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
	 * @param owner        requirement's ownder
	 * @param userDefined  requirement's user-defined attributes as map of
	 *                     name-value pairs
	 */
	public SysMLAbstractRequirement(String identifier, String title, String text, RequirementCategoryEnum category, List<SysMLVerificationMethodKind> verification, SysMLRiskKind risk, RequirementStatusEnum status, Integer priority, String originator, String owner, Map<String, String> userDefined)
	{
		super();
		this.identifier = identifier;
		this.title = title;
		this.text = text;
		this.category = category;
		this.verification = verification;
		this.risk = risk;
		this.status = status;
		this.priority = priority;
		this.originator = originator;
		this.owner = owner;
		this.userDefined = userDefined;
		this.derivedBy = new ArrayList<>();
		this.derivedFrom = new ArrayList<>();
		this.satisfiedBy = new ArrayList<>();
		this.refinedBy = new ArrayList<>();
		this.tracedTo = new ArrayList<>();
		this.verifiedBy = new ArrayList<>();
		this.master = Optional.empty();
		this.supportingInformationLinks = new ArrayList<>();
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

	@Override
	public String toString()
	{
		return String.format("SysMLAbstractRequirement [identifier=%s, title=%s, text=%s, category=%s, verification=%s, risk=%s, status=%s, priority=%s, originator=%s, owner=%s, userDefined=%s]", identifier, title, text, category,
			verification, risk, status, priority, originator, owner, userDefined);
	}

}
