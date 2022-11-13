package dbssystem.sensors;

import java.util.Optional;
import dbssystem.common.PressureSignal;
import dbssystem.common.PulseValue;
import sysmlinjava.annotations.Constraint;
import sysmlinjava.annotations.Flow;
import sysmlinjava.annotations.FullPort;
import sysmlinjava.annotations.Value;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLConstraint;
import sysmlinjava.valuetypes.BBoolean;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjava.valuetypes.InternetAddress;

/**
 * PulseSensor is the SysMLinJava model of a sensor of a patient's pulse for use
 * in the control of a deep-brain stimulation system. It receives pulse signals
 * from a patient and transforms them into pulse values for transmission to the
 * DBS controller. The pulse values are only transmitted upon indication of the
 * presence of a tremor in the patient.
 * 
 * @author ModelerOne
 *
 */
public class PulseSensor extends SysMLBlock
{
	/**
	 * Port for the input of pressure signals used to calculate pulse
	 */
	@FullPort
	public PressureSignalInPort pressureInPort;
	/**
	 * Port for the output of the calculated pulse value (heart rate)
	 */
	@FullPort
	public PulseValueOutPort pulseOutPort;
	/**
	 * Port for the input of indication that tremor is present in the patient
	 */
	@FullPort
	public TremorPresenceInPort tremorPresenceInPort;

	/**
	 * Flow value for the current sensed pressure
	 */
	@Flow
	public PressureSignal pressureIn;
	/**
	 * Flow value for the current pulse output
	 */
	@Flow
	public PulseValue pulseOut;
	/**
	 * Flow value for the current tremor presence indication input
	 */
	@Flow
	public BBoolean tremorPresenceIn;

	/**
	 * Value of the IP address for the port receiving the pressure signal
	 */
	@Value
	public InternetAddress ipAddress;
	/**
	 * Value of the UDP port number for the port receiving the pressure signal
	 */
	@Value
	public IInteger pressureUDPPort;

	/**
	 * Constraint for calculating the pulse from the pressure signal
	 */
	@Constraint
	public SysMLConstraint pressureToPulseConstraint;

	public PulseSensor()
	{
		super("PulseSensor", 0L);
	}

	/**
	 * Starts the sensor by starting the state machine and the pressure input port
	 */
	@Override
	public void start()
	{
		super.start();
		pressureInPort.start();
	}

	/**
	 * Stops the sensor
	 */
	@Override
	public void stop()
	{
		pressureInPort.stop();
		super.stop();
	}

	/**
	 * Event handler for the reciept of a new pressure signal value
	 * 
	 * @param value the new pressure signal value
	 */
	public void onPressureSignal(PressureSignal value)
	{
		logger.info(value.toString());
		if (tremorPresenceIn.isTrue())
		{
			pressureToPulseConstraint.apply();
			pulseOutPort.transmit(pulseOut);
		}
	}

	/**
	 * Event handler for the reciept of a new indication of the presence of tremor
	 * 
	 * @param isPresent the new tremor presence indication
	 */
	public void onTremorPresence(BBoolean isPresent)
	{
		logger.info(isPresent.toString());
		tremorPresenceIn.setValue(isPresent);
	}

	@Override
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new PulseSensorStateMachine(this));
	}

	@Override
	protected void createValues()
	{
		ipAddress = InternetAddress.ofLocalHost();
		pressureUDPPort = new IInteger(8706);
	}

	@Override
	protected void createFlows()
	{
		pressureIn = new PressureSignal(IInteger.of(0));
		pulseOut = new PulseValue(IInteger.of(0));
		tremorPresenceIn = new BBoolean(false);
	}

	@Override
	protected void createFullPorts()
	{
		pressureInPort = new PressureSignalInPort(this, ipAddress.toInetAddress(), pressureUDPPort.toInteger());
		pulseOutPort = new PulseValueOutPort(this, 0L, "PulseValueOutPort");
		tremorPresenceInPort = new TremorPresenceInPort(this, 0L, "TremorPresenceInPort");
	}

	/**
	 * Creates the sensor's constraint to calculate the pulse output value from the
	 * pressure input value.
	 */
	@Override
	protected void createConstraints()
	{
		pressureToPulseConstraint = () ->
		{
			if (tremorPresenceIn.equalTo(BBoolean.True))
				pulseOut = pressureIn.toPulse();
		};
	}
}
