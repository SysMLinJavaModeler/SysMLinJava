package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for capacitance in farads
 * 
 * @author ModelerOne
 */
public class CapacitanceFarads extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -2742653708126329307L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for initial value
	 */
	public CapacitanceFarads(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyFrom capacitance whose value is to be used as the copy value
	 */
	public CapacitanceFarads(CapacitanceFarads copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Farad;
	}
}
