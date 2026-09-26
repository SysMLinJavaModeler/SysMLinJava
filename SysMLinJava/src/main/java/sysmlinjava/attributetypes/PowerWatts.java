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
package sysmlinjava.attributetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava's value type for power in watts
 * 
 * @author ModelerOne
 */
public class PowerWatts extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant instance for zero power
	 */
	public static final PowerWatts zeroPower = new PowerWatts(0);

	/**
	 * Constructor for primitive double intial value in watts
	 * 
	 * @param value initial value
	 */
	public PowerWatts(double value)
	{
		super(value);
	}

	/**
	 * Constructor for Real intial value in watts
	 * 
	 * @param value initial value
	 */
	public PowerWatts(RReal value)
	{
		super(value.value);
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public PowerWatts(PowerWatts copied)
	{
		this(copied.value);
	}

	/**
	 * Constructor - default of zero
	 */
	public PowerWatts()
	{
		super(0);
	}

	/**
	 * Constructor
	 * 
	 * @param value initial value
	 * @param name  unique name
	 * @param id    unique identifier
	 */
	public PowerWatts(double value, String name, Long id)
	{
		super(value, name, id);
	}

	/**
	 * Returns instance with specified values for potential and current
	 * 
	 * @param potential potential value to used for this initial value
	 * @param current   current value to used for this initial value
	 * @return instance with specified values for potential and current
	 */
	public static PowerWatts of(PotentialElectricalVolts potential, CurrentAmps current)
	{
		return new PowerWatts(potential.value * current.value);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new PowerWatts(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Watts;
	}

	@Override
	public String toString()
	{
		return String.format("PowerWatts [value=%s, units=%s]", value, units);
	}
}
