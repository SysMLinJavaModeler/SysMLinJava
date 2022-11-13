package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for force over an area in newtons/square-meter
 * 
 * @author ModelerOne
 *
 */
public class ForceNewtonsPerMeterSquare extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 */
	public ForceNewtonsPerMeterSquare(double value)
	{
		super(value);
	}

	/**
	 * Constructor - default zero magnitude and direction
	 */
	public ForceNewtonsPerMeterSquare()
	{
		super(0);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.NewtonsPerMeterSquare;
	}
}
