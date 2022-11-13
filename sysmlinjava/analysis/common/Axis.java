package sysmlinjava.analysis.common;

import java.io.Serializable;
import java.util.Optional;

/**
 * Axis definition for a numerical axis in a chart display, e.g. line, bar,
 * timing, etc.
 * 
 * @author ModelerOne
 *
 */
public class Axis implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8854669832371302383L;

	/**
	 * Title of the axis, not including the parenthetical units portion
	 */
	public String title;
	/**
	 * Parenthetical units portion of the title
	 */
	public String units;
	/**
	 * Minimum value on the axis.
	 */
	public double minTicValue;
	/**
	 * Maimum value on the axis. Chart will automatically determine max value from
	 * data if value is not present
	 */
	public double maxTicValue;
	/**
	 * Whether chart will automatically determine range of values on the axis from
	 * data. Note this boolean is used (versus making the {@code min/maxTicValue}s
	 * optional) to enable serialization, i.e. {@code Optional} is not serializable.
	 */
	public boolean autoRange;
	/**
	 * Increment between major (numbered) tics on the axis
	 */
	public double majorTicValue;
	/**
	 * Number of tics between the major tics.
	 */
	public int minorTicCount;

	/**
	 * Constructor
	 * 
	 * @param title         Title of the axis, not including the parenthetical units
	 *                      portion
	 * @param units         Parenthetical units portion of the title
	 * @param minTicValue   Optional minimum value on the axis. Chart will
	 *                      automatically determine min value from data if value is
	 *                      not present.
	 * @param maxTicValue   Optional maximum value on the axis. Chart will
	 *                      automatically determine max value from data if value is
	 *                      not present.
	 * @param majorTicValue Increment between major (numbered) tics on the axis
	 * @param minorTicCount Number of tics between the major tics.
	 */
	public Axis(String title, String units, Optional<Double> minTicValue, Optional<Double> maxTicValue, double majorTicValue, int minorTicCount)
	{
		super();
		this.title = title;
		this.units = units;
		this.autoRange = false;
		if (minTicValue.isPresent())
			this.minTicValue = minTicValue.get();
		else
		{
			this.minTicValue = 0;
			this.autoRange = true;
		}
		if (maxTicValue.isPresent())
			this.maxTicValue = maxTicValue.get();
		else
		{
			this.maxTicValue = 100;
			this.autoRange = true;
		}
		this.majorTicValue = majorTicValue;
		this.minorTicCount = minorTicCount;
	}

	@Override
	public String toString()
	{
		return String.format("Axis [title=%s, units=%s, minValue=%s, maxValue=%s, majorTicValue=%s, minorTicCount=%s]", title, units, minTicValue, maxTicValue, majorTicValue, minorTicCount);
	}

}