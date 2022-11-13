package sysmlinjava.common;

import sysmlinjava.analysis.common.StackedProtocolObject;

/**
 * SysMLinJava's represention of the SysML signal. {@code SysMLSignal} is
 * abstract and must be the base class for all signal types used for
 * communicating energy, matter, or information between {@code SysMLFullPort}s.
 * The {@code SysMLSignal} also implements the {@code StackedProtocolObject}
 * interface which provides services for protocol objects in a protocol stack.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLSignal extends SysMLClass implements StackedProtocolObject
{
	/**
	 * Constructor
	 */
	public SysMLSignal()
	{
		super();
	}

	/**
	 * Constructor for signal name
	 * 
	 * @param name name of the signal
	 * @param id unique ID for the signal
	 */
	public SysMLSignal(String name, long id)
	{
		super(name, id);
	}

	/**
	 * Overridable operation that initializes the signal's requirements, if any. An example
	 * follows:
	 * 
	 * <pre>
	 * &#64;Requirement
	 * public SysMLRequirement myFirstRequirement;
	 * 
	 * &#64;Requirement
	 * public SysMLRequirement myNextRequirement;
	 * </pre>
	 * 
	 * <pre>
	 * myFirstRequirement = MySysMLRequirements.firstSysMLRequirement;
	 * myNextRequirement = MySysMLRequirements.nextSysMLRequirement;
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Requirement} annotation and
	 * {@code MySysMLRequirements.firstSysMLRequirement} is a reference to an
	 * instance of a {@code SysMLRequirement} in an extension/specialization of the
	 * {@code SysMLRequirements} class.
	 * <p>
	 * <b>Note</b> that signal requirements should be declared as described above,
	 * i.e. as references to instantiations of {@code SysMLRequirement}s declared
	 * and initialized in classes that extend/specialize the
	 * {@code SysMLRequirements} class. Requirements should <b>not</b> be
	 * declared/initialized in the signal itself. Requirement specifications in the
	 * typical SysML model are collected in a model package where they can be easily
	 * queried and managed. The SysMLinJava approach of collecting requirements in a
	 * single class emulates this method.
	 * 
	 * @see sysmlinjava.requirements.SysMLRequirements
	 */
	protected void createRequirements()
	{
	}

	/**
	 * Overridable operation that creates the dependencies for this signal. Overrides
	 * of this operation should perform initializations of variables annotated as
	 * dependencies. Whereas dependencies are types of association, each dependency
	 * should be defined by a field annotated as follows:
	 * 
	 * <pre>
	 * &#64;Dependency
	 * public APartBlock aPartDependency;
	 * </pre>
	 * 
	 * and initialized by an object reference For example:
	 * 
	 * <pre>
	 * aPartDependency = ((ParentBlock)contextBlock).bPartBlock;
	 * </pre>
	 * 
	 * where {@code aDependency} is the name of a variable that represents a
	 * dependency (trace, usage, etc} of this block on another model element. Note
	 * the assignment value should be a reference to another model element, and not
	 * to a "new" object (constructor).
	 */
	protected void createDependencies()
	{
	}

	@Override
	public String toString()
	{
		return String.format("SysMLSignal [name=%s, id=%s]", name, id);
	}

	/**
	 * Name of method to create requirements, used by SysMLinJava tools
	 */
	public static final String createRequirementsMethodName = "createRequirements";
	/**
	 * Name of method to create dependencies, used by SysMLinJava tools
	 */
	public static final String createDependenciesMethodName = "createDependencies";
}
