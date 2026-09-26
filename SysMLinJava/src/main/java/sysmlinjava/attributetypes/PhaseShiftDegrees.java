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
package sysmlinjava.attributetypes;

import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava's value type for phase shift in degrees.
 * 
 * @author ModelerOne
 *
 */
public class PhaseShiftDegrees extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor with primitive double for initial value
	 * 
	 * @param value phase shift in degrees
	 */
	public PhaseShiftDegrees(double value)
	{
		super(value);
	}

	/**
	 * Constructor with Real for initial value
	 * 
	 * @param degrees phase shift in degrees
	 */
	public PhaseShiftDegrees(RReal degrees)
	{
		super(degrees.value);
	}

	/**
	 * Constructor with another phase shiftDegrees for initial value
	 * 
	 * @param degrees phase shift in phase shiftDegrees
	 */
	public PhaseShiftDegrees(PhaseShiftDegrees degrees)
	{
		super(degrees.value);
	}

	/**
	 * Returns whether the specified value is equal to this value
	 * 
	 * @param degrees phase shift value to compare to this value
	 * @return true if the specified value is equal to this value, false otherwise
	 */
	@Operation
	public boolean equalTo(PhaseShiftDegrees degrees)
	{
		return this.value == degrees.value;
	}

	/**
	 * Returns the opposite (180 degrees greater) phase shift
	 * 
	 * @return opposite phase shift
	 */
	@Operation
	public PhaseShiftDegrees opposite()
	{
		return new PhaseShiftDegrees((value += 180) % 360);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Degrees;
	}

	@Override
	public String toString()
	{
		return String.format("PhaseShiftDegrees [value=%s, units=%s]", value, units);
	}
}
