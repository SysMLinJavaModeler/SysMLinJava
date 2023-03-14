package sysmlinjava.analysis.interactionmessagetransmitter;

import java.time.Instant;
import java.util.logging.Logger;
import sysmlinjava.analysis.interactionsequence.InteractionMessageStrings;
import sysmlinjava.analysis.interactionsequence.InteractionMessageTransmitter;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.InteractionMessageUtility;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.ports.SysMLProxyPort;

/**
 * {@code InteractionMessageTransmitters} enable the transmission from the
 * simulation to an interaction sequence display (sequence diagram) of the
 * interaction messages that occur during the simulation. This implmentation of
 * the {@code SysMLFullPort's} {@code InteractionMessageUtility} enables a
 * grapical display of the real-time sequence diagram of the interactions
 * between the specified parts of the model throughout the simulation.
 * <p>
 * The {@code InteractionMessageTransmitters} must be created and assigned to
 * the {@code messageUtility} variable of the {@code SysMLFullPort} class to
 * perform its function. This creation/assignment should be performed in an
 * overridden version of the {@code SysMLFullPort}'s {@code
 * createInteractionMessageUtility()} operation.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.ports.SysMLFullPort#messageUtility
 * @see sysmlinjava.ports.SysMLFullPort#createInteractionMessageUtility()
 */
public class InteractionMessageTransmitters implements InteractionMessageUtility
{
	/**
	 * A transmitter of interaction message text to UDP port for possible display
	 * and/or storage of this port's transmissions occuring during model execution.
	 */
	private InteractionMessageTransmitter interactionMessageTransmitter;
	/**
	 * Indication if the strings that represent the state transitions are to be
	 * logged to the console. If true, strings are logged to console, otherwise they
	 * are not.
	 */
	private boolean logToConsole;

	/**
	 * Constructor
	 * 
	 * @param interactionMessageTransmitter the
	 *                                      {@code InteractionMessageTransmitter}
	 *                                      that is to perform the transmission of
	 *                                      the interaction message to an
	 *                                      {@code InteractionMessageReceiver}.
	 * @param logToConsole                  indication if the strings that represent
	 *                                      the state transitions are to be logged
	 *                                      to the console. If true, strings are
	 *                                      logged to console, otherwise they are
	 *                                      not.
	 */
	public InteractionMessageTransmitters(InteractionMessageTransmitter interactionMessageTransmitter, boolean logToConsole)
	{
		super();
		this.interactionMessageTransmitter = interactionMessageTransmitter;
		this.logToConsole = logToConsole;
	}

	/**
	 * Performs a transmission of interaction message data for a full port
	 * transmission. Note this operation is automatically invoked by the
	 * {@code SysMLFullPort} if/when the port is connected to another
	 * {@code SysMLFullPort}.
	 */
	@Override
	public void perform(Instant time, SysMLBlock contextBlock, SysMLSignal messageSignal, SysMLFullPort peerPort, Logger logger)
	{
		InteractionMessageStrings strings = new InteractionMessageStrings(time, contextBlock, messageSignal, peerPort);
		if (logToConsole)
			logger.info(strings.logString());
		interactionMessageTransmitter.transmit(strings);
	}

	/**
	 * Performs a transmission of interaction message data for a proxy port
	 * transmission. Note this operation is automatically invoked by the
	 * {@code SysMLProxyPort} if/when the port is connected to another
	 * {@code SysMLProxyPort}.
	 */
	@Override
	public void perform(Instant time, SysMLBlock contextBlock, String message, SysMLProxyPort peerPort, Logger logger)
	{
		InteractionMessageStrings strings = new InteractionMessageStrings(time, contextBlock, message, peerPort);
		if (logToConsole)
			logger.info(strings.logString());
		interactionMessageTransmitter.transmit(strings);
	}

	/**
	 * Stops the transmission of sequence diagram information (interaction messages)
	 * to the sequence diagram display.
	 */
	public void stop()
	{
		interactionMessageTransmitter.stop();
	}
}
