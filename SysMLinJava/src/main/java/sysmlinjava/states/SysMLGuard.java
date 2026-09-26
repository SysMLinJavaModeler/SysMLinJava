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
package sysmlinjava.states;

import java.util.Optional;

import sysmlinjava.constraint.SysMLConstraint;
import sysmlinjava.events.SysMLEvent;

/**
 * SysMLinJava's representation of the SysML state machine transition's guard.
 * The {@code SysMLGuard} is a specialized {@code SysMLConstraint} whose
 * {@code function} contains a {@code SysMLGuardCondition}. The guard condition
 * is a Java functional interface (lambda statement} of the logic that
 * determines whether the guard allows the transition to occur. The
 * {@code SysMLGuard} is a {@code final} class and therefore cannot be extended.
 * It must be used as-is to specify a SysML guard for a transition. An example
 * follows.
 * 
 * <pre>
		:
	&#64;Guard
	public SysMLGuard gasToLiquidGuard;
	&#64;Guard
	public SysMLGuard liquidToIceGuard;
	&#64;Guard
	public SysMLGuard iceToLiquidGuard;
	&#64;Guard
	public SysMLGuard liquidToGasGuard;
	&#64;Guard
	public SysMLGuard gasToDecomposedGuard;
		:
 	&#64;Override
	protected void createGuards()
	{
		gasToLiquidGuard = new SysMLGuard(context, gasToLiquidGuardCondition, "isGasToLiquid");
		liquidToIceGuard = new SysMLGuard(context, liquidToIceGuardCondition, "isLiquidToIce");
		iceToLiquidGuard = new SysMLGuard(context, iceToLiquidGuardCondition, "isIceToLiquid");
		liquidToGasGuard = new SysMLGuard(context, liquidToGasGuardCondition, "isLiquidToGas");
		gasToDecomposedGuard = new SysMLGuard(context, gasToDecomposedGuardCondition, "isGasToDecomposed");
	}
		:
 * </pre>
 * 
 * @author ModelerOne
 * @see SysMLGuardCondition
 */
public final class SysMLGuard extends SysMLConstraint
{
	/**
	 * Optional state behavior context within whose context this guard is to
	 * perform.
	 */
	public Optional<? extends StateBehaviorContext> context;

	/**
	 * Constructor.
	 * 
	 * @param context   The context within which this guard operates
	 * @param condition The condition to be satisfied
	 * @param name      The name of the guard
	 */
	public SysMLGuard(Optional<? extends StateBehaviorContext> context, SysMLGuardCondition condition, String name)
	{
		super(name, 0L);
		this.context = context;
		this.function = Optional.of(condition);
	}

	/**
	 * Boolean operation to be called by the state machine to determine if the guard
	 * condition is satisfied, therby enabling the parent transition to occur. The
	 * operation will invoke the {@code SysMLGuardCondition}, if provided, or return
	 * false otherwise.
	 * 
	 * @param currentEvent Optional event that triggered the transition that hosts
	 *                     the guard
	 * @return Whether the condition is satisfied, i.e. the
	 *         {@code SysMLGuardCondition} is provided and satisfied or false
	 *         otherwise
	 */
	public boolean isSatisfied(Optional<? extends SysMLEvent> currentEvent)
	{
		boolean result = false;
		if (function.isPresent() && function.get() instanceof SysMLGuardCondition)
			result = ((SysMLGuardCondition) function.get()).isSatisfied(currentEvent, context);
		return result;
	}

	/**
	 * Provides the identityString for this guard, either the specified name, if
	 * provided, or the simple class name - {@code SysMLGuard}.
	 * 
	 * @return the guard's identity string.
	 */
	@Override
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}
}
