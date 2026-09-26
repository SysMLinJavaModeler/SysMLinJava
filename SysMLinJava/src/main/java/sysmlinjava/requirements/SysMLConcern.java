package sysmlinjava.requirements;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import sysmlinjava.annotations.SysMLHyperlink;
import sysmlinjava.constraint.SysMLConstraint;
import sysmlinjava.occurrences.SysMLOccurrence;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.parts.SysMLStakeholder;
import sysmlinjava.verifications.SysMLVerificationCase;

/**
 * SysMLinJava representation of the SysML concern. As a specialized type of
 * requirement, the {@code SysMLConcern} includes most of the members of the
 * {@code SysMLRequirement} with those members that are inappropriate for the
 * concern not used.
 */
public final class SysMLConcern extends SysMLRequirement
{
	static Logger logger = Logger.getLogger(SysMLConcern.class.getSimpleName());

	/**
	 * Constructor for constraint-defined concern having no specific occurrence
	 * (occurrence must be set via explicit assignment after constructor)
	 * 
	 * @param identifier                 concern's unique identifier
	 * @param title                      concern's unique title
	 * @param constraintFunction         concern specified as formal executable
	 *                                   constraint as lambda function
	 * @param constraintText             concern specified as simple text
	 *                                   documentation for informal non-executable
	 *                                   constraint
	 * @param subject                    class of subject of this concern
	 * @param actors                     list of classes of actors involved in this
	 *                                   concern, if any
	 * @param stakeholders               list of classes of stakeholders for this
	 *                                   concern, if any
	 * @param assumed                    list of names of instances of
	 *                                   {@code SysMLConstraint}s that are
	 *                                   assumptions for this concern
	 * @param category                   concern's category
	 * @param allocatedTo                list of classes of parts, ports, actions,
	 *                                   items to which this concern is allocated
	 * @param verificationMethods        concern's verification methods
	 * @param verifiedBy                 list of classes of verification cases that
	 *                                   verify this concern was satisfied
	 * @param supportingInformationLinks list of names of instances of
	 *                                   {@code SysMLHyperlink}s to information that
	 *                                   supports this concern.
	 */
	public SysMLConcern(String identifier, String title, String constraintText, Optional<SysMLRequirementConstraintFunction> constraintFunction, Optional<Class<? extends SysMLOccurrence>> subject, List<Class<? extends SysMLStakeholder>> stakeholders, List<Class<? extends SysMLPart>> actors, List<SysMLConstraint> assumed, RequirementCategoryEnum category, List<Class<? extends SysMLOccurrence>> allocatedTo, List<SysMLVerificationMethodKind> verificationMethods, List<Class<? extends SysMLVerificationCase>> verifiedBy, List<SysMLHyperlink> supportingInformationLinks)
	{
		super(identifier, title, constraintText, constraintFunction, subject, stakeholders, List.of(), actors, false, assumed, category, allocatedTo, verificationMethods, verifiedBy,
		supportingInformationLinks);
	}

	/**
	 * Constant string for specifying not specified
	 */
	public static final String notSpecified = "not specified";
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
	 * String format for CSV import of concern. Requirement is defined completely in
	 * single line of text. Line is CSV (using vertical bars instead of commas) of
	 * single instance attributes, followed by kev-value strings for user-defined
	 * attributes and for each dependencies list. Example CSV for a concerns is as
	 * follows.
	 * 
	 * <pre>
	 * {@code
		1.2.3.4|Title of the concern|Text spec of the concern|Functional|Demonstration|Medium|Proposed|1|R Lee|U Grant|myAtt1=[This is my firstAttribute],myAtt2=[This is my secondAttribute]|derivedFrom=[1.1, 4.2.1],verifiedBy=[FunctionalTestCase,PerformanceTestCase,InterfaceTestCase];
	 }
	 * </pre>
	 */
	public static final String csvFormatAttributes = "%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s";
	/**
	 * String format for CSV import of concern user-defined attributes that is in a
	 * bar-separated field in the CSV line. Field consists of a key-value string
	 * pair where the key is the attribute name and the value is a brackets-enclosed
	 * string. An example follows.
	 * 
	 * <pre>
	 * {@code
		userAtt1=[This is a user-defined attribute],userAtt2=[This is another user-defined attribute]
	}
	 * </pre>
	 */
	public static final String csvFormatUserDefined = "%s=[%s]";
	/**
	 * String format for CSV import of concern dependency list that is in a
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
	 * Removes HTML tags from specified string
	 * 
	 * @param string string from which tags are to be removed
	 * @return tag-free string
	 */
	public static String htmlRemove(String string)
	{
		return string.replaceAll(spanStart, emptyString).replaceAll(spanEnd, emptyString).replaceAll(preStart, emptyString).replaceAll(preEnd, emptyString);
	}
}
