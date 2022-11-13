package sysmlinjava.analysis.animatedareadisplay;

/**
 * The {@code AnimatedAreaDisplay} is a simple console application that receives
 * {@code AnimatedAreaDisplayData} via a UDP socket and displays the data as
 * text on the console.
 * <p>
 * <b>Note:</b> This simple text-based display is part of the basic SysMLinJava
 * API. More capable graphics-based displays are available commercially. See
 * SysMLinJava.com for details.
 * 
 * @author ModelerOne
 *
 */
public class AnimatedAreaDisplay
{
	/**
	 * UDP port number to be used to receive display definition and data objects
	 */
	public static final int udpPort = 8898;
	/**
	 * Receiver that performs the UDP protocol to receive and display the display
	 * definition and data objects
	 */
	AnimatedAreaDisplayReceiver receiver;

	/**
	 * Constructor
	 */
	public AnimatedAreaDisplay()
	{
		super();
		receiver = new AnimatedAreaDisplayReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the area display definition and data and displays
	 * their {@code toString()} strings as logs to the console.<br>
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		AnimatedAreaDisplay display = new AnimatedAreaDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}
