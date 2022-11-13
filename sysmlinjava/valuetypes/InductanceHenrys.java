package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for electrical inductance in henrys
 * 
 * @author ModelerOne
 */
public class InductanceHenrys extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = 7176394605719972346L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public InductanceHenrys(double value)
	{
		super(value);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copyOf inductance whose value is to used as initial value of this copy
	 */
	public InductanceHenrys(InductanceHenrys copyOf)
	{
		super(copyOf.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Henry;
	}
}
