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
 * SysMLinJava value type for a volume in cubic-meters
 * 
 * @author ModelerOne
 *
 */
public class VolumeMetersCubic extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public VolumeMetersCubic(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied value to be used for initial value of copy
	 */
	public VolumeMetersCubic(VolumeMetersCubic copied)
	{
		super(copied);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new VolumeMetersCubic(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.CubicMeter;
	}
}
