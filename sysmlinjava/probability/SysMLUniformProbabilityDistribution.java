package sysmlinjava.probability;

/**
 * SysMLinJava representation of the SysML uniform probability distribution for
 * values between a specified min and max following a uniform probability
 * distribution.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLUniformProbabilityDistribution extends SysMLBasicIntervalProbabilityDistribution
{
	/**
	 * Constructor
	 * 
	 * @param minimumValue specified interval minimum
	 * @param maximumValue specified interval maximum
	 */
	public SysMLUniformProbabilityDistribution(Double minimumValue, Double maximumValue)
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
	public double nextRandom()
	{
		return minimumValue + random.nextDouble() * (maximumValue - minimumValue);
	}

	@Override
	protected void createDistributionName()
	{
		distributionName = "Uniform";
	}

}
