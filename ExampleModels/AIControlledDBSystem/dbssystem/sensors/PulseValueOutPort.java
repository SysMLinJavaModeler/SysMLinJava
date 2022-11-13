package dbssystem.sensors;

import dbssystem.common.PulseValue;
import dbssystem.common.PulseValueSignal;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to transmit a pulse value out of the PulseSensor
 * 
 * @author ModelerOne
 *
 */
public class PulseValueOutPort extends SysMLFullPort
{
	public PulseValueOutPort(SysMLBlock contextBlock, Long index, String name)
	{
		super(contextBlock, index, name);
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof PulseValue value)
			result = new PulseValueSignal(value);
		return result;
	}
}
