package sysmlinjava.analysis.barcharts;

import java.io.Serializable;
import java.util.ArrayList;
import sysmlinjava.analysis.common.Axis;
import sysmlinjava.analysis.common.CategoriesAxis;

/**
 * Definition of the bar chart display, i.e. its ID, category axis, y-value
 * axis, and names and colors of the stacked bar layers, if any.
 * 
 * @author ModelerOne
 *
 */
public class BarChartDefinition implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8945635974734237468L;

	/**
	 * ID of the chart (used to associate data received with the chart)
	 */
	public String barChartID;
	/**
	 * Category (bar) axis
	 */
	public CategoriesAxis catAxis;
	/**
	 * Y (bar value) axis
	 */
	public Axis yAxis;
	/**
	 * List of the names of the layers of the "stacked" bar chart. If there are
	 * multiple layers of values for each of the bars in the bar chart, i.e. the
	 * chart is a stacked bar chart, then these names will be used for the legend of
	 * layer names. The index of the name string in the list will be used as the
	 * index into the {@code BarChartData}'s {@code catYData} list to which the name
	 * will be applied, i.e. the first name in this list will be applied to the
	 * first/bottom-most layer of each of the bars; the second name in this list
	 * will be applied to the second from the bottom layer of each of the bars; and
	 * so on.
	 */
	public ArrayList<String> layerNames;

	/**
	 * Constructor
	 * 
	 * @param barChartID ID of the chart (used to associate data received with the
	 *                   chart)
	 * @param catAxis    category (bar) axis
	 * @param yAxis      y (bar value) axis
	 * @param layerNames List of the names of the layers of the "stacked" bar chart.
	 *                   If there are multiple layers of values for each of the bars
	 *                   in the bar chart, i.e. the chart is a stacked bar chart,
	 *                   then these names will be used for the legend of layer
	 *                   names. The index of the name string in the list will be
	 *                   used as the index into the {@code BarChartData}'s
	 *                   {@code catYData} list to which the name will be applied,
	 *                   i.e. the first name in this list will be applied to the
	 *                   first/bottom-most layer of each of the bars; the second
	 *                   name in this list will be applied to the second from the
	 *                   bottom layer of each of the bars; and so on.
	 * 
	 */
	public BarChartDefinition(String barChartID, CategoriesAxis catAxis, Axis yAxis, ArrayList<String> layerNames)
	{
		super();
		this.barChartID = barChartID;
		this.catAxis = catAxis;
		this.yAxis = yAxis;
		this.layerNames = layerNames;
	}

	/**
	 * Log-type string representation of the definition
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("[SC] [barChartID=");
		builder.append(barChartID);
		builder.append(", catAxis=");
		builder.append(catAxis);
		builder.append(", yAxis=");
		builder.append(yAxis);
		builder.append(", layerNames=");
		builder.append(layerNames);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("BarChartDefinition [barChartID=");
		builder.append(barChartID);
		builder.append(", catAxis=");
		builder.append(catAxis);
		builder.append(", yAxis=");
		builder.append(yAxis);
		builder.append(", layerNames=");
		builder.append(layerNames);
		builder.append("]");
		return builder.toString();
	}
}
