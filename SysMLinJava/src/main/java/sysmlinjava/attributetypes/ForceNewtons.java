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

import java.util.List;

import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;
import sysmlinjava.views.common.StackedProtocolObject;

/**
 * SysMLinJava value type for force in newtons
 * 
 * @author ModelerOne
 *
 */
public class ForceNewtons extends Vector2D implements StackedProtocolObject
{
	/** Serializable ID*/private static final long serialVersionUID = -4223420272722664905L;

	/**
	 * Constructor - magnitude value, zero direction
	 * 
	 * @param value double value to be used for initial value
	 */
	public ForceNewtons(double value)
	{
		super(value, 0);
	}

	/**
	 * Constructor - magnitude value, zero direction
	 * 
	 * @param real RReal value to be used for initial value
	 */
	public ForceNewtons(RReal real)
	{
		super(real.value, 0);
	}

	/**
	 * Constructor - mass to force magnitude conversion, zero direction
	 * 
	 * @param mass mass to be converted to force
	 */
	public ForceNewtons(MassKilograms mass)
	{
		this(mass.value * AccelerationMetersPerSecondPerSecond.gravity.value);
	}

	/**
	 * Constructor
	 * 
	 * @param value     double value to be used for initial value
	 * @param direction double value for direction of force vector
	 */
	public ForceNewtons(double value, double direction)
	{
		super(value, direction);
	}

	/**
	 * Constructor
	 * 
	 * @param value     double value to be used for initial value
	 * @param direction double value for direction of force vector
	 * @param id        unique ID, e.g. index into array of values
	 */
	public ForceNewtons(double value, double direction, long id)
	{
		super(value, direction);
		this.id = id;
	}

	/**
	 * Constructor - magnitude and direction as simple 2D vector type
	 * 
	 * @param vector2D vector whose magnitude and direction are used for initial
	 *                 values
	 */
	public ForceNewtons(Vector2D vector2D)
	{
		this(vector2D.value, vector2D.direction.value);
	}

	/**
	 * Constructor - default zero values
	 */
	public ForceNewtons()
	{
		super(0, 0);
	}

	@Operation
	@Override
	public ForceNewtons horizontalComponent()
	{
		return new ForceNewtons(super.horizontalComponent());
	}

	@Operation
	@Override
	public ForceNewtons verticalComponent()
	{
		return new ForceNewtons(super.verticalComponent());
	}

	/**
	 * Returns instance that is the vector sum of the specified set of vectors
	 * 
	 * @param forces list of force values (as vector values) to be summed
	 * @return force as sum of the specified forces
	 */
	@Operation
	public static ForceNewtons sum(List<Vector2D> forces)
	{
		ForceNewtons result = new ForceNewtons(0, 0);
		result.setValue(Vector2D.sum(forces));
		return result;
	}

	/**
	 * Sets this force values to the values of the specified force
	 * 
	 * @param force force whose values are to be used to set this force values
	 */
	public void setValue(ForceNewtons force)
	{
		super.setValue(force);
	}

	@Override
	public String stackNamesString()
	{
		return String.format("%s(value=%8.4f", getClass().getSimpleName(), value);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new ForceNewtons(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Newtons;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ForceNewtons [direction=");
		builder.append(direction);
		builder.append(", value=");
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

}
