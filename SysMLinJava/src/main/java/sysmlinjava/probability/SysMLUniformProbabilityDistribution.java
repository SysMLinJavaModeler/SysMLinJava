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

import sysmlinjava.attributetypes.RReal;

/**
 * SysMLinJava representation of the SysML uniform probability distribution for
 * values between a specified min and max following a uniform probability
 * distribution.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.attributetypes.SysMLAttributeType#createProbabilityDistribution
 */
public final class SysMLUniformProbabilityDistribution extends SysMLBasicIntervalProbabilityDistribution
{
	/**
	 * Constructor
	 * 
	 * @param minimumValue specified interval minimum
	 * @param maximumValue specified interval maximum
	 */
	public SysMLUniformProbabilityDistribution(RReal minimumValue, RReal maximumValue)
	{
		super(minimumValue, maximumValue);
	}

	/**
	 * Returns next random number for a uniform distribution between min and max
	 * values
	 * 
	 * @return next random number in a sequence of uniformly random numbers between
	 *         min and max values;
	 */
	@Override
	public RReal nextRandom()
	{
		return new RReal(minimumValue.value + random.nextDouble() * (maximumValue.value - minimumValue.value));
	}

	@Override
	public void createAttributes()
	{
		super.createAttributes();
		distributionName = "Uniform";
	}
}
