package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for data flow in bits/second
 * 
 * @author ModelerOne
 */
public class BitsPerSecond extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value value to used as initial value
	 */
	public BitsPerSecond(double value)
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
	public BitsPerSecond(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.BitsPerSecond;
	}

	@Override
	public String toString()
	{
		return String.format("BitsPerSecond [value=%s, units=%s]", value, units);
	}
}
