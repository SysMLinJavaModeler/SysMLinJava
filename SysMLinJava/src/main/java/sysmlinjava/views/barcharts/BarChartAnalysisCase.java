










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
package sysmlinjava.views.barcharts;

import java.util.ArrayList;
import java.util.Optional;

import sysmlinjava.analysis.ParametricAnalysisCase;
import sysmlinjava.attributetypes.KeyValueMap;
import sysmlinjava.attributetypes.ListOrdered;
import sysmlinjava.attributetypes.RReal;
import sysmlinjava.connectors.SysMLBindingConnector;
import sysmlinjava.javaannotations.analysis.AnalysisResult;

/**
 * {@code BarChartAnalysisCase} is a SysMLinJava model of a parametric analysis case
 * that produces a bar chart representation of the current value of its
 * constraint parameters, i.e. the constraint produces the bar chart from the
 * parameters.
 * 
 * @author ModelerOne
 *
 */
public abstract class BarChartAnalysisCase extends ParametricAnalysisCase
{
	/**
	 * The analysis result, i.e. the collection of the values for each bar chart category. These
	 * values are initially created/initialized by the {@code createParameters}
	 * operation and calculated by the {@code perform} and transmitted to the bar
	 * chart display (for heights of the bars). List is of the maps of category
	 * values for each layer of the stacked bar - only one map in list if bars
	 * aren't "stacked". Each map contains keys that are the category (bar) names on
	 * the horizontal axis of the bar chart and the map values are the height of the
	 * bars on the vertical axis of the bar chart.
	 * <p>
	 * The {@code categoryParams} must be created/initialized/put into the map in an
	 * override of the {@code createConstraintParameters()} operation. Each map in
	 * the list must be created/initialized before adding category-value pairs to
	 * the map. The map must eventually be added to the {@code categoryParams} list.
	 * Optionally, the map could be created/initialized and added to the list in the
	 * {@code preCreate()} operation.
	 * <p>
	 * The category parameter values need only be initialized to an arbitrary
	 * initial value as the parametric analysis case will change the values to reflect the
	 * current "bound" values. An example operation is as follows.
	 * 
	 * <pre>
	 * {@code
	 * protected void createParameters()
	 * {
	 * 	Map<String, RReal> map = new HashMap<>();
	 * 	map.put(categoryA.toString, new WeightPounds(0));
	 * 	map.put(categoryB.toString, new WeightPounds(0));
	 * 	map.put(categoryC.toString, new WeightPounds(0));
	 * 	categoryParams.get(0).add(map);
	 * }
	 * }</pre>
	 * 
	 * In the case of a "stacked" bar chart where each category maps to multiple
	 * values, the example operation would be as follows:
	 * 
	 * <pre>
	 * {@code
	 * protected void createtParameters()
	 * {
	 * 	for (int i = 0; i < numValuesInStack; i++)
	 * 	{
	 * 		Map<String, RReal> map = new HashMap<>();
	 * 		map.put(categoryA.toString, new WeightPounds(0));
	 * 		map.put(categoryB.toString, new WeightPounds(0));
	 * 		map.put(categoryC.toString, new WeightPounds(0));
	 * 		categoryParams.get(i).add(map);
	 * 	}
	 * }
	 * }</pre>
	 */
	@AnalysisResult
	public ListOrdered<KeyValueMap<String, RReal>> categoryParams;

	/**
	 * Definition of the bar chart to be built/constrained and displayed
	 */
	public BarChartDefinition chartDefinition;

	/**
	 * Transmitter of the chart data to the chart display
	 */
	private BarChartsTransmitter chartTransmitter;

	/**
	 * Constructor for a chart definition and UDP port of the display.
	 * 
	 * @param chartDefinition definition of the chart to be displayed in terms of
	 *                        its axes, etc.
	 * @param udpPort         UDP port to which the chart definition and chart data
	 *                        are to be transmitted
	 * @param logToConsole    whether to send all {@code transmit()} logs to
	 *                        console. Note: {@code transmit()} operation can log
	 *                        every object's {@code toString()} string to the
	 *                        console. The more frequently log messages are sent to
	 *                        console, the greater the CPU resources are needed.
	 *                        This can cause noticable slowing of the console
	 *                        display and related applications.
	 */
	public BarChartAnalysisCase(BarChartDefinition chartDefinition, int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "BarCharts", 0L);
		this.chartDefinition = chartDefinition;
		this.chartTransmitter = new BarChartsTransmitter(udpPort, logToConsole);
		this.chartTransmitter.transmitBarChartDefinition(chartDefinition);
	}

	/**
	 * Retrieves the specified parameter from the associated parameter port and sets
	 * it in the {@code categoryParams} map. Note this is an override of the default
	 * operation in the base class. It uses a less capable method insofar as the bar
	 * chart requires a simpler constraint to compute.
	 */
	@Override
	protected void onParameterChange(String paramID)
	{
		if (!paramID.isEmpty())
		{
			SysMLBindingConnector paramConnector = paramConnectors.get(paramID);
			if (paramConnector != null)
			{
				RReal boundParam = ((RReal)paramConnector.getAttribute());
				if (boundParam != null)
				{
					RReal param = ((RReal)params.get(paramID));
					if (param != null)
						param.value = boundParam.value;
					else
						logger.severe("constraintParam for paramID " + paramID + " not found");
				}
				else
					logger.severe("boundParam for paramID " + paramID + " not found");
			}
			currentParamID = Optional.of(paramID);
		}
		else
		{
			logger.severe("paramID is empty/blank");
			currentParamID = Optional.empty();
		}
	}

	/**
	 * Overridable method to perform the analysis on the current values of the set
	 * of analysis parameters. The method should be overridden to translate analysis
	 * parameter values into the {@code categoryParams} and then either invoke the
	 * {@code transmitChartData()} operation to calculate and transmit the bar chart
	 * data for display, or simply invoke this method (via {@code super.perform()}) to
	 * invoke the {@code transmitChartData()}.
	 */
	@Override
	public void perform()
	{
		transmitChartData();
	}

	/**
	 * Constructs and transmits the chart data for the bar chart display
	 */
	protected void transmitChartData()
	{
		ArrayList<ArrayList<CatYData>> catYDataListsList = new ArrayList<>();
		categoryParams.forEach(map ->
		{
			ArrayList<CatYData> catYDataList = new ArrayList<>();
			map.map.forEach((category, value) -> catYDataList.add(new CatYData(category, value.value)));
			catYDataListsList.add(catYDataList);
		});
		BarChartData chartData = new BarChartData(chartDefinition.barChartID, catYDataListsList);
		chartTransmitter.transmitBarChartData(chartData);
	}

	@Override
	public void stop()
	{
		chartTransmitter.stop();
		super.stop();
	}
}
