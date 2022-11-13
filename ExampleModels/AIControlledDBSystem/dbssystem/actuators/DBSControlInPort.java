package dbssystem.actuators;

import java.net.InetAddress;
import java.util.Optional;
import dbssystem.common.DBSControlSignal;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to receive control data by the DBS actuator
 * 
 * @author ModelerOne
 *
 */
public class DBSControlInPort extends SysMLFullPort
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock DBSActuator to receive the signals
	 * @param ipAddress    IP address to be used for UDP-based port receptions
	 * @param udpPort      UDP port number to be used for UDP-based port receptions
	 */
	public DBSControlInPort(DBSActuator contextBlock, InetAddress ipAddress, Integer udpPort)
	{
		super(contextBlock, Optional.of(contextBlock), ipAddress, udpPort, 0L, "DBSControlInPort");
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof DBSControlSignal control)
			result = new DBSControlSignalEvent(control);
		return result;
	}
}
