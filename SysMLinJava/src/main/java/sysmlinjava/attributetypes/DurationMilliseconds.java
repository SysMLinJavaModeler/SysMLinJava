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
 * SysMLinJava value type for time duration in milliseconds
 * 
 * @author ModelerOne
 *
 */
public class DurationMilliseconds extends IInteger
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;
	/**
	 * Constant value for zero duration
	 */
	public static final DurationMilliseconds ZERO = new DurationMilliseconds(0);
	/**
	 * Constant value of milliseconds per second
	 */
	public static final int millisecondsPerSecond = 1000;

	/**
	 * Constructur
	 * 
	 * @param value long value to be used for initial value
	 */
	public DurationMilliseconds(long value)
	{
		super(value);
	}

	/**
	 * Returns instance with specified milliseconds as initial value
	 * 
	 * @param milliseconds long value to be used as initial value
	 * @return instance of duration in milliseconds
	 */
	public static DurationMilliseconds of(long milliseconds)
	{
		return new DurationMilliseconds(milliseconds);
	}

	/**
	 * Returns instance with specified seconds as initial value
	 * 
	 * @param seconds DurationSeconds to be used as initial value
	 * @return instance of duration in milliseconds for specified seconds
	 */
	public static DurationMilliseconds of(DurationSeconds seconds)
	{
		return new DurationMilliseconds((long)seconds.value * 1000);
	}

	/**
	 * Returns {@code DurationMilliseconds} between two InstantMilliseconds
	 * instances
	 * 
	 * @param earlier earlier instant
	 * @param later   later instant
	 * @return duration in seconds between the two times, i.e. later instant minus
	 *         earlier instant
	 */
	@Operation
	public static DurationMilliseconds between(InstantMilliseconds earlier, InstantMilliseconds later)
	{
		return DurationMilliseconds.of(later.value - earlier.value);
	}

	/**
	 * Returns whether this duration is zero
	 * 
	 * @return true if value is zero, false otherwise
	 */
	@Operation
	public boolean isZero()
	{
		return value == 0;
	}

	/**
	 * Returns instance whose value is equvalent to specified seconds
	 * 
	 * @param seconds specified seconds for initial value
	 * @return instance of duration in milliseconds
	 */
	public static DurationMilliseconds ofSeconds(int seconds)
	{
		return DurationMilliseconds.of(seconds * millisecondsPerSecond);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Milliseconds;
	}

	@Override
	public String toString()
	{
		return String.format("DurationMilliseconds [value=%s, units=%s]", value, units);
	}
}
