package sysmlinjava.analysis.htmldisplay;

/**
 * The {@code HTMLDisplay} is a simple console application that receives
 * {@code HTMLString}s via a UDP socket and displays the HTML text on the
 * console.
 * <p>
 * <b>Note:</b> This simple text-based (console) display is part of the basic
 * SysMLinJava tool set. More capable web browser-like displays are available
 * commercially. See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 *
 */
public class HTMLDisplay
{
	/**
	 * Default port on which the UDP is to receive the HTML strings
	 */
	public static final int udpPort = 8891;
	/**
	 * Runnable that receives the HTML strings via UDP
	 */
	HTMLStringReceiver receiver;

	/**
	 * Constructor
	 */
	public HTMLDisplay()
	{
		super();
		receiver = new HTMLStringReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the HTML strings and displays them as toString()s to
	 * the console.<br>
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		HTMLDisplay display = new HTMLDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}
