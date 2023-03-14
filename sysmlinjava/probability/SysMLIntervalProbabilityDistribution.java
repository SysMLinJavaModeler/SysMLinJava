package sysmlinjava.probability;

/**
 * SysMLinJava representation of an interval probability distribution for values
 * between a specified min and max. {@code SysMLIntervalProbabilityDistribution}
 * must be extended for a specified probability distribution, e.g. uniform,
 * gaussian, log, etc. having min and max values.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLIntervalProbabilityDistribution extends SysMLBasicIntervalProbabilityDistribution
{
	/**
	 * Constructor
	 * 
	 * @param minimumValue specified interval min
	 * @param maximumValue specified interval max
	 */
	public SysMLIntervalProbabilityDistribution(Double minimumValue, Double maximumValue)
	{
		super(minimumValue, maximumValue);
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance of which this instance is to be a copy of
	 */
	public SysMLIntervalProbabilityDistribution(SysMLIntervalProbabilityDistribution copied)
	{
		super(copied);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SysMLIntervalProbabilityDistribution [minimumValue=");
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
