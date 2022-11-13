package sysmlinjava.connectors;

import sysmlinjava.blocks.SysMLBlock;

/**
 * {@code SysMLExternalAssociationBlockConnector} is the SysMLinJava
 * representation of the connector of a SysML association block for a connection
 * with an element located in another {@code BlocContainer}, i.e in another
 * operating system process. The {@code SysMLExternalAssociationBlockConnector}
 * specifies the blocks that participate in the association as well as the
 * {@code SysMLExternalAssociationBlockConnectorFunction} that actually creates
 * the connectors that are identified by the association block. The
 * {@code SysMLExternalAssociationBlockConnectorFunction} is a functional
 * interface which is executed during the operations of the constructor.
 * <p>
 * <b>Note:</b> Within the context of the Java language, a block "connector" is
 * implemented as a simple Java object reference, i.e. a field that is
 * initialized with the reference to another block object. Therefore, the
 * {@code SysMLAssociationBlockConnector} realizes a connector(s) by executing a
 * {@code SysMLAssociationBlockConnectorFunction} function that performs these
 * initializations of these fields with the appropriate references. In the
 * SysMLinJava implementation of the {@code SysMLFullPort}, however, rather than
 * set the field reference directly, an
 * {@code addConnectedPortPeer/Client/Server()} operation on the connected port
 * is invoked to actually set the field reference. The
 * {@code SysMLExternalAssociationBlockConnector} connects a port on a block in
 * this block container with a "replica" of a block in another container. An
 * example follows.
 * 
 * <pre>
 * {@code
	public class DBSSensorsContainer extends BlockContainer
	{
			:
		&#64;ExternalAssociationConnector
		public SysMLExternalAssociationBlockConnector pulseSensorToControllerConnector;
			:
	 	&#64;Override
		protected void createExternalConnectors()
		{
				:
			pulseSensorToControllerConnector = new SysMLExternalAssociationBlockConnector(List.of(pulseSensor), List.of(dbsController), pulseSensorToControllerConnectorFunction);
				:
		}
			:
	}
 * }
 * </pre>
 * 
 * @author ModelerOne
 * @see sysmlinjava.blocks.BlockContainer
 * @see sysmlinjava.connectors.SysMLExternalAssociationBlockConnectorFunction
 */
public class SysMLExternalAssociationBlockConnector extends SysMLAssociationBlockConnector
{
	/**
	 * Constructor for one or more participants, i.e multiple connector realizations
	 * of the association.
	 * 
	 * @param participant0 participant0 in the association, i.e. participant at one
	 *                     end of the connectors
	 * @param participant1 participants1 in the association, i.e. participant at the
	 *                     other end of the connectors
	 * @param connector    The implementation of the functional interface that
	 *                     performs the connection, i.e. creates the connectors
	 *                     between the participants.
	 */
	public SysMLExternalAssociationBlockConnector(SysMLBlock participant0, SysMLBlock participant1, SysMLAssociationBlockConnectorFunction connector)
	{
		super(participant0, participant1, connector);
	}

}
