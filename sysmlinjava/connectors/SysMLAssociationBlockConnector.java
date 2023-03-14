package sysmlinjava.connectors;

import java.util.List;
import sysmlinjava.annotations.AssociationConnectorFunction;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava representation of the SysML association block and its connector.
 * The {@code SysMLAssociationBlockConnector} specifies the blocks that
 * participate in the association as well as the
 * {@code SysMLAssociationBlockConnectorFunction} that actually creates the
 * connectors that are identified by the association block.
 * <p>
 * The {@code SysMLAssociationBlockConnectorFunction} is a functional interface
 * which is executed during the operations of the constructor.
 * <p>
 * <b>Note:</b> Within the context of the Java language, a block "connector" is
 * implemented as a simple Java object reference, i.e. a field that is
 * initialized with the reference to another block object. Therefore, the
 * {@code SysMLAssociationBlockConnector} realizes a connector(s) by executing a
 * {@code SysMLAssociationBlockConnectorFunction} function that performs these
 * initializations of these fields with the appropriate references. The
 * connector function invokes a method on the object at the source of the
 * connector to add the object at the destination of the connector to its set of
 * connections. For example, the connector function connects a pair of full
 * ports by invoking the {@code addConnectedPortPeer(<destination>)} operation
 * of the source port with the destination port for the argument.
 * 
 * @author ModelerOne
 *
 */
public class SysMLAssociationBlockConnector extends SysMLClass
{
	/**
	 * SysMLinJava representation of the SysML's participant0 in the association.
	 */
	public SysMLBlock participant0;
	/**
	 * SysMLinJava representation of the SysML's participant1 in the association.
	 */
	public SysMLBlock participant1;
	/**
	 * SysMLinJava representation of the SysML's participant0 in the association for
	 * more than one participant.
	 */
	public List<? extends SysMLBlock> participants0;
	/**
	 * SysMLinJava representation of the SysML's participant1 in the association for
	 * more than one participant.
	 */
	public List<? extends SysMLBlock> participants1;
	/**
	 * The function to be performed to finally realize the connector(s). The
	 * connector function must be an instance of a functional interface with a
	 * lambda expression as its value. The {@code connect()} operation should be
	 * implemented in the context in which the participant objects exist, i.e. as a
	 * lambda expression in the {@code createConnectorFunctions()} operation in the
	 * same block in which the participant objects fields exist.
	 * <p>
	 * <b>Note:</b>Tools and libraries in the current version of SysMLinJava only
	 * recognize the functional interface (lambda expression) implementation of the
	 * connector function.
	 */
	@AssociationConnectorFunction
	public SysMLAssociationBlockConnectorFunction connectorFunction;

	/**
	 * Constructor for two participant, i.e. single connector realizations of the
	 * association.
	 * 
	 * @param participant0      One of the participants in the association, i.e. at
	 *                          one end of the connector
	 * @param participant1      The other participant in the association, i.e. at
	 *                          the other end of the connector
	 * @param connectorFunction The implementation of the interface that performs
	 *                          the connection, i.e. creates the connector between
	 *                          the participants.
	 */
	public SysMLAssociationBlockConnector(SysMLBlock participant0, SysMLBlock participant1, SysMLAssociationBlockConnectorFunction connectorFunction)
	{
		this.participant0 = participant0;
		this.participant1 = participant1;
		connectorFunction.connect();
	}

	/**
	 * Constructor for two participant, i.e. single connector realizations of the
	 * association.
	 * 
	 * @param participants0      Set of the participants in the association, i.e. at
	 *                           one end of the connectors
	 * @param participants1      The other set of participants in the association,
	 *                           i.e. at the other end of the connectors
	 * @param connectorsFunction The implementation of the interface that performs
	 *                           the connections, i.e. creates the connectors
	 *                           between the participants.
	 */
	public SysMLAssociationBlockConnector(List<? extends SysMLBlock> participants0, List<? extends SysMLBlock> participants1, SysMLAssociationBlockConnectorFunction connectorsFunction)
	{
		this.participants0 = participants0;
		this.participants1 = participants1;
		connectorsFunction.connect();
	}
}
