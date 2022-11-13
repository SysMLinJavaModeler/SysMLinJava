package sysmlinjava.blocks;

/**
 * A block for containing parts in separate process
 * <h2>Block as process</h2> {@code BlockContainer} is a SysMLinJava
 * implementation of a software container for executing {@code SysMLBlock}s in
 * separate operating system processes. The {@code BlockContainer} permits
 * execution of models with virtually unlimited scale and performance as the
 * model can be distributed across as many CPUs and as much memory as is
 * available. The {@code BlockContainer} is, itself, a type of
 * {@code SysMLBlock} in that it contains "parts" and the connectors between
 * these parts just as the typical {@code SysMLBlock} does. However, in order to
 * interact with and between other parts that execute in other
 * {@code BlockContainer}s, the {@code BlockContainer} also has "replicas" of
 * the external parts with which its internal parts must be connected to as
 * well. The {@code ExternalPartReplica} is simply an instantiation of the
 * external part type in the context of the {@code BlockContainer}. This replica
 * instantiation is not actually executed. It is used solely to connect internal
 * parts to the external part replica. Specifically, the container's internal
 * parts' full ports are connected to the external part's full ports via the IP
 * address and UDP port values of the replica parts. The full ports of these
 * internal parts recognize the connected port as external to the container and
 * therefore utilize the IP address/UDP port values to communicate with the
 * external part's full port via the UDP protocol instead of via direct
 * {@code receive(signal)} operation invocation as is the case when the full
 * port is in the same operating system process.
 * <h3>Inter-process block connectors</h3> Because some of the full ports in the
 * {@code BlockContainer}'s parts communicate with full ports in other
 * {@code BlockContainer}s' parts (via the UDP/IP protocol), it is important
 * that these full ports be constructed/initialized in their context block with
 * an assigned IP address and UDP port. The {@code SysMLFullPort} has a single
 * constructor dedicated to this type of instantiation. Any full port in any
 * part in the container that is to receive signals from full ports in parts in
 * other containers, i.e. between OS processes, must be constructed this way in
 * the part's {@code createFullPorts()} operation.
 * <h3>Connecting parts in different block containers</h3>
 * Creation/instantiation of the external part replicas should be performed
 * within the {@code createExternalPartReplicas()} operation. Connecting the
 * internal parts to the external parts represented by the replicas should be
 * performed within the {@code createExternalConnectorFunctions()} and
 * {@code createExternalConnectors()} operations. Connection is performed just
 * as it is for connecting full ports in parts in the same
 * process/container/block, i.e. via the {@code SysMLFullPort}'s
 * {@code addConnectedPeer(SysMLFullPort)} operation.
 * <h3>Example</h3> An example of how to create a model in a
 * specialized/extended class of the {@code BlockContainer} follows. Examples of
 * how to create the elements in an extension/specialization of the
 * {@code BlockContainer} are provided in the comment blocks for the operations
 * declared below. The examples are based on this example declaration of
 * container parts and connectors.
 * 
 * <pre>
	&#64;Part
	public YankeeBlock yankee;
	&#64;Part
	public ZuluBlock zulu;
	
	&#64;ExternalPartReplica
	public AlphaBlock alpha;
	&#64;ExternalPartReplica
	public BravoBlock bravo;
	
	&#64;AssociationConnectorFunction
	public SysMLAssociationBlockConnectorFunction connectorFunction;
	
	&#64;AssociationConnector
	public SysMLAssociationBlockConnector connector;
	
	&#64;ExternalAssociationConnectorFunction
	public SysMLExternalAssociationBlockConnectorFunction externalConnectorFunction;
	
	&#64;ExternalAssociationConnector
	public SysMLExternalAssociationBlockConnector externalConnector;
   
	&#64;Override
	protected void createParts()
	{
		yankee = new Yankee();
		zulu = new Zulu();
	}
	
	&#64;Override
	protected void createExternalPartReplicas()
	{
		alpha = new AlphaBlock();
		bravo = new BravoBlock();
	}
	
	&#64;Override
	protected void createExternalConnectorFunctions()
	{
		externalConnectorFunctionYankeeToAlpha = () -> yankee.outPort.addConnectedPortPeer(alpha.inPort);
		externalConnectorFunctionZuluToBravo   = () ->   zulu.outPort.addConnectedPortPeer(bravo.inPort);
	}
	
	&#64;Override
	protected void createExternalConnectors()
	{
		externalConnectorYankeeToAlpha = new SysMLExternalAssociationBlockConnector(yankee, alpha, externalConnectorFunctionYankeeToAlpha);
		externalConnectorZuluToBravo   = new SysMLExternalAssociationBlockConnector(  zulu, bravo, externalConnectorFunctionZuluToBravo);
	}
 </pre>
 * 
 * @author ModelerOne
 *
 */
public abstract class BlockContainer extends SysMLBlock
{
	/**
	 * Constructor
	 * 
	 * @param name name of the container
	 * @param id   unique id for the container
	 */
	public BlockContainer(String name, Long id)
	{
		super(name, id);
	}

	/**
	 * Creates the properties of the {@code BlockContainer}. This method overrides
	 * the {@code createProperties()} method in {@code SysMLBlock} so as to insert
	 * the creation of the external part "replicas" and external connectors of the
	 * block container in the proper sequence of creation method calls.
	 * <p>
	 * Note that extensions of the {@code BlockContainer} will typically not need to
	 * create such elements as full-ports, activities, constraints, etc. as the
	 * purpose of the {@code BlockContainer} is solely to provide a "part" hosting
	 * process (JVM) for multi-process model executions. This means that for the
	 * typical {@code BlockContainer} calls to create these other elements are
	 * superfluous. Nevertheless, they are included in this
	 * {@code createProperties()} method for those rare instances where such
	 * elements may be needed.
	 */
	@Override
	protected void createProperties()
	{
		createClasses();
		createValues();
		createFlows();
		createReferences();
		createParts();
		createFullPorts();
		createProxyPorts();
		createActivities();
		createConstraints();
		createEvents();
		createStateMachine();
		createRequirements();
		createConstraintBlocks();
		createConstraintParameterConnectorFunctions();
		createConstraintParameterConnectors();
		createConnectorFunctions();
		createConnectors();
		createDependencies();
		createElementGroups();
		createExternalPartReplicas();
		createExternalConnectorFunctions();
		createExternalConnectors();
		enableInteractionMessageTransmissions();
	}

	/**
	 * Overridable operation to create the {@code SysMLBlock}-based "parts"
	 * contained in the container. Whereas the {@code BlockContainer} is, itself, an
	 * extension of the {@code SysMLBlock}, this operation is also an override of
	 * the same method in the {@code SysMLBlock}. An example of how to create the
	 * parts is as follows:
	 * 
	 * <pre>
	 * yankeeBlock = new YankeeBlock();
	 * zuluBlock = new ZuluBlock();
	 * </pre>
	 */
	@Override
	protected void createParts()
	{
	}

	/**
	 * Overridable operation to create the {@code SysMLBlock}-based connector
	 * "functions" that construct the connections, if any, between the parts
	 * contained in the container. Whereas the {@code BlockContainer} is, itself, an
	 * extension of the {@code SysMLBlock}, this operation is also an override of
	 * the same method in the {@code SysMLBlock}. An example of how to create the
	 * connector functions is as follows:
	 * 
	 * <pre>
	 * connectorFunction = () ->
	 * {
	 * 	yankeeBlock.anOutPort.addConnectedPortPeer(zuluBlock.anInPort);
	 * };
	 * </pre>
	 */
	@Override
	protected void createConnectorFunctions()
	{
	}

	/**
	 * Overridable operation to create the {@code SysMLBlock}-based connectors, if
	 * any, between the parts contained in the container. Whereas the
	 * {@code BlockContainer} is, itself, an extension of the {@code SysMLBlock},
	 * this operation is also an override of the same method in the
	 * {@code SysMLBlock}. An example of how to create the connectors is as follows:
	 * \ *
	 * 
	 * <pre>
	 * connector = new SysMLAssociationBlockConnector(yankeeBlock, zuluBlock, connectorFunction);
	 * </pre>
	 */
	@Override
	protected void createConnectors()
	{
	}

	/**
	 * Overridable operation to create the external part replicas used to create the
	 * connectors between parts in this container and external parts in other
	 * containers. An example of how to create the external part replicas is as
	 * follows:
	 * 
	 * <pre>
	 * alphaBlock = new AlphaBlock();
	 * bravoBlock = new BravoBlock();
	 * </pre>
	 */
	protected void createExternalPartReplicas()
	{
	}

	/**
	 * Overridable operation to create the functions that make the connections
	 * between parts that are internal to this container and external parts in other
	 * containers. The creation of the connectors is essentially the same as it is
	 * between parts internal to the connector, i.e. invoking the
	 * {@code <internal part's full port>.addConnectedPortPeer(external part replica's full port)}
	 * operation for each internal part connected to an external part. An example of
	 * how to create the connector function is as follows:
	 * 
	 * <pre>
	 * externalConnectorFunction = () ->
	 * {
	 * 	yankeeBlock.someOutPort.addConnectedPortPeer(alphaBlock.someInPort);
	 * 	zuluBlock.anotherOutPort.addConnectedPortPeer(bravoBlock.anotherInPort);
	 * };
	 * </pre>
	 */
	protected void createExternalConnectorFunctions()
	{
	}

	/**
	 * Overridable operation to create the connectors between parts that are
	 * internal to this container and parts that are external in other containers.
	 * The creation of the connectors is essentially the same as it is between parts
	 * internal to the connector, i.e. creating the
	 * {@code SysMLExternalAssociationBlockConnector} with the
	 * {@code SysMLExternalAssociationBlockConnectorFunction} as an initializing
	 * argument. The difference is the connectors connecting function uses the
	 * "replica" parts for those parts that are located in another block container,
	 * i.e. in another process. An example of how to create the connector is as
	 * follows:
	 * 
	 * <pre>
	 * externalConnector = new SysMLExternalAssociationBlockConnector(List.of(yankeeBlock, zuluBlock), List.of(alphaBlock, bravoBlock), externalConnectorFunction);
	 * </pre>
	 */
	protected void createExternalConnectors()
	{
	}
}
