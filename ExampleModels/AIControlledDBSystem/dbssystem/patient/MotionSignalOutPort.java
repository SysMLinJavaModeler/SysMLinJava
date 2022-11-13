package dbssystem.patient;

import dbssystem.common.MotionSignal;
import dbssystem.common.MotionSignalSignal;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.SysMLFullPort;

/**
 * Port to transmit the patient's motion (tremor) signal out from the patient
 * 
 * @author ModelerOne
 *
 */
public class MotionSignalOutPort extends SysMLFullPort
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock the Patient from which the motion signal is to be
	 *                     transmitted
	 */
	public MotionSignalOutPort(Patient contextBlock)
	{
		super(contextBlock, 0L, "MotionSignalOutPort");
	}

	@Override
	protected SysMLSignal signalFor(SysMLClass object)
	{
		SysMLSignal result = null;
		if (object instanceof MotionSignal level)
			result = new MotionSignalSignal(level);
		return result;
	}
}
