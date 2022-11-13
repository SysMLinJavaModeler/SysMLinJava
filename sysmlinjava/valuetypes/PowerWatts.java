package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava's value type for power in watts
 * 
 * @author ModelerOne
 *
 */
public class PowerWatts extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant instance for zero power
	 */
	public static final PowerWatts zeroPower = new PowerWatts(0);

	/**
	 * Constructor for primitive double intial value in watts
	 * 
	 * @param value initial value
	 */
	public PowerWatts(double value)
	{
		super(value);
	}

	/**
	 * Constructor for Real intial value in watts
	 * 
	 * @param value initial value
	 */
	public PowerWatts(RReal value)
	{
		super(value.value);
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public PowerWatts(PowerWatts copied)
	{
		this(copied.value);
	}

	/**
	 * Constructor - default of zero
	 */
	public PowerWatts()
	{
		super(0);
	}

	/**
	 * Returns instance with specified values for potential and current
	 * 
	 * @param potential potential value to used for this initial value
	 * @param current   current value to used for this initial value
	 * @return instance with specified values for potential and current
	 */
	public static PowerWatts of(PotentialElectricalVolts potential, CurrentAmps current)
	{
		return new PowerWatts(potential.value * current.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Watts;
	}

	@Override
	public String toString()
	{
		return String.format("PowerWatts [value=%s, units=%s]", value, units);
	}
}
