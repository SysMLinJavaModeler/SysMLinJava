package sysmlinjava.attributetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * Viscosity as an extenstion of pressure, for a second
 * 
 * @author ModelerOne
 *
 */
public class ViscosityPascalSecond extends PressureNewtonsPerMeterSquare
{
	private static final long serialVersionUID = -7187408417061035537L;

	/**
	 * Constructor
	 * @param value initial value
	 */
	public ViscosityPascalSecond(double value)
	{
		super(value);
	}

	/**
	 * Copy constructor
	 * @param copyFrom value from which this value is to be a copy of
	 */
	public ViscosityPascalSecond(ViscosityPascalSecond copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new ViscosityPascalSecond(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.PascalSecond;
	}
}
