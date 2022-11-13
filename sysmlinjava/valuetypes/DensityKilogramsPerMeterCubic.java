package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for density in kilograms/cubic-meter
 * 
 * @author ModelerOne
 *
 */
public class DensityKilogramsPerMeterCubic extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value of the initial value
	 */
	public DensityKilogramsPerMeterCubic(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyFrom instance whose value is to be the initial value of this copy
	 */
	public DensityKilogramsPerMeterCubic(DensityKilogramsPerMeterCubic copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.KilogramsPerMeterCubic;
	}
}
