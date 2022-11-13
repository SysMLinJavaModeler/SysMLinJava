package sysmlinjava.connectors;

/**
 * {@code SysMLExternalAssociationBlockConnectorFunction} is part of the
 * SysMLinJava representation of the connector of a SysML association block for
 * a connection with an element located in another {@code BlockContainer}, i.e
 * in another operating system process. The
 * {@code SysMLExternalAssociationBlockConnectorFunction} actually creates the
 * connectors that are identified by the association block. The
 * {@code SysMLExternalAssociationBlockConnectorFunction} is a functional
 * interface which is executed during the operations of the
 * {@code SysMLExternalAssociationBlockConnector} constructor.
 * <p>
 * <b>Note:</b> Within the context of the Java language, a block "connector" is
 * implemented as a simple Java object reference, i.e. a field that is
 * initialized with the reference to another block object. Therefore, the
 * {@code SysMLAssociationBlockConnectorFunction} function performs these
 * initializations of these fields with the appropriate references. In the
 * SysMLinJava implementation of the SysMLFullPort, however, rather than set the
 * field reference directly, an {@code addConnectedPortPeer/Client/Server()}
 * operation on the connected port is invoked to actually set the field
 * reference. The {@code SysMLExternalAssociationBlockConnectorFunction}
 * connects a port on a block in this block container with a "replica" of a
 * block in another container. An example follows.
 * 
 * <pre>
 * {@code
	public class SensorBlocksContainer extends BlockContainer
	{
			:
		&#64;ExternalAssociationConnectorFunction
		public SysMLExternalAssociationBlockConnectorFunction pulseSensorToControllerConnectorFunction;
			:
	
		&#64;Override
		protected void createExternalConnectorFunctions()
		{
				:
			pulseSensorToControllerConnectorFunction = () ->
			{
				pulseSensor.pulseOutPort.addConnectedPortPeer(controller.pulseInPort);
			};
				:
		}
			:
	}
 * }
 * </pre>
 * 
 * @author ModelerOne
 * @see sysmlinjava.blocks.BlockContainer
 * @see sysmlinjava.connectors.SysMLExternalAssociationBlockConnector
 */
public interface SysMLExternalAssociationBlockConnectorFunction extends SysMLAssociationBlockConnectorFunction
{
}
