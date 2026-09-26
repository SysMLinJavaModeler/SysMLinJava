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

import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for movement of a frontal area (e.g. vehicle surface)
 * through a space in square-meters-kilometers/hour.
 * 
 * @author ModelerOne
 *
 */
public class FrontalArealSpeed extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Attribute for the speed of the frontal area
	 */
	@Attribute
	public SpeedKilometersPerHour speed;

	/**
	 * Constructor - area and speed
	 * 
	 * @param area  area to be used for initial value
	 * @param speed speed to be used for initial value
	 */
	public FrontalArealSpeed(AreaMetersSquare area, SpeedKilometersPerHour speed)
	{
		super(area.value);
		this.speed = new SpeedKilometersPerHour(speed.value);
	}

	/**
	 * Constructor - default zero area and speed
	 */
	public FrontalArealSpeed()
	{
		super(0);
		this.speed = new SpeedKilometersPerHour(0);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.SquareMeter;
	}

	@Override
	public String toString()
	{
		return String.format("AreaFrontalSpeed [value=%s, units=%s, name=%s, id=%s, speed=%s]", value, units, name, id, speed);
	}

}