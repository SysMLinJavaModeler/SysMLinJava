package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava's value type for probability in percent
 * 
 * @author ModelerOne
 *
 */
public class ProbabilityPercent extends Percent
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant instance for 100 percent probability
	 */
	public static final ProbabilityPercent certainty = new ProbabilityPercent(100.0); 
	/**
	 * Constant instance for 0 percent probability
	 */
	public static final ProbabilityPercent impossibility = new ProbabilityPercent(0.0); 

	/**
	 * Constructor for primitive double initial value in percent
	 * 
	 * @param value initial value in percent
	 */
	public ProbabilityPercent(double value)
	{
		super(value);
	}

	/**
	 * Constructor for Real initial value in percent
	 * 
	 * @param value initial value in percent
	 */
	public ProbabilityPercent(RReal value)
	{
		super(value.value);
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public ProbabilityPercent(ProbabilityPercent copied)
	{
		super(copied);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Percent;
	}
}
