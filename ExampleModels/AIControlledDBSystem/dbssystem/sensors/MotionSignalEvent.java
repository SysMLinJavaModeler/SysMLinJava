package dbssystem.sensors;

import dbssystem.common.MotionSignal;
import dbssystem.common.MotionSignalSignal;

public class MotionSignalEvent extends SensorSignalEvent
{
	public MotionSignalEvent(MotionSignalSignal signal)
	{
		super(signal, "MotionSignal");
	}

	public MotionSignalEvent(MotionSignal signal)
	{
		super(new MotionSignalSignal(signal), "MotionSignal");
	}

	public MotionSignal getSignal()
	{
		return ((MotionSignalSignal)signal).value;
	}

	@Override
	public String toString()
	{
		return String.format("MotionSignalSignalEvent [signal=%s]", signal);
	}
}
