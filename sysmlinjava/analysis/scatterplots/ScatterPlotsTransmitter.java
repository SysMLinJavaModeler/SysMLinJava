package sysmlinjava.analysis.scatterplots;

import sysmlinjava.analysis.common.UDPTransmitter2;

/**
 * The {@code ScatterPlotsTransmitter} is a specialization of the
 * {@code UDPTransmitter2} to transmit graph data to a
 * {@code ScatterPlotsReceiver} for the display of graph data. This class simply
 * uses the {@code UDPTransmitter2}'s {@code transmit0(Object)} and
 * {@code transmit1(Object)} operations to transmit the
 * {@code ScatterPlotDefinition} and {@code ScatterPlotData} objects via the
 * {@code UDPTransmitter2}'s generic transmit operations.
 * 
 * @author ModelerOne
 * @see sysmlinjava.analysis.scatterplots.ScatterPlotData
 * @see sysmlinjava.analysis.scatterplots.ScatterPlotDefinition
 */
public class ScatterPlotsTransmitter extends UDPTransmitter2<ScatterPlotDefinition, ScatterPlotData>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port which to transmit plot data to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public ScatterPlotsTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "ScatterPlotsTransmitter");
	}

	/**
	 * Transmits a {@code ScatterPlotDefintion} to a {@code ScatterPlotsReceiver} of
	 * the {@code ScatterPlotsDisplay}
	 * 
	 * @param plotDefinition definition/specification of the scatter plots to be
	 *                       displayed
	 */
	public void transmitScatterPlotDefinition(ScatterPlotDefinition plotDefinition)
	{
		super.transmit0(plotDefinition);
	}

	/**
	 * Transmits a {@code ScatterPlotData} to a {@code ScatterPlotsReceiver} of the
	 * {@code ScatterPlotsDisplay}
	 * 
	 * @param plotData plot data to be displayed in the scatter plot
	 */
	public void transmitScatterPlotData(ScatterPlotData plotData)
	{
		super.transmit1(plotData);
	}
}
