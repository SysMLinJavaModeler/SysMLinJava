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
 * SysMLinJava value type for energy in kilowatt-hours
 * 
 * @author ModelerOne
 *
 */
public class EnergyKilowattHours extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 */
	public EnergyKilowattHours(double value)
	{
		super(value);
	}

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 * @param name unique name
	 * @param id unique identifier
	 */
	public EnergyKilowattHours(double value, String name, Long id)
	{
		super(value, name, id);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new EnergyKilowattHours(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KiloWattHours;
	}

	@Override
	public String toString()
	{
		return String.format("EnergyKilowattHours [value=%s, units=%s]", value, units);
	}
}
