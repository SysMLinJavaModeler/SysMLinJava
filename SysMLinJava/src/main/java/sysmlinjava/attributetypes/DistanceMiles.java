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
 * SysMLinJava value type for distance in miles.
 * 
 * @author ModelerOne
 *
 */
public class DistanceMiles extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value distance in miles
	 */
	public DistanceMiles(double value)
	{
		super(value);
	}

	/**
	 * Constructor - conversion
	 * 
	 * @param kilometers distance instance to converted into miles instance
	 */
	public DistanceMiles(DistanceKilometers kilometers)
	{
		super(kilometers.value / 1.609344);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new DistanceMiles(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Miles;
	}

	@Override
	public String toString()
	{
		return String.format("DistanceMiles [value=%s, units=%s]", value, units);
	}
}
