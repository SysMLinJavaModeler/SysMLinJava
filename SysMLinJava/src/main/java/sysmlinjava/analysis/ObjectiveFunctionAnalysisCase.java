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
package sysmlinjava.analysis;

import java.util.Optional;

import sysmlinjava.constraint.BasicConstraintFunction;
import sysmlinjava.constraint.SysMLConstraint;
import sysmlinjava.constraint.SysMLConstraintFunction;
import sysmlinjava.javaannotations.actions.AnalysisCaseAction;
import sysmlinjava.javaannotations.analysis.parametrics.ObjectiveFunction;
import sysmlinjava.javaannotations.analysis.parametrics.ObjectiveFunctionFunction;

/**
 * SysMLinJava representation of the SysMLv1 objective function constraint
 * block.
 * <h2>Specialized parametric analysis</h2> A specialized type of parametric
 * analysis, the {@code ObjectiveFunctionAnalysisCase} simply extends the
 * {@code ParametricAnalysisCase} with parameters that represent measures of
 * effectiveness, performance, and/or suitability, and with one or more
 * constraints that declare the objective function to be satisfied by the
 * parameters.
 * <h3>Parameters as MOP, MOE, MOS</h3> As an abstract class, the
 * {@code ObjectiveFunctionAnalysisCase} is specialized for a particular type of
 * analysis that involves the calculation of a constraint defining an objective
 * function using parameters for the {@code moe}s, {@code mop}s, and/or
 * {@code mos}'s. Parameters should be annotated in accordance with their
 * purpose in this regard, i.e. as a<br>
 * <ul>
 * <li>{@code @MeasureOfEffectiveness},</li>
 * <li>{@code @MeasureOfPerformance}, or</li>
 * <li>{@code @MeasureOfSuitability}.</li>
 * </ul>
 * The {@code mop}, {@code moe}, and {@code mos} parameters for the
 * {@code ObjectiveFunctionAnalysisCase} should be declared locally in the
 * extended class. As with any extension of the {@code ParametricAnalysisCase},
 * the constraint (objective function) must first retrieve the current values of
 * the bound values for the {@code mop}, {@code moe}, and {@code mos} parameters
 * from the {@code params} map.
 * <h2>Objective Function Constraints</h2> In the rare case where more than one
 * constraint is needed to define the objective function, the additional
 * constraints can be declared in the objective function analysis case. These
 * specialized constraints should be annotated with {@code @ObjectiveFunction}
 * to identify them as such. The objective function constraint should be defined
 * in the {@code createObjectiveFunctions()} method.
 * <h2>Objective function analysis case creation same as for parametrice
 * analysis</h2> Other aspects of the specialized
 * {@code ObjectiveFunctionAnalysisCase} are essentially the same as for any
 * {@code ParametricAnalysisCase}, i.e. creating the parameters, creating
 * attributes, etc. as well as the constraints for the objective function.
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.SysMLPart#createParametricAnalysisCasesMethodName
 * @see sysmlinjava.javaannotations.analysis.parametrics.ObjectiveFunction
 * @see sysmlinjava.javaannotations.analysis.parametrics.MeasureOfEffectiveness
 * @see sysmlinjava.javaannotations.analysis.parametrics.MeasureOfPerformance
 * @see sysmlinjava.javaannotations.analysis.parametrics.MeasureOfSuitability
 */
public abstract class ObjectiveFunctionAnalysisCase extends ParametricAnalysisCase
{
	/**
	 * Constraint that is the objective function. The objective function (a
	 * constraint) is invoked every time a parameter port obtains a new value of a
	 * bound parameter and updates one or more of the measures accordingly.
	 */
	@ObjectiveFunction
	public SysMLConstraint objectiveFunction;

	/**
	 * Constraint function that is the objective function's function. The objective function (a
	 * constraint) is invoked every time a parameter port obtains a new value of a
	 * bound parameter and updates one or more of the measures accordingly.
	 */
	@ObjectiveFunctionFunction
	public SysMLConstraintFunction objectiveFunctionFunction;

	/**
	 * Default result indicating objective function completed successfully (used by default {@code perform} action)
	 */
	public Boolean result;
	
	/**
	 * Constructor - initial values
	 * 
	 * @param parent optional parametric analysis case of which this objective
	 *               function analysis case is a part
	 * @param name   unique name
	 */
	public ObjectiveFunctionAnalysisCase(Optional<? extends ParametricAnalysisCase> parent, String name)
	{
		super(parent, name, 0L);
		
		createObjectiveFunction();
		createMeasuresOfEffectiveness();
		createMeasuresOfPerformance();
		createMeasuresOfSuitability();
	}

	@AnalysisCaseAction
	@Override
	public void perform()
	{
		if(objectiveFunction.function.isPresent())
			if(objectiveFunction.function.get() instanceof BasicConstraintFunction constraintFunction)
				result = constraintFunction.satisfied();
	}

	/**
	 * Overridable operation that should create the objective function's constraint
	 * i.e. the {@code objectiveFunction}. The {@code objectiveFunction} should
	 * define the objective function that calculates the objective metric. An
	 * example follows:
	 * 
	 * <pre>
		&#64;Parameter
		DistanceMeters accuracy;
		&#64;Parameter
		Availability availability;
		&#64;Parameter
		Cost$US cost;
	
		&#64;Attribute
		RReal accuracyWeight;
		&#64;Attribute
		RReal availabilityWeight;
		&#64;Attribute
		RReal costWeight;
	
		&#64;MeasureOfEffectiveness
		RReal moe;
			:
		&#64;ObjectiveFunctionFunction
		MyMOEFunction myMOEFunction;
			:

		&#64;Override
		protected void createObjectiveFunctionFunction()
		{
			myMOEFunction = () ->
			{
				moe.setValue(accuracy.multipliedBy(accuracyWeight)
	          .addedTo(availability.multipliedBy(availabilityWeight)
	          .addedTo(cost.multipliedBy(costWeight)));
			};
		}
			:
		&#64;Override
		protected void createObjectiveFunction()
		{
			objectiveFunction = new SysMLConstraint(myMOEFunction, "moe = sum of weighted accuracy, availability, and cost", "objectiveFunction", 0L);
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.javaannotations.analysis.parametrics.ObjectiveFunctionFunction
	 */
	protected void createObjectiveFunctionFunction()
	{
	}
	
	/**
	 * Overridable operation that should create the objective function's constraint
	 * i.e. the {@code objectiveFunction}. The {@code objectiveFunction} should
	 * define the objective function that calculates the objective metric. An
	 * example follows:
	 * 
	 * <pre>
		&#64;Parameter
		DistanceMeters accuracy;
		&#64;Parameter
		Availability availability;
		&#64;Parameter
		Cost$US cost;
	
		&#64;Attribute
		RReal accuracyWeight;
		&#64;Attribute
		RReal availabilityWeight;
		&#64;Attribute
		RReal costWeight;
	
		&#64;MeasureOfEffectiveness
		RReal moe;
			:
		&#64;ConstraintFunction
		MyMOEFunction myMOEFunction;
			:

		&#64;Override
		protected void createObjectiveFunctionFunction()
		{
			myMOEFunction = () ->
			{
				moe.setValue(accuracy.multipliedBy(accuracyWeight)
	          .addedTo(availability.multipliedBy(availabilityWeight)
	          .addedTo(cost.multipliedBy(costWeight)));
			};
		}
			:
		&#64;Override
		protected void createObjectiveFunction()
		{
			objectiveFunction = new SysMLConstraint(myMOEFunction, "moe = sum of weighted accuracy, availability, and cost", "objectiveFunction", 0L);
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.javaannotations.analysis.parametrics.ObjectiveFunction
	 */
	protected void createObjectiveFunction()
	{
	}

	/**
	 * Overridable operation that should create the objective function
	 * analysis case's measure-of-effectivenss parameters by "put"-ing
	 * initialized instances of each into the {@code params} map with
	 * their appropriate "key"s, i.e. their parameter IDs. An example follows:
	 * 
	 * <pre>
	 * {@code
		&#64;MeaureOfEffectiveness
	 	FirstMeasureOfEffectiveness firstMOE;
		&#64;MeaureOfEffectiveness
	 	NextMeasureOfEffectiveness nextMOE;
		&#64;MeaureOfEffectiveness
	 	LastMeasureOfEffectiveness lastMOE;
	 		:
		&#64;Override
		protected void createMeasures()
		{
			FirstMOE = new FirstMeasureOfEffectiveness(<i>initializer</i>);
			NextMOE = new NextMeasureOfEffectiveness(<i>initializer</i>);
			LastMOE = new LastMeasureOfEffectiveness(<i>initializer</i>);
	
			params.put("FirstMeasureOfEffectiveness", FirstMOE));
			params.put("NextMeasureOfEffectiveness", NextMOE);
			params.put("LastMeasureOfEffectiveness", LastMOE);
		}
	 * }
	 * </pre>
	 * 
	 * If no measures-of-effectiveness are calculated by the objective function,
	 * then the method should be empty.
	 * 
	 * @see sysmlinjava.attributetypes.SysMLAttributeType
	 * @see sysmlinjava.javaannotations.analysis.parametrics.MeasureOfEffectiveness
	 */
	protected void createMeasuresOfEffectiveness()
	{
	}

	/**
	 * Overridable operation that should create the objective function
	 * analysis case's measure-of-performance parameters by "put"-ing
	 * initialized instances of each into the {@code params} map with
	 * their appropriate "key"s, i.e. their parameter IDs. An example follows:
	 * 
	 * <pre>
	 * {@code
		&#64;MeaureOfPerformance
	 	FirstMeasureOfPerformance firstMOP;
		&#64;MeaureOfPerformance
	 	NextMeasureOfPerformance nextMOP;
		&#64;MeaureOfPerformance
	 	LastMeasureOfPerformance lastMOP;
	 		:
		&#64;Override
		protected void createMeasures()
		{
			FirstMOP = new FirstMeasureOfPerformance(<i>initializer</i>);
			NextMOP = new NextMeasureOfPerformance(<i>initializer</i>);
			LastMOP = new LastMeasureOfPerformance(<i>initializer</i>);
	
			params.put("FirstMeasureOfPerformance", FirstMOP));
			params.put("NextMeasureOfPerformance", NextMOP);
			params.put("LastMeasureOfPerformance", LastMOP);
		}
	 * }
	 * </pre>
	 * 
	 * If no measures-of-performance are calculated by the objective function, then
	 * the method should be empty.
	 * 
	 * @see sysmlinjava.attributetypes.SysMLAttributeType
	 * @see sysmlinjava.javaannotations.analysis.parametrics.MeasureOfPerformance
	 */
	protected void createMeasuresOfPerformance()
	{
	}

	/**
	 * Overridable abstract operation that should create the objective function
	 * analysis case's measure-of-suitability parameters by "put"-ing
	 * initialized instances of each into the {@code params} map with
	 * their appropriate "key"s, i.e. their parameter IDs. An example follows:
	 * 
	 * <pre>
	 * {@code
		&#64;MeaureOfSuitability
	 	FirstMeasureOfSuitability firstMOS;
		&#64;MeaureOfSuitability
	 	NextMeasureOfSuitability nextMOS;
		&#64;MeaureOfSuitability
	 	LastMeasureOfSuitability lastMOS;
	 		:
		&#64;Override
		protected void createMeasures()
		{
			FirstMOS = new FirstMeasureOfSuitability(<i>initializer</i>);
			NextMOS = new NextMeasureOfSuitability(<i>initializer</i>);
			LastMOS = new LastMeasureOfSuitability(<i>initializer</i>);
	
			params.put("FirstMeasureOfSuitability", FirstMOS));
			params.put("NextMeasureOfSuitability", NextMOS);
			params.put("LastMeasureOfSuitability", LastMOS);
		}
	 * }
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code MeasureOfSuitability} annotation and
	 * {@code XxxxMeasureOfSuitability()} is a constructor of any
	 * {@code SysMLValueType} class. The measure constraint parameter must be a
	 * {@code SysMLValueType} type or an extension of the {@code SysMLValueType}.
	 * <p>
	 * If no measures-of-suitability are calculated by the objective function, then
	 * the method should be empty.
	 * 
	 * @see sysmlinjava.attributetypes.SysMLAttributeType
	 * @see sysmlinjava.javaannotations.analysis.parametrics.MeasureOfSuitability
	 */
	protected void createMeasuresOfSuitability()
	{
	}

	/**
	 * Name of attribute for the objective function's function variable, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String objectiveFunctionFunctionVariableName = "objectiveFunctionFunction";
	/**
	 * Name of attribute for the objective function variable, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String objectiveFunctionVariableName = "objectiveFunction";
	/**
	 * Name of method to create the objective function's function, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createObjectiveFunctionFunctionMethodName = "createObjectiveFunctionFunction";
	/**
	 * Name of method to create the objective function, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createObjectiveFunctionMethodName = "createObjectiveFunction";
	/**
	 * Name of method to create the measures of of effectivenessthe objective
	 * function, used by SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createMeasuresOfEffectivenessMethodName = "createMeasuresOfEffectiveness";
	/**
	 * Name of method to create the measures of performance of the objective
	 * function, used by SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createMeasuresOfPerformanceMethodName = "createMeasuresOfPerformance";
	/**
	 * Name of method to create the measures of suitability of the objective
	 * function, used by SysMLinJava tools, typically not needed for modeling
	 */
	public static final String createMeasuresOfSuitabilityMethodName = "createMeasuresOfSuitability";
}
