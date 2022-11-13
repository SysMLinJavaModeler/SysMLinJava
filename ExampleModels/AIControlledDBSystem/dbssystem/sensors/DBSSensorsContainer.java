
package dbssystem.sensors;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import dbssystem.controller.DBSController;
import sysmlinjava.analysis.interactionmessagetransmitter.InteractionMessageTransmitters;
import sysmlinjava.analysis.interactionsequence.InteractionMessageSequenceDisplay;
import sysmlinjava.analysis.interactionsequence.InteractionMessageTransmitter;
import sysmlinjava.annotations.AssociationConnector;
import sysmlinjava.annotations.AssociationConnectorFunction;
import sysmlinjava.annotations.Part;
import sysmlinjava.annotations.blockcontainers.ExternalAssociationConnector;
import sysmlinjava.annotations.blockcontainers.ExternalAssociationConnectorFunction;
import sysmlinjava.annotations.blockcontainers.ExternalPartReplica;
import sysmlinjava.blocks.BlockContainer;
import sysmlinjava.connectors.SysMLAssociationBlockConnector;
import sysmlinjava.connectors.SysMLAssociationBlockConnectorFunction;
import sysmlinjava.connectors.SysMLExternalAssociationBlockConnector;
import sysmlinjava.connectors.SysMLExternalAssociationBlockConnectorFunction;

/**
 * {@code DBSSensorsContainer} is the SysMLinJava model for a container of block
 * instances (parts) to execute in their own operating system process. The
 * container hosts the two patient sensors, i.e. a tremor motion sensor and a
 * blood pressure sensor. In accordance with the {@code BlockContainer} construct, it
 * also contains a "replica" of the DBS controller to which it transmits the
 * sensor data. The replica of the DBS controller is used to create the
 * connectors from the sensors to the actual DBS controller, whose instance is
 * located in another {@code BlockContainer}, i.e. in another operating system
 * process.
 * 
 * @author ModelerOne
 *
 */
public class DBSSensorsContainer extends BlockContainer
{
	/**
	 * Part for the tremor motion sensor
	 */
	@Part
	public TremorSensor tremorSensor;
	/**
	 * Part for the pulse sensor
	 */
	@Part
	public PulseSensor pulseSensor;

	/**
	 * Replica part for DBS controller
	 */
	@ExternalPartReplica
	public DBSController dbsController;

	/**
	 * Function to make the connection of the tremor sensor to the pulse sensor for
	 * tremor presence indications
	 */
	@AssociationConnectorFunction
	public SysMLAssociationBlockConnectorFunction tremorSensorToPulseSensorConnectorFunction;
	/**
	 * Connector to invoke the tremor-sensor-to-pulse-sesnor connector function
	 */
	@AssociationConnector
	public SysMLAssociationBlockConnector tremorSensorToPulseSensorConnector;

	/**
	 * Function to make the connection of the tremor sensor to the DBS controller
	 * for sensor signals
	 */
	@ExternalAssociationConnector
	public SysMLExternalAssociationBlockConnector tremorSensorToControllerConnector;
	/**
	 * Function to make the connection of the pulse sensor to the DBS controller for
	 * sensor signals
	 */
	@ExternalAssociationConnector
	public SysMLExternalAssociationBlockConnector pulseSensorToControllerConnector;

	/**
	 * Connector to invoke the pulse-sensor-to-controller connector function
	 */
	@ExternalAssociationConnectorFunction
	public SysMLExternalAssociationBlockConnectorFunction pulseSensorToControllerConnectorFunction;
	/**
	 * Connector to invoke the tremor-sensor-to-controller connector function
	 */
	@ExternalAssociationConnectorFunction
	public SysMLExternalAssociationBlockConnectorFunction tremorSensorToControllerConnectorFunction;

	public DBSSensorsContainer()
	{
		super("DBSensorsContainer", 0L);
	}

	@Override
	protected void createExternalPartReplicas()
	{
		dbsController = new DBSController();
	}

	@Override
	protected void createParts()
	{
		tremorSensor = new TremorSensor();
		pulseSensor = new PulseSensor();
	}

	@Override
	protected void createConnectorFunctions()
	{
		tremorSensorToPulseSensorConnectorFunction = () ->
		{
			tremorSensor.tremorPresencePort.addConnectedPortPeer(pulseSensor.tremorPresenceInPort);
		};
	}

	@Override
	protected void createConnectors()
	{
		tremorSensorToPulseSensorConnector = new SysMLAssociationBlockConnector(tremorSensor, pulseSensor, tremorSensorToPulseSensorConnectorFunction);
	}

	@Override
	protected void createExternalConnectorFunctions()
	{
		tremorSensorToControllerConnectorFunction = () ->
		{
			tremorSensor.tremorLevelPort.addConnectedPortPeer(dbsController.tremorInPort);
		};
		pulseSensorToControllerConnectorFunction = () ->
		{
			pulseSensor.pulseOutPort.addConnectedPortPeer(dbsController.pulseInPort);
		};
	}

	@Override
	protected void createExternalConnectors()
	{
		tremorSensorToControllerConnector = new SysMLExternalAssociationBlockConnector(tremorSensor, dbsController, tremorSensorToControllerConnectorFunction);
		pulseSensorToControllerConnector = new SysMLExternalAssociationBlockConnector(pulseSensor, dbsController, pulseSensorToControllerConnectorFunction);
	}

	@Override
	public void enableInteractionMessageTransmissions()
	{
		InteractionMessageTransmitter transmitter = new InteractionMessageTransmitter(InteractionMessageSequenceDisplay.udpPort, true);
		InteractionMessageTransmitters interactionMessageTransmitters = new InteractionMessageTransmitters(transmitter);

		tremorSensor.tremorLevelPort.messageUtility = Optional.of(interactionMessageTransmitters);
		pulseSensor.pulseOutPort.messageUtility = Optional.of(interactionMessageTransmitters);
	}

	/**
	 * Main for the execution of the container in its own process
	 * 
	 * @param args null
	 */
	public static void main(String[] args)
	{
		DBSSensorsContainer container = new DBSSensorsContainer();
		container.tremorSensor.start();
		container.pulseSensor.start();
		try
		{
			Thread.sleep(300_000);
			container.tremorSensor.concurrentExecutionThreads.awaitTermination(30L, TimeUnit.NANOSECONDS);
			container.pulseSensor.concurrentExecutionThreads.awaitTermination(30L, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		container.tremorSensor.stop();
		container.pulseSensor.stop();
		System.exit(0);
	}
}
