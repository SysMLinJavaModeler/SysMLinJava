package dbssystem.patient;

import java.util.Optional;
import dbssystem.actuators.DBSActuator;
import dbssystem.sensors.PulseSensor;
import dbssystem.sensors.TremorSensor;
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
 * {@code PatientContainer} is the SysMLinJava model for a container of block
 * instances (parts) to execute in their own operating system process. The
 * container hosts the patient and his DBS actuator. In accordance with the
 * {@code BlockContainer} construct, it also contains a "replica" of the two
 * sensors that are attached to the patient, i.e. the tremor motion sensor and
 * the blood pressure sensor. The replicas of the two sensors are used to
 * create the connectors from the patient to the actual sensors, whose instances
 * are located in another {@code BlockContainer}, i.e. in another operating
 * system process (JVM).
 * 
 * @author ModelerOne
 *
 */
public class PatientContainer extends BlockContainer
{
	/**
	 * Part for the patient whose tremor is to be reduced/controlled by the DBS
	 * system
	 */
	@Part
	public Patient patient;
	/**
	 * Part for the DBS actuator embedded in the patient's brain.
	 */
	@Part
	public DBSActuator dbsActuator;

	/**
	 * Replica part for the pulse sensor whose actual instance is part of another
	 * container, i.e. in another process.
	 */
	@ExternalPartReplica
	public PulseSensor pulseSensor;
	/**
	 * Replica part for the tremor sensor whose actual instance is part of another
	 * container, i.e. in another process.
	 */
	@ExternalPartReplica
	public TremorSensor tremorSensor;

	/**
	 * Function that makes the connection between the DBS actuator and the patient
	 */
	@AssociationConnectorFunction
	public SysMLAssociationBlockConnectorFunction dbsToPatientConnectorFunction;

	/**
	 * Connector that invokes the function that makes the connection between the DBS
	 * actuator and the patient
	 */
	@AssociationConnector
	public SysMLAssociationBlockConnector dbsToPatientConnector;

	/**
	 * Function that makes the connection between the patient and the tremor sensor
	 * that is part of another container, i.e. external to this container.
	 */
	@ExternalAssociationConnectorFunction
	public SysMLExternalAssociationBlockConnectorFunction patientToTremorSensorConnectorFunction;
	/**
	 * Function that makes the connection between the patient and the pulse sensor
	 * that is part of another container, i.e. external to this container.
	 */
	@ExternalAssociationConnectorFunction
	public SysMLExternalAssociationBlockConnectorFunction patientToPulseSensorConnectorFunction;

	/**
	 * Connector that invokes the function that makes the connection between the
	 * patient and the tremor sensor that is part of another container, i.e.
	 * external to this container.
	 */
	@ExternalAssociationConnector
	public SysMLExternalAssociationBlockConnector patientToTremorSensorConnector;
	/**
	 * Connector that invokes the function that makes the connection between the
	 * patient and the pulse sensor that is part of another container, i.e. external
	 * to this container.
	 */
	@ExternalAssociationConnector
	public SysMLExternalAssociationBlockConnector patientToPulseSensorConnector;

	/**
	 * Constructor
	 */
	public PatientContainer()
	{
		super("PatientContainer", 0L);
	}

	@Override
	protected void createParts()
	{
		patient = new Patient();
		dbsActuator = new DBSActuator();
	}

	@Override
	protected void createExternalPartReplicas()
	{
		pulseSensor = new PulseSensor();
		tremorSensor = new TremorSensor();
	}

	@Override
	protected void createConnectorFunctions()
	{
		dbsToPatientConnectorFunction = () ->
		{
			dbsActuator.dbsSignalPort.addConnectedPortPeer(patient.dbsSignalPort);
		};
	}

	@Override
	protected void createConnectors()
	{
		dbsToPatientConnector = new SysMLAssociationBlockConnector(dbsActuator, patient, dbsToPatientConnectorFunction);
	}

	@Override
	protected void createExternalConnectorFunctions()
	{
		patientToTremorSensorConnectorFunction = () ->
		{
			patient.motionOutPort.addConnectedPortPeer(tremorSensor.motionInPort);
		};
		patientToPulseSensorConnectorFunction = () ->
		{
			patient.pressureOutPort.addConnectedPortPeer(pulseSensor.pressureInPort);
		};
	}

	@Override
	protected void createExternalConnectors()
	{
		patientToTremorSensorConnector = new SysMLExternalAssociationBlockConnector(patient, tremorSensor, patientToTremorSensorConnectorFunction);
		patientToPulseSensorConnector = new SysMLExternalAssociationBlockConnector(patient, pulseSensor, patientToPulseSensorConnectorFunction);
	}

	@Override
	public void enableInteractionMessageTransmissions()
	{
		InteractionMessageTransmitter transmitter = new InteractionMessageTransmitter(InteractionMessageSequenceDisplay.udpPort, true);
		InteractionMessageTransmitters interactionMessageTransmitters = new InteractionMessageTransmitters(transmitter);

		patient.motionOutPort.messageUtility = Optional.of(interactionMessageTransmitters);
		patient.pressureOutPort.messageUtility = Optional.of(interactionMessageTransmitters);
		dbsActuator.dbsSignalPort.messageUtility = Optional.of(interactionMessageTransmitters);
	}
	
	/**
	 * Main for the execution of the container in its own process
	 * 
	 * @param args null
	 */
	public static void main(String[] args)
	{
		PatientContainer container = new PatientContainer();
		container.patient.start();
		container.dbsActuator.start();
		try
		{
			Thread.sleep(300_000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		container.patient.stop();
		container.dbsActuator.stop();
		System.exit(0);
	}
}
