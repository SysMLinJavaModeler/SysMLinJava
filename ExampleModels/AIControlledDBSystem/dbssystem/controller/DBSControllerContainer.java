package dbssystem.controller;

import java.util.Optional;
import dbssystem.actuators.DBSActuator;
import sysmlinjava.analysis.interactionmessagetransmitter.InteractionMessageTransmitters;
import sysmlinjava.analysis.interactionsequence.InteractionMessageSequenceDisplay;
import sysmlinjava.analysis.interactionsequence.InteractionMessageTransmitter;
import sysmlinjava.annotations.Part;
import sysmlinjava.annotations.blockcontainers.ExternalAssociationConnector;
import sysmlinjava.annotations.blockcontainers.ExternalAssociationConnectorFunction;
import sysmlinjava.annotations.blockcontainers.ExternalPartReplica;
import sysmlinjava.blocks.BlockContainer;
import sysmlinjava.connectors.SysMLExternalAssociationBlockConnector;
import sysmlinjava.connectors.SysMLExternalAssociationBlockConnectorFunction;

/**
 * {@code DBSControllerContainer} is the SysMLinJava model for a container of
 * block instances (parts) to execute in their own operating system process. The
 * container hosts the DBS controller, i.e. an AI-based controller for
 * translation of tremor motion sensor and blood pressure sensor inputs into DBS
 * actuator control outputs. In accordance with the {@code BlockContainer} construct,
 * it also contains a "replica" of the DBS actuator to which it transmits the
 * control data. The replica of the DBS controller is used to create the
 * connectors from the controller to the actual DBS actuator, whose instance is
 * located in another {@code BlockContainer}, i.e. in another operating system
 * process.
 * 
 * @author ModelerOne
 *
 */
public class DBSControllerContainer extends BlockContainer
{
	/**
	 * Part for the DBS controller
	 */
	@Part
	public DBSController controller;

	/**
	 * Replica part for the DBS actuator controlled by the DBSController and located
	 * in another process
	 */
	@ExternalPartReplica
	public DBSActuator dbsActuator;

	/**
	 * Function that makes the connection between the DBS controller and the actual
	 * DBS actuator that is part of another container, i.e. in another operating
	 * system process
	 */
	@ExternalAssociationConnectorFunction
	public SysMLExternalAssociationBlockConnectorFunction controllerToActuatorConnectorFunction;

	/**
	 * Connector that invokes the function to make the connection between the
	 * controller and actuator
	 */
	@ExternalAssociationConnector
	public SysMLExternalAssociationBlockConnector controllerToActuatorConnector;

	/**
	 * Constructor
	 */
	public DBSControllerContainer()
	{
		super("DBSControllerContainer", 0L);
	}

	@Override
	protected void createParts()
	{
		controller = new DBSController();
	}

	@Override
	protected void createExternalPartReplicas()
	{
		dbsActuator = new DBSActuator();
	}

	@Override
	protected void createExternalConnectorFunctions()
	{
		controllerToActuatorConnectorFunction = () ->
		{
			controller.controlOutPort.addConnectedPortPeer(dbsActuator.controlPort);
		};
	}

	@Override
	protected void createExternalConnectors()
	{
		controllerToActuatorConnector = new SysMLExternalAssociationBlockConnector(controller, dbsActuator, controllerToActuatorConnectorFunction);
	}

	@Override
	public void enableInteractionMessageTransmissions()
	{
		InteractionMessageTransmitter transmitter = new InteractionMessageTransmitter(InteractionMessageSequenceDisplay.udpPort, true);
		InteractionMessageTransmitters interactionMessageTransmitters = new InteractionMessageTransmitters(transmitter);

		controller.controlOutPort.messageUtility = Optional.of(interactionMessageTransmitters);
	}
	
	/**
	 * Main for the execution of the container in its own process
	 * 
	 * @param args null
	 */
	public static void main(String[] args)
	{
		DBSControllerContainer container = new DBSControllerContainer();
		container.controller.start();
		try
		{
			Thread.sleep(300_000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		container.controller.stop();
		System.exit(0);
	}
}
