package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for area in square meters
 * 
 * @author ModelerOne
 *
 */
public class AreaMetersSquare extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for initial value
	 */
	public AreaMetersSquare(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyFrom value of which this is to be a copy of
	 */
	public AreaMetersSquare(AreaMetersSquare copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.SquareMeter;
	}
}
