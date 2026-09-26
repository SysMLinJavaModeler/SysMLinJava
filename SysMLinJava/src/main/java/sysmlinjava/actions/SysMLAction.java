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
package sysmlinjava.actions;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import sysmlinjava.javaannotations.actions.Action;
import sysmlinjava.javaannotations.actions.ActionFunction;
import sysmlinjava.occurrences.SysMLOccurrence;
import sysmlinjava.occurrences.SysMLSnapshot;

/**
 * SysMLinJava representation of the SysML action. {@code SysMLAction} specifies
 * its action body as a functional interface, typically a lambda function
 * corresponding to a functional interface. an action with an occurence
 * specification, i.e. with "snapshots" in time in which the action is
 * performed. The action ccurremce should be declared as a field annotated as
 * &#64;ActionOccurrence with a variable whose type is a
 * {@code SysMLActionOccurrence} or extension of same. The variable should be
 * created/initialized in the {@code createActionOccurrences()} method of the
 * part, port, or other {@code EventDriven} implementation in which it is a
 * feature.
 * <p>
 * The {@code SysMLAction}, or extension thereof, can be specified with
 * occurrence times, i.e. times at which the action is performed. To make this
 * action executable, the {@code SysMLAction} declares a constructor that
 * creates a {@code Runnable} that invokes the specified {@code function}
 * variable - a functional interface - to perform the specified action. The
 * functional interface may simply invoke a method in the class in which it is
 * created, or it may perform the entire action itself. In any case, the action
 * will be performed (executed) in its own thread of execution in the specified
 * thread pool at the specified "snapshot" times. An example is as follows:
 * 
 * <pre>{@code
		class MeterReader extends SysMLPart
		{
			&#64;Attribute
			SysMLSnapshot firstReadTime;
			&#64;Attribute
			SysMLSnapshot secondReadTime;
			&#64;Attribute
			String meterID;
				:
			&#64;Action
			public void readMeter()
			{
				
			}
				:
			&#64;ActionFunction
			public PerformedActionFunction readMeterFunction;
		
			&#64;Action
			public SysMLAction readMeterAction ;
				:
			&#64;Override
			protected void createActionFunctions()
			{
				readMeterFunction = () -> readMeter();
			}
				:
			&#64;Override
			protected void createSnapshots()
			{
				firstReadTime = new SysMLSnapshot(Instant.now(), "firstReadTime", id);
				secondReadTime = new SysMLSnapshot(Instant.now().plus(Period.ofDays(7)), "secondReadTime", id);
			}
				:
			&#64;Override
			protected void createActions()
			{
				readMeterAction = new SysMLAction(readMeterFunction, List.of(firstReadTime, secondReadTime), concurrentExecutionThreads, "reader", 2875L);
			}
				:
		}}</pre>
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.SysMLPart#createActionFunctions
 * @see sysmlinjava.parts.SysMLPart#createActions
 * @see sysmlinjava.occurrences.SysMLOccurrence#createSnapshots
 */
public class SysMLAction extends SysMLOccurrence
{
	/**
	 * Function of the action. i.e. instance of a functional interface that is an
	 * extension of the (@code ActionFunction} interface. The functional interface
	 * is created/intitialized as a lambda function. The function is typically
	 * defined the context of the model element in which it will execute.
	 */
	@ActionFunction
	public SysMLActionFunction function;

	/**
	 * Constructor for an action that has specified occurrences, i.e. times the
	 * action is to be performed. These specified occurrences typically require that
	 * the action be performed in an execution thread that is different from the
	 * thread of the part that invokes the action.
	 * <p>
	 * Action occurences are presumed to start at the times specified by the
	 * {@code occurrences} argument. If the action function is a
	 * {@code PerformedActionFunction}, this constructor schedules the action to
	 * automatically execute at each of the times specified by these snapshots .
	 * Otherwise, the executable model will have to include code to specifically
	 * execute the action at the snapshot times itself.
	 * <p>
	 * Note that actions to be performed in the same occurrence as the part that
	 * invokes it can be performed by simple java methods in lieu of this explicit
	 * function-based class. Annotation of a method with the {@code &#64;Action}
	 * annotation designates the method as a SysML action whose occurrence is that
	 * of the part in which it is performed.
	 * 
	 * @param function    the executable code, in the form a lambda function, that
	 *                    performs the action.
	 * @param occurrences the occurrences of the action in terms of snapshot times
	 *                    the action is to be performed. Note that occurrences
	 *                    specified by time slices are not supported by this class.
	 *                    If such occurrences are required, then a specialization of
	 *                    this class which executes the action as time slices must
	 *                    be developed and executed.
	 * @param executor    executor which is to "run" the specified action
	 *                    occurrence, usually the thread pool executor of the part
	 *                    that will invoke the action.
	 * @param name        name for the action
	 * @param id          unique identifier of the action
	 */
	public SysMLAction(SysMLActionFunction function, List<SysMLSnapshot> occurrences, ScheduledThreadPoolExecutor executor, String name, Long id)
	{
		super(name, id);
		this.function = function;

		createAttributes();
		createConstraintFunctions();
		createConstraints();
		createRequirements();
		createCustomMetadata();
		createActionFunctions();
		createActions();

		if (function instanceof PerformedActionFunction)
			occurrences.forEach(snapshot ->
			{
				long delay = snapshot.instant.toEpochMilli() - Instant.now().toEpochMilli();
				executor.schedule(() -> ((PerformedActionFunction) function).perform(), delay, TimeUnit.MILLISECONDS);
			});
	}

	/**
	 * Constructor for an action that has no specific occurrence lifetime. This
	 * specified action typically is invoked by another action that may or may not
	 * have a specific occurrence lifetime, i.e as a "sub-action".
	 * <p>
	 * Note that actions that are to be performed in the same occurrence as the part
	 * that invokes it can be performed by simple java methods in lieu of this
	 * explicit function-based class. Annotation of a method with the
	 * {@code &#64;Action} annotation designates the method as a SysML action whose
	 * occurrence is that of the part in which it is performed.
	 * 
	 * @param function the executable code, in the form a lambda function, that
	 *                 invokes the executable action.
	 * @param name     name for the action
	 * @param id       unique identifier of the action
	 */
	public SysMLAction(SysMLActionFunction function, String name, Long id)
	{
		super(name, id);
		this.function = function;

		createAttributes();
		createConstraintFunctions();
		createConstraints();
		createRequirements();
		createCustomMetadata();
		createActionFunctions();
		createActions();
	}

	/**
	 * Constructor for use by extension classes that define the function and/or
	 * occurrence in overrides of the {@code createSnapshots()} and
	 * {@code createIsIndividual()} methods and the {@code createFunction()} method.
	 * 
	 * @param name unique name of the action
	 * @param id   unique numerical id of the action
	 */
	protected SysMLAction(String name, Long id)
	{
		super(name, id);

		createAttributes();
		createFunction();
		createActionFunctions();
		createActions();
		createConstraintFunctions();
		createConstraints();
		createRequirements();
		createCustomMetadata();
	}

	/**
	 * Overridable method to perform the action. This method may be overridden in
	 * lieu of defining the {@code function} of the action to be a
	 * {@code PerformedActionFunction}. The default method is to invoke the
	 * {@code function} if it has been assigned a value, presumably in an override
	 * of the {@code createFunction()} method.
	 */
	@Action
	protected void perform()
	{
		if (function != null && function instanceof PerformedActionFunction actionFunction)
			actionFunction.perform();
	}

	/**
	 * Overridable operation that creates and initializes the action's attributes
	 * (parameters), if any. An example follows:
	 * 
	 * <pre>
		public class MyAction extends SysMLAction
		{
				:
		 	&#64;Attribute
			DistanceMeters length;
				:
			protected void createAttributes()
			{
				length = new DistanceMeters(25);
			}
				:
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.attributetypes.SysMLAttributeType
	 */
	protected void createAttributes()
	{

	}

	/**
	 * Creates the {@code function} of the action. An example implementation is as
	 * follows:
	 * 
	 * <pre>
		public class InitializerAction extends SysMLAction
		{
				:
			&#64;FunctionalInterface
			public interface InitializerFunction extends Function
			{
				boolean perform(AlphaPart alpha, BetaPart beta);
			}
				:
			&#64;Override
			protected void createFunction()
			{
				function = (InitializerFunction)(alpha, beta) ->
				{
					alpha.initialize();
					beta.initialize();
					sleep(5);
					return alpha.initialized() &amp;&amp; beta.initialized();
				};
			}
		}
	 * </pre>
	 */
	protected void createFunction()
	{
	}

	/**
	 * Creates the functions for the (sub) actions of this action. An example
	 * follows.
	 * 
	 * <pre>
		public class InitializerAction extends SysMLAction
		{
				:
			&#64;FunctionalInterface
			public interface InitializerFunction extends Function
			{
				boolean perform(AlphaPart alpha, BetaPart beta);
			}
			
			&#64;ActionFunction
			InitialixerFunction initializerFunction;
				:
			&#64;Override
			protected void createActionFunctions()
			{
				initializerFunction = (alpha, beta) ->
				{
					alpha.initialize();
					beta.initialize();
					sleep(5);
					return alpha.initialized() &amp;&amp; beta.initialized();
				};
			}
		}
	 * </pre>
	 */
	protected void createActionFunctions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the functions of the
	 * subactions that are to be invoked or "called" by this action. An example
	 * follows:
	 * 
	 * <pre>
		public class UpdateAndMonitorAction extends SysMLAction
		{
			&#64;AttributeIn
			IInteger powerLevel;
			&#64;AttributeIn
			IInteger inputLevel;
			&#64;AttributeIn
			IInteger outputLevel;
	
			public UpdateAndMonitorAction(IInteger powerLevel, IInteger inputLevel, IInteger ouputLevel)
			{
				super("UpdateAndMonitor", 0L);
				this.powerLevel = powerLevel;
				this.inputLevel = inputLevel;
				this.outputLevel = outputLevel;
			}
	
			&#64;FunctionalInterface
			interface UpdateActionFunction extends ActionFunction
			{
				void update();
			}
	
			&#64;FunctionalInterface
			interface MonitorActionFunction extends ActionFunction
			{
				boolean isOK();
			}
				:
		 	&#64;ActionFunction
			UpdateActionFunction updateActionFunction;
		 	&#64;ActionFunction
			MonitorActionFunction monitorActionFunction;
				:
		 	&#64;Action
			SysMLAction updateAction;
		 	&#64;Action
			SysMLAction monitorAction;
				:
			&#64;Override
			protected void createActionFunctions()
			{
				updateActionFunction = () ->
				{
					powerLevel = 3;
					inputLevel = 2;
					outputLevel = 4;
				};
				monitorActionFunction = () ->
				{
					return inputLevel * 2.5 + outputLevel * 4 &lt; powerLevel * 2
				};
			}
	
			&#64;Override
			protected void createActions()
			{
				updateAction = new SysMLAction(updateActionFunction, "updateAction", 0L);
				monitorAction = new SysMLAction(monitorActionFunction, "monitorAction", 1L);
			}
				:
			protected void createFunction()
			{
				function = (PerformedActionFunction)() ->
				{
					updateAction.update();
					if(monitorAction.isOK())
						log.info("it worked!");
					else
						log.warning("whoops...");
				};
			}
		}
	 * </pre>
	 */
	protected void createActions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the constraint function
	 * (lambda expressiuns) for the action's constraints. An example is as follows:
	 * 
	 * <pre>{@code
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;Attribute
			int totalWeight;
			&#64;Attribute
			int componentWeight;
			&#64;Attribute
			int containerWeight;
	
			&#64;FunctionalInterface
			public interface NextConstraint extends SysMLAction.ActionBody
			{
				int totalWeight(int waterWeight, int containerWeight);
			}
	
			&#64;ConstraintFunction
			BasicConstraintFunction totalPowerOKFunction;
			&#64;ConstraintFunction
			NextConstraint totalWeightFunction;
	
			void createConstraintFunctions()
			{
				totalPowerOKFunction = (BasicConstraintFunction)() -> return totalPower > electricalPower + mechanicalPower);
				totalWeightFunction = (NextConstraint)(componentsWeight, containerWeight) -> { return componentsWeight + containerWeight;});
			}
		}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraint
	 */
	protected void createConstraintFunctions()
	{
	}

	/**
	 * Overridable operation that creates/initializes the constraint texts
	 * (documentations) for the proxy port's constraints. An example is as follows:
	 * 
	 * <pre>{@code
			public class MyProxyPort extends SysMLProxyPort
			{
					:
				&#64;Attribute
				int totalReadErrors;
				&#64;Attribute
				int totalWriteErrors;
				&#64;Attribute
				int totalErrors;
					:
				&#64;ConstraintText
				SysMLDocumentation errorsConstraintText;
					:
				void createConstraintTexts()
				{
					errorsConstraintText = new SysMLDOcumentation("totalErrors = totalReadErrors + totalWriteErrors");
				}
					:
			}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraint
	 */
	protected void createConstraintTexts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the action's constraints.
	 * <p>
	 * Code format:
	 * 
	 * <pre>{@code
		{
			&#64;Constraint
			SysMLConstraint totalPowerOK;
			&#64;Constraint
			SysMLConstraint totalWeight;
	
			void createConstraints()
			{
				totalPowerOK = new SysMLConstraint(Optional.of(totalPowerOKFunction), totalPowerOKDoc, "TotalPowerOK", 0L);
				totalWeight = new SysMLConstraint(Optional.of(totalWeightFunction), totalWeightDoc, TotalWeight", oL });
			}
		}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraintFunction
	 * @see sysmlinjava.annotations.SysMLDocumentation
	 */
	protected void createConstraints()
	{
	}

	/**
	 * Overridable method that initializes the action's "satisfy" requirements. An
	 * example follows:
	 * 
	 * <pre>
		&#64;SatisfyRequirement
		SysMLRequirement actionsFirstRequirement;
		&#64;SatisfyRequirement
		SysMLRequirement actionsNextRequirement;
			:
		protected void createRequirements()
		{
			actionsFirstRequirement = SystemRequirements.id1_3_2_4;
			actionsNextRequirement = SystemRequirements.id1_3_2_5;
		}
	 * </pre>
	 * 
	 * <b>Note</b> that action requirements should be declared as described above,
	 * i.e. as references to instances of the {@code SysMLRequirement} declared and
	 * initialized in classes that extend/specialize the
	 * {@code SysMLRequirementsCollection} class. Requirements should <b>not</b> be
	 * defined/specified in the action itself. Requirement specifications are in the
	 * form of instances of the {@code SysMLRequirement} or
	 * specializations/extensions thereof. Specializations/extensions should be
	 * declared in a requirements package in the model, as suggested by the SysML
	 * standard. Instances of all requirements, be it of basic
	 * {@code SysMLRequirement}s or of extensions/specializations, should be
	 * declared as {@code static} instances in a class that is an extension of the
	 * {@code SysMLRequirementsCollection} class. Declaring all requirement
	 * instances in a single class enables SysMLinJava tools to be used to manage
	 * and query the requirements.
	 * 
	 * @see sysmlinjava.requirements.SysMLRequirement
	 * @see sysmlinjava.requirements.SysMLRequirementsCollection
	 */

	protected void createRequirements()
	{
	}

	/**
	 * Overridable operation that creates and initializes custom item metadata, i.e.
	 * metadata that is not one of the standard SysaML metadata types. (Standard
	 * metadata elements should be created in overrides of {@code create...()}
	 * methods in the {@code SysMLAnything} class. An example of creating custom
	 * metadata instances is as follows:
	 * 
	 * <pre>
		{
			&#64;Metadata
			SWAPMetadata swap;
				:
			protected void createCustomMetadata()
			{
				swap = new SWAPMetadata(25, 15, 15);
			}
		}
	 * </pre>
	 */
	protected void createCustomMetadata()
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
	 * Name of variable for action's funtion, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String functionVariableName = "function";
	/**
	 * Name of method to create attributes, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createAttributesMethodName = "createAttributes";
	/**
	 * Name of method to create the function, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createFunctionMethodName = "createFunction";
	/**
	 * Name of method to create functions of sub-actions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createActionFunctionsMethodName = "createActionFunctions";
	/**
	 * Name of method to create sub-actions, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createActionsMethodName = "createActions";
	/**
	 * Name of method to create constraint texts, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintTextsMethodName = "createConstraintTexts";
	/**
	 * Name of method to create constraint functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintFunctionsMethodName = "createConstraintFunctions";
	/**
	 * Name of method to create constraints, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createConstraintsMethodName = "createConstraints";
	/**
	 * Name of method to create dependencies, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createDependenciesMethodName = "createDependencies";
	/**
	 * Name of method to create requirements, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createRequirementsMethodName = "createRequirements";
	/**
	 * Name of method to create customized metadata, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createCustomMetadataMethodName = "createCustomMetadatas";
}
