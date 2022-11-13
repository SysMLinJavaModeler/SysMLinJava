package sysmlinjava.analysis.neuralnetdisplay;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;
import sysmlinjava.units.SysMLinJavaUnits;
import sysmlinjava.valuetypes.SysMLValueType;
import sysmlinjava.annotations.Attribute;

/**
 * SysML value type for the output values from a neural net
 * 
 * @author ModelerOne
 *
 */
public class NeuralNetOutputDataSet extends SysMLValueType implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8718231767748674285L;

	/**
	 * Output values from the neural net
	 */
	@Attribute
	public double[] outputValues;

	/**
	 * Constructor
	 * 
	 * @param outputValues output values from the neural net
	 */
	public NeuralNetOutputDataSet(double[] outputValues)
	{
		super();
		this.outputValues = outputValues;
	}

	/**
	 * Returns formatted string of output values
	 * 
	 * @param displayDef defintion of the display that contains format strings
	 * @return formatted string of values
	 */
	public String toFormattedStrings(NeuralNetDisplayDefinition displayDef)
	{
		StringJoiner outputs = new StringJoiner(" ", "[", "]");
		for (int i = 0; i < outputValues.length; i++)
			outputs.add(String.format(displayDef.outputNeuronDefs.get(i).numberFormat, outputValues[i]));

		return outputs.toString();
	}

	/**
	 * Log-type string representation of the output values
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[NN] [outputValues=%s]", Arrays.toString(outputValues));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	@Override
	public String toString()
	{
		return String.format("NeuralNetDataSet [outputValues=%s]", Arrays.toString(outputValues));
	}
}