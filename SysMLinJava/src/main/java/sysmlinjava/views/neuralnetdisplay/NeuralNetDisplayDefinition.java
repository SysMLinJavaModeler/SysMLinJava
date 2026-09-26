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
import java.util.List;
import java.util.StringJoiner;

import sysmlinjava.attributetypes.SysMLAttributeType;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * Definition of the neural net display, i.e. its input nerurons and its output
 * neurons display definitions, and its ID
 * 
 * @author ModelerOne
 */
public class NeuralNetDisplayDefinition extends SysMLAttributeType implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = 8945635974734237468L;

	/**
	 * String for the displays ID
	 */
	public String displayID;
	/**
	 * List of input neuron definitions
	 */
	public List<NeuronDisplayDefinition> inputNeuronDefs;
	/**
	 * List of output neuron definitions
	 */
	public List<NeuronDisplayDefinition> outputNeuronDefs;
	/**
	 * UDP port at which display receives data
	 */
	public int udpPort;

	/**
	 * Constructor
	 * 
	 * @param displayID        the displays ID
	 * @param inputNeuronDefs  list of definitions of input neurons
	 * @param outputNeuronDefs list of definitions of output neurons
	 * @param udpPort          UDP port number to receive the display data
	 */
	public NeuralNetDisplayDefinition(String displayID, List<NeuronDisplayDefinition> inputNeuronDefs, List<NeuronDisplayDefinition> outputNeuronDefs, int udpPort)
	{
		super();
		this.displayID = displayID;
		this.inputNeuronDefs = inputNeuronDefs;
		this.outputNeuronDefs = outputNeuronDefs;
		this.udpPort = udpPort;
	}

	/**
	 * Constructor for empty definition
	 */
	public NeuralNetDisplayDefinition()
	{
		this.displayID = "notSpecified";
		this.inputNeuronDefs = List.of();
		this.outputNeuronDefs = List.of();
		this.udpPort = NeuralNetDisplay.udpPort;
	}

	/**
	 * Sets this display definition to the values of the specified display
	 * definition
	 * 
	 * @param displayDefinition
	 */
	public void setValue(NeuralNetDisplayDefinition displayDefinition)
	{
		this.displayID = displayDefinition.displayID;
		this.inputNeuronDefs = displayDefinition.inputNeuronDefs;
		this.outputNeuronDefs = displayDefinition.outputNeuronDefs;
		this.udpPort = displayDefinition.udpPort;
	}

	/**
	 * Returns string-based list of neuron names and whether they are left justified
	 * 
	 * @param neuronDefs    list of neuron (input and output) definitions
	 * @param leftJustified whether left justified
	 * @return string-based list of neuron names
	 */
	public static String neuronNamesList(List<NeuronDisplayDefinition> neuronDefs, boolean leftJustified)
	{
		String flag = leftJustified ? "-" : "";
		StringJoiner joiner = new StringJoiner("\n");
		neuronDefs.forEach(def -> joiner.add(String.format(flag + "%s", def.name)));
		return joiner.toString();
	}

	/**
	 * String of neuron names
	 * 
	 * @param neuronDefs list of neuron definitions
	 * @return string of names
	 */
	public static String listToString(List<NeuronDisplayDefinition> neuronDefs)
	{
		StringJoiner joiner = new StringJoiner(" ", "[", "]");
		neuronDefs.forEach(def -> joiner.add(String.format("[%s (%s)]", def.name, def.numberFormat)));
		return joiner.toString();
	}

	/**
	 * Log-type representation of the display definition
	 * 
	 * @return log-type string representation of this definition
	 */
	public String toLogString()
	{
		return String.format("[NN] [displayID=%s, inputNeuronNames=%s, outputNeuronNames=%s, udpPort=%d]", displayID, listToString(inputNeuronDefs), listToString(outputNeuronDefs), udpPort);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Object;

	}

	@Override
	public String toString()
	{
		return String.format("NeuralNetDisplayDefinition [displayID=%s, inputNeuronNames=%s, outputNeuronNames=%s, udpPort=%d]", displayID, listToString(inputNeuronDefs), listToString(outputNeuronDefs), udpPort);
	}

	/**
	 * Definition of a neuron display in the neural net display
	 * 
	 * @author ModelerOne
	 */
	public static class NeuronDisplayDefinition implements Serializable
	{
		/** Serializable ID */
		private static final long serialVersionUID = -1163800012956118648L;

		/**
		 * Name of the neuron
		 */
		public String name;
		/**
		 * Format string for displaying the value of the neuron (input or output)
		 */
		public String numberFormat;

		/**
		 * Constructor
		 * 
		 * @param name         neuron's name
		 * @param numberFormat string format for number display
		 */
		public NeuronDisplayDefinition(String name, String numberFormat)
		{
			super();
			this.name = name;
			this.numberFormat = numberFormat;
		}

		@Override
		public String toString()
		{
			return String.format("NeuronDisplayDefinition [name=%s, numberFormat=%s]", name, numberFormat);
		}
	}
}
