package sysmlinjava.valuetypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sysmlinjava.common.ObservableValue;
import sysmlinjava.common.ValueObserver;
import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLUnit;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava's representation of the SysML ValueType. The
 * {@code SysMLValueType} is an abstract class that serves as the base class for
 * specialized value types. It extends the {@code SysMLClass} with a
 * {@code SysMLUnit}, a {@code SysMLProbabilityDistribution} (as prescribed by
 * SysML), and the functions of an {@code ObservableValue}.
 * 
 * The SysMLinJava implementation of the {@code ObservableValue} interface by
 * the {@code SysMLValueType} makes the ValueType "observable", i.e. it can
 * notify (call) associated {@code ValueObserver} objects if/when the value of
 * the {@code SysMLValueType} changes. This abstract class essentially is the
 * SysMLinJava application of the "Observable/Observer" pattern to achieve the
 * "Bound Reference" (binding connector) that is used by the parameter port of
 * the SysML ConstraintBlock and by other SysML model elements to bind
 * valueTypes. This feature of the {@code SysMLValueType} can also be used for
 * other operations in which the "Observable/Observer" pattern might be needed.
 * <p>
 * Note that the SysML specification calls for inclusion of SysML's quantityKind
 * in the properties of the valueType. It also calls for the inclusion of the
 * SysML unit in the valueType as well. Since the unit contains the
 * quantityKind, the SysMLinJava implementation does not include the
 * quantityKind in the valueType so as to prevent duplication of information.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.connectors.SysMLBindingConnector
 * @see sysmlinjava.constraintblocks.SysMLConstraintBlock
 * @see sysmlinjava.ports.SysMLConstraintParameterPort
 */
public abstract class SysMLValueType extends SysMLClass implements ObservableValue
{
	/**
	 * The units for this value type. Must be set via abstract method
	 * {@code createUnits()}
	 * 
	 */
	public SysMLUnit units;
	/**
	 * List of {@code ValueObserver}s to be notified if/when this
	 * {@code SysMLValueType} object's value changes. Note that extension classes of
	 * the {@code SysMLValueType} that need to use the {@code ObservableValue}
	 * interface and this list of {@code ValueObservers} must implement a
	 * "{@code setValue(<value>)} operation that, in addition to setting the value
	 * type's value, also invokes the {@code notifyValueChangeObservers()} operation
	 * in order to notify observing objects of the change.
	 */
	public List<ValueObserver> observers;
	/**
	 * Optional probability distribution of the valueType. Optionally set via method
	 * {@code createProbabilityDistribution()}
	 * 
	 */
	public Optional<SysMLProbabilityDistribution> probabilityDistribution;

	/**
	 * Constructor
	 */
	public SysMLValueType()
	{
		super();
		observers = new ArrayList<>();

		createUnits();
		createProbabilityDistribution();
		createRequirements();
		createDependencies();
	}

	/**
	 * Constructor for deep copy
	 * 
	 * @param copied instance which is to be copied
	 */
	public SysMLValueType(SysMLValueType copied)
	{
		super(copied);
		units = copied.units;
		observers = new ArrayList<>(copied.observers);
		probabilityDistribution = copied.probabilityDistribution;

		createUnits();
		createRequirements();
		createDependencies();
	}

	/**
	 * Constructor for value type whose values are random
	 * 
	 * @param distribution probability distribution of the value type
	 */
	public SysMLValueType(SysMLProbabilityDistribution distribution)
	{
		super();
		observers = new ArrayList<>();
		probabilityDistribution = Optional.of(distribution);

		createUnits();
		createRequirements();
		createDependencies();
	}

	/**
	 * Binds this value to the specified value observer where binding consists of
	 * adding the observer to the value's set of observers and notifying the
	 * observers of a change thereby providing the observer an opportunity to
	 * initialize its value with this value, thereby initializing the "binding".
	 * <p>
	 * This {@code bindTo()} operation can be used to create "binding" connectors of
	 * values in blocks to constraint parameters in constraint blocks. Specifically,
	 * it can (should) be used in {@code SysMLBindingConnectorFunction}s to complete
	 * the bound connection when invoked by the {@code SysMLBindingConnector}.
	 * 
	 * @see sysmlinjava.connectors.SysMLBindingConnectorFunction
	 * @see sysmlinjava.connectors.SysMLBindingConnector
	 * 
	 * @param observer the observer of this value that is to be bound to this value
	 */
	public void bindTo(ValueObserver observer)
	{
		addValueChangeObserver(observer);
		notifyValueChangeObservers();
	}

	@Override
	public void addValueChangeObserver(ValueObserver observer)
	{
		observers.add(observer);
	}

	@Override
	public void notifyValueChangeObservers()
	{
		observers.forEach(observer ->
		{
			observer.valueChanged();
		});
	}

	/**
	 * Required operation to create/assign the units attribute for this value type.
	 * The units variable should be assigned with the name of a {@code SysMLUnit}
	 * instance located in an extension of the {@code SysMLUnits} class. An example
	 * is as follows:
	 * 
	 * <pre>
	 * &#64;Override
	 * protected void createUnits()
	 * {
	 * 	units = SysMLinJavaUnits.FeetCubic;
	 * }
	 * </pre>
	 * 
	 * @see sysmlinjava.units.SysMLinJavaUnits
	 */
	protected abstract void createUnits();

	/**
	 * Overridable operation to create the probability distribution of the values of
	 * this valueType.
	 */
	protected void createProbabilityDistribution()
	{
		probabilityDistribution = Optional.empty();
	}

	@Override
	protected void createAttributes()
	{
	}

	/**
	 * Overridable operation that initializes the value type's requirements, if any.
	 * An example follows:
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
	 * <b>Note</b> that value type requirements should be declared as described
	 * above, i.e. as references to instantiations of {@code SysMLRequirement}s
	 * declared and initialized in classes that extend/specialize the
	 * {@code SysMLRequirements} class. Requirements should <b>not</b> be
	 * declared/initialized in the value type itself. Requirement specifications in
	 * the typical SysML model are collected in a model package where they can be
	 * easily queried and managed. The SysMLinJava approach of collecting
	 * requirements in a single class emulates this method.
	 * 
	 * @see sysmlinjava.requirements.SysMLRequirements
	 */
	protected void createRequirements()
	{
	}

	/**
	 * Overridable operation that creates the dependencies for this value type.
	 * Overrides of this operation should perform initializations of variables
	 * annotated as dependencies. Whereas dependencies are types of association,
	 * each dependency should be defined by a field annotated as follows:
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

	/**
	 * Name of field that contains the probability distribution of the value type,
	 * used by SyMLinJava tools
	 */
	public static final String probabilityDistributionFieldName = "probabilityDistribution";

	/**
	 * Name of field that contains the units of the value type, used by SyMLinJava
	 * tools
	 */
	public static final String unitsFieldName = "units";

	/**
	 * Name of method to create the probability distribution of the value type, used
	 * by SyMLinJava tools
	 */
	public static final String createProbabilityDistributionMethodName = "createProbabilityDistribution";

	/**
	 * Name of method to create (assign) units to the value type, used by SyMLinJava
	 * tools
	 */
	public static final String createUnitsMethodName = "createUnits";
	/**
	 * Name of method to create requirements, used by SysMLinJava tools
	 */
	public static final String createRequirementsMethodName = "createRequirements";
	/**
	 * Name of method to create dependencies, used by SysMLinJava tools
	 */
	public static final String createDependenciesMethodName = "createDependencies";
}
