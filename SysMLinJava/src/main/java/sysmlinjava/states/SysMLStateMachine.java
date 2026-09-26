/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.states;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import sysmlinjava.attributetypes.DurationMilliseconds;
import sysmlinjava.attributetypes.InstantMilliseconds;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.events.SysMLCompletionEvent;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.events.SysMLTimeEvent;
import sysmlinjava.javaannotations.statemachines.FinalState;
import sysmlinjava.javaannotations.statemachines.InitialState;
import sysmlinjava.parts.SysMLPart;

/**
 * SysMLinJava's representation of the SysML state machine.
 * <h2>State machine for synch/asynch part behavior</h2>
 * {@code SysMLStateMachine} is the base class for an executable model of a
 * finite state transition system. As such, it implements the {@code Runnable}
 * interface and is typically executed asynchronously in its own thread of
 * execution in the thread-pool of the context part for which it exists.
 * However, it can be executed synchronously with the thread that submits
 * events. The {@code SysMLStateMachine} can be defined and executed either
 * within the context of a {@code SysMLPart} or as a "stand-alone" state machine
 * to model/simulate a behavior.
 * <h2>Property of the SysMLPart</h2> As an abstract class the
 * {@code SysMLStateMachine} must be extended/specialized. If the specialized
 * class for the state machine executes in the context of a {@code SysMLPart},
 * i.e. it is the part's state machine, then it should be
 * instantiated/initialized within the body of the context {@code SysMLPart}'s
 * {@code createStateMachine()} operation. When used as any other behavior
 * model, it can be instantiated within the context in which it will execute.
 * <h2>Near complete support for SysML/UML standard state machine</h2> The
 * current version of the {@code SysMLStateMachine} supports most of the
 * standard features of the SysMLv2 "state" as well as the SysMLv1/UML state
 * machine. This version supports basic states and transitions where states can
 * execute actions for the OnEnter, Do, and OnExit actions of a state. Composite
 * states and sub-state machines are supported. Pseudo states for initial,
 * choice, and junction pseudo-states are also supported. Transitions can be
 * specified for both internal and external transitions and can be associated
 * with class-based triggers, guards, and effects. And event prioritization is
 * supported. Support for history states is not yet available and is planned for
 * a future version of the {@code SysMLStateMachine}. In any case, all but the
 * most complex of state machines can be precisely modeled and simulated by this
 * version of the {@code SysMLStateMachine}.
 * <h2>State machine execution implementation</h2> The {@code SysMLStateMachine}
 * includes fields for the initial and final states of the state machine.
 * Extending classes create additional states and transitions to construct a
 * complete state machine. This base class provides an {@code onInitialEvent}
 * and an {@code onEvent} operation to execute the state machine. These
 * operations receive all events that are submitted to the state machine and
 * process them in accordance with the state machine definition. The state
 * machine execution can be performed synchronously (same thread as caller) or
 * asychronously (its own thread). Asynchronous exections are performed by the
 * state machine's {@code Runnable} interface implelmenation. The implementation
 * is, of course, the {@code run()} operation, which retrieves the events and
 * invokes {@code onInitialEvent} and an {@code onEvent} operations that
 * processes the events in accordance with the states and transitions specified
 * by the extending class. This {@code run()}, {@code onInitialEvent}, and
 * {@code onEvent} operations implement the state machine in accordance with the
 * UML standard and should not be overridden in extending classes.
 * <h2>Asynchronous event handling</h2> Asynchronous events are submitted to the
 * {@code SysMLStateMachine} via a {@code queueEvent(SysMLEvent)} operation.
 * Events are queued to a standard thread-safe Java
 * {@code PriorityBlockingQueue}, thereby enabling multiple threads to submit
 * events to the state machine. Events are queued in accordance with SysML/UML
 * standard event prioritization. The standard {@code SysMLPart} provides an
 * operation to submit events to the state machine indirectly via the part, but
 * events can be submitted directly to the state machine in a thread-safe manner
 * directly via the {@code queueEvent()} operation. Events are not prioritized
 * in this base class for the {@code SysMLStateMachine}, but event priorities
 * can be achieved by setting the {@code eventComparator} field with the desired
 * comparator. If present, the {@code eventComparator} will be used to keep the
 * events in the queue sorted accordingly.
 * <h2>Built in timer support</h2> The {@code SysMLStateMachine} provides
 * support for automatic generation of time events. Calls to the
 * {@code startTimer()} operation create timer objects that submit
 * {@code TimerEvent}s to the state machine. Processing of the time event may
 * invoke specified {@code SysMLEffect}-based "event handlers" that in-turn
 * invoke transitions and their corresponding effects.
 * <h2>Creating the state machine</h2> The {@code SysMLStateMachine} provides a
 * series of overrideable method calls to create all the states, transitions,
 * guards, and effects of the complete state machine. The
 * {@code SysMLStateMachine} constructor automatically invokes methods to create
 * the elements of the state machine so that extensions to the
 * {@code SysMLStateMachine} need only override the {@code createXxxx()}
 * operations for the applicable elements (fields) declared in the extending
 * class to ensure the system of states and transitions is created/initialized
 * in the correct and complete sequence needed.
 * <h2>Utility capabilities for parametric analysis and displays</h2> Finally,
 * the {@code SysMLStateMachine} base class provides capabilities to perform
 * utility functions during the performance of transitions between and within
 * states. The SysMLStateMachine field variable {@code transitionsUtility} of
 * interface type {@code TransitionsUtility} is invoked whenever a state
 * transition is performed. Transition information such as current state,
 * triggering event, guard, effect, and next state, are passed to the utility's
 * operations. Implementations of this interface can enable capture of the data
 * for state transitions tables, timing diagrams, or for simple capture and
 * storage of the transitions data for later review and analysis. In any case,
 * capture and display of this transitions data can enable detailed analysis of
 * the model execution and make for some audience pleasing and convincing
 * graphics for design reviews and other presentations of the model.
 * <p>
 * See the implementation referenced below for an example of using this
 * capability to transmit state transition table and timing diagram information
 * to respective displays. SysMLinJava contains two rudimentary console-based
 * applications that can receive and display the state transition table and
 * timing diagram data as simple text. However, there are commercially available
 * applications that provide real-time graphical displays of the tables and
 * diagrams. These applications also provide capabilites to save/export the
 * transition tables and timing diagrams as PDF, CSV, and HTML files enabling
 * post-model execution review and analysis of the state transitions and timings
 * of any or all of the state machines in the SysMLinJava model. Visit
 * SysMLinJava.com for more information.
 * 
 * @author ModelerOne
 * @see SysMLPart#createStateMachine()
 * @see sysmlinjava.views.statetransitionstables.StateTransitionsTransmitters
 */
public abstract class SysMLStateMachine extends SysMLAnything implements Runnable
{
	/**
	 * The initial state of the state machine. This is a SysMLinJava defined state
	 * that is used as the starting point of the state machine behavior. Extending
	 * classes should define one transition that starts at this initial state to
	 * ensure correct initialization of the state machine. The
	 * {@code InitialTransition} class is provided as convenient option for this
	 * purpose.
	 * 
	 * @see SysMLInitialState
	 * @see InitialTransition
	 */
	@InitialState
	public SysMLInitialState initialState;
	/**
	 * The final state of the state machine. This is a SysMLinJava defined state
	 * that is used as the stopping point of the state machine behavior. Extending
	 * classes should define at least one transition that ends at this final state
	 * to ensure correct completion of the state machine. The
	 * {@code FinalTransition} class is provided as a convenient option for this
	 * purpose.
	 * 
	 * @see SysMLFinalState
	 * @see FinalTransition
	 */
	@FinalState
	public SysMLFinalState finalState;
	/**
	 * The context part, port, or item for the state machine. The context is the
	 * <i>parent</i> of the state machine, i.e. the part, port, or item that
	 * executes in accordance with this state machine and whose attributes, parts,
	 * ports, or itemss may be accessed and whose actions may be invoked by the
	 * states and transitions of the state machine.
	 */
	public Optional<? extends StateBehaviorContext> context;
	/**
	 * Current state of the state machine.
	 */
	public Optional<SysMLState> currentState;
	/**
	 * Optional state machine which contains this state machine in one or more of
	 * its composite states
	 */
	public Optional<? extends SysMLStateMachine> containingStateMachine;
	/**
	 * Thread future for the thread that <i>runs</i> the state machine. Used to
	 * terminate the thread at completion of the state machine's execution.
	 */
	public Future<?> threadFuture;
	/**
	 * The optional event queue for the state machine. If set, events to the state
	 * machine are submitted to the state machine asynchronously via this queue. As
	 * a thread-safe queue, the eventQueue enables event submission by multiple
	 * execution threads, i.e. the dequeing and response of the state machine is
	 * performed in a seperate thread of execution from the threads that enque the
	 * event. If not set, events to the state machine are submitted synchronously
	 * via call to the {@code onEvent()} operation and the response of the state
	 * machine to the event is performed in the same thread of execution.
	 * <p>
	 * <b>Note:</b>As an instance of the {@code PriorityBlockingQueue} events are
	 * priorized in the queue in accordance with the SysML/UML standard state
	 * machine event queue, i.e. completion and time events are posted to the head
	 * of the queue amongst other types of events. The default implementation of the
	 * {@code Comparator<?>} interface used for the SysMLStateMachine uses this
	 * priority logic, but SysMLinJava users may set their own value for the
	 * comparator to enable another priority scheme if needed.
	 */
	private Optional<PriorityBlockingQueue<SysMLEvent>> eventQueue;
	/**
	 * The comparator of events submitted to the {@code eventQueue}. This comparator
	 * priorizes events in the queue in accordance with the SysML/UML standard state
	 * machine event queue, i.e. completion and time events are posted to the head
	 * of the queue amongst other types of events. SysMLinJava users may set their
	 * own value for the {@code eventComparator} to enable another priority scheme
	 * if needed. The different value of the {@code eventComparator} can be set
	 * anytime after the SysMLStateMachine class's constructor is invoked and before
	 * the first call to the {@code start()} operation.
	 */
	public Comparator<SysMLEvent> eventComparator;
	/**
	 * Hash map-based collection of timers created to generate and submit time
	 * events to the state machine. Timers are created via calls to the startTimer()
	 * operation and are mapped in the collection by their name.
	 */
	private ConcurrentHashMap<String, Timer> timersCollection;

	/**
	 * Optional transitions utility. If present, transitions utility implementation
	 * is invoked when transition is executed, enabling capture of state transition
	 * information for use in state transition tables and state transition timing
	 * diagrams.
	 * 
	 * @see sysmlinjava.views.statetransitionstables.StateTransitionsTransmitters
	 */
	public Optional<TransitionsUtility> transitionsUtility;

	/**
	 * Constructor of the state machine. The constructor sets the context part, sets
	 * the asynchronicity, and optionally sets a unique name for the state machine.
	 * It also initializes the eventQueue, the initial and final states, and invokes
	 * the creation operations that create all the elements of the state machine
	 * model.
	 * <p>
	 * As an abstract class, this {@code SysMLStateMachine}'s constructor must be
	 * invoked by the constructor of the class that extends/specializes the
	 * {@code SysMLStateMachine}. In turn, if the extension/specialized class is to
	 * execute within the context of a specified {@code SysMLPart} context, the
	 * constructor of the extension/specialized class should be invoked by the
	 * context's {@code createStateMachine()} operation.
	 * 
	 * @param context        the {@code SysMLPart} in whose context this state
	 *                       machine is to execute
	 * @param isAsynchronous whether this state machine is to run asynchronously,
	 *                       i.e. in its own thread of execution which is different
	 *                       from the thread(s) that may submit/enque events
	 * @param name           name of the state machine
	 */
	public SysMLStateMachine(Optional<? extends StateBehaviorContext> context, boolean isAsynchronous, String name)
	{
		super(name, 0L);
		this.context = context;
		createEventComparator();
		if (isAsynchronous)
			eventQueue = Optional.of(new PriorityBlockingQueue<SysMLEvent>(100, eventComparator));
		else
			eventQueue = Optional.empty();
		timersCollection = new ConcurrentHashMap<String, Timer>();
		transitionsUtility = Optional.empty();
		createStateCompositeStateMachines();
		createStateEntryActionFunctions();
		createStateDoActionFunctions();
		createStateExitActionFunctions();
		createStates();
		createPseudoStates();
		createGuardConditions();
		createGuards();
		createEffectActionFunctions();
		createEffects();
		createTransitions();
		createTransitionsUtility();
		createRequirements();
		createDependencies();
		currentState = Optional.empty();
	}

	/**
	 * Starts the state machine. That is, submits an initial event to transition out
	 * of the {@code InitialState} in accordance with the {@code InitialTransition}
	 * specified in an extending class.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 */
	public void start()
	{
		if (eventQueue.isPresent())
		{
			eventQueue.get().clear();
			eventQueue.get().put(new InitialEvent());
			threadFuture = context.get().getExecutionThreads().submit(this);
		}
		else
			onInitialEvent(new InitialEvent());
	}

	/**
	 * Stops the previously started state machine. Note that this operation is a
	 * "hard" stop, i.e. if the state machine's {@code run()} operation has not
	 * ended, regardless of the state of the state machine the thread in which the
	 * state machine executes is simply cancelled (terminated).
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 */
	public void stop()
	{
		if (eventQueue.isPresent())
		{
			if (!threadFuture.isDone())
			{
				logger.warning(identityString() + ": cancelling thread");
				boolean cancelled = threadFuture.cancel(true);
				if (!cancelled)
					logger.warning(identityString() + ": thread stop did not occur");
			}
			else
				logger.info(identityString() + ": thread already done");
		}
		else
			onEvent(new FinalEvent());
	}

	/**
	 * Starts a timer that generates and submits time events to the state machine.
	 * Time events are processed by the state machine in accordance with the states
	 * and transitions defined by extended classes.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param event the {@code SysMLTimeEvent} to be submitted to this state
	 *              machine's event queue upon the specified time occurance(s). The
	 *              timer's initial delay and subsequent period are specified in
	 *              this event.
	 */
	public void startTimer(SysMLTimeEvent event)
	{
		Timer timer = new Timer(event);
		timersCollection.put(event.timerID, timer);
	}

	/**
	 * Starts a timer that generates and submits time events to the state machine.
	 * Time events are processed by the state machine in accordance with the states
	 * and transitions defined by extended classes.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param timerID      The unique identifier of the new timer. (used to identify
	 *                     the timer to stop)
	 * @param initialDelay The initial delay before the first time event
	 * @param period       The period between successive time events. A period of 0
	 *                     indicates a single (one-shot) timer is to be created.
	 */
	public void startTimer(String timerID, DurationMilliseconds initialDelay, DurationMilliseconds period)
	{
		Timer timer = new Timer(timerID, initialDelay, period);
		timersCollection.put(timerID, timer);
	}

	/**
	 * Stop the identified timer. Time events will no longer be generated by the
	 * timer.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param timerID unique identifier of the timer to be stopped
	 */
	public void stopTimer(String timerID)
	{
		Optional<Timer> timer = Optional.ofNullable(timersCollection.get(timerID));
		if (timer.isPresent())
		{
			timer.get().timerThreadFuture.cancel(true);
			timersCollection.remove(timerID);
		}
		else
			logger.warning(getClass().getSimpleName() + ": timer with timerID \"" + timerID + "\" not found");
	}

	/**
	 * Enqueues the specified event to the state machine's event queue. The
	 * operation is called for operation of the state machine that is asynchronous
	 * with (in a different thread from) the thread that submits the next event.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param event event to be enqueued
	 */
	public void queueEvent(SysMLEvent event)
	{
		if (eventQueue.isPresent())
			eventQueue.get().put(event);
		else
			onEvent(event);
	}

	/**
	 * {@code Runnable} operation that executes the state machine asychronously (in
	 * a different thread) from the submitter(s) of events. The {@code run()}
	 * operation takes events off the event queue and processes them in accordance
	 * with the states and transitions defined by the
	 * {@code SysMLStateMachine}-based class heirarchy specified for the state
	 * machine. It invokes the {@code onInitialEvent()} operation to start the state
	 * machine (transition out of the initial state) and the {@code onEvent()}
	 * operation for all subsequent events it receives. These operations are
	 * performed in the runnable's thread of execution to perform the event
	 * processing asynchronously from other parts' state machines.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 */

	int count = 0;

	@Override
	public void run()
	{
		logger.info(this.getClass().getSimpleName() + " 0");
		if (eventQueue.isPresent())
		{
			try
			{
				SysMLEvent firstEvent = eventQueue.get().take();
				logger.info(this.getClass().getSimpleName() + " 1");
				if (firstEvent != null && firstEvent instanceof InitialEvent)
					onInitialEvent((InitialEvent) firstEvent);
				else
					logger.warning(identityString() + " first eventQueue.take() is null or not recognized as InitialEvent type");
				while (currentState.isPresent() && currentState.get() != finalState)
				{

					SysMLEvent nextEvent = eventQueue.get().take();
					onEvent(nextEvent);
				}
				if (currentState.isPresent() && currentState.get() == finalState)
				{
					timersCollection.forEach((id, timer) -> timer.timerThreadFuture.cancel(true));
					eventQueue.get().clear();
					if (containingStateMachine.isPresent())
						containingStateMachine.get().queueEvent(new SysMLCompletionEvent("sub-stateMachine completed", "SubStateMachineCompleted", id));
				}
				logger.info(identityString() + ": run() completed");
			} catch (InterruptedException e)
			{
				logger.info(identityString() + ": eventQue.take() interrupted");
			}
		}
		else
			logger.warning(getClass().getSimpleName() + ": no event queue present as state machine is synchronous");
	}

	/**
	 * Event handler for the initial event. That is, for the first event received by
	 * the state machine to transition from the initial state. The operation
	 * performs the initial transition then it determines the next transition, if
	 * any, that is active and performs that transition, until no active transitions
	 * are found or the final state is reached.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param initialEvent initial event received while in initial state.
	 */
	public synchronized void onInitialEvent(InitialEvent initialEvent)
	{
		currentState = Optional.empty();
		InitialTransition initialTransition = getInitialTransition(initialState);
		SysMLVertex nextVertex = performInitialTransition(initialTransition, context);
		Optional<SysMLTransition> nextTransition = getNextTransition(nextVertex, Optional.empty(), context);
		while (nextVertex != finalState && nextTransition.isPresent())
		{
			nextVertex = performTransition(nextTransition.get(), nextVertex, Optional.empty(), context);
			nextTransition = getNextTransition(nextVertex, Optional.empty(), context);
		}
		if (nextVertex instanceof SysMLState)
			currentState = Optional.of((SysMLState) nextVertex);
		else
			logger.severe("next/last vertex after initial event is not a state-type vertex: " + nextVertex.identityString());
	}

	/**
	 * Event handler for the state machine. The event handler traverses the
	 * statemachine's states and transitions network in accordance with the
	 * SysML/UML standard state machine.
	 * <p>
	 * This operation should not be called directly by client parts, such as
	 * {@code SysMLFullPort}s or {@code SysMLPart}s. Instead submission of
	 * {@code SysMLEvent}s to this operation should be done via the
	 * {@code queueEvent()} operation. ({@code SysMLPart} has an
	 * {@code acceptEvent()} operation that calls its state machine's
	 * {@code queueEvent()} operation for you}) This {@code queueEvent()} operation
	 * will correctly invoke the {@code onEvent()} operation in accordance with the
	 * synchronous nature of the state machine, i.e. if the {@code isAsynchronous}
	 * argument of the constructor was set to true, then the event will be submitted
	 * to the state machine via the thread-safe event queue, but if the
	 * {@code isAsynchronous} argument of the constructor was set to false, then the
	 * event will be submitted via a direct call to the {@code onEvent()} operation.
	 * The {@code OnEvent} operation use the following logic:<br>
	 * if the current state has sub-state machines for which one or more of the
	 * these will react to the current event, then the event is enqueued to each of
	 * the sub-state machines.<br>
	 * Other wise, the event is processed for the current state as follows.
	 * <p>
	 * If the event is not a completion event or is a completion event and indicates
	 * all sub-state machines are completed, then the current state is queried for
	 * the next transition.<br>
	 * If a next transition is found, then the next transition is performed and a
	 * new (next) state vertex is set.<br>
	 * If the next vertex is not the same as the current vertex, i.e. the transition
	 * was not an internal transition, then the next vertex is set as the current
	 * vertex and the process is repeated, i.e. the next transition is found and
	 * performed.<br>
	 * The process repeats until no next-transition is found.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @see SysMLStateMachine#queueEvent(SysMLEvent)
	 * @param event The next event to be processed in accordance with this state
	 *              machine's specifed states and transitions.
	 */
	public synchronized void onEvent(SysMLEvent event)
	{
		if (currentState.isPresent())
			if (!subStateMachineUses(currentState.get(), event))
			{
				if (!(event instanceof SysMLCompletionEvent) || event instanceof SysMLCompletionEvent && currentState.get().subStateMachinesCompleted((SysMLCompletionEvent) event))
				{
					SysMLVertex currentVertex = currentState.get();
					Optional<SysMLTransition> nextTransition = getNextTransition(currentVertex, Optional.of(event), context);
					if (nextTransition.isPresent())
					{
						SysMLVertex nextVertex = performTransition(nextTransition.get(), currentVertex, Optional.of(event), context);
						do
							if (nextVertex != currentVertex)
							{
								currentVertex = nextVertex;
								if (currentVertex != finalState)
								{
									nextTransition = getNextTransition(currentVertex, Optional.empty(), context);
									if (nextTransition.isPresent())
										nextVertex = performTransition(nextTransition.get(), currentVertex, Optional.empty(), context);
								}
								else
									nextTransition = Optional.empty();
							}
							else
								nextTransition = Optional.empty();
						while (nextTransition.isPresent());
						if (currentVertex instanceof SysMLState)
							currentState = Optional.of((SysMLState) currentVertex);
						else
							logger.severe("next/last vertex after event " + event.identityString() + " is not a state-type vertex: " + currentVertex.identityString());
					}
				}
			}
			else
				for (SysMLStateMachine subStateMachine : currentState.get().subStateMachines)
					subStateMachine.queueEvent(event);
		else
			logger.severe("currentState is not present for processing of event: " + event.getClass().getSimpleName());
	}

	/**
	 * Overridable operation that should return a list of identity strings for each
	 * of the states in the state machine. This method must be overridden if the
	 * state machine's state transition times transmissions is enabled, i.e. the
	 * list is used by the {@code enableStateTransitionTimesTransmission()}
	 * operation. Specializations of the {@code SysMLStateMachine} should override
	 * this operation by invoking this base class operation and then adding its
	 * additionally specified states, and the {@code finalState}, to the list
	 * returned by this operation.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @return list of state names (identity strings) for states in the state
	 *         machine. For this base class, the list contains only the identity
	 *         string for the {@code initialState}.
	 */
	public List<String> listOfStates()
	{
		List<String> result = new ArrayList<>();
		result.add(initialState.identityString());
		return result;
	}

	/**
	 * Returns the first state vertex that is reached after performance of the
	 * initial transition.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param initialTransition the initial transition
	 * @param context           the context part of this state machine
	 * @return the first state vertex reached after initial transition
	 */
	private SysMLVertex performInitialTransition(InitialTransition initialTransition, Optional<? extends StateBehaviorContext> context)
	{
		SysMLVertex result = null;
		if (initialTransition.effect.isPresent())
			initialTransition.effect.get().perform(Optional.empty(), context);
		result = initialTransition.nextState;

		if (transitionsUtility.isPresent())
			transitionsUtility.get().perform(context, this, initialState, initialTransition, initialTransition.effect, result, logger);

		if (result instanceof SysMLState)
		{
			((SysMLState) result).onEnter();
			((SysMLState) result).doWhileInState();
		}
		return result;
	}

	/**
	 * Returns the next state vertex after performing the specified transition for
	 * the specified event from the specified vertex, and any subsequently
	 * encountered active transitions.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param transition transition to be performed
	 * @param fromVertex vertex from which the transition begins
	 * @param event      event that triggers the transition
	 * @param context    part in whose context this state machine exists
	 * @return next state vertex after performing the transition.
	 */
	private SysMLVertex performTransition(SysMLTransition transition, SysMLVertex fromVertex, Optional<SysMLEvent> event, Optional<? extends StateBehaviorContext> context)
	{
		SysMLVertex result = null;
		boolean isInternalTransition = transition.nextState.equals(fromVertex) && transition.transitionKind == SysMLTransitionKind.internal;
		if (!isInternalTransition && fromVertex instanceof SysMLState)
			((SysMLState) fromVertex).onExit();
		if (transition.effect.isPresent())
			transition.effect.get().perform(event, context);
		result = transition.nextState;

		if (transitionsUtility.isPresent())
			transitionsUtility.get().perform(context, this, fromVertex, event, transition, transition.guard, transition.effect, result, logger);

		if (!isInternalTransition && result instanceof SysMLState)
		{
			((SysMLState) result).onEnter();
			((SysMLState) result).doWhileInState();
		}
		return result;
	}

	/**
	 * Returns the initial transition from the initial state.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param initialState the initial state which contains the initial transition
	 * @return the initial transition from the initial state
	 */
	private InitialTransition getInitialTransition(SysMLInitialState initialState)
	{
		InitialTransition result = null;
		if (!initialState.transitions.isEmpty())
		{
			SysMLTransition transition = initialState.transitions.get(0);
			if (transition instanceof InitialTransition)
				result = ((InitialTransition) transition);
			else
				logger.severe("transition from initial state is not of type InitialTransition: " + transition.getClass().getSimpleName());
		}
		return result;
	}

	/**
	 * Returns the next transition to be performed from the specified state vertex
	 * for the specified event.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param fromVertex   the state vertex from which the transition is to be found
	 * @param currentEvent the current event that is to trigger the next transition,
	 *                     if any, from the vertex
	 * @param context      the part in whose context this state machine operates
	 * @return the next transition to be performed, if any
	 */
	private Optional<SysMLTransition> getNextTransition(SysMLVertex fromVertex, Optional<SysMLEvent> currentEvent, Optional<? extends StateBehaviorContext> context)
	{
		Optional<SysMLTransition> result = Optional.empty();
		ListIterator<? extends SysMLTransition> stateTransitionsIterator = fromVertex.transitions.listIterator();
		while (stateTransitionsIterator.hasNext() && !result.isPresent())
		{
			SysMLTransition candidateTransition = stateTransitionsIterator.next();
			if (candidateTransition.trigger.isPresent())
			{
				if (currentEvent.isPresent())
					if (currentEvent.get().isOfEventType(candidateTransition.trigger.get()))
						if (candidateTransition.guard.isPresent())
						{
							if (candidateTransition.guard.get().isSatisfied(currentEvent))
								result = Optional.of(candidateTransition);
						}
						else
							result = Optional.of(candidateTransition);
			}
			else if (candidateTransition.guard.isPresent())
			{
				if (candidateTransition.guard.get().isSatisfied(currentEvent))
					result = Optional.of(candidateTransition);
			}
			else
				result = Optional.of(candidateTransition);
		}
		return result;
	}

	/**
	 * Returns whether or not any of the sub-state machines in the specified state
	 * uses (reacts to) the specified event.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param currentState state that contains the sub-state machines.
	 * @param currentEvent event to be reacted to (used) by any of the sub-state
	 *                     machines
	 * @return true of any of the sb-state machines uses the event, false otherwise
	 */
	private boolean subStateMachineUses(SysMLState currentState, SysMLEvent currentEvent)
	{
		boolean isUsed = false;
		if (!currentState.subStateMachines.isEmpty())
		{
			ListIterator<? extends SysMLStateMachine> subStateMachines = currentState.subStateMachines.listIterator();
			while (subStateMachines.hasNext() && !isUsed)
			{
				SysMLStateMachine subStateMachine = subStateMachines.next();
				// TODO make subStateMachine.currentState.get() synchronous
				Optional<SysMLTransition> selectedTransition = getNextTransition(subStateMachine.currentState.get(), Optional.of(currentEvent), context);
				if (selectedTransition.isPresent())
					isUsed = true;
			}
		}
		return isUsed;
	}

	/**
	 * Overridable operation that creates the event comparator that implements the
	 * {@code Comparator<SysMLEvent>} interface used by the event queue to
	 * prioritize events in the SysMLStateMachine's event queue. This operation sets
	 * the {@code eventComparator} to a default comparator which gives all events
	 * equal priority, i.e. no prioritization of events. This operation can/should
	 * be overridden in extended classes if/when event prioritization is needed.
	 */
	protected void createEventComparator()
	{
		eventComparator = new DefaultEventComparator();
	}

	/**
	 * Overridable operation that should create the state machines of any composite
	 * states of the state machine. The SysMLState requires all sub-state machines
	 * to be specified as a list, in which case each of the statements in this
	 * overridden operation should be the assignment of a list of state machines to
	 * this state machine's field variable for the list, similar to the following.
	 * <p>
	 * An example follows:
	 *
	 * <pre>
	 * {@code
		my1stNormalOpsStateMachine = new NormalStateMachine(((ControlSystem)context.get()).my1stSubSystem, "my1stNormalStateMachine", 1L);
		my2ndNormalOpsStateMachine = new NormalStateMachine(((ControlSystem)context.get()).my2ndSubSystem, "my2ndNormalStateMachine", 2L);
		my3rdNormalOpsStateMachine = new NormalStateMachine(((ControlSystem)context.get()).my3rdSubSystem, "my3rdNormalStateMachine", 3L);
		my4thNormalOpsStateMachine = new NormalStateMachine(((ControlSystem)context.get()).my4thSubSystem, "my4thNormalStateMachine", 4L);
		my1stEmergencyOpsStateMachine = new EmergencyStateMachine(((ControlSystem)context.get()).my1stSubSystem, "my1stEmergencyStateMachine", 1L);
		my2ndEmergencyOpsStateMachine = new EmergencyStateMachine(((ControlSystem)context.get()).my2ndSubSystem, "my2ndEmergencyStateMachine", 2L);
		my3rdEmergencyOpsStateMachine = new EmergencyStateMachine(((ControlSystem)context.get()).my3rdSubSystem, "my3rdEmergencyStateMachine", 3L);
		my4thEmergencyOpsStateMachine = new EmergencyStateMachine(((ControlSystem)context.get()).my4thSubSystem, "my4thEmergencyStateMachine", 4L);
	 * etc.}
	 * </pre>
	 */
	protected void createStateCompositeStateMachines()
	{
	}

	/**
	 * Overridable operation that should create the state entry action functions, if
	 * any, performed by the states' upon entry.
	 * <p>
	 * An example follows:
	 * 
	 * <pre>
	 * {@code
	
			&#64;EntryActionFunction
			SysMLEntryActionFunction myFirstOnEnterActionFunction;
			&#64;EntryActionFunction
			SysMLEntryActionFunction myNextOnEnterActionFunction;
				:
			&#64;Override
			protected void createStateEntryActionFunctions()
			{
				myFirstOnEnterActionFunction = (context) ->
				{
					&lt;block statements&gt;
				};
				myNextOnEnterActionFunction = (context) ->
				{
					&lt;block statements&gt;
				};
			}
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.states.SysMLEntryActionFunction
	 */
	protected void createStateEntryActionFunctions()
	{
	}

	/**
	 * Overridable operation that should create the action functions, if any,
	 * performed by the states' Do operation of the SysMLState.
	 * <p>
	 * An example follows:
	 * 
	 * <pre>
	 * {@code
	
		&#64;DoActionFunction
		SysMLDoActionFunction myFirstDoActionFunction;
		&#64;DoActionFunction
		SysMLDoActionFunction myNextDorActionFunction;
			:
		&#64;Override
		protected void createStateDoActionFunctions()
		{
			myFirstDoActionFunction = (context) ->
			{
				&lt;block statements&gt;
			};
			myNextDoActionFunction = (context) ->
			{
				&lt;block statements&gt;
			};
		}
	}
	 * </pre>
	 * 
	 * <b>Note:</b> the {@code SysMLDoActionFunction} is executed in its own thread,
	 * different from the one that executes the state machine. The Do action thread
	 * is started immediately after completion of the {@code SysMLOnEnterFunction}
	 * and is either terminated (canceled) if/when a transition-causing event is
	 * received by the state machine, or self-terminates after run-to-completion. In
	 * the latter case, the the Do action should perform a {@code queueEvent()} of a
	 * {@code SysMLCompletionEvent} to the state machine to enable state transitions
	 * that depend on such completions, if any.
	 * 
	 * @see SysMLDoActionFunction
	 */
	protected void createStateDoActionFunctions()
	{
	}

	/**
	 * Overridable operation that should create the state exit action functions, if
	 * any, performed by the states' just before exit.
	 * <p>
	 * An example follows:
	 * 
	 * <pre>
	 * {@code
	
			&#64;ExitActionFunction
			SysMLExitActionFunction myFirstOnExitActionFunction;
			&#64;ExitActionFunction
			SysMLExitActionFunction myNextOnExitActionFunction;
				:
			&#64;Override
			protected void createStateExitActionFunctions()
			{
				myFirstOnExitActionFunction = (context) ->
				{
					&lt;block statements&gt;
				};
				myNextOnExitActionFunction = (context) ->
				{
					&lt;block statements&gt;
				};
			}
		}
	 * </pre>
	 * 
	 * @see SysMLExitActionFunction
	 */
	protected void createStateExitActionFunctions()
	{
	}

	/**
	 * Overridable operation that should create the state machine's states.
	 * <p>
	 * An example follows:
	 * 
	 * <pre>
	protected void createStates()
	{
		super.createStates();
		myFirstState = new SysMLState(context, "FirstState");
		mySecondState = new SysMLState(context, Optional.of(secondOnEnterAction), Optional.of(secondDoAction), Optional.of(secondOnExitAction), "Second");
		myThirdState = new SysMLState(context, Optional.of(thirdOnEnterAction), Optional.empty(), Optional.empty(), "Third");
		myEmergencyState = new SysMLState(context, Optional.empty(),
		List.of(my1stEmergencyOpsStateMachine, my2ndEmergencyOpsStateMachine, my3rdEmergencyOpsStateMachine, my4thEmergencyOpsStateMachine), Optional.empty(), "EmergencyOps");
		myOtherState = new SysMLState(context, "Other");
	}
	</pre>
	 * where the first statement is invoking the states creation of the superclass,
	 * i.e. a default creation of the {@code FinalState}. Other statements create
	 * the states of the state machine as object creation expressions. The target of
	 * the assignment operation is the name of a field with the {@code State}
	 * annotation. The value of the assignment is one of the
	 * {@code new SysMLState()} constructors for the state.
	 * <p>
	 * <b>Note:</b> All states must be declared as type SysMLState. The current
	 * version of SysMLinJava does not recognize extension classes for the state.
	 * Also, the InitialState and FinalState should not be created in this
	 * operation. These states are created by the overridden operation, which must
	 * be invoked as the first statement in this operation.
	 * 
	 * @see SysMLState
	 */
	protected void createStates()
	{
		finalState = new SysMLFinalState(context, "Final");
	}

	/**
	 * Overridable operation that should create the state machine's pseudo-states.
	 * <p>
	 * An example follows:
	 * 
	 * <pre>
	 * {@code
	 * super.createPseudoStates();
	 * myJunctionPseudo = new SysMLJunctionPseudoState(context, "MyJunction");
	 * myChoicePseudoState = new SysMLChoicePseudoState(context, "MyChoice");
	 * }
	 * </pre>
	 * <p>
	 * <b>Note:</b> All pseudo-states must be declared/initialized as type
	 * {@code SysMLInitialState()}, {@code SysMLJunctionPseudoState()}, or
	 * {@code SysMLChoicePseudoState()}. The current version of SysMLinJava does not
	 * recognize extension classes for these pseudo-states. Also, other
	 * pseudo-states are not yet implemented in SysMLinJava.
	 * 
	 * @see SysMLInitialState
	 * @see SysMLJunctionPseudoState
	 * @see SysMLChoicePseudoState
	 */
	protected void createPseudoStates()
	{
		initialState = new SysMLInitialState(context, "Initial");
	}

	/**
	 * Overridable operation that should create the conditions that define the
	 * transition guards.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
	 * {@code
	 * myFirstGuardCondition = (event, context) ->
	 * {
	 * 	part statement;
	 * };
	 * mySecondGuardCondition = (event, context) ->
	 * {
	 * 	part statement;
	 * };
	 * }
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code GuardCondition} annotation and the assignment expression is a
	 * lambda expression with parameters {@code event} and {@code context} that
	 * represent the SysMLEvent that triggered the transition and the part in whose
	 * context the state machine is executing, respectively. The lambda expression
	 * is an implementation of the {@code SysMLGuardCondition} functional interface.
	 * 
	 * @see SysMLGuardCondition
	 */
	protected void createGuardConditions()
	{
	}

	/**
	 * Overridable operation that should create the state machine's guards, if any,
	 * that are used as conditions on transitions.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
	 * {@code
	 *  myFirstGuard = new SysMLGuard(myFirstGuardCondition, "FirstGuard");
	 * mySecondGuard = new SysMLGuard(mySecondGuardCondition,  "SecondGuard");
	 * etc.}
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code Guard} annotation and {@code SysMLGuard()} is the
	 * {@code SysMLGuard} constructor for the guard.<br>
	 * <b>Note:</b> All guards must be declared as type SysMLGuard. The current
	 * version of SysMLinJava does not recognize extension classes for the guard.
	 * 
	 * @see SysMLGuard
	 */
	protected void createGuards()
	{
	}

	/**
	 * Overridable operation that should create the actions, if any, performed as
	 * transition effects.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
	 * myFirstEffectAction = (event, context) ->
	 * {
	 * 	part statement;
	 * };
	 * }
	 * mySecondEffectAction = (event, context) ->
	 *    {
	 *       part statement;
	 *    };
	 * }
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code EffectAction} annotation and the assignment expression is a lambda
	 * expression with parameters {@code event} and {@code context} that represent
	 * the SysMLEvent that triggered the transition and the part in whose context
	 * the state machine is executing, respectively. The lambda expression is an
	 * implementation of the {@code SysMLAction} functional interface.
	 * 
	 * @see SysMLEffectActionFunction
	 */
	protected void createEffectActionFunctions()
	{
	}

	/**
	 * Overridable operation that should create the state machine's effects, if any,
	 * that are used as effects of transitions.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
	 * {@code
	 * myFirstEffect = new SysMLEffect(context, myFirstEffectAction, "FirstTransitionEffect");
	 * myNextEffect = new SysMLEffect(context, myNextEffectAction, "NextTransitionEffect");
	 * etc.}
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code Effect} annotation and {@code SysMLEffect()} is the constructor
	 * for the effect.
	 * <p>
	 * <b>Note:</b> All effects must be declared as type SysMLEffect. The current
	 * version of SysMLinJava does not recognize specialized/extension classes for
	 * the effect.
	 * 
	 * @see SysMLEffect
	 * @see SysMLEffectActionFunction
	 */
	protected void createEffects()
	{
	}

	/**
	 * Overridable abstract operation that should create the state machine's
	 * transitions that are used as transitions between and within states. The
	 * transitions created must include the initial transition needed for the
	 * state-machine to start execution, i.e. the {@code InitialTransition}. Use of
	 * the {@code FinalTransition} to transition to the {@code FinalState} is
	 * optional, but the state machine will continue to execute unless and until it
	 * reaches the {@code FinalState}, so if the {@code FinalTransition} is not
	 * used, a correctly constructed {@code SysMLTransition} must be used to
	 * transition to the final state.
	 * <p>
	 * The {@code InitialTransition} must indicate a previous state of the
	 * {@code InitialState} type and the {@code FinalTransition} (or a
	 * SysMLTransition to the final state) must indicate a next state of the
	 * {@code FinalState} type. The {@code SysMLStateMachine} declares and
	 * constructs an instance of the {@code InitialState} named {@code initialState}
	 * and of the {@code FinalState} named {@code finalState} that can/should be
	 * used for this purpose.
	 * <p>
	 * Typically, the {@code InitialTransition} will be initialized (constructor
	 * call) as the first statement in this {@code createTransitions()} operation.
	 * The {@code new InitialTransition} constructor's {@code nextState} will be a
	 * state specified in the specialization of the {@code SysMLStateMachine} and
	 * the {@code FinalTransition} will be initialized (constructor call) with a
	 * {@code prevState} as an arbitrary state specified in the specialization of
	 * the {@code SysMLStateMachine}. All other transitions can be initialized with
	 * desired {@code prevState/nextState} combinations as well as with specified
	 * triggers, guards, effects, and kinds.
	 * <p>
	 * Transitions from a state should have triggers and guards that result in only
	 * zero or one transition being available for execution by the state machine.
	 * Typically, this means all trigger/guard pairs for transition within or out of
	 * a state should be unique. If more than one transition is available for
	 * execution, the transition that is selected is undefined. However, the current
	 * implementation of the state machine will select the first transition declared
	 * in this {@code createTransitions()} operation that is available for
	 * execution, i.e. the first constructed transition that has the correct trigger
	 * and a satisfied guard or a null guard.
	 * <p>
	 * Example code format:<br>
	 * 
	 * <pre>
	 * {@code
	 *     intialTransition = new InitialTransition(context, initialState,  firstState, "Initial";
	 *    myFirstTransition = new SysMLTransition(context,   firstState, secondState, Optional.of(FirstTriggerEvent.class), Optional.of(firstGuard), Optional.of(firstEffect), "First", SysMLTransitionKind.external);
	 *   mySecondTransition = new SysMLTransition(context,  secondState,  thirdState, Optional.empty(), Optional.of(secondGuard), Optional.empty(), "Second", SysMLTransitionKind.external);
	 * myInternalTransition = new SysMLTransition(context,  secondState, secondState, Optional.of(SecondTriggerEvent), Optional.of(secondGuard), Optional.of(secondEffect), "Internal", SysMLTransitionKind.internal);
	 *    myFinalTransition = new SysMLTransition(context,   thirdState,  finalState, Optional.of(ThirdTriggerEvent.class), Optional.of(thirdGuard), Optional.empty(), "Final", SysMLTransitionKind.external);
	 * --OR--
	 *    myFinalTransition = new FinalTransition(context,   thirdState,  finalState, "Final");
	 * etc.}
	 * </pre>
	 * <p>
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code Transition} annotation and
	 * {@code new InitialTransition(), new SysMLTransition(), and new FinalTransition()}
	 * is the constructor for the transition.
	 * <p>
	 * <b>Note:</b> All transitions (except the {@code InitialTransition} and
	 * {@code FinalTransition}) must be declared as type {@code SysMLTransition}.
	 * The current version of SysMLinJava does not recognize specialized/extension
	 * classes for the transition.
	 * 
	 * @see SysMLTransition
	 * @see InitialTransition
	 * @see FinalTransition
	 */
	protected abstract void createTransitions();

	/**
	 * Overridable operation that initializes the state machine's requirements. An
	 * example follows:
	 * 
	 * <pre>
	 * &#64;Requirement
	 * public SysMLRequirement myFirstRequirement;
	 * 
	 * &#64;Requirement
	 * public SysMLRequirement myNextRequirement;
	 * </pre>
	 * 
	 * <pre>
	 * myFirstRequirement = MySysMLRequirements.firstSysMLRequirement;
	 * myNextRequirement = MySysMLRequirements.nextSysMLRequirement;
	 * </pre>
	 * 
	 * where the the target of the assignment operation is the name of a field with
	 * the {@code &#64;Requirement} annotation and
	 * {@code MySysMLRequirements.firstSysMLRequirement} is a reference to an
	 * instance of a {@code SysMLRequirement} in an extension/specialization of the
	 * {@code SysMLRequirements} class.
	 * <p>
	 * <b>Note</b> that state machine requirements should be declared as described
	 * above, i.e. as references to instantiations of {@code SysMLRequirement}s
	 * declared and initialized in classes that extend/specialize the
	 * {@code SysMLRequirements} class. Requirements should <b>not</b> be
	 * declared/initialized in the state machine itself. Requirement specifications
	 * in the typical SysML model are collected in a model package where they can be
	 * easily queried and managed. The SysMLinJava approach of collecting
	 * requirements in a single class emulates this method.
	 * 
	 * @see sysmlinjava.requirements.SysMLRequirementsCollection
	 */
	protected void createRequirements()
	{
	}

	/**
	 * Overridable operation that creates the dependencies for this state machine.
	 * Overrides of this operation should perform initializations of variables
	 * annotated as dependencies. Whereas dependencies are types of association,
	 * each dependency should be defined by a field annotated as a
	 * 
	 * <pre>
	 * &#64;Dependency
	 * public APartPart aPartDependency;
	 * </pre>
	 * 
	 * and initialized by an object reference For example:
	 * 
	 * <pre>
	 * aPartDependency = ((ParentPart) context).bPartPart;
	 * </pre>
	 * 
	 * where {@code aDependency} is the name of a variable that represents a
	 * dependency (trace, usage, etc} of this part on another model element. Note
	 * the assignment value should be a reference to another model element, and not
	 * to a "new" object (constructor).
	 */
	protected void createDependencies()
	{
	}

	/**
	 * Creates the transitions utility for the state machine to invoke upon
	 * performance of a transition. An example is as follows.
	 * 
	 * <pre>{@code
	 * protected void createTransitionsUtility()
	 * {
	 * 	transitionsUtility = Optional.of(new StateTransitionsTransmitters(Optional.of(new StateTransitionTablesTransmitter(StateTransitionsDisplay.udpPort, true)),
	 * 	Optional.of(new TimingDiagramsTransmitter(TimingDiagramsDisplay.udpPort, true))));
	 * }
	 * }</pre>
	 */
	protected void createTransitionsUtility()
	{
	}

	/**
	 * Interface for an optional implementation of a utility for accessing the state
	 * transitions information (current state, event, guard, effect, next state,
	 * etc.) that are available during state machine execution. The interface's
	 * operations are invoked by the {@code SysMLStateMachine} during performance of
	 * state transistions, i.e. during the {@code performInitialTransition()} and
	 * {@code performTransition()} operations. The implementation of the
	 * {@code TransitionsUtiliy} should be created in an override of the
	 * {@code createTransitionsUtility()} operation by initializing the
	 * {@code transitionsUtility} variable, e.g.
	 * 
	 * <pre>
	 * {@code
	 * transitionsUtility = new MyTransitionsUtility(int arg);
	 * }
	 * </pre>
	 * 
	 * where the constructor is for a class that implements the
	 * {@code TransitionsUtility} interface. The referenced class below from the
	 * SysMLinJava tools package is an example implementation of this interface for
	 * transmitting state transition table and timing diagram information to display
	 * applications.
	 * 
	 * @author ModelerOne
	 * @see sysmlinjava.views.statetransitionstables.StateTransitionTablesTransmitter
	 */
	public static interface TransitionsUtility
	{
		/**
		 * Specifies the method call to transmit to a state transitions table display
		 * the specified state transition information. The transition information is
		 * converted to appropriate text for state table rows and transmitted to the
		 * display.
		 * 
		 * @param context      state machine's context part
		 * @param stateMachine state machine
		 * @param currentState current state of state machine
		 * @param currentEvent current event responding to by state machine
		 * @param transition   current transition of state machine
		 * @param guard        current guard of current transition
		 * @param effect       current effect of current transition
		 * @param nextState    next state after transition
		 * @param logger       logger to be used for logging
		 */
		void perform(Optional<? extends StateBehaviorContext> context, SysMLStateMachine stateMachine, SysMLVertex currentState, Optional<SysMLEvent> currentEvent, SysMLTransition transition, Optional<SysMLGuard> guard, Optional<SysMLEffect> effect, SysMLVertex nextState, Logger logger);

		/**
		 * Specifies the method call to transmit to a state transitions table display
		 * the specified (minimal) state transition information. The transition
		 * information is converted to appropriate text for state table rows and
		 * transmitted to the display.
		 * 
		 * @param context      state machine's context part
		 * @param stateMachine state machine
		 * @param initialState initial state of state machine
		 * @param transition   current transition of state machine
		 * @param effect       current effect of current transition
		 * @param nextState    next state after transition
		 * @param logger       logger to be used for logging
		 */
		void perform(Optional<? extends StateBehaviorContext> context, SysMLStateMachine stateMachine, SysMLInitialState initialState, InitialTransition transition, Optional<SysMLEffect> effect, SysMLVertex nextState, Logger logger);
	}

	/**
	 * SysMLinJava default implementation of the {@code Comparator<SysMLEvent>}
	 * interface used by the event queue to prioritize events in the
	 * SysMLStateMachine's event queue. This default comparator gives all events
	 * equal priority, i.e. no prioritization of events. The event comparator is set
	 * in the {@code createEventComparator()} operation. This operation can/should
	 * be overridden in extended classes to initialize the {@code eventComparator}
	 * with a specialized event comparator if/when event prioritization of events is
	 * needed.
	 * 
	 * @author ModelerOne
	 */
	public class DefaultEventComparator implements Comparator<SysMLEvent>
	{
		/**
		 * Constructor - default, no initializations
		 */
		public DefaultEventComparator()
		{
			super();
		}

		/**
		 * Compares two events for order. Returns a negative integer, zero, or a
		 * positive integer as the first argument is less than, equal to, or greater
		 * than the second. @Override
		 */
		@Override
		public int compare(SysMLEvent left, SysMLEvent right)
		{
			if (left instanceof SysMLTimeEvent)
				if (right instanceof SysMLTimeEvent)
					return 0;
				else
					return 1;
			else if (right instanceof SysMLTimeEvent)
				return -1;
			else
				return 0;

		}
	}

	/**
	 * Timer for generating time events for state machine. The timers are Runnables
	 * and "run" in a thread managed by the thread pool of the state machine's
	 * context part. The timers are started by calling the state machine's
	 * startTimer() operation.
	 * 
	 * @author ModelerOne
	 */
	public class Timer implements Runnable
	{
		/**
		 * Time event sent to state machine when timer expires
		 */
		private SysMLTimeEvent event;
		/**
		 * Future for (reference to) the started timer
		 */
		private ScheduledFuture<?> timerThreadFuture;

		/**
		 * Timer constructor for relative time
		 * 
		 * @param timerID      The unique identifier of the new timer. (used to identify
		 *                     the timer to stop)
		 * @param initialDelay The initial delay before the first time event
		 * @param period       The period between successive time events. A period of 0
		 *                     indicates a single (one-shot) timer is to be created.
		 */
		public Timer(String timerID, DurationMilliseconds initialDelay, DurationMilliseconds period)
		{
			super();
			event = new SysMLTimeEvent(timerID, initialDelay, Optional.of(period));
			if (!period.isZero())
				timerThreadFuture = context.get().getExecutionThreads().scheduleAtFixedRate(this, initialDelay.value, period.value, TimeUnit.MILLISECONDS);
			else
				timerThreadFuture = context.get().getExecutionThreads().schedule(this, initialDelay.value, TimeUnit.MILLISECONDS);
		}

		/**
		 * Timer constructor for absolute time
		 * 
		 * @param timerID      The unique identifier of the new timer. (used to identify
		 *                     the timer to stop)
		 * @param absoluteTime the instant at which the event is to occur
		 * @param period       Optional period of repetion of the event after the first
		 *                     event at absoluteTime
		 */
		public Timer(String timerID, InstantMilliseconds absoluteTime, Optional<DurationMilliseconds> period)
		{
			super();
			event = new SysMLTimeEvent(timerID, absoluteTime, period);
			DurationMilliseconds initialDelay = DurationMilliseconds.between(InstantMilliseconds.now(), absoluteTime);
			if (period.isPresent())
				timerThreadFuture = context.get().getExecutionThreads().scheduleAtFixedRate(this, initialDelay.value, period.get().value, TimeUnit.MILLISECONDS);
			else
				timerThreadFuture = context.get().getExecutionThreads().schedule(this, initialDelay.value, TimeUnit.MILLISECONDS);
		}

		/**
		 * Timer constructor for initialization with SysML time event.
		 * 
		 * @param event the SysML time event that specifies the timer
		 */
		public Timer(SysMLTimeEvent event)
		{
			this.event = event;
			DurationMilliseconds initialDelay = DurationMilliseconds.ZERO;
			if (event.absoluteTime.isPresent())
				initialDelay = DurationMilliseconds.between(InstantMilliseconds.now(), event.absoluteTime.get());
			else if (event.relativeTime.isPresent())
				initialDelay = event.relativeTime.get();
			if (event.periodicTime.isPresent())
				timerThreadFuture = context.get().getExecutionThreads().scheduleAtFixedRate(this, initialDelay.value, event.periodicTime.get().value, TimeUnit.MILLISECONDS);
			else
				timerThreadFuture = context.get().getExecutionThreads().schedule(this, initialDelay.value, TimeUnit.MILLISECONDS);
		}

		/**
		 * Runnable {@code run()} operation. Operation is called at timer's thread
		 * execution time. On invocation, it either queues up a {@code SysMLTimeEvent}
		 * to the {@code eventQueue} or, if this is not an asynchronous state machine,
		 * simply invokes the state-machine's {@code onEvent()} event-handling
		 * operation.
		 */
		@Override
		public void run()
		{
			if (eventQueue.isPresent())
				queueEvent(event);
			else
				onEvent(event);
		}
	}

	/**
	 * Name of method to create state on-enter action functions, used by SyMLinJava
	 * tools
	 */
	public static final String createStateEntryActionFunctionsMethodName = "createStateEntryActionFunctions";
	/**
	 * Name of method to create state do action functions, used by SyMLinJava tools
	 */
	public static final String createStateDoActionFunctionsMethodName = "createStateDoActionFunctions";
	/**
	 * Name of method to create state on-exit action functions, used by SyMLinJava
	 * tools
	 */
	public static final String createStateExitActionFunctionsMethodName = "createStateOnExitActionFunctions";
	/**
	 * Name of method to create states, used by SyMLinJava tools
	 */
	public static final String createStatesMethodName = "createStates";
	/**
	 * Name of method to create composite state state machines, used by SyMLinJava
	 * tools
	 */
	public static final String createStateCompositeStateMachinesMethodName = "createStateCompositeStateMachines";
	/**
	 * Name of method to create pseudo-states, used by SyMLinJava tools
	 */
	public static final String createPseudoStatesMethodName = "createPseudoStates";
	/**
	 * Name of method to create initial state, used by SyMLinJava tools
	 */
	public static final String initialStateVariableName = "initialState";
	/**
	 * Name of variable for final state, used by SyMLinJava tools
	 */
	public static final String finalStateVariableName = "finalState";
	/**
	 * Name of method to create effect action functions, used by SyMLinJava tools
	 */
	public static final String createEffectActionFunctionsMethodName = "createEffectActionFunctions";
	/**
	 * Name of method to create effects, used by SyMLinJava tools
	 */
	public static final String createEffectsMethodName = "createEffects";
	/**
	 * Name of method to create guard conditions, used by SyMLinJava tools
	 */
	public static final String createGuardConditionsMethodName = "createGuardConditions";
	/**
	 * Name of method to create guards, used by SyMLinJava tools
	 */
	public static final String createGuardsMethodName = "createGuards";
	/**
	 * Name of method to create transitions, used by SyMLinJava tools
	 */
	public static final String createTransitionsMethodName = "createTransitions";
	/**
	 * Name of method to create requirements, used by SysMLinJava tools
	 */
	public static final String createRequirementsMethodName = "createRequirements";
	/**
	 * Name of method to create dependencies, used by SysMLinJava tools
	 */
	public static final String createDependenciesMethodName = "createDependencies";
	/**
	 * Name of method to create custom metadata, used by SysMLinJava tools
	 */
	public static final String createCustomMetadataMethodName = "createCustomMetadata";
}