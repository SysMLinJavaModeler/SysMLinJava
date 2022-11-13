package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for distance in kilometers.
 * 
 * @author ModelerOne
 *
 */
public class DistanceKilometers extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value of the initial value
	 */
	public DistanceKilometers(double value)
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
	public DistanceKilometers(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Constructor - convert from miles
	 * 
	 * @param miles distance in miles to be used as the initial value
	 */
	public DistanceKilometers(DistanceMiles miles)
	{
		super(miles.value * 1.609344);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Kilometers;
	}

	@Override
	public String toString()
	{
		return String.format("DistanceKilometers [value=%s, units=%s]", value, units);
	}
}
