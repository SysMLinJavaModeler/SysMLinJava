
/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.neuralnetdisplay;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;

import sysmlinjava.attributetypes.SysMLAttributeType;
import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysML value type for the input values to a neural net
 * 
 * @author ModelerOne
 */
public class NeuralNetInputDataSet extends SysMLAttributeType implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = 8718231767748674285L;

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
	 * Sets the input values and notifies all attribute change observers
	 * 
	 * @param inputDataSet NeuralNetInputDataSet to which this is to be set
	 */
	public void setValue(NeuralNetInputDataSet inputDataSet)
	{
		this.setValue(inputDataSet.inputValues);
	}

	/**
	 * Sets the input values and notifies all attribute change observers
	 * 
	 * @param inputValues values to which this is to be set
	 */
	public void setValue(double[] inputValues)
	{
		this.inputValues = inputValues;
		notifyAttributeObservers();
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
	public SysMLAttributeType copy()
	{
		return new NeuralNetInputDataSet(Arrays.copyOf(inputValues, inputValues.length));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("NeuralNetInputDataSet [inputValues=");
		builder.append(Arrays.toString(inputValues));
		builder.append(", units=");
		builder.append(units);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}