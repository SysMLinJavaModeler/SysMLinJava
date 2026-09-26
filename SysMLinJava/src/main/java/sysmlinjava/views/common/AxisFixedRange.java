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
package sysmlinjava.views.common;

import java.io.Serializable;

/**
 * Axis definition for a numerical axis in a chart display, e.g. line, bar,
 * timing, etc.
 * 
 * @author ModelerOne
 *
 */
public class AxisFixedRange implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8854669832371302383L;

	/**
	 * Title of the axis, not including the parenthetical units portion
	 */
	public String title;
	/**
	 * Parenthetical units portion of the title
	 */
	public String units;
	/**
	 * Minimum value on the axis
	 */
	public double minTicValue;
	/**
	 * Maimum value on the axis
	 */
	public double maxTicValue;
	/**
	 * Increment between major (numbered) tics on the axis
	 */
	public double majorTicValue;
	/**
	 * Number of tics between the major tics.
	 */
	public int minorTicCount;

	/**
	 * Constructor
	 * 
	 * @param title         Title of the axis, not including the parenthetical units
	 *                      portion
	 * @param units         Parenthetical units portion of the title
	 * @param minTicValue   Minimum value on the axis
	 * @param maxTicValue   Maximum value on the axis
	 * @param majorTicValue Increment between major (numbered) tics on the axis
	 * @param minorTicCount Number of tics between the major tics.
	 */
	public AxisFixedRange(String title, String units, double minTicValue, double maxTicValue, double majorTicValue, int minorTicCount)
	{
		super();
		this.title = title;
		this.units = units;
		this.minTicValue = minTicValue;
		this.maxTicValue = maxTicValue;
		this.majorTicValue = majorTicValue;
		this.minorTicCount = minorTicCount;
	}

	@Override
	public String toString()
	{
		return String.format("Axis [title=%s, units=%s, minValue=%s, maxValue=%s, majorTicValue=%s, minorTicCount=%s]", title, units, minTicValue, maxTicValue, majorTicValue, minorTicCount);
	}

}