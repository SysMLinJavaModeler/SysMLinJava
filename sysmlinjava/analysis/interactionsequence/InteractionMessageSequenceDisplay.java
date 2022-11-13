package sysmlinjava.analysis.interactionsequence;

/**
 * Command line (console) based sequential display of interaction messages
 * generated by a SysMLinJava model process. The display runs as a
 * {@code main()} operation and receives the interaction message information as
 * formatted strings that are displayed as log outputs on the console.
 * 
 * @author ModelerOne
 *
 */
public class InteractionMessageSequenceDisplay
{
	/**
	 * Constant int value for the UDP port on which the interaction message strings
	 * will be received
	 */
	public static final int udpPort = 8892;
	/**
	 * Receiver of the interaction message strings
	 */
	InteractionMessageReceiver receiver;

	/**
	 * Constructor - constructs the receiver in preparation for run
	 */
	public InteractionMessageSequenceDisplay()
	{
		receiver = new InteractionMessageReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the interaction message data and displays it as
	 * toString()s to the console.<br>
	 * <b>Note:</b> This simple text-based (console) display is part of the basic
	 * SysMLinJava tool set. More capable graphics-based displays are available
	 * commercially. See SysMLinJava.com for details.
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		InteractionMessageSequenceDisplay display = new InteractionMessageSequenceDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}