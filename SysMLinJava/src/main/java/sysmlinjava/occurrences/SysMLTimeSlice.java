package sysmlinjava.occurrences;

import java.time.Instant;
import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava representation of the SysML time slice. {@code SysMLTimeSlice}
 * contains two instants in time representing the start and done times of the
 * time slice.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLTimeSlice extends SysMLAnything
{
	/**
	 * Instant for the start time of the time slice
	 */
	public Instant start;
	/**
	 * Instant for the done time of the time slice
	 */
	public Instant done;

	/**
	 * @param start time the time slice begins
	 * @param done  time the time slice ends
	 * @param name  unique name of the time slice
	 * @param id    unique ID for the the time slice (or 0 if not used)
	 */
	public SysMLTimeSlice(Instant start, Instant done, String name, Long id)
	{
		super(name, id);
		this.start = start;
		this.done = done;
	}
}