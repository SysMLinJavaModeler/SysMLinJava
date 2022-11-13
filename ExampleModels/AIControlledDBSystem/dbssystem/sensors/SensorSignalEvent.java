package dbssystem.sensors;

import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;

public abstract class SensorSignalEvent extends SysMLSignalEvent
{
	public SensorSignalEvent(SysMLSignal signal, String name)
	{
		super(name);
		this.signal = signal;
	}

	@Override
	public void createSignal()
	{
		signal = null;
	}

}
