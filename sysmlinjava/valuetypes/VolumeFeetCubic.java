package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a volume in cubic-feet
 * 
 * @author ModelerOne
 *
 */
public class VolumeFeetCubic  extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public VolumeFeetCubic(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied value to used as initial value of this copy
	 */
	public VolumeFeetCubic(VolumeFeetCubic copied)
	{
		super(copied);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.FeetCubic;
	}
}
