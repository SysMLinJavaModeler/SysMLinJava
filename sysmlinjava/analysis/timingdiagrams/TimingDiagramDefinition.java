package sysmlinjava.analysis.timingdiagrams;

import java.io.Serializable;

/**
 * Definition of the timing diagram, i.e. its states axis and its time axis, and
 * it ID
 * 
 * @author ModelerOne
 *
 */
public class TimingDiagramDefinition implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8945635974734237468L;

	/**
	 * ID of the diagram
	 */
	public String timingDiagramID;
	/**
	 * Axis (vertical) for the states
	 */
	public StatesAxis statesAxis;
	/**
	 * Axis (horizontal) for the time
	 */
	public TimeAxis timeAxis;

	/**
	 * Constructor
	 * 
	 * @param timingDiagramID diagram ID
	 * @param statesAxis      axis for states
	 * @param timeAxis        axis for times
	 */
	public TimingDiagramDefinition(String timingDiagramID, StatesAxis statesAxis, TimeAxis timeAxis)
	{
		super();
		this.timingDiagramID = timingDiagramID;
		this.statesAxis = statesAxis;
		this.timeAxis = timeAxis;
	}

	/**
	 * Log-type representation of the diagram definition
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[TD][timingDiagramID=%s, statesAxis=%s, timeAxis=%s]", timingDiagramID, statesAxis.toString(), timeAxis.toString());
	}

	@Override
	public String toString()
	{
		return String.format("TimingDiagramDefinition [timingDiagramID=%s, statesAxis=%s, timeAxis=%s]", timingDiagramID, statesAxis.toString(), timeAxis.toString());
	}
}
