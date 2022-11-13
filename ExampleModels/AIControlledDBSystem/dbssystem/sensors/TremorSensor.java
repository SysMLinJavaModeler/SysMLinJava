package dbssystem.sensors;

import java.util.Optional;
import dbssystem.common.MotionSignal;
import dbssystem.common.TremorLevel;
import sysmlinjava.annotations.Constraint;
import sysmlinjava.annotations.Flow;
import sysmlinjava.annotations.FullPort;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLConstraint;
import sysmlinjava.valuetypes.BBoolean;
import sysmlinjava.valuetypes.DistanceMillimeters;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjava.valuetypes.InternetAddress;
import sysmlinjava.valuetypes.JerkMetersPerSecondCubed;

/**
 * TremorSensor is the SysMLinJava model of a sensor of a patient's tremor for
 * use in the control of a deep-brain stimulation system. It receives tremor
 * signals from a patient and transforms them into tremor values for
 * transmission to the DBS controller. It also transmits a signal to the pulse
 * sensor if/when tremor is present in the patient.
 * 
 * @author ModelerOne
 *
 */
public class TremorSensor extends SysMLBlock
{
	/**
	 * Port for the output of tremor level values
	 */
	@FullPort
	public TremorLevelOutPort tremorLevelPort;
	/**
	 * Port for the output of the presence of tremor
	 */
	@FullPort
	public TremorPresenceOutPort tremorPresencePort;
	/**
	 * Port for the input of motion signal from the patient
	 */
	@FullPort
	public MotionSignalInPort motionInPort;

	/**
	 * Value for the max amplitude of motion recognized for calculating tremor jerk
	 */
	@Value
	public JerkMetersPerSecondCubed maxAmplitudeJerk;
	/**
	 * Value for the maximum jerk recognized from the patient
	 */
	@Value
	public JerkMetersPerSecondCubed maxValidJerk;
	/**
	 * Value of the IP address used to receive the patient's motion signal
	 */
	@Value
	public InternetAddress ipAddress;
	/**
	 * Value of the UDP port number used to receive the patient's motion signal
	 */
	@Value
	public IInteger motionUDPPort;

	/**
	 * Flow value output for indication that tremor is present in the patient
	 */
	@Flow
	public BBoolean tremorPresenceOut;
	/**
	 * Flow value input for the jerk in the patient's motion
	 */
	@Flow
	public JerkMetersPerSecondCubed jerkIn;
	/**
	 * Flow value output for the tremor frequency
	 */
	@Flow
	public FrequencyHertz tremorFrequencyOut;
	/**
	 * Flow value output for the tremor amplitude
	 */
	@Flow
	public DistanceMillimeters tremorAmplitudeOut;

	/**
	 * Constraint for the calculation of and output tremor level from the input
	 * motion
	 */
	@Constraint
	public SysMLConstraint motionToTremorConstraint;

	public TremorSensor()
	{
		super("TremorSensor", 0L);
	}

	/**
	 * Event handler for the receipt of a motion signal from the patient
	 * 
	 * @param motion motion signal from patient
	 */
	public void onMotion(MotionSignal motion)
	{
		logger.info(motion.toString());
		jerkIn.setValue(motion.jerk);
		motionToTremorConstraint.apply();
		tremorLevelPort.transmit(new TremorLevel(tremorFrequencyOut, tremorAmplitudeOut));
		if (jerkIn.value >= 0 && jerkIn.lessThanOrEqualTo(maxValidJerk))
			tremorPresencePort.transmit(BBoolean.True);
		else
			tremorPresencePort.transmit(BBoolean.False);
	}

	/**
	 * Starts the sensor, i.e. starts the state machine and the port to receive
	 * motion inputs from the patient
	 */
	@Override
	public void start()
	{
		super.start();
		motionInPort.start();
	}

	/**
	 * Stops the sensor
	 */
	@Override
	public void stop()
	{
		motionInPort.stop();
		super.stop();
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new TremorSensorStateMachine(this));
	}

	@Override
	protected void createValues()
	{
		maxValidJerk = new JerkMetersPerSecondCubed(0.7);
		maxAmplitudeJerk = new JerkMetersPerSecondCubed(0.3);
		ipAddress = InternetAddress.ofLocalHost();
		motionUDPPort = new IInteger(8705);
	}

	@Override
	protected void createFlows()
	{
		jerkIn = new JerkMetersPerSecondCubed(0);
		tremorPresenceOut = new BBoolean(false);
		tremorFrequencyOut = new FrequencyHertz(0);
		tremorAmplitudeOut = new DistanceMillimeters(0);
	}

	@Override
	protected void createFullPorts()
	{
		tremorLevelPort = new TremorLevelOutPort(this, 0L, "TremorLevelOutPort");
		motionInPort = new MotionSignalInPort(this, ipAddress.toInetAddress(), motionUDPPort.toInteger());
		tremorPresencePort = new TremorPresenceOutPort(this, 0L, "TremorPresencePort");
	}

	/**
	 * Creates the constraint to calculate tremor level from patient motion. This
	 * calculation is an artificially simple algorithm used to enable demonstration
	 * of this SysMLinJava model.
	 */
	@Override
	protected void createConstraints()
	{
		motionToTremorConstraint = () ->
		{
			if (jerkIn.value >= 0 && jerkIn.lessThanOrEqualTo(maxValidJerk))
			{
				tremorFrequencyOut.setValue(jerkIn.value * 10 * 2);
				if (jerkIn.lessThanOrEqualTo(maxAmplitudeJerk))
					tremorAmplitudeOut.setValue(jerkIn.value * 10 * 5);
				else
					tremorAmplitudeOut.setValue(15 - (jerkIn.value - maxAmplitudeJerk.value) * 10 * 5);
			}
			else
				logger.warning("invalid jerk value received: " + jerkIn.toString() + "; ignored");
		};
	}
}
