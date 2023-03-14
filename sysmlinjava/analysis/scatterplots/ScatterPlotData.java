package sysmlinjava.analysis.scatterplots;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringJoiner;
import sysmlinjava.analysis.common.XY;

/**
 * Data (x-y values) to be displayed on a scatter plot display
 * 
 * @author ModelerOne
 *
 */
public class ScatterPlotData implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -7567118199087274202L;

	/**
	 * ID of the plot
	 */
	public String scatterPlotID;
	/**
	 * List of the x-y data points to be "plotted"
	 */
	public ArrayList<XY> xyData;

	/**
	 * Constructor
	 * 
	 * @param scatterPlotID ID of the plot
	 * @param xyData        list of the x-y data points to be "plotted"
	 */
	public ScatterPlotData(String scatterPlotID, ArrayList<XY> xyData)
	{
		super();
		this.scatterPlotID = scatterPlotID;
		this.xyData = xyData;
	}

	/**
	 * String representation of the x-y point values
	 * 
	 * @return string of x-y values
	 */
	public String xyDataString()
	{
		StringJoiner joiner = new StringJoiner(" ");
		xyData.forEach(value ->
		{
			joiner.add(String.format("[%3.3f, %3.3f]", value.xValue, value.yValue));
		});
		return joiner.toString();
	}

	/**
	 * Log-type representation of the data
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[SC] scatterPlotID=%s, xyData=%s]", scatterPlotID, xyDataString());
	}

	@Override
	public String toString()
	{
		return String.format("ScatterPlotData [scatterPlotID=%s, xyData=%s]", scatterPlotID, xyDataString());
	}
}