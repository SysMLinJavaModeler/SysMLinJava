package sysmlinjava.analysis.barcharts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import sysmlinjava.annotations.parametrics.ConstraintParameter;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;
import sysmlinjava.ports.SysMLConstraintParameterPort;
import sysmlinjava.valuetypes.RReal;

/**
 * {@code BarChartConstraintBlock} is a SysMLinJava model of a constraint block
 * that produces a bar chart representation of the current value of its
 * constraint parameters, i.e. the constraint produces the bar chart from the
 * parameters.
 * 
 * @author ModelerOne
 *
 */
public abstract class BarChartConstraintBlock extends SysMLConstraintBlock
{
	/**
	 * Collection of the current parameter values for each bar chart category. These
	 * category parameters are created by the {@code createConstraintParameters}
	 * function and calculated by the {@code constraint} and transmitted to the bar
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
	 * initial value as the constraint block will change the values to reflect the
	 * current "bound" values. An example operation is as follows.
	 * 
	 * <pre>
	 * {@code
	 * protected void createConstraintParameters()
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
	 * protected void createConstraintParameters()
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
	@ConstraintParameter
	public List<Map<String, RReal>> categoryParams;

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
	public BarChartConstraintBlock(BarChartDefinition chartDefinition, int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "BarCharts");
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
			SysMLConstraintParameterPort paramPort = paramPorts.get(paramID);
			if (paramPort != null)
			{
				RReal boundParam = ((RReal)paramPort.getValue());
				if (boundParam != null)
				{
					RReal constraintParam = ((RReal)constraintParams.get(paramID));
					if (constraintParam != null)
						constraintParam.value = boundParam.value;
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
	 * Operation that performs the constraint specified by the {@code constraint}
	 * field variable. After performing the constraint, the operation proceeds to
	 * construct the {@code BarChartData} and transmits it to the bar chart display.
	 * This operation can be overidden to perform additional/different
	 * constraint(s), but any overiding operation should lastly invoke the
	 * {@code transmitChartData()} operation to calculate and transmit the bar chart
	 * data for display.
	 */
	@Override
	protected void performConstraints()
	{
		constraint.apply();
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
			map.forEach((category, value) -> catYDataList.add(new CatYData(category, value.value)));
			catYDataListsList.add(catYDataList);
		});
		BarChartData chartData = new BarChartData(chartDefinition.barChartID, catYDataListsList);
		chartTransmitter.transmitBarChartData(chartData);
	}

	@Override
	protected void preCreate()
	{
		super.preCreate();
		categoryParams = new ArrayList<>();
	}

	@Override
	public void stop()
	{
		chartTransmitter.stop();
		super.stop();
	}
}
