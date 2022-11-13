package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for time duration in milliseconds
 * 
 * @author ModelerOne
 *
 */
public class DurationMilliseconds extends IInteger
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;
	/**
	 * Constant value for zero duration
	 */
	public static final DurationMilliseconds ZERO = new DurationMilliseconds(0);
	/**
	 * Constant value of milliseconds per second
	 */
	public static final int millisecondsPerSecond = 1000;

	/**
	 * Constructur
	 * 
	 * @param value long value to be used for initial value
	 */
	public DurationMilliseconds(long value)
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
	public DurationMilliseconds(long value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Returns instance with specified milliseconds as initial value
	 * 
	 * @param milliseconds long value to be used as initial value
	 * @return instance of duration in milliseconds
	 */
	public static DurationMilliseconds of(long milliseconds)
	{
		return new DurationMilliseconds(milliseconds);
	}

	/**
	 * Returns {@code DurationMilliseconds} between two InstantMilliseconds
	 * instances
	 * 
	 * @param earlier earlier instant
	 * @param later   later instant
	 * @return duration in seconds between the two times, i.e. later instant minus
	 *         earlier instant
	 */
	public static DurationMilliseconds between(InstantMilliseconds earlier, InstantMilliseconds later)
	{
		return DurationMilliseconds.of(later.value - earlier.value);
	}

	/**
	 * Returns whether this duration is zero
	 * 
	 * @return true if value is zero, false otherwise
	 */
	public boolean isZero()
	{
		return value == 0;
	}

	/**
	 * Returns instance whose value is equvalent to specified seconds
	 * 
	 * @param seconds specified seconds for initial value
	 * @return instance of duration in milliseconds
	 */
	public static DurationMilliseconds ofSeconds(int seconds)
	{
		return DurationMilliseconds.of(seconds * millisecondsPerSecond);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Milliseconds;
	}

	@Override
	public String toString()
	{
		return String.format("DurationMilliseconds [value=%s, units=%s]", value, units);
	}
}
