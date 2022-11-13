package dbssystem.sensors;

import dbssystem.common.TremorLevel;
import dbssystem.common.TremorLevelSignal;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to transmit a tremor level out of the TremorSensor
 * 
 * @author ModelerOne
 *
 */
public class TremorLevelOutPort extends SysMLFullPort
{
	public TremorLevelOutPort(SysMLBlock contextBlock, Long id, String name)
	{
		super(contextBlock, id, name);
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if(object instanceof TremorLevel level)
			result = new TremorLevelSignal(level);
		return result;
	}
}
