package sysmlinjava.analysis.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * The {@code UDPReceiver} is a {@code Runnable} class that receive objects from
 * {@code UDPTransmitter}s via the User Datagram Protocol (UDP). This abstract
 * class must be specialized (extended) to receive specific types of objects by
 * overriding its {@code receive()} operation.
 * 
 * @author ModelerOne
 *
 */
public abstract class UDPReceiver implements Runnable
{
	/**
	 * Logger for this UDP receiver
	 */
	protected Logger logger;
	/**
	 * Port on which the receiver operates, i.e. socket uses to read datagrams
	 */
	public int udpPort;
	/**
	 * Socket on which the receiver receives datagrams
	 */
	public DatagramSocket publicSocket;
	/**
	 * Number bytes in datagram packet byte buffer
	 */
	public static final int byteBufferSize = 100_000;

	/**
	 * Constructor - initial value
	 * 
	 * @param udpPort UDP port on which to receive objects in UDP datagrams
	 */
	public UDPReceiver(int udpPort)
	{
		super();
		logger = Logger.getLogger(this.getClass().getName());
		this.udpPort = udpPort;
	}

	/**
	 * Run operation that simply receives a UDP datagram, streams the datagram's
	 * byte array into an object via an {@code ObjectInputStream}, and invokes the
	 * abstract operation to {@code receive(Object)} to process the received object.
	 * It continues this activity until an indication that the reception is done is
	 * received from the {@code receive(Object)} operation.
	 */
	public void run()
	{
		logger.info("run() started");
		try (DatagramSocket socket = new DatagramSocket(udpPort))
		{
			logger.info("socket opened, receiving on UDP port " + udpPort);
			publicSocket = socket;
			boolean done = false;
			do
			{
				try
				{
					DatagramPacket packet = new DatagramPacket(new byte[byteBufferSize], byteBufferSize);
					socket.receive(packet);
					ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength());
					try (ObjectInputStream objectStream = new ObjectInputStream(byteStream))
					{
						try
						{
							Object readObject = objectStream.readObject();
							done = receive(readObject);
						} catch (ClassNotFoundException e)
						{
							e.printStackTrace();
						}
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				} catch (SocketException e)
				{
					if (socket.isClosed() || e.getMessage().toLowerCase().contains("socket closed"))
					{
						logger.info("socket closed");
						done = true;
					}
					else
						e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			} while (!done);
		} catch (BindException e)
		{
			logger.severe(String.format("Exception %s - UDP port %d for this display may already be in use for another display.", e.getMessage(), udpPort));
		} catch (SocketException e)
		{
			e.printStackTrace();
		} finally
		{
			logger.info("run() completed");
		}
	}

	/**
	 * Receives the specified data object received via UDP. Overrides of this
	 * operation should translate (cast) the data into the appropriate type for the
	 * specialized receiver and perform whatever processing is neccesary for the
	 * type of data. An example is as follows:
	 * 
	 * <pre>
	 * {@code
	 * 
	 * if (data instanceof LineChartData)
	 * 	logger.info(((LineChartData)data).toString());
	 * else if (data instanceof LineChartDefinition)
	 * 	logger.info(((LineChartDefinition)data).toString());
	 * else
	 * 	logger.warning("unrecognized object received");
	 * return false;
	 * 
	 * }
	 * </pre>
	 * 
	 * @param data data object received via the UDP
	 * @return true if the receiver is to stop/close its operation, false otherwise.
	 */
	public abstract boolean receive(Object data);
}