package sysmlinjava.analysis.neuralnetdisplay;

import java.io.Serializable;
import java.util.StringJoiner;
import sysmlinjava.units.SysMLinJavaUnits;
import sysmlinjava.valuetypes.SysMLValueType;

/**
 * Data (set of input and output neuron data) to be displayed on a neural net
 * display
 * 
 * @author ModelerOne
 *
 */
public class NeuralNetDisplayData extends SysMLValueType implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8005702743864254971L;

	/**
	 * ID of the display
	 */
	public String displayID;
	/**
	 * Data set for one input set and its corresponsing output set of neurons
	 */
	public NeuralNetDataSet dataSet;

	/**
	 * Constructor
	 * 
	 * @param displayID ID of the display
	 * @param dataSet   Data set for one input set and its corresponsing output set
	 *                  of neurons
	 */
	public NeuralNetDisplayData(String displayID, NeuralNetDataSet dataSet)
	{
		super();
		this.displayID = displayID;
		this.dataSet = dataSet;
	}

	/**
	 * String representation of the data set
	 * 
	 * @param displayDef definition of the display
	 * @return string representation of the data set
	 */
	public String toDisplayString(NeuralNetDisplayDefinition displayDef)
	{
		StringBuilder result = new StringBuilder();
		result.append(String.format("%s", displayID));
		StringJoiner inputValues = new StringJoiner(",", "[", "]");
		for (int i = 0; i < displayDef.inputNeuronDefs.size(); i++)
		{
			String format = displayDef.inputNeuronDefs.get(i).numberFormat;
			inputValues.add(String.format(format, dataSet.inputValues[i]));
		}
		result.append(inputValues.toString());
		StringJoiner outputValues = new StringJoiner(",", "[", "]");
		for (int i = 0; i < displayDef.outputNeuronDefs.size(); i++)
		{
			String format = displayDef.outputNeuronDefs.get(i).numberFormat;
			outputValues.add(String.format(format, dataSet.outputValues[i]));
		}
		result.append(outputValues.toString());
		return result.toString();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Object;
	}

	@Override
	public String toString()
	{
		return String.format("NeuralNetDisplayData [displayID=%s, dataSet=%s]", displayID, dataSet);
	}
}