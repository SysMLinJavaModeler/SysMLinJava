package dbssystem.controller;

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
 * State machine for the DBSController. The state machine consists of only two
 * states - one to initialize the controller and one to repeatedly handle receipts
 * of tremor and pulse values from the patient.
 * 
 * @author ModelerOne
 *
 */
public class DBSControllerStateMachine extends SysMLStateMachine
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
	protected SysMLTransition operationalOnTremorLevelTransition;
	@Transition
	protected SysMLTransition operationalOnPulseValueTransition;
	@Transition
	protected SysMLTransition operationalToFinalTransition;

	@EffectActivity
	protected SysMLEffectActivity onPulseValueEffectActivity;
	@EffectActivity
	protected SysMLEffectActivity onTremorLevelEffectActivity;

	@Effect
	protected SysMLEffect onPulseValueEffect;
	@Effect
	protected SysMLEffect onTremorLevelEffect;

	public DBSControllerStateMachine(DBSController contextBlock)
	{
		super(Optional.of(contextBlock), true, "DBSControllerStateMachine");
	}

	@Override
	protected void createStates()
	{
		super.createStates();
		initializingState = new SysMLState(contextBlock, "Initializing");
		operationalState = new SysMLState(contextBlock, "Operational");
	}

	protected void createEffectActivities()
	{
		onPulseValueEffectActivity = (event, contextBlock) ->
		{
			PulseValueSignalEvent pulseEvent = (PulseValueSignalEvent)event.get();
			DBSController controller = (DBSController)contextBlock.get();
			controller.onPulseValue(pulseEvent.getValue());
		};
		onTremorLevelEffectActivity = (event, contextBlock) ->
		{
			TremorLevelEvent tremorEvent = (TremorLevelEvent)event.get();
			DBSController controller = (DBSController)contextBlock.get();
			controller.onTremorLevel(tremorEvent.getLevel());
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		onPulseValueEffect = new SysMLEffect(contextBlock, onPulseValueEffectActivity, "onPulseValue");
		onTremorLevelEffect = new SysMLEffect(contextBlock, onTremorLevelEffectActivity, "onTremorLevel");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		operationalOnPulseValueTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(PulseValueSignalEvent.class), Optional.empty(), Optional.of(onPulseValueEffect), "OperationalOnPulseValue",
			SysMLTransitionKind.internal);
		operationalOnTremorLevelTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(TremorLevelEvent.class), Optional.empty(), Optional.of(onTremorLevelEffect), "OperationalOnTremorLevel",
			SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "OperationalToFinal");
	}
}
