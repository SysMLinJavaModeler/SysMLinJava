package sysmlinjava.analysis.linecharts;

/**
 * The {@code LineChartsDisplay} is a simple console application that receives
 * {@code LineChartData} via a UDP socket and displays the data as text on the
 * console.
 * <p>
 * <b>Note:</b> This simple text-based display is part of the basic SysMLinJava
 * tool set. More capable graphics-based displays are available commercially.
 * See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 *
 */
public class LineChartsDisplay
{
	/**
	 * UDP port via which the receiver is to receive line chart data
	 */
	public static final int udpPort = 8893;
	/**
	 * Receiver of the line chart data
	 */
	LineChartsReceiver receiver;

	/**
	 * Constructor - constructs the receiver
	 */
	public LineChartsDisplay()
	{
		super();
		receiver = new LineChartsReceiver(udpPort);
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
		LineChartsDisplay display = new LineChartsDisplay();
		display.receiver.run();
		Runtime.getRuntime().exit(0);
	}
}
