package sysmlinjava.analysis.statetransitions;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive state transition table
 * data for disply via the {@code StateTransitionsDisplay}. This class simply
 * implements the {@code UDPReceiver}'s {@code receive(Object)} operation for
 * the state transition table data objects by displaying the objects as
 * {@code toString()}s in log entries.
 * 
 * @author ModelerOne
 * @see sysmlinjava.analysis.statetransitions.StateTransitionStrings
 */
public class StateTransitionStringsReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive state transitions data
	 */
	public StateTransitionStringsReceiver(int udpPort)
	{
		super(udpPort);
	}

//	@Override
	public boolean receive(Object data)
	{
		if (data instanceof StateTransitionStrings)
			logger.info(((StateTransitionStrings)data).toString());
		else
			logger.warning("unrecognized data type: " + data.getClass().getSimpleName());
		return false;
	}
}
