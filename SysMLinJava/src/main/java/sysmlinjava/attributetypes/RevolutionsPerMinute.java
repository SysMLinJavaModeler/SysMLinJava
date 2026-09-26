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
 * SysMLinJava value type for RPM (revolutions/minute)
 * 
 * @author ModelerOne
 *
 */
public class RevolutionsPerMinute extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value for initial value
	 */
	public RevolutionsPerMinute(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.RevolutionsPerMinute;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RevolutionsPerMinute [value=");
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

	/**
	 * Returns the wheel RPM for specified wheel diameter and specified velocity
	 * 
	 * @param wheelDiameter diameter of the wheel
	 * @param atVelocity    velocity of the wheel
	 * @return wheel's RPM
	 */
	public static RevolutionsPerMinute valueOf(DistanceMeters wheelDiameter, VelocityMetersPerSecondRadians atVelocity)
	{
		double revolutionsPerMeter = 1 / (wheelDiameter.value * Math.PI);
		double metersPerMinute = atVelocity.value * 60;
		return new RevolutionsPerMinute(revolutionsPerMeter * metersPerMinute);
	}
}
