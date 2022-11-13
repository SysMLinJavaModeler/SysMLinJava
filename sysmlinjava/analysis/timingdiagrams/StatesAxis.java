package sysmlinjava.analysis.timingdiagrams;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

/**
 * Axis for the states to be displayed by a timeing diagram
 * 
 * @author ModelerOne
 *
 */
public class StatesAxis implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8854669832371302383L;

	/**
	 * List of names of the states on the axis
	 */
	public List<String> states;

	/**
	 * Constructor
	 * 
	 * @param states list of names of the states on the axis
	 */
	public StatesAxis(List<String> states)
	{
		super();
		this.states = states;
	}

	@Override
	public String toString()
	{
		StringJoiner joiner = new StringJoiner(", ");
		states.forEach(state -> joiner.add(state));
		return String.format("StatesAxis [states=%s]", joiner.toString());
	}
}