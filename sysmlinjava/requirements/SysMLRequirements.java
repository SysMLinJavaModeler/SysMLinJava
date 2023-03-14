package sysmlinjava.requirements;

import java.util.List;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava representation of a set of SysML requirements. The set is
 * declared as public static instances of {@code SysMLRequirement}s and are
 * optionally collected into a list of requirements and optionally linked via
 * their decomposition dependencies. An example of how requirements are declared
 * as static instances of {@code SysMLRequirement}s is as follows.
 * 
 * <pre>
 * 
	&#64;Requirement
	public static SysMLRequirement root = new SysMLRequirement("", "System ModemRadio", "System ModemRadio shall exhibit structure and behavior as specified", RequirementCategoryEnum.Capability, SysMLVerificationMethodKind.Test, SysMLRiskKind.Low);
	&#64;Requirement
	public static SysMLRequirement id1 = new SysMLRequirement("1", "System ModemRadio states/transitions", "System ModemRadio shall operate in accordance with specified states and transitions", RequirementCategoryEnum.StatesTransitions, SysMLVerificationMethodKind.Demonstration, SysMLRiskKind.Low);
	&#64;Requirement
	public static SysMLRequirement id1_1 = new SysMLRequirement("1.1", "System ModemRadio state", " System shall operate in accordance with specified states", RequirementCategoryEnum.StatesTransitions, SysMLVerificationMethodKind.Demonstration, SysMLRiskKind.Low);
	&#64;Requirement
	public static SysMLRequirement id1_1_1 = new SysMLRequirement("1.1.1", "System ModemRadio Initial state", "System ModemRadio shall operate in state Initial", RequirementCategoryEnum.StatesTransitions, SysMLVerificationMethodKind.Demonstration, SysMLRiskKind.Low);
 * </pre>
 * 
 * Specializations/extensions of this class must declare each of the
 * {@code SysMLRequirement}s in the set as a field variable. The type of the
 * field variable must be {@code SysMLRequirement} and the field should include
 * the &#64;{@code Requirement} annotation.
 * <p>
 * The list of requirements is created by the {@code createList()} method.
 * Specializations of {@code SysMLRequirements} should override this method to
 * add each of the {@code SysMLRequirement} instances to the list.
 * <p>
 * The decomposition dependencies are created by the
 * {@code createDecompostions()} method. Specializations of
 * {@code SysMLRequirements} should override this method to create the
 * dependencies.
 * <p>
 * Note that tools are commercially available that can automatically generate
 * specialized classes of the {@code SysMLRequirements}. Requirements can be
 * auto-generated from a SysMLinJava model and/or imported into the tool from
 * other requirement specification formats such as CSV, SQL, etc. These
 * auto-generated or imported requirements can then be formed into a
 * specialization of the {@code SysMLRequriements}. See SysMLinJava.com for
 * details.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLRequirements extends SysMLClass
{
	/**
	 * List of the {@code SysMLRequirement}s declared above. List is created by the
	 * {@code createList()} operation below.
	 */
	public List<SysMLRequirement> requirements;

	/**
	 * Constructor that invokes methods to create list of requirements and create
	 * decomposition dependencies.
	 */
	public SysMLRequirements()
	{
		super();
		createList();
		createDecomposedBys();
		createDecompositionOfs();
		createContainedBys();
		createContaineds();
		createSatisfiedBys();
		createCopyOfs();
		createCopiedBys();
		createRefinedBys();
		createTracedTos();
		createTracedFroms();
		createVerifiedBys();
		createDerivedBys();
		createDerivedFroms();
		createSpecializationOfs();
		createMasters();
		createSupportingInformationLinks();
	}

	/**
	 * Creates the {@code requirements} list of all the declared requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		requirements = Arrays.asList(
			root, id1, id1_1, id1_1_1, id1_1_2, id1_1_3, id1_1_4, id1_2, id1_2_1, id1_2_2,
			id1_2_3, id1_2_4, id1_2_5, id1_2_6, id2, id2_1, id2_1_1, id2_1_1_1, id2_1_2,
			id2_1_3, id2_2, id2_2_1, id2_2_1_1, id2_2_2, id2_2_3, id2_3, id2_3_1, id2_3_1_1,
			id2_3_2, id2_3_3, id2_4, id2_4_1, id2_4_1_1, id2_4_2, id2_4_3, id3, id3_1,
			id3_1_1, id3_1_2, id3_1_3);
	 * }
	 * </pre>
	 */
	protected void createList()
	{
	}

	/**
	 * Creates the SysML {@code <<decomposedBy>>} dependencies between requirements,
	 * e.g.
	 * 
	 * <pre>
	 * {@code
		root.decomposedBy.addAll(List.of(id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12));
		id1.decomposedBy.addAll(List.of(id1_1, id1_2));
		id1_1.decomposedBy.addAll(List.of(id1_1_1, id1_1_2, id1_1_3, id1_1_4));
		id1_2.decomposedBy.addAll(List.of(id1_2_1, id1_2_2, id1_2_3, id1_2_4, id1_2_5, id1_2_6));
		id2.decomposedBy.addAll(List.of(id2_1, id2_2, id2_3, id2_4));
		id2_1.decomposedBy.addAll(List.of(id2_1_1, id2_1_2, id2_1_3));
		etc.
	 * }
	 * </pre>
	 */
	protected void createDecomposedBys()
	{
	}

	/**
	 * Creates the SysML {@code <<decompositionOf>>} dependencies between requirements,
	 * e.g.
	 * 
	 * <pre>
	 * {@code
		root.decompositionOf.addAll(List.of(id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12));
		id1.decompositionOf.addAll(List.of(id1_1, id1_2));
		id1_1.decompositionOf.addAll(List.of(id1_1_1, id1_1_2, id1_1_3, id1_1_4));
		id1_2.decompositionOf.addAll(List.of(id1_2_1, id1_2_2, id1_2_3, id1_2_4, id1_2_5, id1_2_6));
		id2.decompositionOf.addAll(List.of(id2_1, id2_2, id2_3, id2_4));
		id2_1.decompositionOf.addAll(List.of(id2_1_1, id2_1_2, id2_1_3));
		etc.
	 * }
	 * </pre>
	 */
	protected void createDecompositionOfs()
	{
	}

	/**
	 * Creates the SysML {@code <<containedBy>>} dependencies between requirements,
	 * e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.containedBy = Optional.of(id1);
		id1_2.containedBy = Optional.of(id1);
		id2_1_2.containedBy = Optional.of(id2_1);
		etc.
	 * }
	 * </pre>
	 */
	protected void createContainedBys()
	{
	}

	/**
	 * Creates the SysML {@code <<copyOf>>} dependencies between requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.copyOf = Optional.of(id1);
		id1_2.copyOf = Optional.of(id1);
		id2_1_2.copyOf = Optional.of(id2_1);
		etc.
	 * }
	 * </pre>
	 */
	protected void createCopyOfs()
	{
	}

	/**
	 * Creates the SysML {@code <<containeds>>} dependencies between requirements,
	 * e.g.
	 * 
	 * <pre>
	 * {@code
		root.containeds.addAll(List.of(id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12));
		id1.containeds.addAll(List.of(id1_1, id1_2));
		id1_1.containeds.addAll(List.of(id1_1_1, id1_1_2, id1_1_3, id1_1_4));
		id1_2.containeds.addAll(List.of(id1_2_1, id1_2_2, id1_2_3, id1_2_4, id1_2_5, id1_2_6));
		id2.containeds.addAll(List.of(id2_1, id2_2, id2_3, id2_4));
		id2_1.containeds.addAll(List.of(id2_1_1, id2_1_2, id2_1_3));
		etc.
	 * }
	 * </pre>
	 */
	protected void createContaineds()
	{
	}

	/**
	 * Creates the SysML {@code <<satisfiedBy>>} dependencies of requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.satisfiedBy.addAll(List.of(AClass.class, BClass.class));
		id1_2.satisfiedBy.addAll(List.of(CClass.class));
		id2_1.satisfiedBy.addAll(List.of(DClass.class, EClass.class, FClass.class));
		etc.
	 * }
	 * </pre>
	 */
	protected void createSatisfiedBys()
	{
	}

	/**
	 * Creates the SysML {@code <<refinedBy>>} dependencies of requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.refinedBy.addAll(List.of(AClass.class, BClass.class));
		id1_2.refinedBy.addAll(List.of(CClass.class));
		id2_1.refinedBy.addAll(List.of(DClass.class, EClass.class, FClass.class));
		etc.
	 * }
	 * </pre>
	 */
	protected void createRefinedBys()
	{
	}

	/**
	 * Creates the SysML {@code <<tracedTo>>} dependencies of requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.tracedTo.addAll(List.of(AClass.class, BClass.class));
		id1_2.tracedTo.addAll(List.of(CClass.class));
		id2_1.traced.addAll(List.of(DClass.class, EClass.class, FClass.class));
		etc.
	 * }
	 * </pre>
	 */
	protected void createTracedTos()
	{
	}

	/**
	 * Creates the SysML {@code <<tracedFrom>>} dependencies of requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.tracedTo.addAll(List.of(AClass.class, BClass.class));
		id1_2.tracedTo.addAll(List.of(CClass.class));
		id2_1.traced.addAll(List.of(DClass.class, EClass.class, FClass.class));
		etc.
	 * }
	 * </pre>
	 */
	protected void createTracedFroms()
	{
	}

	/**
	 * Creates the SysML {@code <<verifiedBy>>} dependencies between requirements,
	 * e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.verifiedBy.addAll(List.of(ATestClass.class, BTestClass.class));
		id1_2.verifiedBy.addAll(List.of(CTestClass.class));
		id2_1.verifiedBy.addAll(List.of(DTestClass.class, ETestClass.class, FTestClass.class));
		etc.
	 * }
	 * </pre>
	 */
	protected void createVerifiedBys()
	{
	}

	/**
	 * Creates the SysML {@code <<copiedBy>>} dependencies between requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.copiedBy.addAll(List.of(id4_5_2));
		id1_2.copiedBy.addAll(List.of(id3_2_1, id2_1_3));
		id2_1.copiedBy.addAll(List.of(id#_2_1, id2_1_4));
		etc.
	 * }
	 * </pre>
	 */
	protected void createCopiedBys()
	{
	}

	/**
	 * Creates the SysML {@code <<derivedBy>>} dependencies between requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id1_1.derivedBy.addAll(List.of(id4_5_2));
		id1_2.derivedBy.addAll(List.of(id3_2_1, id2_1_3));
		id2_1.derivedBy.addAll(List.of(id#_2_1, id2_1_4));
		etc.
	 * }
	 * </pre>
	 */
	protected void createDerivedBys()
	{
	}

	/**
	 * Creates the SysML {@code <<derivedFrom>>} dependencies between requirements,
	 * e.g.
	 * 
	 * <pre>
	 * {@code
		id1.derivedFrom.addAll(List.of(root));
		id1_1.derivedFrom.addAll(List.of(id1));
		id1_2.derivedFrom.addAll(List.of(id1_1));
		id2.derivedFrom.addAll(List.of(root));
		id2_1.derivedFrom.addAll(List.of(id2));
		etc.
	 * }
	 * </pre>
	 */
	protected void createDerivedFroms()
	{
	}

	/**
	 * Creates the SysML {@code <<master>>} dependencies between requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id15_1_3.master = Optional.of(id1_2_3);
		id7_3_3_2.master = Optional.of(id3_1_2_3);
		etc.
	 * }
	 * </pre>
	 */
	protected void createMasters()
	{
	}

	/**
	 * Creates the SysML {@code <<specialization>>} dependencies between requirements,
	 * e.g.
	 * 
	 * <pre>
	 * {@code
		id5_2_3.specializationOf = Optional.of(id1_3);
		id12_2_4_1.specializationOf = Optional.of(id6_4_6);
	 }
	 * </pre>
	 */
	protected void createSpecializationOfs()
	{
	}

	/**
	 * Creates the SysML {@code <<supportingInformation>>} dependencies of
	 * requirements, e.g.
	 * 
	 * <pre>
	 * {@code
		id1.supportingInformationLinks.addAll(List.of(new SysMLHyperlink("SystemA Ops Manual", "file://Models/Docs/SystemAOpsManual.pdf")));
		id2_5_3.supportingInformationLinks.addAll(List.of(new SysMLHyperlink("ComponentA Parts List", "file://Models/Docs/ComponentA/BOM.xls")));
	 }
	 * </pre>
	 */
	protected void createSupportingInformationLinks()
	{
	}

	/**
	 * Name of method to create decomposedBys, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createDecomposedBysMethodName = "createDecomposedBys";
	/**
	 * Name of method to create decompositionOfs, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createDecompositionOfsMethodName = "createDecompositionOfs";
	/**
	 * Name of method to create containedBys, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createContainedBysMethodName = "createContainedBys";
	/**
	 * Name of method to create containeds, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createContainedsMethodName = "createContaineds";
	/**
	 * Name of method to create copiedBys, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createCopiedBysMethodName = "createCopiedBys";
	/**
	 * Name of method to create copyOfs, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createCopyOfsMethodName = "createCopyOfs";
	/**
	 * Name of method to create satisfiedBys, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createSatisfiedBysMethodName = "createSatisfiedBys";
	/**
	 * Name of method to create refinedBys, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createRefinedBysMethodName = "createRefinedBys";
	/**
	 * Name of method to create tracedTos, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createTracedTosMethodName = "createTracedTos";
	/**
	 * Name of method to create tracedFroms, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createTracedFromsMethodName = "createTracedFroms";
	/**
	 * Name of method to create verifiedBys, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createVerifiedBysMethodName = "createVerifiedBys";
	/**
	 * Name of method to create derivedBys, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createDerivedBysMethodName = "createDerivedBys";
	/**
	 * Name of method to create derivedFroms, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createDerivedFromsMethodName = "createDerivedFroms";
	/**
	 * Name of method to create masters, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createMastersMethodName = "createMasters";
	/**
	 * Name of method to create specializationsOfs, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createSpecializationOfsMethodName = "createSpecializationOfs";
	/**
	 * Name of method to create supportingInformationLinks, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createSupportingInformationLinksMethodName = "createSupportingInformationLinks";
}
