package sysmlinjava.analysis.linecharts;

import java.util.HashMap;
import java.util.Optional;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;
import sysmlinjava.ports.SysMLConstraintParameterPort;
import sysmlinjava.ports.SysMLConstraintParameterPortFunction;
import sysmlinjava.valuetypes.Point2D;

/**
 * {@code LineChartConstraintBlock} is a SysMLinJava model of a constraint block
 * that produces a line chart representation of up to 4 constraint parameters.
 * The lines on the chart each have their own y axis with each set of y values
 * sharing the same x axis. Specializations of the
 * {@code LineChartConstraintBlock} need only define the constraint parameter
 * port functions that implement the "binding" connectors of the constraint
 * parameters to be displayed in the line chart, i.e. they need only to
 * override/implement the {@code createConstraintParameterPortFunction()}
 * method. All other activities needed to display the constraint paramter values
 * in the line chart are provided by this context block.
 * 
 * @author ModelerOne
 *
 */
public abstract class LineChartsConstraintBlock extends SysMLConstraintBlock
{
	/**
	 * Enumeration of the lines in the line chart display. The enum is of the 4
	 * lines available in the display, but only the enums for the corresponding
	 * number of lines specfied in the ({@code chartDefinition} will be used for the
	 * constraint parameters, ports, and port functions. Specializations of this
	 * constraint block should bind constraint parameters accordingly, i.e. bind
	 * constraint parameter {@code line0} to the parameter that is to be displayed
	 * as line 0 (right-most y-axis), constraint parameter {@code line1} to the
	 * parameter that is to be displayed as line 1 (2nd y-axis), etc. as required by
	 * the number of constraint parameters to be displayed as lines in the line
	 * chart display.
	 * 
	 */
	public enum LineEnum
	{
		/**
		 * First line on the chart
		 */
		line0,
		/**
		 * Second line on the chart
		 */
		line1,
		/**
		 * Third line on the chart
		 */
		line2,
		/**
		 * Fourth line on the chart
		 */
		line3
	}

	/**
	 * Definition of the bar chart to be built/constrained and displayed. The chart
	 * definition is provided via the constructor.
	 */
	public LineChartDefinition chartDefinition;

	/**
	 * Data for update of the line chart display. Chart data is calculated by the
	 * constraint that is defined by specializations of this constraint block.
	 */
	public LineChartData chartData;

	/**
	 * Transmitter of the chart data to the chart display
	 */
	private LineChartsTransmitter chartTransmitter;

	/**
	 * Constructor
	 * 
	 * @param chartDefinition definition of the line chart in terms of its axes,
	 *                        lines, title, etc.
	 * @param udpPort         UDP port to which the line chart data is to be
	 *                        transmitted
	 * @param logToConsole    whether to send all {@code transmit()} logs to
	 *                        console. Note: {@code transmit()} operation can log
	 *                        every object's {@code toString()} string to the
	 *                        console. The more frequently log messages are sent to
	 *                        console, the greater the CPU resources are needed.
	 *                        This can cause noticable slowing of the console
	 *                        display and related applications.
	 */
	public LineChartsConstraintBlock(LineChartDefinition chartDefinition, int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "LineCharts");
		this.chartDefinition = chartDefinition;
		this.chartData = new LineChartData(chartDefinition);
		this.chartTransmitter = new LineChartsTransmitter(udpPort, logToConsole);
		this.chartTransmitter.transmitGraph(chartDefinition);
	}

	/**
	 * Operation that performs the constraint specified by the {@code constraint}
	 * field variable. After performing the constraint, the operation proceeds to
	 * construct the {@code LineChartData} and transmits it to the line charts
	 * display. This operation can be overidden to perform additional/different
	 * constraint(s), but the overiding operation should lastly invoke the
	 * {@code transmitChartData()} operation to transmit the line charts data for
	 * display.
	 */
	@Override
	protected void performConstraints()
	{
		constraint.apply();
		transmitChartData();
	}

	/**
	 * Transmits the chart data for the line chart display
	 */
	protected void transmitChartData()
	{
		chartTransmitter.transmit1(chartData);
	}

	/**
	 * Overridable operation that simply instantiates the map of parameters.
	 * Overrides should invoke this operation and then "put()" the needed parameters
	 * into the map for access by the {@code performConstraints()} operation.
	 */
	@Override
	protected void createConstraintParameters()
	{
		constraintParams = new HashMap<>();
		for (LineEnum lineEnum : LineEnum.values())
			constraintParams.put(lineEnum.toString(), new Point2D());
	}

	@Override
	protected void createConstraints()
	{
		constraint = () ->
		{
			if (currentParamID.isPresent())
			{
				Point2D pointParam = (Point2D)constraintParams.get(currentParamID.get());
				int lineIndex = LineEnum.valueOf(currentParamID.get()).ordinal();
				for (int i = 0; i < chartData.linesPoints.size(); i++)
				{
					chartData.linesPoints.get(i).clear();
					if (i == lineIndex)
						chartData.linesPoints.get(lineIndex).add(pointParam);
				}
			}
		};
	}

	/**
	 * Overridable operation that simply instantiates the map of parameter port
	 * functions. Overrides should invoke this operation and then "put()" the needed
	 * function instances (lambda expressions) into the map for access by the
	 * overridden {@code createConstraintParameterPorts()} operation, i.e. as an
	 * argument to the {@code SysMLConstraintParameterPort} constructor.
	 */
	@Override
	protected void createConstraintParameterPortFunctions()
	{
		paramPortFunctions = new HashMap<>();
	}

	/**
	 * Overridable operation that simply instantiates the map of parameter ports.
	 * Overrides should invoke this operation and then "put()" the needed port
	 * instances into the map for access by the {@code retrieveParameters()}
	 * operation.
	 */
	@Override
	protected void createConstraintParameterPorts()
	{
		paramPorts = new HashMap<>();
		for (LineEnum lineEnum : LineEnum.values())
		{
			SysMLConstraintParameterPortFunction portFunction = paramPortFunctions.get(lineEnum.toString());
			if (portFunction != null)
			{
				SysMLConstraintParameterPort paramPort = new SysMLConstraintParameterPort(this, portFunction, lineEnum.toString());
				paramPorts.put(lineEnum.toString(), paramPort);
			}
		}
	}

	@Override
	public void stop()
	{
		chartTransmitter.stop();
		super.stop();
	}
}
