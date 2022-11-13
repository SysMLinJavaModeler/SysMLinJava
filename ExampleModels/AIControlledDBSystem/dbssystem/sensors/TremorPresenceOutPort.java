package dbssystem.sensors;

import dbssystem.common.TremorPresenceSignal;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.valuetypes.BBoolean;

/**
 * Port to transmit a tremor presence indicator out of the TremorSensor
 * 
 * @author ModelerOne
 *
 */
public class TremorPresenceOutPort extends SysMLFullPort
{
	public TremorPresenceOutPort(SysMLBlock contextBlock, Long id, String name)
	{
		super(contextBlock, id, name);
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof BBoolean isPresent)
			result = new TremorPresenceSignal(isPresent);
		return result;
	}
}
