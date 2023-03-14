package sysmlinjava.analysis.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * Generic class to transmit objects of a single type T via the User Datagram
 * Protocol (UDP) to a {@code UDPReceiver}. {@code UDPTransmitter} transmits
 * individual instances of the type T to the {@code UDPReceiver} via simple
 * datagrams.
 * 
 * @author ModelerOne
 *
 * @param <T> The type (class) of object that is to be transmitted.
 */
public class UDPTransmitter<T>
{
	/**
	 * Logger
	 */
	protected Logger logger;
	/**
	 * Whether or not to log {@code transmit1()} to console ({@code transmit0}
	 * always logs)
	 */
	protected boolean logToConsole;
	/**
	 * Port to which the socket is to transmit the T objects
	 */
	final int udpPort;
	/**
	 * Socket used to transmit the objects
	 */
	DatagramSocket socket;
	/**
	 * Byte stream that encapsulates the object stream
	 */
	ByteArrayOutputStream byteStream;
	/**
	 * Object stream that encapsulates the object
	 */
	ObjectOutputStream objectStream;
	/**
	 * String name for this socket
	 */
	String socketName;

	/**
	 * Constructor, which creates the socket
	 * 
	 * @param udpPort      port to which socket is to transmit
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every
	 *                     object's {@code toString()} string to the console. The
	 *                     more frequently log messages are sent to console, the
	 *                     greater the CPU resources are needed. This can cause
	 *                     noticable slowing of the console display and related
	 *                     applications.
	 * @param socketName unique name for this transmitter's socket, e.g. Bar Chart, HTML Display
	 */
	public UDPTransmitter(int udpPort, boolean logToConsole, String socketName)
	{
		super();
		logger = Logger.getLogger(this.getClass().getSimpleName());
		this.udpPort = udpPort;
		this.logToConsole = logToConsole;
		this.socketName = socketName;
		try
		{
			socket = new DatagramSocket();
			logger.info(String.format("%s socket opened, transmitting via UDP port: %d", socketName, udpPort));
		} catch (SocketException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Operation to actually perform the transmission of the specified T object.
	 * Transmit simply creates the necessary output streams, uses them to construct
	 * the datagram, and sends the datagram via the socket.
	 * 
	 * @param t object of type T to be transmitted to the UDPReceiver
	 */
	public void transmit(T t)
	{
		if(logToConsole)
			logger.info(t.toString());
		try
		{
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(100_000);
			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(t);
			DatagramPacket packet = new DatagramPacket(byteStream.toByteArray(), 0, byteStream.size(), InetAddress.getLocalHost(), udpPort);
			socket.send(packet);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Stops the transmitter (closes the socket)
	 */
	public void stop()
	{
		socket.close();
		logger.info(String.format("%s socket closed for UDP port: %d", socketName, udpPort));
	}
}
