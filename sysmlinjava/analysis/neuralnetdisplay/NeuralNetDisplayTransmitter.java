package sysmlinjava.analysis.neuralnetdisplay;

import sysmlinjava.analysis.common.UDPTransmitter2;

/**
 * The {@code NeuralNetDisplayTransmitter} is a specialization of the
 * {@code UDPTransmitter2} to transmit neural network display data to a
 * {@code NeuralNetDisplayReceiver} for the display of a neural network
 * execution. This class simply uses the {@code UDPTransmitter2}'s
 * {@code transmit0(Object)} and {@code transmit1(Object)} operations to
 * transmit the {@code NeuralNetDisplayDefinition} and
 * {@code NeuralNetDisplayData} objects via the {@code UDPTransmitter2}'s
 * generic transmit operations.
 * 
 * @author ModelerOne
 * @see sysmlinjava.analysis.neuralnetdisplay.NeuralNetDisplayData
 * @see sysmlinjava.analysis.neuralnetdisplay.NeuralNetDisplayDefinition
 */
public class NeuralNetDisplayTransmitter extends UDPTransmitter2<NeuralNetDisplayDefinition, NeuralNetDisplayData>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort      UDP port which to transmit neural network data to
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public NeuralNetDisplayTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole, "NeuralNetDisplayTransmitter");
	}

	/**
	 * Transmits a {@code NeuralNetDisplayDefintion} to a
	 * {@code NeuralNetDisplayReceiver} of the {@code NeuralNetDisplay}
	 * 
	 * @param displayDefinition definition/specification of the neural network
	 *                          display
	 */
	public void transmitNeuralNetDisplayDefinition(NeuralNetDisplayDefinition displayDefinition)
	{
		super.transmit0(displayDefinition);
	}

	/**
	 * Transmits a {@code NeuralNetDisplayData} to a
	 * {@code NeuralNetDisplayReceiver} of the {@code NeuralNetDisplay}
	 * 
	 * @param displayData data provided to the input layer and data returned by the
	 *                    output layer from a calculation by the neural network to
	 *                    be displayed in the display.
	 */
	public void transmitNeuralNetDisplayData(NeuralNetDisplayData displayData)
	{
		super.transmit1(displayData);
	}
}
