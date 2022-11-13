package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for acceleration in kilometers-per-hour-per-second
 * 
 * @author ModelerOne
 *
 */
public class AccelerationKilometersPerHourPerSecond extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 */
	public AccelerationKilometersPerHourPerSecond(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KilometersPerHourPerSecond;
	}

	@Override
	public String toString()
	{
		return String.format("AccelerationKilometersPerHourPerSecond [value=%s, units=%s]", value, units);
	}
}
