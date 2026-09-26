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

import static java.lang.Math.PI;
import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava's value type for phase shift in radians.
 * 
 * @author ModelerOne
 *
 */
public class PhaseShiftRadians extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor with primitive double for initial value
	 * 
	 * @param value phase shift in radians
	 */
	public PhaseShiftRadians(double value)
	{
		super(value);
	}

	/**
	 * Constructor with Real for initial value
	 * 
	 * @param value phase shift in radians
	 */
	public PhaseShiftRadians(RReal value)
	{
		super(value.value);
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public PhaseShiftRadians(PhaseShiftRadians copied)
	{
		super(copied);
	}

	/**
	 * Constructor for conversion from degrees
	 * 
	 * @param degrees initial phase shift in degrees
	 */
	public PhaseShiftRadians(PhaseShiftDegrees degrees)
	{
		super(degrees.value);
	}

	/**
	 * Returns the opposite (180 degrees or PI radians greater) phase shift
	 * 
	 * @return opposite phase shift
	 */
	@Operation
	public PhaseShiftRadians opposite()
	{
		return new PhaseShiftRadians((value += PI) % (2 * PI));
	}

	/**
	 * Returns this phase shift as phase shift in degrees
	 * 
	 * @return phase shift in degrees
	 */
	@Operation
	public PhaseShiftDegrees toDegrees()
	{
		return new PhaseShiftDegrees(Math.toDegrees(value));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Radians;
	}

	@Override
	public String toString()
	{
		return String.format("PhaseShiftRadians [value=%s, units=%s]", value, units);
	}

}
