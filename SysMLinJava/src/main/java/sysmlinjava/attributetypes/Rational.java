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

import java.io.Serializable;
import java.util.Optional;

/**
 * SysMLinJava attribute type for the SysML Rational. Rational simply extends
 * RReal with infinity values
 * <p>
 * <b>Note:</b> as an {@code ObservableValue} this value type can be "observed"
 * by other objects. {@code ValueObserver}s call the {@code addValueObserver()}
 * operation to be notiified (called-back) by the {@code RReal} object of any
 * change in its value. This notification will only occur if and when the
 * {@code Rational} {@code value} is changed by a call to the {@code setValue()}
 * operation. So, while the {@code Rational.value} is publicly accessible and
 * can be changed by direct assignment, the {@code setValue()} operation must be
 * used if {@code ValueObserver}s are to be automatically notified of the
 * change.
 * 
 * @author ModelerOne
 *
 */
public class Rational extends RReal implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	private enum InfinitiesEnum
	{
		negInfinity, posInfinity
	}

	/**
	 * Rational instance for positivie infinity
	 */
	public static final Rational positiveInfinity = new Rational(InfinitiesEnum.posInfinity);
	/**
	 * Rational instance for negative infinity
	 */
	public static final Rational negativeInfinity = new Rational(InfinitiesEnum.negInfinity);

	private Optional<InfinitiesEnum> infinity;

	/**
	 * Constructor - initial value. Units are assumed to be simple numeric units,
	 * i.e. simply a real value.
	 * 
	 * @param value double value of the RReal
	 */
	public Rational(double value)
	{
		super(value);
		infinity = Optional.empty();
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied RReal instance whose value is to be the initial value of this
	 *               copy
	 */
	public Rational(Rational copied)
	{
		super(copied);
		this.value = copied.value;
		this.infinity = copied.infinity;
	}

	/**
	 * Returns positive infinity value
	 * 
	 * @return instance of positive infinity value
	 */
	public Rational positiveInfinity()
	{
		return new Rational(InfinitiesEnum.posInfinity);
	}

	/**
	 * Returns negative infinity value
	 * 
	 * @return instance of negative infinity value
	 */
	public Rational negativeInfinity()
	{
		return new Rational(InfinitiesEnum.negInfinity);
	}

	/**
	 * Returns whether or not this rational is positive infinity value
	 * 
	 * @return true if this rational is positive infinity value, false otherwise
	 */
	public boolean isPositiveInfinity()
	{
		return infinity.isPresent() && infinity.get() == InfinitiesEnum.posInfinity;
	}

	/**
	 * Returns whether or not this rational is negative infinity value
	 * 
	 * @return true if this rational is negative infinity value, false otherwise
	 */
	public boolean isNegativeInfinity()
	{
		return infinity.isPresent() && infinity.get() == InfinitiesEnum.negInfinity;
	}

	/**
	 * Constructor - initial value. Units are assumed to be simple numeric units,
	 * i.e. simply a real value.
	 * 
	 * @param value double value of the RReal
	 */
	private Rational(InfinitiesEnum value)
	{
		super(0);
		infinity = Optional.of(value);
	}
}