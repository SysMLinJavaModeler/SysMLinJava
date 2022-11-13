package sysmlinjava.constraintblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import sysmlinjava.annotations.Constraint;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.ObservableValue;
import sysmlinjava.common.SysMLConstraint;
import sysmlinjava.common.ValueObserver;
import sysmlinjava.ports.SysMLConstraintParameterPort;
import sysmlinjava.ports.SysMLConstraintParameterPortFunction;
import sysmlinjava.valuetypes.SysMLValueType;

/**
 * Abstract base class for all SysML ConstraintBlock representations.
 * <h2>SysML constraint block base class</h2> Extensions of the
 * {@code SysMLConstraintBlock} class declare fields and methods that represent
 * SysML constraint block properties and operations such as constraint
 * parameters, constraint parameter ports, and constraint parameter port
 * functions, as well as the properties and operations of the SysML block, from
 * which the SysML constraint block is extended.
 * <h3>ObservableValue and ValueObserver interfaces</h3> The
 * {@code SysMLConstraintBlock} is also an {@code ObservableValue} as well as a
 * {@code ValueObserver}. That is, the constraint parameters of the constraint
 * block may be observed by other block objects and/or the constraint block may
 * observe other block's values. In either case, the observed values can be
 * constraint parameters of the current or other constraint blocks.
 * <h3>Constraint Parameters</h3> As in the SysML constraint block, the
 * {@code SysMLConstraintBlock} includes constraint parameters. These parameters
 * correspond to the variables used in the constraints specified in the
 * constraint block. Constraint parameters fields that are instances of
 * {@code SysMLValueTypes} and annotated by the ConstraintParameter annotation.
 * <h3>Constraint Parameter Ports</h3> Constraint parameters may be "bound" to
 * values in other blocks by the {@code SysMLConstraintParameterPort}. The
 * {@code SysMLConstraintParameterPort} serves as the bound reference end used
 * in the SysML parametric diagram to "bind" constraint parameters to values of
 * other blocks or constraint blocks. These parameter ports are specified by
 * fields of the {@code SysMLConstraintParameterPort} type and are annotated
 * with the {@code ConstraintParameterPort} annotation and created in the
 * {@code createParameterPort()} operation.
 * <h3>Multi-Threaded Parametric Analyses</h3> The {@code SysMLConstraintBlock}
 * is capable of performing multi-threaded parametric analysis during an actual
 * model execution or simulation. This capability is typically not available by
 * conventional diagram-based SysML tools. However, in SysMLinJava, the
 * constraint block can leverage the multi-threaded features of the inherited
 * constraint block and of the {@code SysMLConstraintParameterPort} to input
 * parameters that reside in different threads of execution to "bind" values in
 * other blocks to the constraint block's parameters in a thread-safe manner.
 * The default configuration of the {@code SysMLConstraintBlock} is to support
 * asynchronous constraint blocks in multi-threaded block models. If a fully
 * synchronous (single threaded) model is used, however, then the
 * {@code createStateMachine} operation can be overridden to set the
 * {@code stateMachine = Optional.empty()} if desired.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLConstraintBlock extends SysMLBlock implements ObservableValue, ValueObserver
{
	/**
	 * Collection of parameter ports that are connected to the bound parameters to
	 * obtain their latest values. Map keys are the paramIDs for the ports and the
	 * map values are the ports. The {@code paramPorts} must be
	 * created/initialized/put into the map in an override of the
	 * {@code createConstraintParameterPorts()} operation.
	 */
	public Map<String, SysMLConstraintParameterPort> paramPorts;

	/**
	 * Map collection of parameter port functions that actually access the bound
	 * parameters. Map keys are the paramIDs for the ports to which the function is
	 * to be assigned and the map values are the port functions. The
	 * {@code paramPortFunctions} must be created/initialized/put into the map in an
	 * override of the {@code createConstraintParameterPortFunctions()} operation
	 */
	public Map<String, SysMLConstraintParameterPortFunction> paramPortFunctions;

	/**
	 * Map of the constraint parameter IDs-to-Values. Constraint parameters are
	 * updated in the map when their "bound" values changes and can be retrieved
	 * from the map using the parameter IDs. All constraint params are retrieved
	 * from the parameter ports and stored in the map as basic
	 * {@code SysMLValueType}s. Therefore, parameter values retrieved from the map
	 * should (must) be cast to the correct type needed for operations on the
	 * parameters, e.g in constraint calculations. Note that the constraint
	 * parameters could be stored to/retrieved from individual instance variables in
	 * an extended constraint block instead of as the mapped variables provided by
	 * this base class. However, if stored as individual instance variables, the
	 * extended constraint block must override and implement the
	 * {@code retrieveParameters()} operation to store the retrieved parameters into
	 * these alternate variable values , because the default operation retrieves the
	 * parameters into this map.
	 */
	public Map<String, SysMLValueType> constraintParams;

	/**
	 * Constraint function that calculates the constraint parameters. The constraint
	 * function is invoked everytime a parameter port obtains a new value of a bound
	 * parameter and updates one or more of the {@code constraintPrams} accordingly.
	 */
	@Constraint
	public SysMLConstraint constraint;
	/**
	 * Optional identification of the current (last one updated) parameter. This
	 * identifier is updated for every parameter retrieval from a parameter port and
	 * can be used by the {@code retrieveParameters()} and
	 * {@code performConstraints()} operation to identify the currently changed
	 * parameter.
	 */
	public Optional<String> currentParamID;

	/**
	 * Optional identification of the previous (last one before current) parameter.
	 * This identifier is updated for every parameter retrieval from a parameter
	 * port and can be used by the {@code retrieveParameters()} and
	 * {@code performConstraints()} operation to identify the previously changed
	 * parameter (to perhaps reset objects that are dependent on its changes, etc.)
	 */
	public Optional<String> previousParamID;

	/**
	 * Value of current/latest parameter that has been updated/changed and as
	 * identified by the {@code currentParamID}
	 */
	public SysMLValueType currentParam;

	/**
	 * Value of previous/last parameter that has been updated/changed and as
	 * identified by the {@code previousParamID}
	 */
	public SysMLValueType previousParam;

	/**
	 * List of {@code ValueObserver}s of this constraint block that are to be
	 * notified of some event that takes place in the block. Note this
	 * implementation of the {@code ObservableValue} is in addition to the
	 * {@code ValueObserver}s that may have been added to the constraint parameters
	 * of the {@code SysMLValueType} type, which also implements the
	 * {@code ObservableValue} interface.
	 */
	public List<ValueObserver> valueObservers;

	/**
	 * Optional value of a "parent" constraint block, i.e. a
	 * {@code SysMLConstraintBlock} that is above this one in a hierarchy of
	 * constraint blocks. The parent constraint block may be a {@code ValueObserver}
	 * to this constraint block's constraint parameters and or to the constraint
	 * block itself if it needs to react to changes to this constraint block.
	 */
	public Optional<? extends SysMLConstraintBlock> parent;

	/**
	 * Constructor
	 * 
	 * @param parent Optional parent constraint block in the hierarchy (if any) of
	 *               constraint blocks.
	 * @param name   Unique name of the constraint block.
	 */
	public SysMLConstraintBlock(Optional<? extends SysMLConstraintBlock> parent, String name)
	{
		super();
		this.parent = parent;
		this.name = Optional.of(name);
		createConstraintParameters();
		createConstraintParameterPortFunctions();
		createConstraintParameterPorts();
	}

	@Override
	public void addValueChangeObserver(ValueObserver observer)
	{
		valueObservers.add(observer);
	}

	/**
	 * Reacts to notification by a bound parameter (which is an
	 * {@code ObservableValue}) that the observed parameter value has changed. This
	 * emulates the SysML's "binding connector" for a constraint parameter.
	 * 
	 * @param paramID unique ID of the constraint parameter whose value changed.
	 */
	public synchronized void valueChanged(String paramID)
	{
		if (stateMachine.isPresent())
		{
			SysMLParameterChangeEvent changeEvent = new SysMLParameterChangeEvent();
			changeEvent.changeExpression = paramID;
			acceptEvent(changeEvent);
		}
		else
		{
			onParameterChange(paramID);
			performConstraints();
			notifyValueChangeObservers();
		}
	}

	/**
	 * Reacts to notification by a bound parameter (which is an
	 * {@code ObservableValue}) that the observed parameter value has changed. This
	 * emulates the SysML's "binding connector" for a constraint parameter.
	 */
	@Override
	public void valueChanged()
	{
		valueChanged("");
	}

	@Override
	public void notifyValueChangeObservers()
	{
		valueObservers.forEach(valueChangeObserver -> valueChangeObserver.valueChanged());
	}

	/**
	 * Overridable operation that responds to parameter change, i.e. retrieves the
	 * changed constraint parameter from the binding
	 * {@code SysMLConstraintParameterPort} and stores it in the corresponding
	 * constraint parameter(s). By default, the parameters are retrieved/stored to
	 * make the latest constraint parameter values available for performance of any
	 * constraints by the specified {@code performConstraints()} operation.
	 * 
	 * @param paramID unique ID of the constraint parameter that was changed/updated
	 */
	protected void onParameterChange(String paramID)
	{
		if (!paramID.isEmpty())
		{
			previousParamID = currentParamID;
			currentParamID = Optional.of(paramID);
			SysMLConstraintParameterPort paramPort = paramPorts.get(currentParamID.get());
			if (paramPort != null)
			{
				if (currentParam != null)
					previousParam = currentParam;
				SysMLValueType boundParam = paramPort.getValue();
				if (boundParam != null)
				{
					currentParam = boundParam;
					constraintParams.put(currentParamID.get(), currentParam);
				}
				else
					logger.severe("bound parameter value not retrieved from its bound parameter port: " + paramID);
			}
			else
				logger.severe("bound parameter port not found for parameter: " + paramID);
		}
		else
			currentParamID = Optional.empty();
	}

	/**
	 * Overridable operation to handle occurrence of a time event. This operation
	 * will be invoked only if a timer was specified for the state machine created
	 * in the {@code createStateMachine()} operation.
	 */
	protected void onTimeEvent()
	{
		logger.warning("time event not handled, may need to override operation to handle event");
	}

	/**
	 * Abstract operation that, when overridden/implemented performs (executes) the
	 * constraints specified by any {@code SysMLConstraint} variables. The
	 * constraints should retrieve the constraint parameter values from the
	 * {@code constraintParamValue} map using the {@code currentParamID} to retrieve
	 * the currently changed parameter value. An exampe follows:
	 * 
	 * <pre>
	 * {@code
	 * {
	 * 	constraint.calculate();
	 * 	additionalConstraint.calculate();
	 * }
	 * }
	 * </pre>
	 */
	protected abstract void performConstraints();

	@Override
	protected void preCreate()
	{
		paramPorts = new HashMap<>();
		paramPortFunctions = new HashMap<>();
		constraintParams = new HashMap<>();
		constraint = null;
		currentParam = null;
		previousParam = null;
		currentParamID = Optional.empty();
		previousParamID = Optional.empty();
		valueObservers = new ArrayList<>();
	}

	/**
	 * Overridable operation that instantiates the constraint block state machine,
	 * if any, for this constraint block. By default, the
	 * {@code SysMLConstraintBlock} creates its state machine to be an instance of
	 * the {@code SysMLConstraintBlockStateMachine}. The
	 * {@code SysMLConstraintBlockStateMachine} is designed to handle
	 * {@code SysMLParameterChangeEvent}s produced by
	 * {@code SysMLConstraintParameterPort}s whose bound parameters are updated in
	 * thread(s) different from the thread of the constraints of the constraint
	 * block. It handles the events in accordance with SysML standard constraint
	 * block operation.<br>
	 * However, if a different state machine is needed for the
	 * {@code SysMLConstraintBlock}, this {@code createStateMachine()} operation may
	 * be overridden to assign another type of {@code SysMLStateMachine} to the
	 * constraint block's {@code stateMachine}, or to assign none at all, i.e.
	 * {@code stateMachine = Optional.empty()}. If another type of
	 * {@code SysMLStateMachine} is needed, it can be installed by assigning the
	 * {@code stateMachine} variable with the instance of the custom state machine
	 * in the overriding operation. If no state machine is needed, then the
	 * overriding operation need only be empty.
	 */
	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new SysMLConstraintBlockStateMachine(this, this.getClass().getSimpleName()));
	}

	/**
	 * Overridable operation that should create the constraint block's
	 * {@code constraint}. The {@code constraint} should perform whatever operations
	 * are necessary to produce the output of the constraint block, e.g. perform a
	 * number of calculations, develop display data, invoke assertions, etc. An
	 * example follows:
	 * 
	 * <pre>
	 * {@code
	 * constraint = () ->
	 * {
	 * 	if (currentParamID.isPresent() && currentParamID.get().equals("paramA"))
	 * 	{
	 * 		paramA = constraintParams.get("paramA");
	 * 		paramB = constraintParams.get("paramB");
	 * 		assert paramA.equals(paramB) : "paramA != paramB";
	 * 		paramC.setValue(paramA.betaVal);
	 * 	}
	 * };
	 * }
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the {@code constraint}
	 * variable in the constraint block and the value of the assignment is a lambda
	 * expression for the constraint that uses the constraint parameters.
	 * <p>
	 * Whereas the constraint parameters are of the SysMLValueType type, use of the
	 * {@code setValue(<value>)} operation to set the value of the parameter will
	 * automatically "bind" the new value of the parameter to any bound connections,
	 * i.e. the method will notify any {@code ValueObserver}s of the parameter of
	 * the change in value of the parameter.
	 * 
	 * @see SysMLConstraint
	 */
	protected abstract void createConstraints();

	/**
	 * Overridable abstract operation that should create the constraint block's
	 * constraint parameters by "put"-ing initialized instances of each into the
	 * {@code constraintParams} map with their appropriate "key"s, i.e. their
	 * parameter IDs. An example follows:
	 * 
	 * <pre>
	 * {@code
		paramA = new SysMLValueTypeA(<i>initializer</i>);
		paramB = new SysMLValueTypeB(<i>initializer</i>);
		paramC = new SysMLValueTypeC(<i>initializer</i>);
	 	
		constraintParams.put("paramA", paramA));
		constraintParams.put("paramB", paramB);
		constraintParams.put("paramC", paramC);
	 * }
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code ConstraintParameter} annotation and {@code SysMLValueTypeX} is a
	 * constructor of any {@code SysMLValueType} class. The constraint parameter
	 * must be of a {@code SysMLValueType} type.
	 * 
	 * @see sysmlinjava.valuetypes.SysMLValueType
	 */
	protected abstract void createConstraintParameters();

	/**
	 * Overridable abstract operation that should create the constraint block's
	 * parameter port functions, i.e. the code (a lambda expression) that emulates
	 * the "binding connector" that is provided by the constraint parameter port.
	 * The function should:
	 * <ul>
	 * <li>retrieve the bound parameter value from the contextBlock (the block that
	 * has the value corresponding to the "bound" parameter)
	 * <li>update the parameter value in the constraint block via the
	 * {@code constraintParameterPort}
	 * </ul>
	 * An example follows:
	 * 
	 * <pre>
	 * {@code
	 * {
	 * 	paramABindingFunction = (constraintParameterPort, contextBlock) ->
	 * 	{
	 * 		ParamTypeA paramA = ((BlockTypeA)contextBlock).paramA;
	 * 		constraintParameterPort.updateParameterValue(new ParamTypeA(paramA));
	 * 	};
	 * 
	 * 	paramBBindingFunction = (constraintParameterPort, contextBlock) ->
	 * 	{
	 * 		ParamTypeB paramB = ((BlockTypeB)contextBlock).paramB;
	 * 		constraintParameterPort.queuedParameterValues.put(new ParamTypeB(paramB));
	 * 		constraintParameterPort.constraintBlock.valueChanged(paramBID);
	 * 	};
	 * 
	 * 	paramPortFunctions.put("paramA", paramABindingFunction);
	 * 	paramPortFunctions.put("paramB", paramABindingFunction);
	 * }
	 * }
	 * </pre>
	 * 
	 * @see sysmlinjava.ports.SysMLConstraintParameterPortFunction
	 * @see sysmlinjava.ports.SysMLConstraintParameterPort
	 */
	protected abstract void createConstraintParameterPortFunctions();

	/**
	 * Overridable abstract operation that should create the constraint block's
	 * constraint parameter ports. An example follows:
	 * 
	 * <pre>
	 * {@code
	 * {
	 * 	paramAPort = new SysMLConstraintParameterPort(this, paramABindingFunction, "paramA");
	 * 	paramBPort = new SysMLConstraintParameterPort(this, paramBBindingFunction, "paramB");
	 * 
	 * 	paramPorts.put("paramA", paramAPort);
	 * 	paramPorts.put("paramB", paramBPort);
	 * }
	 * }
	 * </pre>
	 * 
	 * The example shows the parameter ports being added to the map of parameter
	 * ports. This map is used by the default {@code retrieveParameters()} operation
	 * to identify the parameter port that retrieves the current parameter whose
	 * value has changed. If an extension of the {@code SysMLConstraintBlock}
	 * overrides the {@code retrieveParameters()} operation to retrieve the
	 * parameters from the parameter ports in a way that doesn't require the
	 * parameter ports map, then these map calls may not be needed.
	 * 
	 * @see sysmlinjava.ports.SysMLConstraintParameterPortFunction
	 * @see sysmlinjava.ports.SysMLConstraintParameterPort
	 */
	protected abstract void createConstraintParameterPorts();

	/**
	 * Name of attribute for the constraint variable, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String constraintVariableName = "constraint";
	/**
	 * Name of method to create the constraints, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintsMethodName = "createConstraints";
	/**
	 * Name of method to create the constraint parameters, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createConstraintParametersMethodName = "createConstraintParameters";
	/**
	 * Name of method to create the constraint parameter ports, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createConstraintParameterPortsMethodName = "createConstraintParameterPorts";
	/**
	 * Name of method to create the constraint parameter port functions, used by
	 * SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createConstraintParameterPortFunctionsMethodName = "createConstraintParameterPortFunctions";
	/**
	 * Name of method to create the binding connector functions, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createBindingConnectorFunctionsMethodName = "createBindingConnectorFunctions";
	/**
	 * Name of method to create the binding connectors, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createBindingConnectorsMethodName = "createBindingConnectors";
}
