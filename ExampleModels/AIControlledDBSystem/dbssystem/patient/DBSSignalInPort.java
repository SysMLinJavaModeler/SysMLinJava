package dbssystem.patient;

import java.util.Optional;
import dbssystem.common.DBSSignalSignal;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port for receiving the DBS actuator's signal by the patient.
 * 
 * @author ModelerOne
 *
 */
public class DBSSignalInPort extends SysMLFullPort
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock the Patient which is to receive the DBS signal
	 */
	public DBSSignalInPort(Patient contextBlock)
	{
		super(contextBlock, Optional.of(contextBlock), 0L, "DBSSignalInPort");
	}

	@Override
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		SysMLSignalEvent result = null;
		if (signal instanceof DBSSignalSignal dbsSignal)
			result = new DBSSignalEvent(dbsSignal.value);
		return result;
	}
}
