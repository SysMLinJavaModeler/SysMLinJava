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

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import sysmlinjava.javaannotations.actions.Calculation;
import sysmlinjava.occurrences.SysMLSnapshot;

/**
 * SysMLinJava representation of the SysML calculation occurrence.
 * <p>
 * <b>Note</b>: This form of the SysML calculation is provided to enable it to
 * occur at times that are different from the occurrence of the part or port
 * that invokes/performs the calculation. A calculation whose occurrence is the
 * same as the occurrence of the part or port that performs the calculation
 * should be specified by a method declared in the part or port class and this
 * method should be annotated with the &#64;{@code Calculation} annotation.
 * <p>
 * {@code SysMLCalculation} is a specialized {@code SysMLAction} with an
 * occurence specification, i.e. with "snapshots" in time in which the action is
 * performed. The action occurremce should be declared as a field annotated as
 * &#64;{@code Calculation} with a variable whose type is a
 * {@code SysMLCalculation} or extension therof. The variable should be
 * created/initialized in the {@code createCalculations()} method of the part,
 * port, or other {@code StateBehaviorContext} implementation in which it is a
 * feature.
 * <p>
 * As an extension of the {@code SysMLAction}, the {@code SysMLCalculation} or
 * extnsion thereof can be specified with occurrence times, i.e. times at which
 * the calculation is performed. To make this calculation executable, the
 * {@code SysMLCalculation} declares a constructor that creates a
 * {@code Runnable} that invokes the specified {@code function} variable - a
 * functional interface - to perform the specified calculation. The functional
 * interface may simply invoke a method in the class in which it is created, or
 * it may perform the entire calculation itself. In any case, the calculation
 * will be performed (executed) in its own thread of execution in the specified
 * thread pool at the specified "snapshot" times. An example is as follows:
 * 
 * <pre>{@code
	public class DistanceCalculator extends SysMLPart
	{
		&#64;Attribute
		DistanceKilometers eastwardDistance;
		&#64;Attribute
		DistanceKilometers northwardDistance;
		&#64;Attribute
		DistanceKilometers shortestDistance;
		
		&#64;CalculationFunction
		SysMLCalculationFunction shortestDistanceCalculationFuncion;
		&#64;Calculation
		SysMLCalculation shortestDistanceCalculation;
			:
		protected void createCalculationFunctions()
		{
			shortestDistanceCalculationFuncion = (SysMLCalculationFunction)() ->
			{
				shortestDistance = eastwardDistance.squared().added(westwardDistance.squared().squareRoot();
			};
		}
			:
		protected void createCalculations()
		{
			shortestDistanceCalculation = new SysMLCalculation(shortestDistanceCalculationFuncion, "ShortestDistance", 0L);
		}
	}
	}</pre>
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.SysMLPart#createCalculationFunctions
 * @see sysmlinjava.parts.SysMLPart#createCalculations
 */
public class SysMLCalculation extends SysMLAction
{

	/**
	 * Constructor for a specifed calculation function that occurs (is to be
	 * performed) at the specified times. Constructor for use by extension classes
	 * that define the (sub) calculations and their functions via the
	 * {@code createCalculationFunctions()}, and {@code createCalculations()}
	 * method. Note the calculation {@code function} is created/intialized via an
	 * override of the {@code createFunction()} method.
	 * 
	 * @param function    {@code SysMLCalculationFunction} instance that performs
	 *                    the calculation action, i.e. instance of executable lambda
	 *                    function for the calculation.
	 * @param occurrences the occurrences of the calculation in terms of snapshot
	 *                    times the calculation is to be performed
	 * @param executor    executor which is to "run" the specified function for the
	 *                    calculation at it specified occurrence times. This is
	 *                    usually the thread pool executor of the part that will
	 *                    invoke the action.
	 * @param name        name for the calculation
	 * @param id          unique identifier of the calculation
	 */
	public SysMLCalculation(SysMLCalculationFunction function, List<SysMLSnapshot> occurrences, ScheduledThreadPoolExecutor executor, String name, Long id)
	{
		super(function, occurrences, executor, name, id);

		createCalculationFunctions();
		createCalculations();
	}

	/**
	 * Constructor for a specified calculation function.
	 * 
	 * @param function {@code SysMLCalculationFunction} instance that performs the
	 *                 calculation action, i.e. instance of executable lambda
	 *                 function for the calculation.
	 * @param name     name for the calculation
	 * @param id       unique identifier of the calculation
	 */
	public SysMLCalculation(SysMLCalculationFunction function, String name, Long id)
	{
		super(function, name, id);

		createCalculationFunctions();
		createCalculations();
	}

	/**
	 * Constructor for use by extension classes that define the (sub) calculations
	 * and their functions via the {@code createCalculationFunctions()}, and
	 * {@code createCalculations()} method. Note the calculation {@code function} is
	 * created/intialized via an override of the {@code createFunction()} method.
	 * 
	 * @param name unique name of the action
	 * @param id   unique numerical id of the action
	 */
	protected SysMLCalculation(String name, Long id)
	{
		super(name, id);

		createCalculationFunctions();
		createCalculations();
	}

	/**
	 * Overridable method to perform the calculation. This method may be overridden
	 * in lieu of defining the {@code function} of the calculation to be a
	 * {@code SysMLCalculationFunction}. The default method simply invokes the
	 * {@code function} if it has been assigned a value, presumably in an override
	 * of the {@code createFunction()} method.
	 */
	@Calculation
	@Override
	protected void perform()
	{
		if (function != null && function instanceof SysMLCalculationFunction calculationFunction)
			calculationFunction.perform();
	}

	/**
	 * Overridable method to create/initialize the declared calculation functions.
	 * An example follows.
	 * 
	 * <pre>{@code
		public class DistanceCalculator extends SysMLPart
		{
			&#64;Attribute
			DistanceKilometers eastwardDistance;
			&#64;Attribute
			DistanceKilometers northwardDistance;
			&#64;Attribute
			DistanceKilometers shortestDistance;
			
			&#64;CalculationFunction
			SysMLCalculationFunction shortestDistanceCalculationFuncion;
				:
			protected void createCalculationFunctions()
			{
				shortestDistanceCalculationFuncion = (SysMLCalculationFunction)() ->
				{
					shortestDistance = eastwardDistance.squared().added(westwardDistance.squared().squareRoot();
				};
			}
				:
		}
		}</pre>
	 * 
	 * @author ModelerOne
	 * @see sysmlinjava.parts.SysMLPart#createCalculationFunctions
	 * @see sysmlinjava.parts.SysMLPart#createCalculations
	 */
	protected void createCalculationFunctions()
	{
	}

	/**
	 * Overridable method to create/initialize the declared calculations. An example
	 * follows.
	 * 
	 * <pre>{@code
		public class DistanceCalculator extends SysMLPart
		{
			&#64;Attribute
			DistanceKilometers eastwardDistance;
			&#64;Attribute
			DistanceKilometers northwardDistance;
			&#64;Attribute
			DistanceKilometers shortestDistance;
			
			&#64;CalculationFunction
			SysMLCalculationFunction shortestDistanceCalculationFuncion;
			&#64;Calculation
			SysMLCalculation shortestDistanceCalculation;
				:
			protected void createCalculationFunctions()
			{
				shortestDistanceCalculationFuncion = (SysMLCalculationFunction)() ->
				{
					shortestDistance = eastwardDistance.squared().added(westwardDistance.squared().squareRoot();
				};
			}
				:
			protected void createCalculations()
			{
				shortestDistanceCalculation = new SysMLCalculation(shortestDistanceCalculationFuncion, "ShortestDistance", 0L);
			}
		}
		}</pre>
	 * 
	 * @author ModelerOne
	 * @see sysmlinjava.parts.SysMLPart#createCalculationFunctions
	 * @see sysmlinjava.parts.SysMLPart#createCalculations
	 */
	protected void createCalculations()
	{
	}

	/**
	 * Name of method to create calculation functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createCalculationFunctionsMethodName = "createCalculationFunctions";
	/**
	 * Name of method to create calculations, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createCalculationsMethodName = "createCalculations";
}
