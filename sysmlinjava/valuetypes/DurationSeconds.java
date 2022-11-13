package sysmlinjava.valuetypes;

import java.time.Duration;
import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for time duration in seconds
 * 
 * @author ModelerOne
 *
 */
public class DurationSeconds extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant value for zero duration
	 */
	public static final DurationSeconds ZERO = new DurationSeconds(0.0);
	/**
	 * Constant value for minimum duration supported
	 */
	public static final DurationSeconds MIN = new DurationSeconds(Double.MIN_VALUE);
	/**
	 * Constant value for maximum duration supported
	 */
	public static final DurationSeconds MAX = new DurationSeconds(Double.MAX_VALUE);

	/**
	 * Constructor for initial value specified as primitive double
	 * 
	 * @param value initial value
	 */
	public DurationSeconds(double value)
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
	public DurationSeconds(double value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	/**
	 * Constructor for initial value specified as Real
	 * 
	 * @param value initial value
	 */
	public DurationSeconds(RReal value)
	{
		super(value.value);
	}

	/**
	 * Sets the value of this to the specified duration value
	 * 
	 * @param duration value to be set as this value
	 */
	public void setValue(DurationSeconds duration)
	{
		super.setValue(duration.value);
	}

	/**
	 * Returns {@code DurationSeconds} of specified seconds as primitive double
	 * 
	 * @param seconds seconds value of {@code DurationSeconds}
	 * @return instance of {@code DurationSeconds} for specified seconds
	 */
	public static DurationSeconds of(double seconds)
	{
		return new DurationSeconds(seconds);
	}

	/**
	 * Returns {@code DurationSeconds} of specified seconds as Real
	 * 
	 * @param seconds seconds value of {@code DurationSeconds}
	 * @return instance of {@code DurationSeconds} for specified seconds
	 */
	public static DurationSeconds of(RReal seconds)
	{
		return new DurationSeconds(seconds);
	}

	/**
	 * Returns {@code DurationSeconds} of specified Duration
	 * 
	 * @param duration {@code Duration} value for new {@code DurationSeconds}
	 * @return instance of {@code DurationSeconds} for specified {@code Duration}
	 */
	public static DurationSeconds of(Duration duration)
	{
		return new DurationSeconds(duration.toMillis() / 1000.0);
	}

	/**
	 * Returns {@code DurationSeconds} between two InstantMilliseconds instances
	 * 
	 * @param earlier earlier instant
	 * @param later   later instant
	 * @return duration in seconds between the two times, i.e. later instant minus
	 *         earlier instant
	 */
	public static DurationSeconds between(InstantMilliseconds earlier, InstantMilliseconds later)
	{
		return DurationSeconds.of((later.value - earlier.value) / 1000.0);
	}

	/**
	 * Returns {@code DurationSeconds} of specified milliseconds
	 * 
	 * @param milliseconds milliseconds value of {@code DurationSeconds}
	 * @return instance of {@code DurationSeconds} for specified milliseconds
	 */
	public static DurationSeconds ofMillis(double milliseconds)
	{
		return new DurationSeconds(milliseconds / 1_000.0);
	}

	/**
	 * Returns {@code DurationSeconds} for this duration plus specified
	 * {@code DurationSeconds}
	 * 
	 * @param duration {@code DurationSeconds} to be added to this
	 *                 {@code DurationSeconds}
	 * @return {@code DurationSeconds} that is addition of this and specified
	 *         duration
	 */
	public DurationSeconds plus(DurationSeconds duration)
	{
		return new DurationSeconds(value + duration.value);
	}

	/**
	 * Returns {@code DurationSeconds} for this duration plus specified seconds.
	 * 
	 * @param seconds seconds to be added to this {@code DurationSeconds}
	 * @return {@code DurationSeconds} that is addition of this and specified
	 *         duration
	 */
	public DurationSeconds plus(double seconds)
	{
		return new DurationSeconds(value + seconds);
	}

	/**
	 * Returns {@code DurationSeconds} for this duration minus specified
	 * {@code DurationSeconds}
	 * 
	 * @param duration {@code DurationSeconds} to be subtracted from this
	 *                 {@code DurationSeconds}
	 * @return {@code DurationSeconds} that is subtraction from this of specified
	 *         duration
	 */
	public DurationSeconds minus(DurationSeconds duration)
	{
		return new DurationSeconds(value - duration.value);
	}

	/**
	 * Returns {@code DurationSeconds} for this duration minus specified seconds.
	 * 
	 * @param seconds seconds to be subtracted from this {@code DurationSeconds}
	 * @return {@code DurationSeconds} that is subtraction from this of specified
	 *         duration
	 */
	public DurationSeconds minus(double seconds)
	{
		return new DurationSeconds(value - seconds);
	}

	/**
	 * Returns conversion to java.time.Duration
	 * 
	 * @return this as a java.time.Duration
	 */
	public Duration toDuration()
	{
		return Duration.ofMillis((long)(value * 1000.0));
	}

	/**
	 * Returns the modulus/remainder of this value for the specified divisor seconds
	 * 
	 * @param seconds duration value to be used as divisor
	 * @return duration seconds instance for this modulo of the specified seconds
	 */
	public DurationSeconds moduloOf(DurationSeconds seconds)
	{
		return DurationSeconds.of(value % seconds.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Seconds;
	}
}
