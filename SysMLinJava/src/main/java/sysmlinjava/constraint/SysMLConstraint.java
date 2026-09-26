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
package sysmlinjava.constraint;

import java.util.Optional;

import sysmlinjava.annotations.SysMLDocumentation;
import sysmlinjava.javaannotations.constraint.ConstraintFunction;
import sysmlinjava.javaannotations.constraint.ConstraintText;
import sysmlinjava.occurrences.SysMLOccurrence;

/**
 * SysMLinJava representation of the SysML constraint. {@code SysMLConstraint}
 * is an occurrence with an optional function and text that define a constraint
 * on a model element. The constraint {@code function} is a functional interface
 * specification that is an extension of the {@code ConstraintFunction}
 * interface. The {@code text} is a documenation that contains the function code
 * and/or specifies the constraint in plain text.
 * <p>
 * The {@code SysMLConstraint} should be declared as a field in the extended
 * {@code SysMLPart}, {@code SysMLPort}, {@code SysMLAction}, {@code SysMLItem},
 * or collection {@code SysMLRequirementsCollection},
 * {@code SysMLViewpointsCollection}, etc. class. The field should be annotated
 * with the {@code Constraint} annotation. It should then created/initialized
 * with an object creation expression either as part of the field declaration or
 * in the override of the {@code SysMLPart}, {@code SysMLPort},
 * {@code SysMLAction}, or {@code SysMLItem} class's {@code createConstraints()}
 * method. An example is as follows:
 * 
 * <pre>{@code
		public MyPart extends SysMLPart
		{
	     &#64;Attribute
	     protected PowerWatts powerIn;
	     &#64;Attribute
	     protected HeatWatts heatOut;
	     &#64;Attribute
	     protected Percent mechanical;
	         :
			&#64;FunctionalInterface
			public interface PercentMechanicalFunction extends SysMLConstraintFunction
			{
				Percent percentMechanical(PowerWatts powerIn, HeatWatts heatOut);
			}
	         :
	     &#64;ConstraintFunction
	     protected SysMLConstraintFunction firstConstraintFunction;

	     &#64;Constraint
	     protected SysMLConstraint firstConstraint;

	     &#64;Override
	     protected void createConstraintFunctions()
	     {
	         firstConstraint = (PercentMechanicalFunction)(powerIn, heatOut) ->
	         {
	         	return new Percent(powerIn.subtracted(heatOut).dividedBy(powerIn));
	         });
	     }

	     &#64;Override
	     protected void createConstraints()
	     {
	         firstConstraint = new SysMLConstraint(Optional.of(firstConstraintFunction), "mechanical percent = powerIn - heatOut / powerIn", "percentMechanicla", 0L);
	     }
     }}
 * </pre>
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.SysMLPart#createConstraints
 */
public class SysMLConstraint extends SysMLOccurrence
{
	/**
	 * Optional function to specify the constraint, i.e. instance of an extension of
	 * the (@code SysMLConstraintFunction} interface that is created (as a Lambda
	 * function), usually in the scope/context of the model element in which it will
	 * execute. This definition of the constraint is an alternative is to defining
	 * as simple text in {@code text} variable.
	 * <p>
	 * Note that some model views can only display constraints as text, in which
	 * case it may be desirable to duplicate the code assigned to the
	 * {@code function} variable as simple text in the {@code text} variable to
	 * enable its display by these views.
	 */
	@ConstraintFunction
	public Optional<? extends SysMLConstraintFunction> function;

	/**
	 * Documentation text to specify the constraint, i.e. instance of (@code
	 * SysMLDocumentation} that contains a textual representation of the constraint.
	 * This documentation of the constraint may be an alternative way of defining
	 * the formal lambda function in {@code function} variable and/or simply be an
	 * informal text-based specification of the constraint.
	 */
	@ConstraintText
	public SysMLDocumentation text;

	/**
	 * Constructor for specified function and occurence
	 * 
	 * @param function optional name of instance of executable lambda function that
	 *                 defines the executable constraint, i.e. instance of the java
	 *                 code for a lambda function for a functional interface that
	 *                 extends the {@code SysMLConstraintFunction} interface.
	 * @param text     documentation that specifies the constraint as free-form text.
	 * @param name     unique name for the constraint
	 * @param id       unique identifier of the constraint
	 */
	public SysMLConstraint(Optional<? extends SysMLConstraintFunction> function, SysMLDocumentation text, String name, long id)
	{
		super(name, id);
		this.function = function;
		this.text = text;
	}

	/**
	 * Constructor for empty constraint with name and id, for use in creating
	 * instance of extension/specialization of {@code SysMLConstraint}
	 * 
	 * @param name name for the constraint
	 * @param id   unique identifier of the constraint
	 */
	protected SysMLConstraint(String name, long id)
	{
		super(name, id);
		this.function = Optional.empty();
		this.text = SysMLDocumentation.notSpecified;

		createAttributes();
		createFunction();
		createText();
		createConstraintFunctions();
		createConstraintTexts();
		createConstraints();
	}

	/**
	 * Overridable method to execute the constraint to determine satisfac. This
	 * method may be overridden in lieu of defining the @{@code function}, i.e. the
	 * {@code BasicConstraintFunction} for the constraint. The default method is to
	 * invoke the {@code function} if it has been assigned a value of type
	 * {@code ConstraintFunction}, presumably in an override of the
	 * {@code createFunction()} method.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @return constraint result
	 */
	protected boolean satisfied()
	{
		boolean result = false;
		if (function.isPresent() && function.get() instanceof BasicConstraintFunction constraintFunction)
			result = constraintFunction.satisfied();
		else
			logger.warning("constraint function (instance of BasicConstraintFunction) is not present to perform constraint");
		return result;
	}

	/**
	 * Creates the attributes, if any, that are used in specifying the
	 * constraint.This method is invoked in an extended type of
	 * {@code SysMLConstraint}. An example follows.
	 * 
	 * <pre>{@code
		public class SWAPConstraint extends SysMLConstraint
		{
				:
			&#64;Attribute
			PowerWatts maxPowerIn;
			&#64;Attribute
			HeatWatts minHeatOut;
				:
	
			&#64;Override
			protected void createAttributes()
			{
				maxPowerIn = new PowerWatts(1500);
				minHeatOut = new HeatWatts(1500);
			}
				:
			&#64;Override
			protected void createFunction
			{
				function = Optional.of((BasicConstraintFunction)() -> minHeatOut.greaterThan(maxPowerIn));
			}
		}}
	 * </pre>
	 */
	protected void createAttributes()
	{
	}

	/**
	 * Overridable operation that creates the constraint's function, i.e. an
	 * executable lambda function that resolves the constraint. This method is
	 * invoked in an <b>extended</b> type of {@code SysMLConstraint}. It initializes
	 * the {@code function} as executable code that performs the constraint. The
	 * method must consist of a statement that assigns the executable code as a
	 * lambda function to the {@code function} variable. The lambda function must be
	 * type-cast to the functional interface that defines the constraint in terms of
	 * its inputs and output. An example follows.
	 * 
	 * <pre>{@code
		public class PlotConstraint extends SysMLConstraint
		{
			&#64;Attribute
			IInteger x;
			&#64;Attribute
			IInteger y;
	
			&#64;FunctionalInterface
			public interface MyConstraintFunction extends SysMLConstraintFunction
			{
				int getY(int x);
			}
	
	
			&#64;Override
			public void createAttributes()
			{
				x = 25;
				y = 10;
			}
	
			&#64;Override
			public void createFunction()
			{
				function = Optional.of(MyConstraintFunction)(x) ->
				{
					int y = 10 * x.value + 25;
					return y;
				};
			}
		}}
	 * </pre>
	 */
	protected void createFunction()
	{
	}

	/**
	 * Overridable operation that creates the (sub)constraint as a simple text
	 * document. This method is invoked in an <b>extended</b> type of
	 * {@code SysMLConstraint}. It creates the {@code text} variable and initializes
	 * it with a simple textual representation of the constraint. An example
	 * follows.
	 * 
	 * <pre>{@code
		{
				:
			&#64;ConstraintText
			SysMLDocumentation text;
				:
			public void createText()
			{
				text = new SysMLDocumentation("minimum heat shall be less than maximum power"));
			}
				:
		}}
	 * </pre>
	 */
	protected void createText()
	{
	}

	/**
	 * Overridable method to create/initialize (sub)constraint functions of this
	 * constraint's function, i.e. constraint functions that are invoked/referenced
	 * by this constraint's function and/or each other. An example follows
	 * 
	 * <pre>{@code
	 }</pre>
	 */
	protected void createConstraintFunctions()
	{
	}

	/**
	 * Overridable method to create/initialize (sub)constraint texts of this
	 * constraint's text, i.e. constraint texts that are referenced by this
	 * constraint's text and/or each other. An example follows.
	 * 
	 * <pre>{@code
	 }</pre>
	 */
	protected void createConstraintTexts()
	{
	}

	/**
	 * Overridable method to create/initialize (sub)constraints of this constraint,
	 * i.e. constraints that are invoked/referenced by this constraint and/or each
	 * other. An example follows.
	 * 
	 * <pre>{@code
	 }</pre>
	 */
	protected void createConstraints()
	{
	}

	/**
	 * Name of function variable, used by SysMLinJava tools, typically not needed
	 * for modeling
	 */
	public static final String functionVariableName = "function";
	/**
	 * Name of text variable, used by SysMLinJava tools, typically not needed for
	 * modeling
	 */
	public static final String textVariableName = "text";

	/**
	 * Name of method to create attributes, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createAttributesMethodName = "createAttributes";
	/**
	 * Name of method to create constraint's function, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createFunctionMethodName = "createFunction";
	/**
	 * Name of method to create constraint's text, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createTextMethodName = "createText";
	/**
	 * Name of method to create sub-constraint functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintFunctionsMethodName = "createConstraintFunctions";
	/**
	 * Name of method to create sub-constraint texts, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintTextsMethodName = "createConstraintTexts";
	/**
	 * Name of method to create sub-constraints, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintsMethodName = "createConstraints";
}