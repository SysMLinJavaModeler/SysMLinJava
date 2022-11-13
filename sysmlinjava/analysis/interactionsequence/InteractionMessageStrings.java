package sysmlinjava.analysis.interactionsequence;

import java.io.Serializable;
import java.time.Instant;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.ports.SysMLFullPort;
import sysmlinjava.ports.SysMLProxyPort;

/**
 * Strings that define a message between lifelines in a sequence diagram, i.e.
 * the source lifeline, destination lifeline, and the message from the source to
 * the destination.
 * 
 * @author ModelerOne
 *
 */
public class InteractionMessageStrings implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -3032907429853237196L;

	/**
	 * Time of the message occurance
	 */
	public Instant time;
	/**
	 * Title/name for the message to be displayed between the source and destination
	 */
	public String messageTitle;
	/**
	 * Title/name of the source lifeline
	 */
	public String sourceLifelineTitle;
	/**
	 * Title/name of the destination lifeline
	 */
	public String destinationLifelineTitle;

	/**
	 * Constructor from actual string values
	 * 
	 * @param time                     time of the message occurance
	 * @param messageTitle             title/name for the message to be displayed
	 *                                 between the source and destination
	 * @param sourceLifelineTitle      title/name of the source lifeline
	 * @param destinationLifelineTitle title/name of the destination lifeline
	 */
	public InteractionMessageStrings(Instant time, String messageTitle, String sourceLifelineTitle, String destinationLifelineTitle)
	{
		super();
		this.time = time;
		this.messageTitle = messageTitle;
		this.sourceLifelineTitle = sourceLifelineTitle;
		this.destinationLifelineTitle = destinationLifelineTitle;
	}

	/**
	 * Constructor from SysMLinJava elements involved in the message to be converted
	 * into message string values
	 * 
	 * @param time          time of the message occurance
	 * @param contextBlock  block that contained the port that was the source of the
	 *                      message
	 * @param messageSignal signal that carried the message transmitted from source
	 *                      to destination
	 * @param peerPort      full port that received the message for the block that
	 *                      was the destination of the message
	 */
	public InteractionMessageStrings(Instant time, SysMLBlock contextBlock, SysMLSignal messageSignal, SysMLFullPort peerPort)
	{
		super();
		this.time = time;
		sourceLifelineTitle = contextBlock.identityString();
		destinationLifelineTitle = peerPort.contextBlock.get().identityString();
		messageTitle = messageSignal.stackNamesString();
	}

	/**
	 * Constructor from SysMLinJava elements involved in the message to be converted
	 * into message string values
	 * 
	 * @param time         time of the message occurance
	 * @param contextBlock block that contained the port that was the source of the
	 *                     message
	 * @param message      string representation of the message used for the
	 *                     interaction, i.e. the message transmitted from source to
	 *                     destination
	 * @param peerPort     proxy port that received the message for the block that
	 *                     was the destination of the message
	 */
	public InteractionMessageStrings(Instant time, SysMLBlock contextBlock, String message, SysMLProxyPort peerPort)
	{
		super();
		this.time = time;
		sourceLifelineTitle = contextBlock.identityString();
		if (peerPort.implementingContextBlock.isPresent())
			destinationLifelineTitle = peerPort.implementingContextBlock.get().identityString();
		messageTitle = message;
	}

	/**
	 * Returns a log-like string representation of the message where it depicts the
	 * message display on a sequence diagram, i.e. labeled line from source to
	 * destination
	 * 
	 * @return sequence diagram-like string for use in a log output
	 */
	public String logString()
	{
		return String.format("[SEQ] %s --- %s ---> %s", sourceLifelineTitle, messageTitle, destinationLifelineTitle);
	}

	@Override
	public String toString()
	{
		return String.format("InteractionMessageStrings [time=%s, message=%s, source=%s, destination=%s]", time, messageTitle, sourceLifelineTitle, destinationLifelineTitle);
	}
}