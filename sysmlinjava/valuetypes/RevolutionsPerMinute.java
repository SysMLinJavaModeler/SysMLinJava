package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for RPM (revolutions/minute)
 * 
 * @author ModelerOne
 *
 */
public class RevolutionsPerMinute extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constructor
	 * 
	 * @param value double value for initial value
	 */
	public RevolutionsPerMinute(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.RevolutionsPerMinute;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RevolutionsPerMinute [value=");
		builder.append(value);
		builder.append(", units=");
		builder.append(units);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

	public static RevolutionsPerMinute valueOf(DistanceMeters wheelDiameter, VelocityMetersPerSecondRadians atVelocity)
	{
		double revolutionsPerMeter = 1 / (wheelDiameter.value * Math.PI);
		double metersPerMinute = atVelocity.value * 60;
		return new RevolutionsPerMinute(revolutionsPerMeter * metersPerMinute);
	}
}
