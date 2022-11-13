package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for mass in grams
 * 
 * @author ModelerOne
 *
 */
public class MassGrams extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be the initial value
	 */
	public MassGrams(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Grams;
	}

}
