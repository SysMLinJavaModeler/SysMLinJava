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
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a rectangle whose sides measure in meters.
 * 
 * @author ModelerOne
 *
 */
public class RectangleMeters extends SysMLAttributeType
{
	/**
	 * Attribute for rectangle length
	 */
	@Attribute
	public double length;
	/**
	 * Attribute for rectangle width
	 */
	@Attribute
	public double width;

	/**
	 * Constructor - all initial values
	 * 
	 * @param length double value for length
	 * @param width  double value for width
	 */
	public RectangleMeters(double length, double width)
	{
		super();
		this.length = length;
		this.width = width;
	}

	/**
	 * Returns the area of the rectangle
	 * 
	 * @return area of the rectangle
	 */
	@Operation
	public AreaMetersSquare areaMetersSquare()
	{
		return new AreaMetersSquare(width * length);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Meters;
	}
}
