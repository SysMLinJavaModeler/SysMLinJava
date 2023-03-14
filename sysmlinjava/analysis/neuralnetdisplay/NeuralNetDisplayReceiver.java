package sysmlinjava.analysis.neuralnetdisplay;

import sysmlinjava.analysis.common.UDPReceiver;

/**
 * Specialization of the {@code UDPReceiver} to receive neural network data for
 * display via the {@code NeuralNetDisplay}. This class simply implements the
 * {@code UDPReceiver}'s {@code receive(Object)} operation for the neural net
 * data objects by displaying the objects as {@code toString()}s as log entries.
 * 
 * @author ModelerOne
 * @see NeuralNetDisplayData
 * @see NeuralNetDisplayDefinition
 */
public class NeuralNetDisplayReceiver extends UDPReceiver
{
	/**
	 * Definition of the display
	 */
	NeuralNetDisplayDefinition displayDef;

	/**
	 * Constructur - initial value of UDP port
	 * 
	 * @param udpPort UDP port on which to receive neural net data
	 */
	public NeuralNetDisplayReceiver(int udpPort)
	{
		super(udpPort, "NeuralNetDisplayReceiver");
	}

	@Override
	public boolean receive(Object data)
	{
		if (data instanceof NeuralNetDisplayData)
		{
			logger.info(((NeuralNetDisplayData)data).toDisplayString(displayDef));
		}
		else if (data instanceof NeuralNetDisplayDefinition)
		{
			this.displayDef = ((NeuralNetDisplayDefinition)data);
			logger.info(displayDef.toString());
		}
		return false;
	}
}
