package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava's value type for electrical potential (voltage) in volts
 * 
 * @author ModelerOne
 *
 */
public class PotentialElectricalVolts extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constuctor - initial value
	 * 
	 * @param value double value to be used as the initial value
	 */
	public PotentialElectricalVolts(double value)
	{
		super(value);
	}

	/**
	 * Constuctor - copy
	 * 
	 * @param copied instance whose value is to be used as the initial value of this
	 *               copy
	 */
	public PotentialElectricalVolts(PotentialElectricalVolts copied)
	{
		super(copied);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Volts;
	}

}
