package dbssystem.sensors;

import java.util.Optional;
import sysmlinjava.annotations.statemachines.Effect;
import sysmlinjava.annotations.statemachines.EffectActivity;
import sysmlinjava.annotations.statemachines.State;
import sysmlinjava.annotations.statemachines.Transition;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.statemachine.FinalTransition;
import sysmlinjava.statemachine.InitialTransition;
import sysmlinjava.statemachine.SysMLEffect;
import sysmlinjava.statemachine.SysMLEffectActivity;
import sysmlinjava.statemachine.SysMLState;
import sysmlinjava.statemachine.SysMLStateMachine;
import sysmlinjava.statemachine.SysMLTransition;
import sysmlinjava.statemachine.SysMLTransitionKind;

/**
 * Abstract state machine for a common sensor. The state machine consists of
 * only two states - one to initialize the sensor and one to repeatedly handle
 * input/receipt of sensed values.
 * 
 * @author ModelerOne
 *
 */
public abstract class SensorStateMachine extends SysMLStateMachine
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
	protected SysMLTransition operationalOnSensedValueSignalTransition;
	@Transition
	protected SysMLTransition operationalToFinalTransition;

	@EffectActivity
	protected SysMLEffectActivity onSensedValueSignalEffectActivity;

	@Effect
	protected SysMLEffect onSensedValueSignalEffect;

	public SensorStateMachine(SysMLBlock sensorContextBlock, String name)
	{
		super(Optional.of(sensorContextBlock), true, name);
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
		//Effect activities to be created in method override, e.g.
		//onSensedValueSignalEffectActivity = () -> processNewSensedValue();
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		onSensedValueSignalEffect = new SysMLEffect(contextBlock, onSensedValueSignalEffectActivity, "onSensedValueChange");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		operationalOnSensedValueSignalTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(SensorSignalEvent.class), Optional.empty(), Optional.of(onSensedValueSignalEffect),
			"OperationalOnSensedValueSignal", SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "OperationalToFinal");
	}
}
