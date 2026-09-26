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
import sysmlinjava.attributetypes.RReal;
import sysmlinjava.javaannotations.attributes.Attribute;

/**
 * SysMLinJava representation of the SysML basic interval probability
 * distribution. {@code SysMLBasicIntervalProbabilityDistribution} models values
 * between a specified min and max for a default uniform probability
 * distribution.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.attributetypes.SysMLAttributeType#createProbabilityDistribution
 */
public abstract class SysMLBasicIntervalProbabilityDistribution extends SysMLProbabilityDistribution
{
	/**
	 * Value of interval minimum
	 */
	@Attribute
	public RReal minimumValue;
	/**
	 * Value of interval maximum
	 */
	@Attribute
	public RReal maximumValue;

	/**
	 * Constructor
	 * 
	 * @param minimumValue specified interval minimum
	 * @param maximumValue specified interval maximum
	 */
	public SysMLBasicIntervalProbabilityDistribution(RReal minimumValue, RReal maximumValue)
	{
		super();
		this.minimumValue.value = minimumValue.value;
		this.maximumValue.value = maximumValue.value;
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance of which this instance is to be a copy of
	 */
	public SysMLBasicIntervalProbabilityDistribution(SysMLBasicIntervalProbabilityDistribution copied)
	{
		super(copied);
		this.minimumValue.value = copied.minimumValue.value;
		this.maximumValue.value = copied.maximumValue.value;
	}

	@Override
	public RReal nextRandom()
	{
		return new RReal(minimumValue.value + super.nextRandom().value * maximumValue.value - minimumValue.value);
	}

	@Override
	public void createAttributes()
	{
		super.createAttributes();
		name = Optional.of("BasicIntervalProbabilityDistribution");
		minimumValue = new RReal(0.0);
		maximumValue = new RReal(1.0);
	}
}
