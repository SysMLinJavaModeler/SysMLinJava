package sysmlinjava.analysis.statetransitions;

/**
 * Console display of rows of state transitions table as received via
 * {@code StateTransitionStringsReceiver}
 * 
 * @author ModelerOne
 *
 */
public class StateTransitionsDisplay
{
	/**
	 * UDP port number on which the receiver operates
	 */
	public static final int udpPort = 8895;

	/**
	 * The receiver of the strings that contain the state transition table rows
	 */
	StateTransitionStringsReceiver receiver;

	/**
	 * Constructor (no args)
	 */
	public StateTransitionsDisplay()
	{
		super();
		receiver = new StateTransitionStringsReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the row strings and displays them as toStrings() to
	 * the console.<br>
	 * <b>Note:</b> This simple text-based (console) display is part of the basic
	 * SysMLinJava tool set. More capable graphics-based displays of the state
	 * transition tables are available commercially. See SysMLinJava.com for
	 * details.
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		StateTransitionsDisplay display = new StateTransitionsDisplay();
		display.receiver.run();
	}
}
