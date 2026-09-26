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
 * SysMLinJava value type for a geospatial rectangle in terms of a upper left
 * and a lower right {@code PointGeospatial}. Rectangle assumes its east and
 * west edges lie on great circles.
 * 
 * @author ModelerOne
 *
 */
public class RectangleGeospatial extends SysMLAttributeType
{
	/**
	 * Rectangle's upper left position
	 */
	@Attribute
	public PointGeospatial upperLeft;
	/**
	 * Rectangle's lower right left position
	 */
	@Attribute
	public PointGeospatial lowerRight;

	/**
	 * Constructor - initial value
	 * 
	 * @param upperLeft  point value for the upper left corner of this geospatial
	 *                   rectangle
	 * @param lowerRight point value for the lower right corner of this geospatial
	 *                   rectangle
	 */
	public RectangleGeospatial(PointGeospatial upperLeft, PointGeospatial lowerRight)
	{
		super();
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied instance whose values are to be used for the initial values of
	 *               this copy
	 */
	public RectangleGeospatial(RectangleGeospatial copied)
	{
		super(copied);
		this.upperLeft = new PointGeospatial(copied.upperLeft);
		this.lowerRight = new PointGeospatial(copied.lowerRight);
	}

	/**
	 * Sets the value of the rectangle to specified points
	 * 
	 * @param upperLeft  point for upper left corner of the rectangle
	 * @param lowerRight point for lower right corner of the rectangle
	 */
	public void setValue(PointGeospatial upperLeft, PointGeospatial lowerRight)
	{
		this.upperLeft.setValue(upperLeft);
		this.lowerRight.setValue(lowerRight);
		notifyAttributeObservers();
	}

	/**
	 * Sets the value of the rectangle to that of specified other point
	 * 
	 * @param rectangle rectangle whose value is to be set for this value
	 */
	public void setValue(RectangleGeospatial rectangle)
	{
		setValue(rectangle.upperLeft, rectangle.lowerRight);
		notifyAttributeObservers();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Point;
	}

	@Override
	public String toString()
	{
		return String.format("RectangleGeospatial [name=%s, id=%s, units=%s, upperLeft=%s, lowerRight=%s]", name, id, units, upperLeft, lowerRight);
	}
}
