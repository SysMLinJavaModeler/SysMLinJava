package sysmlinjava.analysis.statetransitions;

import sysmlinjava.analysis.common.UDPTransmitter;

/**
 * The {@code StateTransitionStringsTransmitter} is a specialization of the
 * {@code UDPTransmitter} to transmit {@code StateTransitionStrings} to a
 * {@code StateTransitionStringsReceiver} for the display of state transition
 * tables. The {@code StateTransitionStringsTransmitter} is simply an extension
 * of the generic {@code UDPTransmitter} for the transmission of
 * {@code StateTransitionStrings}.
 * 
 * @author ModelerOne
 * @see sysmlinjava.analysis.statetransitions.StateTransitionStrings
 */
public class StateTransitionTablesTransmitter extends UDPTransmitter<StateTransitionStrings>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port to which transmit state transition data is to be
	 *                     transmitted to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public StateTransitionTablesTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "StateTransitionTablesTransmitter");
	}
}
