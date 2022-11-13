package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for frequency in hertz (cycles-per-second)
 * 
 * @author ModelerOne
 *
 */
public class FrequencyHertz extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used as initial value
	 */
	public FrequencyHertz(double value)
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
	public FrequencyHertz(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Constructor
	 * 
	 * @param value RReal value to be used as initial value
	 */
	public FrequencyHertz(RReal value)
	{
		super(value.value);
	}

	/**
	 * Returns instance for specified value
	 * 
	 * @param value double value to be used as initial value in new instance
	 * @return new instance of FrequencyHertz
	 */
	public static FrequencyHertz of(double value)
	{
		return new FrequencyHertz(value);
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public FrequencyHertz(FrequencyHertz copied)
	{
		super(copied);
	}

	/**
	 * Constructor - default zero
	 */
	public FrequencyHertz()
	{
		super(0);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Hertz;
	}

	@Override
	public String toString()
	{
		return String.format("FrequencyHertz [value=%s, units=%s]", value, units);
	}
}
