package dbssystem.controller;

import dbssystem.common.PulseValue;
import dbssystem.common.PulseValueSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.valuetypes.IInteger;

public class PulseValueSignalEvent extends SysMLSignalEvent
{
	public PulseValueSignalEvent(PulseValueSignal signal)
	{
		super("PulseValue");
		this.signal = signal;
	}

	public PulseValueSignalEvent(PulseValue packet)
	{
		super("PulseValue");
		this.signal = new PulseValueSignal(packet);
	}

	@Override
	public void createSignal()
	{
		signal = new PulseValueSignal(new PulseValue(new IInteger(0)));
		
	}

	public PulseValue getValue()
	{
		return ((PulseValueSignal)signal).value;
	}

	@Override
	public String toString()
	{
		return String.format("PulseValueSignalEvent [signal=%s]", signal);
	}
}
