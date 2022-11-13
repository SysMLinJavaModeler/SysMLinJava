package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for torque in pound-feet (a.k.a foot-pounds)
 * 
 * @author ModelerOne
 *
 */
public class TorquePoundFeet  extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public TorquePoundFeet(double value)
	{
		super(value);
	}

	/**
	 * Constructor for initial value and probability distribution
	 * 
	 * @param value        initial value
	 * @param distribution probability distribution used to produce random values
	 *                     via consecutive {@code getValue()} calls.
	 */
	public TorquePoundFeet(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.PoundFeet;
	}

	@Override
	public String toString()
	{
		return String.format("TorqueNewtonMeters [value=%s, units=%s]", value, units);
	}
}
