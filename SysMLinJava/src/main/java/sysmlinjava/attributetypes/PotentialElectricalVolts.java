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
 * SysMLinJava's value type for electrical potential (voltage) in volts
 * 
 * @author ModelerOne
 *
 */
public class PotentialElectricalVolts extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constuctor - initial value
	 * 
	 * @param value double value to be used as the initial value
	 */
	public PotentialElectricalVolts(double value)
	{
		super(value);
	}

	/**
	 * Constuctor - copy
	 * 
	 * @param copied instance whose value is to be used as the initial value of this
	 *               copy
	 */
	public PotentialElectricalVolts(PotentialElectricalVolts copied)
	{
		super(copied);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Volts;
	}

}
