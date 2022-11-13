package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for altitude in meters
 * 
 * @author ModelerOne
 *
 */
public class AltitudeMeters extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for initial value
	 */
	public AltitudeMeters(double value)
	{
		super(value);
	}

	/**
	 * Constructor
	 * 
	 * @param value RReal value to be used for initial value
	 */
	public AltitudeMeters(RReal value)
	{
		this(value.value);
	}

	/**
	 * Constructor
	 * 
	 * @param value distance value to be used for initial value
	 */
	public AltitudeMeters(DistanceMeters value)
	{
		this(value.value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied altitude whose value is to used for copy
	 */
	public AltitudeMeters(AltitudeMeters copied)
	{
		super(copied);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Meters;
	}

	@Override
	public String toString()
	{
		return String.format("AltitudeMeters [value=%s, units=%s]", value, units);
	}
}
