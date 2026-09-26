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
 * SysMLinJava value type for latitude in degrees
 * 
 * @author ModelerOne
 *
 */
public class LatitudeDegrees  extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant latitude degrees for north pole
	 */
	public static final LatitudeDegrees northPole = new LatitudeDegrees(90);
	/**
	 * Constant latitude degrees for south pole
	 */
	public static final LatitudeDegrees southPole = new LatitudeDegrees(-90);
	/**
	 * Constant latitude degrees for equator
	 */
	public static final LatitudeDegrees equator = new LatitudeDegrees(0);
	/**
	 * Constant latitude degrees per mile distance
	 */
	public static final LatitudeDegrees perMile = new LatitudeDegrees(1 / 69.05);

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeDegrees(double value)
	{
		super(value);
	}

	/**
	 * Constructor for Real initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeDegrees(RReal value)
	{
		this(value.value);
	}

	/**
	 * Constructor for LatitudeDegrees initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeDegrees(LatitudeDegrees value)
	{
		this(value.value);
	}

	/**
	 * Constructor for LatitudeRadians initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeDegrees(LatitudeRadians value)
	{
		this(Math.toDegrees(value.value));
	}

	/**
	 * Constructor for DirectionDegrees initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeDegrees(DirectionDegrees value)
	{
		this(value.value);
	}

	/**
	 * Constructor for DirectionRadians initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeDegrees(DirectionRadians value)
	{
		this(Math.toDegrees(value.value));
	}

	@Operation
	@Override
	public LatitudeDegrees added(RReal real)
	{
		return new LatitudeDegrees(value + real.value);
	}

	@Operation
	@Override
	public LatitudeDegrees subtracted(RReal real)
	{
		return new LatitudeDegrees(value - real.value);
	}

	@Operation
	@Override
	public LatitudeDegrees multipliedBy(RReal real)
	{
		return new LatitudeDegrees(value * real.value);
	}

	@Operation
	@Override
	public LatitudeDegrees dividedBy(RReal real)
	{
		return new LatitudeDegrees(value / real.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Degrees;
	}
}
