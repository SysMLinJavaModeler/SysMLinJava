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
 * SysMLinJava value type for density in kilograms/cubic-meter
 * 
 * @author ModelerOne
 *
 */
public class DensityKilogramsPerMeterCubic extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value of the initial value
	 */
	public DensityKilogramsPerMeterCubic(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyFrom instance whose value is to be the initial value of this copy
	 */
	public DensityKilogramsPerMeterCubic(DensityKilogramsPerMeterCubic copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new DensityKilogramsPerMeterCubic(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KilogramsPerMeterCubic;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("DensityKilogramsPerMeterCubic [value=");
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
