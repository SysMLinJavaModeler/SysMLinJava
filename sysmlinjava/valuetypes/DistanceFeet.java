package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for distance in feet.
 * 
 * @author ModelerOne
 *
 */
public class DistanceFeet  extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value of the initial value
	 */
	public DistanceFeet(double value)
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
	public DistanceFeet(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Feet;
	}

	@Override
	public String toString()
	{
		return String.format("DistanceFeet [value=%s, units=%s]", value, units);
	}
}
