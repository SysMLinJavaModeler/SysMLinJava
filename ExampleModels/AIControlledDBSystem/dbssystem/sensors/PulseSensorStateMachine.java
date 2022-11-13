package dbssystem.sensors;

/**
 * State machine for the PulseSensor. The state machine consists of only two
 * states - one to initialize the sensor and one to repeatedly handle receipts
 * of pressure signals from the patient as well as tremor presence signals from
 * the tremor sensor. Note this state machine extends a base state machine to
 * demonstrate the SysMLinJava's support for state machine reuse by
 * extension/sepcialization.
 * 
 * @author ModelerOne
 *
 */
public class PulseSensorStateMachine extends SensorStateMachine
{
	public PulseSensorStateMachine(PulseSensor contextBlock)
	{
		super(contextBlock, "PulseSensorStateMachine");
	}

	@Override
	protected void createEffectActivities()
	{
		onSensedValueSignalEffectActivity = (event, contextBlock) ->
		{
			System.out.println(getClass().getSimpleName() + ".onSensedValueSignalEffectActivity(): event=" + event.get().toString());
			if (event.get() instanceof PressureSignalEvent signalEvent)
			{
				PulseSensor sensor = (PulseSensor)contextBlock.get();
				sensor.onPressureSignal(signalEvent.getSignal());
			}
			else if (event.get() instanceof TremorPresenceEvent signalEvent)
			{
				PulseSensor sensor = (PulseSensor)contextBlock.get();
				sensor.onTremorPresence(signalEvent.getPresence());
			}
		};
	}

}
