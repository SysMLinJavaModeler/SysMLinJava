package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for distance in meters.
 * 
 * @author ModelerOne
 *
 */
public class DistanceMeters extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant for meters per mile
	 */
	public static final DistanceMeters perMile = new DistanceMeters(1609.344);

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value distance in meters
	 */
	public DistanceMeters(double value)
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
	public DistanceMeters(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Constructor for Real initial value
	 * 
	 * @param value initial distance in meters
	 */
	public DistanceMeters(RReal value)
	{
		super(value.value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyFrom Distance whose value is to be used for initial value of copy
	 */
	public DistanceMeters(DistanceMeters copyFrom)
	{
		super(copyFrom);
	}

	/**
	 * Constructor - default 0
	 * 
	 */
	public DistanceMeters()
	{
		super(0);
	}	
	
	/**
	 * Returns whether or not this distance is between (in range of) the specified
	 * distances
	 * 
	 * @param distance1 lesser distance in range to be used
	 * @param distance2 greater distance in range to be used
	 * @return true if this distance is between the two, false otherwise.
	 */
	public boolean isBetween(DistanceMeters distance1, DistanceMeters distance2)
	{
		return value > distance1.value && value < distance2.value;
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Meters;
	}

	@Override
	public String toString()
	{
		return String.format("DistanceMeters [value=%s, units=%s]", value, units);
	}
}
