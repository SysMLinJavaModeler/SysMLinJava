package sysmlinjava.analysis.barcharts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Data (category values) to be displayed on a bar chart display
 * 
 * @author ModelerOne
 *
 */
public class BarChartData implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8005702743864254971L;

	/**
	 * ID of the bar chart on which the data is to be displayed
	 */
	public String barChartID;
	/**
	 * Data for the bar chart, i.e. a list of lists of category (bar) values, where
	 * the inner list is list of the values represented at one layer in the "stack"
	 * of values in a stacked bar. The outer list is a list of these lists of values
	 * for one layer. For a single value bar chart, i.e. no "stacked" bars, the
	 * outter list will contain only a single list of {@code CatYData} objects. For
	 * a two value stack bar chart, the outter list will contain two lists of
	 * {@code CayYData} objects, etc.
	 */
	public ArrayList<ArrayList<CatYData>> catYData;
	
	/**
	 * Constructor
	 * 
	 * @param barChartID ID of the chart to display the data
	 * @param catYData   Data for the bar chart, i.e. a list of lists of category
	 *                   (bar) values, where the inner list is list of the values
	 *                   represented at one layer in the "stack" of values in a
	 *                   stacked bar. The outer list is a list of these lists of
	 *                   values for one layer. For a single value bar chart, i.e. no
	 *                   "stacked" bars, the outter list will contain only a single
	 *                   list of {@code CatYData} objects. For a two value stack bar
	 *                   chart, the outter list will contain two lists of
	 *                   {@code CayYData} objects, etc.
	 */
	public BarChartData(String barChartID, ArrayList<ArrayList<CatYData>> catYData)
	{
		super();
		this.barChartID = barChartID;
		this.catYData = catYData;
	}

	/**
	 * Validates the data for the bar chart
	 * 
	 * @param chart definition of the bar chart
	 * @return is valid for the chart
	 */
	public boolean isValidFor(BarChartDefinition chart)
	{
		boolean result = true;
		if (chart != null)
		{
			if (!barChartID.equals(chart.barChartID))
				result = false;
			else if(catYData.size() != chart.layerNames.size())
				result = false;
			else
				for (ArrayList<CatYData> stackLayerData : catYData)
					if (stackLayerData.size() != chart.catAxis.categories.size())
						result = false;
		}
		else
			result = false;
		return result;
	}

	/**
	 * String representation of the data
	 * 
	 * @return string representation of the data
	 */
	public String catYDataString()
	{
		StringJoiner listListJoiner = new StringJoiner("\n");
		for (ArrayList<CatYData> stackLayerData : catYData)
		{
			StringJoiner listJoiner = new StringJoiner(",", "[", "]");
			for (CatYData data : stackLayerData)
				listJoiner.add(String.format("[%s, %s]", data.category, data.yValue));
			listListJoiner.add(listJoiner.toString());
		}
		return listListJoiner.toString();
	}

	/**
	 * String for log-type display
	 * 
	 * @return log type display string
	 */
	public String toLogString()
	{
		return String.format("[BC] [barChartID=%s, catYyData=%n%s]", barChartID, catYDataString());
	}

	@Override
	public String toString()
	{
		return String.format("BarChartData [barChartID=%s, catYyData=%n%s]", barChartID, catYDataString());
	}
}