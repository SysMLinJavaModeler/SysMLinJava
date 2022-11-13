package sysmlinjava.analysis.animatedareadisplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import sysmlinjava.annotations.parametrics.ConstraintParameter;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;
import sysmlinjava.ports.SysMLConstraintParameterPort;
import sysmlinjava.valuetypes.Point2D;
import sysmlinjava.valuetypes.Polyline2D;

/**
 * {@code AnimatedAreaDisplayConstraintBlock} is a SysMLinJava model of a SysML
 * constraint block that produces an area display of objects whose states and
 * positions are represented by the current values of its constraint parameters,
 * i.e. the constraint block produces the area display from constraint
 * parameters.
 * <p>
 * {@code AnimatedAreaDisplayConstraintBlock} extends the basic
 * {@code SysMLConstraintBlock}. As such, the constraint block defines a set of
 * constraint parameters, the constraint parameter ports that are "bound" to the
 * block values that correspond to the constraint parameters, and the constraint
 * that "calculates" the area display from the constraint parameters.
 * <p>
 * As an abstract class, the {@code AnimatedAreaDisplayConstraintBlock} must be
 * extended/specialized for the particular area display that is to be generated.
 * Specializations may use the constraint parameters defined in this base class,
 * i.e. the Point2D-type and Points2D-type parameters for the most commonly used
 * parameters for an area display, i.e. object positions, but may also define
 * other constraint parameters in the specialized class as needed to further
 * specialize the area display. In any case, the specialized constraint block
 * must also define the constraint parameter ports that are "bound" to the
 * constraint parameters as well as the constraint that "calculates" the area
 * display from the constraint parameters.
 * <p>
 * As described in the {@code SysMLConstraintBlock} comments, the constraint
 * parameter ports must be connected (bound) to the block values that correspond
 * to the constraint parameters. These connectors must be created in the
 * {@code createConstraintParameterConnectors()} method in a block that contains
 * the {@code AnimatedAreaDisplayConstraintBlock} specialization and the blocks
 * that contain the bound values.
 * 
 * @author ModelerOne
 *
 */
public abstract class AnimatedAreaDisplayConstraintBlock extends SysMLConstraintBlock
{
	/**
	 * Map collection of Point2D-type constraint parameters that are obtained via
	 * the parameter ports and used as input to the {@code constraint} function. Map
	 * keys are the paramIDs of the parameters and the map values are the
	 * Point2D-type parameters. The params must be created/initialized in the map in
	 * an override of the {@code createConstraintParameters()} operation.
	 */
	@ConstraintParameter
	public Map<String, Point2D> pointParams;

	/**
	 * Map collection of Points2D-type constraint parameters that are obtained via
	 * the parameter ports and used as input to the {@code constraint} function. Map
	 * keys are the paramIDs of the parameters and the map values are the
	 * Points2D-type parameters. The params must be created/initialized in the map
	 * in an override of the {@code createConstraintParameters()} operation.
	 */
	@ConstraintParameter
	public Map<String, Polyline2D> lineParams;

	/**
	 * Definition of the area display to be calculated/constrained and displayed
	 */
	public AnimatedAreaDisplayDefinition displayDefinition;

	/**
	 * Definition of the area display data be calculated/constrained and displayed
	 */
	public AnimatedAreaDisplayData displayData;

	/**
	 * Transmitter of the area display's definition and data to the area display
	 */
	private AnimatedAreaDisplayTransmitter displayTransmitter;

	/**
	 * Constructor
	 * 
	 * @param udpPort number of UDP port on which to receive the display definition
	 *                and data
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every
	 *                     object's {@code toString()} string to the console. The
	 *                     more frequently log messages are sent to console, the
	 *                     greater the CPU resources are needed. This can cause
	 *                     noticable slowing of the console display and related
	 *                     applications.
	 */
	public AnimatedAreaDisplayConstraintBlock(int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "AnimatedAreaDisplay");
		createDisplayDefinition();
		createDisplayData();
		displayTransmitter = new AnimatedAreaDisplayTransmitter(udpPort, logToConsole);
		displayTransmitter.transmitDefinition(displayDefinition);
	}

	/**
	 * Retrieves the uniquely identified parameter from the associated parameter
	 * port and sets it in the corresponding constraint parameters map.
	 */
	@Override
	protected void onParameterChange(String paramID)
	{
		if (!paramID.isEmpty())
		{
			SysMLConstraintParameterPort paramPort = paramPorts.get(paramID);
			if (paramPort != null)
			{
				currentParam = paramPort.getValue();
				if (currentParam instanceof Point2D)
				{
					Point2D point2D = pointParams.get(paramID);
					point2D.xValue = ((Point2D)currentParam).xValue;
					point2D.yValue = ((Point2D)currentParam).yValue;
				}
				else if (currentParam instanceof Polyline2D)
				{
					Polyline2D points2D = lineParams.get(paramID);
					points2D.value = ((Polyline2D)currentParam).value;
				}
				else
					logger.severe("unrecognized valueType in parameter port: " + currentParam.getClass().getSimpleName() + " in port " + paramPort.name);
				;
			}
			currentParamID = Optional.of(paramID);
		}
		else
			currentParamID = Optional.empty();
	}

	/**
	 * Operation that performs the constraint specified by the {@code constraint}
	 * field variable. Constraint consists mainly of calculating/updating the
	 * display data based on the changed constraint parameter(s) and then
	 * transmiting it to the area display. This operation can be overidden to
	 * perform additional/different constraint(s), but the overiding operation
	 * should lastly invoke the {@code transmitDisplayData()} operation to transmit
	 * the area display data for display.
	 */
	@Override
	protected void performConstraints()
	{
		constraint.apply();
		transmitDisplayData();
	}

	/**
	 * Transmits the display data for the area display
	 */
	protected void transmitDisplayData()
	{
		displayTransmitter.transmitData(displayData);
	}

	/**
	 * Overridable operation that creates/constructs the area display definition.
	 * Specializations should override (and invoke) this method to initialize the
	 * values of the {@code displayDefinition} for tranmission to the area display.
	 */
	protected void createDisplayDefinition()
	{
		displayDefinition = new AnimatedAreaDisplayDefinition();
	}

	/**
	 * Overridable operation that creates/constructs the area display data.
	 * Specializations should override (and invoke) this method to initialize the
	 * values of the {@code displayData} for tranmission to the area display.	 *
	 */
	protected void createDisplayData()
	{
		displayData = new AnimatedAreaDisplayData();
	}

	/**
	 * Overridable operation that simply instantiates the map of parameters.
	 * Specializations should override (and invoke) this operation so as to and then
	 * "put()" the needed parameters into the map for access by the {@code
	 * performConstraints()} operation.
	 */
	@Override
	protected void createConstraintParameters()
	{
		pointParams = new HashMap<>();
		lineParams = new HashMap<>();
	}

	/**
	 * Overridable operation to create the {@code constraint} function to be
	 * performed by the {@code performConstraints()} operation. Specializations
	 * should create the constraint function (a lambda expression) to use the
	 * current constraint parameter and parameter ID to identify which object(s) in
	 * the display are to be updated/changed and the maps of constraint parameters
	 * to calculate/update the display data to be transmitted to the area display.
	 */
	@Override
	protected void createConstraints()
	{
		constraint = () ->
		{
			if (currentParam instanceof Point2D)
				displayData.updateImage(currentParamID.get(), null, null, null, null, null, ((Point2D)currentParam), null);
			else if (currentParam instanceof Polyline2D)
				displayData.updateLine(currentParamID.get(), null, ((Polyline2D)currentParam), null, null, null);
		};
	}

	/**
	 * Overridable operation that simply instantiates the map of parameter port
	 * functions. Specializations shoud override (and invoke) this operation to
	 * "put()" the needed function instances (lambda expressions) into the map for
	 * access by the overridden {@code createConstraintParameterPorts()} operation,
	 * i.e. as an argument to the {@code SysMLConstraintParameterPort} constructor.
	 */
	@Override
	protected void createConstraintParameterPortFunctions()
	{
		paramPortFunctions = new HashMap<>();
	}

	/**
	 * Overridable operation that simply instantiates the map of parameter ports.
	 * Specializations shoule override (and invoke) this operation to "put()" the
	 * needed port instances into the map for access by the
	 * {@code retrieveParameters()} operation.
	 */
	@Override
	protected void createConstraintParameterPorts()
	{
		paramPorts = new HashMap<>();
	}

	@Override
	public void stop()
	{
		displayTransmitter.stop();
		super.stop();
	}
}
