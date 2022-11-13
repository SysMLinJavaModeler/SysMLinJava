package dbssystem.patient;

import java.util.Optional;
import sysmlinjava.annotations.statemachines.Effect;
import sysmlinjava.annotations.statemachines.EffectActivity;
import sysmlinjava.annotations.statemachines.OnEnterActivity;
import sysmlinjava.annotations.statemachines.State;
import sysmlinjava.annotations.statemachines.Transition;
import sysmlinjava.events.SysMLTimeEvent;
import sysmlinjava.statemachine.FinalTransition;
import sysmlinjava.statemachine.InitialTransition;
import sysmlinjava.statemachine.SysMLEffect;
import sysmlinjava.statemachine.SysMLEffectActivity;
import sysmlinjava.statemachine.SysMLOnEnterActivity;
import sysmlinjava.statemachine.SysMLState;
import sysmlinjava.statemachine.SysMLStateMachine;
import sysmlinjava.statemachine.SysMLTransition;
import sysmlinjava.statemachine.SysMLTransitionKind;
import sysmlinjava.valuetypes.DurationMilliseconds;

/**
 * State machine for the Patient. The state machine consists of only two states
 * - one to initialize the patient and one to repeatedly handle receipts of
 * {@code DBSSignal}s from the {@code DBSActuator} and occurance of motion
 * transmission times (simulating repeated tremor motion).
 * 
 * @author ModelerOne
 *
 */
public class PatientStateMachine extends SysMLStateMachine
{
	@OnEnterActivity
	protected SysMLOnEnterActivity initializeActivity;

	@State
	protected SysMLState initializingState;
	@State
	protected SysMLState operationalState;

	@Transition
	protected InitialTransition initialToInitializingTransition;
	@Transition
	protected SysMLTransition initializingToOperationalTransition;
	@Transition
	protected SysMLTransition operationalOnDBSSignalTransition;
	@Transition
	protected SysMLTransition operationalOnMotionTimeTransition;
	@Transition
	protected SysMLTransition operationalToFinalTransition;

	@EffectActivity
	protected SysMLEffectActivity onDBSSignalEffectActivity;
	@EffectActivity
	protected SysMLEffectActivity onMotionTimeEffectActivity;

	@Effect
	protected SysMLEffect onDBSSignalEffect;
	@Effect
	protected SysMLEffect onMotionTimeEffect;

	static final String timerID = "motionTimer";

	public PatientStateMachine(Patient contextBlock)
	{
		super(Optional.of(contextBlock), true, "PatientStateMachine");
	}

	@Override
	protected void createStateOnEnterActivities()
	{
		initializeActivity = (contextBlock) ->
		{
			contextBlock.get().stateMachine.get().startTimer(new SysMLTimeEvent(timerID, DurationMilliseconds.ofSeconds(5), Optional.of(DurationMilliseconds.ofSeconds(5))));
		};
	}

	@Override
	protected void createStates()
	{
		super.createStates();
		initializingState = new SysMLState(contextBlock, Optional.of(initializeActivity), Optional.empty(), Optional.empty(), "Initializing");
		operationalState = new SysMLState(contextBlock, "Operational");
	}

	@Override
	protected void createEffectActivities()
	{
		onDBSSignalEffectActivity = (event, contextBlock) ->
		{
			DBSSignalEvent signalEvent = (DBSSignalEvent)event.get();
			Patient patient = (Patient)contextBlock.get();
			patient.onDBSSignal(signalEvent.getSignal());
		};
		onMotionTimeEffectActivity = (event, contextBlock) ->
		{
			System.out.println(getClass().getSimpleName() + ".onMotionTimeEffectActivity");			
			Patient patient = (Patient)contextBlock.get();
			patient.onMotionTime();
		};
	}

	@Override
	protected void createEffects()
	{
		super.createEffects();
		onDBSSignalEffect = new SysMLEffect(contextBlock, onDBSSignalEffectActivity, "onDBSSignal");
		onMotionTimeEffect = new SysMLEffect(contextBlock, onMotionTimeEffectActivity, "onMotionTime");
	}

	@Override
	protected void createTransitions()
	{
		initialToInitializingTransition = new InitialTransition(contextBlock, initialState, initializingState, "InitialToInitializing");
		initializingToOperationalTransition = new SysMLTransition(contextBlock, initializingState, operationalState, "InitializingToOperational");
		operationalOnDBSSignalTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(DBSSignalEvent.class), Optional.empty(), Optional.of(onDBSSignalEffect), "OperationalOnDBSSignal",
			SysMLTransitionKind.internal);
		operationalOnMotionTimeTransition = new SysMLTransition(contextBlock, operationalState, operationalState, Optional.of(SysMLTimeEvent.class), Optional.empty(), Optional.of(onMotionTimeEffect), "OperationalOnMotionTime",
			SysMLTransitionKind.internal);
		operationalToFinalTransition = new FinalTransition(contextBlock, operationalState, finalState, "OperationalToFinal");
	}
}
