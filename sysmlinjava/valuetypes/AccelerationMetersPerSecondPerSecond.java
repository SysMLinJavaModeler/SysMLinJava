package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for acceleration in meters/second/second
 * 
 * @author ModelerOne
 *
 */
public class AccelerationMetersPerSecondPerSecond extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * A constant value for the acceleration of gravity
	 */
	public static final AccelerationMetersPerSecondPerSecond gravity = new AccelerationMetersPerSecondPerSecond(9.80665);

	/**
	 * Constructor
	 * 
	 * @param value value to be used for initial value
	 */
	public AccelerationMetersPerSecondPerSecond(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.MeterPerSecondSquared;
	}
}
