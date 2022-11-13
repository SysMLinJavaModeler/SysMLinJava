package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for current in amperes (amps)
 * 
 * @author ModelerOne
 *
 */
public class CurrentAmps extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value of the initial value
	 */
	public CurrentAmps(double value)
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
	public CurrentAmps(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyFrom instance whose value is to be the initial value of this copy
	 */
	public CurrentAmps(CurrentAmps copyFrom)
	{
		super(copyFrom);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Amps;
	}
}
