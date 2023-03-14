package sysmlinjava.probability;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.annotations.Operation;
import sysmlinjava.valuetypes.RReal;

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

	@Operation
	@Override
	public double nextRandom()
	{
		return mean.value + random.nextGaussian() * standardDeviation.value;
	}

	@Override
	protected void createDistributionName()
	{
		distributionName = "Normal";
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SysMLNormalProbabilityDistribution [mean=");
		builder.append(mean);
		builder.append(", standardDeviation=");
		builder.append(standardDeviation);
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
