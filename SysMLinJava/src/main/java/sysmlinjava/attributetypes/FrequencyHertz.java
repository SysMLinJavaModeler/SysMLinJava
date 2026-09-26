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

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for frequency in hertz (cycles-per-second)
 * 
 * @author ModelerOne
 *
 */
public class FrequencyHertz extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 */
	public FrequencyHertz(double value)
	{
		super(value);
	}

	/**
	 * Constructor
	 * 
	 * @param value RReal value to be used as initial value
	 */
	public FrequencyHertz(RReal value)
	{
		super(value.value);
	}

	/**
	 * Returns instance for specified value
	 * 
	 * @param value double value to be used as initial value in new instance
	 * @return new instance of FrequencyHertz
	 */
	public static FrequencyHertz of(double value)
	{
		return new FrequencyHertz(value);
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public FrequencyHertz(FrequencyHertz copied)
	{
		super(copied);
	}

	/**
	 * Constructor - default zero
	 */
	public FrequencyHertz()
	{
		super(0);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new FrequencyHertz(this);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Hertz;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("FrequencyHertz [value=");
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
