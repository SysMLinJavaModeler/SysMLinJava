/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.neuralnetdisplay;

import java.io.Serializable;
import java.util.StringJoiner;

import sysmlinjava.attributetypes.SysMLAttributeType;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * Data (set of input and output neuron data) to be displayed on a neural net
 * display
 * 
 * @author ModelerOne
 *
 */
public class NeuralNetDisplayData extends SysMLAttributeType implements Serializable
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
	 * Constructor for empty data set
	 */
	public NeuralNetDisplayData()
	{
		this.displayID = "notSpecified";
		this.dataSet = new NeuralNetDataSet();
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