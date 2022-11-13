package sysmlinjava.probability;

import java.util.Random;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava representation of the SysML distribution property stereotype.
 * {@code SysMLProbabilityDistribution} is the base class for all the
 * probability distributions. It includes a random number generator that can be
 * used by specialized distributions to generate random number for the specific
 * distribution.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLProbabilityDistribution extends SysMLClass
{
	/**
	 * Randum number generator for use in generating values for the distribution
	 */
	@Attribute
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
		createDistributionName();
		createRandomGenerator();
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
	 * Creates the name of the distribution
	 */
	protected abstract void createDistributionName();

	/**
	 * Creates the random number generator for the distribution. This default
	 * operation creates the Java standard generator. Extended classes may need to
	 * overide to create a specialized generator.
	 */
	private void createRandomGenerator()
	{
		random = new Random();
	}

	/**
	 * Returns next random number. By default uses uniform distribution between 0.0
	 * and 1.0.
	 * 
	 * @return next random number in a sequence of random numbers from 0.0 to 1.0;
	 */
	public double nextRandom()
	{
		return random.nextDouble();
	}
}
