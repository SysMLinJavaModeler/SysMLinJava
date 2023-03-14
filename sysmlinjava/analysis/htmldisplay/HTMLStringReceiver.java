package sysmlinjava.analysis.htmldisplay;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * The {@code HTMLStringReceiver} is an extension of the {@code UDPReceiver} to
 * receive HTML strings for disply via the {@code HTMLDisplay}. This class
 * simply implements the {@code UDPReceiver}'s {@code receive(Object)} operation
 * for the HTML strings by displaying them as log messages via
 * {@code logger.info()} calls.
 * 
 * @author ModelerOne
 * @see HTMLString
 */
public class HTMLStringReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive HTML strings
	 */
	public HTMLStringReceiver(int udpPort)
	{
		super(udpPort, "HTMLStringReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof HTMLString)
			logger.info(((HTMLString)data).toString());
		else
			logger.warning("unrecognized object type received: " + data.getClass().getSimpleName());
		return false;
	}
}
