package dbssystem.actuators;

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
 * State machine for the DBSActuator. The state machine consists of only two
 * states - one to initialize the actuator and one to repeatedly handle receipts of control
 * values.
 * 
 * @author ModelerOne
 *
 */
public class DBSActuatorStateMachine extends SysMLStateMachine
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
	protected SysMLTransition operationalOnDBSControlTransition;
	@Transition
	protected SysMLTransition operationalToFinalTransition;

	@EffectActivity
	protected SysMLEffectActivity onDBSControlEffectActivity;

	@Effect
	protected SysMLEffect onDBSControlEffect;

	public DBSActuatorStateMachine(DBSActuator contextBlock)
	{
		super(Optional.of(contextBlock), true, "DBSActuatorStateMachine");
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
		onDBSControlEffectActivity = (event, contextBlock) ->
		{
			DBSControlSignalEvent controlEvent = (DBSControlSignalEvent)event.get();
			DBSActuator actuator = (DBSActuator)contextBlock.get();
			actuator.onDBSControl(controlEvent.getControl());
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		onDBSControlEffect = new SysMLEffect(contextBlock, onDBSControlEffectActivity, "onDBSControl");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		operationalOnDBSControlTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(DBSControlSignalEvent.class), Optional.empty(), Optional.of(onDBSControlEffect), "OperationalOnDBSControl",
			SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "OperationalToFinal");
	}
}
