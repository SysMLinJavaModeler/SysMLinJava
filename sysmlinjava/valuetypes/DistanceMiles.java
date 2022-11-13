package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for distance in miles.
 * 
 * @author ModelerOne
 *
 */
public class DistanceMiles extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value distance in miles
	 */
	public DistanceMiles(double value)
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
	public DistanceMiles(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Constructor - conversion
	 * 
	 * @param kilometers distance instance to converted into miles instance
	 */
	public DistanceMiles(DistanceKilometers kilometers)
	{
		super(kilometers.value / 1.609344);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Miles;
	}

	@Override
	public String toString()
	{
		return String.format("DistanceMiles [value=%s, units=%s]", value, units);
	}
}
