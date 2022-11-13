
package sysmlinjava.analysis.neuralnetdisplay;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;
import sysmlinjava.valuetypes.SysMLValueType;

/**
 * SysML value type for the input values to a neural net
 * 
 * @author ModelerOne
 *
 */
public class NeuralNetInputDataSet extends SysMLValueType implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8718231767748674285L;

	/**
	 * Input values to the neural net
	 */
	@Attribute
	public double[] inputValues;

	/**
	 * Constructor
	 * 
	 * @param inputValues input values to the neural net
	 */
	public NeuralNetInputDataSet(double[] inputValues)
	{
		super();
		this.inputValues = inputValues;
	}

	/**
	 * Sets value of the input values and notifies all value change observers
	 * 
	 * @param annInput array of input values
	 */
	public void setValue(double[] annInput)
	{
		inputValues = annInput;
		notifyValueChangeObservers();
	}

	/**
	 * Returns formatted string of input values
	 * 
	 * @param displayDef defintion of the display that contains format strings
	 * @return formatted string of values
	 */
	public String toFormattedStrings(NeuralNetDisplayDefinition displayDef)
	{
		StringJoiner inputs = new StringJoiner(" ", "[", "]");
		for (int i = 0; i < inputValues.length; i++)
			inputs.add(String.format(displayDef.inputNeuronDefs.get(i).numberFormat, inputValues[i]));

		return inputs.toString();
	}

	/**
	 * Log-type string representation of the input values
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[NN] [inputValues=%s]", Arrays.toString(inputValues));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	@Override
	public String toString()
	{
		return String.format("NeuralNetDataSet [inputValues=%s]", Arrays.toString(inputValues));
	}
}