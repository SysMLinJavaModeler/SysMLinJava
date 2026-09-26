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
 * SysMLinJava value type for a hexahedron (6-sides) in terms of its height,
 * width, and length in meters
 * 
 * @author ModelerOne
 *
 */
public class HexahedronMeters extends SysMLAttributeType
{
	/**
	 * Height of the hexahedron
	 */
	@Attribute
	public double height;
	/**
	 * Width of the hexahedron
	 */
	@Attribute
	public double width;
	/**
	 * Length of the hexahedron
	 */
	@Attribute
	public double length;

	/**
	 * Constructor for all attributes
	 * 
	 * @param height height of the hexahedron
	 * @param width  width of the hexahedron
	 * @param length length of the hexahedron
	 */
	public HexahedronMeters(double height, double width, double length)
	{
		super();
		this.height = height;
		this.width = width;
		this.length = length;
	}

	/**
	 * Returns the volume of the hexahedron
	 * 
	 * @return volume in cubic meters
	 */
	public VolumeMetersCubic volumeOf()
	{
		return new VolumeMetersCubic(height * width * length);
	}

	/**
	 * Returns the hexahedron's height
	 * 
	 * @return height (in meters)
	 */
	public double getHeight()
	{
		return height;
	}

	/**
	 * Sets the hexahedron's height
	 * 
	 * @param height double value in meters to use as height
	 */
	public void setHeight(double height)
	{
		this.height = height;
		notifyAttributeObservers();
	}

	/**
	 * Returns the hexahedron's width
	 * 
	 * @return width (in meters)
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * Sets the hexahedron's width
	 * 
	 * @param width double value in meters to use as width
	 */
	public void setWidth(double width)
	{
		this.width = width;
		notifyAttributeObservers();
	}

	/**
	 * Returns the hexahedron's length
	 * 
	 * @return length (in meters)
	 */
	public double getLength()
	{
		return length;
	}

	/**
	 * Sets the hexahedron's length
	 * 
	 * @param length double value in meters to use as length
	 */
	public void setLength(double length)
	{
		this.length = length;
		notifyAttributeObservers();
	}

	/**
	 * Sets the attributes of the hexahedron
	 * 
	 * @param height height of the hexahedron
	 * @param width  width of the hexahedron
	 * @param length length of the hexahedron
	 */
	public void setHeightWidthLength(double height, double width, double length)
	{
		this.height = height;
		this.width = width;
		this.length = length;
		notifyAttributeObservers();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Meters;
	}

}
