package sysmlinjava.analysis.linecharts;

import sysmlinjava.analysis.common.UDPTransmitter2;

/**
 * The {@code GraphDataTransmitter} is a specialization of the
 * {@code UDPTransmitter2} to transmit graph data to a {@code GraphDataReceiver}
 * for the display of graph data. This class simply implements the
 * {@code UDPTransmitter2}'s {@code transmit(Graph)} and
 * {@code transmit(GraphData)} operations for the graph data objects by
 * transmitting objects via the {@code UDPTransmitter2}'s generic transmit
 * operations.
 * 
 * @author ModelerOne
 * @see sysmlinjava.analysis.linecharts.LineChartData
 * @see sysmlinjava.analysis.linecharts.LineChartDefinition
 */
public class LineChartsTransmitter extends UDPTransmitter2<LineChartDefinition, LineChartData>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port which to transmit graph data to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public LineChartsTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole);
	}

	/**
	 * Transmits a {@code Graph} specification to a {@code GraphDataReceiver} of the
	 * {@code GraphDisplay}
	 * 
	 * @param graph the specification of the graph to be displayed
	 */
	public void transmitGraph(LineChartDefinition graph)
	{
		super.transmit0(graph);
	}

	/**
	 * Transmits a {@code Graph} specification to a {@code GraphDataReceiver} of the
	 * {@code GraphDisplay}
	 * 
	 * @param graphData the data to be added to the displayed graph
	 */
	public void transmitGraphData(LineChartData graphData)
	{
		super.transmit1(graphData);
	}
}
