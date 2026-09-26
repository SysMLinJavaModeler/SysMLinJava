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
 * SysMLinJava value type for speed in meters/second
 * 
 * @author ModelerOne
 *
 */
public class SpeedMetersPerSecond extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant value for speed per one mile per hour - for conversion to speed in
	 * miles per hour
	 */
	public static final SpeedMetersPerSecond perMilesPerHour = new SpeedMetersPerSecond(1609.344 / 3600);

	/**
	 * Constant value for zero meters per second
	 */
	public static final SpeedMetersPerSecond zero = new SpeedMetersPerSecond(0);

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public SpeedMetersPerSecond(double value)
	{
		super(value);
	}

	/**
	 * Constructor
	 * 
	 * @param value RReal value to be used for this initial value
	 */
	public SpeedMetersPerSecond(RReal value)
	{
		this(value.value);
	}

	/**
	 * Constructor - conversion
	 * 
	 * @param milesPerHour speed instance to be converted into this speed instance
	 */
	public SpeedMetersPerSecond(SpeedMilesPerHour milesPerHour)
	{
		super(milesPerHour.value * 0.44704);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new SpeedMetersPerSecond(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.MeterPerSecond;
	}

	@Override
	public String toString()
	{
		return String.format("SpeedMetersPerSecond [value=%s]", value);
	}
}
