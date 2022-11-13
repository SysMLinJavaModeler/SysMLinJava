package sysmlinjava.analysis.animatedareadisplay;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive area display definition
 * and data objects for disply via the {@code AreaDisplay}. This class simply
 * implements the {@code UDPReceiver}'s {@code receive(Object)} operation for
 * the definition and data objects by displaying the objects as
 * {@code toString()}s log entries.
 * 
 * @author ModelerOne
 * @see AnimatedAreaDisplayDefinition
 * @see AnimatedAreaDisplayData
 */
public class AnimatedAreaDisplayReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive the area display definition and
	 *                data
	 */
	public AnimatedAreaDisplayReceiver(int udpPort)
	{
		super(udpPort);
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof AnimatedAreaDisplayData)
			logger.info(((AnimatedAreaDisplayData)data).toString());
		else if (data instanceof AnimatedAreaDisplayDefinition)
			logger.info(((AnimatedAreaDisplayDefinition)data).toString());
		return false;
	}
}
