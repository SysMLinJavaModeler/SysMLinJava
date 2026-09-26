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

import static java.lang.Math.PI;

import java.io.Serializable;

import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the cartesian coordinate in two dimensional
 * euclidean space in terms of an x and y coordinates expressed as Java
 * {@code double} values<br>
 * <p>
 * <b>Note:</b> as an {@code ObservableValue} this value type can be "observed"
 * by other objects. {@code ValueObserver}s call the {@code addValueObserver()}
 * operation to be notified (called-back) by the {@code Point2D} object of any
 * change in its value. This notification will only occur if and when the
 * {@code Point2D} {@code value} is changed by a call to the {@code setValue()}
 * operation. So, while the {@code Point2D.value} is publicly accessible and can
 * be changed by direct assignment, the {@code setValue()} operation must be
 * used if {@code ValueObserver}s are to be automatically notified of any
 * change.
 * 
 * @author ModelerOne
 *
 */
public class Point3D extends SysMLAttributeType implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = 790350123208690812L;

	/**
	 * x-value of the Point3D
	 */
	@Attribute
	public double xValue;
	/**
	 * y-value of the Point3D
	 */
	@Attribute
	public double yValue;
	/**
	 * z-value of the Point3D
	 */
	@Attribute
	public double zValue;
	/**
	 * Whether or not values are empty/not yet set
	 */
	public boolean isEmpty;

	/**
	 * Constructor for empty values
	 */
	public Point3D()
	{
		super();
		this.isEmpty = true;
		this.createUnits();
	}

	/**
	 * Minimal constructor specifying the point's x and y values. Units are assumed
	 * to be simple Point units.
	 * 
	 * @param xValue double value of the Point2D's x corrdinate
	 * @param yValue double value of the Point2D's y corrdinate
	 */
	public Point3D(double xValue, double yValue, double zValue)
	{
		super();
		this.xValue = xValue;
		this.yValue = yValue;
		this.zValue = zValue;
		this.isEmpty = false;
		this.createUnits();
	}

	/**
	 * Copy constructor
	 * 
	 * @param copied instance to be copied
	 */
	public Point3D(Point3D copied)
	{
		super();
		this.xValue = copied.xValue;
		this.yValue = copied.yValue;
		this.zValue = copied.zValue;
		this.isEmpty = false;
	}

	/**
	 * Returns whether or not values are empty/not yet set
	 * 
	 * @return true if point's x and y values are missing/not yet set, false
	 *         otherwise
	 */
	public boolean isEmpty()
	{
		return isEmpty;
	}

	/**
	 * Sets values to specified values and notifies all change observers
	 * 
	 * @param xValue point's x value
	 * @param yValue point's y value
	 */
	public void setValue(double xValue, double yValue, double zValue)
	{
		this.xValue = xValue;
		this.yValue = yValue;
		this.zValue = zValue;
		notifyAttributeObservers();
	}

	/**
	 * Sets x, y, and z values to values of specified point and notifies all change
	 * observers
	 * 
	 * @param point point whose values are to be used
	 */
	public void setValue(Point3D point)
	{
		setValue(point.xValue, point.yValue, point.zValue);
	}

	/**
	 * Returns the value of X
	 * 
	 * @return value of X
	 */
	public double getXValue()
	{
		return xValue;
	}

	/**
	 * Returns the value of Y
	 * 
	 * @return value of Y
	 */
	public double getYValue()
	{
		return yValue;
	}

	/**
	 * Returns the value of Z
	 * 
	 * @return value of ZX
	 */
	public double getZValue()
	{
		return zValue;
	}

	/**
	 * Adds the specified points x and y values to the current points x and y values
	 * 
	 * @param value values to be added
	 */
	@Operation
	public void add(Point3D value)
	{
		xValue += value.xValue;
		yValue += value.yValue;
		zValue += value.zValue;
	}

	/**
	 * Scales (multiplies) this point by the specified value
	 * 
	 * @param multipliedBy value to multiply this by
	 */
	@Operation
	public void scale(double multipliedBy)
	{
		xValue *= multipliedBy;
		yValue *= multipliedBy;
		zValue *= multipliedBy;
	}

	/**
	 * Constant value for 90 degrees in radians
	 */
	static final double rad90 = 0.5 * PI;
	/**
	 * Constant value for 180 degrees in radians
	 */
	static final double rad180 = 1.0 * PI;
	/**
	 * Constant value for 270 degrees in radians
	 */
	static final double rad270 = 1.5 * PI;
	/**
	 * Constant value for 360 degrees in radians
	 */
	static final double rad360 = 2.0 * PI;

	/**
	 * Return an instance that is this multiplied by the specified value
	 * 
	 * @param multipliedBy value to multiply this by
	 * @return instance that is this multiplied by the specified value
	 */
	@Operation
	public Point3D scaled(double multipliedBy)
	{
		return new Point3D(xValue * multipliedBy, yValue * multipliedBy, zValue * multipliedBy);
	}

	/**
	 * Returns instance with specified values
	 * 
	 * @param xValue initial x value
	 * @param yValue initial y value
	 * @param zValue initial z value
	 * @return instance with initial x, y, z values
	 */
	public static Point3D of(double xValue, double yValue, double zValue)
	{
		return new Point3D(xValue, yValue, zValue);
	}

	/**
	 * Returns instance with an empty point
	 * 
	 * @return instance with an empty point
	 */
	public static Point3D empty()
	{
		return new Point3D();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Point;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Point3D [xValue=");
		builder.append(xValue);
		builder.append(", yValue=");
		builder.append(yValue);
		builder.append(", zValue=");
		builder.append(zValue);
		builder.append(", isEmpty=");
		builder.append(isEmpty);
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
