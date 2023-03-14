package sysmlinjava.analysis.linecharts;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive line chart data for disply
 * via the {@code LineChartsDisplay}. This class simply implements the
 * {@code UDPReceiver}'s {@code receive(Object)} operation for the line chart data
 * objects by displaying the objects as {@code toString()}s as log entries.
 * 
 * @author ModelerOne
 * @see LineChartData
 * @see LineChartsDisplay
 */
public class LineChartsReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive line chart data
	 */
	public LineChartsReceiver(int udpPort)
	{
		super(udpPort, "LineChartsReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof LineChartData)
			logger.info(((LineChartData)data).toString());
		else if (data instanceof LineChartDefinition)
			logger.info(((LineChartDefinition)data).toString());
		return false;
	}
}
