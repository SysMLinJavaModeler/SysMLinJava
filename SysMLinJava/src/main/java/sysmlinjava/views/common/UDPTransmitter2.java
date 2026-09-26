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
package sysmlinjava.views.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * Generic class to transmit objects of two types T and U via the User Datagram
 * Protocol (UDP) to a {@code UDPReceiver}. {@code UDPTransmitter2} transmits
 * individual instances of either type T or U to the {@code UDPReceiver} via
 * simple datagrams.
 * 
 * @author ModelerOne
 *
 * @param <T> One type (class) of object that is to be transmitted.
 * @param <U> Another type (class) of object that is to be transmitted.
 */
public class UDPTransmitter2<T, U>
{
	/**
	 * Logger
	 */
	protected Logger logger;
	/**
	 * Whether or not to log {@code transmit1()} to console ({@code transmit0}
	 * always logs)
	 */
	public boolean logToConsole;
	/**
	 * Port to which the socket is to transmit the T objects
	 */
	public int udpPort;
	/**
	 * Socket used to transmit the objects
	 */
	DatagramSocket socket;
	/**
	 * Byte stream that encapsulates the object stream
	 */
	ByteArrayOutputStream byteStream;
	/**
	 * Object stream that encapsulates the object stream
	 */
	ObjectOutputStream objectStream;
	/**
	 * String name of this transmitter's socket
	 */
	String socketName;
	/**
	 * Number bytes in datagram packet buffer
	 */
	public static final int byteBufferSize = 100_000;

	/**
	 * Constructor, which creates the socket
	 * 
	 * @param udpPort      port to which socket is to transmit
	 * @param logToConsole whether to send all {@code transmit1()} logs to console.
	 *                     Note: {@code transmit1()} operation can log every
	 *                     object's {@code toString()} string to the console. The
	 *                     more frequently log messages are sent to console, the
	 *                     greater the CPU resources are needed. This can cause
	 *                     noticable slowing of the console display and related
	 *                     applications.
	 * @param socketName unique name for this transmitter's socket, e.g. Bar Chart, HTML Display
	 */
	public UDPTransmitter2(int udpPort, boolean logToConsole, String socketName)
	{
		super();
		logger = Logger.getLogger(getClass().getName());
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
	 * Operation to actually perform the transmission of one of the specified types
	 * of object. Transmit simply creates the necessary output streams, uses them to
	 * construct the datagram, and sends the datagram via the socket.
	 * 
	 * @param t Object of type T to be transmitted to the UDPReceiver
	 */
	public void transmit0(T t)
	{
		if (logToConsole)
			logger.info(t.toString());
		try
		{
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(byteBufferSize);
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
	 * Operation to actually perform the transmission of the other specified types
	 * of object. Transmit simply creates the necessary output streams, uses them to
	 * construct the datagram, and sends the datagram via the socket.
	 * 
	 * @param u Object of type U to be transmitted to the UDPReceiver
	 */
	public void transmit1(U u)
	{
		if (logToConsole)
			logger.info(u.toString());
		try
		{
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(byteBufferSize);
			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(u);
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
