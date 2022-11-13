package sysmlinjava.analysis.timingdiagrams;

import java.io.Serializable;

/**
 * Definition of a time axis of a 2D chart
 * 
 * @author ModelerOne
 *
 */
public class TimeAxis implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8854669832371302383L;

	/**
	 * Maximum time of the axis in seconds
	 */
	public double maxTimeSeconds;
	/**
	 * Seconds between major (numbered) tics on the axis
	 */
	public double majorIncrementSeconds;
	/**
	 * Number of tic increments between the major tics
	 */
	public int minorIncrementCount;

	/**
	 * Constructor
	 * 
	 * @param maxTimeSeconds        maximum time of the axis in seconds
	 * @param majorIncrementSeconds seconds between major (numbered) tics on the
	 *                              axis
	 * @param minorIncrementCount   number of tic increments between the major tics
	 */
	public TimeAxis(double maxTimeSeconds, double majorIncrementSeconds, int minorIncrementCount)
	{
		super();
		this.maxTimeSeconds = maxTimeSeconds;
		this.majorIncrementSeconds = majorIncrementSeconds;
		this.minorIncrementCount = minorIncrementCount;
	}

	@Override
	public String toString()
	{
		return String.format("TimeAxis [maxTimeSeconds=%s, majorIncrementSeconds=%s, minorIncrementCount=%s]", maxTimeSeconds, majorIncrementSeconds, minorIncrementCount);
	}
}