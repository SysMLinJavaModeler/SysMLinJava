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
 * SysMLinJava value type for a 2D rectangle in terms of a upper left and a
 * lower right {@code Point2D}s. Rectangle is assumed to be edge-parallel to
 * cartesian axes.
 * 
 * @author ModelerOne
 *
 */
public class Rectangle2D extends SysMLAttributeType
{
	/**
	 * Rectangle's upper left point
	 */
	@Attribute
	public Point2D upperLeft;
	/**
	 * Rectangle's lower right left point
	 */
	@Attribute
	public Point2D lowerRight;

	/**
	 * Constructor
	 * 
	 * @param upperLeft  point for upper left corner of the rectangle
	 * @param lowerRight point for lower right corner of the rectangle
	 */
	public Rectangle2D(Point2D upperLeft, Point2D lowerRight)
	{
		super();
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;
	}

	/**
	 * Sets the value of the rectangle to specified points
	 * 
	 * @param upperLeft  point for upper left corner of the rectangle
	 * @param lowerRight point for lower right corner of the rectangle
	 */
	public void setValue(Point2D upperLeft, Point2D lowerRight)
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
	public void setValue(Rectangle2D rectangle)
	{
		setValue(rectangle.upperLeft, rectangle.lowerRight);
		notifyAttributeObservers();
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied instance whose values are to be used for this copy
	 */
	public Rectangle2D(Rectangle2D copied)
	{
		super(copied);
		this.upperLeft = new Point2D(copied.upperLeft);
		this.lowerRight = new Point2D(copied.lowerRight);
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
