package sysmlinjava.valuetypes;

import java.io.Serializable;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for jerk (acceleration/second) in
 * meters/second/second/second
 * 
 * @author ModelerOne
 *
 */
public class JerkMetersPerSecondCubed extends RReal implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -1255809766693169266L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public JerkMetersPerSecondCubed(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.MetersPerSecondCubed;
	}
}
