package sysmlinjava.events;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.valuetypes.DurationMilliseconds;
import sysmlinjava.valuetypes.InstantMilliseconds;

/**
 * SysMLinJava's representation of the SysML time event. The
 * {@code SysMLTimeEvent} extends the basic {@code SysMLEvent} and provides two
 * methods of specifying a time to be associated with the time event, i.e.
 * <ul>
 * <li>absoluteTime: an {@code InstantMilliseconds} when the event occurs or
 * will occur</li>
 * <li>relativeTime: a {@code DurationMilliseconds} when the event occurs
 * relative to now</li>
 * <li></li>
 * </ul>
 * The time event can be specified to repeat at a specified interval as well by
 * specifying a periodic time as a {@code DurationMilliseconds}.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLTimeEvent extends SysMLEvent
{
	/**
	 * Identifying string that is presumably unique within the context of the
	 * context state machine.
	 */
	@Attribute
	public String timerID;

	/**
	 * Optional absolute time at which event is to happen, i.e. SysML's "at" time.
	 * Must be empty if relativeTime is present.
	 */
	@Attribute
	public Optional<InstantMilliseconds> absoluteTime;
	/**
	 * Optional relative time (from now) at which event is to happen, i.e. SysML's
	 * "after" time. Must be empty if absolute Time is present. Absolute time will
	 * be used if both times are present.
	 */
	@Attribute
	public Optional<DurationMilliseconds> relativeTime;

	/**
	 * Optional periodic time (for repeated events) at which event is to repeat.
	 * This a SyMLinJava extension to the SysML time event. Repeats start after the
	 * first event occuring at the absolute or relative time specified.
	 */
	@Attribute
	public Optional<DurationMilliseconds> periodicTime;

	/**
	 * Default timer ID if timer ID is not specified
	 */
	static final String unspecifiedTimerID = "unspecifiedTimerID";

	/**
	 * Constructor specifying an identifier and an absolute time of event occurance.
	 * 
	 * @param timerID      unique string-based identifier of this timer
	 * @param absoluteTime the instant at which the event is to occur
	 * @param periodicTime Optional period of repetion of the event after the first
	 *                     event at absoluteTime
	 */
	public SysMLTimeEvent(String timerID, InstantMilliseconds absoluteTime, Optional<DurationMilliseconds> periodicTime)
	{
		super();
		this.timerID = (timerID != null && !timerID.isBlank()) ? timerID : unspecifiedTimerID;
		this.absoluteTime = absoluteTime != null ? Optional.of(absoluteTime) : Optional.of(InstantMilliseconds.now());
		this.relativeTime = Optional.empty();
		this.periodicTime = periodicTime != null ? periodicTime : Optional.empty();
	}

	/**
	 * Constructor specifying an identifier and a relative time of event occurance.
	 * 
	 * @param timerID      unique string-based identifier of this timer
	 * @param relativeTime the duration of time from now that the event is to occur.
	 * @param periodicTime Optional period of repetion of the event after the first
	 *                     event at relativeTime
	 */
	public SysMLTimeEvent(String timerID, DurationMilliseconds relativeTime, Optional<DurationMilliseconds> periodicTime)
	{
		super();
		this.timerID = (timerID != null && !timerID.isBlank()) ? timerID : unspecifiedTimerID;
		this.absoluteTime = Optional.empty();
		this.relativeTime = relativeTime != null ? Optional.of(relativeTime) : Optional.of(DurationMilliseconds.ZERO);
		this.periodicTime = periodicTime != null ? periodicTime : Optional.empty();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLTimeEvent [timerID=%s, absoluteTime=%s, relativeTime=%s, periodicTime=%s]", timerID, absoluteTime, relativeTime, periodicTime);
	}

}
