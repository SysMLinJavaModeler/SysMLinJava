package sysmlinjava.analysis.neuralnetdisplay;

/**
 * The {@code NeuralNetDisplay} is a simple console application that receives
 * {@code NeuralNetData} via a UDP socket and displays the data as text on the
 * console.
 * <p>
 * <b>Note:</b> This simple text-based display is part of the basic SysMLinJava
 * tool set. More capable graphics-based displays are available commercially.
 * See SysMLinJava.com for details.
 * 
 * @author ModelerOne
 *
 */
public class NeuralNetDisplay
{
	/**
	 * UDP port at which the display data is to be received
	 */
	public static final int udpPort = 8897;
	/**
	 * Receiver of the dispay data
	 */
	NeuralNetDisplayReceiver receiver;

	/**
	 * Constructor
	 */
	public NeuralNetDisplay()
	{
		super();
		receiver = new NeuralNetDisplayReceiver(udpPort);
	}

	/**
	 * Main for console process. Simply constructs the display and starts (runs) its
	 * receiver which receives the line chart data and displays them as toString()s
	 * to the console.<br>
	 * 
	 * @param args null arguments
	 */
	public static void main(String[] args)
	{
		NeuralNetDisplay display = new NeuralNetDisplay();
		display.receiver.run();
	}
}
