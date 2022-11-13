package sysmlinjava.connectors;

import sysmlinjava.common.SysMLInterface;

/**
 * {@code SysMLAssociationBlockConnectorFunction} is part of the SysMLinJava
 * representation of the SysML connector associated with an association. In
 * SysMLinJava, connectors are simply Java references to other classes and
 * blocks, so the {@code SysMLAssociationBlockConnectorFunction} is a function
 * that instantiates/initializes the reference.
 * {@code SysMLAssociationBlockConnectorFunction} is invoked by the
 * {@code SysMLAssociationBlockConnector} during its
 * construction/initialization.
 * <p>
 * The {@code SysMLAssociationBlockConnectorFunction} should be declared as a
 * field in the extended {@code SysMLBlock} class that contains both of the
 * connected elements. The field should be annotated with the
 * {@code AssociationConnectorFunction} annotation. It should then be
 * implemented as an instance of a Lambda function in the override of the
 * SysMLBlock's {@code createConnectorFunctions()} operation. An example
 * follows.
 * 
 * <pre>
 * {@code
	public class SystemDelta extends SysMLBlock
	{
			:
		&#64;AssociationConnectorFunction
		public SysMLAssociationBlockConnectorFunction deltaComputerToSwitchRouterConnectorFunction;
			:
		&#64;Override
		protected void createConnectorFunctions()
		{
				:
			deltaComputerToSwitchRouterConnectorFunction = () ->
			{
				deltaComputer.ethernetPort.addConnectedPortPeer(switchRouter.ethernetPort0);
				switchRouter.ethernetPort0.addConnectedPortPeer(deltaComputer.ethernetPort);
			};
				:
		}
	}
 * }
 * </pre>
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLAssociationBlockConnectorFunction extends SysMLInterface
{
	/**
	 * Performs the connections (field initializations) that are represented by the
	 * containing association block.
	 */
	void connect();
}