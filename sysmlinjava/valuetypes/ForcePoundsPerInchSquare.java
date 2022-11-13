package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for force over an area in poundes/square-inch
 * 
 * @author ModelerOne
 *
 */
public class ForcePoundsPerInchSquare  extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 */
	public ForcePoundsPerInchSquare(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.PoundsPerInchSquare;
	}
}
