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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sysmlinjava.actions.SysMLAction;
import sysmlinjava.states.StateBehaviorContext;

/**
 * SysMLinJava's representation of SysML's proxy-port.
 * <h2>SysML proxy port to invoke behaviors on other part or
 * ports</h2>{@code SysMLProxyPort} is an extension of the basic
 * {@code SysMLClass}. It extends the class with an optional implementing
 * context part or port, i.e. a part or port that implements the
 * interface that is realized by the proxy-port. It also
 * extends the class with optional connections to other proxy ports and uses
 * these to invoke interface operations on the connected part or ports.
 * <h3>As part of calling part or port ..</h3> The {@code SysMLProxyPort} is
 * configured in one of two ways - as a port on the <b>calling</b> part or port
 * or as a port on the <b>called</b> part or port. As a port on the calling part
 * or port, the {@code SysMLProxyPort} is connected to a port on the called part
 * or port, its conjugate port. It uses this connection to invoke an operation
 * of the {@code {@code SysMLPart} or {@code SysMLPort}Interface} it implements
 * via the connected proxy port.
 * <h3>As part of called part or port ..</h3> As a port on the called part or
 * port, the {@code SysMLProxyPort} has a reference to the called part or port
 * that realizes the {@code {@code SysMLPart} or {@code SysMLPort}Interface}. It
 * uses this reference to actually invoke the behaviors (operations and/or state
 * machine) of the {@code {@code SysMLPart} or {@code SysMLPort}}. As a port on
 * the called part or port, the {@code SysMLProxyPort} is considered to be a
 * "conjugate" port, as defined in SysML. In either case, the SysMLinJava
 * representation of the proxy port is consistent with the standard SysML
 * proxy-port in that the {@code SysMLProxyPort} is, in fact, a proxy for the
 * {@code SysMLPart} or {@code SysMLPort} that finally implements the interface
 * operations.
 * <h3>Utility for generating messages for sequence diagrams</h3> Finally, the
 * {@code SysMLProxyPort} base class provides capabilities to perform utility
 * functions during the performance of transitions between and within states.
 * The {@code SysMLProxyPort} field variable {@code messageUtility} of interface
 * type {@code InteractionMessageUtility} is invoked whenever a message is
 * transmitted between part or ports via their full ports. Message information
 * such as source part or port, message type, destination part or port, etc. are
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
 * in a different {@code PartContainer}, i.e. operation calls across
 * processes/JVMs are not supported. As a result, connected proxy ports must
 * reside in the same operating system process (JVM), i.e. in the same
 * {@code PartContainer}.
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.SysMLPart#createPorts()
 * @see sysmlinjava.views.interactionssequencediagram.InteractionMessageTransmitters
 */
public abstract class SysMLProxyPort extends SysMLAction
{
	/**
	 * {@code SysMLPart}-based part or {@code SysMLPort}-based port that contains
	 * this {@code SysMLProxyPort}. The context can be used to invoke a part or port
	 * behavior when a method of the proxy port is called.
	 */
	public StateBehaviorContext context;
	/**
	 * {@code SysMLPart}-based part or {@code SysMLPort}-based port that implements
	 * the operations specified by the port's {@code SysMLPart} or
	 * {@code SysMLPort}Interface. This part or port is used to invoke a part or
	 * port's behavior when a method of the proxy port is called.
	 */
	public Optional<StateBehaviorContext> implementingContext;
	/**
	 * Ports to which this port is connected as peer ports
	 */
	protected List<SysMLProxyPort> connectedPortsPeers;
	/**
	 * Ports to which this port is connected as virtual peer ports
	 */
	protected List<SysMLProxyPort> virtualConnectedPortsPeers;

	/**
	 * Optional implementation of the {@code InteractionMessageUtility} interface.
	 * If present, the {@code messageUtility} is invoked for every message
	 * (method/action call) that is invoked by this proxy port.
	 * 
	 * @see InteractionMessageUtility
	 */
	public Optional<InteractionMessageUtility> messageUtility;

	/**
	 * Constructor with maximal specification.
	 * 
	 * @param context             The {@code {@code SysMLPart} or {@code SysMLPort}}
	 *                            which provides the context for this port. It is a
	 *                            {@code {@code SysMLPart} or {@code
	 *                            SysMLPort}}-based part or port that is the
	 *                            containing (context) part or port of this
	 *                            (possibly nested) {@code SysMLProxyPort}. The
	 *                            context part is used to invoke a part or port
	 *                            behavior when a method of the proxy port is
	 *                            called.
	 * @param implementingContext Optional {@code {@code SysMLPart} or {@code
	 *                            SysMLPort}} that is implementing the operations
	 *                            specified by the extended port's {@code {@code
	 *                            SysMLPart} or {@code SysMLPort}Interface}. This
	 *                            could be the same as the context part or a "part"
	 *                            within the context part. It is a {@code {@code
	 *                            SysMLPart} or {@code SysMLPort}}-based part or
	 *                            port that implements the operations specified by
	 *                            the port's {@code {@code SysMLPart} or {@code
	 *                            SysMLPort}Interface}. This part or port is used to
	 *                            invoke a part or port's (possibly part's) behavior
	 *                            when a method of the proxy port is called.
	 * @param name                Unique name for this proxy port
	 * @param id                  An arbitrary index to be associated with this
	 *                            port. For example, an array index for an array of
	 *                            ports.
	 */
	public SysMLProxyPort(StateBehaviorContext context, Optional<StateBehaviorContext> implementingContext, String name, Long id)
	{
		super(name, id);
		this.context = context;
		this.implementingContext = implementingContext;
		connectedPortsPeers = new ArrayList<>();
		messageUtility = Optional.empty();

		createItems();
		createProxyPorts();
		createDependencies();
	}

	/**
	 * Overridable operation that creates and initializes the proxy port's items. An
	 * example is as follows:
	 * 
	 * <pre>
		&#64;Item
		ItemTypeA myFirstItem;
		&#64;Item
		ItemTypeA myNextItem;
	
		&#64;Override
		protected void createItems()
		{
			myFirstItem = new ItemTypeA(&lt;initializer&gt;);
			myNextItem = new ItemTypeB(&lt;initializer&gt;);
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.items.SysMLItem
	 */
	protected void createItems()
	{
	}

	/**
	 * Overridable operation that creates and initializes the proxy port's nested
	 * proxy ports. An example follows.
	 * 
	 * <pre>{@code
			public class MyProxyPort extends SysMLProxyPort
			{
				&#64;ProxyPort
				ProxyPortA myFirstProxyPort;
				&#64;ProxyPort
				ProxyPortB myNextProxyPort;
	
				&#64;Override
				protected void createProxyPorts()
				{
					myFirstProxyPort = new ProxyPortA(&lt;initializer&gt;);
					myNextProxyPort = new ProxyPortB(&lt;initializer&gt;);
				}
			}}</pre>
	 * 
	 * @see sysmlinjava.ports.SysMLProxyPort
	 */
	protected void createProxyPorts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the constraint function
	 * (lambda expressiuns) for the proxy port's constraints. An example is as
	 * follows:
	 * 
	 * <pre>{@code
		public class MyProxyPort extends SysMLProxyPort
		{
			&#64;Attribute
			int totalReadErrors;
			&#64;Attribute
			int totalWriteErrors;
			&#64;Attribute
			int totalErrors;
	
			&#64;FunctionalInterface
			public interface ErrorsConstraintFunction extends SysMLConstraintFunction
			{
				int totalErrors(int readErrors, int writeErrors);
			}
	
			&#64;ConstraintFunction
			ErrorsConstraintFunction errorsConstraintFunction;
	
			void createConstraintFunctions()
			{
				errorsConstraintFunction = (ErrorsConstraintFunction)(readErrors, writeErrors) -> { return totalReadErrors + totalWriteErrors;});
			}
		}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraint
	 */
	@Override
	protected void createConstraintFunctions()
	{
	}

	/**
	 * Overridable operation that creates/initializes the constraint texts
	 * (documentations) for the proxy port's constraints. An example is as follows:
	 * 
	 * <pre>{@code
			public class MyProxyPort extends SysMLProxyPort
			{
					:
				&#64;Attribute
				int totalReadErrors;
				&#64;Attribute
				int totalWriteErrors;
				&#64;Attribute
				int totalErrors;
					:
				&#64;ConstraintText
				SysMLDocumentation errorsConstraintText;
					:
				void createConstraintTexts()
				{
					errorsConstraintText = new SysMLDOcumentation("totalErrors = totalReadErrors + totalWriteErrors");
				}
					:
			}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraint
	 */
	@Override
	protected void createConstraintTexts()
	{
	}

	/**
	 * Overridable operation that creates/initializes the proxy port's constraints.
	 * An example follows.
	 * 
	 * <pre>{@code
		public class MyProxyPort extends SysMLProxyPort
		{
			&#64;Attribute
			int totalReadErrors;
			&#64;Attribute
			int totalWriteErrors;
			&#64;Attribute
			int totalErrors;
	
			&#64;ConstraintFunction
			ErrorsConstraintFunction errorsConstraintFunction;
			&#64;ConstraintText
			SysMLDocumentation errorsConstraintText;
			&#64;Constraint
			SysMLConstraint errorsConstraint;
	
			&#64;FunctionalInterface
			public interface ErrorsConstraintFunction extends SysMLConstraintFunction
			{
				int totalErrors(int readErrors, int writeErrors);
			}
	
			void createConstraintFunctions()
			{
				errorsConstraintFunction = (ErrorsConstraintFunction)(readErrors, writeErrors) -> { return totalReadErrors + totalWriteErrors;});
			}
	
			void createConstraintTexts()
			{
				errorsConstraintText = new SysMLDocumentation("totalErrors = totalReadErrors + totalWriteErrors");
			}
			
			void createConstraints()
			{
				errorsConstraint = new SysMLConstraint(Optional.of(errorsConstraintFunction), errorsConstraintText, "Errors", 0L);
			}
		}}</pre>
	 * 
	 * @see sysmlinjava.constraint.SysMLConstraintFunction
	 * @see sysmlinjava.annotations.SysMLDocumentation
	 */
	@Override
	protected void createConstraints()
	{
	}

	/**
	 * Overridable operation that initializes the proxy port's requirements. An
	 * example follows:
	 * 
	 * <pre>{@code
				public class MyProxyPort extends SysMLProxyPort
				{
						:
					&#64;Requirement
					MyFirstSysMLRequirement myFirstRequirement;
					&#64;Requirement
					MyNextSysMLRequirement myNexRequirement;
						:
					protected void createRequirements()
					{
						myFirstRequirement = MySysMLRequirements.firstSysMLRequirement;
						myNextRequirement = MySysMLRequirements.nextSysMLRequirement;
					}
						:
				}}</pre>
	 * 
	 * <b>Note</b> that proxy port requirements should be declared as described
	 * above, i.e. as references to instantiations (variables with class scope) of
	 * {@code SysMLRequirement}s declared and initialized in classes that
	 * extend/specialize the {@code SysMLRequirementsCollection} class. Requirements
	 * should <b>not</b> be declared/initialized in the proxy port itself.
	 * Requirements in the typical SysMLinJava model are declared and initialized in
	 * a {@code SysMLRequirementsCollection} class where they can be easily queried
	 * and managed. This SysMLinJava approach of collecting requirements in a single
	 * class emulates the standard SysML approach of collecting all specified
	 * requirements in a model "package".
	 * 
	 * @see sysmlinjava.requirements.SysMLRequirementsCollection
	 */

	@Override
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
	 * public APartPart aPartDependency;
	 * </pre>
	 * 
	 * and initialized by an object reference For example:
	 * 
	 * <pre>
	 * aPartDependency = ((ParentPart) contextPart).bPartPart;
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
	 * part or port via the interface represented by the port; and this port will be
	 * used by its context part or port as an interface to the remote port, i.e.
	 * this proxy port with present an interface to its context part or port that is
	 * identical to the interface of the connected proxy port.
	 * 
	 * @param peer port (likely in another part or port) to be the peer port to this
	 *             port. The peer should be declared as a java field access variable
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
	 * port will be used to invoke operations on other part or ports via the
	 * interface represented by the port; and this port will be used by its context
	 * part or port as an interface to the remote port, i.e. this proxy port will
	 * present an interface to its context part or port that is identical to the
	 * interface of the connected proxy port.
	 * 
	 * @param index the index in the collection of peer ports at which the port is
	 *              to be added
	 * @param peer  port (likely in another part or port) to be the peer port to
	 *              this port. The peer should be declared as a java field access
	 *              variable (a.k.a "nested element path" in SysML) with scope
	 *              element names as needed. An example of how to invoke/call this
	 *              method is as follows:
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
	 * Name of method to create proxy port's items, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createItemsMethodName = "createItems";
	/**
	 * Name of method to create items's attributes, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createAttributesMethodName = "createAttributes";
	/**
	 * Name of method to create full ports, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createProxyPortsMethodName = "createProxyPorts";
	/**
	 * Name of method to create constraint functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintFunctionsMethodName = "createConstraintFunctions";
	/**
	 * Name of method to create constraint texts, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintTextsMethodName = "createConstraintTexts";
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
	/**
	 * Name of method to create items's customized metadata, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createCustomMetadataMethodName = "createCustomMetadatas";
	/**
	 * Name of method to add connected port peer, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String addConnectedPortPeerMethodName = "addConnectedPortPeer";

	/**
	 * Overridable operation for which the overriding operation should optionally
	 * create/initialize the {@code messageUtility} field variable. If the variable
	 * is present, it will be used to invoke the operation of the
	 * {@code InteractionMessageUtility} interface for use in interaction diagram
	 * displays.
	 * 
	 * @see sysmlinjava.ports.InteractionMessageUtility
	 * @see sysmlinjava.views.interactionssequencediagram.InteractionMessageTransmitter
	 */
	protected void createInteractionMessageUtility()
	{
	}
}
