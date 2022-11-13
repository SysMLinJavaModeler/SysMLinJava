package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for electrical resistance in ohms
 * 
 * @author ModelerOne
 */
public class ResistanceOhms extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = 140922311342589973L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public ResistanceOhms(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied resistance whose value is to used as initial value of this copy
	 */
	public ResistanceOhms(ResistanceOhms copied)
	{
		super(copied.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Ohm;
	}
}
