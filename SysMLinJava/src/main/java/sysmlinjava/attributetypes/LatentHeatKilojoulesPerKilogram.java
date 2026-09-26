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
 * SysMLinJava value type for latent-heat in kilojoules-per-kilogram
 * 
 * @author ModelerOne
 *
 */
public class LatentHeatKilojoulesPerKilogram extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = 5906656002048836337L;

	/**
	 * Constant value for latent heat for water condensation
	 */
	public static final LatentHeatKilojoulesPerKilogram waterCondensationLH = new LatentHeatKilojoulesPerKilogram(334.0);
	/**
	 * Constant value for latent heat for water vaporization
	 */
	public static final LatentHeatKilojoulesPerKilogram waterVaporizationLH = new LatentHeatKilojoulesPerKilogram(2264.705);

	/**
	 * Constructor
	 * 
	 * @param value double value to be the initial value
	 */
	public LatentHeatKilojoulesPerKilogram(double value)
	{
		super(value);
		units = SysMLinJavaUnits.KilojoulesPerKilogram;
	}

	@Override
	public String toString()
	{
		return String.format("LatentHeatKilojoulesPerKilogram [value=%s, units=%s]", value, units);
	}
}
