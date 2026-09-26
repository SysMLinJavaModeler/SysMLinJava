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
 * SysMLinJava value type for distance in meters.
 * 
 * @author ModelerOne
 *
 */
public class DistanceMeters extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant for meters per mile
	 */
	public static final DistanceMeters perMile = new DistanceMeters(1609.344);

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value distance in meters
	 */
	public DistanceMeters(double value)
	{
		super(value);
	}

	/**
	 * Constructor for Real initial value
	 * 
	 * @param value initial distance in meters
	 */
	public DistanceMeters(RReal value)
	{
		super(value.value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyFrom Distance whose value is to be used for initial value of copy
	 */
	public DistanceMeters(DistanceMeters copyFrom)
	{
		super(copyFrom);
	}

	/**
	 * Constructor - default 0
	 * 
	 */
	public DistanceMeters()
	{
		super(0);
	}
	
	/**
	 * Returns whether or not this distance is between (in range of) the specified
	 * distances
	 * 
	 * @param distance1 lesser distance in range to be used
	 * @param distance2 greater distance in range to be used
	 * @return true if this distance is between the two, false otherwise.
	 */
	public boolean isBetween(DistanceMeters distance1, DistanceMeters distance2)
	{
		return value > distance1.value && value < distance2.value;
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new DistanceMeters(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Meters;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("DistanceMeters [value=");
		builder.append(value);
		builder.append(", units=");
		builder.append(units);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}
