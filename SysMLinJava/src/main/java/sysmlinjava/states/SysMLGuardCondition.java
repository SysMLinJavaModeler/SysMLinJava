/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
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
package sysmlinjava.states;

import java.util.Optional;

import sysmlinjava.constraint.SysMLConstraintFunction;
import sysmlinjava.events.SysMLEvent;

/**
 * Functional interface specifying whether the guard allows the transition to
 * occur. This is the condition function to be performed by the transition's
 * {@code SysMLGuard} element.
 * <p>
 * The {@code SysMLGuardCondition} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code GuardCondition} annotation. It should then be implemented as an
 * instance of a Lambda function in the override of the
 * {@code SysMLStateMachine}'s {@code createGuardConditions()} operation to
 * provide the function with access to the state machine's properties. An
 * example follows.
 * 
 * <pre>
		:
	&#64;GuardCondition
	public SysMLGuardCondition gasToLiquidGuardCondition;
	&#64;GuardCondition
	public SysMLGuardCondition liquidToIceGuardCondition;
	&#64;GuardCondition
	public SysMLGuardCondition iceToLiquidGuardCondition;
	&#64;GuardCondition
	public SysMLGuardCondition liquidToGasGuardCondition;
	&#64;GuardCondition
	public SysMLGuardCondition gasToDecomposedGuardCondition;
		:
	&#64;Override
	protected void createGuardConditions()
	{
		gasToLiquidGuardCondition = (event, context) ->
		{
			TemperatureDegreesC temp = ((H2O)context.get()).temp;
			TemperatureDegreesC gasTemp = (((H2O)context.get()).gasTemp);
			return temp.lessThan(gasTemp);
		};
		liquidToIceGuardCondition = (event, context) ->
		{
			TemperatureDegreesC temp = ((H2O)context.get()).temp;
			TemperatureDegreesC iceTemp = (((H2O)context.get()).iceTemp);
			return temp.lessThanOrEqualTo(iceTemp);
		};
		iceToLiquidGuardCondition = (event, context) ->
		{
			TemperatureDegreesC temp = ((H2O)context.get()).temp;
			TemperatureDegreesC iceTemp = ((H2O)context.get()).iceTemp;
			LatentHeatKilojoulesPerKilogram latentHeat = ((H2O)context.get()).latentHeat;
			LatentHeatKilojoulesPerKilogram minLatentHeat = ((H2O)context.get()).minLatentHeatCondensation;
			return temp.greaterThan(iceTemp) &#38;&#38; latentHeat.greaterThanOrEqualTo(minLatentHeat);
		};
		liquidToGasGuardCondition = (event, context) ->
		{
			TemperatureDegreesC temp = ((H2O)context.get()).temp;
			TemperatureDegreesC gasTemp = ((H2O)context.get()).gasTemp;
			LatentHeatKilojoulesPerKilogram latentHeat = ((H2O)context.get()).latentHeat;
			LatentHeatKilojoulesPerKilogram minLatentHeat = ((H2O)context.get()).minLatentHeatEvaporation;
			return temp.greaterThanOrEqualTo(gasTemp) &#38;&#38; latentHeat.greaterThanOrEqualTo(minLatentHeat);
		};
		gasToDecomposedGuardCondition = (event, context) ->
		{
			TemperatureDegreesC temp = ((H2O)context.get()).temp;
			TemperatureDegreesC maxGasTemp = ((H2O)context.get()).decomposedTemp;
			return temp.greaterThanOrEqualTo(maxGasTemp);
		};
	}
		:
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLGuardCondition extends SysMLConstraintFunction
{
	/**
	 * Returns whether or not the guard's condition is satisfied for the specified
	 * event and context
	 * 
	 * @param currentEvent the event to be used in the condition
	 * @param context the context to be used in the condition
	 * @return whether or not the condition is satisfied
	 */
	boolean isSatisfied(Optional<? extends SysMLEvent> currentEvent, Optional<? extends StateBehaviorContext> context);
}