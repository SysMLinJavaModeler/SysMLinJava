package sysmlinjava.ports;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.blocks.SysMLInterfaceBlock;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava's representation of SysML's proxy-port.
 * <h2>SysML proxy port to invoke behaviors on other
 * blocks</h2>{@code SysMLProxyPort} is an extension of the basic
 * {@code SysMLClass}. It extends the class with an optional implementing
 * context block, i.e. a block that implements the {@code SysMLInterfaceBlock}
 * that is also realized by the proxy-port. It also extends the class with
 * optional connections to other proxy ports and uses these to invoke interface
 * operations on the connected blocks.
 * <h3>As part of calling block ..</h3> The {@code SysMLProxyPort} is configured
 * in one of two ways - as a port on the <b>calling</b> block or as a port on
 * the <b>called</b> block. As a port on the calling block, the
 * {@code SysMLProxyPort} is connected to a port on the called block, its
 * conjugate port. It uses this connection to invoke an operation of the
 * {@code SysMLBlockInterface} it implements via the connected proxy port.
 * <h3>As part of called block ..</h3> As a port on the called block, the
 * {@code SysMLProxyPort} has a reference to the called block that realizes the
 * {@code SysMLBlockInterface}. It uses this reference to actually invoke the
 * behaviors (operations and/or state machine) of the {@code SysMLBlock}. As a
 * port on the called block, the {@code SysMLProxyPort} is considered to be a
 * "conjugate" port, as defined in SysML. In either case, the SysMLinJava
 * representation of the proxy port is consistent with the standard SysML
 * proxy-port in that the {@code SysMLProxyPort} is, in fact, a proxy for the
 * SysMLBlock that finally implements the interface operations.
 * <h3>Utility for generating messages for sequence diagrams</h3> Finally, the
 * {@code SysMLProxyFullPort} base class provides capabilities to perform
 * utility functions during the performance of transitions between and within
 * states. The {@code SysMLFullPort} field variable {@code messageUtility} of
 * interface type {@code InteractionMessageUtility} is invoked whenever a
 * message is transmitted between blocks via their full ports. Message
 * information such as source block, message type, destination block, etc. are
 * passed to the utility's operations. Implementations of this interface can
 * enable capture of the data for such items as sequence diagrams, or for simple
 * capture and storage of the message data for later review and analysis. In any
 * case, capture and display of this interaction message data can enable
 * detailed analysis of the model execution and make for some audience pleasing
 * and convincing graphics for design reviews and other presentations of the
 * model.
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
 * <h3>Connected proxy ports in same process/JVM</h3> Note that, unlike the full
 * port, the proxy port cannot be connected to another proxy port that resides
 * in a different {@code BlockContainer}, i.e. operation calls across
 * processes/JVMs are not supported. As a result, connected proxy ports must
 * reside in the same operating system process (JVM), i.e. in the same
 * {@code BlockContainer}.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.blocks.SysMLBlock#createProxyPorts()
 * @see sysmlinjava.analysis.interactionmessagetransmitter.InteractionMessageTransmitters
 */
public abstract class SysMLProxyPort extends SysMLClass implements SysMLInterfaceBlock
{
	/**
	 * SysMLinJava representation of the isBehavior of a SysML proxy port. Assumed
	 * to be behavioral port if implementingContextBlock is present. Otherwise not.
	 */
	public boolean isBehavior;
	/**
	 * SysMLBlock-based block that is the containing (context) block of this
	 * (possibly nested) {@code SysMLProxyPort}. The contextBlock is used to invoke
	 * a block behavior when a method of the proxy port is called.
	 */
	public SysMLBlock contextBlock;
	/**
	 * SysMLBlock-based block that implements the operations specified by the port's
	 * SysMLBlockInterface. This block is used to invoke a block's (possibly part's)
	 * behavior when a method of the proxy port is called.
	 */
	public Optional<? extends SysMLBlock> implementingContextBlock;
	/**
	 * Ports to which this port is connected as peer ports
	 */
	protected List<SysMLProxyPort> connectedPortsPeers;
	/**
	 * Ports to which this port is connected as peer ports
	 */
	protected List<SysMLProxyPort> virtualConnectedPortsPeers;

	/**
	 * Optional implementation of the {@code InteractionMessageUtility} interface.
	 * If present, the {@code messageUtility} is invoked for every message
	 * ({@code SysMLSignal}) that is transmitted by this full port.
	 * 
	 * @see InteractionMessageUtility
	 */
	public Optional<InteractionMessageUtility> messageUtility;

	/**
	 * Constructor with maximal specification.
	 * 
	 * @param contextBlock             The {@code SysMLBlock} which provides the
	 *                                 context for this port. It is a
	 *                                 {@code SysMLBlock}-based block that is the
	 *                                 containing (context) block of this (possibly
	 *                                 nested) {@code SysMLProxyPort}. The
	 *                                 contextBlock is used to invoke a block
	 *                                 behavior when a method of the proxy port is
	 *                                 called.
	 * @param implementingContextBlock Optional {@code SysMLBlock} that is
	 *                                 implementing the operations specified by the
	 *                                 extended port's {@code SysMLBlockInterface}.
	 *                                 This could be the same as the contextBlock or
	 *                                 a "part" within the contextBlock. It is a
	 *                                 {@code SysMLBlock}-based block that
	 *                                 implements the operations specified by the
	 *                                 port's {@code SysMLBlockInterface}. This
	 *                                 block is used to invoke a block's (possibly
	 *                                 part's) behavior when a method of the proxy
	 *                                 port is called.
	 * @param id                       An arbitrary index to be associated with this
	 *                                 port. For example, an array index for an
	 *                                 array of ports.
	 */
	public SysMLProxyPort(SysMLBlock contextBlock, Optional<? extends SysMLBlock> implementingContextBlock, Long id)
	{
		super();
		this.id = id;
		this.contextBlock = contextBlock;
		this.implementingContextBlock = implementingContextBlock;
		isBehavior = implementingContextBlock.isPresent();
		connectedPortsPeers = new ArrayList<>();
		messageUtility = Optional.empty();
		createValues();
		createFlows();
	}

	/**
	 * Overridable operation that creates and initializes the port's values, if any.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
		myFirstValue = new ValueTypeA(&lt;initializer&gt;);
		myNextValue = new ValueTypeB(&lt;initializer&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code Value} annotation and {@code ValueTypeX} is a constructor of one
	 * of the {@code SysMLValueType} classes, i.e. values are declared by
	 * {@code SysMLValueType}s. Initializers for RReal or IInteger value types, or
	 * for extensions of the RReal or IInteger value types, usually include an
	 * integer or double number as the first argument(s). These numbers should be
	 * formatted in decimal format. Use of the underscore (e.g. 10_000.01) can be
	 * used for thousands demarcation which will be converted to comma (10,000.01)
	 * by SysMLinJava tools for value displays.
	 * 
	 * @see sysmlinjava.valuetypes.SysMLValueType
	 */
	protected void createValues()
	{
	}

	/**
	 * Overridable operation that creates and initializes the port's flows, if any.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
		myFirstFlow = new ValueTypeA(&lt;initializer&gt;);
		myNextFlow = new ValueTypeB(&lt;initializer&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code Flow} annotation and {@code ValueTypeX} is a constructor of one of
	 * the {@code SysMLValueType} classes, i.e. values are declared by
	 * {@code SysMLValueType}s. Initializers for RReal or IInteger value types, or
	 * for extensions of the RReal or IInteger value types, usually include an
	 * integer or double number as the first argument(s). These numbers should be
	 * formatted in decimal format. Use of the underscore (e.g. 10_000.01) can be
	 * used for thousands demarcation which will be converted to comma (10,000.01)
	 * by SysMLinJava tools for flow displays.
	 * 
	 * @see sysmlinjava.valuetypes.SysMLValueType
	 */
	protected void createFlows()
	{
	}

	/**
	 * Overridable operation that creates and initializes the propxy port's nested
	 * proxy ports.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		myFirstProxyPort = new ProxyPortTypeA(&lt;initializer&gt;);
		myNextProxyPort = new ProxyPortTypeB(&lt;initializer&gt;);
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;ProxyPort} annotation and {@code ProxyPortTypeX} is a
	 * constructor of one any class that implements an extension of the
	 * {@code SysMLProxyPort} interface.
	 * 
	 * @see sysmlinjava.ports.SysMLProxyPort
	 */
	protected void createProxyPorts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the proxy port's
	 * constraints.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
		myFirstConstraint = () -> &lt;lamda expression/block statement&gt;;
		myNextConstraint = () -> &lt;lamda expression/block statement&gt;;
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Constraint} annotation and lambda expression/block statement
	 * is a block statement that corresponds to a lambda expression that is an
	 * implementation of the {@code SysMLConstraint} functional interface.<br>
	 * <b>Note:</b>Implementation of this creation operation is very similar to that
	 * of the {@code createActivities()} operation.
	 * 
	 * @see sysmlinjava.common.SysMLConstraint
	 */
	protected void createConstraints()
	{
	}

	/**
	 * Overridable operation that initializes the proxy port's requirements. An
	 * example follows:
	 * 
	 * <pre>
	 * myFirstRequirement = MySysMLRequirements.firstSysMLRequirement;
	 * myNextRequirement = MySysMLRequirements.nextSysMLRequirement;
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Requirement} annotation and
	 * {@code MySysMLRequirements.firstSysMLRequirement} is a reference to an
	 * instance of a {@code SysMLRequirement} in an extension/specialization of the
	 * {@code SysMLRequirements} class.
	 * <p>
	 * <b>Note</b> that proxy port requirements should be declared as described
	 * above, i.e. as references to instantiations of {@code SysMLRequirement}s
	 * declared and initialized in classes that extend/specialize the
	 * {@code SysMLRequirements} class. Requirements should <b>not</b> be
	 * declared/initialized in the block itself. Requirement specifications in the
	 * typical SysML model are collected in a model package where they can be easily
	 * queried and managed. The SysMLinJava approach of collecting requirements in a
	 * single class emulates this method.
	 * 
	 * @see sysmlinjava.requirements.SysMLRequirements
	 */

	protected void createRequirements()
	{
	}

	/**
	 * Overridable operation that creates the dependencies for this proxy port.
	 * Overrides of this operation should perform initializations of variables
	 * annotated as dependencies. Whereas dependencies are type of association, each
	 * dependency should be defined by a field annotated as a
	 * 
	 * <pre>
	 * &#64;Dependency
	 * public APartBlock aPartDependency;
	 * </pre>
	 * 
	 * and initialized by an object reference For example:
	 * 
	 * <pre>
	 * aPartDependency = ((ParentBlock)contextBlock).bPartBlock;
	 * </pre>
	 * 
	 * where {@code aDependency} is the name of a variable that represents a
	 * dependency (trace, usage, etc} of this proxy port on another model element.
	 * Note the assignment value should be a reference to another model element, and
	 * not to a "new" object (constructor).
	 */
	protected void createDependencies()
	{
	}

	/**
	 * Adds a port to the collection of ports that are to operate as "peers" to this
	 * port, i.e. they are "peers" (at the same level) to this port in a protocol
	 * "stack" of ports. This port will be used to invoke operations on an other
	 * block via the interface represented by the port; and this port will be used
	 * by its context block as an interface to the remote port, i.e. this proxy port
	 * with present an interface to its context block that is identical to the
	 * interface of the connected proxy port.
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
	public void addConnectedPortPeer(SysMLProxyPort peer)
	{
		connectedPortsPeers.add(peer);
	}

	/**
	 * Adds a port to the collection of ports, that are to operate as "peers" to
	 * this port, at the specified index in the collection; i.e. the collection of
	 * "peers" (at the same level) to this port in a protocol "stack" of ports. This
	 * port will be used to invoke operations on other blocks via the interface
	 * represented by the port; and this port will be used by its context block as
	 * an interface to the remote port, i.e. this proxy port will present an
	 * interface to its context block that is identical to the interface of the
	 * connected proxy port.
	 * 
	 * @param index the index in the collection of peer ports at which the port is
	 *              to be added
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
	public void addConnectedPortPeer(int index, SysMLProxyPort peer)
	{
		connectedPortsPeers.add(index, peer);
	}

	/**
	 * Returns the connected peer proxy port at the specified index in the
	 * collection of connectec peer proxy ports.
	 * 
	 * @param index index in the collection from which to retrieve the connected
	 *              peer port
	 * @return connected peer proxy port at the specified index
	 */
	public SysMLProxyPort getConnectedPortPeer(int index)
	{
		return connectedPortsPeers.get(index);
	}

	/**
	 * Name of method to create values, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createValuesMethodName = "createValues";
	/**
	 * Name of method to create flows, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createFlowsMethodName = "createFlows";
	/**
	 * Name of method to create full ports, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createProxyPortsMethodName = "createProxyPorts";
	/**
	 * Name of method to create constraints, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createConstraintsMethodName = "createConstraints";
	/**
	 * Name of method to create requirements, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createRequirementsMethodName = "createRequirements";
	/**
	 * Name of method to create dependencies, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createDependenciesMethodName = "createDependencies";
}
