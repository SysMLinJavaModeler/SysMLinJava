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

import java.time.LocalTime;
import java.util.Optional;

import sysmlinjava.analysis.ParametricAnalysisCase;
import sysmlinjava.attributetypes.IInteger;
import sysmlinjava.javaannotations.analysis.AnalysisResult;
import sysmlinjava.javaannotations.analysis.parametrics.Parameter;
import sysmlinjava.javaannotations.attributes.Attribute;

/**
 * Abstract parametric analysis case for the parametric analysis of a neural
 * network. Analysis parameters are the values that are input to the neural
 * network's input neuraons and output from its output neurons. Both inputs and
 * outputs are sent to a neural network display application for display in a
 * inputs/outputs table that can be used to analyze the performance of the
 * neural net.
 * 
 * @author ModelerOne
 */
public abstract class NeuralNetworkAnalysisCase extends ParametricAnalysisCase
{
	// /**
	// * Subject of the analysis Neural network that performs the calculation of the
	// * output params from the input params.
	// */
	// public NeuralNetwork neuralNet;
	/**
	 * Number of neurons in the neural net's input layer
	 */
	@Attribute
	public IInteger numberInputNeurons;
	/**
	 * Number of neurons in the neural net's output layer
	 */
	@Attribute
	public IInteger numberOutputNeurons;
	/**
	 * Analysis parameter for the inputs to the neural net constraint
	 */
	@Parameter
	public NeuralNetInputDataSet neuralNetInputParam;
	/**
	 * Analysis parameter for the outputs of the neural net constraint
	 */
	@Parameter
	public NeuralNetOutputDataSet neuralNetOutputParam;

	/**
	 * Definition of the neural network display to be built and displayed for this
	 * parametric analysis case
	 */
	@Attribute
	public NeuralNetDisplayDefinition displayDefinition;

	/**
	 * Analysis result that is the neural net's input and output values to be
	 * displayed
	 */
	@AnalysisResult
	public NeuralNetDisplayData displayData;

	/**
	 * Transmitter of the neural network data to the neural network display
	 */
	private NeuralNetDisplayTransmitter displayTransmitter;

	/**
	 * Whether to send all {@code transmit()} logs to console.
	 */
	private boolean logToConsole;

	/**
	 * Constructor
	 * 
	 * @param parent       the {@code ParametricAnalysisCase} in whose context the
	 *                     neural net's input and output constraint parameters are
	 *                     located.
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public NeuralNetworkAnalysisCase(Optional<ParametricAnalysisCase> parent, boolean logToConsole)
	{
		super(parent, "NeuralNetwork", 0L);
		this.logToConsole = logToConsole;
	}

	@Override
	public void start()
	{
		displayTransmitter = new NeuralNetDisplayTransmitter(displayDefinition.udpPort, logToConsole);
		displayTransmitter.transmitNeuralNetDisplayDefinition(displayDefinition);
		super.start();
	}

	@Override
	public void stop()
	{
		super.stop();
		displayTransmitter.stop();
	}

	/**
	 * Operation that performs the constraint specified by the {@code constraint}
	 * variable. After performing the constraint, the operation notifies the output
	 * parameter port of the value change so it can update the value in its bound
	 * parameter. It then proceeds to transmit the neural net's input and output
	 * data sets to the neural net display, if the display is configured for this
	 * parametric analysis case.
	 * <p>
	 * This operation performs the complete activity needed for most applications of
	 * the neural net parametric analysis case and need not be overridden unless a
	 * different/custom activity is needed.
	 */
	@Override
	public void perform()
	{
		transmitDisplayData();
	}

	/**
	 * Constructs and transmits the neural net display data for display in the
	 * neural net display
	 * <p>
	 * This operation performs the complete activity needed for most applications of
	 * the neural net parametric analysis case and need not be overridden unless a
	 * different/custom type of display data transmission is needed.
	 */
	protected void transmitDisplayData()
	{
		displayData.dataSet = new NeuralNetDataSet(LocalTime.now(), neuralNetInputParam.inputValues, neuralNetOutputParam.outputValues);
		displayTransmitter.transmitNeuralNetDisplayData(displayData);
	}

	/**
	 * Overridable operation to create the display definition and the numbers of
	 * input and output neurons in the neural net. These attributes are set to
	 * default (empty and zero) values. This operation should be overriden by
	 * specialized classes to set appropriate values to these attributes.
	 */
	@Override
	protected void createAttributes()
	{
		numberInputNeurons = new IInteger(0);
		numberOutputNeurons = new IInteger(0);
		displayDefinition = new NeuralNetDisplayDefinition();
	}

	@Override
	protected void createParameters()
	{
		neuralNetInputParam = new NeuralNetInputDataSet(new double[(int) numberInputNeurons.value]);
		neuralNetOutputParam = new NeuralNetOutputDataSet(new double[(int) numberOutputNeurons.value]);
	}

	@Override
	/**
	 * Operation to create the display data, i.e the inputs to and outputs from the
	 * neural net constraint. This operation need not be overridden if the
	 * numberIn/OutNeuron values have been set in createValues() operation.
	 */
	protected void createResult()
	{
		displayData = new NeuralNetDisplayData(displayDefinition.displayID, new NeuralNetDataSet());
	}
}
