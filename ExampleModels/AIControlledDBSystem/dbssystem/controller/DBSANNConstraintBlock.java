package dbssystem.controller;

import sysmlinjava.analysis.neuralnetdisplay.NeuralNetDisplay;
import sysmlinjava.analysis.neuralnetdisplay.NeuralNetDisplayDefinition;
import sysmlinjava.analysis.neuralnetdisplay.NeuralNetInputDataSet;
import sysmlinjava.analysis.neuralnetdisplay.NeuralNetworkConstraintBlock;
import sysmlinjava.analysis.neuralnetdisplay.NeuralNetDisplayDefinition.NeuronDisplayDefinition;
import sysmlinjava.ports.SysMLConstraintParameterPort;
import java.util.List;
import java.util.Optional;
import dbssystem.controller.DBSANN.TremorAmplitudeEnum;
import dbssystem.controller.DBSANN.FreqAmpPulse;
import dbssystem.controller.DBSANN.FreqPhase;
import dbssystem.controller.DBSANN.FrequencyEnum;
import dbssystem.controller.DBSANN.PulseEnum;

public class DBSANNConstraintBlock extends NeuralNetworkConstraintBlock
{
	/**
	 * Unique ID for the display
	 */
	public static final String displayID = "NeuralNetDisplay";
	/**
	 * Format string for values
	 */
	public static final String format = "%5.2f";
	/**
	 * Definition of the input neurons display
	 */
	public static final List<NeuronDisplayDefinition> inputNeuronDefs = List.of(new NeuronDisplayDefinition("Frequency High", format), new NeuronDisplayDefinition("Frequency Med ", format),
		new NeuronDisplayDefinition("Frequency Low ", format), new NeuronDisplayDefinition("Frequency Off ", format), new NeuronDisplayDefinition("Amplitude High", format), new NeuronDisplayDefinition("Amplitude Low ", format),
		new NeuronDisplayDefinition("Pulse     High", format), new NeuronDisplayDefinition("Pulse     Low ", format));
	/**
	 * Definition of the output neurons display
	 */
	public static final List<NeuronDisplayDefinition> outputNeuronDefs = List.of(new NeuronDisplayDefinition("Frequency  High ", format), new NeuronDisplayDefinition("Frequency  Med  ", format),
		new NeuronDisplayDefinition("Frequency  Low  ", format), new NeuronDisplayDefinition("Frequency  Off  ", format), new NeuronDisplayDefinition("PhaseShift Large", format), new NeuronDisplayDefinition("PhaseShift Small", format));

	public DBSANNConstraintBlock(DBSController controller)
	{
		super(Optional.of(controller), Optional.of(new NeuralNetDisplayDefinition(displayID, inputNeuronDefs, outputNeuronDefs, NeuralNetDisplay.udpPort)), true);
		neuralNet = new DBSANN();
	}

	/**
	 * Starts the constraint block, i.e. transitions the block's state machine, if
	 * one has been configure, to its first state and transmits the display
	 * definition, if one has been configured, to the display. This operation should
	 * be called when the constraint block is to start performing/calculating its
	 * neural net constraint from it's bound (and changing) neural net input
	 * parameters located in the parameter context block.
	 */
	@Override
	public void start()
	{
		super.start();
	}

	/**
	 * Stops the constraint block, i.e. transitions the block's state machine to the
	 * final state (stops it), and stops transmission of neural net display data to
	 * the neural net display
	 */
	@Override
	public void stop()
	{
		super.stop();
	}

	@Override
	protected void createValues()
	{
		super.createValues();
		numberInputNeurons.value = inputNeuronDefs.size();
		numberOutputNeurons.value = outputNeuronDefs.size();
	}

	@Override
	protected void createConstraintParameterPortFunctions()
	{
		neuralNetInputParamPortFunction = (constraintParameterPort, contextBlock) ->
		{
			DBSController controller = (DBSController)contextBlock;
			FrequencyEnum freq = FrequencyEnum.valueOf(controller.currentTremor.frequency.value);
			TremorAmplitudeEnum amp = TremorAmplitudeEnum.valueOf(controller.currentTremor.amplitude.value);
			PulseEnum pulse = PulseEnum.valueOf(controller.currentPulseRate.value);
			FreqAmpPulse fap = new FreqAmpPulse(freq, amp, pulse);
			constraintParameterPort.queuedParameterValues.add(new NeuralNetInputDataSet(fap.toANNInput()));
			constraintParameterPort.constraintBlock.valueChanged();
		};
		neuralNetOutputParamPortFunction = (constraintParameterPort, contextBlock) ->
		{
			FreqPhase freqPhase = FreqPhase.fromANNOutput(neuralNetOutputParam.outputValues);
			DBSController controller = (DBSController)contextBlock;
			controller.currentDBSFrequency.setValue(freqPhase.frequency.hertz);
			controller.currentDBSPhaseShift.setValue(freqPhase.phaseShift.radians);
			controller.currentControl.setValue(controller.currentDBSFrequency, controller.currentDBSPhaseShift);
		};
	}

	@Override
	protected void createConstraintParameterPorts()
	{
		neuralNetInputParamPort = new SysMLConstraintParameterPort(this, neuralNetInputParamPortFunction, "NeuralNetInputParamsPort");
		neuralNetOutputParamPort = new SysMLConstraintParameterPort(this, neuralNetOutputParamPortFunction, "NeuralNetOutputParamsPort");
	}
}
