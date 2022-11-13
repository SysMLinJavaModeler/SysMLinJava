package sysmlinjava.probability;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.annotations.Operation;

/**
 * SysMLinJava representation of a normal (gaussian) probability distribution
 * with a specified mean and standard deviation.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLNormalProbabilityDistribution extends SysMLProbabilityDistribution
{
	/**
	 * Mean value of the normal probability distribution
	 */
	@Attribute
	public Double mean;

	/**
	 * Standard deviation value of the normal probability distribution
	 */
	@Attribute
	public Double standardDeviation;

	/**
	 * Constructor for specified mean and standard deviation
	 * 
	 * @param mean              mean of the normal probability distribution
	 * @param standardDeviation standard deviation of the normal probability
	 *                          distribution
	 */
	public SysMLNormalProbabilityDistribution(Double mean, Double standardDeviation)
	{
		super();
		this.mean = mean;
		this.standardDeviation = standardDeviation;
	}

	@Operation
	@Override
	public double nextRandom()
	{
		return mean + random.nextGaussian() * standardDeviation;
	}

	@Override
	protected void createDistributionName()
	{
		distributionName = "Normal";
	}
}
