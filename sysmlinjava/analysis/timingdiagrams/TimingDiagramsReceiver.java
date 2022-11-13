package sysmlinjava.analysis.timingdiagrams;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive timing diagram data for
 * disply via the {@code TimingDiagramsDisplay}. This class simply implements
 * the {@code UDPReceiver}'s {@code receive(Object)} operation for the timing
 * diagram data objects by displaying the objects as log-type strings similar to
 * {@code toString()}s.
 * 
 * @author ModelerOne
 * @see TimingDiagramDefinition
 * @see StateTransitionTiming
 */
public class TimingDiagramsReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive graph data
	 */
	public TimingDiagramsReceiver(int udpPort)
	{
		super(udpPort);
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof TimingDiagramDefinition)
			logger.info(((TimingDiagramDefinition)data).toLogString());
		else if (data instanceof StateTransitionTiming)
			logger.info(((StateTransitionTiming)data).toLogString());
		return false;
	}
}
