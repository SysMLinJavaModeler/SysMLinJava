
package dbssystem.sensors;

import java.util.Optional;
import sysmlinjava.annotations.statemachines.Effect;
import sysmlinjava.annotations.statemachines.EffectActivity;
import sysmlinjava.annotations.statemachines.State;
import sysmlinjava.annotations.statemachines.Transition;
import sysmlinjava.statemachine.FinalTransition;
import sysmlinjava.statemachine.InitialTransition;
import sysmlinjava.statemachine.SysMLEffect;
import sysmlinjava.statemachine.SysMLEffectActivity;
import sysmlinjava.statemachine.SysMLState;
import sysmlinjava.statemachine.SysMLStateMachine;
import sysmlinjava.statemachine.SysMLTransition;
import sysmlinjava.statemachine.SysMLTransitionKind;

/**
 * State machine for the TremorSensor. The state machine consists of only two states
 * - one to initialize the sensor and one to repeatedly handle receipts of
 * motion signals from the patient.
 * 
 * @author ModelerOne
 *
 */
public class TremorSensorStateMachine extends SysMLStateMachine
{
	@State
	protected SysMLState initializingState;
	@State
	protected SysMLState operationalState;

	@Transition
	protected InitialTransition initialToInitializingTransition;
	@Transition
	protected SysMLTransition initializingToOperationalTransition;
	@Transition
	protected SysMLTransition operationalOnMotionSignalSignalTransition;
	@Transition
	protected SysMLTransition operationalToFinalTransition;

	@EffectActivity
	protected SysMLEffectActivity onMotionSignalSignalEffectActivity;

	@Effect
	protected SysMLEffect onMotionSignalSignalEffect;

	public TremorSensorStateMachine(TremorSensor contextBlock)
	{
		super(Optional.of(contextBlock), true, "TremorSensorStateMachine");
	}

	@Override
	protected void createStates()
	{
		super.createStates();
		initializingState = new SysMLState(contextBlock, "Initializing");
		operationalState = new SysMLState(contextBlock, "Operational");
	}
	
	@Override
	protected void createEffectActivities()
	{
		onMotionSignalSignalEffectActivity = (event, contextBlock) ->
		{
			if(event.get() instanceof MotionSignalEvent signalEvent)
			{
				TremorSensor sensor = (TremorSensor)contextBlock.get();
				sensor.onMotion(signalEvent.getSignal());
			}
		};
	}

	@Override
	protected void createEffects()
	{
		onMotionSignalSignalEffect = new SysMLEffect(contextBlock, onMotionSignalSignalEffectActivity, "onMotionSignal");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		operationalOnMotionSignalSignalTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(MotionSignalEvent.class), Optional.empty(), Optional.of(onMotionSignalSignalEffect), "OperationalOnMotionSignalSignal", SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "OperationalToFinal");
	}

}
