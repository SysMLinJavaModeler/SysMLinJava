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
package sysmlinjava.probability;

import java.util.Optional;
import java.util.Random;

import sysmlinjava.attributetypes.RReal;
import sysmlinjava.attributetypes.SysMLAttributeType;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava representation of the SysML distribution property stereotype.
 * {@code SysMLProbabilityDistribution} is the base class for all the
 * probability distributions. It includes a random number generator that can be
 * used by specialized distributions to generate random number for the specific
 * distribution.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.attributetypes.SysMLAttributeType#createProbabilityDistribution
 */
public abstract class SysMLProbabilityDistribution extends SysMLAttributeType
{
	/**
	 * Randum number generator for use in generating values for the distribution
	 */
	protected Random random;

	/**
	 * Name of the probability distribution
	 */
	@Attribute
	public String distributionName;

	/**
	 * Constructor
	 */
	public SysMLProbabilityDistribution()
	{
		super();
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance this instance is to be copy of
	 */
	public SysMLProbabilityDistribution(SysMLProbabilityDistribution copied)
	{
		super(copied);
		this.distributionName = copied.distributionName;
		this.random = copied.random;
	}

	/**
	 * Overridable operation that returns next random number. Returns value of 0 for
	 * unknown random number distribution. Subclasses should override in accordance
	 * with desired distribution.
	 * 
	 * @return next random number in a sequence of random numbers that are in
	 *         accordance with applicable distribution
	 */
	public RReal nextRandom()
	{
		return new RReal(0);
	}

	@Override
	protected void createAttributes()
	{
		super.createAttributes();
		name = Optional.of("ProbabilityDistribution");
		random = new Random();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	/**
	 * 
	 */
	public static final String createAttributesMethodName = "createAttributes";
}
