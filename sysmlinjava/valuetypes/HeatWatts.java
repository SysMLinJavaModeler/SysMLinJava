package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for heat in watts
 * 
 * @author ModelerOne
 *
 */
public class HeatWatts extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for initial value
	 */
	public HeatWatts(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied heat to be used as initial value of copy
	 */
	public HeatWatts(HeatWatts copied)
	{
		super(copied);
	}

	/**
	 * Constructor - default zero
	 */
	public HeatWatts()
	{
		super(0);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Watts;
	}
}
