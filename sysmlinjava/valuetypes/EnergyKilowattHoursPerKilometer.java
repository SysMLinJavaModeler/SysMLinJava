package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for energy over a distance in kilowatt-hours/kilometer
 * 
 * @author ModelerOne
 *
 */
public class EnergyKilowattHoursPerKilometer extends EnergyKilowattHours
{
	/** Serializable ID*/private static final long serialVersionUID = -1514623191232433665L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 */
	public EnergyKilowattHoursPerKilometer(double value)
	{
		super(value);
	}

	@Override
	public String toString()
	{
		return String.format("EnergyKilowattHoursPerKilometer [value=%s, units=%s]", value, units);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KiloWattHoursPerKilometer;
	}
}
