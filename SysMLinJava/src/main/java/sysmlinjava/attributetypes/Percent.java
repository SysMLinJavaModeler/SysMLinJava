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

import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for percent as real number 0.0 thru 100.0.
 * 
 * @author ModelerOne
 *
 */
public class Percent extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be the initial value
	 */
	public Percent(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied value to be used as the initial value of the copy
	 */
	public Percent(Percent copied)
	{
		super(copied);
	}

	/**
	 * Percent as fraction (0.0 thru 1.0)
	 * 
	 * @return value / 100
	 */
	@Operation
	public double asFraction()
	{
		return value / 100;
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Percent;
	}
}
