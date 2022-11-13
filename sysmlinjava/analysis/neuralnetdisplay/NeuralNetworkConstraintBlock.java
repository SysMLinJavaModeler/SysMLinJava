package sysmlinjava.analysis.neuralnetdisplay;

import java.time.LocalTime;
import java.util.Optional;
import sysmlinjava.annotations.Constraint;
import sysmlinjava.annotations.Part;
import sysmlinjava.annotations.Value;
import sysmlinjava.annotations.parametrics.ConstraintParameter;
import sysmlinjava.annotations.parametrics.ConstraintParameterPort;
import sysmlinjava.annotations.parametrics.ConstraintParameterPortFunction;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLConstraint;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;
import sysmlinjava.ports.SysMLConstraintParameterPort;
import sysmlinjava.ports.SysMLConstraintParameterPortFunction;
import sysmlinjava.valuetypes.IInteger;

/**
 * Constraint block for the parametric analysis of a neural network. Constraint
 * parameters are the values to the neural network's input neuraons and from its
 * output neurons. Both inputs and outputs are sent to a neural network display
 * application for display in a inputs/outputs table.
 * 
 * @author ModelerOne
 *
 */
public class NeuralNetworkConstraintBlock extends SysMLConstraintBlock
{
	/**
	 * Neural network that performs the calculation of (constrains) the input params
	 * to the output params.
	 */
	@Part
	public NeuralNetwork neuralNet;
	/**
	 * Invocation of the neural net to calculate (constrain) the input params to the
	 * output params
	 */
	@Constraint
	public SysMLConstraint neuralNetworkConstraint;
	/**
	 * Number of neurons in the neural net's input layer
	 */
	@Value
	public IInteger numberInputNeurons;
	/**
	 * Number of neurons in the neural net's output layer
	 */
	@Value
	public IInteger numberOutputNeurons;
	/**
	 * Constraint parameter for the inputs to the neural net constraint
	 */
	@ConstraintParameter
	public NeuralNetInputDataSet neuralNetInputParam;
	/**
	 * Constraint parameter for the outputs of the neural net constraint
	 */
	@ConstraintParameter
	public NeuralNetOutputDataSet neuralNetOutputParam;
	/**
	 * Parameter port that is connected to the bound input parameters to retrieve
	 * their latest values. The {@code neuralNetInputParamPort} must be
	 * created/initialized in an override of the
	 * {@code createConstraintParameterPorts()} operation. It must be initialized
	 * with the {@code neuralNetInputParamPortFunction}.
	 */
	@ConstraintParameterPort
	public SysMLConstraintParameterPort neuralNetInputParamPort;
	/**
	 * Parameter port that is connected to the bound output parameters to set their
	 * their latest values. The {@code neuralNetOutputParamPort} must be
	 * created/initialized in an override of the
	 * {@code createConstraintParameterPorts()} operation. It must be initialized
	 * with the {@code neuralNetOutputParamPortFunction}.
	 */
	@ConstraintParameterPort
	public SysMLConstraintParameterPort neuralNetOutputParamPort;

	/**
	 * Parameter port function that actually retrieves the bound input parameter.
	 * The {@code neuralNetInputParamPortFunction} must be created/initialized in an
	 * override of the {@code createConstraintParameterPortFunctions()} operation.
	 * This function must be used to initialize the {@code neuralNetInputParamPort}.
	 */
	@ConstraintParameterPortFunction
	public SysMLConstraintParameterPortFunction neuralNetInputParamPortFunction;
	/**
	 * Parameter port function that actually sets the bound output parameter. The
	 * {@code neuralNetOutputParamPortFunction} must be created/initialized in an
	 * override of the {@code createConstraintParameterPortFunctions()} operation.
	 * This function must be used to initialize the
	 * {@code neuralNetOutputParamPort}.
	 */
	@ConstraintParameterPortFunction
	public SysMLConstraintParameterPortFunction neuralNetOutputParamPortFunction;
	/**
	 * Optional definition of the neural network display to be built and displayed
	 * for this constraint block
	 */
	public Optional<NeuralNetDisplayDefinition> displayDefinition;
	/**
	 * Transmitter of the neural network data to the neural network display
	 */
	private NeuralNetDisplayTransmitter displayTransmitter;

	/**
	 * Whether to send all {@code transmit()} logs to console.
	 */
	private boolean logToConsole;

	/**
	 * Constructor
	 * 
	 * @param paramContextBlock the SysMLBlock in whose context the neural net's
	 *                          input and output constraint parameters are located.
	 * @param displayDefinition definition of the neural net's inputs/outputs
	 *                          display.
	 * @param logToConsole      whether to send all {@code transmit()} logs to
	 *                          console. Note: {@code transmit()} operation can log
	 *                          every object's {@code toString()} string to the
	 *                          console. The more frequently log messages are sent
	 *                          to console, the greater the CPU resources are
	 *                          needed. This can cause noticable slowing of the
	 *                          console display and related applications.
	 */
	public NeuralNetworkConstraintBlock(Optional<? extends SysMLBlock> paramContextBlock, Optional<NeuralNetDisplayDefinition> displayDefinition, boolean logToConsole)
	{
		super(Optional.empty(), "NeuralNetwork");
		this.contextBlock = paramContextBlock;
		this.displayDefinition = displayDefinition;
		this.logToConsole = logToConsole;
	}

	@Override
	public void start()
	{
		if (displayDefinition.isPresent())
		{
			displayTransmitter = new NeuralNetDisplayTransmitter(displayDefinition.get().udpPort, logToConsole);
			displayTransmitter.transmitNeuralNetDisplayDefinition(displayDefinition.get());
		}
		super.start();
	}

	@Override
	public void stop()
	{
		super.stop();
		if (displayDefinition.isPresent())
			displayTransmitter.stop();
	}

	/**
	 * Retrieves the input parameter from the input param port. This operation is
	 * invoked by the constrain block's state machine upon a "change" event
	 * submitted by the bound parameter upon its being changed.
	 * <p>
	 * This operation performs the complete activity needed for most applications of
	 * the neural net constraint block and need not be overridden unless a
	 * different/custom type of parameter retrieval from the port is needed.
	 */
	@Override
	protected void onParameterChange(String paramID)
	{
		neuralNetInputParam = (NeuralNetInputDataSet)neuralNetInputParamPort.getValue();
	}

	/**
	 * Operation that performs the constraint specified by the {@code constraint}
	 * variable. After performing the constraint, the operation notifies the output
	 * parameter port of the value change so it can update the value in its bound
	 * parameter. It then proceeds to construct transmit the neural net's input and
	 * output data sets to the neural net display, if the display is configured for
	 * this constraint block.
	 * <p>
	 * This operation performs the complete activity needed for most applications of
	 * the neural net constraint block and need not be overridden unless a
	 * different/custom activity is needed.
	 */
	@Override
	protected void performConstraints()
	{
		neuralNetworkConstraint.apply();
		neuralNetOutputParamPort.valueChanged();
		displayDefinition.ifPresent(displayDefinition -> transmitDisplayData());
	}

	/**
	 * Constructs and transmits the neural net display data for display in the
	 * neural net display
	 * <p>
	 * This operation performs the complete activity needed for most applications of
	 * the neural net constraint block and need not be overridden unless a
	 * different/custom type of display data transmission is needed.
	 */
	protected void transmitDisplayData()
	{
		NeuralNetDataSet displayDataSet = new NeuralNetDataSet(LocalTime.now(), neuralNetInputParam.inputValues, neuralNetOutputParam.outputValues);
		NeuralNetDisplayData displayData = new NeuralNetDisplayData(displayDefinition.get().displayID, displayDataSet);
		displayTransmitter.transmitNeuralNetDisplayData(displayData);
	}

	/**
	 * Overridable operation to create the numbers of input and output neurons in
	 * the neural net, i.e the number of input and output values to be used by the
	 * neural net constraint. Numbers are initialized to zero and this operation
	 * should be overriden such that this operation is (super)invoked and then the
	 * actual values of the IIntegers are set.
	 */
	@Override
	protected void createValues()
	{
		numberInputNeurons = new IInteger(0);
		numberOutputNeurons = new IInteger(0);
	}

	/**
	 * Operation to create the two constraint parameters, i.e the inputs to and
	 * outputs from the neural net constraint. This operation need not be overridden
	 * if the numberIn/OutNeuron values have been set in createValues() operation.
	 */
	@Override
	protected void createConstraintParameters()
	{
		neuralNetInputParam = new NeuralNetInputDataSet(new double[(int)numberInputNeurons.value]);
		neuralNetOutputParam = new NeuralNetOutputDataSet(new double[(int)numberOutputNeurons.value]);
	}

	/**
	 * Operation to create the {@code constraint} function of the constraint block.
	 * The function is performed by the {@code performConstraints()} operation. The
	 * created function simply invokes the NeuralNetwork's calculate operation to
	 * calculate the neural net output data set from the neural net input data set.
	 * <p>
	 * This "create" operation need not be overridden as long as the
	 * {@code neuralNet} part complies with the {@code NeuralNetwork} interface and
	 * performs the correct neural net calculation of outputs from inputs.
	 */
	@Override
	protected void createConstraints()
	{
		neuralNetworkConstraint = () ->
		{
			neuralNetOutputParam = neuralNet.calculate(neuralNetInputParam);
		};
	}

	/**
	 * Overridable operation to create the functions performed by the constraint
	 * parameter ports to retrieve or transmit the bound constraint parameters.
	 * <p>
	 * One function of type {@code SysMLConstraintParameterPortFunction} should be
	 * specified for the input parameter port that accesses the inputs to the neural
	 * net, translates them into the input parameter, puts the parameter on the
	 * port's parameter queue, and posts a change event to the port's constraint
	 * block.
	 * <p>
	 * The other function, also of type {@code SysMLConstraintParameterPortFunction}
	 * should be specified for the output parameter port that accesses the outputs
	 * from the neural net, translates them into the output parameters in the
	 * context block, and then sets the value of the output parameters in the
	 * context block.
	 */
	@Override
	protected void createConstraintParameterPortFunctions()
	{
	}

	/**
	 * Overridable operation to create the ports that retrieve and transmit the
	 * inputs to and outputs from the neural net. A new
	 * {@code SysMLConstraintParemeterPort} should be created/initialized for the
	 * {@code neuralNetInputParamPort} and the {@code neuralNetOutputParamPort} with
	 * each using the appropriate {@code SysMLConstraintParameterPortFunction}
	 * defined above.
	 */
	@Override
	protected void createConstraintParameterPorts()
	{
	}
}
