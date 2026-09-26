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

/**
 * Functional interface for the operation to calculate the output data set for
 * the specified input data set of the implementing neural network
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface NeuralNetwork
{
	/**
	 * Returns an instance of the output data for a neural network calculated from
	 * the specified input data set
	 * 
	 * @param inputDataSet input to the neural network
	 * @return output of the neural network
	 */
	NeuralNetOutputDataSet calculate(NeuralNetInputDataSet inputDataSet);
}
