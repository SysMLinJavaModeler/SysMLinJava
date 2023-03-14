package sysmlinjava.analysis.scatterplots;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive plot data for disply
 * via the {@code ScatterPlotsDisplay}. This class simply implements the
 * {@code UDPReceiver}'s {@code receive(Object)} operation for the plot data
 * objects by displaying the objects as {@code toString()}s as log entries.
 * 
 * @author ModelerOne
 * @see ScatterPlotData
 * @see ScatterPlotDefinition
 */
public class ScatterPlotsReceiver extends UDPReceiver
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port on which to receive plot data
	 */
	public ScatterPlotsReceiver(int udpPort)
	{
		super(udpPort, "ScatterPlotsReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof ScatterPlotData)
			logger.info(((ScatterPlotData)data).toString());
		else if (data instanceof ScatterPlotDefinition)
			logger.info(((ScatterPlotDefinition)data).toString());
		return false;
	}
}
