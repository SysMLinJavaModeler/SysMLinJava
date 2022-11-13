package dbssystem.patient;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import dbssystem.common.DBSSignal;
import dbssystem.common.MotionSignal;
import dbssystem.common.PressureSignal;
import sysmlinjava.annotations.Constraint;
import sysmlinjava.annotations.Flow;
import sysmlinjava.annotations.FullPort;
import sysmlinjava.annotations.Reception;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLConstraint;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjava.valuetypes.JerkMetersPerSecondCubed;
import sysmlinjava.valuetypes.PhaseShiftRadians;
import sysmlinjava.valuetypes.PotentialElectricalVolts;

/**
 * Patient is the SysMLinJava model of a patient using a deep-brain stimulation
 * system. It receives DBS signals from a DBS actuator emplanted in his brain to
 * reduce his tremor motion. The Patient also transmits tremor motion signals as
 * well as blood pressure signals to sensors that send information to a DBS
 * controller.
 * <p>
 * Note that this patient simulation model presents an "open loop" simulation,
 * i.e. the patient's response to the DBS is based solely on a time-based
 * calculation and is not a function of the received DBS signal. This simple
 * simulation is used to focus the simulation on demonstrating the concepts and
 * principles of modeling and simulation with SysMLinJava.
 * 
 * @author ModelerOne
 *
 */
public class Patient extends SysMLBlock
{
	/**
	 * Port to receive the DBS signal from the DBS actuator
	 */
	@FullPort
	public DBSSignalInPort dbsSignalPort;
	/**
	 * Port to transmit a tremor motion signal to a tremor sensorr
	 */
	@FullPort
	public MotionSignalOutPort motionOutPort;
	/**
	 * Port to transmit a blood pressure signal to a pulse sensor
	 */
	@FullPort
	public PressureSignalOutPort pressureOutPort;

	/**
	 * Flow value of the current DBS signal input
	 */
	@Flow
	public DBSSignal dbsSignalIn;
	/**
	 * Flow value of the current tremor motion signal output
	 */
	@Flow
	public MotionSignal motionOut;
	/**
	 * Flow value of the current blood pressure signal output
	 */
	@Flow
	public PressureSignal pressureOut;

	/**
	 * Value of time at which the patient simulation began
	 */
	@Value
	public Instant startInstant;

	/**
	 * Constraint the calculates the simulated patient's new tremor motion and blood
	 * pressure values
	 */
	@Constraint
	public SysMLConstraint dbsToMotionPressure;

	/**
	 * Constructor
	 */
	public Patient()
	{
		super("Patient", 0L);
	}

	/**
	 * Event handler (reception) to receive the next value of the DBS signal
	 * 
	 * @param signal the DBS signal value
	 */
	@Reception
	public void onDBSSignal(DBSSignal signal)
	{
		logger.info(signal.toString());
		dbsSignalIn.setValue(signal);
	}

	/**
	 * Event handler for the next time at which the simulation is to transmit
	 * patient tremor motion and blood pressure values to the sensors.
	 */
	public void onMotionTime()
	{
		logger.info("motion time");
		dbsToMotionPressure.apply();
		motionOutPort.transmit(motionOut);
		pressureOutPort.transmit(pressureOut);
	}

	@Override
	public void start()
	{
		super.start();
		dbsSignalPort.start();
	}

	@Override
	public void stop()
	{
		dbsSignalPort.stop();
		super.stop();
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new PatientStateMachine(this));
	}

	@Override
	protected void createValues()
	{
		startInstant = Instant.now();
	}

	@Override
	protected void createFlows()
	{
		dbsSignalIn = new DBSSignal(new FrequencyHertz(0), new PotentialElectricalVolts(0), new PhaseShiftRadians(0));
		motionOut = new MotionSignal(new JerkMetersPerSecondCubed(0));
		pressureOut = new PressureSignal(IInteger.of(60));
	}

	@Override
	protected void createFullPorts()
	{
		dbsSignalPort = new DBSSignalInPort(this);
		pressureOutPort = new PressureSignalOutPort(this);
		motionOutPort = new MotionSignalOutPort(this);
	}

	/**
	 * Creates the constraint to calculate the patient's new tremor motion and blood
	 * pressure values
	 */
	@Override
	protected void createConstraints()
	{
		dbsToMotionPressure = () ->
		{
			Duration timeSinceStart = Duration.between(startInstant, Instant.now());
			motionOut.jerk.value = 0.6 - (0.6 / 300.0) * timeSinceStart.toSeconds();
			pressureOut.rate.value = 40;
		};
	}
}
