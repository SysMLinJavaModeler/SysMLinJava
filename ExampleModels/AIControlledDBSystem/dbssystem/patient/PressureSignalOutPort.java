package dbssystem.patient;

import dbssystem.common.PressureSignal;
import dbssystem.common.PressureSignalSignal;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to transmit the patient's blood pressure signal out to a controller
 * 
 * @author ModelerOne
 *
 */
public class PressureSignalOutPort extends SysMLFullPort
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock the Patient from which the pressure signal is to
	 *                     transmitted
	 */
	public PressureSignalOutPort(Patient contextBlock)
	{
		super(contextBlock, 0L, "PressureSignalOutPort");
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if (object instanceof PressureSignal output)
			result = new PressureSignalSignal(output);
		return result;
	}
}
