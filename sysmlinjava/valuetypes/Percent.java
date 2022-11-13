package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for percent as real number 0.0 thru 100.0.
 * 
 * @author ModelerOne
 *
 */
public class Percent extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be the initial value
	 */
	public Percent(double value)
	{
		super(value);
	}

	/**
	 * Constructor for initial value and probability distribution
	 * 
	 * @param value        initial value
	 * @param distribution probability distribution used to produce random values
	 *                     via consecutive {@code getValue()} calls.
	 */
	public Percent(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied value to be used as the initial value of the copy
	 */
	public Percent(Percent copied)
	{
		super(copied);
	}

	/**
	 * Percent as fraction (0.0 thru 1.0)
	 * 
	 * @return value / 100
	 */
	public double asFraction()
	{
		return value / 100;
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Percent;
	}
}
