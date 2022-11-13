package sysmlinjava.analysis.linecharts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringJoiner;
import sysmlinjava.analysis.common.Axis;
import sysmlinjava.analysis.common.AxisFixedRange;

/**
 * Definition of the line chart display, i.e. its x axis and y axes, and it ID
 * 
 * @author ModelerOne
 *
 */
public class LineChartDefinition implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8945635974734237468L;

	/**
	 * ID of the chart (used to associate data received with the chart)
	 */
	public String chartID;
	/**
	 * List of y axes
	 */
	public ArrayList<AxisFixedRange> yAxes;
	/**
	 * X axis
	 */
	public Axis xAxis;

	/**
	 * Constructor
	 * 
	 * @param chartID ID of the chart
	 * @param yAxes   list of the y axes, each of which has a fixed range
	 * @param xAxis   the x axis
	 */
	public LineChartDefinition(String chartID, ArrayList<AxisFixedRange> yAxes, Axis xAxis)
	{
		super();
		this.chartID = chartID;
		this.yAxes = yAxes;
		this.xAxis = xAxis;
	}
	

	/**
	 * Log-type representation of the chart definition
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[LC][graphID=%s%nxAxis=%s%n%s]", chartID, xAxis.toString(), yAxesToLogString());
	}

	@Override
	public String toString()
	{
		return String.format("Graph [graphID=%s, xAxis=%s, %s]", chartID, xAxis.toString(), yAxesToString());
	}

	/**
	 * Log-type representation of the y-axes definitions
	 * 
	 * @return log-type string
	 */
	private String yAxesToLogString()
	{
		StringJoiner joiner = new StringJoiner("\n");
		yAxes.forEach(axis -> joiner.add("yAxis=" + axis.toString()));
		return joiner.toString();
	}

	/**
	 * toString() representation of the y-axes definitions
	 * 
	 * @return string representation of y-axes defs
	 */
	private String yAxesToString()
	{
		StringJoiner joiner = new StringJoiner(", ", "yAxes=", "");
		yAxes.forEach(axis -> joiner.add(axis.toString()));
		return joiner.toString();
	}
}
