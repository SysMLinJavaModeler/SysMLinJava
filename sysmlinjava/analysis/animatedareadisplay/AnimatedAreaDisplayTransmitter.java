package sysmlinjava.analysis.animatedareadisplay;

import sysmlinjava.analysis.common.UDPTransmitter2;

/**
 * The {@code AnimatedAreaDisplayTransmitter} is a specialization of the
 * {@code UDPTransmitter2} to transmit display data to a
 * {@code AnimatedAreaDisplayReceiver} for the display of area (2D euclidean
 * space) objects. This class simply implements the {@code transmitDefinition()}
 * and {@code transmitData()} operations that invoke the
 * {@code UDPTransmitter2}'s {@code transmit0()} and {@code transmit1()}
 * operations to transmit objects via the {@code UDPTransmitter2}'s generic
 * transmit operations.
 * 
 * @author ModelerOne
 * 
 * @see AnimatedAreaDisplayDefinition
 * @see AnimatedAreaDisplayData
 */
public class AnimatedAreaDisplayTransmitter extends UDPTransmitter2<AnimatedAreaDisplayData, AnimatedAreaDisplayDefinition>
{
	/**
	 * Constructur with UDP port specification
	 * 
	 * @param udpPort UDP port via which to transmit area display definitions and
	 *                data
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operations can log every
	 *                     object's {@code toString()} string to the console. The
	 *                     more frequently log messages are sent to console, the
	 *                     greater the CPU resources are needed. This can cause
	 *                     noticable slowing of the console display and related
	 *                     applications.
	 */
	public AnimatedAreaDisplayTransmitter(int udpPort, boolean logToConsole)
	{
		super(udpPort, logToConsole);
	}

	/**
	 * Transmits a {@code AnimatedAreaDisplayData} instance to a
	 * {@code AreaDisplayReceiver} of the {@code AnimatedAreaDisplay}
	 * 
	 * @param displayData data to be displayed
	 */
	public void transmitData(AnimatedAreaDisplayData displayData)
	{
		super.transmit0(displayData);
	}

	/**
	 * Transmits a {@code AnimatedAreaDisplayDefinition} specification to a
	 * {@code AnimatedAreaDisplayReceiver} of the {@code AnimatedAreaDisplay}
	 * 
	 * @param displayDefinition the definition of the area display to be provided
	 */
	public void transmitDefinition(AnimatedAreaDisplayDefinition displayDefinition)
	{
		super.transmit1(displayDefinition);
	}

	@Override
	public void stop()
	{
		super.stop();
	}
}
