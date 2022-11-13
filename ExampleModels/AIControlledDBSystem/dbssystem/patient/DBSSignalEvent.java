package dbssystem.patient;

import dbssystem.common.DBSSignal;
import dbssystem.common.DBSSignalSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.PhaseShiftRadians;
import sysmlinjava.valuetypes.PotentialElectricalVolts;

/**
 * SysMLSignalEvent for the DBS signal that is transmitted by the DBS actuator
 * to the patient's brain.
 * 
 * @author ModelerOne
 *
 */
public class DBSSignalEvent extends SysMLSignalEvent
{
	public DBSSignalEvent(DBSSignalSignal signal)
	{
		super("DBSSignal");
		this.signal = signal;
	}

	public DBSSignalEvent(DBSSignal signal)
	{
		super("DBSSignal");
		this.signal = new DBSSignalSignal(signal);
	}

	public DBSSignal getSignal()
	{
		return ((DBSSignalSignal)signal).value;
	}

	@Override
	public String toString()
	{
		return String.format("DBSSignalSignalEvent [signal=%s]", signal);
	}

	@Override
	public void createSignal()
	{
		signal = new DBSSignalSignal(new DBSSignal(new FrequencyHertz(0), new PotentialElectricalVolts(0), new PhaseShiftRadians(0)));
	}
}
