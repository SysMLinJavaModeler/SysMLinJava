/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.attributetypes;

import java.io.Serializable;

import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.attributes.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the SysML Real represented by a java
 * {@code double} value
 * <p>
 * This representation of the real number type is for a generic real as used in
 * SysML. Hence the use of a Java {@code double} for its value. This SysML real
 * is not the same as the java float, double, Float or Double types as SysML
 * does not discriminate between bit-lengths of real or floating- point numeric
 * types as the Java language does.
 * <p>
 * Note that the {@code Real} name is used by SysML/UML to represent real
 * numbers. To prevent any confusion between this SysML {@code Real} and its
 * representation in SysMLinJava, the double R is used in the {@code RReal}
 * name, consistent with the double letters used for SysMLinJava's
 * {@code BBoolean}, {@code IInteger}, and {@code SString} value types.
 * <p>
 * <b>Note:</b> as an {@code ObservableValue} this value type can be "observed"
 * by other objects. {@code ValueObserver}s call the {@code addValueObserver()}
 * operation to be notiified (called-back) by the {@code RReal} object of any
 * change in its value. This notification will only occur if and when the
 * {@code RReal} {@code value} is changed by a call to the {@code setValue()}
 * operation. So, while the {@code RReal.value} is publicly accessible and can
 * be changed by direct assignment, the {@code setValue()} operation must be
 * used if {@code ValueObserver}s are to be automatically notified of the
 * change.
 * 
 * @author ModelerOne
 */
public class RReal extends SysMLAttributeType implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Current value of the RReal
	 */
	@Attribute
	public double value;

	/**
	 * Constructor - initial value. Units are assumed to be simple numeric units,
	 * i.e. simply a real value.
	 * 
	 * @param value double value of the RReal
	 */
	public RReal(double value)
	{
		super();
		this.value = value;
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied RReal instance whose value is to be the initial value of this
	 *               copy
	 */
	public RReal(RReal copied)
	{
		super(copied);
		this.value = copied.value;
	}

	/**
	 * Constructor for named/id'ed instance
	 * 
	 * @param value double value of the RReal
	 * @param name  unique name
	 * @param id    unique identifier
	 */
	public RReal(double value, String name, Long id)
	{
		super(name, id);
		this.value = value;
	}

	/**
	 * Sets the value to the specified double value and notifies all
	 * {@code ValueChangeObservers} of the change.
	 * 
	 * @param value double value that this value is to be set to
	 */
	public void setValue(double value)
	{
		this.value = value;
		notifyAttributeObservers();
	}

	/**
	 * Sets the value to the specified RReal value and notifies all
	 * {@code ValueChangeObservers} of the change.
	 * 
	 * @param value RReal whose value this value is to be set to
	 */
	public void setValue(RReal value)
	{
		setValue(value.value);
	}

	/**
	 * Returns this value
	 * 
	 * @return this value
	 */
	public double getValue()
	{
		if (probabilityDistribution.isPresent())
			value = probabilityDistribution.get().nextRandom().value;
		return value;
	}

	/**
	 * Returns this value changed to its value added to specified real value
	 * 
	 * @param value double value to add to this value
	 */
	@Operation
	public void add(double value)
	{
		this.value += value;
	}

	/**
	 * Returns this value changed to its value added to specified real value
	 * 
	 * @param real RReal value to add to this value
	 */
	@Operation
	public void add(RReal real)
	{
		this.value += real.value;
	}

	/**
	 * Returns RReal for this value added to specified RReal value
	 * 
	 * @param real RReal value to be added to this value
	 * @return RReal for this value added to specified RReal value
	 */
	@Operation
	public RReal added(RReal real)
	{
		return new RReal(this.value + real.value);
	}

	/**
	 * Returns RReal for specified RReal value subtracted from this value
	 * 
	 * @param real RReal value to be subtracted from this value
	 * @return RReal for specified RReal value subtracted from this value
	 */
	@Operation
	public RReal subtracted(RReal real)
	{
		return new RReal(this.value - real.value);
	}

	/**
	 * Changes this value to its value subtracted by specified double value
	 * 
	 * @param value double value to subtract from this value
	 */
	@Operation
	public void subtract(double value)
	{
		this.value -= value;
	}

	/**
	 * Changes this value to its value multiplied by specified double value
	 * 
	 * @param value double value to multiply this value by
	 */
	@Operation
	public void multiply(double value)
	{
		this.value *= value;
	}

	/**
	 * Changes this value to its value multiplied by specified real value
	 * 
	 * @param real RReal value to multiply this value by
	 */
	@Operation
	public void multiplyBy(RReal real)
	{
		this.value *= real.value;
	}

	/**
	 * Returns new instance that is this value multiplied by specified real value
	 * 
	 * @param real RReal value to multiply this value by
	 * @return instance of this value multiplied by specified value
	 */
	@Operation
	public RReal multipliedBy(RReal real)
	{
		return new RReal(this.value * real.value);
	}

	/**
	 * Returns new instance that is this value multiplied by specified double value
	 * 
	 * @param value double value to multiply this value by
	 * @return instance of this value multiplied by specified value
	 */
	@Operation
	public RReal multipliedBy(double value)
	{
		return new RReal(this.value * value);
	}

	/**
	 * Returns new instance that is this value divided by specified double value
	 * 
	 * @param value double value to divide this value by
	 * @return instance of this value divided by specified value
	 */
	@Operation
	public RReal dividedBy(double value)
	{
		return new RReal(this.value / value);
	}

	/**
	 * Returns new instance that is this value divided by specified real value
	 * 
	 * @param real RReal value to divide this value by
	 * @return instance of this value divided by specified value
	 */
	@Operation
	public RReal dividedBy(RReal real)
	{
		return new RReal(this.value / real.value);
	}

	/**
	 * This value changed to its value divided by specified real value
	 * 
	 * @param real RReal value with which this value is to be divided by
	 */
	@Operation
	public void divide(RReal real)
	{
		this.value /= real.value;
	}

	/**
	 * This value changed to its value divided by specified real value
	 * 
	 * @param value double value with which this value is to be divided by
	 */
	@Operation
	public void divide(double value)
	{
		this.value /= value;
	}

	/**
	 * Returns new instance that is this value squared
	 * 
	 * @return instance of this value squared
	 */
	@Operation
	public RReal squared()
	{
		return new RReal(Math.pow(value, 2));
	}

	/**
	 * Returns new instance that is square root of this value
	 * 
	 * @param real RReal value for square root of
	 * @return instance of this value's square root
	 */
	@Operation
	public RReal squarRoot(RReal real)
	{
		return new RReal(Math.sqrt(real.value));
	}

	/**
	 * Returns new instance that is this value negated
	 * 
	 * @return instance of this value megated
	 */
	@Operation
	public RReal negated()
	{
		return new RReal(this.value *= -1.0);
	}

	/**
	 * This value changed to its absolute value
	 */
	@Operation
	public void abs()
	{
		value = Math.abs(value);
	}

	/**
	 * Returns new instance that is this value changed to its absolute value
	 * 
	 * @return instance that is this value changed to its absolute value
	 */
	@Operation
	public RReal absed()
	{
		return new RReal(Math.abs(value));
	}

	/**
	 * This value changed to its negtive value
	 */
	@Operation
	public void negate()
	{
		value *= -1.0;
	}

	/**
	 * Sets this value to zero
	 */
	@Operation
	public void zero()
	{
		value = 0.0;
	}

	/**
	 * Returns whether this value is less than specified value
	 * 
	 * @param real value to compare
	 * @return whether this value is less than specified value
	 */
	@Operation
	public boolean lessThan(RReal real)
	{
		return this.value < real.value;
	}

	/**
	 * Returns whether this value is less than specified value
	 * 
	 * @param value double value to compare
	 * @return whether this value is less than specified value
	 */
	@Operation
	public boolean lessThan(double value)
	{
		return this.value < value;
	}

	/**
	 * Returns whether this value is greater than specified value
	 * 
	 * @param real value to compare
	 * @return whether this value is greater than specified value
	 */
	@Operation
	public boolean greaterThan(RReal real)
	{
		return this.value > real.value;
	}

	/**
	 * Returns whether this value is greater than specified value
	 * 
	 * @param value double value to compare
	 * @return whether this value is greater than specified value
	 */
	@Operation
	public boolean greaterThan(double value)
	{
		return this.value > value;
	}

	/**
	 * Returns whether this value is less than or equal than specified value
	 * 
	 * @param real value to compare
	 * @return whether this value is less than or equal than specified value
	 */
	@Operation
	public boolean lessThanOrEqualTo(RReal real)
	{
		return this.value <= real.value;
	}

	/**
	 * Returns whether this value is greater than or equal than specified value
	 * 
	 * @param other double value to compare
	 * @return whether this value is greater than or equal than specified value
	 */
	@Operation
	public boolean greaterThanOrEqualTo(double other)
	{
		return this.value >= other;
	}

	/**
	 * Returns whether this value is greater than or equal than specified value
	 * 
	 * @param real RReal value to compare
	 * @return whether this value is greater than or equal than specified value
	 */
	@Operation
	public boolean greaterThanOrEqualTo(RReal real)
	{
		return this.value >= real.value;
	}

	/**
	 * Returns new instance that is the absolute value of this value
	 * 
	 * @return instance that is the absolute value of this value
	 */
	@Operation
	public RReal absoluted()
	{
		return new RReal(Math.abs(value));
	}

	/**
	 * Returns new instance that is the modulus value of this value for the
	 * specified value
	 * 
	 * @param value value of the divisor for the modulus
	 * @return instance that is the modulus value of this value for the specified
	 *         value
	 */
	@Operation
	public RReal moduloOf(RReal value)
	{
		return new RReal(this.value % value.value);
	}

	/**
	 * Returns whether this value is zero
	 * 
	 * @return true if this value is zero, false otherwise
	 */
	@Operation
	public boolean isZero()
	{
		return value == 0.0;
	}

	/**
	 * Returns new instance whose initial value is the specified value
	 * 
	 * @param value value to be the initial value
	 * @return instance whose initial value is the specified value
	 */
	@Operation
	public static RReal of(double value)
	{
		return new RReal(value);
	}

	/**
	 * Returns the numeric length of hypotenuse for right triangle of specified
	 * opposite and adjacent sides
	 * 
	 * @param opposite length of one of the right sides
	 * @param adjacent length of the other of the right sides
	 * @return length of hypotenuse
	 */
	@Operation
	public static RReal hypotenuseOf(RReal opposite, RReal adjacent)
	{
		return new RReal(Math.sqrt(Math.pow(opposite.value, 2) + Math.pow(adjacent.value, 2)));
	}

	/**
	 * Returns the numeric length of the opposite side for right triangle of
	 * specified hypotenuse and angle
	 * 
	 * @param hypotenuse length of the hypotenuse
	 * @param angle      radian angle opposite from side to be returned
	 * @return length of side opposite the angle
	 */
	@Operation
	public static RReal oppositeOf(RReal hypotenuse, RReal angle)
	{
		return new RReal(hypotenuse.value * Math.sin(angle.value));
	}

	/**
	 * Returns the numeric length of the adjacent side for right triangle of
	 * specified hypotenuse and angle
	 * 
	 * @param hypotenuse length of the hypotenuse
	 * @param angle      radian angle adjacent to side to be returned
	 * @return length of side adjacent to the angle
	 */
	@Operation
	public static RReal adjacentOf(RReal hypotenuse, RReal angle)
	{
		return new RReal(hypotenuse.value * Math.cos(angle.value));
	}

	/**
	 * Returns the radian angle for a right triangle with specified opposite and
	 * adjacent sides
	 * 
	 * @param opposite length of side opposite the angle
	 * @param adjacent length of side adjacent to the angle
	 * @return radian angle
	 */
	@Operation
	public static RReal tanAngleOf(RReal opposite, RReal adjacent)
	{
		return new RReal(Math.atan(opposite.dividedBy(adjacent).value));
	}

	/**
	 * Returns the radian angle for a right triangle with specified opposite side
	 * and hypotenuse
	 * 
	 * @param opposite   length of side opposite the angle
	 * @param hypotenuse length of hypotenuse
	 * @return radian angle
	 */
	@Operation
	public static RReal sinAngleOf(RReal opposite, RReal hypotenuse)
	{
		return new RReal(Math.asin(opposite.dividedBy(hypotenuse).value));
	}

	/**
	 * Returns the radian angle for a right triangle with specified adjacent side
	 * and hypotenuse
	 * 
	 * @param adjacent   length of side adjacent to the angle
	 * @param hypotenuse length of hypotenuse
	 * @return radian angle
	 */
	@Operation
	public static RReal cosAngleOf(RReal adjacent, RReal hypotenuse)
	{
		return new RReal(Math.acos(adjacent.dividedBy(hypotenuse).value));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new RReal(value);
	}

	/**
	 * Whether this value equals the specified real's value
	 * 
	 * @param real value to be compared for equality to this value
	 * @return true if this value equals the specified real's value, false otherwise
	 */
	@Operation
	public boolean equals(RReal real)
	{
		return value == real.value;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RReal [value=");
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
