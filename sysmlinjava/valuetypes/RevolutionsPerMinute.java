package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for RPM (revolutions/minute)
 * 
 * @author ModelerOne
 *
 */
public class RevolutionsPerMinute extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value for initial value
	 */
	public RevolutionsPerMinute(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.RevolutionsPerMinute;
	}
}
