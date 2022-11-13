package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for movement of a frontal area (e.g. vehicle surface)
 * through a space in square-meters-kilometers/hour.
 * 
 * @author ModelerOne
 *
 */
public class FrontalArealSpeed extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Attribute for the speed of the frontal area
	 */
	@Attribute
	public SpeedKilometersPerHour speed;

	/**
	 * Constructor - area and speed
	 * 
	 * @param area  area to be used for initial value
	 * @param speed speed to be used for initial value
	 */
	public FrontalArealSpeed(AreaMetersSquare area, SpeedKilometersPerHour speed)
	{
		super(area.value);
		this.speed = new SpeedKilometersPerHour(speed.value);
	}

	/**
	 * Constructor - default zero area and speed
	 */
	public FrontalArealSpeed()
	{
		super(0);
		this.speed = new SpeedKilometersPerHour(0);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.SquareMeter;
	}

	@Override
	public String toString()
	{
		return String.format("AreaFrontalSpeed [value=%s, units=%s, name=%s, id=%s, speed=%s]", value, units, name, id, speed);
	}

}