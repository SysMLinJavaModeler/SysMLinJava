package dbssystem.sensors;

import dbssystem.common.TremorPresenceSignal;
import sysmlinjava.valuetypes.BBoolean;

public class TremorPresenceEvent extends SensorSignalEvent
{
	public TremorPresenceEvent(TremorPresenceSignal signal)
	{
		super(signal, "TremorPresence");
	}

	public TremorPresenceEvent(BBoolean isPresent)
	{
		super(new TremorPresenceSignal(isPresent), "TremorPresence");
	}

	public BBoolean getPresence()
	{
		return ((TremorPresenceSignal)signal).isPresent;
	}

	@Override
	public String toString()
	{
		return String.format("TremorPresenceSignalEvent [signal=%s]", signal);
	}
}
