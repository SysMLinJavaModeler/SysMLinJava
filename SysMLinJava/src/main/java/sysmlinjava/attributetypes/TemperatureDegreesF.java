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
 * SysMLinJava value type for temperature in degrees fahrenheit
 * 
 * @author ModelerOne
 *
 */
public class TemperatureDegreesF extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant instance for the temperature of water freezing
	 */
	public static final TemperatureDegreesF waterFreezing = new TemperatureDegreesF(32.0);
	/**
	 * Constant instance for the temperature of water boiling
	 */
	public static final TemperatureDegreesF waterBoiling = new TemperatureDegreesF(212.0);

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public TemperatureDegreesF(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.DegreesC;
	}
}
