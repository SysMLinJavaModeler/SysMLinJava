package dbssystem.sensors;

import java.net.InetAddress;
import java.util.Optional;
import dbssystem.common.MotionSignalSignal;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port for receiving the tremor motion signals from the patient by the tremor
 * motion sensor
 * 
 * @author ModelerOne
 *
 */
public class MotionSignalInPort extends SysMLFullPort
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock TremorSensor to receive the signals
	 * @param ipAddress    IP address to be used for UDP-based port receptions
	 * @param udpPort      UDP port number to be used for UDP-based port receptions
	 */
	public MotionSignalInPort(TremorSensor contextBlock, InetAddress ipAddress, Integer udpPort)
	{
		super(contextBlock, Optional.of(contextBlock), ipAddress, udpPort, 0L, "MotionSignalInPort");
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof MotionSignalSignal input)
			result = new MotionSignalEvent(input.value);
		return result;
	}
}
