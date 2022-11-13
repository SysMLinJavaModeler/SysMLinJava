package dbssystem.controller;

import java.util.List;
import java.util.Optional;
import dbssystem.common.DBSControl;
import dbssystem.common.PulseValue;
import dbssystem.common.TremorLevel;
import sysmlinjava.analysis.neuralnetdisplay.NeuralNetDisplayDefinition.NeuronDisplayDefinition;
import sysmlinjava.annotations.FullPort;
import sysmlinjava.annotations.Reception;
import sysmlinjava.annotations.Value;
import sysmlinjava.annotations.parametrics.BindingConnector;
import sysmlinjava.annotations.parametrics.BindingConnectorFunction;
import sysmlinjava.annotations.parametrics.ConstraintBlock;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.ValueObserver;
import sysmlinjava.connectors.SysMLBindingConnector;
import sysmlinjava.connectors.SysMLBindingConnectorFunction;
import sysmlinjava.valuetypes.DistanceMillimeters;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjava.valuetypes.InternetAddress;
import sysmlinjava.valuetypes.PhaseShiftRadians;

/**
 * DBSController is a SysMLinJava model of a controller that uses a patient's
 * tremor level and pulse to determine a control value for a deep-brain
 * stimulator signal to be injected into the patient's brain to reduce the
 * tremor. It receives tremor and pulse signals from sensors on the patient and
 * performs an AI-based constraint to determine the signal to be injected into
 * the patient's brain by an embedded DBS actuator.
 * <p>
 * Note the AI used in this model is artificially simple to enable
 * straightforward demonstration of the modeling and simulation of the DBS
 * system using SysMLinJava.
 * 
 * @author ModelerOne
 *
 */
public class DBSController extends SysMLBlock implements ValueObserver
{
	/**
	 * Port for the input of tremor level from the patient
	 */
	@FullPort
	public TremorLevelInPort tremorInPort;
	/**
	 * Port for the input of pulse values from the patient
	 */
	@FullPort
	public PulseValueInPort pulseInPort;
	/**
	 * Port for the output of DBS actuator controls to the actuator in the patient's
	 * brain
	 */
	@FullPort
	public DBSControlOutPort controlOutPort;

	/**
	 * Value of the current tremor level of the patient
	 */
	@Value
	public TremorLevel currentTremor;
	/**
	 * Value of the current pulse of the patient
	 */
	@Value
	public PulseValue currentPulseRate;
	/**
	 * Value of the current frequency of the DBS signal being transmitted to the
	 * patient's brain
	 */
	@Value
	public FrequencyHertz currentDBSFrequency;
	/**
	 * Value of the current pahse shift (relative to patient's tremor) of the DBS
	 * signal being transmitted to the patient's brain
	 */
	@Value
	public PhaseShiftRadians currentDBSPhaseShift;
	/**
	 * Value of the current frequency of the patient's tremor
	 */
	@Value
	public FrequencyHertz currentTremorFrequency;
	/**
	 * Value of the current amplitude of the patient's tremor
	 */
	@Value
	public DistanceMillimeters currentTremorAmplitude;
	/**
	 * Value of the current control being sent of the DBS actuator in the patient's
	 * brain.
	 */
	@Value
	public DBSControl currentControl;
	/**
	 * Value of the IP address to be used by the ports to receive tremor and pulse
	 * values
	 */
	@Value
	public InternetAddress ipAddress;
	/**
	 * Value of the UDP port to be used by the port to receive tremor level values
	 */
	@Value
	public IInteger tremorLevelUDPPort;
	/**
	 * Value of the UDP port to be used by the port to receive pulse values
	 */
	@Value
	public IInteger pulseValueUDPPort;

	/**
	 * Constraint block that determines the DBS signal to be output to the patient
	 * from the tremor and pulse values received from the patient
	 */
	@ConstraintBlock
	public DBSANNConstraintBlock dbsANNConstraintBlock;

	/**
	 * Function to make the (binding) connection of the tremor and pulse input
	 * parameters to the constraint block's corresponding input parameter ports.
	 */
	@BindingConnectorFunction
	public SysMLBindingConnectorFunction inputParamConnnectorFunction;
	/**
	 * Function to make the (binding) connection of the constraint block's
	 * corresponding output parameter port to the actuator control output
	 * parameters.
	 */
	@BindingConnectorFunction
	public SysMLBindingConnectorFunction outputParamConnnectorFunction;

	/**
	 * Connector that invokes the connector function to finally make the (binding)
	 * connection of the constraint block's input parameter ports to the tremor and
	 * pulse parameters
	 */
	@BindingConnector
	public SysMLBindingConnector inputParamConnnector;
	/**
	 * Connector that invokes the connector function to finally make the (binding)
	 * connection of the control parameter to the constraint block's output
	 * parameter port
	 */
	@BindingConnector
	public SysMLBindingConnector outputParamConnector;

	/**
	 * Constructor
	 * 
	 */
	public DBSController()
	{
		super("DBSController", 0L);
	}

	/**
	 * Starts the controller, i.e. starts the state machine (to being event
	 * handling), the constraint block (to perform constraints and start the neural
	 * net display), and the two input ports to receive the patient's tremor level
	 * and pulse rate.
	 */
	@Override
	public void start()
	{
		super.start();
		dbsANNConstraintBlock.start();
		tremorInPort.start();
		pulseInPort.start();
	}

	/**
	 * Stops the controller
	 */
	@Override
	public void stop()
	{
		tremorInPort.stop();
		pulseInPort.stop();
		dbsANNConstraintBlock.stop();
		super.stop();
	}

	/**
	 * Event handler for the receipt of a new tremor level value
	 * 
	 * @param level new tremor level value
	 */
	@Reception
	public void onTremorLevel(TremorLevel level)
	{
		logger.info(level.toString());
		currentTremor.setValue(level);
	}

	/**
	 * Event handler for the receipt of a new pulse value
	 * 
	 * @param value new pulse value
	 */
	@Reception
	public void onPulseValue(PulseValue value)
	{
		logger.info(value.toString());
		currentPulseRate.setValue(value);
	}

	/**
	 * Response to notification of a change to the actuator control value,
	 * presumably as a result of a new calculation by the AI-based constraint block.
	 */
	@Override
	public void valueChanged()
	{
		controlOutPort.transmit(currentControl);
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new DBSControllerStateMachine(this));
	}

	@Override
	protected void createValues()
	{
		currentDBSFrequency = new FrequencyHertz(0);
		currentDBSPhaseShift = new PhaseShiftRadians(0);
		currentTremorFrequency = new FrequencyHertz(0);
		currentTremorAmplitude = new DistanceMillimeters(0);
		currentTremor = new TremorLevel(new FrequencyHertz(5.0), new DistanceMillimeters(12.0));
		currentPulseRate = new PulseValue(IInteger.of(0));
		currentControl = new DBSControl(currentDBSFrequency, currentDBSPhaseShift);

		ipAddress = InternetAddress.ofLocalHost();
		tremorLevelUDPPort = new IInteger(8701);
		pulseValueUDPPort = new IInteger(8702);
	}

	@Override
	protected void createFullPorts()
	{
		tremorInPort = new TremorLevelInPort(this, ipAddress.toInetAddress(), tremorLevelUDPPort.toInteger());
		pulseInPort = new PulseValueInPort(this, ipAddress.toInetAddress(), pulseValueUDPPort.toInteger());
		controlOutPort = new DBSControlOutPort(this, 0L, "DBSControlOutPort");
	}

	@Override
	protected void createConstraintBlocks()
	{
		dbsANNConstraintBlock = new DBSANNConstraintBlock(this);
	}

	@Override
	protected void createConstraintParameterConnectorFunctions()
	{
		inputParamConnnectorFunction = () ->
		{
			dbsANNConstraintBlock.neuralNetInputParamPort.setParameterContextBlock(this);
			currentTremor.addValueChangeObserver(dbsANNConstraintBlock.neuralNetInputParamPort);
		};
		outputParamConnnectorFunction = () ->
		{
			dbsANNConstraintBlock.neuralNetOutputParamPort.setParameterContextBlock(this);
			currentControl.addValueChangeObserver(this);
		};
	}

	@Override
	protected void createConstraintParameterConnectors()
	{
		inputParamConnnector = new SysMLBindingConnector(this, dbsANNConstraintBlock, inputParamConnnectorFunction);
		outputParamConnector = new SysMLBindingConnector(this, dbsANNConstraintBlock, outputParamConnnectorFunction);
	}

	/**
	 * ID of the neural net display definition to be used to display the inputs and
	 * outputs of the controllers AI-base constraint block
	 */
	public static final String displayID = "NeuralNetDisplay";
	/**
	 * String format of the input and output values to be displayed by the neural
	 * net inputs/outputs display
	 */
	public static final String format = "%5.2f";
	/**
	 * List of the neuron definitions for the input values to be displayed by the
	 * neural net inputs/outputs display
	 */
	public static final List<NeuronDisplayDefinition> inputNeuronDefs = List.of(new NeuronDisplayDefinition("Frequency High", format), new NeuronDisplayDefinition("Frequency Med ", format),
		new NeuronDisplayDefinition("Frequency Low ", format), new NeuronDisplayDefinition("Frequency Off ", format), new NeuronDisplayDefinition("Amplitude High", format), new NeuronDisplayDefinition("Amplitude Low ", format),
		new NeuronDisplayDefinition("Pulse     High", format), new NeuronDisplayDefinition("Pulse     Low ", format));
	/**
	 * List of the neuron definitions for the output values to be displayed by the
	 * neural net inputs/outputs display
	 */
	public static final List<NeuronDisplayDefinition> outputNeuronDefs = List.of(new NeuronDisplayDefinition("Frequency  High ", format), new NeuronDisplayDefinition("Frequency  Med  ", format),
		new NeuronDisplayDefinition("Frequency  Low  ", format), new NeuronDisplayDefinition("Frequency  Off  ", format), new NeuronDisplayDefinition("PhaseShift Large", format), new NeuronDisplayDefinition("PhaseShift Small", format));
}
