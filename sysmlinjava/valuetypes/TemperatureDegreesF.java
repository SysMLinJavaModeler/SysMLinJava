package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for temperature in degrees fahrenheit
 * 
 * @author ModelerOne
 *
 */
public class TemperatureDegreesF extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant instance for the temperature of water freezing
	 */
	public static final TemperatureDegreesF waterFreezing = new TemperatureDegreesF(32.0);
	/**
	 * Constant instance for the temperature of water boiling
	 */
	public static final TemperatureDegreesF waterBoiling = new TemperatureDegreesF(212.0);

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public TemperatureDegreesF(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.DegreesC;
	}
}
