package sysmlinjava.analysis.scatterplots;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sysmlinjava.analysis.common.XY;
import sysmlinjava.annotations.parametrics.ConstraintParameter;
import sysmlinjava.annotations.parametrics.ConstraintParameterPort;
import sysmlinjava.annotations.parametrics.ConstraintParameterPortFunction;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;
import sysmlinjava.ports.SysMLConstraintParameterPort;
import sysmlinjava.ports.SysMLConstraintParameterPortFunction;
import sysmlinjava.valuetypes.Point2D;

/**
 * {@code ScatterPlotsConstraintBlock} is a SysMLinJava model of a constraint
 * block that produces a scatter plot chart representation of the a sequence of
 * X-Y values which are the sequence of values of the constraint parameters over
 * time, i.e. the constraint produces the scatter plot from the constraint
 * parameter values.
 * 
 * @author ModelerOne
 *
 */
public class ScatterPlotConstraintBlock extends SysMLConstraintBlock
{
	/**
	 * Port via which the plot value is bound to the constraint parameter
	 */
	@ConstraintParameterPort
	public SysMLConstraintParameterPort plotValuePort;

	/**
	 * Function used by the port to bind the plot value to the constraint parameter
	 */
	@ConstraintParameterPortFunction
	public SysMLConstraintParameterPortFunction plotValuePortFunction;

	/**
	 * Constraint parameter for the point value to be plotted
	 */
	@ConstraintParameter
	public Point2D point;

	/**
	 * Data for the plot display
	 */
	public ScatterPlotData xyPlot;
	/**
	 * Definition of the plot display
	 */
	public ScatterPlotDefinition plotDefinition;
	/**
	 * Transmitter of the plot definition and data to the display
	 */
	private ScatterPlotsTransmitter plotsTransmitter;

	/**
	 * Constructor - initialization
	 * 
	 * @param plotDefinition definition of the plot display
	 * @param udpPort        UDP port to which the plot definition and data are to
	 *                       be sent
	 * @param logToConsole   whether to send all {@code transmit()} logs to console.
	 *                       Note: {@code transmit()} operation can log every
	 *                       object's {@code toString()} string to the console. The
	 *                       more frequently log messages are sent to console, the
	 *                       greater the CPU resources are needed. This can cause
	 *                       noticable slowing of the console display and related
	 *                       applications.
	 */
	public ScatterPlotConstraintBlock(ScatterPlotDefinition plotDefinition, int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "ScatterPlot");
		this.plotDefinition = plotDefinition;
		this.plotsTransmitter = new ScatterPlotsTransmitter(udpPort, logToConsole);
		this.plotsTransmitter.transmitScatterPlotDefinition(plotDefinition);
	}

	@Override
	protected void onParameterChange(String paramID)
	{
		Point2D temp = (Point2D)plotValuePort.getValue();
		point.xValue = temp.xValue;
		point.yValue = temp.yValue;
	}

	@Override
	protected void performConstraints()
	{
		constraint.apply();
		plotsTransmitter.transmitScatterPlotData(xyPlot);
	}

	@Override
	protected void createConstraintParameters()
	{
		point = new Point2D(0, 0);
	}

	@Override
	protected void createConstraints()
	{
		constraint = () ->
		{
			ArrayList<XY> xyData = new ArrayList<>();
			xyData.addAll(List.of(new XY(point.xValue, point.yValue)));
			xyPlot = new ScatterPlotData(plotDefinition.scatterPlotID, xyData);
		};
	}

	@Override
	protected void createConstraintParameterPortFunctions()
	{
		// Override to define plotValuePortFunction
		plotValuePortFunction = (parameterPort, parameterContextBlock) ->
		{
		};
	}

	@Override
	protected void createConstraintParameterPorts()
	{
		plotValuePort = new SysMLConstraintParameterPort(this, plotValuePortFunction, "pointParam");
	}
}
