/*
 * Copyright 2021 SysMLinJava.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.blocks;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import sysmlinjava.annotations.statemachines.StateMachine;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.statemachine.SysMLStateMachine;

/**
 * SysMLinJava's represention of the SysML block
 * <h2>The block in SysMLinJava</h2>{@code SysMLBlock} is an abstract class that
 * provides a base class for for all SysML block representations. Extensions of
 * the {@code SysMLBlock} class declare fields and methods that represent SysML
 * block properties and operations such as values, flows, references, parts,
 * ports, constraints, and constraint blocks as well as operations and
 * receptions. They also declare fields that represent connectors of the block's
 * parts and ports.
 * <h3>Optional behavioral state machine</h3> The {@code SysMLBlock} also
 * contains an optional state machine. This state machine is the SysMLinJava
 * representation of the state machine/chart/diagram in standard SysML. If
 * declared, it is defined in terms of a class that extends the
 * {@code SysMLStateMachine} abstract base class. This extension class defines
 * the state machine in terms of associated SysMLinJava classes for states,
 * transitions, guards, effects, events, etc. The {@code SysMLStateMachine} also
 * contains a {@code Runnable} that is optionally executed in a concurrent
 * execution thread. Use of this runnable allows the state machine to execute
 * asynchronously from other model elements therefore enabling more precise
 * modeling through multi-threaded model executions.
 * <p>
 * The {@code SysMLBlock}'s thread-based state machine execution begins with
 * invocation of the block's {@code start()} operation. This operation starts
 * the state machine runnable in its own thread while the block's {@code stop()}
 * method stops the state machine thread. The
 * {@code acceptEvent(SysMLEvent event)} method enqueues the specified event to
 * the state machine for action as specified by the declared
 * {@code SysMLStateMachine}.
 * <h3>Optional parent context block</h3> The {@code SysMLBlock} base class also
 * provides for an optional context block property which may be used by declared
 * operations and activities to access the properties and behaviors of a
 * <i>parent</i> block.
 * <h3>Thread pool</h3> Also included in this base class for the block is a
 * {@code ScheduledThreadPoolExecutor}. This concurrency object is used to "run"
 * the declared {@code SysMLStateMachine} instance, but it also provides a
 * thread pool for controlled execution of any other concurrent threads of
 * execution that might be needed by specializations of the {@code SysMLBlock}.
 * Use of the thread pool further enhances SysMLinJava's support for more
 * precise modeling through multi-threaded model executions. *
 * <h3>Create/initialize methods</h3> Finally, the {@code SysMLBlock} provides a
 * series of overrideable method calls to create all the properties (the java
 * fields and methods) of the represented SysML block (a java class). The
 * {@code SysMLBlock} constructor automatically invokes methods to
 * create/initialize the values, flows, references, parts, ports, constraints,
 * comments, requirements, etc - any SysML type that can be declared in a SysML
 * block. This automatice invocation of methods allows extensions to the
 * {@code SysMLBlock} to simply override the {@code createXxxx()} methods for
 * the applicable properties (fields) declared in the block (class) and perform
 * the creations/initializations in the methods. This ensures the properties are
 * created/initialized in the correct and complete sequence needed. The
 * overridable {@code createXxxx()} methods provide a "reminder" to create and
 * initialize the block's properties as well as a framework for their complete
 * definition.
 * <p>
 * A simplified example of code for a block model follows:
 * 
 * <pre>
	public class ElectricMotor extends SysMLBlock
	{
		&#64;FullPort
		public ElectricalPowerReceivePort electricalLine;
		&#64;FullPort
		public MotorTorqueTransmitPort wheelMotorDisc;
		&#64;FullPort
		public MechanicalForceTransmitPort suspensionMount;
	
		&#64;Flow
		public PowerWatts electricalPowerIn;
		&#64;Flow
		public TorqueNewtonMeters mechanicalTorqueOut;
		&#64;Flow
		public ForceNewtons weightOnSuspensionOut;
	
		&#64;Value
		public TorqueNewtonMetersPerKilowatt torqueNmPerKw;
		&#64;Value
		public RevolutionsPerMinute revolutionsPerMinute;
		&#64;Value
		public Percent motorEfficiency;
	
		&#64;Constraint
		public SysMLConstraint mechanicalTorqueCalculation;
	
		public ElectricMotor(MotorInWheelSystem motorInWheelSystem, String name, Long id)
		{
			super(motorInWheelSystem, name, id);
		}
	
		&#64;Operation
		public void transmitWeight()
		{
			suspensionMount.transmit(weightOnSuspensionOut);
		}
	
		&#64;Reception
		public void onElectricalPower(PowerWatts powerWatts)
		{
			logger.info(powerWatts.toString());
			electricalPowerIn.setValue(powerWatts.value);
			mechanicalTorqueCalculation.apply();
			wheelMotorDisc.transmit(mechanicalTorqueOut);
		}
	
		&#64;Override
		protected void createStateMachine()
		{
			stateMachine = Optional.of(new ElectricMotorStateMachine(this));
		}
	
		&#64;Override
		protected void createValues()
		{
			torqueNmPerKw = new TorqueNewtonMetersPerKilowatt(5);
			revolutionsPerMinute = new RevolutionsPerMinute(240);
			motorEfficiency = new Percent(95);
		}
	
		&#64;Override
		protected void createFlows()
		{
			electricalPowerIn = new PowerWatts(0);
			mechanicalTorqueOut = new TorqueNewtonMeters(0);
			weightOnSuspensionOut = new ForceNewtons(25, Math.toRadians(180));
		}
	
		&#64;Override
		protected void createFullPorts()
		{
			electricalLine = new ElectricalPowerReceivePort(this, this, 0);
			wheelMotorDisc = new MotorTorqueTransmitPort(this, 0);
			suspensionMount = new MechanicalForceTransmitPort(this, 0);
		}
	
		&#64;Override
		protected void createConstraints()
		{
			mechanicalTorqueCalculation = () ->
			{
				mechanicalTorqueOut.value = torqueNmPerKw.value(electricalPowerIn.value / 1000)motorEfficiency.asFraction(); // (60 / (2Math.PI))(electricalPowerIn.value /
																																	 // revolutionsPerMinute.value) //(motorEfficiency.value / 100);
			};
		}
	}
 * </pre>
 * 
 * @see sysmlinjava.statemachine.SysMLStateMachine
 * @see java.util.concurrent.ScheduledThreadPoolExecutor
 * @see java.lang.Runnable
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLBlock extends SysMLClass
{
	/**
	 * Optional SysML state machine for this SysML block. The
	 * {@code SysMLStateMachine} may be declared as "asynchronous" in which case
	 * it's {@code Runnable} will be automatically executed in one of the threads
	 * provided by the {@code concurrentExecutionThreads} declared below. Note the
	 * {@code StateMachine} annotation is used by SysMLinJava tools to identify this
	 * field as the block's state machine declaration.
	 */
	@StateMachine
	public Optional<? extends SysMLStateMachine> stateMachine;

	/**
	 * Optional SysML block that serves as the block "context" in which this block
	 * resides. The contextBlock may be used by other block properties, such as
	 * operations and parts, for access to parent block properties.
	 */
	public Optional<? extends SysMLBlock> contextBlock;

	/**
	 * Instance of the Java API's {@code ScheduledThreadPoolExecutor} used to "run"
	 * the optional state machine's {@code Runnable}. It is also available to
	 * {@code SysMLBlock} extensions to execute other threads of execution that
	 * might be declared for the block.
	 */
	public ScheduledThreadPoolExecutor concurrentExecutionThreads;

	/**
	 * Constructor initialized with no state machine, no context block, default name
	 * and ID
	 */
	public SysMLBlock()
	{
		super();
		stateMachine = Optional.empty();
		contextBlock = Optional.empty();
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		preCreate();
		createProperties();
	}

	/**
	 * Constructor initialized with no state machine, no context block, specified
	 * name and ID
	 * 
	 * @param name name to be associated with the block
	 * @param id   unique identifier for this block
	 */
	public SysMLBlock(String name, Long id)
	{
		super(name, id);
		stateMachine = Optional.empty();
		this.contextBlock = Optional.empty();
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		preCreate();
		createProperties();
	}

	/**
	 * Constructor initialized with no state machine, specified context block, name
	 * and ID
	 * 
	 * @param contextBlock the {@code SysMLBlock} in whose context this
	 *                     {@code SysMLBlock} is to operate, i.e. optional parent
	 *                     block
	 * @param name         name to be associated with the block
	 * @param id           unique long integer identifier for this block
	 */
	public SysMLBlock(SysMLBlock contextBlock, String name, Long id)
	{
		super(name, id);
		stateMachine = Optional.empty();
		this.contextBlock = Optional.of(contextBlock);
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		preCreate();
		createProperties();
	}

	/**
	 * Creates/initializes properties as necessary prior to their
	 * creation/initialization during the {@code createProperties} operation.
	 * Creations/initializations typically include collection creation prior to
	 * creation of collection members. An example {@code preCreate()} operation for
	 * some example properties is as follows.
	 * 
	 * <pre>
	 * {@code
	 * List<ProtocolPort> commsPorts;
	 * List<Point2D> mountPoints;
	 * 
	 * &#64;Override
	 * protected void preCreate()
	 * {
	 * 	commsPorts = new LinkedList<>();
	 * 	mountPoints = new ArrayList<>();
	 * }
	 * }
	 * </pre>
	 */
	protected void preCreate()
	{
	}

	/**
	 * Creates/initializes the properties of a {@code SysMLBlock} that are declared
	 * by the fields in classes that extend the {@code SysMLBlock} class. Property
	 * creation/initialization is performed by overriding and implementing any or
	 * all of the operations invoked by this operation in accordance with the
	 * specification of the operations below.
	 * <p>
	 * <b>Note:</b> Usage of these creation operations to create/initialize the
	 * block properties (fields) promotes standardized creation of block properties
	 * and ensures their correct sequence of construction - all in preparation for
	 * correct model execution and for use by SysMLinJava tools to correctly
	 * interpret the Java code as a SysML model. Modelers should ensure that object
	 * creations are compatible and consistent with the sequence of "create...()"
	 * calls invoked by this operation. If a different sequence of model
	 * creation/initialization is required, then this operation may be overridden
	 * and used to invoke a custom creation/initialization of the block's
	 * properties. It should be noted, hwever, that SysMLinJava tools use the
	 * specified {@code create...()} operations to perform their tasks, so using any
	 * different named operations may void the capabilities of these tools.
	 */
	protected void createProperties()
	{
		createClasses();
		createValues();
		createFlows();
		createSignals();
		createEvents();
		createParts();
		createFullPorts();
		createProxyPorts();
		createActivities();
		createConstraints();
		createStateMachine();
		createRequirements();
		createConstraintBlocks();
		createReferences();
		createConstraintParameterConnectorFunctions();
		createConstraintParameterConnectors();
		createConnectorFunctions();
		createConnectors();
		createDependencies();
		createElementGroups();
		enableInteractionMessageTransmissions();
	}

	/**
	 * Starts the block's state machine behavior, if a state machine is present. The
	 * operation submits an {@code InitialEvent} to the state machine's event queue
	 * to thereby start its execution thread. The state machine will execute in
	 * accordance with the states and transitions that are defined in the class that
	 * extends the {@code SysMLStateMachine}.
	 * 
	 */
	public void start()
	{
		if (stateMachine.isPresent())
			stateMachine.get().start();
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to start");
	}

	/**
	 * Accepts and queues a specified event into the state machine's event queue.
	 * Whereas the event queue is a thread safe object, this method can be called to
	 * inject an event into the state machine from any thread.
	 * 
	 * @param event event to be queued to the state machine
	 */
	public void acceptEvent(SysMLEvent event)
	{
		if (stateMachine.isPresent())
			stateMachine.get().queueEvent(event);
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to accept event: " + event.getClass().getSimpleName());
	}

	/**
	 * Stops the block's state machine-based behavior, if a state machine is present
	 * in the block. The operation simply stops the state machine execution. Note if
	 * this block's state machine is asynchronous, i.e. executes in its own thread,
	 * then this operation imposes a "hard stop" where the thread is canceled
	 * regardless of what state it's in. If the block's state machine is
	 * synchronous, this operation can only queue up a {@code FinalEvent} to the
	 * event queue, on the assumption that the state machine is capable of handling
	 * the {@code FinalEvent} at the point in which the {@code stop()} method is
	 * invoked. Therefore, if a block is synchronous and this stop method is used,
	 * it's state machine should be modeled to handle the {@code FinalEvent}
	 * accordingly.
	 */
	public void stop()
	{
		if (stateMachine.isPresent())
			stateMachine.get().stop();
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to stop");
	}

	/**
	 * Delays the calling thread (sleeps) for the specified seconds of time.
	 * 
	 * @param seconds time to sleep in seconds. Use up to 3 decimal places for
	 *                fractions of a second, i.e. the delay is capable of the
	 *                milliseconds precision provided by java's
	 *                {@code Thread.sleep(<millis>)} operation.
	 */
	public void delay(double seconds)
	{
		try
		{
			Thread.sleep((long)(seconds * 1000));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Overridable operation that creates and initializes the block's values.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
		myFirstValue = new ValueTypeA(&lt;initializer&gt;);
		myNextValue = new ValueTypeB(&lt;initializer&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Value} annotation and {@code ValueTypeX} is a constructor of
	 * one of the {@code SysMLValueType} classes, i.e. values are declared by
	 * {@code SysMLValueType}s. Initializers for Real or Int value types, or for
	 * extensions of the Real or Int value types, usually include an integer or
	 * double number as the first argument(s). These numbers should be formatted in
	 * decimal format. Use of the underscore (e.g. 10_000.01) can be used for
	 * thousands demarcation which will be converted to comma (10,000.01) by
	 * SysMLinJava tools for value displays.
	 * 
	 * @see sysmlinjava.valuetypes.SysMLValueType
	 */
	protected void createValues()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's flows.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		myFirstFlowInOut = new FlowTypeA(&lt;initializer&gt;);
		myNextFlowOut = new FlowTypeB(&lt;initializer&gt;);
		myNextFlowIn = new FlowTypeB(&lt;initializer&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Flow} annotation and {@code FlowTypeX} is a constructor of
	 * one of the {@code SysMLValueType}s, i.e. flows are declared by
	 * {@code SysMLValueType}s
	 * <p>
	 * <b>Note:</b> The name of the flow may/should end with the flow's
	 * {@code SysMLFlowDirectionKind} string value. If the field annotation does not
	 * include an annotation member/value pair for the
	 * {@code SysMLFlowDirectionKind} and the {@code SysMLFlowDirectionKind} string
	 * value is at the end of the flow variable name, then the string value
	 * ({@code In, Out, or InOut})will be used as the flow direction.
	 * 
	 * @see sysmlinjava.valuetypes.SysMLValueType
	 * @see sysmlinjava.annotations.Flow
	 * @see sysmlinjava.kinds.SysMLFlowDirectionKind
	 */
	protected void createFlows()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's parts.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		myFirstPart = new PartTypeA(&lt;initializer&gt;);
		myNextPart = new PartTypeB(&lt;initializer&gt;);
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Part} annotation and {@code PartTypeX} is a constructor of
	 * one any class that extends the {@code SysMLBlock} class.
	 * 
	 * @see sysmlinjava.blocks.SysMLBlock
	 */
	protected void createParts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's full ports.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		myFirstFullPort = new FullPortTypeA(&lt;initializer&gt;);
		myNextFullPort = new FullPortTypeB(&lt;initializer&gt;);
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;FullPort} annotation and {@code FullPortTypeX} is a
	 * constructor of one any class that extends the {@code SysMLFullPort} class.
	 * 
	 * @see sysmlinjava.ports.SysMLFullPort
	 */
	protected void createFullPorts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's proxy ports.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		myFirstProxyPort = new ProxyPortTypeA(&lt;initializer&gt;);
		myNextProxyPort = new ProxyPortTypeB(&lt;initializer&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;ProxyPort} annotation and {@code ProxyPortTypeX} is a
	 * constructor of one any class that implements an extension of the
	 * {@code SysMLProxyPort} interface.
	 * 
	 * @see sysmlinjava.ports.SysMLProxyPort
	 */
	protected void createProxyPorts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's activities.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
		myFirstActivity = (&lt;params&gt;) -> &lt;lamda expression/block statement&gt;;
		myNextActivity = (&lt;params&gt;) -> &lt;lamda expression/block statement&gt;;
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Activity} annotation and lambda expression/block statement is
	 * a block statement that corresponds to lambda expression that is an
	 * implementation of the {@code SysMLActivity} functional interface.
	 * 
	 * @see sysmlinjava.common.SysMLActivity
	 */
	protected void createActivities()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's connector
	 * functions, i.e. the code that performs part and port connections.
	 * 
	 * <pre>
		myFirstConnectorFunction = () -> &lt;lamda expression/block statement&gt;;
		myNextConnectorFunction = () -> &lt;lamda expression/block statement&gt;;
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;ConnectorFunction} annotation and lambda expression/block
	 * statement is a block statement that corresponds to a lambda expression that
	 * is an implementation of the {@code SysMLAssociationBlockConnectorFunction}
	 * functional interface.<br>
	 * <b>Note:</b>Connectors in SysMLinJava are declared as
	 * {@code SysMLAssociationBlockConnector}s, i.e. all connectors must be based on
	 * defined associations. Hence, the connector function is the code that performs
	 * the connections that are defined by the association, i.e. the participants of
	 * the association as defined in the association block.
	 * 
	 * @see sysmlinjava.connectors.SysMLAssociationBlockConnectorFunction
	 * @see sysmlinjava.connectors.SysMLAssociationBlockConnector
	 */
	protected void createConnectorFunctions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's connectors.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
		myFirstConnector = new SysMLAssociationBlock(&lt;initializer&gt;participants0&gt;, &lt;initializer&gt;participants1&gt;, &lt;initializer&gt;connectorFunction&gt;);
		myNextConnector = new SysMLAssociationBlock(&lt;initializer&gt;participants0&gt;, &lt;initializer&gt;participants1&gt;, &lt;connectorFunction&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Connector} annotation and
	 * {@code SysMLAssociationBlockConnector} is a constructor of the
	 * SysMLAssociationBlockConnector class.<br>
	 * <b>Note:</b>Connectors in SysMLinJava are declared as
	 * SysMLAssociationBlocksConnector, i.e. all connectors must be based on defined
	 * associations. Therefore, the connectorFunction is one of the
	 * {@code SysMLAssociationBlockConnectorFunction}s that were created in the
	 * {@code createConnectorFunctions()} operations above. The
	 * {@code createConnectorFunctions()} operation will be invoked during the
	 * SysMLAssociationBlockConnector constructor execution to actually "connect"
	 * the participating elements. The {@code createConnectorFunctions()} operation
	 * will be invoked before the {@code createConnectors()} operation, thereby
	 * ensuring that the connector functions are defined before they are executed.
	 * 
	 * @see sysmlinjava.connectors.SysMLAssociationBlockConnectorFunction
	 * @see sysmlinjava.connectors.SysMLAssociationBlockConnector
	 */
	protected void createConnectors()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's constraints.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
		myFirstConstraint = () -> &lt;lamda expression/block statement&gt;;
		myNextConstraint = () -> &lt;lamda expression/block statement&gt;;
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Constraint} annotation and lambda expression/block statement
	 * is a block statement that corresponds to a lambda expression that is an
	 * implementation of the {@code SysMLConstraint} functional interface.<br>
	 * <b>Note:</b>Implementation of this creation operation is very similar to that
	 * of the {@code createActivities()} operation.
	 * 
	 * @see sysmlinjava.common.SysMLConstraint
	 */
	protected void createConstraints()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's constraint
	 * blocks.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		myFirstConstraintBlock = new ConstraintBlockA(&lt;parent&gt;;);
		myNextConstraintBlock = new ConstraingBlockB(&lt;parent&gt;;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;ConstraintBlock} annotation and {@code ConstraintBlockX} is a
	 * constructor of a class that is an extension of the
	 * {@code SysMLConstraintBlock} class.<br>
	 * <b>Note:</b>Constraint blocks in SysMLinJava are extensions of the block,
	 * just as they are ins standard SysML. While all the features of the block are
	 * inherited by the {@code SysMLConstraintBlock}, there are a number of features
	 * that are added by the extended constraint block.
	 * 
	 * @see sysmlinjava.constraintblocks.SysMLConstraintBlock
	 */
	protected void createConstraintBlocks()
	{
	}

	/**
	 * Overridable operation that creates/initializes the constraint block's
	 * constraint parameter connector functions. An example follows:
	 * 
	 * <pre>
	 * aConstraintParameterConnectorFunction = () ->
	 * {
	 * 	SysMLConstraintParameterPort aParameterPort = myConstraintBlock.paramPorts.get("aConstraintParameterID");
	 * 	aParameterPort.parameterContextBlock = myBlock;
	 * 	aValue.addValueChangeObserver(aParameterPort);
	 * 
	 * 	SysMLConstraintParameterPort bParameterPort = myConstraintBlock.paramPorts.get("bConstraintParameterID");
	 * 	bParameterPort.parameterContextBlock = myBlock;
	 * 	bValue.addValueChangeObserver(vParameterPort);
	 * };
	 * </pre>
	 * 
	 * where the function consists of two parameter connections<br>
	 * <ul>
	 * <li>first that binds/connects the {@code aValue} value in {@code myBlock} to
	 * the {@code aParameterPort} port in the {@code myConstraintBlock} constraint
	 * block</li>
	 * <li>second that binds/connects the {@code bValue} value in {@code myBlock} to
	 * the {@code bParameterPort} port in the {@code myConstraintBlock} constraint
	 * block</li>
	 * </ul>
	 * <b>Note:</b>Constraint blocks in SysMLinJava are extensions of the block,
	 * just as they are in standard SysML. While all the features of the block are
	 * inherited by the {@code SysMLConstraintBlock}, there are a number of
	 * features, such as the constraint parameter port, that are added by the
	 * extended constraint block.
	 * 
	 * @see sysmlinjava.connectors.SysMLBindingConnector
	 * @see sysmlinjava.connectors.SysMLBindingConnectorFunction
	 * @see sysmlinjava.constraintblocks.SysMLConstraintBlock
	 * @see sysmlinjava.ports.SysMLConstraintParameterPort
	 * @see sysmlinjava.ports.SysMLConstraintParameterPortFunction
	 */
	protected void createConstraintParameterConnectorFunctions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the constraint block's
	 * constraint parameter connectors. An example follows:
	 * 
	 * <pre>
	 * aConstraintParameterConnector = new SysMLBindingConnector(myParamsBlock, myConstraintBlock, aConstraintParameterConnectorFunction);
	 * </pre>
	 * 
	 * where the statement initializes the constraint parameter connector with the
	 * block that contains the value that is to be "bound" to the port in the
	 * constraint block.<br>
	 * <b>Note:</b>Constraint blocks in SysMLinJava are extensions of the block,
	 * just as they are ins standard SysML. While all the features of the block are
	 * inherited by the SysMLConstraintBlock, there are a number of features, such
	 * as the parameter port, that are added by the extended constraint block.
	 * 
	 * @see sysmlinjava.connectors.SysMLBindingConnector
	 * @see sysmlinjava.connectors.SysMLBindingConnectorFunction
	 * @see sysmlinjava.constraintblocks.SysMLConstraintBlock
	 * @see sysmlinjava.ports.SysMLConstraintParameterPort
	 * @see sysmlinjava.ports.SysMLConstraintParameterPortFunction
	 */
	protected void createConstraintParameterConnectors()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's declared
	 * events.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
	   myTimeEvent = new SysMLTimeEvent(&lt;initializer&gt;);
	   myCallEvent = new MyCallEvent(&lt;initializer&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Event} annotation and {@code new SysMLTimeEvent} and
	 * {@code MyCallEvent} are constructors of a specific time event and an
	 * extended/specialized call event, respectively.
	 * <p>
	 * <b>Note</b> this operation is likely seldom used as events are typically
	 * instantiated when the event occurs rather than as part of block construction
	 * - the exception being the {@code SysMLTimeEvent}, which is used to start
	 * timers as well as to indicate timer expiration.
	 * 
	 * @see sysmlinjava.events.SysMLEvent
	 * @see sysmlinjava.events.SysMLCallEvent
	 * @see sysmlinjava.events.SysMLChangeEvent
	 * @see sysmlinjava.events.SysMLCompletionEvent
	 * @see sysmlinjava.events.SysMLSignalEvent
	 * @see sysmlinjava.events.SysMLTimeEvent
	 */
	protected void createEvents()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's declared
	 * signals.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
	   myControlSignal = new ControlSignal(&lt;initializer&gt;);
	   myMonitorSignal = new MonitorSignal(&lt;initializer&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Signal} annotation and {@code new ControlSignal} and
	 * {@code new MonitorSignal} are constructors of a specific extended/specialized
	 * type of signal.
	 * <p>
	 * <b>Note</b> this operation is likely seldom used as signals are typically
	 * instantiated when the signal is transmitted rather than as part of block
	 * construction.
	 * 
	 */
	protected void createSignals()
	{
	}

	/**
	 * Overridable operation that creates and initializes the block's declared class
	 * objects.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
	   myClassObject = new MyClassObject(&lt;initializer&gt;);
	   anotherClassObject = new AnotherClassObject(&lt;initializer&gt;);
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Classs} annotation of type {@code SysMClass} extension and
	 * {@code new MyClassObject} and {@code new AnotherCLassObject} are constructors
	 * of a specific type of {@code SysMLClass}.
	 * 
	 * @see sysmlinjava.common.SysMLClass
	 */
	protected void createClasses()
	{
	}

	/**
	 * Overridable operation that should create the block's state machine. An
	 * example follows:
	 * 
	 * <pre>
	 * stateMachine = new MyBlocksStateMachine(this);
	 * </pre>
	 * 
	 * where {@code stateMachine} is the optional variable that represents the
	 * blocks state machine and {@code MyBlocksStateMachine} is a class that is an
	 * extension of the {@code SysMLStateMachine} class.
	 * 
	 * @see sysmlinjava.statemachine.SysMLStateMachine
	 */
	protected void createStateMachine()
	{
	}

	/**
	 * Overridable operation that initializes the block's references.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
	 * myFirstRef = SomeClass.someObject;
	 * myNextRef = AnotherClass.anotherObject;
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Reference} annotation and {@code SomeClass.someObject} is a
	 * reference to an object ({@code static} field) in another {@code SysMLBlock}
	 * or {@code SysMLClass}.
	 * 
	 * @see sysmlinjava.common.SysMLClass
	 */
	protected void createReferences()
	{
	}

	/**
	 * Overridable operation that initializes the block's requirements. An example
	 * follows:
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
	 * <b>Note</b> that block requirements should be declared as described above,
	 * i.e. as references to instantiations of {@code SysMLRequirement}s declared
	 * and initialized in classes that extend/specialize the
	 * {@code SysMLRequirements} class. Requirements should <b>not</b> be
	 * declared/initialized in the block itself. Requirement specifications in the
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
	 * Overridable operation that creates the dependencies for this block. Overrides
	 * of this operation should perform initializations of variables annotated as
	 * dependencies. Whereas dependencies are type of association, each dependency
	 * should be defined by a field annotated as a
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
	 * Overridable operation to enable the transmission from the simulation (model
	 * execution) to an interaction sequence (sequence diagram) display of the
	 * interaction messages that occur during the simulation. This operation enables
	 * a grapical display of the real-time sequence diagram of the interactions
	 * between the specified parts of the model throughout the simulation.
	 * <p>
	 * Interaction messages are representations of the information that is
	 * transmitted from one full-port to another. By enabling the interaction
	 * message transmissions here, the information that is transmitted from the
	 * fullport, i.e. message time, message source block, signal or operation call
	 * (message), and message destination block, is transmitted to a specified UDP
	 * port for possible display in a sequence diagram or other representation.
	 * <p>
	 * Specifically, interaction message transmissions for a full-port are enabled
	 * by assigning an instance of the {@code InteractionMessageTransmitters} to the
	 * {@code messageUtility} variable of the full-port. An example of how to use
	 * this method to enable transmission of interaction messages for four specific
	 * full-ports is as follows.
	 * 
	 * <pre>
	 * protected void enableInteractionMessageTransmissions()
	 * {
	 * 	InteractionMessageTransmitter transmitter = new InteractionMessageTransmitter(InteractionMessageSequenceDisplay.udpPort);
	 * 	InteractionMessageTransmitters interactionMessageTransmitters = new InteractionMessageTransmitters(transmitter);
	 * 
	 * 	vehicle.brakeLeftFront.messageUtility = Optional.of(interactionMessageTransmitters);
	 * 	vehicle.brakeRightFront.messageUtility = Optional.of(interactionMessageTransmitters);
	 * 	vehicle.brakeLeftRear.messageUtility = Optional.of(interactionMessageTransmitters);
	 * 	vehicle.brakeRightRear.messageUtility = Optional.of(interactionMessageTransmitters);
	 * }
	 * </pre>
	 * 
	 * @see sysmlinjava.ports.SysMLFullPort
	 * @see sysmlinjava.ports.SysMLProxyPort
	 * @see sysmlinjava.ports.InteractionMessageUtility
	 * @see sysmlinjava.analysis.interactionmessagetransmitter.InteractionMessageTransmitters
	 * @see sysmlinjava.analysis.interactionsequence.InteractionMessageTransmitter
	 */
	protected void enableInteractionMessageTransmissions()
	{
	}

	/**
	 * Name of state machine variable, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String stateMachineVariableName = "stateMachine";
	/**
	 * Name of field for block's state machine, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String stateMachineFieldName = "stateMachine";
	/**
	 * Name of method to create state machine, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createStateMachineMethodName = "createStateMachine";
	/**
	 * Name of method to create values, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createValuesMethodName = "createValues";
	/**
	 * Name of method to create flows, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createFlowsMethodName = "createFlows";
	/**
	 * Name of method to create references, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createReferencesMethodName = "createReferences";
	/**
	 * Name of method to create parts, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createPartsMethodName = "createParts";
	/**
	 * Name of method to create full ports, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createFullPortsMethodName = "createFullPorts";
	/**
	 * Name of method to create full ports, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createProxyPortsMethodName = "createProxyPorts";
	/**
	 * Name of method to create activities, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createActivitiesMethodName = "createActivities";
	/**
	 * Name of method to create events, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createEventsMethodName = "createEvents";
	/**
	 * Name of method to create signals, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createSignalsMethodName = "createSignals";
	/**
	 * Name of method to create classes, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createClassesMethodName = "createClasses";
	/**
	 * Name of method to create connector functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConnectorFunctionsMethodName = "createConnectorFunctions";
	/**
	 * Name of method to create connectors, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createConnectorsMethodName = "createConnectors";
	/**
	 * Name of method to create constraints, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createConstraintsMethodName = "createConstraints";
	/**
	 * Name of method to create constraint blocks, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintBlocksMethodName = "createConstraintBlocks";
	/**
	 * Name of method to create constraint parameter connector functions, used by
	 * SyMLinJava tools
	 */
	public static final String createConstraintParameterConnectorFunctionsMethodName = "createConstraintParameterConnectorFunctions";
	/**
	 * Name of method to create constraint parameter connectors, used by SyMLinJava
	 * tools
	 */
	public static final String createConstraintParameterConnectorsMethodName = "createConstraintParameterConnectors";
	/**
	 * Name of method to create comments, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createCommentsMethodName = "createComments";
	/**
	 * Name of method to create problems, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createProblemsMethodName = "createProblems";
	/**
	 * Name of method to create rationales, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createRationalesMethodName = "createRationales";
	/**
	 * Name of method to create hyperlink comments, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createHyperlinksMethodName = "createHyperlinks";
	/**
	 * Name of method to create element group comments, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createElementGroupsMethodName = "createElementGroups";
	/**
	 * Name of method to create constraint note comments, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintNotesMethodName = "createConstraintNotes";
	/**
	 * Name of method to create requirements, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createRequirementsMethodName = "createRequirements";
	/**
	 * Name of method to create dependencies, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createDependenciesMethodName = "createDependencies";
}
