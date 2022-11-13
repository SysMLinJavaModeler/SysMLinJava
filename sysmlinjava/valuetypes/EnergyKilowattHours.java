package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for energy in kilowatt-hours
 * 
 * @author ModelerOne
 *
 */
public class EnergyKilowattHours extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 */
	public EnergyKilowattHours(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KiloWattHours;
	}

	@Override
	public String toString()
	{
		return String.format("EnergyKilowattHours [value=%s, units=%s]", value, units);
	}
}
