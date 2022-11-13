package dbssystem.sensors;

import dbssystem.common.PressureSignal;
import dbssystem.common.PressureSignalSignal;

public class PressureSignalEvent extends SensorSignalEvent
{
	public PressureSignalEvent(PressureSignalSignal signal)
	{
		super(signal, "PressureSignal");
	}

	public PressureSignalEvent(PressureSignal signal)
	{
		super(new PressureSignalSignal(signal), "PressureSignal");
	}

	public PressureSignal getSignal()
	{
		return ((PressureSignalSignal)signal).value;
	}

	@Override
	public String toString()
	{
		return String.format("PressureSignalSignalEvent [signal=%s]", signal);
	}
}
