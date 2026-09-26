/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.linechart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import sysmlinjava.attributetypes.Point2D;

/**
 * Data (x, y values) to be displayed on a line chart display
 * 
 * @author ModelerOne
 *
 */
public class LineChartData implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = -7567118199087274202L;
	/**
	 * ID of the line chart on which the data is to be displayed
	 */
	public String chartID;
	/**
	 * Point data for the lines in the line chart. The outtermost list is a list of
	 * the lists of points to be added to each line in the line chart display. The
	 * first list of points is for the first line which uses the rightmost y-axis in
	 * the chart. The 2nd list of points is for the 2nd line which uses the 2nd from
	 * the right y-axis in the chart, etc. The list can be described as follows:
	 * <ul>
	 * <li>if y-axes count >= 0, 1st list element is list of points, if any, to be
	 * added to 1st line associated with 1st y-axis from right</li>
	 * <li>if y-axes count >= 1, 2nd list element is list of points, if any, to be
	 * added to 2nd line associated with 2nd y-axis from right</li>
	 * <li>if y-axes count >= 2, 3rd list element is list of points, if any, to be
	 * added to 3rd line associated with 3rd y-axis from right</li>
	 * <li>if y-axes count >= 3, 4th list element is list of points, if any, to be
	 * added to 4th line associated with 4th y-axis from right</li>
	 * </ul>
	 */
	public List<List<Point2D>> linesPoints;

	/**
	 * Constructor
	 * 
	 * @param chartID     ID of the line chart on which to display the data
	 * @param linesPoints points in each of the lines in the chart
	 */
	public LineChartData(String chartID, List<List<Point2D>> linesPoints)
	{
		super();
		this.chartID = chartID;
		this.linesPoints = linesPoints;
	}

	/**
	 * Constructor
	 * 
	 * @param chartDefinition chart definition associated with this chart data
	 */
	public LineChartData(LineChartDefinition chartDefinition)
	{
		super();
		this.chartID = chartDefinition.chartID;
		this.linesPoints = new ArrayList<List<Point2D>>();
		for (int i = 0; i < chartDefinition.yAxes.size(); i++)
			linesPoints.add(new ArrayList<>());
	}

	/**
	 * Validates the data for the line chart
	 * 
	 * @param chartDefinition definition of the line chart
	 * @return is valid for the chart
	 */
	public boolean isValidFor(LineChartDefinition chartDefinition)
	{
		boolean result = false;
		if (chartDefinition != null && chartID.equals(chartDefinition.chartID) && !linesPoints.isEmpty() && linesPoints.size() == chartDefinition.yAxes.size())
			result = true;
		return result;
	}

	/**
	 * String representation of the graph data
	 * 
	 * @return data as string, one list per line
	 */
	public String graphDataString()
	{
		StringJoiner listlist = new StringJoiner("");
		for (int lineIndex = 0; lineIndex < linesPoints.size(); lineIndex++)
		{
			StringJoiner list = new StringJoiner(",", String.format(" line%d: ", lineIndex), "");
			for (Point2D point : linesPoints.get(lineIndex))
			{
				if (!point.isEmpty)
					list.add(String.format("[%f %f]", point.xValue, point.yValue));
				else
					list.add("[]");
			}
			listlist.add(list.toString());
		}
		return listlist.toString();
	}

	/**
	 * Log-type string representation
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[LC] graphID=%s, data: %s", chartID, graphDataString());
	}

	@Override
	public String toString()
	{
		return String.format("LineChartData [graphID=%s, data=%s]", chartID, graphDataString());
	}
}