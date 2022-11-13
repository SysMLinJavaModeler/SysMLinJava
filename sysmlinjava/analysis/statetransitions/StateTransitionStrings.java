package sysmlinjava.analysis.statetransitions;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import sysmlinjava.analysis.statetransitions.StateTransitionStrings;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.statemachine.InitialEvent;
import sysmlinjava.statemachine.SysMLInitialState;
import sysmlinjava.statemachine.SysMLEffect;
import sysmlinjava.statemachine.SysMLGuard;
import sysmlinjava.statemachine.SysMLTransition;
import sysmlinjava.statemachine.SysMLVertex;

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
	 * Name of the statemachines context block (block for which this statemachine
	 * exists
	 */
	public String contextBlockString;
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
	 * @param contextBlock      context block of the state machine
	 * @param initialState      initial state of the state machine
	 * @param initialTransition initial transition from the initial state
	 * @param effect            effect of the transition
	 * @param nextState         next state after the transition
	 */
	public StateTransitionStrings(Optional<? extends SysMLBlock> contextBlock, SysMLInitialState initialState, SysMLTransition initialTransition, Optional<? extends SysMLEffect> effect, SysMLVertex nextState)
	{
		contextBlockString = contextBlock.isPresent() ? contextBlock.get().identityString() : none;
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
	 * @param contextBlock context block of the state machine
	 * @param currentState current state of the state machine
	 * @param currentEvent current event received by the state machine
	 * @param transition   transition taken in response to the event
	 * @param guard        guard on the transition
	 * @param effect       effect of the transition
	 * @param nextState    next state after the transition
	 */
	public StateTransitionStrings(Optional<? extends SysMLBlock> contextBlock, SysMLVertex currentState, Optional<SysMLEvent> currentEvent, SysMLTransition transition, Optional<? extends SysMLGuard> guard, Optional<? extends SysMLEffect> effect, SysMLVertex nextState)
	{
		contextBlockString = contextBlock.isPresent() ? contextBlock.get().identityString() : none;
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
	 * @param contextBlockString string representation of the context block of the
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
	public StateTransitionStrings(String contextBlockString, String timeMillisString, String currentStateString, String currentEventString, String transitionString, String guardString, String effectString, String nextStateString)
	{
		super();
		this.contextBlockString = contextBlockString;
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
		return Arrays.asList(contextBlockString, timeMillisString, currentStateString, currentEventString, transitionString, guardString, effectString, nextStateString);
	}

	/**
	 * Return a comma-separated string of the strings, optionally including the
	 * context block string
	 * 
	 * @param includeContextBlock true if string should include the context block
	 *                            string, false otherwise
	 * @return CSV string
	 */
	public String asCSVString(boolean includeContextBlock)
	{
		StringJoiner joiner = new StringJoiner(",");
		joiner.add(timeMillisString);
		if (includeContextBlock)
			joiner.add(contextBlockString);
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
	 * @param includeContextBlock true if row string should include the context
	 *                            block string, false otherwise
	 * @return HTML table row string
	 */
	public String asHTMLTableRowString(boolean includeContextBlock)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<tr>");
		builder.append(String.format(cellFormat, timeMillisString));
		if (includeContextBlock)
			builder.append(String.format(cellFormat, contextBlockString));
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
		return String.format("[STM]contextBlock: %s, timeMillis: %s, currentState: %s, currentEvent: %s, transition: %s, guard: %s, effect: %s, nextState: %s", contextBlockString, timeMillisString, currentStateString, currentEventString,
			transitionString, guardString, effectString, nextStateString);
	}

	@Override
	public String toString()
	{
		return String.format("StateTransitionStrings [contextBlockString=%s, timeMillisString: %s, currentStateString=%s, currentEventString=%s, transitionString=%s, triggerString=%s, guardString=%s, nextStateString=%s]",
			contextBlockString, timeMillisString, currentStateString, currentEventString, transitionString, guardString, effectString, nextStateString);
	}

	/**
	 * Comparator for state transition strings to sort chronologically
	 * 
	 * @author ModelerOne
	 *
	 */
	public static class StateTransitionsListComparator implements Comparator<StateTransitionStrings>
	{
		@Override
		public int compare(StateTransitionStrings left, StateTransitionStrings right)
		{
			double diff = Double.valueOf(left.timeMillisString) - Double.valueOf(right.timeMillisString);
			return diff < 0 ? -1 : (diff > 0 ? 1 : 0);
		}
	}
}
