package sysmlinjava.occurrences;

import java.time.Instant;
import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava representation of the SysML snapshot in time.
 * {@code SysMLSnapshot} contains a single instant in time representing the time
 * of the snap shot.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLSnapshot extends SysMLAnything
{
	/**
	 * Instant of time of the snaphot
	 */
	public Instant instant;

	/**
	 * @param instant time of the snapshot
	 * @param name    unique name of this snapshot
	 * @param id      unique ID for the snapshot (or 0 if not needed)
	 */
	public SysMLSnapshot(Instant instant, String name, Long id)
	{
		super(name, id);
		this.instant = instant;
	}
}