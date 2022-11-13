package sysmlinjava.analysis.neuralnetdisplay;

import sysmlinjava.common.SysMLInterface;

/**
 * Functional interface for the operation to calculate the output data set for
 * the specified input data set of the implementing neural network
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface NeuralNetwork extends SysMLInterface
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
