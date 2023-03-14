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

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SysMLUniformProbabilityDistribution [minimumValue=");
		builder.append(minimumValue);
		builder.append(", maximumValue=");
		builder.append(maximumValue);
		builder.append(", distributionName=");
		builder.append(distributionName);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

}
