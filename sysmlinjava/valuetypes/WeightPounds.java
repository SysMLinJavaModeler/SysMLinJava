package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for weight in pounds
 * 
 * @author ModelerOne
 *
 */
public class WeightPounds  extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011577283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value to be used for this initial value
	 */
	public WeightPounds(double value)
	{
		super(value);
	}

	/**
	 * Constructor
	 * 
	 * @param value RReal value to be used for this initial value
	 */
	public WeightPounds(RReal value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Pounds;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("WeightPounds [value=");
		builder.append(value);
		builder.append(", units=");
		builder.append(units);
		builder.append("]");
		return builder.toString();
	}
}
