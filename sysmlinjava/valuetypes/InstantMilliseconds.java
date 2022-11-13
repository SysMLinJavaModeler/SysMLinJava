package sysmlinjava.valuetypes;

import java.time.Instant;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for time at an instant in milliseconds from the
 * standard "epoch" time. This value type is a analog for the Java Instant.
 * 
 * @author ModelerOne
 *
 */
public class InstantMilliseconds extends IInteger
{
	/** Serializable ID*/private static final long serialVersionUID = 1586873940618314774L;

	/**
	 * Constructor
	 * 
	 * @param millis double value to be used for this initial value
	 */
	public InstantMilliseconds(long millis)
	{
		super(millis);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyOf instant whose value is to used as initial value of this copy
	 */
	public InstantMilliseconds(InstantMilliseconds copyOf)
	{
		super(copyOf);
	}

	/**
	 * Constructor - default of zero
	 */
	public InstantMilliseconds()
	{
		super(0);
	}

	/**
	 * Returns the instant for the current time - now
	 * 
	 * @return instant value for now
	 */
	public static InstantMilliseconds now()
	{
		return new InstantMilliseconds(Instant.now().toEpochMilli());
	}

	/**
	 * Constant value for max instant
	 */
	public static InstantMilliseconds MAX = new InstantMilliseconds(Long.MAX_VALUE);
	/**
	 * Constant value for min instant
	 */
	public static InstantMilliseconds MIN = new InstantMilliseconds(Long.MIN_VALUE);
	/**
	 * Constant value for epoch instant (instant zero)
	 */
	public static InstantMilliseconds EPOCH = new InstantMilliseconds(0L);

	/**
	 * Returns instance that is conversion of specified java.time.Instant
	 * 
	 * @param instant java.time.Instant value
	 * @return instant for specified java.time.Instant
	 */
	public static InstantMilliseconds of(Instant instant)
	{
		return new InstantMilliseconds(instant.toEpochMilli());
	}

	/**
	 * Returns instance that is conversion of integral milliseconds since epoch
	 * instant
	 * 
	 * @param epochMillis integer value of milliseconds since epoch instant
	 * @return instant for specified milliseconds since epoch
	 */
	public static InstantMilliseconds of(IInteger epochMillis)
	{
		return new InstantMilliseconds(epochMillis.value);
	}

	/**
	 * Returns instance that is conversion of integral milliseconds since epoch
	 * instant
	 * 
	 * @param epochMillis integer value of milliseconds since epoch instant
	 * @return instant for specified milliseconds since epoch
	 */
	public static InstantMilliseconds of(Integer epochMillis)
	{
		return new InstantMilliseconds(epochMillis);
	}

	/**
	 * Returns current instant milliseconds
	 * 
	 * @return current instant milliconds as long
	 */
	public long millis()
	{
		return value;
	}

	/**
	 * Returns whether current instant is before specified instant in time
	 * 
	 * @param instant instant to compare
	 * @return true if specified instant is before this instant, false otherwise
	 */
	public boolean isBefore(InstantMilliseconds instant)
	{
		return value < instant.value;
	}

	/**
	 * Returns whether current instant is after specified instant in time
	 * 
	 * @param instant instant to compare
	 * @return true if specified instant is after this instant, false otherwise
	 */
	public boolean isAfter(InstantMilliseconds instant)
	{
		return value > instant.value;
	}

	/**
	 * Returns whether current instant is before specified java.time.Instant in time
	 * 
	 * @param instant instant to compare
	 * @return true if specified instant is before this instant, false otherwise
	 */
	public boolean isBefore(Instant instant)
	{
		return value < instant.toEpochMilli();
	}

	/**
	 * Returns whether current instant is after specified java.time.Instant in time
	 * 
	 * @param instant instant to compare
	 * @return true if specified instant is after this instant, false otherwise
	 */
	public boolean isAfter(Instant instant)
	{
		return value > instant.toEpochMilli();
	}

	/**
	 * Returns instant that is this instant with added duration
	 * 
	 * @param durationSeconds duration to be added to this instant
	 * @return instant that is this instant with added duration
	 */
	public InstantMilliseconds add(DurationSeconds durationSeconds)
	{
		return new InstantMilliseconds((long)(value + durationSeconds.value * 1000));
	}

	/**
	 * Returns time duration that is the specified time instant sbutracted from this
	 * time instant
	 * 
	 * @param time instant to be subtracted from this instant
	 * @return duration result of the subraction
	 */
	public DurationSeconds subtracted(InstantMilliseconds time)
	{
		return new DurationSeconds((value - time.value) / 1000.0);
	}

	/**
	 * Sets this value to the specified instant value
	 * 
	 * @param instant instant whose value is to be used to set this value
	 */
	public void setValue(InstantMilliseconds instant)
	{
		super.setValue(instant.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Milliseconds;
	}

	@Override
	public String toString()
	{
		return String.format("InstantMilliseconds [time=%s(%s)]", Instant.ofEpochMilli(value).toString(), value);
	}
}
