package sysmlinjava.analysis.statetransitionstransmitters;

import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;
import sysmlinjava.analysis.statetransitions.StateTransitionStrings;
import sysmlinjava.analysis.statetransitions.StateTransitionTablesTransmitter;
import sysmlinjava.analysis.timingdiagrams.StateTransitionTiming;
import sysmlinjava.analysis.timingdiagrams.TimingDiagramDefinition;
import sysmlinjava.analysis.timingdiagrams.TimingDiagramsTransmitter;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.statemachine.SysMLInitialState;
import sysmlinjava.statemachine.InitialTransition;
import sysmlinjava.statemachine.SysMLEffect;
import sysmlinjava.statemachine.SysMLGuard;
import sysmlinjava.statemachine.SysMLStateMachine;
import sysmlinjava.statemachine.SysMLStateMachine.TransitionsUtility;
import sysmlinjava.statemachine.SysMLTransition;
import sysmlinjava.statemachine.SysMLVertex;

/**
 * SysMLinJava utility class for the transmission of state transitions data to a
 * state transitions display. The utility includes two types of data
 * transmitters, i.e.
 * <ul>
 * <li>a transmitter of state transition table data that includes current state,
 * triggering event, guard, effect, and next state</li>
 * <li>a transmitter of state transition timing data that includes the
 * <i>from</i> and <i>to</i> states at time of transition</li>
 * </ul>
 * Either or both of the utilities are constructed/initialized and assigned to
 * the {@code SysMLStateMachine}'s {@code transitionsUtility} variable. This
 * assignment is made in the {@code createTransitionsUtility()} method of the
 * specialized state machine class. An example of use of one of the two
 * transition utilities follows.
 * 
 * <pre>
 * {@code
 * &#64;Override
 * protected void createTransitionsUtility()
 * {
 * 	transitionsUtility = Optional.of(new StateTransitionsTransmitters(Optional.of(new StateTransitionTablesTransmitter(StateTransitionsDisplay.udpPort)), Optional.empty()));
 * }
 * }
 * </pre>
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLStateMachine#transitionsUtility
 */
public class StateTransitionsTransmitters implements TransitionsUtility
{
	/**
	 * A transmitter of state transition table text to UDP port. For possible
	 * display and/or storage of this state machine's state transitions occuring
	 * during model execution. The transmitter is enabled by calling the
	 * {@code enableStateTransitionStringsTransmission(int udpPort)} operation.
	 */
	private Optional<StateTransitionTablesTransmitter> stateTransitionTablesTransmitter;
	/**
	 * A transmitter of state timing diagrams data to UDP port. For possible display
	 * and/or storage of this state machine's state transition timings occuring
	 * during model execution. The transmitter is enabled by calling the
	 * {@code enableStateTransitionTimingsTransmission(int udpPort)} operation.
	 */
	private Optional<TimingDiagramsTransmitter> stateTimingDiagramsTransmitter;
	/**
	 * Indication if the strings that represent the state transitions are to be
	 * logged to the console. If true, strings are logged to console, otherwise they
	 * are not.
	 */
	private boolean logToConsole;

	/**
	 * Constructor - initial values
	 * 
	 * @param stateTransitionTablesTransmitter optional transmitter of the state
	 *                                         transition tables
	 * @param stateTimingDiagramsTransmitter   optional transmitter of the state
	 *                                         timing diagrams
	 * @param logToConsole                     strings that represent the state
	 *                                         transitions wll be logged to the
	 *                                         console if true, will not be
	 *                                         otherwise
	 */
	public StateTransitionsTransmitters(Optional<StateTransitionTablesTransmitter> stateTransitionTablesTransmitter, Optional<TimingDiagramsTransmitter> stateTimingDiagramsTransmitter, boolean logToConsole)
	{
		super();
		this.stateTransitionTablesTransmitter = stateTransitionTablesTransmitter;
		this.stateTimingDiagramsTransmitter = stateTimingDiagramsTransmitter;
		this.logToConsole = logToConsole;
	}

	@Override
	public void perform(Optional<? extends SysMLBlock> contextBlock, SysMLStateMachine stateMachine, SysMLVertex currentState, Optional<SysMLEvent> currentEvent, SysMLTransition transition, Optional<? extends SysMLGuard> guard,
		Optional<? extends SysMLEffect> effect, SysMLVertex nextState, Logger logger)
	{
		StateTransitionStrings strings = new StateTransitionStrings(contextBlock, currentState, currentEvent, transition, transition.guard, transition.effect, nextState);
		if (logToConsole)
			logger.info(strings.logString());
		if (stateTransitionTablesTransmitter.isPresent())
			stateTransitionTablesTransmitter.get().transmit(strings);
		if (stateTimingDiagramsTransmitter.isPresent())
			stateTimingDiagramsTransmitter.get().transmitStateTime(new StateTransitionTiming(stateMachine.identityString(), currentState.identityString(), nextState.identityString(), Instant.now()));
	}

	@Override
	public void perform(Optional<? extends SysMLBlock> contextBlock, SysMLStateMachine stateMachine, SysMLInitialState initialState, InitialTransition initialTransition, Optional<? extends SysMLEffect> effect, SysMLVertex nextState,
		Logger logger)
	{
		StateTransitionStrings strings = new StateTransitionStrings(contextBlock, initialState, initialTransition, initialTransition.effect, nextState);
		if (logToConsole)
			logger.info(strings.logString());
		if (stateTransitionTablesTransmitter.isPresent())
			stateTransitionTablesTransmitter.get().transmit(strings);
		if (stateTimingDiagramsTransmitter.isPresent())
			stateTimingDiagramsTransmitter.get().transmitStateTime(new StateTransitionTiming(stateMachine.identityString(), initialState.identityString(), nextState.identityString(), Instant.now()));
	}

	/**
	 * Transmits the specified timing diagram definition to the timing diagram
	 * display, thereby establishing a timing diagram to subsequently receive timing
	 * data for display.
	 * 
	 * @param timingDiagramDefinition definition of the timing diagram to
	 *                                subsequently receive timing data for display.
	 */
	public void transmit(TimingDiagramDefinition timingDiagramDefinition)
	{
		stateTimingDiagramsTransmitter.get().transmitTimingDiagram(timingDiagramDefinition);
	}

	/**
	 * Stops the transmission of state transition tables and/or state timing
	 * diagrams data to the applicable displays. Attempts to transmit to either of
	 * these displays via the transmitters will fail after this call to stop.
	 */
	public void stop()
	{
		if (stateTransitionTablesTransmitter.isPresent())
			stateTransitionTablesTransmitter.get().stop();
		if (stateTimingDiagramsTransmitter.isPresent())
			stateTimingDiagramsTransmitter.get().stop();
	}

}
