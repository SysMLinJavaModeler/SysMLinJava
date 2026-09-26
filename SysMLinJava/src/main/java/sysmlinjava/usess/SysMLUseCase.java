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
package sysmlinjava.usess;

import java.util.logging.Logger;

import sysmlinjava.actions.SysMLCalculationFunction;
import sysmlinjava.actions.SysMLCase;
import sysmlinjava.javaannotations.actions.UseCaseAction;

/**
 * SysMLinJava representation of the SysML use case. The {@code SysMLUseCase}
 * represents the use case(s) that describe the use of and interactions with the
 * subject by its actors.
 * <p>
 * {@code SysMLUseCase} is an extension of the {@code SysMLCase}. It includes a
 * {@code subject} as the system being used and the {@code actors} that use
 * (interact with) the system
 * <p>
 * The {@code SysMLUseCase} is an abstract class in which extension classes
 * specify the action(s) and sub-actions to be performed by the subject as
 * interactions with the actors. Each extended {@code SysMLUseCase} also
 * contains zero or more sub-use cases of the {@code SysMLCase}, i.e. the
 * {@code SysMLUseCase} is a collection of use cases and/or a hierarchy of use
 * cases. Instances of the use case are declared in Java field variables of
 * extensions of type {@code SysMLUseCase}.
 * <p>
 * The use case should be executable as a means of verifying a complete and
 * correct use case model. An executable use case model is achieved by creating
 * and executing a standard {@code main()} method in the use case class that
 * instantiates the use case and invokes its overriden {@code perform()} method.
 * An example executable {@code SysMLUseCase} model in SysMLinJava is as
 * follows:
 * 
 * <pre>{@code
		public class GuardSystemBasicUseCase extends SysMLUseCase
		{
			&#64;Subject
			public GuardSystem system;
			&#64;Actor
			public Operator operator;
			&#64;Actor
			public Target target;

			public GuardSystemBasicUseCase()
			{
				super("BasicUseCase", 0L);
				system = new GuardSystem();
				operator = new Operator();
				target = new Target();
			}

			&#64;Override
			&#64;UseCaseAction
			public void perform()
			{
				configure();
				locateTarget();
				trackTarget();
				strikeTarget();
				assessTarget();
			}

			&#64;UseCase
			public void configure()
			{
				operator.configure();
				system.configure();
			};
 
			&#64;UseCase
			public void locateTarget();
			{
				operator.locateTarget();
				system.locateTarget();
				target.returnLocated();
			}

			&#64;UseCase
			public void trackTarget();
			{
				operator.trackTarget();
				system.trackTarget();
				target.returnTracked();
			}

			&#64;UseCase
			public void strikeTarget();
			{
				operator.strikeTarget();
				system.strikeTarget();
				target.returnStriked();
			}

			&#64;UseCase
			public void assessTarget();
			{
				operator.assessTarget();
				system.assessTarget();
				target.returnAssessed();
			}

			&#64;Override
			protected void createObjective()
			{
				objective = SystemRequirementsCollection.operatorTracksTarget");
			}

			&#64;Override
			protected void createSubject()
			{
				subject = Optional.of(GuardSystem.class);
			}

			&#64;Override
			protected void createActors()
			{
				actors = List.of(Operator.class, Target.class);
			}

			static class GuardSystem extends SysMLPart
			{
				public void configure() {...}
				public void locateTarget() {...}
				public void trackTarget() {...}
				public void strikeTarget() {...}
				public void assessTarget() {...}
			}

			static class Operator extends SysMLPart
			{
				public void configure() {...}
				public void locateTarget() {...}
				public void trackTarget() {...}
				public void strikeTarget() {...}
				public void assessTarget() {...}
			}

			static class Target extends SysMLPart
			{
				public void returnLocated() {...}
				public void returnTracked() {...}
				public void returnStriked() {...}
				public void returnAssessed() {...}
			}

			public void main(String[] args)
			{
				GuardSystemBasicUseCase useCase = new GuardSystemBasicUseCase();
				useCase.perform();
				Runtime.getRuntime().exit(0);
			}
		}}</pre>
 * 
 * Note that the {@code main()} method is used to execute the use case. In
 * accordance with the principles of SysMLinJava modeling, use case execution
 * enhances achieving the goal of the use case model being a complete and
 * correct one.
 * 
 * @author ModelerOne
 */
public abstract class SysMLUseCase extends SysMLCase
{
	/**
	 * Logger for use case
	 */
	public static Logger logger = Logger.getLogger(SysMLUseCase.class.getSimpleName());

	/**
	 * Constructor for use by subclasses of the {@code SysMLUseCase} where features
	 * are set in overridden {@code create...()} methods.
	 * 
	 * @param name unique name for the case
	 * @param id   unique ID for the case
	 */
	public SysMLUseCase(String name, Long id)
	{
		super(name, id);

	}

	/**
	 * Overridable method to perform the use case action. The {@code perform()}
	 * method can be overridden to perform a custom action for the use case as
	 * needed. Default is to perform the {@code function} of the case action, if
	 * present and if it is of type {@code SysMLActionFunction}. The case
	 * action function is defined in an override of the base @{@code SysMLAction}'s
	 * {@code createActionFunctions()} method.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @see sysmlinjava.actions.SysMLAction#createActionFunctions
	 */
	@UseCaseAction
	@Override
	protected void perform()
	{
		if (function != null && function instanceof SysMLCalculationFunction useCaseFunction)
			useCaseFunction.perform();
	}

	/**
	 * Creates action/calculation functions of the included and sub-use cases. (The
	 * use case's "main" function is created/initialized in override of
	 * {@code createUseCaseActionFunction()} or {@code perform()} method) Functions
	 * can be used as an alternative to methods to define the actions/calculations
	 * of the use cases. An example follows: /** Creates the included and sub-use
	 * cases of the use case. An example follows:
	 * 
	 * <pre>{@code
				public class StrikeUseCase extends SysMLUseCase
				{
					&#64;UseCase
					FindTargetUseCase findTarget;
					&#64;UseCase
					FixTargetUseCase fixTarget;
					&#64;UseCase
					TrackTargetUseCase trackTarget;
					&#64;UseCase
					TargetTargetUseCase targetTarget;
					&#64;UseCase
					EngageTargetUseCase engageTarget;
					&#64;UseCase
					AssessTargetUseCase assessTarget;
						:
					&#64;Override
					protected void createUseCases()
					{
						findTarget = new FindTargetUseCase(findTargetFunction);
						fixTarget = new FixTargetUseCase(fixTargetFunction);
						trackTarget = new TrackTargetUseCase(trackTargetFunction);
						targetTarget = new TargetTargetUseCase(targetTargetFunction);
						engageTarget = new EngageTargetUseCase(engageTargetFunction);
						assessTarget = new AssessTargetUseCase(assessTargetFunction);
					}
						:
				}}</pre>
	 * 
	 * Note that use cases can be created as &#64;{@code UseCase} annotated class
	 * methods - in lieu of creating {@code SysMLUseCase} instances shown here.
	 */
	protected void createUseCases()
	{
	}

	/**
	 * Name of method to create use case action functions, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createUseCaseActionFunctionsMethodName = "createUseCaseActionFunctions";
	/**
	 * Name of method to create use case actions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createUseCaseActionsMethodName = "createUseCaseActions";
	/**
	 * Name of method to create use cases, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createUseCasesMethodName = "createUseCases";
}