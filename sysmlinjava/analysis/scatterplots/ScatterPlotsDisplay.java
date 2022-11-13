package sysmlinjava.analysis.scatterplots;

/**
 * The {@code ScatterPlotsDisplay} is a simple console application that receives
 * {@code ScatterPlotData} via a UDP socket and displays the data as text on the
 * console.
 * <p>
 * <b>Note:</b> This simple text-based display is part of the basic SysMLinJava
 * tool set. More capable graphics-based displays are available commercially.
 * See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 *
 */
public class ScatterPlotsDisplay
{
	/**
	 * UDP port at which the display data and definition are to be received
	 */
	public static final int udpPort = 8894;
	/**
	 * Receiver of the display data and definition
	 */
	ScatterPlotsReceiver receiver;

	/**
	 * Constructor - initialize receiver
	 */
	public ScatterPlotsDisplay()
	{
		super();
		receiver = new ScatterPlotsReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the line chart data and displays them as toString()s
	 * to the console.<br>
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		ScatterPlotsDisplay display = new ScatterPlotsDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}
