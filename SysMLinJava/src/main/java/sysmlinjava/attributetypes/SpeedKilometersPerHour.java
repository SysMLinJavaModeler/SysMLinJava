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

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for speed in kilometers/hour
 * 
 * @author ModelerOne
 *
 */
public class SpeedKilometersPerHour extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor - initial value
	 * 
	 * @param value double value for the initial value
	 */
	public SpeedKilometersPerHour(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param milesPerHour speed value to be used as initial value of this copy
	 */
	public SpeedKilometersPerHour(SpeedMilesPerHour milesPerHour)
	{
		super(milesPerHour.value * 1.609344);
	}

	/**
	 * Constructor - default zero
	 */
	public SpeedKilometersPerHour()
	{
		super(0);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new SpeedKilometersPerHour(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KilometersPerHour;
	}

	@Override
	public String toString()
	{
		return String.format("SpeedKilometersPerHour [value=%s, units=%s]", value, units);
	}
}
