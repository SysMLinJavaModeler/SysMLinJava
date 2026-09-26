
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

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import sysmlinjava.events.SysMLEvent;

/**
 * SysMLinJava-specific interface for model elements that exhibit state-based
 * behavior, i.e. a part, port, or other element whose behavior is defined by an
 * asychronous state machine. Implementation of this interface enables more
 * precise modeling of the element for model execution. Implementing elements
 * should implement the interface by including instances of an extended
 * {@code SysMLStateMachine} and a {@code ScheduledThreadPoolExecutor} in their
 * class as done by the {@code SysMLPart} and the {@code SysMLPort}.
 * 
 * @author ModelerOne
 * @see sysmlinjava.items.SysMLItem#stateMachine
 * @see sysmlinjava.parts.SysMLPart#concurrentExecutionThreads
 * @see sysmlinjava.analysis.ParametricAnalysisCase#concurrentExecutionThreads
 */
public interface StateBehaviorContext
{
	/**
	 * Starts the event-driven's state machine behavior, if a state machine is
	 * present. The operation submits an {@code InitialEvent} to the state machine's
	 * event queue to thereby start its execution thread. The state machine will
	 * execute in accordance with the states and transitions that are defined in the
	 * class that extends the {@code SysMLStateMachine}.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @see sysmlinjava.parts.SysMLPart#start()
	 */
	public void start();

	/**
	 * Accepts and queues a specified event into the state machine's event queue.
	 * Whereas the event queue is a thread safe object, this method can be called to
	 * inject an event into the state machine from any thread.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param event event to be queued to the state machine
	 * @see sysmlinjava.parts.SysMLPart#acceptEvent(SysMLEvent)
	 */
	public void acceptEvent(SysMLEvent event);

	/**
	 * Stops the event-driven's state machine-based behavior, if a state machine is
	 * present in the event-driven. The operation simply stops the state machine
	 * execution. Note if this event-driven's state machine is asynchronous, i.e.
	 * executes in its own thread, then this operation imposes a "hard stop" where
	 * the thread is canceled regardless of what state it's in. If the part or
	 * port's state machine is synchronous, this operation can only queue up a
	 * {@code FinalEvent} to the event queue, on the assumption that the state
	 * machine is capable of handling the {@code FinalEvent} at the point in which
	 * the {@code stop()} method is invoked. Therefore, if a event-driven is
	 * synchronous and this stop method is used, it's state machine should be
	 * modeled to handle the {@code FinalEvent} accordingly.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @see sysmlinjava.parts.SysMLPart#stop()
	 */
	public void stop();

	/**
	 * Delays the calling thread (sleeps) for the specified seconds of time.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param seconds time to sleep in seconds. Use up to 3 decimal places for
	 *                fractions of a second, i.e. the delay is capable of the
	 *                milliseconds precision provided by java's
	 *                {@code Thread.sleep(<millis>)} operation.
	 * @see sysmlinjava.parts.SysMLPart#delay(double)
	 */
	public void delay(double seconds);

	/**
	 * Overridable operation for the identity String for this event-driven for use
	 * in logging and other display outputs.
	 * 
	 * @return the identity string for this event-driven
	 * @see sysmlinjava.parts.SysMLPart#getIdentityString()
	 */
	public String getIdentityString();

	/**
	 * Returns the event-driven's concurrent execution thread pool for executing
	 * multiple threads of model execution.
	 * 
	 * @return the event-driven's thread pool
	 * @see sysmlinjava.parts.SysMLPart#getExecutionThreads()
	 */
	public ScheduledThreadPoolExecutor getExecutionThreads();

	/**
	 * Returns the event-driven's state machine, if present.
	 * 
	 * @return the event-driven's optional state machine
	 * @see sysmlinjava.parts.SysMLPart#getStateMachine()
	 */
	public Optional<? extends SysMLStateMachine> getStateMachine();
}
