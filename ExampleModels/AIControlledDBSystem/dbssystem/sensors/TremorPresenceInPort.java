package dbssystem.sensors;

import java.util.Optional;
import dbssystem.common.TremorPresenceSignal;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port for receiving the tremor presence indications from the tremor sensor by
 * the pulse sensor
 * 
 * @author ModelerOne
 *
 */
public class TremorPresenceInPort extends SysMLFullPort
{
	public TremorPresenceInPort(SysMLBlock contextBlock, Long id, String name)
	{
		super(contextBlock, Optional.of(contextBlock), id, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof TremorPresenceSignal presence)
			result = new TremorPresenceEvent(presence.isPresent);
		return result;
	}

}
