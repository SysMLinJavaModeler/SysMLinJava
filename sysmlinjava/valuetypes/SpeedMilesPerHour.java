package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for speed in miles/hour
 * 
 * @author ModelerOne
 *
 */
public class SpeedMilesPerHour extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public SpeedMilesPerHour(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied instance whose value is to used as initial value of this copy
	 */
	public SpeedMilesPerHour(SpeedMilesPerHour copied)
	{
		super(copied);
	}

	/**
	 * Constructor - conversion from kph
	 * 
	 * @param kilometersPerHour value to be converted by this instance
	 */
	public SpeedMilesPerHour(SpeedKilometersPerHour kilometersPerHour)
	{
		super(kilometersPerHour.value / 1.609344);
	}

	/**
	 * Constructor - conversion from mps
	 * 
	 * @param metersPerSecond value to be converted by this instance
	 */
	public SpeedMilesPerHour(SpeedMetersPerSecond metersPerSecond)
	{
		super(metersPerSecond.value / 0.44704);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.MilesPerHour;
	}

	@Override
	public String toString()
	{
		return String.format("SpeedMilesPerHour [value=%s]", value);
	}
}
