package dbssystem.actuators;

import java.util.Optional;
import dbssystem.common.DBSControl;
import dbssystem.common.DBSSignal;
import sysmlinjava.annotations.Constraint;
import sysmlinjava.annotations.Flow;
import sysmlinjava.annotations.FullPort;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLConstraint;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjava.valuetypes.InternetAddress;
import sysmlinjava.valuetypes.PhaseShiftRadians;
import sysmlinjava.valuetypes.PotentialElectricalVolts;

/**
 * DBSActuator is the SysMLinJava model of an actuator for a deep-brain
 * stimulation system. It receives control signals and transforms them into low
 * power electrical signals to be injected into the patient's brain to
 * reduce/control the patient's tremor.
 * 
 * @author ModelerOne
 *
 */
public class DBSActuator extends SysMLBlock
{
	/**
	 * Port for ouput of the DBS signal to the patient
	 */
	@FullPort
	public DBSSignalOutPort dbsSignalPort;
	/**
	 * Port for input of the control signals to the acuator
	 */
	@FullPort
	public DBSControlInPort controlPort;

	/**
	 * Value of the control that was last input
	 */
	@Value
	public DBSControl controlIn;
	/**
	 * Value of the standard amplitude to be used for the DBS signal to the patient
	 */
	@Value
	private PotentialElectricalVolts standardAmplitude;
	/**
	 * Value of the UDP port used to receive the controls
	 */
	@Value
	private IInteger controlUDPPort;
	/**
	 * Value of the IP address used to receive the controls
	 */
	@Value
	private InternetAddress controlIPAddress;

	/**
	 * Flow value of the DBS signal currently being injected out into the patient's
	 * brain.
	 */
	@Flow
	public DBSSignal signalOut;

	/**
	 * Constraint that calculates the DBS signal output from the control input
	 */
	@Constraint
	public SysMLConstraint controlToSignal;

	/**
	 * Constructor
	 */
	public DBSActuator()
	{
		super("DBSActuator", 0L);
	}

	/**
	 * Event handler for receipt of new DBS control object. The operation stores the
	 * control information, invokes the constraint to calculate the new DBS signal
	 * value, and transmits the new signal to the patient's brain.
	 * 
	 * @param control control data received
	 */
	public void onDBSControl(DBSControl control)
	{
		logger.info(control.toString());
		controlIn.frequency.value = control.frequency.value;
		controlIn.phaseShift.value = control.phaseShift.value;
		controlToSignal.apply();
		dbsSignalPort.transmit(signalOut);
	}

	/**
	 * Starts the actuator, ie. starts the state machine and the control port
	 */
	@Override
	public void start()
	{
		super.start();
		controlPort.start();
	}

	/**
	 * Stops the actuator
	 */
	@Override
	public void stop()
	{
		controlPort.stop();
		super.stop();
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new DBSActuatorStateMachine(this));
	}

	@Override
	protected void createValues()
	{
		controlIn = new DBSControl(new FrequencyHertz(0), new PhaseShiftRadians(0));
		standardAmplitude = new PotentialElectricalVolts(20.0e-6);
		controlIPAddress = InternetAddress.ofLocalHost();
		controlUDPPort = new IInteger(8703);
	}

	@Override
	protected void createFlows()
	{
		signalOut = new DBSSignal(new FrequencyHertz(0), new PotentialElectricalVolts(0), new PhaseShiftRadians(0));
	}

	@Override
	protected void createFullPorts()
	{
		dbsSignalPort = new DBSSignalOutPort(this);
		controlPort = new DBSControlInPort(this, controlIPAddress.toInetAddress(), controlUDPPort.toInteger());
	}

	/**
	 * Creates the actuator's constraint that simply sets the actuator outputs to
	 * the values provided in the actuator's control.
	 */
	@Override
	protected void createConstraints()
	{
		controlToSignal = () ->
		{
			signalOut.frequency.value = controlIn.frequency.value;
			signalOut.phaseShift.value = controlIn.phaseShift.value;
			signalOut.amplitude.value = standardAmplitude.value;
		};
	}
}
