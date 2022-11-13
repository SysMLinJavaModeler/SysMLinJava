package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for speed in kilometers/hour
 * 
 * @author ModelerOne
 *
 */
public class SpeedKilometersPerHour extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor - initial value
	 * 
	 * @param value double value for the initial value
	 */
	public SpeedKilometersPerHour(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param milesPerHour speed value to be used as initial value of this copy
	 */
	public SpeedKilometersPerHour(SpeedMilesPerHour milesPerHour)
	{
		super(milesPerHour.value * 1.609344);
	}

	/**
	 * Constructor - default zero
	 */
	public SpeedKilometersPerHour()
	{
		super(0);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KilometersPerHour;
	}

	@Override
	public String toString()
	{
		return String.format("SpeedKilometersPerHour [value=%s, units=%s]", value, units);
	}
}
