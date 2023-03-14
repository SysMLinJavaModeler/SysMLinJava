package sysmlinjava.analysis.animatedareadisplay;

import java.util.Map;
import java.util.Optional;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;

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
	 * Definition of the area display to be calculated/constrained and displayed
	 */
	protected AnimatedAreaDisplayDefinition displayDefinition;

	/**
	 * Definition of the area display data be calculated/constrained and displayed
	 */
	protected AnimatedAreaDisplayData displayData;

	/**
	 * Transmitter of the area display's definition and data to the area display
	 */
	private AnimatedAreaDisplayTransmitter displayTransmitter;
	
	/**
	 * Map of the constraint parameter IDs to animation objects on the area display
	 */
	protected Map<String, String> paramIDAObjectIDMap;

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

	@Override
	public void stop()
	{
		displayTransmitter.stop();
		super.stop();
	}
}
