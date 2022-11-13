package sysmlinjava.analysis.neuralnetdisplay;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Data set for the neural net, i.e. set of input values and their corresponding
 * output values
 * 
 * @author ModelerOne
 *
 */
public class NeuralNetDataSet implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8718231767748674285L;

	/**
	 * Time of the data input/output
	 */
	public LocalTime time;
	/**
	 * Inputs to the neural net
	 */
	public double[] inputValues;
	/**
	 * Outputs from the inputs to the neural net
	 */
	public double[] outputValues;

	/**
	 * Constructor
	 * 
	 * @param time         time of the data
	 * @param inputValues  values input to the neural net
	 * @param outputValues values output from the neural net
	 */
	public NeuralNetDataSet(LocalTime time, double[] inputValues, double[] outputValues)
	{
		super();
		this.time = time;
		this.inputValues = inputValues;
		this.outputValues = outputValues;
	}

	/**
	 * Returns formatted strings for the input and output values
	 * 
	 * @param displayDef definition of the display, i.e. including the format string
	 *                   for each in/out value
	 * @return formatted string of the values
	 */
	public String toFormattedStrings(NeuralNetDisplayDefinition displayDef)
	{
		StringJoiner inputs = new StringJoiner(" ", "[", "]");
		for (int i = 0; i < inputValues.length; i++)
			inputs.add(String.format(displayDef.inputNeuronDefs.get(i).numberFormat, inputValues[i]));

		StringJoiner outputs = new StringJoiner(" ", "[", "]");
		for (int i = 0; i < outputValues.length; i++)
			outputs.add(String.format(displayDef.outputNeuronDefs.get(i).numberFormat, outputValues[i]));

		return time.toString() + " " + inputs.toString() + " --> " + outputs.toString();
	}

	/**
	 * Log-type representation of the data set
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[NN] [time=%s, inputValues=%s, outputValues=%s]", time.toString(), Arrays.toString(inputValues), Arrays.toString(outputValues));
	}

	@Override
	public String toString()
	{
		return String.format("NeuralNetDataSet [time=%s, inputValues=%s, outputValues=%s]", time.toString(), Arrays.toString(inputValues), Arrays.toString(outputValues));
	}
}