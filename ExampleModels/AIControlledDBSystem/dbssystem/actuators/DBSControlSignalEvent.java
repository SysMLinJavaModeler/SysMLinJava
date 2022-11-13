package dbssystem.actuators;

import dbssystem.common.DBSControl;
import dbssystem.common.DBSControlSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.PhaseShiftRadians;

public class DBSControlSignalEvent extends SysMLSignalEvent
{
	public DBSControlSignalEvent(DBSControlSignal signal)
	{
		super("DBSControl");
		this.signal = signal;
	}

	public DBSControl getControl()
	{
		return ((DBSControlSignal)signal).control;
	}

	@Override
	public void createSignal()
	{
		signal = new DBSControlSignal(new DBSControl(new FrequencyHertz(0), new PhaseShiftRadians(0)) );
	}
}
