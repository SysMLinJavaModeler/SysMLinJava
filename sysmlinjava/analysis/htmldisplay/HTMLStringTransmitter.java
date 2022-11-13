package sysmlinjava.analysis.htmldisplay;

import sysmlinjava.analysis.common.UDPTransmitter;

/**
 * The {@code HTMLStringTransmitter} is a specialization of the
 * {@code UDPTransmitter} to transmit HTML strings to a
 * {@code HTMLStringReceiver} for the display of HTML. This class simply extends
 * the generic {@code UDPTransmitter} for the {@code HTMLString}.
 * 
 * @author ModelerOne
 * 
 * @see sysmlinjava.analysis.htmldisplay.HTMLString
 */
public class HTMLStringTransmitter extends UDPTransmitter<HTMLString>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port which to transmit HTML strings data to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public HTMLStringTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole);
	}
}
