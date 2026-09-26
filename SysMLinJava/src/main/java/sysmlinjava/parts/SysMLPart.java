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
package sysmlinjava.parts;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import sysmlinjava.events.SysMLEvent;
import sysmlinjava.items.SysMLItem;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjava.states.SysMLStateMachine;

/**
 * SysMLinJava's represention of the SysML part
 * <h2>The part in SysMLinJava</h2>{@code SysMLPart} is an abstract class that
 * provides a base class for all SysML part representations. Extensions of the
 * {@code SysMLPart} class declare fields and methods that represent SysML part
 * features and operations such as attributes, references, parts, ports,
 * constraints, and actions. They also declare fields that represent connectors
 * of the part's parts and ports.
 * <h3>Optional behavioral state machine</h3> The {@code SysMLPart} also
 * contains an optional state machine. This state machine is the SysMLinJava
 * representation of the state in standard SysML. If declared, it is defined in
 * terms of a class that extends the {@code SysMLStateMachine} abstract base
 * class. This extension class defines the state machine in terms of associated
 * SysMLinJava classes for states, transitions, guards, effects, events, etc.
 * The {@code SysMLStateMachine} also contains a {@code Runnable} that is
 * optionally executed in a concurrent execution thread. Use of this runnable
 * allows the part's state machine to execute asynchronously from other model
 * elements therefore enabling more precise modeling through multi-threaded
 * model executions.
 * <p>
 * The {@code SysMLPart}'s thread-based state machine execution begins with
 * invocation of the part's {@code start()} operation. This operation starts the
 * state machine runnable in its own thread while the part's {@code stop()}
 * method stops the state machine thread. The
 * {@code acceptEvent(SysMLEvent event)} method enqueues the specified event to
 * the state machine for action as specified by the declared
 * {@code SysMLStateMachine}.
 * <h3>Optional parent context part</h3> The {@code SysMLPart} base class also
 * provides for an optional context part or port property which may be used by
 * declared operations and activities to access the features and behaviors of a
 * <i>parent</i> part or port.
 * <h3>Thread pool</h3> Also included in the super-class for the part is a
 * {@code ScheduledThreadPoolExecutor}. This concurrency object is used to "run"
 * the declared {@code SysMLStateMachine} instance, but it also provides a
 * thread pool for controlled execution of any other concurrent threads of
 * execution that might be needed by specializations of the {@code SysMLPart}.
 * Use of the thread pool further enhances SysMLinJava's support for more
 * precise modeling through multi-threaded model executions.
 * <h3>Create/initialize methods</h3> Finally, the {@code SysMLPart} provides a
 * series of overrideable method calls to create all the features (the java
 * fields and methods) of the represented SysML part (a java class). The
 * {@code SysMLPart} constructor automatically invokes methods to
 * create/initialize the values, flows, references, parts, ports, constraints,
 * comments, requirements, etc - any SysML type that can be declared in a SysML
 * part. This automatic invocation of methods allows extensions to the
 * {@code SysMLPart} to simply override the {@code createXxxx()} methods for the
 * applicable features (fields) declared in the part (class) and perform the
 * creations/initializations in the methods. This ensures the features are
 * created/initialized in the correct and complete sequence needed. The
 * overridable {@code createXxxx()} methods provide a "reminder" to create and
 * initialize the part's features as well as a framework for their complete
 * definition.
 * <p>
 * A simplified example of code for a part model follows:
 * 
 * <pre>
	public class ElectricMotor extends SysMLPart
	{
		&#64;Port
		public ElectricalPowerReceivePort electricalLine;
		&#64;Port
		public MotorTorqueTransmitPort wheelMotorDisc;
		&#64;Port
		public MechanicalForceTransmitPort suspensionMount;

		&#64;Attribute
		public PowerWatts electricalPowerIn;
		&#64;Attribute
		public TorqueNewtonMeters mechanicalTorqueOut;
		&#64;Attribute
		public ForceNewtons weightOnSuspensionOut;
		&#64;Attribute
		public TorqueNewtonMetersPerKilowatt torqueNmPerKw;
		&#64;Attribute
		public RevolutionsPerMinute revolutionsPerMinute;
		&#64;Attribute
		public Percent motorEfficiency;

		&#64;Constraint
		public SysMLConstraint mechanicalTorqueCalculation;

		public ElectricMotor(MotorInWheelSystem motorInWheelSystem, String name, Long id)
		{
			super(motorInWheelSystem, name, id);
		}

		&#64;PerformedAction
		public void transmitWeight()
		{
			suspensionMount.transmit(weightOnSuspensionOut);
		}

		&#64;PerformedAction
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
		protected void createAttributes()
		{
			torqueNmPerKw = new TorqueNewtonMetersPerKilowatt(5);
			revolutionsPerMinute = new RevolutionsPerMinute(240);
			motorEfficiency = new Percent(95);
			electricalPowerIn = new PowerWatts(0);
			mechanicalTorqueOut = new TorqueNewtonMeters(0);
			weightOnSuspensionOut = new ForceNewtons(25, Math.PI));
		}

		&#64;Override
		protected void createPorts()
		{
			electricalLine = new ElectricalPowerReceivePort(this, this, 0);
			wheelMotorDisc = new MotorTorqueTransmitPort(this, 0);
			suspensionMount = new MechanicalForceTransmitPort(this, 0);
		}

		&#64;Override
		protected void createConstraints()
		{
			mechanicalTorqueCalculation = new SysMLConstraint((Function)() ->
			{
				mechanicalTorqueOut.value = torqueNmPerKw.value(electricalPowerIn.value / 1000)motorEfficiency.asFraction(); // (60 / (2Math.PI))(electricalPowerIn.value /
																																	 // revolutionsPerMinute.value) //(motorEfficiency.value / 100);
			});
		}
	}
 * </pre>
 * 
 * @see sysmlinjava.states.SysMLStateMachine
 * @see java.util.concurrent.ScheduledThreadPoolExecutor
 * @see java.lang.Runnable
 * @author ModelerOne
 */
public abstract class SysMLPart extends SysMLItem implements StateBehaviorContext
{

	/**
	 * Optional SysML part or port (as interface reference) that serves as the
	 * part's "context" in which this part resides. The context may be used by other
	 * part features, such as operations and parts, for access to parent part
	 * features.
	 */
	public Optional<StateBehaviorContext> context;

	/**
	 * Instance of the Java API's {@code ScheduledThreadPoolExecutor} used to "run"
	 * the optional state machine's {@code Runnable}. It is also available to
	 * {@code SysMLPart} extensions to execute other threads of execution that might
	 * be needed for more precise modeling of the part.
	 */
	public ScheduledThreadPoolExecutor concurrentExecutionThreads;

	/**
	 * Constructor initialized specified context part or port, name and ID
	 * 
	 * @param context Optional {@code EventDriven} part or port in whose context
	 *                this {@code SysMLPart} is to operate, i.e. parent part or port
	 * @param name    name to be associated with the part
	 * @param id      unique long integer identifier for this part
	 */
	protected SysMLPart(Optional<StateBehaviorContext> context, String name, Long id)
	{
		super(name, id);
		this.context = context;
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);

		createPorts();
		createActionFunctions();
		createActions();
		createCalculations();
		createConstraintFunctions();
		createConstraints();
		createEvents();
		createAnalysisCases();
		createVerificationCases();
		createUseCases();
		createFlowConnectors();
		createBindingConnectors();
		createCustomMetadatas();
		enableInteractionMessageTransmissions();
	}

	/**
	 * Constructor initialized no context part or port, specified name and ID
	 * 
	 * @param name name to be associated with the part
	 * @param id   unique identifier for this part
	 */
	protected SysMLPart(String name, Long id)
	{
		super(name, id);
		this.context = Optional.empty();
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);

		createPorts();
		createActionFunctions();
		createActions();
		createCalculations();
		createConstraintFunctions();
		createConstraints();
		createEvents();
		createAnalysisCases();
		createVerificationCases();
		createUseCases();
		createFlowConnectors();
		createBindingConnectors();
		createCustomMetadatas();
		enableInteractionMessageTransmissions();
	}

	/**
	 * Constructor initialized with no context part or port, default name and ID
	 */
	protected SysMLPart()
	{
		super();
		this.context = Optional.empty();
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);

		createPorts();
		createActionFunctions();
		createActions();
		createCalculations();
		createConstraintFunctions();
		createConstraints();
		createEvents();
		createAnalysisCases();
		createVerificationCases();
		createUseCases();
		createFlowConnectors();
		createBindingConnectors();
		createCustomMetadatas();
		enableInteractionMessageTransmissions();
	}

	/**
	 * Starts the part's state machine behavior, if a state machine is present. The
	 * operation submits an {@code InitialEvent} to the state machine's event queue
	 * to thereby start its execution thread. The state machine will execute in
	 * accordance with the states and transitions that are defined in the class that
	 * extends the {@code SysMLStateMachine}.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 */
	@Override
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
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param event event to be queued to the state machine
	 */
	@Override
	public void acceptEvent(SysMLEvent event)
	{
		if (stateMachine.isPresent())
			stateMachine.get().queueEvent(event);
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to accept event: " + event.getClass().getSimpleName());
	}

	/**
	 * Stops the part's state machine-based behavior, if a state machine is present
	 * in the part. The operation simply stops the state machine execution. Note if
	 * this part's state machine is asynchronous, i.e. executes in its own thread,
	 * then this operation imposes a "hard stop" where the thread is canceled
	 * regardless of what state it's in. If the part's state machine is synchronous,
	 * this operation can only queue up a {@code FinalEvent} to the event queue, on
	 * the assumption that the state machine is capable of handling the
	 * {@code FinalEvent} at the point in which the {@code stop()} method is
	 * invoked. Therefore, if a part is synchronous and this stop method is used,
	 * it's state machine should be modeled to handle the {@code FinalEvent}
	 * accordingly.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 */
	@Override
	public void stop()
	{
		if (stateMachine.isPresent())
			stateMachine.get().stop();
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to stop");
	}

	/**
	 * Delays the calling thread (sleeps) for the specified seconds of time.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param seconds time to sleep in seconds. Use up to 3 decimal places for
	 *                fractions of a second, i.e. the delay is capable of the
	 *                milliseconds precision provided by java's
	 *                {@code Thread.sleep(<millis>)} operation.
	 */
	@Override
	public void delay(double seconds)
	{
		try
		{
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getIdentityString()
	{
		return identityString();
	}

	@Override
	public ScheduledThreadPoolExecutor getExecutionThreads()
	{
		return concurrentExecutionThreads;
	}

	@Override
	public Optional<? extends SysMLStateMachine> getStateMachine()
	{
		return stateMachine;
	}

	/**
	 * Creates/initializes the features of a {@code SysMLPart} that are declared by
	 * the fields in classes that extend the {@code SysMLPart} class. Feature
	 * creation/initialization is performed by overriding and implementing any or
	 * all of the operations invoked by this operation in accordance with the
	 * specification of the operations below.
	 * <p>
	 * <b>Note:</b> Usage of these creation operations to create/initialize the
	 * features of the part (fields) promotes standardized creation of part features
	 * and ensures their correct sequence of construction - all in preparation for
	 * correct model execution and for use by SysMLinJava tools to correctly
	 * interpret the Java code as a SysML model. Modelers should ensure that object
	 * creations are compatible and consistent with the sequence of "create...()"
	 * calls invoked by this operation. If a different sequence of model
	 * creation/initialization is required, then this operation may be overridden
	 * and used to invoke a custom creation/initialization of the part's features.
	 * It should be noted, hwever, that SysMLinJava tools use the specified
	 * {@code create...()} operations to perform their tasks, so using any different
	 * named operations may void the capabilities of these tools.
	 */

	/**
	 * Overridable operation that creates and initializes the part's ports.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		&#64;Port
		PortTypeA myFirstPort;
		&#64;Port
		PortTypeA myNextPort;
	
		protected void createPorts()
		{
			myFirstPort = new PortTypeA(&lt;initializer&gt;);
			myNextPort = new PortTypeB(&lt;initializer&gt;);
		}
	 * </pre>
	 * 
	 * The ports are declared as field variables annotated with the
	 * {@code &#64;Port} annotation. Each port is created/initialized using an
	 * object creation statement whose type is a class that extends the
	 * {@code SysMLPort} and whose arguments specify the initial value(s) of the
	 * port, if any. Initializer arguments that are numerical should be formatted in
	 * decimal format. Use of the underscore (e.g. 10_000.01) can be used for
	 * thousands demarcation which will be converted to comma (10,000.01) by
	 * SysMLinJava tools for value displays.
	 * 
	 * @see sysmlinjava.ports.SysMLPort
	 */
	protected void createPorts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's performed
	 * actions. An example is as follows:
	 * 
	 * <pre>{@code
		public class SWAP extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;ActionFunction
			TotalPowerFunction getTotalPowerFunction;
			&#64;ActionFunction
			TotalWeightFunction getTotalWeightFunction;
	
			&#64;Action
			SysMLAction getTotalPower;
			&#64;Action
			SysMLAction getTotalWeight;
	
			&#64;FunctionalInterface
			public interface TotalPowerFunction extends ActionFunction
			{
				PowerWatts = totalPower();
			};
	
			&#64;FunctionalInterface
			public interface TotalWeightFunction extends ActionFunction
			{
				WeightPounds totalWeight(WeightPounds componentsWeight, WeightPounds containerWeight);
			};
				:
			void createActionFunctions()
			{
				getTotalPowerFunction = () -> totalPower = electricalPower + mechanicalPower;
				getTotalWeightzFunction = (componentsWeight, containerWeight) -> { return componentsWeight.added(containerWeight); });
			}
				:
			void createActions()
			{
				getTotalPower = new SysMLAction(getTotalPowerFunction);
				getTotalWeight = new SysMLAction(getTotalWeightFunction);
			}
		}}</pre>
	 * <p>
	 * The actions are declared as field variables annotated with the
	 * {@code &#64;Action} annotation. Each action is created/initialized using an
	 * object creation statement whose type is a {@code SysMLAction} and whose
	 * argument specifies a lambda expression that is an implementation of a
	 * functional interface that is an extension of the {@code SysMLAction}'s
	 * {@code SysMLActionBody} interface. When declared in this way, the lambda
	 * expression will have access to all variables and methods in the containing
	 * class, as well as to any arguments it may declare. In any case, the extended
	 * {@code SysMLActionBody} interface must declare the method that is to be
	 * called to invoke the lambda function.
	 * 
	 * @see sysmlinjava.actions.SysMLAction
	 */
	protected void createActionFunctions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's actions (actins
	 * declared in a field variable). An example is as follows:
	 * 
	 * <pre>{@code
		public class SWAP extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;ActionFunction
			TotalPowerFunction getTotalPowerFunction;
			&#64;Action
			TotalWeightFunction getTotalWeightFunction;
	
			&#64;Action
			SysMLAction getTotalPower;
			&#64;Action
			SysMLAction getTotalWeight;
	
			&#64;FunctionalInterface
			public interface TotalPowerFunction extends SysMLActionFunction
			{
				PowerWatts = totalPower();
			};
	
			&#64;FunctionalInterface
			public interface TotalWeightFunction extends SysMLActionFunction
			{
				WeightPounds totalWeight(WeightPounds componentsWeight, WeightPounds containerWeight);
			};
				:
			void createActionFunctions()
			{
				getTotalPowerFunction = () -> totalPower = electricalPower + mechanicalPower;
				getTotalWeightzFunction = (componentsWeight, containerWeight) -> { return componentsWeight.added(containerWeight); });
			}
				:
			void createActions()
			{
				getTotalPower = new SysMLAction(getTotalPowerFunction);
				getTotalWeight = new SysMLAction(getTotalWeightFunction);
			}
		}}</pre>
	 * <p>
	 * Note that actions can also be defined by &#64;{@code Action}-annotated
	 * standard Java methods in the part or port class that performs the action.
	 * Whereas these types of action are defined as standard Java methods, they need
	 * not be "created/initialized" in this or any other {@code create...()} method.
	 * The body of the method defines the action function for the action that is
	 * defined by the method. An example follows:
	 * 
	 * <pre>{@code
		public class SWAP extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;Action
			public void getTotalPower()
			{
				totalPower = electricalPower + mechanicalPower;
			}
				:
			&#64;Action
			public WeightPounds getTotalWeight(WeightPounds componentsWeight, WeightPounds containerWeight)
			{
				 return componentsWeight.added(containerWeight);
			}
				:
		}}</pre>
	 * 
	 * @see sysmlinjava.javaannotations.actions.Action
	 * @see sysmlinjava.actions.SysMLAction
	 */
	protected void createActions()
	{
	}

	protected void createProxyPorts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's calculation functions
	 * for calculations declared in a field variable. An example is as follows:
	 * 
	 * <pre>{@code
		public class PoweredSystem extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;ActionFunction
			TotalPowerFunction getTotalPowerFunction;
			&#64;Calculation
			TotalWeightFunction getTotalWeightFunction;
	
			&#64;Action
			SysMLCalculation getTotalPower;
			&#64;Calculation
			SysMLCalculation getTotalWeight;
	
			&#64;FunctionalInterface
			public interface TotalPowerFunction extends SysMLActionFunction
			{
				PowerWatts = totalPower();
			};
	
			&#64;FunctionalInterface
			public interface TotalWeightFunction extends SysMLActionFunction
			{
				WeightPounds totalWeight(WeightPounds componentsWeight, WeightPounds containerWeight);
			};
				:
			void createActionFunctions()
			{
				getTotalPowerFunction = () -> totalPower = electricalPower + mechanicalPower;
				getTotalWeightzFunction = (componentsWeight, containerWeight) -> { return componentsWeight.added(containerWeight); });
			}
				:
			void createActions()
			{
				getTotalPower = new SysMLCalculation(getTotalPowerFunction);
				getTotalWeight = new SysMLCalculation(getTotalWeightFunction);
			}
		}}</pre>
	 * <p>
	 * Note that calculation functions can also be defined by
	 * &#64;{@code Calculation}-annotated standard Java methods in the part or port
	 * class that performs the calculation. Whereas these types of calculation are
	 * defined as standard Java methods, they need not be "created/initialized" in
	 * this or any other {@code create...()} method. The body of the method defines
	 * the calculation function for the calculation that is defined by the method.
	 * An example follows:
	 * 
	 * <pre>{@code
		public class PoweredSystem extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;Calculation
			public void getTotalPower()
			{
				totalPower = electricalPower + mechanicalPower;
			}
				:
			&#64;Calculation
			public WeightPounds getTotalWeight(WeightPounds componentsWeight, WeightPounds containerWeight)
			{
				 return componentsWeight.added(containerWeight);
			}
				:
		}}</pre>
	 * 
	 * @see sysmlinjava.javaannotations.actions.CalculationFunction
	 * @see sysmlinjava.javaannotations.actions.Calculation
	 * @see sysmlinjava.actions.SysMLCalculationFunction
	 * @see sysmlinjava.actions.SysMLCalculation
	 */
	protected void createCalculationFunctions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's calculations
	 * (calculations declared in a field variable). An example is as follows:
	 * 
	 * <pre>{@code
		public class PoweredSystem extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;ActionFunction
			TotalPowerFunction getTotalPowerFunction;
			&#64;Calculation
			TotalWeightFunction getTotalWeightFunction;
	
			&#64;Action
			SysMLCalculation getTotalPower;
			&#64;Calculation
			SysMLCalculation getTotalWeight;
	
			&#64;FunctionalInterface
			public interface TotalPowerFunction extends SysMLActionFunction
			{
				PowerWatts = totalPower();
			};
	
			&#64;FunctionalInterface
			public interface TotalWeightFunction extends SysMLActionFunction
			{
				WeightPounds totalWeight(WeightPounds componentsWeight, WeightPounds containerWeight);
			};
				:
			void createActionFunctions()
			{
				getTotalPowerFunction = () -> totalPower = electricalPower + mechanicalPower;
				getTotalWeightzFunction = (componentsWeight, containerWeight) -> { return componentsWeight.added(containerWeight); });
			}
				:
			void createActions()
			{
				getTotalPower = new SysMLCalculation(getTotalPowerFunction);
				getTotalWeight = new SysMLCalculation(getTotalWeightFunction);
			}
		}}</pre>
	 * <p>
	 * Note that calculations can also be defined by
	 * &#64;{@code Calculation}-annotated standard Java methods in the part or port
	 * class that performs the calculation. Whereas these types of calculation are
	 * defined as standard Java methods, they need not be "created/initialized" in
	 * this or any other {@code create...()} method. The body of the method defines
	 * the calculation function for the calculation that is defined by the method.
	 * An example follows:
	 * 
	 * <pre>{@code
		public class PoweredSystem extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;Calculation
			public void getTotalPower()
			{
				totalPower = electricalPower + mechanicalPower;
			}
				:
			&#64;Calculation
			public WeightPounds getTotalWeight(WeightPounds componentsWeight, WeightPounds containerWeight)
			{
				 return componentsWeight.added(containerWeight);
			}
				:
		}}</pre>
	 * 
	 * @see sysmlinjava.javaannotations.actions.Calculation
	 * @see sysmlinjava.actions.SysMLCalculation
	 */
	protected void createCalculations()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's declared
	 * events.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		&#64;TimeEvent
		SysMLTimeEvent myTimeEvent;
		&#64;CallEvent
		MyCallEvent myCallEvent;
	
		protected void createEvents()
		{
		   myTimeEvent = new SysMLTimeEvent(&lt;initializer&gt;);
		   myCallEvent = new MyCallEvent(&lt;initializer&gt;);
		}
	 * </pre>
	 * <p>
	 * where the the targets of the assignment operations are the names of fields
	 * with the {@code &#64;TimeEvent} and {@code &#64;CallEvent} annotations and
	 * {@code new SysMLTimeEvent} and {@code MyCallEvent} are constructors of a
	 * specific time event and an extended/specialized call event, respectively.
	 * <p>
	 * <b>Note</b> this operation is likely seldom used as events are typically
	 * instantiated when the event occurs rather than as part of part construction -
	 * the exception being the {@code SysMLTimeEvent}, which is used to start timers
	 * as well as to indicate timer expiration.
	 * 
	 * @see sysmlinjava.events.SysMLEvent
	 * @see sysmlinjava.events.SysMLCallEvent
	 * @see sysmlinjava.events.SysMLChangeEvent
	 * @see sysmlinjava.events.SysMLCompletionEvent
	 * @see sysmlinjava.events.SysMLSignalEvent
	 * @see sysmlinjava.events.SysMLTimeEvent
	 */
	@Override
	protected void createEvents()
	{
	}

	protected void createAnalysisCases()
	{

	}

	protected void createParametricAnalysisCases()
	{

	}

	protected void createVerificationCases()
	{

	}

	protected void createUseCases()
	{

	}

	/**
	 * Overridable operation that creates and initializes the part's connectors. An
	 * example follows.
	 * 
	 * <pre>
			&#64;FlowConnector
			public SysMLFlowConnector tanksToPipeConnector;
			 :
			protected void createFlowConnectors()
			{
				tanksToPipeConnector = new SysMLFlowConnector(TypesEnum.peertopeer, List.of(fluidReservoir1, fluidReservoir2), List.of(pipe), "", 0L);
			}
	 * </pre>
	 * 
	 * @see sysmlinjava.connectors.SysMLFlowConnector
	 */
	protected void createFlowConnectors()
	{
	}

	protected void createBindingConnectors()
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
	 * transmitted from one port to another. By enabling the interaction message
	 * transmissions here, the information that is transmitted from the port, i.e.
	 * message time, message source part, signal or operation call (message), and
	 * message destination part, is transmitted to a specified UDP port for
	 * possible display in a sequence diagram or other representation.
	 * <p>
	 * Specifically, interaction message transmissions for a -port are enabled by
	 * assigning an instance of the {@code InteractionMessageTransmitters} to the
	 * {@code messageUtility} variable of the port. An example of how to use this
	 * method to enable transmission of interaction messages for four specific ports
	 * is as follows.
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
	 * @see sysmlinjava.ports.SysMLPort
	 * @see sysmlinjava.ports.SysMLProxyPort
	 * @see sysmlinjava.ports.InteractionMessageUtility
	 * @see sysmlinjava.views.interactionssequencediagram.InteractionMessageTransmitters
	 * @see sysmlinjava.views.interactionssequencediagram.InteractionMessageTransmitter
	 */
	protected void enableInteractionMessageTransmissions()
	{
	}

	/**
	 * Name of method to create ports, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createPortsMethodName = "createPorts";
	/**
	 * Name of method to create action functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createActionFunctionsMethodName = "createActionFunctions";
	/**
	 * Name of method to create actions, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createActionsMethodName = "createActions";
	/**
	 * Name of method to create procy port actions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createProxyPortsMethodName = "createProxyPorts";
	/**
	 * Name of method to create calculations, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createCalculationsMethodName = "createCalculations";
	/**
	 * Name of method to create items, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createItemsMethodName = "createItems";
	/**
	 * Name of method to create events, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createEventsMethodName = "createEvents";
	/**
	 * Name of method to create analysis cases, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createAnalysisCasesMethodName = "createAnalysisCases";
	/**
	 * Name of method to create verification cases, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createVerificationCasesMethodName = "createVerificationCases";
	/**
	 * Name of method to create parametric analysis cases, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createParametricAnalysisCasesMethodName = "createParametricAnalysisCases";
	/**
	 * Name of method to create use cases, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createUseCasesMethodName = "createUseCases";
	/**
	 * Name of method to create flow connectors, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createFlowConnectorsMethodName = "createFlowConnectors";
	/**
	 * Name of method to create binding connectors, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createBindingConnectorsMethodName = "createBindingConnectors";
	/**
	 * Name of method to create metadatas, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createMetadataMethodName = "createMetadata";
	/**
	 * Name of method to enable interaction message transmissions, used by
	 * SysMLinJava tools, typically not needed for modeling
	 */
	public static final String enableInteractionMessageTransmissionsMethodName = "enableInteractionMessageTransmissions";
}
