package sysmlinjava.probability;

import sysmlinjava.annotations.Attribute;

/**
 * SysMLinJava representation of the SysML basic interval probability
 * distribution. {@code SysMLBasicIntervalProbabilityDistribution} models values
 * between a specified min and max for a default uniform probability
 * distribution.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLBasicIntervalProbabilityDistribution extends SysMLProbabilityDistribution
{
	/**
	 * Value of interval minimum
	 */
	@Attribute
	public Double minimumValue;
	/**
	 * Value of interval maximum
	 */
	@Attribute
	public Double maximumValue;

	/**
	 * Constructor
	 * 
	 * @param minimumValue specified interval minimum
	 * @param maximumValue specified interval maximum
	 */
	public SysMLBasicIntervalProbabilityDistribution(Double minimumValue, Double maximumValue)
	{
		super();
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance of which this instance is to be a copy of
	 */
	public SysMLBasicIntervalProbabilityDistribution(SysMLBasicIntervalProbabilityDistribution copied)
	{
		super(copied);
		this.minimumValue = copied.minimumValue;
		this.maximumValue = copied.maximumValue;
	}

	@Override
	public double nextRandom()
	{
		return this.minimumValue + super.nextRandom() * maximumValue - minimumValue;
	}
}
