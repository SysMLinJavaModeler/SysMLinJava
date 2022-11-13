package dbssystem.controller;

import sysmlinjava.analysis.neuralnetdisplay.NeuralNetInputDataSet;
import sysmlinjava.analysis.neuralnetdisplay.NeuralNetOutputDataSet;
import sysmlinjava.analysis.neuralnetdisplay.NeuralNetwork;
import sysmlinjava.common.SysMLClass;
import java.util.Arrays;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;

/**
 * {@code DBSANN} is a SysMLinJava model of an artificial neurial network (ANN)
 * trained to calculate the control values for a deep-brain stimulation (DBS).
 * The neural net is trained with an artificially simple data set to demonstrate
 * the use of SysMLinJava to model and simulate (execute the model of) an
 * AI-based control system. Training is performed during
 * construction/initialization to simplify the development and deployment of the
 * neural network.
 * <p>
 * The DBSANN is implements a {@code NeuralNetwork} interface enabling it to be
 * the constraint mechanism of the {@code NeuralNetworkConstraintBlock} This
 * constraint block provides the input parameters to the neural network,
 * executes its algorithm, and retrieves its outputs.
 * <p>
 * The DBSANN uses a basic multi-layer perceptron neural net provided by the
 * Neuroph Java Neural Network Framework (http://neuroph.sourceforge.net/).
 * However, any neural network framework could be used to realize the
 * {@code NeuralNetwork}.
 * 
 * @author ModelerOne
 *
 */
public class DBSANN extends SysMLClass implements NeuralNetwork
{
	/**
	 * Attribute for the neural network framework API
	 */
	MultiLayerPerceptron nn;
	/**
	 * Attribute for the training set
	 */
	DataSet trainingSet;

	/**
	 * Constructor. In addition to initializing the neural net and training data
	 * set, it also proceeds to create the training data and use it to actually
	 * train the NN.
	 */
	public DBSANN()
	{
		nn = new MultiLayerPerceptron(8, 8, 8, 6);
		trainingSet = new DataSet(8, 6);
		createTrainingSet();
		train();
	}

	/**
	 * Basic training data
	 */
	double[][][] inputToOutput = {{{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0}, {1.0, 0.0, 0.0, 0.0, 0.0, 1.0}}, {{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0}, {1.0, 0.0, 0.0, 0.0, 1.0, 0.0}},
		{{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0}, {1.0, 0.0, 0.0, 0.0, 0.0, 1.0}}, {{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0}, {1.0, 0.0, 0.0, 0.0, 1.0, 0.0}}, {{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0}, {0.0, 1.0, 0.0, 0.0, 0.0, 1.0}},
		{{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0}, {0.0, 1.0, 0.0, 0.0, 1.0, 0.0}}, {{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0}, {0.0, 1.0, 0.0, 0.0, 0.0, 1.0}}, {{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0}, {0.0, 1.0, 0.0, 0.0, 1.0, 0.0}},
		{{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 1.0, 0.0, 1.0, 0.0}}, {{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0}, {0.0, 0.0, 1.0, 0.0, 0.0, 1.0}}, {{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0}, {0.0, 0.0, 1.0, 0.0, 1.0, 0.0}},
		{{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0}, {0.0, 0.0, 1.0, 0.0, 0.0, 1.0}}, {{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0, 1.0, 0.0}}, {{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0}, {0.0, 0.0, 0.0, 1.0, 0.0, 1.0}},
		{{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0, 1.0, 0.0}}, {{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0}, {0.0, 0.0, 0.0, 1.0, 0.0, 1.0}}};

	/**
	 * Creates the training data set from the basic training data by simply creating
	 * numerous copies and shuffling (pseudo-randomizing) it.
	 */
	protected void createTrainingSet()
	{
		for (int copy = 0; copy < 5; copy++)
			for (int row = 0; row < inputToOutput.length; row++)
			{
				double[] inputs = inputToOutput[row][0];
				double[] outputs = inputToOutput[row][1];
				trainingSet.add(inputs, outputs);
			}

		trainingSet.shuffle();
	}

	/**
	 * Trains the neural net. Sets the training "rules" and executes the learing
	 * operation
	 */
	public void train()
	{
		nn.getLearningRule().setMaxError(0.001);
		nn.getLearningRule().setLearningRate(0.7);
		nn.getLearningRule().setMaxIterations(1000);
		nn.learn(trainingSet);
	}

	/**
	 * Tests the trained neural net. This test is not used in deployment of the
	 * DBSANN, but can be used for development testing as needed.
	 */
	public void test()
	{
		DataSet testingSet = new DataSet(8, 6);
		for (int row = 0; row < inputToOutput.length; row++)
		{
			double[] inputs = inputToOutput[row][0];
			double[] outputs = inputToOutput[row][1];
			testingSet.add(inputs, outputs);
		}

		for (int row = 0; row < testingSet.size(); row++)
		{
			double[] testInputRow = testingSet.get(row).getInput();
			nn.setInput(testInputRow);
			nn.calculate();
			double[] testOutput = nn.getOutput();
			double[] trainOutput = testingSet.get(row).getDesiredOutput();
			double[] trainTestDifference = new double[2];
			trainTestDifference[0] = trainOutput[0] - testOutput[0];
			trainTestDifference[1] = trainOutput[1] - testOutput[1];

			System.out.println(String.format("%3d) %s %s %s %s", row, Arrays.toString(testInputRow), Arrays.toString(testOutput), Arrays.toString(trainOutput), Arrays.toString(trainTestDifference)));
		}
	}

	/**
	 * Performs the DBSANN calculation of outputs (DBS signal frequency/phase) from
	 * inputs (tremor frequency/amplitude and pulse). It translates the DBS
	 * parameters into basic neural network primitives and uses the NN
	 * implementation to calculate the primitive values.
	 * 
	 * @param freqAmpPulse inputs to the first layer of the DBSANN
	 * @return outputs from the last layer of the DBSANN
	 */
	public FreqPhase freqPhaseFor(FreqAmpPulse freqAmpPulse)
	{
		double[] testInputRow = freqAmpPulse.toANNInput();
		nn.setInput(testInputRow);
		nn.calculate();
		double[] testOutput = nn.getOutput();
		return FreqPhase.fromANNOutput(testOutput);
	}

	/**
	 * Calculates the (presumably) trained {@code NeuralNetwork} output data from
	 * specified input data.
	 */
	@Override
	public NeuralNetOutputDataSet calculate(NeuralNetInputDataSet inputDataSet)
	{
		double[] testInputRow = inputDataSet.inputValues;
		nn.setInput(testInputRow);
		nn.calculate();
		return new NeuralNetOutputDataSet(nn.getOutput());
	}

	/**
	 * Representation of the input values to the first layer of the DBSANN which
	 * include categories of tremor frequency and amplitude as well as categories of
	 * pulse.
	 * 
	 * @author ModelerOne
	 *
	 */
	public static class FreqAmpPulse
	{
		/**
		 * Frequency category of patient's tremor
		 */
		public FrequencyEnum frequency;
		/**
		 * Amplitude category of patient's tremor
		 */
		public TremorAmplitudeEnum amplitude;
		/**
		 * Pulse category of patient's pulse
		 */
		public PulseEnum pulse;

		/**
		 * Constructor
		 * 
		 * @param frequency frequency category of patient's tremor
		 * @param amplitude amplitude category of patient's tremor
		 * @param pulse     pulse category of patient's pulse
		 */
		public FreqAmpPulse(FrequencyEnum frequency, TremorAmplitudeEnum amplitude, PulseEnum pulse)
		{
			super();
			this.frequency = frequency;
			this.amplitude = amplitude;
			this.pulse = pulse;
		}

		/**
		 * Translation of a set of frequency/amplitude/pulse category values into an
		 * array of primitive (double) values that can be input to the implemented
		 * neural network.
		 * 
		 * @return array of double values for input to the neural net implementation
		 */
		public double[] toANNInput()
		{
			double[] freqInput = frequency.annInputValueOf();
			double[] ampInput = amplitude.annInputValueOf();
			double[] pulseInput = pulse.annInputValueOf();
			double[] result = new double[freqInput.length + ampInput.length + pulseInput.length];
			int j = 0;
			for (int i = 0; i < freqInput.length; i++, j++)
				result[j] = freqInput[i];
			for (int i = 0; i < ampInput.length; i++, j++)
				result[j] = ampInput[i];
			for (int i = 0; i < pulseInput.length; i++, j++)
				result[j] = pulseInput[i];
			return result;
		}

		@Override
		public String toString()
		{
			return String.format("FreqAmpPulse [frequency=%s, amplitude=%s, pulse=%s]", frequency, amplitude, pulse);
		}
	}

	/**
	 * Representation of the output values from the last layer of the DBSANN which
	 * include categories of DBS signal frequency and pahse shift.
	 * 
	 * @author ModelerOne
	 *
	 */
	public static class FreqPhase
	{
		/**
		 * Enum value of frequency
		 */
		public FrequencyEnum frequency;
		/**
		 * Enum value of phase shift
		 */
		public PhaseShiftEnum phaseShift;

		/**
		 * Constructor
		 * 
		 * @param frequency  enum value of frequency
		 * @param phaseShift enum value of phase shift
		 */
		public FreqPhase(FrequencyEnum frequency, PhaseShiftEnum phaseShift)
		{
			super();
			this.frequency = frequency;
			this.phaseShift = phaseShift;
		}

		/**
		 * Translation of an array of primitive (double) values that were output from
		 * the implemented neural network to a set of frequency/phase-shift category
		 * values for use by the DBS controller
		 * 
		 * @param output value of output neurons
		 * @return set of frequency/phase-shift category values
		 */
		public static FreqPhase fromANNOutput(double[] output)
		{
			FrequencyEnum freq = FrequencyEnum.none;
			PhaseShiftEnum phase = PhaseShiftEnum.small;
			int numFreqs = FrequencyEnum.values().length;
			int numPhases = PhaseShiftEnum.values().length;
			int j = 0;
			for (int i = 0; i < numFreqs; i++, j++)
			{
				if (output[j] > 0.5)
					freq = FrequencyEnum.values()[i];
			}
			for (int i = 0; i < numPhases; i++, j++)
			{
				if (output[j] > 0.5)
					phase = PhaseShiftEnum.values()[i];
			}
			return new FreqPhase(freq, phase);
		}

		@Override
		public String toString()
		{
			return String.format("FreqPhase [frequency=%s, phaseShift=%s]", frequency, phaseShift);
		}
	}

	/**
	 * Enumeration of the categories of frequency that used for inputs to and
	 * outputs from the DBSANN
	 * 
	 * @author ModelerOne
	 *
	 */
	public enum FrequencyEnum
	{
		/**
		 * High frequency
		 */
		high(7.0),
		/**
		 * Medium frequency
		 */
		medium(4.0),
		/**
		 * Low frequency
		 */
		low(1.0),
		/**
		 * No frequency
		 */
		none(0.0);

		/**
		 * Frequency associated with the frequency category
		 */
		public double hertz;

		/**
		 * Input values associated with each frequency category being "active"
		 */
		public final static double[][] annInputValues = {{1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, {0.0, 0.0, 0.0, 1.0}};

		/**
		 * Constructor (hidden)
		 * 
		 * @param hertz frequency associated with the frequency category
		 */
		private FrequencyEnum(double hertz)
		{
			this.hertz = hertz;
		}

		/**
		 * Returns enum value that maps to the specified frequency
		 * 
		 * @param frequencyHertz specified frequency
		 * @return enum value
		 */
		public static FrequencyEnum valueOf(double frequencyHertz)
		{
			FrequencyEnum result = none;
			if (frequencyHertz >= high.hertz)
				result = high;
			else if (frequencyHertz >= medium.hertz)
				result = medium;
			else if (frequencyHertz >= low.hertz)
				result = low;
			return result;
		}

		/**
		 * Returns the neural network input values to be used for this enum value active
		 * 
		 * @return input values
		 */
		public double[] annInputValueOf()
		{
			return annInputValues[ordinal()];
		}
	}

	/**
	 * Enumeration of the categories of amplitude that used for inputs to the DBSANN
	 * 
	 * @author ModelerOne
	 *
	 */
	public enum TremorAmplitudeEnum
	{
		/**
		 * High tremor amplitude
		 */
		high(10.0),
		/**
		 * Low tremor amplitude
		 */
		low(0.0);

		/**
		 * Distance of tremor associated with this amplitude category
		 */
		public double millimeters;

		/**
		 * Input values associated with each amplitude category being "active"
		 */
		public final static double[][] annInputValues = {{1.0, 0.0}, {0.0, 1.0}};

		/**
		 * Constructor (hidden)
		 * 
		 * @param inches amplitude associated with the ammplitude category
		 */
		private TremorAmplitudeEnum(double inches)
		{
			this.millimeters = inches;
		}

		/**
		 * Returns enum value that maps to the specified frequency
		 * 
		 * @param millimeters tremor amplitude
		 * @return enum value
		 */
		public static TremorAmplitudeEnum valueOf(double millimeters)
		{
			if (millimeters > high.millimeters)
				return high;
			else
				return low;
		}

		/**
		 * Returns the neural network input values to be used for this enum value active
		 * 
		 * @return input values
		 */
		public double[] annInputValueOf()
		{
			return annInputValues[ordinal()];
		}
	}

	/**
	 * Enumeration of the categories of pulse that are used for inputs to the DBSANN
	 * 
	 * @author ModelerOne
	 *
	 */
	public enum PulseEnum
	{
		/**
		 * High pulse rate
		 */
		high(80),
		/**
		 * Low pulse rate
		 */
		low(0.0);

		/**
		 * Pulse rate associated with this pulse category
		 */
		public double beatsPerMin;

		/**
		 * Input values associated with each pulse category being "active"
		 */
		public final static double[][] annInputValues = {{1.0, 0.0}, {0.0, 1.0}};

		/**
		 * Constructor (hidden)
		 * 
		 * @param beatsPerMin pulse associated with the pulse category
		 */
		private PulseEnum(double beatsPerMin)
		{
			this.beatsPerMin = beatsPerMin;
		}

		/**
		 * Returns enum value that maps to the specified pulse
		 * 
		 * @param beatsPerMin specified pulse
		 * @return enum value
		 */
		public static PulseEnum valueOf(double beatsPerMin)
		{
			if (beatsPerMin > high.beatsPerMin)
				return high;
			else
				return low;
		}

		/**
		 * Returns the neural network input values to be used for this enum value active
		 * 
		 * @return input values
		 */
		public double[] annInputValueOf()
		{
			return annInputValues[ordinal()];
		}
	}

	/**
	 * Enumeration of the categories of phase shift that are used for outputs from
	 * the DBSANN
	 * 
	 * @author ModelerOne
	 *
	 */
	public enum PhaseShiftEnum
	{
		/**
		 * Large phase shift
		 */
		large(1.0),
		/**
		 * Small phase shift
		 */
		small(0.5);

		/**
		 * Phase shift (radians) associated with this enum value
		 */
		public double radians;

		/**
		 * Output values (approximate) associated with each pulse category being
		 * "active"
		 */
		public final static double[][] annOutputValues = {{1.0, 0.0}, {0.0, 1.0}};

		private PhaseShiftEnum(double radians)
		{
			this.radians = radians;
		}

		/**
		 * Returns the neural network output values to be used for this enum value
		 * active
		 * 
		 * @return output values
		 */
		public double[] annOutputValueOf()
		{
			return annOutputValues[ordinal()];
		}
	}
}
