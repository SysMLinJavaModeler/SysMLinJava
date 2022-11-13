package sysmlinjava.ports;

import java.time.Instant;
import java.util.logging.Logger;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLSignal;

/**
 * {@code MessagesUtility} is an interface for an implementation of a utility
 * for accessing the message information (source block, message type,
 * destination block, etc.) of a block interaction. These block interactions are
 * typically performed as signal transmissions or operation calls between blocks
 * via full or proxy ports. These interactions occur during model execution
 * forming an interaction sequence, which can be displayed in the form of a
 * SysML sequence diagram. Use of a message utility can enable the generation of
 * the sequence diagram during model execution.
 * <p>
 * The interface's operations are invoked by the {@code SysMLFullPort} during
 * performance of full port transmissions, i.e. during the {@code transmit()}
 * operation. The implementation of the {@code MessagesUtiliy} should be created
 * in an override of the {@code createMessagessUtility()} operation by
 * initializing the {@code messagesUtility} variable, e.g.
 * 
 * <pre>
        messagesUtility = new MyMessagesUtility(int arg);
 * </pre>
 * 
 * where the constructor is for a class that implements the
 * {@code MessagesUtility} interface. The referenced class below from the
 * SysMLinJava tools package is an example implementation of this interface for
 * transmitting interaction sequence message information to sequence diagram
 * display applications.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.analysis.interactionmessagetransmitter.InteractionMessageTransmitters
 */
public interface InteractionMessageUtility
{
	/**
	 * Performs the actual transmission of an interaction message (in the form a a
	 * {@code SysMLSignal} between the specified block and the specified peer port
	 * to a sequence diagram display. The {@code perform()} operation is invoked by
	 * the {@code SysMLFullPort}'s {@code transmit()} operation, immediately before
	 * transmitting the {@code SysMLSignal}-based message to the peer port thereby
	 * enabling real-time display of the interaction in the sequence diagram.
	 * 
	 * @param time          time of the interaction (message transmission)
	 * @param contextBlock  SysMLBlock in whose context this full port resides, i.e.
	 *                      in which it is a full port
	 * @param messageSignal signal which contains the message
	 * @param peerPort      peer full port to which the message is transmitted
	 * @param logger        this full port's logger to be used for logging as needed
	 */
	void perform(Instant time, SysMLBlock contextBlock, SysMLSignal messageSignal, SysMLFullPort peerPort, Logger logger);

	/**
	 * /** Performs the actual transmission of an interaction message (in the form a
	 * a {@code SysMLSignal} between the specified block and the specified peer port
	 * to a sequence diagram display. The {@code perform()} operation is invoked by
	 * the class that extends the {@code SysMLProxyPort} {@code transmit()}
	 * operation, immediately before invoking the operation with the name specified
	 * by a text-based message in the peer proxy port thereby enabling real-time
	 * display of the interaction in the sequence diagram.
	 * 
	 * @param time         time of the interaction (operation call)
	 * @param contextBlock SysMLBlock in whose context this proxy port resides, i.e.
	 *                     in which it is a proxy port
	 * @param message      textual representation of the operation call (name,
	 *                     arguments, etc.)
	 * @param peer         peer proxy port in which the invoked operation resides.
	 * @param logger       this proxy port's logger to be used for logging as
	 *                     needed.
	 */
	void perform(Instant time, SysMLBlock contextBlock, String message, SysMLProxyPort peer, Logger logger);
}