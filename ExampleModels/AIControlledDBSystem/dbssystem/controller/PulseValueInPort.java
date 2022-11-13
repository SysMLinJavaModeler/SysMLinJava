package dbssystem.controller;

import java.net.InetAddress;
import java.util.Optional;
import dbssystem.common.PulseValueSignal;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to receive pulse value signals from the pulse sensor by the controller
 * 
 * @author ModelerOne
 *
 */
public class PulseValueInPort extends SysMLFullPort
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock DBSController to receive the signals
	 * @param ipAddress    IP address to be used for UDP-based port receptions
	 * @param udpPort      UDP port number to be used for UDP-based port receptions
	 */
	public PulseValueInPort(DBSController contextBlock, InetAddress ipAddress, Integer udpPort)
	{
		super(contextBlock, Optional.of(contextBlock), ipAddress, udpPort, 0L, "PulseValueInPort");
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof PulseValueSignal pulseValue)
			result = new PulseValueSignalEvent(pulseValue.value);
		return result;
	}

}
