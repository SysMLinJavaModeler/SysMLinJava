package sysmlinjava.analysis.timingdiagrams;

import java.io.Serializable;
import java.time.Instant;

/**
 * Definition of a state transition to be displayed on a timing diagram
 * 
 * @author ModelerOne
 *
 */
public class StateTransitionTiming implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -7567118199087274202L;

	/**
	 * ID of the timing diagram to display the transition
	 */
	public String timingDiagramID;
	/**
	 * Name of the state transitioned from
	 */
	public String fromState;
	/**
	 * Name of the state transitioned to
	 */
	public String toState;
	/**
	 * Time of the transition
	 */
	public Instant time;

	/**
	 * Constructor
	 * 
	 * @param timingDiagramID ID of the timing diagram to display the transition
	 * @param fromState       name of the state transitioned from
	 * @param toState         name of the state transitioned to
	 * @param time            time of the transition
	 */
	public StateTransitionTiming(String timingDiagramID, String fromState, String toState, Instant time)
	{
		super();
		this.timingDiagramID = timingDiagramID;
		this.fromState = fromState;
		this.toState = toState;
		this.time = time;
	}

	/**
	 * Determines whether this data if valid for the diagram
	 * 
	 * @param diagram definition of the diagram
	 * @return if valid true, false otherwise
	 */
	public boolean isValidFor(TimingDiagramDefinition diagram)
	{
		boolean result = true;
		return result;
	}

	/**
	 * Log-type representation of the transition timing data
	 * 
	 * @return log-type string
	 * 
	 */
	public String toLogString()
	{
		return String.format("[TD] timingDiagramID=%s, fromState=%s, toState=%s, time=%s]", timingDiagramID, fromState, toState, time);
	}

	@Override
	public String toString()
	{
		return String.format("StateTransitionTiming [timingDiagramID=%s, fromState=%s, toState=%s, time=%s]", timingDiagramID, fromState, toState, time);
	}
}