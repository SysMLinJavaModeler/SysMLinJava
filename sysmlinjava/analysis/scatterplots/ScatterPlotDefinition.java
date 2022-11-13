package sysmlinjava.analysis.scatterplots;

import java.io.Serializable;
import sysmlinjava.analysis.common.Axis;

/**
 * Definition of the scatter plot display, i.e. its x and y axes, and it ID
 * 
 * @author ModelerOne
 *
 */
public class ScatterPlotDefinition implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8945635974734237468L;

	/**
	 * ID of the plot
	 */
	public String scatterPlotID;
	/**
	 * Axis for the x values
	 */
	public Axis xAxis;
	/**
	 * Axis for the y values
	 */
	public Axis yAxis;

	/**
	 * Constructor
	 * 
	 * @param plotID ID of the plot
	 * @param xAxis  axis for the x values
	 * @param yAxis  axis for the y values
	 */
	public ScatterPlotDefinition(String plotID, Axis xAxis, Axis yAxis)
	{
		super();
		this.scatterPlotID = plotID;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	/**
	 * Log-type representation of the definition
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[SC] [scatterPlotID=%s, xAxis=%s, yAxis=%s]", scatterPlotID, xAxis.toString(), yAxis.toString());
	}

	@Override
	public String toString()
	{
		return String.format("ScatterPlotDefinition [scatterPlotID=%s, xAxis=%s, yAxis=%s]", scatterPlotID, xAxis, yAxis);
	}
}
