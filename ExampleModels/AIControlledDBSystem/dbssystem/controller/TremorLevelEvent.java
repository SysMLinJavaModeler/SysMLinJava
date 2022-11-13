package dbssystem.controller;

import dbssystem.common.TremorLevel;
import dbssystem.common.TremorLevelSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.valuetypes.DistanceMillimeters;
import sysmlinjava.valuetypes.FrequencyHertz;

public class TremorLevelEvent extends SysMLSignalEvent
{
	public TremorLevelEvent(TremorLevelSignal signal)
	{
		super("TremorLevel");
	}

	public TremorLevelEvent(TremorLevel packet)
	{
		super("TremorLevel");
		this.signal = new TremorLevelSignal(packet);
	}

	public TremorLevel getLevel()
	{
		return ((TremorLevelSignal)signal).value;
	}

	@Override
	public String toString()
	{
		return String.format("TremorLevelSignalEvent [signal=%s]", signal);
	}

	@Override
	public void createSignal()
	{
		signal = new TremorLevelSignal(new TremorLevel(new FrequencyHertz(0), new DistanceMillimeters(0)));
	}
}
