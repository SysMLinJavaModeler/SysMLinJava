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
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import sysmlinjava.annotations.Operation;
import sysmlinjava.annotations.Reception;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.events.SysMLSignalEvent;

/**
 * SysMLinJava's representation of the SysML full-port.
 * <h2>SysML full port for comms across connections</h2>As in SysML,
 * {@code SysMLFullPort} is an extention of the basic {@code SysMLBlock}. It
 * extends the block with an optional event context block, i.e. a block to which
 * it can submit {@code SysMLSignalEvent}s for received {@code SysMLSignal}s. It
 * also extends the block with connections to other ports or blocks and uses
 * these for its operations to transmit and receive objects and signals across
 * the connections.
 * <h3>As member of protocol stack</h3> The connections to other ports or blocks
 * provides the capability to quickly and correctly model a "protocol stack"
 * type of communications architecture. Connections are provided between
 * "client" and "server" ports which emulate ports above and below one another
 * in the protocol stack, and "peer" port connections emulate peer-level ports
 * that communicate the same protocol. By creating client-to-server connections
 * and peer-to-peer connections within and across protocol stacks of ports, the
 * SysMLinJava model can execute/simulate virtually any type of communications
 * protocol architecture, i.e. any type of interface, with virtually any level
 * of complexity and precision.
 * <h3>Built-in and extensible receive/transmit operations</h3> The
 * {@code SysMLFullPort} provides specific capabilities to transmit and receive
 * classes and signals to and from the connected ports and blocks. These
 * transmit and receive capabilities reflect the most common types of
 * operations/receptions performed by full-ports in SysML models. However, the
 * {@code SysMLFullPort} can be extended to override these operations/receptions
 * and/or perform other specialized types of operations/receptions as needed.
 * <h3>Supports comm between ports in different OS processes (JVMs)</h3> This
 * {@code SysMLFullPort} can be constructed/instantiated to receive
 * {@code SysMLSignals} from another full port that operates in a different
 * operating system process potentially on another computer system. This
 * capability can be realized by invoking the constructer that includes an IP
 * address and UDP port arguments. This constructor starts a separate thread of
 * execution that receives UDP datagrams on the specified UDP port and then
 * invokes the {@code receive(SysMLSignal)} operation. SysMLinJava provides a
 * {@code BlockContainer} class that can be extended to host an arbitrary
 * portion of the SysMLinJava model in the context of its own operating system
 * process. This container class provides information on how to utilize the
 * container to achieve a SysMLinJava model that can be constructed and executed
 * across multiple processes for greater model scalability and performance.
 * <h3>Utility for generating messages for sequence diagrams</h3> Finally, the
 * {@code SysMLFullPort} base class provides capabilities to perform utility
 * functions during the performance of the transmit operation. The
 * {@code SysMLFullPort} field variable {@code messageUtility} of optional
 * interface type {@code InteractionMessageUtility} is, if present, invoked
 * whenever a message is transmitted between blocks via their full ports.
 * Message information such as source block, message type, destination block,
 * etc. are passed to the utility's operations. Implementations of this
 * interface can enable capture of the data for such items as sequence diagrams,
 * or for simple capture and storage of the message data for later review and
 * analysis. In any case, capture and display of this interaction message data
 * can enable detailed analysis of the model execution and make for some
 * audience pleasing and convincing graphics for design reviews and other
 * presentations of the model.
 * <h3>Example sequence diagram message displays</h3> See the implementation
 * referenced below for an example of using this capability to transmit
 * interaction message information to respective displays. SysMLinJava contains
 * a rudimentary console-based application that can receive and display the
 * message data as simple text. However, there are commercially available
 * applications that provide real-time graphical displays of the messages in a
 * SysML sequence diagram. These applications also provide capabilites to
 * save/export the sequence diagrams as PDF, CSV, and HTML files enabling
 * post-model execution review and analysis of the interactions between elements
 * of the modeled system. Visit SysMLinJava.com for more information.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.blocks.SysMLBlock#createFullPorts()
 * @see sysmlinjava.analysis.interactionmessagetransmitter.InteractionMessageTransmitters
 * @see sysmlinjava.blocks.BlockContainer
 */
public abstract class SysMLFullPort extends SysMLBlock
{
	// /**
	// * Index into array of ports for this port.
	// */
	// public Integer index;
	/**
	 * {@code SysMLBlock}-based block to which port events are to be submitted
	 */
	public Optional<? extends SysMLBlock> eventContextBlock;
	/**
	 * Ports to which this port is connected that are "above" this port in a
	 * protocol stack.
	 */
	protected List<SysMLFullPort> connectedPortsClients;
	/**
	 * Ports to which this port is connected that are "below" this port in a
	 * protocol stack.
	 */
	protected List<SysMLFullPort> connectedPortsServers;
	/**
	 * Ports to which this port is connected that are "peers" to this port in other
	 * protocol stacks.
	 */
	protected List<SysMLFullPort> connectedPortsPeers;
	/**
	 * Ports to which this port is "virtually" connected that are peers to this port
	 * in other protocol stacks. Virtual port peers represent ports in a "protocol
	 * stack", a.k.a. "interface layers" that are connected virtually via
	 * lower-layer protocols/layers. For example, an HTTP client (web browser) port
	 * would be virtually connected to a peer HTTP server (web server) port via the
	 * TCP/IP/Ethernet protocols in the web browser and web server computers.
	 * SysMLinJava provides the {@code connectedVirtualPortsPeers} collection as a
	 * means of modeling virtual connections without requiring a capability to
	 * execute (transmit and receive signals via) the connection. Executable
	 * connections should be modeled as {@code connectedPortsPeers} objects.
	 * <p>
	 * The SysMLinJava TaskMaster&trade; application utilizes virtual connectors in
	 * its interface requirements generator tool. Modelers will need to specify
	 * virtual connectors as {@code connectedVirtualPortsPeers} if/when this tool is
	 * utilized. See SysMLinJava.com for more information.
	 */
	protected List<SysMLFullPort> virtualConnectedPortsPeers;

	/**
	 * Transmitter of UDP datagrams for full port configured for inter-process
	 * connection, i.e. the full port and its connected port peers are in different
	 * {@code BlockContainer}s. The udpTransmitter is created automatically by the
	 * constructor when the constructor is invoked with a specified IP address and
	 * UDP port.
	 */
	protected Optional<UDPTransmitter> udpTransmitter;
	/**
	 * Receiver of UDP datagrams for full port configured for inter-process
	 * connection, i.e. the full port and its connected port peers are in different
	 * {@code BlockContainer}s. The udpReceiver is created automatically by the
	 * constructor when the constructor is invoked with a IP address of
	 * {@code localhost} and a specified UDP port.
	 */
	protected Optional<UDPReceiver> udpReceiver;
	/**
	 * IP address for this full port when configured for inter-process connection,
	 * i.e. the full port and its connected port peers are in different
	 * {@code BlockContainer}s.
	 */
	protected Optional<InetAddress> ipAddress;
	/**
	 * UDP port number for this full port configured for inter-process connection,
	 * i.e. the full port and its connected port peers are in different
	 * {@code BlockContainer}s.
	 */
	protected Optional<Integer> udpPort;

	/**
	 * Optional implementation of the {@code InteractionMessageUtility} interface.
	 * If present, the {@code messageUtility} is invoked for every message
	 * ({@code SysMLSignal}) that is transmitted by this full port.
	 * 
	 * @see InteractionMessageUtility
	 */
	public Optional<InteractionMessageUtility> messageUtility;

	/**
	 * Constructor with minimal (contextBlock and index) specification.
	 * 
	 * @param contextBlock The {@code SysMLBlock} which provides the context for
	 *                     (contains) this port, i.e. the block whose fields or
	 *                     operations may need to be accessed by this port. Note
	 *                     that this constructor does NOT specify the
	 *                     {@code eventContextBlock} needed for an event-driven
	 *                     contextBlock for which this port produces
	 *                     {@code SysMLEvent}s. Use constructors below to specify an
	 *                     {@code eventContextBlock}.
	 * @param id           An arbitrary index to be associated with this port. For
	 *                     example, an array index for an array of ports.
	 */
	public SysMLFullPort(SysMLBlock contextBlock, Long id)
	{
		super(contextBlock, "", id);
		eventContextBlock = Optional.empty();
		messageUtility = Optional.empty();
		ipAddress = Optional.empty();
		udpPort = Optional.empty();
		udpTransmitter = Optional.empty();
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();
	}

	/**
	 * Constructor with contextBlock, index, and name specification.
	 * 
	 * @param contextBlock The {@code SysMLBlock} which provides the context for
	 *                     (contains) this port, i.e. the block whose fields or
	 *                     operations may need to be accessed by this port. Note
	 *                     that this constructor does NOT specify the
	 *                     {@code eventContextBlock} needed for an event-driven
	 *                     contextBlock for which this port produces
	 *                     {@code SysMLEvent}s. Use constructors below to specify an
	 *                     eventContextBlock.
	 * @param id           An arbitrary ID to be associated with this port. For
	 *                     example, an index into an array of ports.
	 * @param name         name to be associated with this instance of the
	 *                     {@code SysMLFullPort}.
	 */
	public SysMLFullPort(SysMLBlock contextBlock, Long id, String name)
	{
		super(contextBlock, name, id);
		eventContextBlock = Optional.empty();
		messageUtility = Optional.empty();
		ipAddress = Optional.empty();
		udpPort = Optional.empty();
		udpTransmitter = Optional.empty();
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();
	}

	/**
	 * Constructor with contextBlock, eventContextBlock, and index specification.
	 * 
	 * @param contextBlock      The {@code SysMLBlock} which provides the context
	 *                          for (contains) this port.
	 * @param eventContextBlock Optional {@code SysMLBlock} to which
	 *                          {@code SysMLEvent}s (for receipt of
	 *                          {@code SysMLSignal}s or {@code SysMLClass}es) are to
	 *                          submitted. Use this constructor if the port is to
	 *                          provide {@code SysMLEvent}s to an event-drive block.
	 * @param id                An arbitrary index to be associated with this port.
	 *                          For example, an index into an array of ports.
	 */
	public SysMLFullPort(SysMLBlock contextBlock, Optional<? extends SysMLBlock> eventContextBlock, Long id)
	{
		this(contextBlock, id, "");
		this.eventContextBlock = eventContextBlock;
	}

	/**
	 * Constructor with maximal specification.
	 * 
	 * @param contextBlock      The {@code SysMLBlock} which provides the context
	 *                          for (contains) this port.
	 * @param eventContextBlock Optional {@code SysMLBlock} to which
	 *                          {@code SysMLEvent}s (for receipt of
	 *                          {@code SysMLSignal}s or {@code SysMLClass}es) are to
	 *                          be submitted. Use this constructor if the port is to
	 *                          provide {@code SysMLEvent}s to an event-driven
	 *                          block.
	 * @param id                An arbitrary index to be associated with this port.
	 *                          For example, an index into an array of ports.
	 * @param name              name to be associated with this instance of the
	 *                          {@code SysMLFullPort}.
	 */
	public SysMLFullPort(SysMLBlock contextBlock, Optional<? extends SysMLBlock> eventContextBlock, Long id, String name)
	{
		this(contextBlock, id, name);
		this.eventContextBlock = eventContextBlock;
	}

	/**
	 * Constructor with maximal specification for a full port that receives
	 * {@code SysMLSignal}s from full ports in other processes. This constuctor
	 * should only be used if/when the context block is a "part" of a
	 * {@code BlockContainer} which supports blocks executing in separate operating
	 * system processes.
	 * 
	 * @param contextBlock      The {@code SysMLBlock} which provides the context
	 *                          for (contains) this port.
	 * @param eventContextBlock Optional {@code SysMLBlock} to which
	 *                          {@code SysMLEvent}s (for receipt of
	 *                          {@code SysMLSignal}s or {@code SysMLClass}es) are to
	 *                          submitted. Use this constructor if the port is to
	 *                          provide {@code SysMLEvent}s to an event-driven
	 *                          block.
	 * @param ipAddress         IP address for this full ports host computer.
	 * @param udpPort           UDP port assigned to this full port on which to
	 *                          receive {@code SysMLSignal}s in UDP datagrams.
	 * @param id                An arbitrary index to be associated with this port.
	 *                          For example, an index into an array of ports.
	 * @param name              name to be associated with this instance of the
	 *                          {@code SysMLFullPort}.
	 * 
	 * @see sysmlinjava.blocks.BlockContainer
	 */
	public SysMLFullPort(SysMLBlock contextBlock, Optional<? extends SysMLBlock> eventContextBlock, InetAddress ipAddress, Integer udpPort, Long id, String name)
	{
		super(contextBlock, name, id);
		this.eventContextBlock = eventContextBlock;
		this.messageUtility = Optional.empty();
		this.ipAddress = Optional.of(ipAddress);
		this.udpPort = Optional.of(udpPort);
		udpTransmitter = Optional.of(new UDPTransmitter());
		udpReceiver = Optional.empty();
		connectedPortsClients = new ArrayList<>();
		connectedPortsServers = new ArrayList<>();
		connectedPortsPeers = new ArrayList<>();
		virtualConnectedPortsPeers = new ArrayList<>();

	}

	/**
	 * Starts the port, i.e. starts its state machine, if one is configured for the
	 * port, and, if the port is configured to receive objects from a port in
	 * another operating system process, then it starts the UDP receiver to receive
	 * those objects.
	 */
	@Override
	public void start()
	{
		if (stateMachine.isPresent())
			super.start();
		if (udpPort.isPresent())
			udpReceiver = Optional.of(new UDPReceiver(udpPort.get(), this));
	}

	/**
	 * Stops the port, i.e. stop the UDP receiver and the state machine, if either
	 * has been configured for the port.
	 */
	@Override
	public void stop()
	{
		if (udpPort.isPresent())
			udpReceiver.get().stop();
		if (stateMachine.isPresent())
			super.stop();
	}

	/**
	 * Receives a signal object. This operation is called by a "peer" port which is
	 * connected to this port providing the specified {@code SysMLSignal}-based
	 * object. The {@code receive} behavior models that of an interaction protocol
	 * between connected ports by simply receiving a signal value and either
	 * submitting a signal event to a state-machine for reception handling or
	 * submitting the signal contents to another (client) protocol for further
	 * processing.
	 * <p>
	 * If an {@code eventContextBlock} was specified for this full port, then the
	 * signal is translated into an event and the event is submitted to the
	 * {@code eventContextBlock}s event queue. If not and a client port was
	 * connected to this port, then the client protocol object is extracted from the
	 * signal and received by the client protocol.
	 * 
	 * @param signal The signal to be received from the "peer" port.
	 */
	@Reception
	public void receive(SysMLSignal signal)
	{
		if (eventContextBlock.isPresent())
		{
			SysMLSignalEvent event = eventFor(signal);
			event.index = id.intValue();
			eventContextBlock.get().acceptEvent(event);
		}
		else if (!connectedPortsClients.isEmpty())
		{
			SysMLClass object = clientObjectFor(signal);
			connectedPortsClients.forEach(client ->
			{
				client.receive(object);
			});
		}
	}

	/**
	 * Receives an object. This operation is called by a "server" port which is
	 * connected to this port providing the specified {@code SysMLClass}-based
	 * object. The server port typically represents a lower level communications
	 * protocol in a "stack" of protocols.
	 * <p>
	 * If any client ports are connected to this port, then the client protocol
	 * object is extracted from the received object and received by the client
	 * protocols. If not and an {@code eventContextBlock} was specified for this
	 * full port, then the object is inserted into an event and the event is
	 * submitted to the {@code eventContextBlock}s event queue.
	 * 
	 * @param object The object to be received by this client port from the "server"
	 *               port.
	 */
	@Operation
	public void receive(SysMLClass object)
	{
		if (!connectedPortsClients.isEmpty())
		{
			SysMLClass clientObject = clientObjectFor(object);
			connectedPortsClients.forEach(client ->
			{
				logger.info(String.format("[SEQ] %s[%d] >> %s >> %s[%d]", this.getClass().getSimpleName(), this.id, clientObject.getClass().getSimpleName(), client.getClass().getSimpleName(), client.id));
				client.receive(clientObject);
			});
		}
		else if (eventContextBlock.isPresent())
		{
			SysMLEvent event = eventFor(object);
			eventContextBlock.get().acceptEvent(event);
		}
	}

	/**
	 * Transmits an object. This operation is called to transmit the specified
	 * {@code SysMLClass}-based object to "server" or "peer" ports to which this
	 * port is connected. The transmission will be to any and all "peer" ports, if
	 * any are connected. But if none are, the transmission will be to any and all
	 * "server" ports, if any are connected.
	 * <p>
	 * If a peer-level protocol port was connected to this port, then the object to
	 * be transmitted is embedded in a {@code SysMLSignal} and transmitted to the
	 * connected peer full port (typically a full port in another block). If not and
	 * a server protocol (typically a lower level protocol in the protocol "stack"
	 * in the same block) was specified for this full port, then the object is
	 * inserted into the server's protocol object and transmitted to the server port
	 * for further processing and transmission in the "stack".
	 * 
	 * @param object The object to be transmitted to the "server" or "peer" port.
	 */
	@Operation
	public void transmit(SysMLClass object)
	{
		if (!connectedPortsPeers.isEmpty())
		{
			SysMLSignal signal = signalFor(object);

			for (SysMLFullPort peer : connectedPortsPeers)
			{
				try
				{
					messageUtility.ifPresent(utility -> utility.perform(Instant.now(), contextBlock.get(), signal, peer, logger));

					if (!udpTransmitter.isPresent() && !peer.ipAddress.isPresent() && !peer.udpPort.isPresent())
						peer.receive(signal);
					else if (udpTransmitter.isPresent() && peer.ipAddress.isPresent() && peer.udpPort.isPresent())
						udpTransmitter.get().transmit(signal, peer.ipAddress.get(), peer.udpPort.get());
					else
						logger
							.severe(String.format("one or more elements for UDP connection are missing: udpTransmitter? %s, ipAddress? %s, udpPort? %s", !udpTransmitter.isPresent(), !peer.ipAddress.isPresent(), !peer.udpPort.isPresent()));
				} catch (NullPointerException e)
				{
					e.printStackTrace();
				}
			}
		}
		else if (!connectedPortsServers.isEmpty())
		{
			SysMLClass serverObject = serverObjectFor(object);
			connectedPortsServers.forEach(server ->
			{
				String thisIndexString = String.format("[%d]", this.id);
				String serverIndexString = String.format("[%d]", server.id);
				logger.info(String.format("[SEQ] %s%s >> %s >> %s%s", this.getClass().getSimpleName(), thisIndexString, serverObject.getClass().getSimpleName(), server.getClass().getSimpleName(), serverIndexString));
				server.transmit(serverObject);
			});
		}
	}

	/**
	 * Adds a port to the collection of ports that are to operate as "clients" to
	 * this port, i.e. they are "above" this port in a protocol "stack" of ports and
	 * this port will be receiving objects from this port to be encapsulated into
	 * this port's protocol and transmitted to a server port or to a peer port; and
	 * this port will be receiving objects from a server port or from a peer port
	 * and decapsulating them from this port's protocl and transmitting the objects
	 * to the client port.
	 * 
	 * @param client port to be the client port to this port. The client should be
	 *               declared as a java field access variable (a.k.a "nested element
	 *               path" in SysML) with scope element names as needed. An example
	 *               of how to invoke/call this method is as follows:
	 * 
	 *               <pre>
	 * ({@code
		elementA.nestedElementA1.addConnectedPortClient(elementA.nestedElementA2);
	 }
	 * </pre>
	 * 
	 */
	public void addConnectedPortClient(SysMLFullPort client)
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
	 * 
	 * @param index  index in the collection of client ports at which this client is
	 *               to be located
	 * @param client port to be the client port to this port. The client should be
	 *               declared as a java field access variable (a.k.a "nested element
	 *               path" in SysML) with scope element names as needed. An example
	 *               of how to invoke/call this method is as follows:
	 * 
	 *               <pre>
	 * ({@code
		elementA.nestedElementA1.addConnectedPortClient(elementA.nestedElementA2);
	 }
	 * </pre>
	 * 
	 */
	public void addConnectedPortClient(int index, SysMLFullPort client)
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
	 * 
	 * @param server port to be the server port to this port. The server should be
	 *               declared as a java field access variable (a.k.a "nested element
	 *               path" in SysML) with scope element names as needed. An example
	 *               of how to invoke/call this method is as follows:
	 * 
	 *               <pre>
	 * ({@code
		elementA.nestedElementA2.addConnectedPortServer(elementA.nestedElementA1);
	 }
	 * </pre>
	 * 
	 */
	public void addConnectedPortServer(SysMLFullPort server)
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
	 * 
	 * @param server port to be the server port to this port. The server should be
	 *               declared as a java field access variable (a.k.a "nested element
	 *               path" in SysML) with scope element names as needed. An example
	 *               of how to invoke/call this method is as follows:
	 * 
	 *               <pre>
	 * ({@code
		elementA.nestedElementA2.addConnectedPortServer(elementA.nestedElementA1);
	 }
	 * </pre>
	 * 
	 * @param index  index in list of connected port servers at which to add
	 */
	public void addConnectedPortServer(int index, SysMLFullPort server)
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
	 * 
	 * @param peer port (likely in another block) to be the peer port to this port.
	 *             The peer should be declared as a java field access variable
	 *             (a.k.a "nested element path" in SysML) with scope element names
	 *             as needed. An example of how to invoke/call this method is as
	 *             follows:
	 * 
	 *             <pre>
	 * ({@code
		elementA.nestedElementA2.addConnectedPortPeer(elementB.nestedElementB1);
	 }
	 * </pre>
	 */
	public void addConnectedPortPeer(SysMLFullPort peer)
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
	 * 
	 * @param index index in the collection of peer ports at which this peer is to
	 *              be located
	 * @param peer  port (likely in another block) to be the peer port to this port.
	 *              The peer should be declared as a java field access variable
	 *              (a.k.a "nested element path" in SysML) with scope element names
	 *              as needed. An example of how to invoke/call this method is as
	 *              follows:
	 * 
	 *              <pre>
	 * ({@code
		elementA.nestedElementA2.addConnectedPortPeer(elementB.nestedElementB1);
	 }
	 * </pre>
	 */
	public void addConnectedPortPeer(int index, SysMLFullPort peer)
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
	 * 
	 * @param peer port (likely in another block) to be the virtually connected peer
	 *             port to this port. The peer should be declared as a java field
	 *             access variable (a.k.a "nested element path" in SysML) with scope
	 *             element names as needed. An example of how to invoke/call this
	 *             method is as follows:
	 * 
	 *             <pre>
	 *             elementA.nestedPortA2.addVirtualConnectedPortPeer(elementB.nestedPortB1);
	 *             </pre>
	 */
	public void addVirtualConnectedPortPeer(SysMLFullPort peer)
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
	 * 
	 * @param index index in the collection of peer ports at which this peer is to
	 *              be located
	 * @param peer  port (likely in another block) to be the virtually connected
	 *              peer port to this port. The peer should be declared as a java
	 *              field access variable (a.k.a "nested element path" in SysML)
	 *              with scope element names as needed. An example of how to
	 *              invoke/call this method is as follows:
	 * 
	 *              <pre>
	 *              elementA.nestedElementA2.addVirtualConnectedPortPeer(elementB.nestedElementB1);
	 *              </pre>
	 */
	public void addVirtualConnectedPortPeer(int index, SysMLFullPort peer)
	{
		virtualConnectedPortsPeers.add(peer);
	}

	/**
	 * Returns the "peer" port located at the indexed location in the collecion of
	 * peer ports
	 * 
	 * @param index index in the collection of peer ports at which this peer is
	 *              located
	 * @return the indexed peer port
	 */
	public SysMLFullPort getConnectedPortPeer(int index)
	{
		return connectedPortsPeers.get(index);
	}

	/**
	 * Returns the "server" port located at the indexed location in the collecion of
	 * server ports
	 * 
	 * @param index index in the collection of server ports at which this server is
	 *              located
	 * @return the indexed server port
	 */
	public SysMLFullPort getConnectedPortServer(int index)
	{
		return connectedPortsServers.get(index);
	}

	/**
	 * Returns the "client" port located at the indexed location in the collecion of
	 * client ports
	 * 
	 * @param index index in the collection of client ports at which this client is
	 *              located
	 * @return the indexed client port
	 */
	public SysMLFullPort getConnectedPortClient(int index)
	{
		return connectedPortsClients.get(index);
	}

	/**
	 * Overridable operation to transform the specified object into a
	 * {@code SysMLEvent}-based event.<br>
	 * <b>Note:</b>Extensions of {@code SysMLFullPort} <b>must</b> override and
	 * implement this operation if this port is a "client" port that receives
	 * {@code SysMLClass}-based objects from "server" ports and generates
	 * {@code SysMLEvent}s for the {@code eventContextBlock} that contains the
	 * objects for the {@code eventContextBlock}.
	 * 
	 * @param object The received {@code SysMLClass}-based object.
	 * @return {@code SysMLEvent} for the object.
	 */
	protected SysMLEvent eventFor(SysMLClass object)
	{
		return null;
	}

	/**
	 * Overridable operation to transform the object in the specified signal into a
	 * {@code SysMLSignalEvent}-based event.<br>
	 * <b>Note:</b>Extensions of {@code SysMLFullPort} <b>must</b> override and
	 * implement this operation if this port is a peer-level protocol port that
	 * receives {@code SysMLSignal}-based signals from peer-level port(s) and
	 * generates {@code SysMLSignalEvent}s that contain the objects contained by the
	 * signals for the {@code eventContextBlock}.
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
	 * <b>Note:</b>Extensions of {@code SysMLFullPort} <b>must</b> override and
	 * implement this operation if this port is a "client" port for a lower-level
	 * protocol that receives {@code SysMLClass}-based objects from a "server" port
	 * for an upper-level protocol and decapsulates them for an opper-level
	 * protocol.
	 * 
	 * @param serverObject The {@code SysMLClass}-based object received from the
	 *                     lower-level protocol. @return {@code SysMLClass}-based
	 *                     object for the upper-level protocol that decapsulates the
	 *                     serverObject.
	 * @return {@code SysMLClass}-based object decapsulated from the
	 *         {@code serverObject}n
	 */
	protected SysMLClass clientObjectFor(SysMLClass serverObject)
	{
		return null;
	}

	/**
	 * Overridable operation to transform the object in the specified signal into a
	 * {@code SysMLClass}-based object.<br>
	 * <b>Note:</b>Extensions of {@code SysMLFullPort} <b>must</b> override and
	 * implement this operation if this port is a peer-level protocol port that
	 * receives SysML-based signals from peer-level port(s) and extracts
	 * {@code SysMLClass}-based objects from the signal for transmission to "client"
	 * ports.
	 * 
	 * @param signal The {@code SysMLSignal}-based signal received from the "peer"
	 *               port. @return {@code SysMLClass}-based object extracted from
	 *               the signal.
	 * 
	 * @return {@code SysMLClass}-based object retrieved from the {@code signal}
	 */
	protected SysMLClass clientObjectFor(SysMLSignal signal)
	{
		return null;
	}

	/**
	 * Overridable operation to transform (encapsulate) the specified upper-level
	 * protocol object into a {@code SysMLSignal}-based signal.<br>
	 * <b>Note:</b>Extensions of {@code SysMLFullPort} <b>must</b> override and
	 * implement this operation if this port is a "server" port that receives
	 * {@code SysMLClass}-based objects from a "client" port for an upper-level
	 * protocol or from another {@code SysMLBlock} object and encapsulates them into
	 * a SysML-based signal for transmission to a peer-level protocol port.
	 * 
	 * @param object The {@code SysMLClass}-based object received from the
	 *               upper-level protocol or other block. @return
	 *               {@code SysMLSignal}s-based signal that encapsulates the object.
	 * @return {@code SysMLSignal}-based signal that is to contain the specified
	 *         {@code object}
	 */
	protected SysMLSignal signalFor(SysMLClass object)
	{
		return null;
	}

	/**
	 * Overridable operation to transform the specified upper-level protocol object
	 * into a lower-level protocol object.<br>
	 * <b>Note:</b>Extensions of {@code SysMLFullPort} <b>must</b> override and
	 * implement this operation if this port is a "server" port that receives
	 * {@code SysMLClass}-based objects from a "client" port for an upper-level
	 * protocol and encapsulates them for a lower-level protocol.
	 * 
	 * @param clientObject The {@code SysMLClass}-based object received from the
	 *                     upper-level protocol. @return {@code SysMLClass}-based
	 *                     object for the lower-level protocol that encapsulates the
	 *                     clientObject.
	 * @return {@code SysMLClass}-based object that encapsulates the specified
	 *         {@code clientObject}
	 */
	protected SysMLClass serverObjectFor(SysMLClass clientObject)
	{
		return null;
	}

	/**
	 * Overridable operation for which the overriding operation should optionally
	 * create/initialize the {@code messageUtility} field variable. If the variable
	 * is present, it will be used to invoke the operation of the
	 * {@code InteractionMessageUtility} interface.
	 */
	protected void createInteractionMessageUtility()
	{
	}

	/**
	 * The {@code UDPReceiver} is a {@code Runnable} class that receives objects
	 * from {@code UDPTransmitter}s via the User Datagram Protocol (UDP). It is used
	 * by {@code SysMLFullPort} instances to execute interactions betwen ports that
	 * reside in separate {@code BlockContainer}s, i.e. in separate JVM processes.
	 * <p>
	 * The {@code SysMLFullPort} is configured for inter-process port interactions
	 * by simply using the constructor that specifies the port's internet address
	 * and UDP port number. This constructor will instantiate the
	 * {@code UDPReceiver} to receive and/or transmit signals via the UDP protocol
	 * from/to a "remote" full-port. The full-port must also be "connected" to the
	 * remote port via a call to the {@code addConnectedPortPeer()} method where the
	 * port to be added is an instance of a {@code SysMLFullPort} that is likewise
	 * instantiated with an internet address and UDP port number. See the
	 * {@code BlockContainer} for more details.
	 * <p>
	 * Modelers typically will not need to reference the {@code UDPReceiver} in any
	 * way as its instantiation and operation are automatically configured when the
	 * {@code SysMLFullPort} is extended and constructed in this way.
	 * 
	 * @author ModelerOne
	 *
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
		public SysMLFullPort fullPort;
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
		 * @param udpPort  UDP port on which the receiver is to receive signal objects
		 * @param fullPort the full port whose {@code receive()} operation is to be
		 *                 invoked to receive the signal objects.
		 */
		public UDPReceiver(int udpPort, SysMLFullPort fullPort)
		{
			super();
			logger = Logger.getLogger(this.getClass().getName());
			this.fullPort = fullPort;
			this.udpPort = udpPort;
			runner = Optional.of(fullPort.concurrentExecutionThreads.submit(this));
			logger.info(String.format("run() submitted for execution for full port %s on udpPort %s", fullPort.identityString(), udpPort));
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
		 * full port's {@code receive(Object)} operation to process the received object.
		 * It continues this activity until the run is interrupted by a socket closure.
		 */
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
								if (readObject instanceof SysMLSignal)
									fullPort.receive((SysMLSignal)readObject);
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
				} while (!done);
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generic class to transmit {@code SysMLSignal}s via the User Datagram Protocol
	 * (UDP) to a UDPReceiver of an other {@code SysMLFullPort} in another process.
	 * UDPTransmitter transmits individual instances of the {@code SysMLSignal} to
	 * the remote {@code SysMLFullPort}'s UDPReceiver via simple datagrams.
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
		 * 
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
		 * 
		 * @param ipAddress IP address of the remote port
		 * @param udpPort   udpPort of the remote port
		 */
		public void transmit(SysMLSignal signal, InetAddress ipAddress, int udpPort)
		{
			try
			{
				ByteArrayOutputStream byteStream = new ByteArrayOutputStream(100_000);
				ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
				objectStream.writeObject(signal);
				DatagramPacket packet = new DatagramPacket(byteStream.toByteArray(), 0, byteStream.size(), ipAddress, udpPort);
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
		}
	}

	/**
	 * Name of method to add a connected port peer, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String addConnectedPortPeerMethodName = "addConnectedPortPeer";
	/**
	 * Name of method to add a connected port server, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String addConnectedPortServerMethodName = "addConnectedPortServer";
	/**
	 * Name of method to add a connected port client, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String addConnectedPortClientMethodName = "addConnectedPortClient";
	/**
	 * Name of method to add a virtual connected port peer, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String addVirtualConnectedPortPeerMethodName = "addVirtualConnectedPortPeer";
}