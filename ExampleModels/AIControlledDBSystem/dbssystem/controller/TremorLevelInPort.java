package dbssystem.controller;

import java.net.InetAddress;
import java.util.Optional;
import dbssystem.common.TremorLevelSignal;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to receive tremor level values from the tremor motion sensor by the
 * controller
 * 
 * @author ModelerOne
 *
 */
public class TremorLevelInPort extends SysMLFullPort
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock DBSControllerr to receive the signals
	 * @param ipAddress    IP address to be used for UDP-based port receptions
	 * @param udpPort      UDP port number to be used for UDP-based port receptions
	 */
	public TremorLevelInPort(DBSController contextBlock, InetAddress ipAddress, Integer udpPort)
	{
		super(contextBlock, Optional.of(contextBlock), ipAddress, udpPort, 0L, "TremorLevelInPort");
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof TremorLevelSignal levelSignal)
			result = new TremorLevelEvent(levelSignal.value);
		return result;
	}

}
