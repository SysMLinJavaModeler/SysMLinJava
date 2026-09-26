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
 * SysMLinJava value type for speed in miles/hour
 * 
 * @author ModelerOne
 *
 */
public class SpeedMilesPerHour extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public SpeedMilesPerHour(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied instance whose value is to used as initial value of this copy
	 */
	public SpeedMilesPerHour(SpeedMilesPerHour copied)
	{
		super(copied);
	}

	/**
	 * Constructor - conversion from kph
	 * 
	 * @param kilometersPerHour value to be converted by this instance
	 */
	public SpeedMilesPerHour(SpeedKilometersPerHour kilometersPerHour)
	{
		super(kilometersPerHour.value / 1.609344);
	}

	/**
	 * Constructor - conversion from mps
	 * 
	 * @param metersPerSecond value to be converted by this instance
	 */
	public SpeedMilesPerHour(SpeedMetersPerSecond metersPerSecond)
	{
		super(metersPerSecond.value / 0.44704);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new SpeedMilesPerHour(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.MilesPerHour;
	}

	@Override
	public String toString()
	{
		return String.format("SpeedMilesPerHour [value=%s]", value);
	}
}
