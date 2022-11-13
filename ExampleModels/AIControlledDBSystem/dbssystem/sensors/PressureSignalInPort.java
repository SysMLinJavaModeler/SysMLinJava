package dbssystem.sensors;

import java.net.InetAddress;
import java.util.Optional;
import dbssystem.common.PressureSignalSignal;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port for receiving the blood pressure signals from the patient by the pulse
 * sensor
 * 
 * @author ModelerOne
 *
 */
public class PressureSignalInPort extends SysMLFullPort
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock PulseSensor to receive the signals
	 * @param ipAddress    IP address to be used for UDP-based port signal
	 *                     receptions
	 * @param udpPort      UDP port number to be used for UDP-based port signal
	 *                     receptions
	 */
	public PressureSignalInPort(PulseSensor contextBlock, InetAddress ipAddress, Integer udpPort)
	{
		super(contextBlock, Optional.of(contextBlock), ipAddress, udpPort, 0L, "PressureSignalInPort");
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof PressureSignalSignal input)
			result = new PressureSignalEvent(input.value);
		return result;
	}

}
