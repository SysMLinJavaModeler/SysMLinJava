package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a volume in cubic-meters
 * 
 * @author ModelerOne
 *
 */
public class VolumeMetersCubic extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public VolumeMetersCubic(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied value to be used for initial value of copy
	 */
	public VolumeMetersCubic(VolumeMetersCubic copied)
	{
		super(copied);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.CubicMeter;
	}
}
