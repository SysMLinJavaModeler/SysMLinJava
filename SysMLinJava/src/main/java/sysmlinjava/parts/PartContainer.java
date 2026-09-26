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
package sysmlinjava.parts;

/**
 * A Part that executes in a separate process.
 * <h2>Part as process</h2> {@code PartContainer} is a SysMLinJava
 * implementation of a software container for executing {@code SysMLPart}s in
 * separate operating system processes. The {@code PartContainer} permits
 * execution of models with virtually unlimited scale and performance as the
 * model can be distributed across as many computers and CPUs and as much memory
 * as is available. The {@code PartContainer} is, itself, a type of
 * {@code SysMLPart} in that it contains "parts" and the connectors between
 * these parts just as the typical {@code SysMLPart} does. However, in order to
 * interact with and between other parts that execute in other
 * {@code PartContainer}s in other processes and/or other computers, the
 * {@code PartContainer} also has "replicas" of the external parts with which
 * its internal parts must be connected to as well. The
 * {@code ExternalPartReplica} is simply an instantiation of the external part
 * type in the context of the {@code PartContainer}. This replica instantiation
 * is not actually executed. It is used solely to connect internal parts to the
 * external part replica. Specifically, the container's internal parts' ports
 * are connected to the external part's ports via the IP address and UDP port
 * values of the replica parts. The ports of these internal parts recognize the
 * connected port as external to the container and therefore utilize the IP
 * address/UDP port values to communicate with the external part's port via the
 * UDP protocol instead of via direct {@code receive(signal)} operation
 * invocation as is the case when the port is in the same operating system
 * process.
 * <h3>Inter-process Part connectors</h3> Because some of the ports in the
 * {@code PartContainer}'s parts communicate with ports in other
 * {@code PartContainer}s' parts (via the UDP/IP protocol), it is important that
 * these ports be constructed/initialized in their context Part with an assigned
 * IP address and UDP port. The {@code SysMLFullPort} has a single constructor
 * dedicated to this type of instantiation. Any port in any part in the
 * container that is to receive signals from ports in parts in other containers,
 * i.e. between OS processes, must be constructed this way in the part's
 * {@code createFullPorts()} operation.
 * <h3>Connecting parts in different Part containers</h3> Creation/instantiation
 * of the external part replicas should be performed within the
 * {@code createExternalPartReplicas()} operation. Connecting the internal parts
 * to the external parts represented by the replicas should be performed within
 * the {@code createExternalConnectorFunctions()} and
 * {@code createExternalConnectors()} operations. Connection is performed just
 * as it is for connecting ports in parts in the same process/container/Part,
 * i.e. via the {@code SysMLPort}'s {@code addConnectedPeer(SysMLPort)}
 * operation.
 * <h3>Example</h3> An example of how to create a model in a
 * specialized/extended class of the {@code PartContainer} follows. Examples of
 * how to create the elements in an extension/specialization of the
 * {@code PartContainer} are provided in the comment blocks for the operations
 * declared below. The examples are based on this example declaration of
 * container parts and connectors.
 * 
 * <pre>
 * &#64;Part
 * public YankeePart yankee;
 * &#64;Part
 * public ZuluPart zulu;
 * 
 * &#64;ExternalPartReplica
 * public AlphaPart alpha;
 * &#64;ExternalPartReplica
 * public BravoPart bravo;
 * 
 * &#64;AssociationConnectorFunction
 * public SysMLAssociationPartConnectorFunction connectorFunction;
 * 
 * &#64;AssociationConnector
 * public SysMLAssociationPartConnector connector;
 * 
 * &#64;ExternalAssociationConnectorFunction
 * public SysMLExternalAssociationPartConnectorFunction externalConnectorFunction;
 * 
 * &#64;ExternalAssociationConnector
 * public SysMLExternalAssociationPartConnector externalConnector;
 * 
 * &#64;Override
 * protected void createParts()
 * {
 * 	yankee = new Yankee();
 * 	zulu = new Zulu();
 * }
 * 
 * &#64;Override
 * protected void createExternalPartReplicas()
 * {
 * 	alpha = new AlphaPart();
 * 	bravo = new BravoPart();
 * }
 * 
 * &#64;Override
 * protected void createExternalConnectorFunctions()
 * {
 * 	externalConnectorFunctionYankeeToAlpha = () -> yankee.outPort.addConnectedPortPeer(alpha.inPort);
 * 	externalConnectorFunctionZuluToBravo = () -> zulu.outPort.addConnectedPortPeer(bravo.inPort);
 * }
 * 
 * &#64;Override
 * protected void createExternalConnectors()
 * {
 * 	externalConnectorYankeeToAlpha = new SysMLExternalAssociationPartConnector(yankee, alpha, externalConnectorFunctionYankeeToAlpha);
 * 	externalConnectorZuluToBravo = new SysMLExternalAssociationPartConnector(zulu, bravo, externalConnectorFunctionZuluToBravo);
 * }
 * </pre>
 * 
 * @author ModelerOne
 */
public abstract class PartContainer extends SysMLPart
{
	/**
	 * Constructor for the creation of the container's parts, external part
	 * "replicas", and external connectors of the Part container in the proper
	 * sequence.
	 * <p>
	 * 
	 * @param name name of the container
	 * @param id   unique id for the container
	 */
	public PartContainer(String name, Long id)
	{
		super(name, id);
	}

	/**
	 * Overridable operation to create the containers parts and external part
	 * replicas used to create the connectors between parts in this container and
	 * external parts in other containers. An example of how to create the external
	 * part replicas is as follows:
	 * 
	 * <pre>
	 * alphaPart = new AlphaPart();
	 * bravoPart = new BravoPart();
	 * </pre>
	 * 
	 * As described above, the part "replica" instances are just replicas of the part
	 * instances that are located in other {@code PartContainer}s running in other
	 * processes on the same or different computers or CPUs. The replicas are
	 * instantiated solely to provide a local context in which the Part container
	 * can be compiled with all of its parts. Designation of the part of a replica
	 * is used to form the connectors with the actual part located in another Part
	 * container in another process, possibly in another computer.
	 */
	@Override
	protected void createParts()
	{
	}

	/**
	 * Overridable operation to create the connectors between parts that are
	 * internal to this container and parts that are external in other containers.
	 * The creation of the connectors is essentially the same as it is between parts
	 * internal to the connector, i.e. creating the
	 * {@code SysMLExternalAssociationPartConnector} with the
	 * {@code SysMLExternalAssociationPartConnectorFunction} as an initializing
	 * argument. The difference is the connectors connecting function uses the
	 * "replica" parts for those parts that are located in another Part container,
	 * i.e. in another process. An example of how to create the connector is as
	 * follows:
	 * 
	 * <pre>
	 * externalConnector = new SysMLExternalAssociationPartConnector(List.of(yankeePart, zuluPart), List.of(alphaPart, bravoPart), externalConnectorFunction);
	 * </pre>
	 */
	@Override
	protected void createFlowConnectors()
	{
	}
}
