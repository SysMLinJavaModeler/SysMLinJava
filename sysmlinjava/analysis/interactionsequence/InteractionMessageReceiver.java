package sysmlinjava.analysis.interactionsequence;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * Specialization of the UDPReceiver to receive interaction message data for
 * disply via the InteractionSequenceDisplay (sequence diagram). This class
 * simply implements the UDPReceiver's receive(Object) operation for the
 * interaction message objects by displaying the objects as toString()s in log
 * entries.
 * 
 * @author ModelerOne
 * @see sysmlinjava.analysis.interactionsequence.InteractionMessageStrings
 */
public class InteractionMessageReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive interaction message data
	 */
	public InteractionMessageReceiver(int udpPort)
	{
		super(udpPort, "InteractionMessageReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
//TODO
//		if (data instanceof InteractionMessageStrings messageStrings)
//			logger.info(messageStrings.toString());
		if (data instanceof InteractionMessageStrings)
			logger.info(((InteractionMessageStrings)data).toString());
		else
			logger.warning("unrecognized data type: " + data.getClass().getSimpleName());
		return false;
	}
}
