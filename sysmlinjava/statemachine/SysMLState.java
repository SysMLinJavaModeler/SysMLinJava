package sysmlinjava.statemachine;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import sysmlinjava.blocks.SysMLBlock;

/**
 * The SysMLinJava representation of the state in SysML's state machine.
 * {@code SysMLState} should be used as the type of all states in the state
 * machine and should not be extended for further specialization.
 * {@code SysMLState} contains the enter, do, and exit activities. In lieu of
 * the do activity, the state can contain sub-state machines. As a vertex, the
 * {@code SysMLState} includes state transitions which are added by
 * {@code SysMLTransition} initializers. An example follows.
 * 
 * <pre>
		:
	&#64;State
	public SysMLState ice;
	&#64;State
	public SysMLState liquid;
	&#64;State
	public SysMLState gas;
	&#64;State
	public SysMLState decomposed;
		:
	&#64;Override
	protected void createStates()
	{
		super.createStates();
		ice = new SysMLState(contextBlock, "Ice");
		liquid = new SysMLState(contextBlock, "Liquid");
		gas = new SysMLState(contextBlock, "Gas");
		decomposed = new SysMLState(contextBlock, Optional.of(decomposedOnEnterActivity), Optional.empty(), Optional.empty(), "Decomposed");
	}
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 */
public class SysMLState extends SysMLVertex
{
	/**
	 * Optional activity to be performed upon entry into the state.
	 */
	public Optional<SysMLOnEnterActivity> onEnterActivity;
	/**
	 * Optional activity to be performed while in the state. If set, the
	 * {@code doActivity} is executed in lieu of the {@code subStateMachines}.
	 */
	public Optional<SysMLDoActivity> doActivity;
	/**
	 * Optional activity to be performed upon exiting from the state.
	 */
	public Optional<SysMLOnExitActivity> onExitActivity;
	/**
	 * List collection of state machines to be executed while in the (composite)
	 * state. Multiple sub-state machines are executed in parallel, i.e. as
	 * concurrent state machines within the context of the current state. The
	 * {@code subStateMachines} execute in lieu of the {@code doActivity}, if set.
	 */
	public List<? extends SysMLStateMachine> subStateMachines;
	/**
	 * List collection of IDs of sub-state machines that have submitted
	 * {@code StateMachineCompletionEvent}s to be executed while in the (composite)
	 * state. Used to determine when all sub-state machines have completed, i.e.
	 * have transitioned to their final states.
	 */
	public List<Long> completedSubStateMachineIDs;
	/**
	 * Optional state machine that contains this state. The
	 * {@code containingStateMachine} is required if there are any
	 * {@code subStateMachines} and the {@code stateMachine} of {@code contextBlock}
	 * will be used by default, but this value can be set to another stateMachine as
	 * needed.
	 */
	public Optional<SysMLStateMachine> containingStateMachine;
	/**
	 * Optional Future for the {@code doActivity} which runs in a separate thread
	 * while in the current state. This future is used to stop the
	 * {@code doActivity} if/when exit out of the current state is triggered.
	 */
	public Optional<Future<?>> doActivityFuture;
	/**
	 * Optional {@code ScheduledThreadPoolExecutor} for the {@code doActivity} which
	 * runs the separate thread while in the current state.
	 */
	public Optional<ScheduledThreadPoolExecutor> doActivityExecutor;

	/**
	 * Constructor for specifying enter, do, exit activities of the state.
	 * 
	 * @param contextBlock    Optional SysMLBlock based context in which the state
	 *                        must operate.
	 * @param onEnterFunction Optional activity to be performed upon entry into the
	 *                        state.
	 * @param doActivity      Optional activity to be performed while in the state.
	 * @param onExitFunction  Optional activity to be performed upon exit from the
	 *                        state.
	 * @param name            Optional name of the state.
	 */
	public SysMLState(Optional<? extends SysMLBlock> contextBlock, Optional<SysMLOnEnterActivity> onEnterFunction, Optional<SysMLDoActivity> doActivity, Optional<SysMLOnExitActivity> onExitFunction, String name)
	{
		super(contextBlock, name);
		this.transitions = new ArrayList<>();
		this.onEnterActivity = onEnterFunction;
		this.doActivity = doActivity;
		this.onExitActivity = onExitFunction;
		if (doActivity.isPresent())
		{
			if (!contextBlock.isPresent())
				doActivityExecutor = Optional.of(new ScheduledThreadPoolExecutor(1));
		}
		this.doActivityFuture = Optional.empty();
		this.subStateMachines = new ArrayList<>();
		this.completedSubStateMachineIDs = new ArrayList<>();
		this.containingStateMachine = Optional.empty();
	}

	/**
	 * Constructor for specifying enter and exit activities of the state as well as
	 * a list of sub-state machines that are to execute while in the state.
	 * 
	 * @param contextBlock     Optional SysMLBlock based context in which the state
	 *                         must operate.
	 * @param onEnterFunction  Optional activity to be performed upon entry into the
	 *                         state.
	 * @param subStateMachines List of behaviors (state machines) to be executed
	 *                         while in the state.
	 * @param onExitFunction   Optional activity to be performed upon exit from the
	 *                         state.
	 * @param name             Optional name of the state.
	 */
	public SysMLState(SysMLBlock contextBlock, Optional<SysMLOnEnterActivity> onEnterFunction, List<? extends SysMLStateMachine> subStateMachines, Optional<SysMLOnExitActivity> onExitFunction, String name)
	{
		super(Optional.of(contextBlock), name);
		this.transitions = new ArrayList<>();
		this.onEnterActivity = onEnterFunction;
		this.doActivity = Optional.empty();
		this.onExitActivity = onExitFunction;
		this.doActivityFuture = Optional.empty();
		this.subStateMachines = subStateMachines;
		this.completedSubStateMachineIDs = new ArrayList<>();
		this.containingStateMachine = Optional.empty();
	}

	/**
	 * Constructor for specifying state with no enter, do, exit activities and no
	 * sub-state machines.
	 * 
	 * @param contextBlock Optional {@code SysMLBlock}-based context in which the
	 *                     state must operate.
	 * @param name         Name of the state.
	 */
	public SysMLState(Optional<? extends SysMLBlock> contextBlock, String name)
	{
		this(contextBlock, Optional.empty(), Optional.empty(), Optional.empty(), name);
	}

	/**
	 * Returns whether or not receipt of the specified completion event means that
	 * all sub-state machines have now completed.
	 * 
	 * @param completionEvent completion event presumably just received from one of
	 *                        the sub-state machines
	 * @return true if all sub-state machines are now completed, false otherwise
	 */
	public boolean subStateMachinesCompleted(StateMachineCompletionEvent completionEvent)
	{
		completedSubStateMachineIDs.add(Long.valueOf(completionEvent.id));
		boolean allCompleted = true;
		if (!subStateMachines.isEmpty())
		{
			ListIterator<? extends SysMLStateMachine> iterator = subStateMachines.listIterator();
			while (iterator.hasNext() && allCompleted)
			{
				SysMLStateMachine nextSM = iterator.next();
				boolean smCompleted = completedSubStateMachineIDs.contains(nextSM.id);
				boolean smFinal = nextSM.currentState.isPresent() && nextSM.currentState.get() == nextSM.finalState;
				if (!smCompleted || !smFinal)
					allCompleted = false;
			}
			if (allCompleted)
				completedSubStateMachineIDs.clear();
		}
		else
			allCompleted = false;
		return allCompleted;
	}

	/**
	 * Operation called upon entry into the state. {@code onEnter()} invokes the
	 * specified ({@code onEnterActivity}, if provided, and, if present, all
	 * sub-state machines are executed (started)
	 */
	public void onEnter()
	{
		if (onEnterActivity.isPresent())
			onEnterActivity.get().perform(contextBlock);
		if (!subStateMachines.isEmpty())
			executeSubStateMachines();
	}

	/**
	 * Activity to be performed while in the state. {@code doWhileInState()} invokes
	 * the specified {@code doActivity}, if provided, performing the activity in a
	 * separate thread of execution.
	 */
	public void doWhileInState()
	{
		if (doActivity.isPresent())
		{
			Runnable runner = new Runnable()
			{
				public void run()
				{
					doActivity.get().perform(contextBlock);
				}
			};
			if (contextBlock.isPresent())
				doActivityFuture = Optional.of(contextBlock.get().concurrentExecutionThreads.submit(runner));
			else
				doActivityFuture = Optional.of(doActivityExecutor.get().submit(runner));
		}
	}

	/**
	 * Operation called upon exit from the state. {@code onExit()} invokes the
	 * specified {@code onExitActivity}, if provided, but only after it terminates
	 * the {@code doActivity}, if present and started, or terminates the sub-state
	 * machines, if present.
	 */
	public void onExit()
	{
		if (!subStateMachines.isEmpty())
			terminateSubStateMachines();
		else if (doActivityFuture.isPresent() && !doActivityFuture.get().isDone())
			doActivityFuture.get().cancel(true);
		if (onExitActivity.isPresent())
			onExitActivity.get().perform(contextBlock);
	}

	/**
	 * Executes (starts) all of the sub-state machines that have been added to this
	 * state
	 */
	private void executeSubStateMachines()
	{
		completedSubStateMachineIDs.clear();
		subStateMachines.forEach(subStateMachine ->
		{
			if (containingStateMachine.isEmpty() && contextBlock.isPresent())
				containingStateMachine = Optional.of(contextBlock.get().stateMachine.get());
			subStateMachine.containingStateMachine = containingStateMachine;
			subStateMachine.start();
		});
	}

	/**
	 * Terminates (stops) all of the sub-state machines that have been added to this
	 * state.
	 */
	private void terminateSubStateMachines()
	{
		subStateMachines.forEach(subStateMachine -> subStateMachine.stop());
	};
}
