package sysmlinjava.valuetypes;

import static java.lang.Math.*;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for latitude in radians
 * 
 * @author ModelerOne
 *
 */
public class LatitudeRadians  extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant latitude radians for north pole
	 */
	public static final LatitudeRadians northPole = new LatitudeRadians(PI / 2);
	/**
	 * Constant latitude radians for south pole
	 */
	public static final LatitudeRadians southPole = new LatitudeRadians(-PI / 2);
	/**
	 * Constant latitude radians for equator
	 */
	public static final LatitudeRadians equator = new LatitudeRadians(0);

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeRadians(double value)
	{
		super(value);
	}

	/**
	 * Constructor for Real initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeRadians(RReal value)
	{
		this(value.value);
	}

	/**
	 * Constructor for LatitudeDegrees initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeRadians(LatitudeDegrees value)
	{
		this(toRadians(value.value));
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public LatitudeRadians(LatitudeRadians copied)
	{
		super(copied);
	}

	/**
	 * Constructor for DirectionDegrees initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeRadians(DirectionDegrees value)
	{
		this(toRadians(value.value));
	}

	/**
	 * Constructor for DirectionRadians initial value
	 * 
	 * @param value initial latitude
	 */
	public LatitudeRadians(DirectionRadians value)
	{
		this(value.value);
	}

	@Override
	public LatitudeRadians added(RReal real)
	{
		return new LatitudeRadians(value + real.value);
	}

	@Override
	public LatitudeRadians subtracted(RReal real)
	{
		return new LatitudeRadians(value - real.value);
	}

	@Override
	public LatitudeRadians multipliedBy(RReal real)
	{
		return new LatitudeRadians(value * real.value);
	}

	@Override
	public LatitudeRadians dividedBy(RReal real)
	{
		return new LatitudeRadians(value / real.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Radians;
	}

	@Override
	public java.lang.String toString()
	{
		return java.lang.String.format("LatitudeRadians [value=%s[%s], units=%s]", value, Math.toDegrees(value), units.symbol);
	}
}
