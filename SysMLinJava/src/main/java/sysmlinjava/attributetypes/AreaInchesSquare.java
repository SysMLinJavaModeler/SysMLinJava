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
 * SysMLinJava value type for area in square inches
 * 
 * @author ModelerOne
 *
 */
public class AreaInchesSquare extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489111777283872L;

	/**
	 * Constructor
	 * 
	 * @param value initial value for square inches of area
	 */
	public AreaInchesSquare(double value)
	{
		super(value);
	}

	/**
	 * Copy constructor
	 * 
	 * @param copyFrom value from which this is to be copied from
	 */
	public AreaInchesSquare(AreaInchesSquare copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.InchesSquare;
	}
}
