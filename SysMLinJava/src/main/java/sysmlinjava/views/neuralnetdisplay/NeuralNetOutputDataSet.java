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
 * SysML value type for the output values from a neural net
 * 
 * @author ModelerOne
 */
public class NeuralNetOutputDataSet extends SysMLAttributeType implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = 8718231767748674285L;

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
	 * Sets value of the output values and notifies all value change observers
	 * 
	 * @param outputDataSet NeuralNetOutputDataSet to which this is to be set
	 */
	public void setValue(NeuralNetOutputDataSet outputDataSet)
	{
		this.outputValues = outputDataSet.outputValues;
		notifyAttributeObservers();
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
	public SysMLAttributeType copy()
	{
		return new NeuralNetOutputDataSet(Arrays.copyOf(outputValues, outputValues.length));
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
		builder.append("NeuralNetOutputDataSet [outputValues=");
		builder.append(Arrays.toString(outputValues));
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