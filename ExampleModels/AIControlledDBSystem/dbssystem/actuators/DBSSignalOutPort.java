package dbssystem.actuators;

import dbssystem.common.DBSSignal;
import dbssystem.common.DBSSignalSignal;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to transmit a low-power signal out of the DBS actuator
 * 
 * @author ModelerOne
 *
 */
public class DBSSignalOutPort extends SysMLFullPort
{

	public DBSSignalOutPort(DBSActuator contextBlock)
	{
		super(contextBlock, 0L, "DBSSignalOutPort");
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof DBSSignal signal)
			result = new DBSSignalSignal(signal);
		return result;
	}

}
