package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for temperature in degrees centigrade
 * 
 * @author ModelerOne
 *
 */
public class TemperatureDegreesC  extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant instance for the temperature of water freezing
	 */
	public static final TemperatureDegreesF  waterFreezing = new TemperatureDegreesF(0.0);
	/**
	 * Constant instance for the temperature of water boiling
	 */
	public static final TemperatureDegreesF  waterBoiling  = new TemperatureDegreesF(100.0);

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public TemperatureDegreesC(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.DegreesC;
	}
}
