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
package sysmlinjava.verifications;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

import sysmlinjava.actions.SysMLCalculationFunction;
import sysmlinjava.actions.SysMLCase;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.javaannotations.actions.VerificationCaseAction;
import sysmlinjava.javaannotations.requirements.VerificationMethod;
import sysmlinjava.javaannotations.verifications.Verdict;
import sysmlinjava.requirements.SysMLVerificationMethodKind;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjava.states.SysMLStateMachine;

/**
 * SysMLinJava representation of the SysML verification case. The
 * {@code SysMLVerificationCase} represents the actions that are to be performed
 * on the modeled system-under-test or {@code subject} of the verification. As
 * part of the executable model, however, the verification cases can invoke
 * activities on the SysMLinJava model, in which case these executable
 * verification cases serve as models of the test cases on the actual system.
 * <p>
 * {@code SysMLVerificationCase} is an extension of the {@code SysMLCase}. It
 * therefore includes a {@code subject}, {@code objective}, and {@code method}
 * of the verification, An example follows.
 * 
 * <pre>{@code
		public class MyVerificationCase extends SysMLVerificationCase
		{
			//actions, attributes, calculations, requirements, sub-cases, etc. of the verification are declared here//
			public MyVerificationCase(String name, Long id, Optional<StateBehaviorContext> context)
			{
				super("MyVerificationCase", 0L, Optional.of(MyVerificationCaseStateBehavior));
			}
			
			&#64;Override
			&#64;VerificationAction protected void perform()
			{
				//Main verfication action performed here
			}
			
			&#64;Override protected void createVerificationMethodKind()
			{
				method = SysMLVerificationMethodKind.Test;
			}
			
			&#64;Override protected void createObjective()
			{
				objective = new SysMLRequirement("1", "Interface Analysis",
				new SysMLDocumentation("Analysis shall verify protocol connections operate correctly"),
				RequirementCategoryEnum.Functional,
				List.of(SysMLVerificationMethodKind.Test),
				  :
				List.of());
			}
			
			&#64;Override
			protected void createSubject()
			{
				subject = Optional.of(SystemDomain.class);
			}
			
			&#64;Override protected void createActors()
			{
				actors = List.of(SystemA.class, OperatorB.class);
			}
				:
			//actions, attributes, calculations, requirements, sub-cases, etc. of the verification are created/initialized here//
		}}
		</pre>
 * 
 * The {@code SysMLVerificationCase} executes via the {@code perform()}
 * operation. The operation is invoked in a {@code main()} method declared in
 * the top-level extended {@code SysMLVerificationCase} class. An example
 * {@code main()} implementation is as follows:
 * 
 * <pre>{@code
			protected static void main(String[] args)
			{
				MyVerificationCase verification = new MyVerificationCase();'
				verification.perform();
			
				System.exit(0);
			}}</pre>
 * 
 * @author ModelerOne
 */
public abstract class SysMLVerificationCase extends SysMLCase implements StateBehaviorContext
{
	/**
	 * Logger for verification case
	 */
	public static Logger logger = Logger.getLogger(SysMLVerificationCase.class.getSimpleName());

	/**
	 * Method of verification
	 */
	@VerificationMethod
	public SysMLVerificationMethodKind method;
	/**
	 * Verdict of verification
	 */
	@Verdict
	public SysMLVerdictKind verdict;

	/**
	 * Optional state behavior context (as instance of part or port) that serves as
	 * the state-based context in which this case executes. The context may be used
	 * by other case features, such as its actions or sub-cases for access to
	 * context features.
	 */
	protected Optional<StateBehaviorContext> context;

	/**
	 * Optional SysML state machine for this SysML verification case. The
	 * {@code SysMLStateMachine} may be declared as "asynchronous" in which case
	 * it's {@code Runnable} will be automatically executed in one of the threads
	 * provided by the {@code concurrentExecutionThreads} declared below.
	 */
	protected Optional<? extends SysMLStateMachine> stateMachine;

	/**
	 * Instance of the Java API's {@code ScheduledThreadPoolExecutor} used to "run"
	 * the optional state machine's {@code Runnable}. It is also available to
	 * {@code SysMLPart} extensions to execute other threads of execution that might
	 * be needed for more precise modeling of the part.
	 */
	protected ScheduledThreadPoolExecutor concurrentExecutionThreads;

	/**
	 * Constructor for use by subclasses of the {@code SysMLVerificationCase} where
	 * features are set in overridden {@code create...()} methods.
	 * 
	 * @param name    unique name for the case
	 * @param id      unique ID for the case
	 * @param context optional state behavior context in which the case is to be
	 *                executed
	 */
	public SysMLVerificationCase(String name, Long id, Optional<StateBehaviorContext> context)
	{
		super(name, id);
		verdict = SysMLVerdictKind.inconclusive;
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		stateMachine = Optional.empty();

		createVerificationCaseActionFunctions();
		createVerificationCaseActions();
		createVerificationMethodKind();
		createVerdictKind();
		createStateMachine();
		createVerificationCases();
	}

	/**
	 * Overridable method to perform the verification case action. The
	 * {@code perform()} method can be overridden to perform a custom action for the
	 * verification case as needed. Default is to perform the {@code function} of
	 * the case action, if present and if it is of type
	 * {@code SysMLCalculationFunction}. The case action function is defined in an
	 * override of the base @{@code SysMLAction}'s {@code createFunction()} method.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @see sysmlinjava.actions.SysMLAction#createActionFunctions
	 */
	@VerificationCaseAction
	@Override
	protected void perform()
	{
		if (function != null && function instanceof SysMLCalculationFunction verificationCaseFunction)
			verificationCaseFunction.perform();
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
	 * Stops the verification case's state machine-based behavior, if a state
	 * machine is present in the case. The operation simply stops the state machine
	 * execution. Note if this case's state machine is asynchronous, i.e. executes
	 * in its own thread, then this operation imposes a "hard stop" where the thread
	 * is canceled regardless of what state it's in. If the case's state machine is
	 * synchronous, this operation can only queue up a {@code FinalEvent} to the
	 * event queue, on the assumption that the state machine is capable of handling
	 * the {@code FinalEvent} at the point in which the {@code stop()} method is
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
	 * Creates/initializes the action functions of the verification case. Note
	 * verification case action functions can be defined by simple methods annotated
	 * with &#64;{@code VerificationCaseAction} in lieu of this use of the
	 * {@code SysMLCalculation} and {@code SysMLCalculationFunction}. An example
	 * follows.
	 * 
	 * <pre>{@code
				public class MyVerificationCase extends SysMLVerificationCase
				{
					MySystem testSystem;
							:
					public MyVerificationCase(String name, Long id, Optional<StateBehaviorContext> context)
					{
						super(name, id, context);
						testSystem = new MySystem();
					}
						:
					&#64;VerificationCaseActionFunction
					SysMLCalculationFunction firstTestFunction;
					&#64;VerificationCaseActionFunction
					SysMLCalculationFunction secondTestFunction;
						:
					&#64;VerificationCaseAction
					SysMLCalculation firstTest;
					&#64;VerificationCaseAction
					SysMLCalculation secondTest;
						:
					&#64;Override
					protected void createSubject()
					{
						subject = Optional.of(MySystem.class);
					}
				
					&#64;Override
					protected void createObjective()
					{
						:
					}
				
					&#64;Override
					protected void createActors()
					{
						:
					}
				
					&#64;Override
					protected void createVerificationCaseActionFunctions()
					{
						firstTestFunction = () ->
						{
							testSystem.start();
							delay(30);
							testSystem.stop();
						};
					}
							:
					&#64;Override
					protected void createVerificationCaseActions()
					{
						firstTest = new SysMLCalculation(firstTestFunction, "FirstTest", 0L);
					}
							:
				}
				}</pre>
	 */
	protected void createVerificationCaseActionFunctions()
	{
	}

	/**
	 * Creates/initializes the actions of the verification case. Note verification
	 * case actions can be defined by simple methods annotated with
	 * &#64;{@code VerificationCaseAction} in lieu of this use of the
	 * {@code SysMLCalculation} and {@code SysMLCalculationFunction}. An example
	 * follows.
	 * 
	 * <pre>{@code
				public class MyVerificationCase extends SysMLVerificationCase
				{
					MySystem testSystem;
							:
					public MyVerificationCase(String name, Long id, Optional<StateBehaviorContext> context)
					{
						super(name, id, context);
						testSystem = new MySystem();
					}
						:
					&#64;VerificationCaseActionFunction
					SysMLCalculationFunction firstTestFunction;
					&#64;VerificationCaseActionFunction
					SysMLCalculationFunction secondTestFunction;
						:
					&#64;VerificationCaseAction
					SysMLCalculation firstTest;
					&#64;VerificationCaseAction
					SysMLCalculation secondTest;
						:
					&#64;Override
					protected void createSubject()
					{
						subject = Optional.of(MySystem.class);
					}
				
					&#64;Override
					protected void createObjective()
					{
						:
					}
				
					&#64;Override
					protected void createActors()
					{
						:
					}
				
					&#64;Override
					protected void createVerificationCaseActionFunctions()
					{
						firstTestFunction = () ->
						{
							testSystem.start();
							delay(30);
							testSystem.stop();
						};
					}
							:
					&#64;Override
					protected void createVerificationCaseActions()
					{
						firstTest = new SysMLCalculation(firstTestFunction, "FirstTest", 0L);
					}
							:
				}
				}</pre>
	 */
	protected void createVerificationCaseActions()
	{
	}

	/**
	 * Creates/initializes the optional state machine used for the execution of teh
	 * verification case. The state machine enables the verification to execution
	 * asynchronously from the execution of the model elements. The state machine
	 * will execute in the {@code context} of the case that was provided by the
	 * constructor.
	 */
	protected void createStateMachine()
	{
	}

	/**
	 * Creates/initializes the kind of verification method used by this case. The
	 * mthod is initialized by the caseconstructor as {@code test}, but can be
	 * initialized to a different value in this method. An example follows.
	 * 
	 * <pre>{@code
				public class MyVerificationCase extends SysMLVerificationCase
				{
						:
					&#64;Override
					protected void createVerificationMethodKind()
					{
						method = SysMLVerdicationMethodKind.test;
					}
								:
				}
				}</pre>
	 */
	protected void createVerificationMethodKind()
	{
	}

	/**
	 * Creates/initializes the verdict of the verification case. The verdict is
	 * initialized by the case constructor to {@code inconclusive}, but and can be
	 * initialized to a different value in this methos. In any case, the
	 * verification case will set the verdict to the final value after performing
	 * the verification action. An example follows.
	 * 
	 * <pre>{@code
				public class MyVerificationCase extends SysMLVerificationCase
				{
						:
					&#64;Override
					protected void createVerdictKind()
					{
						verdict = SysMLVerdictKind.fail;
					}
						:
				}
				}</pre>
	 */
	protected void createVerdictKind()
	{
	}

	/**
	 * Creates/initializes the (sub) verification cases. An example follows.
	 * 
	 * <pre>{@code
				public class MyVerificationCase extends SysMLVerificationCase
				{
					&#64;VerificationCase
					FirstVerificationCase firstTest;
					&#64;VerificationCase
					NextVerificationCase nextTest;
					
						:
					&#64;Override
					protected void createVerificationCases()
					{
						firstTest = new FirstVerificationCase("FirstTest", 0L);
						firstTest = new FirstVerificationCase("FirstTest", 0L);
						;
					}
						:
				}
				}</pre>
	 */
	protected void createVerificationCases()
	{
	}

	/**
	 * Name of variable for case's verification method, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String methodName = "method";
	/**
	 * Name of variable for case's verdict kind, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String verdictVariableName = "verdict";
	/**
	 * Name of method to create verification case action functions, used by
	 * SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createVerificationCaseActionFunctionsMethodName = "createVerificationCaseActionFunctions";
	/**
	 * Name of method to create verification case actions, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createVerificationCaseActionsMethodName = "createVerificationCaseActions";
	/**
	 * Name of method to create verification cases, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createVerificationCasesMethodName = "createVerificationCases";
	/**
	 * Name of method to create verification method, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createVerificationMethodKindMethodName = "createVerificationMethodKind";
	/**
	 * Name of method to create verdict, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createVerdictKindMethodName = "createVerdictKind";
	/**
	 * Name of method to create verification state machine, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createStateMachineMethodName = "createStateMachine";

}