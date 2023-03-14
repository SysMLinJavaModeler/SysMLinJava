package sysmlinjava.analysis.barcharts;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive bar chart data for display
 * via the {@code BarChartsDisplay}. This class simply implements the
 * {@code UDPReceiver}'s {@code receive(Object)} operation for the plot data
 * objects by displaying the objects as {@code toString()}s as log entries.
 * 
 * @author ModelerOne
 * @see BarChartData
 * @see BarChartDefinition
 */
public class BarChartsReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive chart data
	 */
	public BarChartsReceiver(int udpPort)
	{
		super(udpPort, "BarChartsReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof BarChartData)
			logger.info(((BarChartData)data).toString());
		else if (data instanceof BarChartDefinition)
			logger.info(((BarChartDefinition)data).toString());
		return false;
	}
}
