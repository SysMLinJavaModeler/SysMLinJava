package sysmlinjava.analysis.timingdiagrams;

/**
 * The {@code TimingDiagramsDisplay} is a simple console application that
 * receives {@code StateTime}s via a UDP socket and displays the data as text on
 * the console.
 * <p>
 * <b>Note:</b> This simple text-based display is part of the basic SysMLinJava
 * tool set. More capable graphics-based displays are available commercially.
 * See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 *
 */
public class TimingDiagramsDisplay
{
	/**
	 * Default UDP port that will be used for datagram receives
	 */
	public static final int udpPort = 8896;

	/**
	 * Receiver of the {@code TimingDiagram} and {@code StateTime} objects to be
	 * displayed
	 */
	TimingDiagramsReceiver receiver;

	/**
	 * Constructor
	 */
	public TimingDiagramsDisplay()
	{
		super();
		receiver = new TimingDiagramsReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the state timing data and displays them as log entry-like strings to
	 * the console.<br>
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		TimingDiagramsDisplay display = new TimingDiagramsDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}
