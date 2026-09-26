package sysmlinjava.metadata;

import sysmlinjava.attributetypes.ProbabilityPercent;

/**
 * SysMLinJava representation of the SysML level used primarily for risk levels
 */
public final class SysMLLevel extends ProbabilityPercent
{
	private static final long serialVersionUID = 8848447891578547775L;

	/**
	 * Constroctor for intial value
	 * 
	 * @param value initial value
	 */
	public SysMLLevel(double value)
	{
		super(value);
	}
}