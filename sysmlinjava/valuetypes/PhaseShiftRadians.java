package sysmlinjava.valuetypes;

import static java.lang.Math.PI;
import sysmlinjava.annotations.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava's value type for phase shift in radians.
 * 
 * @author ModelerOne
 *
 */
public class PhaseShiftRadians extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor with primitive double for initial value
	 * 
	 * @param value phase shift in radians
	 */
	public PhaseShiftRadians(double value)
	{
		super(value);
	}

	/**
	 * Constructor with Real for initial value
	 * 
	 * @param value phase shift in radians
	 */
	public PhaseShiftRadians(RReal value)
	{
		super(value.value);
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public PhaseShiftRadians(PhaseShiftRadians copied)
	{
		super(copied);
	}

	/**
	 * Constructor for conversion from degrees
	 * 
	 * @param degrees initial phase shift in degrees
	 */
	public PhaseShiftRadians(PhaseShiftDegrees degrees)
	{
		super(degrees.value);
	}

	/**
	 * Returns the opposite (180 degrees or PI radians greater) phase shift
	 * 
	 * @return opposite phase shift
	 */
	@Operation
	public PhaseShiftRadians opposite()
	{
		return new PhaseShiftRadians((value += PI) % (2 * PI));
	}

	/**
	 * Returns this phase shift as phase shift in degrees
	 * 
	 * @return phase shift in degrees
	 */
	public PhaseShiftDegrees toDegrees()
	{
		return new PhaseShiftDegrees(Math.toDegrees(value));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Radians;
	}

	@Override
	public String toString()
	{
		return String.format("PhaseShiftRadians [value=%s, units=%s]", value, units);
	}

}
