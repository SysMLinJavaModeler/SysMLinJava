package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for area in square inches
 * 
 * @author ModelerOne
 *
 */
public class AreaInchesSquare extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489111777283872L;

	/**
	 * Constructor
	 * 
	 * @param value initial value for square inches of area
	 */
	public AreaInchesSquare(double value)
	{
		super(value);
	}

	/**
	 * Copy constructor
	 * 
	 * @param copyFrom value from which this is to be copied from
	 */
	public AreaInchesSquare(AreaInchesSquare copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.InchesSquare;
	}
}
