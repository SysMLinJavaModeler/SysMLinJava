package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for area in square feet
 * 
 * @author ModelerOne
 *
 */
public class AreaFeetSquare extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value initial value for square feet of area
	 */
	public AreaFeetSquare(double value)
	{
		super(value);
	}

	/**
	 * Copy constructor
	 * 
	 * @param copyFrom value from which this is to be copied from
	 */
	public AreaFeetSquare(AreaFeetSquare copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.FeetSquare;
	}
}
