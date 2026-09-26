/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.statetransitionstables;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjava.states.InitialEvent;
import sysmlinjava.states.SysMLEffect;
import sysmlinjava.states.SysMLGuard;
import sysmlinjava.states.SysMLInitialState;
import sysmlinjava.states.SysMLTransition;
import sysmlinjava.states.SysMLVertex;

/**
 * A set of strings that specify the names or text of elements of a state
 * transition that occured in a state machine during a model execution. These
 * strings constitute a row in a state transition table. The transition strings
 * can be formatted in a number of ways so as to provide a state transition
 * specification for logger entries, string lists, comma-separated value (CSV)
 * lines of text, and as table rows in HTML.
 * 
 * @author ModelerOne
 *
 */
public class StateTransitionStrings implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = -5354564772508604300L;

	/**
	 * Name of the statemachines context part or port (part or port for which this statemachine
	 * exists
	 */
	public String contextString;
	/**
	 * Millisecond time of this state transition's occurance
	 */
	public String timeMillisString;
	/**
	 * Name of the current (transitioning from) state of the transition
	 */
	public String currentStateString;
	/**
	 * Name of the current (invoking) event that triggered the transition
	 */
	public String currentEventString;
	/**
	 * Name of the the transition
	 */
	public String transitionString;
	/**
	 * The condition logic, if any, that enabled the transition
	 */
	public String guardString;
	/**
	 * The effect, (code logic) if any, of the transition
	 */
	public String effectString;
	/**
	 * Name of the next (transitioning to) state of the transition
	 */
	public String nextStateString;

	/**
	 * String value for the state transition table cell when there is no data for
	 * that cell
	 */
	public final static String none = "<none>";

	/**
	 * Constructor to build strings that describe the initial transition from the
	 * initial state of the state machine
	 * 
	 * @param context      context part or port of the state machine
	 * @param initialState      initial state of the state machine
	 * @param initialTransition initial transition from the initial state
	 * @param effect            effect of the transition
	 * @param nextState         next state after the transition
	 */
	public StateTransitionStrings(Optional<? extends StateBehaviorContext> context, SysMLInitialState initialState, SysMLTransition initialTransition, Optional<SysMLEffect> effect, SysMLVertex nextState)
	{
		contextString = context.isPresent() ? context.get().getIdentityString() : none;
		timeMillisString = String.valueOf(LocalTime.now().toNanoOfDay() / 1_000_000);
		currentStateString = initialState.identityString();
		currentEventString = new InitialEvent().identityString();
		transitionString = initialTransition.identityString();
		guardString = none;
		effectString = effect.isPresent() ? effect.get().identityString() : none;
		nextStateString = nextState.identityString();
	}

	/**
	 * Constructor to build strings that describe the transition from actual state
	 * machine objects involved in the transition.
	 * 
	 * @param context context part or port of the state machine
	 * @param currentState current state of the state machine
	 * @param currentEvent current event received by the state machine
	 * @param transition   transition taken in response to the event
	 * @param guard        guard on the transition
	 * @param effect       effect of the transition
	 * @param nextState    next state after the transition
	 */
	public StateTransitionStrings(Optional<? extends StateBehaviorContext> context, SysMLVertex currentState, Optional<SysMLEvent> currentEvent, SysMLTransition transition, Optional<SysMLGuard> guard, Optional<SysMLEffect> effect, SysMLVertex nextState)
	{
		contextString = context.isPresent() ? context.get().getIdentityString() : none;
		timeMillisString = String.valueOf(LocalTime.now().toNanoOfDay() / 1_000_000);
		currentStateString = currentState.identityString();
		currentEventString = currentEvent.isPresent() ? currentEvent.get().identityString() : none;
		transitionString = transition.identityString();
		guardString = guard.isPresent() ? guard.get().identityString() : none;
		effectString = effect.isPresent() ? effect.get().identityString() : none;
		nextStateString = nextState.identityString();
	}

	/**
	 * Constructor to use provided strings for strings that describe the transition
	 * 
	 * @param contextString string representation of the context part or port of the
	 *                           state machine
	 * @param timeMillisString   string representation of the time of the current
	 *                           event
	 * @param currentStateString string representation of the current state
	 * @param currentEventString string representation of the current event received
	 *                           by the state machine
	 * @param transitionString   string representation of the transition that took
	 *                           place
	 * @param guardString        string representation of the guard on the
	 *                           transition
	 * @param effectString       string representation of the effect of the
	 *                           transition
	 * @param nextStateString    string representation of the next state after
	 *                           transition
	 */
	public StateTransitionStrings(String contextString, String timeMillisString, String currentStateString, String currentEventString, String transitionString, String guardString, String effectString, String nextStateString)
	{
		super();
		this.contextString = contextString;
		this.timeMillisString = timeMillisString;
		this.currentStateString = currentStateString;
		this.currentEventString = currentEventString;
		this.transitionString = transitionString;
		this.guardString = guardString;
		this.effectString = effectString;
		this.nextStateString = nextStateString;
	}

	/**
	 * Return the strings as a list
	 * 
	 * @return List of the strings
	 */
	public List<String> asList()
	{
		return Arrays.asList(contextString, timeMillisString, currentStateString, currentEventString, transitionString, guardString, effectString, nextStateString);
	}

	/**
	 * Return a comma-separated string of the strings, optionally including the
	 * context part or port string
	 * 
	 * @param includecontext true if string should include the context part or port
	 *                            string, false otherwise
	 * @return CSV string
	 */
	public String asCSVString(boolean includecontext)
	{
		StringJoiner joiner = new StringJoiner(",");
		joiner.add(timeMillisString);
		if (includecontext)
			joiner.add(contextString);
		joiner.add(currentStateString);
		joiner.add(currentEventString);
		joiner.add(transitionString);
		joiner.add(guardString);
		joiner.add(effectString);
		joiner.add(nextStateString);
		return joiner.toString();
	}

	/**
	 * Format string for the HTML tag for the cells of the state transition table
	 */
	final String cellFormat = "<td>%s</td>";

	/**
	 * Return a string suitable for a table row in an HTML table.
	 * <p>
	 * <b>Note:</b>Strings designated as empty by values of {@code <none>} are
	 * transposed for HTML compatibility, so the use of {@code <none>} must conform
	 * to this format to be compatible with this method.
	 * 
	 * @param includecontext true if row string should include the context part
	 *                            or port string, false otherwise
	 * @return HTML table row string
	 */
	public String asHTMLTableRowString(boolean includecontext)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<tr>");
		builder.append(String.format(cellFormat, timeMillisString));
		if (includecontext)
			builder.append(String.format(cellFormat, contextString));
		builder.append(String.format(cellFormat, currentStateString));
		builder.append(String.format(cellFormat, currentEventString.replace("<none>", "&lt;none&gt;")));
		builder.append(String.format(cellFormat, transitionString));
		builder.append(String.format(cellFormat, guardString.replace("<none>", "&lt;none&gt;")));
		builder.append(String.format(cellFormat, effectString.replace("<none>", "&lt;none&gt;")));
		builder.append(String.format(cellFormat, nextStateString));
		builder.append("</tr>\r\n");
		return builder.toString();
	}

	/**
	 * Return a string for a log entry
	 * 
	 * @return String formatted for a log entry
	 */
	public String logString()
	{
		return String.format("[STM]context: %s, timeMillis: %s, currentState: %s, currentEvent: %s, transition: %s, guard: %s, effect: %s, nextState: %s", contextString, timeMillisString, currentStateString, currentEventString,
			transitionString, guardString, effectString, nextStateString);
	}

	@Override
	public String toString()
	{
		return String.format("StateTransitionStrings [contextString=%s, timeMillisString: %s, currentStateString=%s, currentEventString=%s, transitionString=%s, triggerString=%s, guardString=%s, nextStateString=%s]",
			contextString, timeMillisString, currentStateString, currentEventString, transitionString, guardString, effectString, nextStateString);
	}

	/**
	 * Comparator for state transition strings to sort chronologically
	 * 
	 * @author ModelerOne
	 *
	 */
	public static class StateTransitionsListComparator implements Comparator<StateTransitionStrings>
	{
		/**
		 * Constructor - default, no initializations
		 */
		public StateTransitionsListComparator()
		{
			super();
		}

		@Override
		public int compare(StateTransitionStrings left, StateTransitionStrings right)
		{
			double diff = Double.valueOf(left.timeMillisString) - Double.valueOf(right.timeMillisString);
			return diff < 0 ? -1 : (diff > 0 ? 1 : 0);
		}
	}
}
