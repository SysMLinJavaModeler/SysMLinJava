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
package sysmlinjava.ports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.events.SysMLSignalEvent;
import sysmlinjava.items.SysMLSignal;
import sysmlinjava.javaannotations.statemachines.StateMachine;
import sysmlinjava.occurrences.SysMLOccurrence;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjava.states.SysMLStateMachine;

/**
 * SysMLinJava's representation of the SysML port. This SysMLinJava
 * representation of the SysML port is a default representation to support
 * executable models. As such, it provides port connectors (that are invoked by
 * parts or ports that contain the connected ports) to connect the ports. And it
 * provides actions (invoked by parts or ports that contain the port) to
 * transmit and receive items to and from connected ports.
 * <p>
 * The {@code SysMLPort} extends the {@code SysMLOccurrence} and can include any
 * {@code SysMLPort}s that are nested (sub-)ports of the port. Extensions should
 * add any attributes or items that are used as inputs or outputs via the port.
 * <p>
 * {@code SysMLPort} also provides connectors for use of the port as a member of
 * a "protocol stack". Connectors to "server" ports are provided to connect to a
 * port in an upper-level of the stack, while connectors to "client" ports are
 * provided to connect to a port in a lower-level of the stack. Connectors to
 * "peer" ports are provided to connect this port to ports at the same-level
 * protocol port in other parts or ports.
 * 
 * @author ModelerOne
 * @see sysmlinjava.ports.SysMLPort
 * @see sysmlinjava.ports.SysMLProxyPort
 * @see sysmlinjava.parts.SysMLPart#createPorts()
 */
public abstract class SysMLPort extends SysMLOccurrence implements StateBehaviorContext
{
	/**
	 * Optional SysML part or port implementing {@code StateBehaviorContext} that
	 * serves as the port's "context" in which this port resides. The context may be
	 * used by other port features, such as actions and parts, for access to parent
	 * part or port features.
	 */
	public Optional<StateBehaviorContext> context;
	/**
	 * Part or port implementing {@code StateBehaviorContext} interface to which
	 * port events, such as received signal events, are to be submitted
	 */
	public Optional<StateBehaviorContext> eventContext;
	/**
	 * Ports to which this port is connected that are "above" this port in a
	 * protocol stack, i.e. ports that use this port to transmit and receive
	 */
	protected List<SysMLPort> connectedPortsClients;
	/**
	 * Ports to which this port is connected that are "below" this port in a
	 * protocol stack, i.e. ports that are used by this port to transmit and
	 * receive..
	 */
	protected List<SysMLPort> connectedPortsServers;
	/**
	 * Ports to which this port is connected that are "peers" to this port in other
	 * protocol stacks.
	 */
	protected List<SysMLPort> connectedPortsPeers;
	/**
	 * Ports to which this port is "virtually" connected that are peers to this port
	 * in other protocol stacks. This type of connection is provided to supports
	 * SysML's connectors, that merely specify an interface connection without
	 * regard to the capability to "execute" the connection.
	 * <p>
	 * Virtual port peers represent ports in a "protocol stack", a.k.a. "interface
	 * layers" that are connected virtually via lower-layer protocols/layers. For
	 * example, an HTTP client (web browser) port would be virtually connected to a
	 * peer HTTP server (web server) port via the TCP/IP/Ethernet protocols in the
	 * web browser and web server computers. SysMLinJava provides the
	 * {@code connectedVirtualPortsPeers} collection as a means of modeling virtual
	 * connections without requiring a capability to execute (transmit and receive
	 * signals via) the connection. Executable connections should be modeled as
	 * {@code connectedPortsPeers} objects.
	 * <p>
	 * The SysMLinJava TaskMaster&trade; application utilizes virtual connectors in
	 * its interface requirements generator tool. Modelers will need to specify
	 * virtual connectors as {@code connectedVirtualPortsPeers} if/when this tool is
	 * utilized. See SysMLinJava.com for more information.
	 */
	protected List<SysMLPort> virtualConnectedPortsPeers;

	/**
	 * Optional SysML state machine for this SysML port. The
	 * {@code SysMLStateMachine} may be declared as "asynchronous" in which case
	 * it's {@code Runnable} will be automatically executed in one of the threads
	 * provided by the {@code concurrentExecutionThreads} declared below. Note the
	 * {@code StateMachine} annotation is used by SysMLinJava tools to identify this
	 * field as the item's state machine declaration.
	 */
	@StateMachine
	public Optional<? extends SysMLStateMachine> stateMachine;

	/**
	 * Instance of the Java API's {@code ScheduledThreadPoolExecutor} used to "run"
	 * the optional state machine's {@code Runnable}. It is also available to
	 * {@code SysMLPort} extensions to execute other threads of execution that might
	 * be needed for the port.
	 */
	public ScheduledThreadPoolExecutor concurrentExecutionThreads;

	/**
	 * Transmitter of UDP datagrams for a port configured for inter-process
	 * connection, i.e. the port and its connected port peers are in different
	 * {@code PartContainer}s. The udpTransmitter is created automatically by the
	 * constructor when the constructor is invoked with a specified IP address and
	 * UDP port.
	 */
	protected Optional<UDPTransmitter> udpTransmitter;
	/**
	 * Receiver of UDP datagrams for port configured for inter-process connection,
	 * i.e. the port and its connected port peers are in different
	 * {@code PartContainer}s. The udpReceiver is created automatically by the
	 * constructor when the constructor is invoked with a IP address of
	 * {@code localhost} and a specified UDP port.
	 */
	protected Optional<UDPReceiver> udpReceiver;
	/**
	 * IP address for this port when configured for inter-process connection, i.e.
	 * the port and its connected port peers are in different
	 * {@code PartContainer}s.
	 */
	protected Optional<InetAddress> ipAddress;
	/**
	 * UDP port number for this port configured for inter-process connection, i.e.
	 * the port and its connected port peers are in different
	 * {@code PartContainer}s.
	 */
	protected Optional<Integer> udpPort;

	/**
	 * Optional implementation of the {@code InteractionMessageUtility} interface.
	 * If present, the {@code messageUtility} is invoked for every message
	 * ({@code SysMLSignal}) that is transmitted by this port.
	 * 
	 * @see InteractionMessageUtility
	 */
	public Optional<InteractionMessageUtility> messageUtility;

	/**
	 * Constructor with contextPart, index, and name specification. Note this
	 * construtor is for a port that does not submit its received signals via signal
	 * event to the part's state machine. Use one of the costructors below for this
	 * capability.
	 * 
	 * @param context The {@code SysMLPart} or {@code SysMLPort} implmenting a
	 *                {@code StateBehaviorContext} which provides the context for
	 *                (contains) this port, i.e. the part or port whose features may
	 *                need to be accessed by this port.
	 * @param id      An arbitrary index to be associated with this port. For
	 *                example, an array index for an array of ports.
	 */
	public SysMLPort(StateBehaviorContext context, Long id)
	{
		super(SysMLPort.class.getSimpleName(), id);
		this.context = Optional.of(context);
		eventContext = Optional.empty();
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		messageUtility = Optional.empty();
		ipAddress = Optional.empty();
		udpPort = Optional.empty();
		udpTransmitter = Optional.empty();
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();
		createFeatures();
	}

	/**
	 * Constructor with contextPart, index, and name specification. Note this
	 * construtor is for a port that does not submit its received signals via signal
	 * event to the part's state machine. Use one of the costructors below for this
	 * capability.
	 * 
	 * @param context The {@code SysMLPart} or {@code SysMLPort} implmenting a
	 *                {@code StateBehaviorContext} which provides the context for
	 *                (contains) this port, i.e. the part or port whose features may
	 *                need to be accessed by this port.
	 * @param id      An arbitrary ID to be associated with this port. For example,
	 *                an index into an array of ports.
	 * @param name    name to be associated with this instance of the
	 *                {@code SysMLPort}.
	 */
	public SysMLPort(StateBehaviorContext context, Long id, String name)
	{
		super(name, id);
		this.context = Optional.of(context);
		eventContext = Optional.empty();
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		messageUtility = Optional.empty();
		ipAddress = Optional.empty();
		udpPort = Optional.empty();
		udpTransmitter = Optional.empty();
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();
		createFeatures();
	}

	/**
	 * Constructor with contextPart, eventContextPart, and index specification.
	 * 
	 * @param context      The {@code SysMLPart} or {@code SysMLPort} implmenting a
	 *                     {@code StateBehaviorContext} which provides the context
	 *                     for (contains) this port, i.e. the part or port whose
	 *                     features may need to be accessed by this port.
	 * @param eventContext Optional {@code SysMLPart} or {@code SysMLPort}
	 *                     implmenting a {@code StateBehaviorContext} to which
	 *                     {@code SysMLSignalEvent}s (for receipt of
	 *                     {@code SysMLSignal}s or {@code SysMLAnything}s) are to be
	 *                     submitted. Use this constructor if the port's
	 *                     {@code receive()} operation is to submit received signals
	 *                     to the event context via {@code SysMLSignalEvent}s.
	 * @param id           An arbitrary index to be associated with this port. For
	 *                     example, an index into an array of ports.
	 */
	public SysMLPort(StateBehaviorContext context, Optional<StateBehaviorContext> eventContext, Long id)
	{
		this(context, id, SysMLPort.class.getSimpleName());
		this.eventContext = eventContext;
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		messageUtility = Optional.empty();
		ipAddress = Optional.empty();
		udpPort = Optional.empty();
		udpTransmitter = Optional.empty();
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();
		createFeatures();
	}

	/**
	 * Constructor with maximal specification.
	 * 
	 * @param context      The {@code SysMLPart} or {@code SysMLPort} implmenting a
	 *                     {@code StateBehaviorContext} which provides the context
	 *                     for (contains) this port, i.e. the part or port whose
	 *                     features may need to be accessed by this port.
	 * @param eventContext Optional {@code SysMLPart} or {@code SysMLPort}
	 *                     implmenting a {@code StateBehaviorContext} to which
	 *                     {@code SysMLSignalEvent}s (for receipt of
	 *                     {@code SysMLSignal}s or {@code SysMLAnything}s) are to be
	 *                     submitted. Use this constructor if the port's
	 *                     {@code receive()} operation is to submit received signals
	 *                     to the event context via {@code SysMLSignalEvent}s.
	 * @param id           An arbitrary index to be associated with this port. For
	 *                     example, an index into an array of ports.
	 * @param name         name to be associated with this instance of the
	 *                     {@code SysMLPort}.
	 */
	public SysMLPort(StateBehaviorContext context, Optional<StateBehaviorContext> eventContext, Long id, String name)
	{
		this(context, id, name);
		this.eventContext = eventContext;
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		messageUtility = Optional.empty();
		ipAddress = Optional.empty();
		udpPort = Optional.empty();
		udpTransmitter = Optional.empty();
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();
		createFeatures();
	}

	/**
	 * Constructor with maximal specification for a port that receives
	 * {@code SysMLSignal}s from ports in other processes. This constuctor should
	 * only be used if/when the context part is a "part" of a {@code PartContainer}
	 * which supports parts executing in separate operating system processes.
	 * 
	 * @param context      The {@code SysMLPart} or {@code SysMLPort} implmenting a
	 *                     {@code StateBehaviorContext} which provides the context
	 *                     for (contains) this port, i.e. the part or port whose
	 *                     features may need to be accessed by this port.
	 * @param eventContext Optional {@code SysMLPart} or {@code SysMLPort}
	 *                     implementing a {@code StateBehaviorContext} to which
	 *                     {@code SysMLSignalEvent}s (for receipt of
	 *                     {@code SysMLSignal}s or {@code SysMLAnything}s) are to be
	 *                     submitted. Use this constructor if the port's
	 *                     {@code receive()} operation is to submit received signals
	 *                     to the event context via {@code SysMLSignalEvent}s.
	 * @param ipAddress    IP address for this port's host computer.
	 * @param udpPort      UDP port assigned to this port on which to receive
	 *                     {@code SysMLSignal}s in UDP datagrams.
	 * @param id           An arbitrary index to be associated with this port. For
	 *                     example, an index into an array of ports.
	 * @param name         name to be associated with this instance of the
	 *                     {@code SysMLPort}.
	 * @see sysmlinjava.parts.PartContainer
	 */
	public SysMLPort(StateBehaviorContext context, Optional<StateBehaviorContext> eventContext, InetAddress ipAddress, Integer udpPort, Long id, String name)
	{
		super(name, id);
		this.context = Optional.of(context);
		this.eventContext = eventContext;
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		messageUtility = Optional.empty();
		this.ipAddress = Optional.of(ipAddress);
		this.udpPort = Optional.of(udpPort);
		udpTransmitter = Optional.empty();
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();
		createFeatures();
	}

	/**
	 * Constructor for index, and name specification only.
	 * 
	 * @param id   An arbitrary ID to be associated with this port. For example, an
	 *             index into an array of ports.
	 * @param name name to be associated with this instance of the
	 *             {@code SysMLPort}.
	 */
	public SysMLPort(String name, long id)
	{
		super(name, id);
		this.context = Optional.empty();
		this.eventContext = Optional.empty();
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		messageUtility = Optional.empty();
		ipAddress = Optional.empty();
		udpPort = Optional.empty();
		udpTransmitter = Optional.empty();
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();
		createFeatures();
	}

	/**
	 * Starts the port's state machine behavior, if a state machine is present. The
	 * operation submits an {@code InitialEvent} to the state machine's event queue
	 * to thereby start its execution thread. The state machine will execute in
	 * accordance with the states and transitions that are defined in the class that
	 * extends the {@code SysMLStateMachine}.
	 * <p>
	 * if the port uses the UDP protocol (typically between {@code PartContainer}s)
	 * then the port's {@code UDPReceiver} will also be started.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @see sysmlinjava.ports.SysMLPort.UDPReceiver
	 */
	@Override
	public void start()
	{
		if (stateMachine.isPresent())
			stateMachine.get().start();
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to start");

		if (ipAddress.isPresent() && udpPort.isPresent())
			udpReceiver = Optional.of(new UDPReceiver(udpPort.get(), this));
	}

	/**
	 * Accepts and queues a specified event into the state machine's event queue.
	 * Whereas the event queue is a thread safe object, this method can be called to
	 * inject an event into the state machine from any thread of model execution.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param event event to be queued to the state machine
	 */
	@Override
	public void acceptEvent(SysMLEvent event)
	{
		if (stateMachine.isPresent())
			stateMachine.get().queueEvent(event);
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to accept event: " + event.getClass().getSimpleName());
	}

	/**
	 * Stops the port's state machine-based behavior, if a state machine is present
	 * in the port. The operation simply stops the state machine execution. Note if
	 * this port's state machine is asynchronous, i.e. executes in its own thread,
	 * then this operation imposes a "hard stop" where the thread is canceled
	 * regardless of what state it's in. If the part's state machine is
	 * synchronous, this operation can only queue up a {@code FinalEvent} to the
	 * event queue, on the assumption that the state machine is capable of handling
	 * the {@code FinalEvent} at the point in which the {@code stop()} method is
	 * invoked. Therefore, if a port is synchronous and this stop method is used,
	 * it's state machine should be modeled to handle the {@code FinalEvent}
	 * accordingly.
	 * <p>
	 * If this port uses the UDP protocol for inter-process communications
	 * (typically between {@code PartContainer}s) then the port's
	 * {@code UDPReceiver} will also be stopped.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @see sysmlinjava.ports.SysMLPort.UDPReceiver
	 */
	@Override
	public void stop()
	{
		if (udpReceiver.isPresent())
			udpReceiver.get().stop();

		if (stateMachine.isPresent())
			stateMachine.get().stop();
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to stop");
	}

	/**
	 * Delays the calling thread (sleeps) for the specified seconds of time.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param seconds time to sleep in seconds. Use up to 3 decimal places for
	 *                fractions of a second, i.e. the delay is capable of the
	 *                milliseconds precision provided by java's
	 *                {@code Thread.sleep(<millis>)} operation.
	 */
	@Override
	public void delay(double seconds)
	{
		try
		{
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Receives a signal object. This operation is called by a "peer" port which is
	 * connected to this port providing the specified {@code SysMLSignal}-based
	 * object. The {@code receive()}'s behavior models that of an interaction
	 * protocol between connected ports by simply receiving a signal value and
	 * either submitting a signal event to a state-machine for reception event
	 * handling or submitting the signal contents to another (client) protocol for
	 * further processing.
	 * <p>
	 * If an {@code eventContext} was specified for this port, then the signal is
	 * translated into a {@code SysMLSignalEvent} which is submitted to the
	 * {@code eventContext}'s event queue. If not and a client port was connected to
	 * this port, then the client protocol object is extracted from the signal and
	 * received by the client protocol.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param signal The signal to be received from the "peer" port.
	 */
	public void receive(SysMLSignal signal)
	{
		if (eventContext.isPresent())
		{
			SysMLSignalEvent event = eventFor(signal);
			event.index = id.intValue();
			eventContext.get().acceptEvent(event);
		}
		else if (!connectedPortsClients.isEmpty())
		{
			SysMLAnything object = clientObjectFor(signal);
			connectedPortsClients.forEach(client -> client.receive(object));
		}
		else
			logger.warning("no port clients or event context to receive " + signal.getClass().getSimpleName());
	}

	/**
	 * Receives an object. This operation is called by a "server" port which is
	 * connected to this port providing the specified {@code SysMLAnything}-based
	 * object. The server port typically represents a lower level communications
	 * protocol in a "stack" of protocols.
	 * <p>
	 * If any client ports are connected to this port, then the client protocol
	 * object is extracted from the received object and received by the client
	 * protocols. If not and an {@code eventContext} was specified for this port,
	 * then the object is inserted into a {@code SysMLEvent} which is submitted to
	 * the {@code eventContext}'s event queue.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param object The object to be received by this "client" port from the
	 *               calling "server" port.
	 */
	public void receive(SysMLAnything object)
	{
		if (!connectedPortsClients.isEmpty())
		{
			SysMLAnything clientObject = clientObjectFor(object);
			for (SysMLPort client : connectedPortsClients)
			{
				logger.info(String.format("[SEQ] %s[%d] >> %s >> %s[%d]", this.getClass().getSimpleName(), this.id, clientObject.getClass().getSimpleName(), client.getClass().getSimpleName(), client.id));
				client.receive(clientObject);
			}
		}
		else if (eventContext.isPresent())
		{
			SysMLEvent event = eventFor(object);
			eventContext.get().acceptEvent(event);
		}
		else
			logger.warning("no port clients or event context to receive " + object.getClass().getSimpleName());
	}

	/**
	 * Transmits an object. This operation is called to transmit the specified
	 * {@code SysMLAnything}-based object to "server" or "peer" ports to which this
	 * port is connected. The transmission will be to any and all "peer" ports, if
	 * any are connected. But if none are, the transmission will be to any and all
	 * "server" ports, if any are connected.
	 * <p>
	 * If a peer-level protocol port was connected to this port, then the object to
	 * be transmitted is embedded in a {@code SysMLSignal} and transmitted to the
	 * connected peer port (typically a port in another part). If not and a server
	 * protocol (typically a lower level protocol in the protocol "stack" in the
	 * same prt) was specified for this port, then the object is inserted into the
	 * server's protocol object and transmitted to the server port for further
	 * processing and transmission in the "stack".
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param object The object to be transmitted to the "server" or "peer" port.
	 */
	public void transmit(SysMLAnything object)
	{
		if (!connectedPortsPeers.isEmpty())
		{
			SysMLSignal signal = signalFor(object);
			for (SysMLPort peer : connectedPortsPeers)
				try
				{
					messageUtility.ifPresent(utility ->
					{
						utility.perform(Instant.now(), context.get(), signal, peer, logger);
					});

					if (!udpTransmitter.isPresent() && !peer.ipAddress.isPresent() && !peer.udpPort.isPresent())
					{
						peer.receive(signal);
					}
					else if (udpTransmitter.isPresent() && peer.ipAddress.isPresent() && peer.udpPort.isPresent())
						udpTransmitter.get().transmit(signal, peer.ipAddress.get(), peer.udpPort.get());
					else
						logger.severe(String.format("one or more elements for UDP connection are missing: udpTransmitter? %s, ipAddress? %s, udpPort? %s", !udpTransmitter.isPresent(), !peer.ipAddress.isPresent(), !peer.udpPort.isPresent()));
				} catch (NullPointerException e)
				{
					e.printStackTrace();
				}
		}
		else if (!connectedPortsServers.isEmpty())
		{
			SysMLAnything serverObject = serverObjectFor(object);
			for (SysMLPort server : connectedPortsServers)
			{
				String thisIndexString = String.format("[%d]", this.id);
				String serverIndexString = String.format("[%d]", server.id);
				logger.info(String.format("[SEQ] %s%s >> %s >> %s%s", this.getClass().getSimpleName(), thisIndexString, serverObject.getClass().getSimpleName(), server.getClass().getSimpleName(), serverIndexString));
				server.transmit(serverObject);
			}
		}
		else
			logger.warning("no port peers or port servers to transmit " + object.getClass().getSimpleName());
	}

	/**
	 * Adds a port to the collection of ports that are to operate as "clients" to
	 * this port, i.e. they are "above" this port in a protocol "stack" of ports and
	 * this port will be receiving objects from this port to be encapsulated into
	 * this port's protocol and transmitted to a server port or to a peer port; and
	 * this port will be receiving objects from a server port or from a peer port
	 * and decapsulating them from this port's protocl and transmitting the objects
	 * to the client port.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param client port to be the client port to this port. The client should be
	 *               declared as a java field access variable (a.k.a "nested element
	 *               path" in SysML) with scope element names as needed. An example
	 *               of how to invoke/call this method is as follows:
	 * 
	 *               <pre>{@code
		elementA.nestedElementA1.connectToClientPort(elementA.nestedElementA2);
	 }
	 * </pre>
	 */
	public void connectToClientPort(SysMLPort client)
	{
		connectedPortsClients.add(client);
	}

	/**
	 * Adds a port at an indexed location in the collection of ports that are to
	 * operate as "clients" to this port, i.e. they are "above" this port in a
	 * protocol "stack" of ports and this port will be receiving objects from this
	 * port to be encapsulated into this port's protocol and transmitted to a server
	 * port or to a peer port; and this port will be receiving objects from a server
	 * port or from a peer port and decapsulating them from this port's protocl and
	 * transmitting the objects to the client port.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param index  index in the collection of client ports at which this client is
	 *               to be located
	 * @param client port to be the client port to this port. The client should be
	 *               declared as a java field access variable (a.k.a "nested element
	 *               path" in SysML) with scope element names as needed.
	 */
	public void connectToClientPort(int index, SysMLPort client)
	{
		connectedPortsClients.add(index, client);
	}

	/**
	 * Adds a port to the collection of ports that are to operate as "servers" to
	 * this port, i.e. they are "below" this port in a protocol "stack" of ports and
	 * this port will be receiving objects from this server port to be decapsulated
	 * from this port's protocol and transmitted to a client or to another part; and
	 * this port will be receiving objects from a client port or from another part
	 * and encapsulating them for this port's protocl and transmitting the objects
	 * to the server port.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param server port to be the server port to this port. The server should be
	 *               declared as a java field access variable (a.k.a "nested element
	 *               path" in SysML) with scope element names as needed.
	 */
	public void connectToServerPort(SysMLPort server)
	{
		connectedPortsServers.add(server);
	}

	/**
	 * Adds a port at the specified index to the collection of ports that are to
	 * operate as "servers" to this port, i.e. they are "below" this port in a
	 * protocol "stack" of ports and this port will be receiving objects from this
	 * server port to be decapsulated from this port's protocol and transmitted to a
	 * client or to another part; and this port will be receiving objects from a
	 * client port or from another part and encapsulating them for this port's
	 * protocl and transmitting the objects to the server port.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param server port to be the server port to this port. The server should be
	 *               declared as a java field access variable (a.k.a "nested element
	 *               path" in SysML) with scope element names as needed.
	 * @param index  index in list of connected port servers at which to add
	 */
	public void connectToServerPort(int index, SysMLPort server)
	{
		connectedPortsServers.add(index, server);
	}

	/**
	 * Adds a port to the collection of ports that are to operate as "peers" to this
	 * port, i.e. they are "peers" (at the same level) to this port in a protocol
	 * "stack" of ports. This port will be receiving objects from this peer port to
	 * be decapsulated from this port's protocol and transmitted to a client or to
	 * another part; and this port will be receiving objects from a client port or
	 * from another part and encapsulating them for this port's protocol and
	 * transmitting the objects to the peer port.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param peer port (likely in another part) to be the peer port to this port.
	 *             The peer should be declared as a java field access variable
	 *             (a.k.a "nested element path" in SysML) with scope element names
	 *             as needed.
	 */
	public void connectToPeerPort(SysMLPort peer)
	{
		connectedPortsPeers.add(peer);
		if (peer.ipAddress.isPresent() && peer.udpPort.isPresent() && udpTransmitter.isEmpty())
			udpTransmitter = Optional.of(new UDPTransmitter());
	}

	/**
	 * Adds a port to the collection of ports that are to operate as "peers" to this
	 * port, i.e. they are "peers" (at the same level) to this port in a protocol
	 * "stack" of ports and this port will be receiving objects from this peer port
	 * to be decapsulated from this port's protocol and transmitted to a client or
	 * to another part; and this port will be receiving objects from a client port
	 * or from another part and encapsulating them for this port's protocl and
	 * transmitting the objects to the peer port.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param index index in the collection of peer ports at which this peer is to
	 *              be located
	 * @param peer  port (likely in another part) to be the peer port to this port.
	 *              The peer should be declared as a java field access variable
	 *              (a.k.a "nested element path" in SysML) with scope element names
	 *              as needed.
	 */
	public void connectToPeerPort(int index, SysMLPort peer)
	{
		connectedPortsPeers.add(index, peer);
		if (peer.ipAddress.isPresent() && peer.udpPort.isPresent() && udpTransmitter.isEmpty())
			udpTransmitter = Optional.of(new UDPTransmitter());
	}

	/**
	 * Adds a port to the collection of ports that are to operate as "virtual peers"
	 * to this port, i.e. they are "peers" (at the same level) to this port in a
	 * protocol "stack" for virtual connection between the ports.
	 * <p>
	 * This connection of peer ports represents a connection between peers for
	 * modeling purposes only and will not be used for any signal transmit or
	 * receive operations. Only a normal {@code connectedPortPeer} can be used for
	 * that, i.e. while the peer ports are virtually connected, actual execution of
	 * the peer-to-peer protocol may be performed via lower level protocols in
	 * layers or "stack" of protocols. SysMLinJava uses the virtual connected port
	 * peer for modeling purposes only to specify a virtual connection, e.g. to
	 * identify an interface requirement, and has no role in model execution. Of
	 * course, extended classes may override the receive and transmit operations of
	 * the full port to assign the virtual connection with another role as desired.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param peer port (likely in another part) to be the virtually connected peer
	 *             port to this port. The peer should be declared as a java field
	 *             access variable (a.k.a "nested element path" in SysML) with scope
	 *             element names as needed.
	 */
	public void virtualConnectToPeerPort(SysMLPort peer)
	{
		virtualConnectedPortsPeers.add(peer);
	}

	/**
	 * Adds a port to the collection of ports that are to operate as "virtual peers"
	 * to this port, i.e. they are "peers" (at the same level) to this port in a
	 * protocol "stack" for virtual connection between the ports.
	 * <p>
	 * This connection of peer ports represents a connection between peers for
	 * modeling purposes only and will not be used for any signal transmit or
	 * receive operations. Only a normal {@code connectedPortPeer} can be used for
	 * that, i.e. while the peer ports are virtually connected, actual execution of
	 * the peer-to-peer protocol may be performed via lower level protocols in
	 * layers or "stack" of protocols. SysMLinJava uses the virtual connected port
	 * peer for modeling purposes only to specify a virtual connection, e.g. to
	 * identify an interface requirement, and has no role in model execution. Of
	 * course, extended classes may override the receive and transmit operations of
	 * the full port to assign the virtual connection with another role as desired.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param index index in the collection of peer ports at which this peer is to
	 *              be located
	 * @param peer  port (likely in another part) to be the virtually connected
	 *              peer port to this port. The peer should be declared as a java
	 *              field access variable (a.k.a "nested element path" in SysML)
	 *              with scope element names as needed.
	 */
	public void virtualConnectToPeerPort(int index, SysMLPort peer)
	{
		virtualConnectedPortsPeers.add(peer);
	}

	/**
	 * Returns the "peer" port located at the indexed location in the collecion of
	 * peer ports
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param index index in the collection of peer ports at which this peer is
	 *              located
	 * @return the indexed peer port
	 */
	public SysMLPort getConnectedPeerPort(int index)
	{
		return connectedPortsPeers.get(index);
	}

	/**
	 * Returns the "server" port located at the indexed location in the collecion of
	 * server ports
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param index index in the collection of server ports at which this server is
	 *              located
	 * @return the indexed server port
	 */
	public SysMLPort getConnectedServerPort(int index)
	{
		return connectedPortsServers.get(index);
	}

	/**
	 * Returns the "client" port located at the indexed location in the collecion of
	 * client ports
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param index index in the collection of client ports at which this client is
	 *              located
	 * @return the indexed client port
	 */
	public SysMLPort getConnectedClientPort(int index)
	{
		return connectedPortsClients.get(index);
	}

	@Override
	public String getIdentityString()
	{
		return identityString();
	}

	@Override
	public ScheduledThreadPoolExecutor getExecutionThreads()
	{
		return concurrentExecutionThreads;
	}

	@Override
	public Optional<? extends SysMLStateMachine> getStateMachine()
	{
		return stateMachine;
	}

	/**
	 * Overridable operation to transform the specified object into a
	 * {@code SysMLEvent}-based event.<br>
	 * <b>Note:</b>Extensions of {@code SysMLPort} <b>must</b> override and
	 * implement this operation if this port is a "client" port that receives
	 * {@code SysMLAnything}-based objects from "server" ports and generates
	 * {@code SysMLEvent}s for the {@code eventContextPart} that contains the
	 * objects for the {@code eventContextPart}.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param object the received {@code SysMLAnything}-based object such as an item
	 *               or attriribute.
	 * @return {@code SysMLEvent} for the object.
	 */
	protected SysMLEvent eventFor(SysMLAnything object)
	{
		return null;
	}

	/**
	 * Overridable operation to transform the object in the specified signal into a
	 * {@code SysMLSignalEvent}-based event.<br>
	 * <b>Note:</b>Extensions of {@code SysMLPort} <b>must</b> override and
	 * implement this operation if this port is a peer-level protocol port that
	 * receives {@code SysMLSignal}-based signals from peer-level port(s) and
	 * generates {@code SysMLSignalEvent}s that contain the objects contained by the
	 * signals for the {@code eventContextPart}.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param signal The {@code SysMLSignal}-based signal received from the "peer"
	 *               port.
	 * @return {@code SysMLSignalEvent}-based event that contains the objects from
	 *         the signal.
	 */
	protected SysMLSignalEvent eventFor(SysMLSignal signal)
	{
		return null;
	}

	/**
	 * Overridable operation to transform (decapsulate) the specified lower-level
	 * protocol object into a upper-level protocol object.<br>
	 * <b>Note:</b>Extensions of {@code SysMLPort} <b>must</b> override and
	 * implement this operation if this port is a "client" port for a lower-level
	 * protocol that receives {@code SysMLAnything}-based objects from a "server"
	 * port for an upper-level protocol and decapsulates them for an opper-level
	 * protocol.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param serverObject The {@code SysMLAnything}-based object received from the
	 *                     lower-level protocol. @return {@code SysMLAnything}-based
	 *                     object, such as an item or attribute, for the upper-level
	 *                     protocol that decapsulates the serverObject.
	 * @return {@code SysMLAnything}-based object, such as an item or attribute,
	 *         decapsulated from the {@code serverObject}
	 */
	protected SysMLAnything clientObjectFor(SysMLAnything serverObject)
	{
		return null;
	}

	/**
	 * Overridable operation to transform the object in the specified signal into a
	 * {@code SysMLAnything}-based object.<br>
	 * <b>Note:</b>Extensions of {@code SysMLPort} <b>must</b> override and
	 * implement this operation if this port is a peer-level protocol port that
	 * receives SysML-based signals from peer-level port(s) and extracts
	 * {@code SysMLAnything}-based objects from the signal for transmission to
	 * "client" ports.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param signal The {@code SysMLSignal}-based signal received from the "peer"
	 *               port. @return {@code SysMLAnything}-based object extracted from
	 *               the signal.
	 * @return {@code SysMLAnything}-based object, such as an item or attribute,
	 *         retrieved from the {@code signal}
	 */
	protected SysMLAnything clientObjectFor(SysMLSignal signal)
	{
		return null;
	}

	/**
	 * Overridable operation to transform (encapsulate) the specified upper-level
	 * protocol object into a {@code SysMLSignal}-based signal.<br>
	 * <b>Note:</b>Extensions of {@code SysMLPort} <b>must</b> override and
	 * implement this operation if this port is a "server" port that receives
	 * {@code SysMLAnything}-based objects from a "client" port for an upper-level
	 * protocol or from another {@code SysMLPart} object and encapsulates them into
	 * a SysML-based signal for transmission to a peer-level protocol port.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param object The {@code SysMLAnything}-based object, such as an item or
	 *               attribute, received from the upper-level protocol or other
	 *               part. @return {@code SysMLSignal}s-based signal that
	 *               encapsulates the object.
	 * @return {@code SysMLSignal}-based signal that is to contain the specified
	 *         {@code object}
	 */
	protected SysMLSignal signalFor(SysMLAnything object)
	{
		return null;
	}

	/**
	 * Overridable operation to transform the specified upper-level protocol object
	 * into a lower-level protocol object.<br>
	 * <b>Note:</b>Extensions of {@code SysMLPort} <b>must</b> override and
	 * implement this operation if this port is a "server" port that receives
	 * {@code SysMLAnything}-based objects from a "client" port for an upper-level
	 * protocol and encapsulates them for a lower-level protocol.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param clientObject The {@code SysMLAnything}-based object received from the
	 *                     upper-level protocol. @return {@code SysMLAnything}-based
	 *                     object for the lower-level protocol that encapsulates the
	 *                     clientObject.
	 * @return {@code SysMLAnything}-based object, such as an item or attribute,
	 *         that encapsulates the specified {@code clientObject}
	 */
	protected SysMLAnything serverObjectFor(SysMLAnything clientObject)
	{
		return null;
	}

	/**
	 * Creates/initializes features of the port, This method presumes a correct
	 * sequence in which the features are to be created/initialized. The method can
	 * be overridden to invoke a different sequence if needed.
	 */
	protected void createFeatures()
	{
		createAttributes();
		createItems();
		createParts();
		createPorts();
		createEvents();
		createStateMachine();
		createSpatialExtent();
		createActionFunctions();
		createActions();
		createConstraintFunctions();
		createConstraintTexts();
		createConstraints();
		createDependencies();
		createCustomMetadatas();
		createRequirements();
		createInteractionMessageUtility();
	}

	/**
	 * Overridable operation that creates/initializes the part's state machine, if
	 * any. (The default is for the part to have no state machine.) The state
	 * machine is declared as a variable of a (@code SysMLStateMachine} extension
	 * type annotated with &#64;StateMachine. The state machine is
	 * created/intialized in an override of the {@code createStateMachine()} method.
	 * An example is as follows:
	 * 
	 * <pre>{@code
		public class MyItem extends SysMLItem
		{
				:
			&#64;StateMachine
			MyItemsStateMachine stateMachine;
				:
			&#64;Override
			protected void createStateMachine()
			{
				stateMachine = new MyItemsStateMachine(this);
			}
				:
		}}</pre>
	 * 
	 * @see sysmlinjava.states.SysMLStateMachine
	 */
	protected void createStateMachine()
	{
		stateMachine = Optional.empty();
	}

	protected void createAttributes()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's input items,
	 * output items, and sub-items. An example is as follows:
	 * 
	 * <pre>
		&#64;Item
		ItemTypeA myFirstItem;
		&#64;Item
		ItemTypeA myNextItem;
		&#64;ItemIn
		ItemTypeA myFirstItemIn;
		&#64;ItemOut
		ItemTypeA myNextItemOut;
	
		&#64;Override
		protected void createItems()
		{
			myFirstItem = new ItemTypeA(&lt;initializer&gt;);
			myNextItem = new ItemTypeB(&lt;initializer&gt;);
			myFirstItemIn = new ItemTypeA(&lt;initializer&gt;);
			myNextItemOut = new ItemTypeB(&lt;initializer&gt;);
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.items.SysMLItem
	 */
	protected void createItems()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's parts. An
	 * exampe is as follows:
	 * 
	 * <pre>
		&#64;Part
		PartTypeA myFirstPart;
		&#64;Part
		PartTypeA myNextPart;
	
		&#64;Override
		protected void createParts()
		{
			myFirstPart = new PartTypeA(&lt;initializer&gt;);
			myNextPart = new PartTypeB(&lt;initializer&gt;);
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.parts.SysMLPart
	 */
	protected void createParts()
	{
	}

	protected void createPorts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's performed
	 * actions. An example is as follows:
	 * 
	 * <pre>{@code
		public class SWAP extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;ActionFunction
			TotalPowerFunction getTotalPowerFunction;
			&#64;ActionFunction
			TotalWeightFunction getTotalWeightFunction;
	
			&#64;Action
			SysMLAction getTotalPower;
			&#64;Action
			SysMLAction getTotalWeight;
	
			&#64;FunctionalInterface
			public interface TotalPowerFunction extends ActionFunction
			{
				PowerWatts = totalPower();
			};
	
			&#64;FunctionalInterface
			public interface TotalWeightFunction extends ActionFunction
			{
				WeightPounds totalWeight(WeightPounds componentsWeight, WeightPounds containerWeight);
			};
				:
			void createActionFunctions()
			{
				getTotalPowerFunction = () -> totalPower = electricalPower + mechanicalPower;
				getTotalWeightzFunction = (componentsWeight, containerWeight) -> { return componentsWeight.added(containerWeight); });
			}
				:
			void createActions()
			{
				getTotalPower = new SysMLAction(getTotalPowerFunction);
				getTotalWeight = new SysMLAction(getTotalWeightFunction);
			}
		}}</pre>
	 * <p>
	 * The actions are declared as field variables annotated with the
	 * {@code &#64;Action} annotation. Each action is created/initialized using an
	 * object creation statement whose type is a {@code SysMLAction} and whose
	 * argument specifies a lambda expression that is an implementation of a
	 * functional interface that is an extension of the {@code SysMLAction}'s
	 * {@code SysMLActionBody} interface. When declared in this way, the lambda
	 * expression will have access to all variables and methods in the containing
	 * class, as well as to any arguments it may declare. In any case, the extended
	 * {@code SysMLActionBody} interface must declare the method that is to be
	 * called to invoke the lambda function.
	 * 
	 * @see sysmlinjava.actions.SysMLAction
	 */
	protected void createActionFunctions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's actions (actins
	 * declared in a field variable). An example is as follows:
	 * 
	 * <pre>{@code
		public class SWAP extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;ActionFunction
			TotalPowerFunction getTotalPowerFunction;
			&#64;Action
			TotalWeightFunction getTotalWeightFunction;
	
			&#64;Action
			SysMLAction getTotalPower;
			&#64;Action
			SysMLAction getTotalWeight;
	
			&#64;FunctionalInterface
			public interface TotalPowerFunction extends SysMLActionFunction
			{
				PowerWatts = totalPower();
			};
	
			&#64;FunctionalInterface
			public interface TotalWeightFunction extends SysMLActionFunction
			{
				WeightPounds totalWeight(WeightPounds componentsWeight, WeightPounds containerWeight);
			};
				:
			void createActionFunctions()
			{
				getTotalPowerFunction = () -> totalPower = electricalPower + mechanicalPower;
				getTotalWeightzFunction = (componentsWeight, containerWeight) -> { return componentsWeight.added(containerWeight); });
			}
				:
			void createActions()
			{
				getTotalPower = new SysMLAction(getTotalPowerFunction);
				getTotalWeight = new SysMLAction(getTotalWeightFunction);
			}
		}}</pre>
	 * <p>
	 * Note that actions can also be defined by &#64;{@code Action}-annotated
	 * standard Java methods in the part or port class that performs the action.
	 * Whereas these types of action are defined as standard Java methods, they need
	 * not be "created/initialized" in this or any other {@code create...()} method.
	 * The body of the method defines the action function for the action that is
	 * defined by the method. An example follows:
	 * 
	 * <pre>{@code
		public class SWAP extends SysMLPart
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;Action
			public void getTotalPower()
			{
				totalPower = electricalPower + mechanicalPower;
			}
				:
			&#64;Action
			public WeightPounds getTotalWeight(WeightPounds componentsWeight, WeightPounds containerWeight)
			{
				 return componentsWeight.added(containerWeight);
			}
				:
		}}</pre>
	 * 
	 * @see sysmlinjava.javaannotations.actions.Action
	 * @see sysmlinjava.actions.SysMLAction
	 */
	protected void createActions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's spatial extent.
	 * An example follows:
	 * 
	 * <pre>{@code
		public class BigPipe extends SysMLPart
		{
			&#64;SpatialExtent
			SysMLSpatialExtent spatialExtent;
				:
			&#64Override;
			protected void createSpatialExtent()
			{
				spatialExtent = new Cylinder(new Point(0, 0, 0), new Vector(0, 0, 0), 5, 250);
			}
				:
		}}</pre>
	 * 
	 * @see sysmlinjava.items.SysMLSpatialExtent.Box
	 * @see sysmlinjava.items.SysMLSpatialExtent.Cylinder
	 * @see sysmlinjava.items.SysMLSpatialExtent.Sphere
	 * @see sysmlinjava.items.SysMLSpatialExtent.Mesh
	 */
	protected void createSpatialExtent()
	{
	}

	/**
	 * Overridable operation that creates and initializes the port's declared
	 * events.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		&#64;TimeEvent
		SysMLTimeEvent myTimeEvent;
		&#64;CallEvent
		MyCallEvent myCallEvent;
	
		protected void createEvents()
		{
		   myTimeEvent = new SysMLTimeEvent(&lt;initializer&gt;);
		   myCallEvent = new MyCallEvent(&lt;initializer&gt;);
		}
	 * </pre>
	 * <p>
	 * where the the targets of the assignment operations are the names of fields
	 * with the {@code &#64;TimeEvent} and {@code &#64;CallEvent} annotations and
	 * {@code new SysMLTimeEvent} and {@code MyCallEvent} are constructors of a
	 * specific time event and an extended/specialized call event, respectively.
	 * <p>
	 * <b>Note</b> this operation is likely seldom used as events are typically
	 * instantiated when the event occurs rather than as part of part construction -
	 * the exception being the {@code SysMLTimeEvent}, which is used to start timers
	 * as well as to indicate timer expiration.
	 * 
	 * @see sysmlinjava.events.SysMLEvent
	 * @see sysmlinjava.events.SysMLCallEvent
	 * @see sysmlinjava.events.SysMLChangeEvent
	 * @see sysmlinjava.events.SysMLCompletionEvent
	 * @see sysmlinjava.events.SysMLSignalEvent
	 * @see sysmlinjava.events.SysMLTimeEvent
	 */
	protected void createEvents()
	{
	}

	/**
	 * Overridable operation that creates and initializes the constraint function
	 * (lambda expressiuns) for the part's constraints. An example is as follows:
	 * 
	 * <pre>{@code
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;Attribute
			int totalWeight;
			&#64;Attribute
			int componentWeight;
			&#64;Attribute
			int containerWeight;
	
			&#64;FunctionalInterface
			public interface NextConstraint extends SysMLAction.ActionBody
			{
				int totalWeight(int waterWeight, int containerWeight);
			}
	
			&#64;ConstraintFunction
			BasicConstraintFunction totalPowerOKFunction;
			&#64;ConstraintFunction
			NextConstraint totalWeightFunction;
	
			void createConstraintFunctions()
			{
				totalPowerOKFunction = (BasicConstraintFunction)() -> return totalPower > electricalPower + mechanicalPower);
				totalWeightFunction = (NextConstraint)(componentsWeight, containerWeight) -> { return componentsWeight + containerWeight;});
			}
		}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraint
	 */
	protected void createConstraintFunctions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the constraint texts
	 * (documentations) for the part's constraints. An example is as follows:
	 * 
	 * <pre>{@code
		{
			&#64;Attribute
			int totalPower;
			&#64;Attribute
			int electricalPower;
			&#64;Attribute
			int mechanicalPower;
	
			&#64;Attribute
			int totalWeight;
			&#64;Attribute
			int componentWeight;
			&#64;Attribute
			int containerWeight;
	
			&#64;ConstraintText
			SysMLDocumentation totalPowerOKDoc;
			&#64;ConstraintText
			SysMLConstraintText totalWeightDoc;
	
			void createConstraintTexts()
			{
				totalPowerOKDoc = new SysMLDocumentation("totalPower > electricalPower + mechanicalPower");
				totalWeightDoc = new SysMLDocumentation("totalWeight = componentsWeight + containerWeight");
			}
		}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraint
	 */
	protected void createConstraintTexts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's constraints.
	 * <p>
	 * Code format:
	 * 
	 * <pre>{@code
		{
			&#64;ConstraintText
			SysMLDocumentation totalPowerOKDoc;
			&#64;ConstraintText
			SysMLDocumentation totalWeightDoc;
	
			&#64;ConstraintFunction
			SysMLConstraintFunction totalPowerOKFunction;
			&#64;ConstraintFunction
			SysMLConstraintFunction totalWeightFunction;
	
			&#64;Constraint
			SysMLConstraint totalPowerOK;
			&#64;Constraint
			SysMLConstraint totalWeight;
	
			void createConstraints()
			{
				totalPowerOK = new SysMLConstraint(Optional.of(totalPowerOKFunction), totalPowerOKDoc, "TotalPowerOK", 0L);
				totalWeight = new SysMLConstraint(Optional.of(totalWeightFunction), totalWeightDoc, TotalWeight", oL });
			}
		}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraintFunction
	 * @see sysmlinjava.annotations.SysMLDocumentation
	 */
	protected void createConstraints()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's dependencies,
	 * if any. An example follows:
	 * 
	 * <pre>
		{
			&#64;Dependency
			SysMLDependency usage;
				:
			protected void createDependencies()
			{
				usage = new SysMLDependency(Optional.of(this.getClass()), SensorA.class);
			}
		}
	 * </pre>
	 */
	protected void createDependencies()
	{
	}

	/**
	 * Overridable operation that creates and initializes custom item metadata, i.e.
	 * metadata that is not one of the standard SysaML metadata types. (Standard
	 * metadata elements should be created in overrides of {@code create...()}
	 * methods in the {@code SysMLAnything} class. An example of creating custom
	 * metadata instances is as follows:
	 * 
	 * <pre>
		{
			&#64;Metadata
			SWAPMetadata swap;
				:
			protected void createCustomMetadata()
			{
				swap = new SWAPMetadata(25, 15, 15);
			}
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.javaannotations.metadata.Metadata
	 * @see sysmlinjava.metadata.SysMLMetadata
	 */
	protected void createCustomMetadatas()
	{
	}

	/**
	 * Overridable operation that specifies the port's requirements. Requirements
	 * declared in this class should be created/initialized with variable names of
	 * requirements specified in a specialization of the
	 * {@code SysMLRequirementsCollection}. Requirements should <b>not</b> be
	 * specified in the port itself. Requirement specifications in the typical SysML
	 * model are collected in a model package where they can be easily queried and
	 * managed. The SysMLinJava approach of collecting requirements in a single
	 * class emulates this method. An example follows:
	 * 
	 * <pre>
		&#64;Requirement
		SysMLRequirement myFirstRequirement;
		&#64;Requirement
		SysMLRequirement myNextRequirement;
			:
		protected void createRequirements()
		{
			myFirstRequirement = MySysMLRequirements.firstSysMLRequirement;
			myNextRequirement = MySysMLRequirements.nextSysMLRequirement;
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.javaannotations.requirements.Requirement
	 * @see sysmlinjava.requirements.SysMLRequirement
	 * @see sysmlinjava.requirements.SysMLRequirementsCollection
	 */

	protected void createRequirements()
	{
	}

	/**
	 * Overridable operation for which the overriding operation can optionally
	 * create/initialize the {@code messageUtility} field variable. If the variable
	 * is present during execution, it will be used to invoke the operation of the
	 * {@code InteractionMessageUtility} interface.
	 * <p>
	 * Note it is usually better to set the {@code messageUtility} in the
	 * {@code enableInteractionMessageTransmissions()} method of a part that
	 * contains this port (or part that contains a part or port that contains this
	 * port, etc) as multiple ports can share/utilize the same instance of the
	 * {@code InteractionMessageUtility}.
	 * 
	 * @see sysmlinjava.ports.InteractionMessageUtility
	 */
	protected void createInteractionMessageUtility()
	{
	}

	/**
	 * The {@code UDPReceiver} is a {@code Runnable} class that receives objects
	 * from {@code UDPTransmitter}s via the User Datagram Protocol (UDP). It is used
	 * by {@code SysMLPort} instances to execute interactions betwen ports that
	 * reside in separate {@code PartContainer}s, i.e. in separate JVM processes.
	 * <p>
	 * The {@code SysMLPort} is configured for inter-process port interactions by
	 * simply using the constructor that specifies the port's internet address and
	 * UDP port number. This constructor will instantiate the {@code UDPReceiver} to
	 * receive and/or transmit signals via the UDP protocol from/to a "remote"
	 * full-port. The full-port must also be "connected" to the remote port via a
	 * call to the {@code addConnectedPortPeer()} method where the port to be added
	 * is an instance of a {@code SysMLPort} that is likewise instantiated with an
	 * internet address and UDP port number. See the {@code PartContainer} for more
	 * details.
	 * <p>
	 * Modelers typically will not need to reference the {@code UDPReceiver} in any
	 * way as its instantiation and operation are automatically configured when the
	 * {@code SysMLPort} is extended and constructed in this way.
	 * 
	 * @author ModelerOne
	 */
	public class UDPReceiver implements Runnable
	{
		/**
		 * Logger for this UDPReceiver
		 */
		protected Logger logger;
		/**
		 * Full port of which this receiver is a part
		 */
		public SysMLPort port;
		/**
		 * Port on which the receiver operates, i.e. socket uses to read datagrams
		 */
		public int udpPort;
		/**
		 * Socket on which the receiver receives datagrams
		 */
		public DatagramSocket publicSocket;
		/**
		 * Future for run() operation that is asynchronously receiving signals from
		 * ports in other processes.
		 */
		public Optional<Future<?>> runner;

		/**
		 * Constant value for the size of the byte buffer used to receive UDP packets
		 * containing the objects transmitted between ports in separate processes
		 */
		public static final int byteBufferSize = 100_000;

		/**
		 * Constructor
		 * 
		 * @param udpPort UDP port on which the receiver is to receive signal objects
		 * @param port    the port whose {@code receive()} operation is to be invoked to
		 *                receive the signal objects.
		 */
		public UDPReceiver(int udpPort, SysMLPort port)
		{
			super();
			logger = Logger.getLogger(this.getClass().getName());
			this.port = port;
			this.udpPort = udpPort;
			runner = Optional.of(port.concurrentExecutionThreads.submit(this));
			logger.info(String.format("run() submitted for execution for full port %s on udpPort %s", port.identityString(), udpPort));
		}

		/**
		 * Stops (cancels) the receiver thread if not already done and closes the UDP
		 * socket.
		 */
		public void stop()
		{
			if (runner.isPresent() && !runner.get().isDone())
				runner.get().cancel(true);
			publicSocket.close();
		}

		/**
		 * Run operation that simply receives a UDP datagram, streams the datagram's
		 * byte array into an object via an {@code ObjectInputStream}, and invokes the
		 * flow port's {@code receive(Object)} operation to process the received object.
		 * It continues this activity until the run is interrupted by a socket closure.
		 */
		@Override
		public void run()
		{
			logger.info("run() started");
			try (DatagramSocket socket = new DatagramSocket(udpPort))
			{
				logger.info("new DatagramSocket() created on port " + udpPort);
				publicSocket = socket;
				boolean done = false;
				logger.info("receiving packets on DatagramSocket...");
				do
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
								if (readObject instanceof SysMLSignal)
									port.receive((SysMLSignal) readObject);
								else
									logger.severe("unrecognized object type received: " + readObject.getClass().getSimpleName());
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
				while (!done);
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generic class to transmit {@code SysMLSignal}s via the User Datagram Protocol
	 * (UDP) to a UDPReceiver of an other {@code SysMLPort} in another process.
	 * UDPTransmitter transmits individual instances of the {@code SysMLSignal} to
	 * the remote {@code SysMLPort}'s UDPReceiver via simple datagrams.
	 * 
	 * @author ModelerOne
	 */
	public class UDPTransmitter
	{
		/**
		 * Logger for this UDP transmitter
		 */
		Logger logger;

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
		 * Constructor, which creates the socket
		 */
		public UDPTransmitter()
		{
			super();
			logger = Logger.getLogger(this.getClass().getSimpleName());
			try
			{
				socket = new DatagramSocket();
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
		}

		/**
		 * Operation to actually perform the transmission of the specified
		 * {@code SysMLSignal}. Transmit simply creates the necessary output streams,
		 * uses them to construct the datagram with the signal, and sends the datagram
		 * via the socket.
		 * 
		 * @param signal    {@code SysMLSignal} to be transmitted to remote (not in this
		 *                  process) port
		 * @param ipAddress IP address of the remote port
		 * @param udpPort   udpPort of the remote port
		 */
		public void transmit(SysMLSignal signal, InetAddress ipAddress, int udpPort)
		{
			logger.info("signal=%s, to port=%d".formatted(signal.getClass().getSimpleName(), udpPort));
			try
			{
				ByteArrayOutputStream byteStream = new ByteArrayOutputStream(100_000);
				ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
				objectStream.writeObject(signal);
				DatagramPacket packet = new DatagramPacket(byteStream.toByteArray(), 0, byteStream.size(), ipAddress, udpPort);
				socket.send(packet);
				logger.info("packet sent to port=%d".formatted(packet.getPort()));
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
		}
	}

	/**
	 * Name of state machine variable, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String stateMachineVariableName = "stateMachine";
	/**
	 * Name of method to create state machine, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createStateMachineMethodName = "createStateMachine";
	/**
	 * Name of method to create attributes, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createAttributesMethodName = "createAttributes";
	/**
	 * Name of method to create attributes input via the port, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createAttrbutesInMethodName = "createAttrbutesIn";
	/**
	 * Name of method to create attributes output via the port, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createAttrbutesOutMethodName = "createAttributesOut";
	/**
	 * Name of method to create item's (sub)items, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createItemsMethodName = "createItems";
	/**
	 * Name of method to create items input via the port, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createItemsInMethodName = "createItemsIn";
	/**
	 * Name of method to create items output via the port, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createItemsOutMethodName = "createItemsOut";
	/**
	 * Name of method to create parts, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createPartsMethodName = "createParts";
	/**
	 * Name of method to create subports, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createPortsMethodName = "createPorts";
	/**
	 * Name of method to create action functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createActionFunctionsMethodName = "createActionFunctions";
	/**
	 * Name of method to create actions, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createActionsMethodName = "createActions";
	/**
	 * Name of method to create spatial extent, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createSpatialExtentMethodName = "createSpatialExtent";
	/**
	 * Name of method to create constraint functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintFunctionsMethodName = "createConstraintFunctions";
	/**
	 * Name of method to create constraints, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createConstraintTextsMethodName = "createConstraintTexts";
	/**
	 * Name of method to create constraint texts, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintsMethodName = "createConstraints";
	/**
	 * Name of method to create items's requirements, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createRequirementsMethodName = "createRequirements";
	/**
	 * Name of method to create items's dependencies, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createDependenciesMethodName = "createDependencies";
	/**
	 * Name of method to create items's customized metadata, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createCustomMetadataMethodName = "createCustomMetadatas";
	/**
	 * Name of method to connect to a peer port, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String connectToPeerPortMethodName = "connectToPeerPort";
	/**
	 * Name of method to connect to a server port, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String connectToServerPortMethodName = "connectToServerPort";
	/**
	 * Name of method to connect to a client port, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String connectToClientPortMethodName = "connectToClientPort";
	/**
	 * Name of method to virtually connect to a peer port, used by SysMLinJava
	 * tools, tools, typically not needed for modeling
	 */
	public static final String virtualConnectToPeerPortMethodName = "virtualConnectToPeerPort";
}