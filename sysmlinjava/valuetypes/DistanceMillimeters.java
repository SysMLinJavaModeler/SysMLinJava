package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for distance in millimeters.
 * 
 * @author ModelerOne
 *
 */
public class DistanceMillimeters extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value distance in millimeters
	 */
	public DistanceMillimeters(double value)
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
	public DistanceMillimeters(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Millimeters;
	}

	@Override
	public String toString()
	{
		return String.format("DistanceMillimeters [value=%s, units=%s]", value, units);
	}
}
