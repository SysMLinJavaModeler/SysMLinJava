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
import sysmlinjava.javaannotations.actions.Action;
import sysmlinjava.javaannotations.attributes.Attribute;

/**
 * SysMLinJava representation of a normal (gaussian) probability distribution
 * with a specified mean and standard deviation.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.attributetypes.SysMLAttributeType#createProbabilityDistribution
 */
public final class SysMLNormalProbabilityDistribution extends SysMLProbabilityDistribution
{
	/**
	 * Mean value of the normal probability distribution
	 */
	@Attribute
	public RReal mean;

	/**
	 * Standard deviation value of the normal probability distribution
	 */
	@Attribute
	public RReal standardDeviation;

	/**
	 * Constructor for specified mean and standard deviation
	 * 
	 * @param mean              mean of the normal probability distribution
	 * @param standardDeviation standard deviation of the normal probability
	 *                          distribution
	 */
	public SysMLNormalProbabilityDistribution(RReal mean, RReal standardDeviation)
	{
		super();
		this.mean = mean;
		this.standardDeviation = standardDeviation;
	}

	@Action
	@Override
	public RReal nextRandom()
	{
		return new RReal(mean.value + random.nextGaussian() * standardDeviation.value);
	}

	@Override
	protected void createAttributes()
	{
		super.createAttributes();
		distributionName = "Normal";
	}
}
