package sysmlinjava.analysis.interactionsequence;

import sysmlinjava.analysis.common.UDPTransmitter;

/**
 * The {@code InteractionMessageTransmitter} is a specialization of the
 * {@code UDPTransmitter} to transmit {@code InteractionMessageStrings} to a
 * {@code InteractionMessageReceiver} for the display of interaction messages.
 * This class simply extends the generic {@code UDPTransmitter} for the
 * {@code InteractionMessageStrings}.
 * 
 * @author ModelerOne
 * @see sysmlinjava.analysis.interactionsequence.InteractionMessageStrings
 */
public class InteractionMessageTransmitter extends UDPTransmitter<InteractionMessageStrings>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port which to transmit interaction message data to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public InteractionMessageTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "InteractionMessageTransmitter");
	}
}