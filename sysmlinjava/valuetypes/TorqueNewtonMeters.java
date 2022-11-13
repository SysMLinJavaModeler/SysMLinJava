package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for torque in newton-meters
 * 
 * @author ModelerOne
 *
 */
public class TorqueNewtonMeters extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public TorqueNewtonMeters(double value)
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
	public TorqueNewtonMeters(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param torque torque value to be used for initial value of copy
	 */
	public TorqueNewtonMeters(TorqueNewtonMeters torque)
	{
		super(torque);
	}

	/**
	 * Constructor
	 */
	public TorqueNewtonMeters()
	{
		super(0);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.NewtonMeters;
	}

	@Override
	public String toString()
	{
		return String.format("TorqueNewtonMeters [value=%s, units=%s]", value, units);
	}
}
