package sysmlinjava.metadata;

import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava representation of the SysML risk level used for risk metadata.
 */
public final class SysMLRiskLevel extends SysMLAnything
{
	/**
	 * Level of impact risk
	 */
	public SysMLLevel impact;
	/**
	 * Level of occurrence risk
	 */
	public SysMLLevel occurrence;

	/**
	 * Constructor for initial values as {@code SysMLLevel}
	 * 
	 * @param impact     Level of impact risk
	 * @param occurrence Level of occurrence risk
	 */
	public SysMLRiskLevel(SysMLLevel impact, SysMLLevel occurrence)
	{
		super();
		this.impact = impact;
		this.occurrence = occurrence;
	}

	/**
	 * Constructor for initial values as doubles
	 * 
	 * @param impact     percent probability of impact risk
	 * @param occurrence percent probability of occurrence risk
	 */
	public SysMLRiskLevel(double impact, double occurrence)
	{
		super();
		this.impact = new SysMLLevel(impact);
		this.occurrence = new SysMLLevel(occurrence);
	}
}