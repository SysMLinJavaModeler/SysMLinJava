package sysmlinjava.analysis.barcharts;

/**
 * The {@code BarChartsDisplay} is a simple console application that receives
 * {@code BarChartData} via a UDP socket and displays the data as text on the
 * console.
 * <p>
 * <b>Note:</b> This simple text-based display is part of the basic SysMLinJava
 * analysis tool set. More capable graphics-based displays are available
 * commercially. See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 *
 */
public class BarChartsDisplay
{
	/**
	 * UDP port via which the bar chart data is to be received
	 */
	public static final int udpPort = 8890;
	/**
	 * Bar Charts Receiver that receives the data for the display via the UDP port
	 */
	BarChartsReceiver receiver;

	/**
	 * Constructor
	 */
	public BarChartsDisplay()
	{
		super();
		receiver = new BarChartsReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the bar chart data and displays them as
	 * {@code toString()}s to the console.<br>
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		BarChartsDisplay display = new BarChartsDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}
