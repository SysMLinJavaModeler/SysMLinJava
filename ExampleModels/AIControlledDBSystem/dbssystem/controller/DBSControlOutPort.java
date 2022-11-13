package dbssystem.controller;

import dbssystem.common.DBSControl;
import dbssystem.common.DBSControlSignal;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to transmit a DBS actuator control out of the DBS controller
 * 
 * @author ModelerOne
 *
 */
public class DBSControlOutPort extends SysMLFullPort
{
	public DBSControlOutPort(SysMLBlock contextBlock, Long index, String name)
	{
		super(contextBlock, index, name);
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof DBSControl control)
			result = new DBSControlSignal(control);
		return result;
	}

}
