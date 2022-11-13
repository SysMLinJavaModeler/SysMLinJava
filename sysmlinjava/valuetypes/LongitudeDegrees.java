package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for longitude in degrees
 * 
 * @author ModelerOne
 *
 */
public class LongitudeDegrees extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant value for the earth's circumference in km
	 */
	public static final double earthCircumferenceKilometers = 40_040;
	/**
	 * Constant value for the earth's circumference in miles
	 */
	public static final double earthCircumferenceMiles = 24_880;
	/**
	 * Constant value for the number of km per radian at the equator
	 */
	public static final double kilometersPerRadianAtEquator = earthCircumferenceKilometers / (2 * Math.PI);
	/**
	 * Constant value for the number of km per degree at the equator
	 */
	public static final double kilometersPerDegreeAtEquator = earthCircumferenceKilometers / 360;
	/**
	 * Constant value for the number of miles per radian at the equator
	 */
	public static final double milesPerRadianAtEquator = earthCircumferenceMiles / (2 * Math.PI);
	/**
	 * Constant value for the number of miles per degree at the equator
	 */
	public static final double milesPerDegreeAtEquator = earthCircumferenceMiles / 360;
	/**
	 * Constant value for the number of degrees per radian
	 */
	public static final double degreesPerRadian = 360 / (2 * Math.PI);
	/**
	 * Constant value for the number of radians per degree
	 */
	public static final double radiansPerDegree = (2 * Math.PI) / 360;
	/**
	 * Constant value for the longitude degrees (0) at the prime meridian
	 */
	public static final LongitudeDegrees primeMeridan = new LongitudeDegrees(0);

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value initial longitude
	 */
	public LongitudeDegrees(double value)
	{
		super(value);
	}

	/**
	 * Constructor for Real initial value
	 * 
	 * @param value initial longitude
	 */
	public LongitudeDegrees(RReal value)
	{
		this(value.value);
	}

	/**
	 * Constructor for LongitudeDegrees initial value
	 * 
	 * @param value initial latitude
	 */
	public LongitudeDegrees(LongitudeDegrees value)
	{
		this(value.value);
	}

	/**
	 * Constructor for LongitudeRadians initial value
	 * 
	 * @param value initial latitude
	 */
	public LongitudeDegrees(LongitudeRadians value)
	{
		this(Math.toDegrees(value.value));
	}

	/**
	 * Constructor for DirectionDegrees initial value
	 * 
	 * @param value initial latitude
	 */
	public LongitudeDegrees(DirectionDegrees value)
	{
		this(value.value);
	}

	/**
	 * Constructor for DirectionRadians initial value
	 * 
	 * @param value initial latitude
	 */
	public LongitudeDegrees(DirectionRadians value)
	{
		this(Math.toDegrees(value.value));
	}

	@Override
	public LongitudeDegrees added(RReal real)
	{
		return new LongitudeDegrees(value + real.value);
	}

	@Override
	public LongitudeDegrees subtracted(RReal real)
	{
		return new LongitudeDegrees(value - real.value);
	}

	@Override
	public LongitudeDegrees multipliedBy(RReal real)
	{
		return new LongitudeDegrees(value * real.value);
	}

	@Override
	public LongitudeDegrees dividedBy(RReal real)
	{
		return new LongitudeDegrees(value / real.value);
	}

	/**
	 * Returns the distance per one degree longitude at the specified latitude
	 * 
	 * @param atLatitude latitude of the distance to be returned
	 * @return miles distance per one degree longitude at the specified latitude
	 */
	public static DistanceMiles milesPerDegreeLongitude(LatitudeDegrees atLatitude)
	{
		return new DistanceMiles(milesPerDegreeAtEquator * Math.cos(Math.toRadians(atLatitude.value)));
	}

	/**
	 * Returns the distance per one radian longitude at the specified latitude
	 * 
	 * @param atLatitude latitude of the distance to be returned
	 * @return miles distance per one radian longitude at the specified latitude
	 */
	public static DistanceMiles milesPerRadianLongitude(LatitudeDegrees atLatitude)
	{
		return new DistanceMiles(milesPerRadianAtEquator * Math.cos(Math.toRadians(atLatitude.value)));
	}

	/**
	 * Returns the distance per one degree longitude at the specified latitude
	 * 
	 * @param atLatitude latitude of the distance to be returned
	 * @return kilometers distance per one degree longitude at the specified
	 *         latitude
	 */
	public static DistanceKilometers kilometersPerDegreeLongitude(LatitudeDegrees atLatitude)
	{
		return new DistanceKilometers(kilometersPerDegreeAtEquator * Math.cos(Math.toRadians(atLatitude.value)));
	}

	/**
	 * Returns the distance per one degree longitude at the specified latitude
	 * 
	 * @param atLatitude latitude of the distance to be returned
	 * @return kilometers distance per one radian longitude at the specified
	 *         latitude
	 */
	public static DistanceKilometers kilometersPerRadianLongitude(LatitudeDegrees atLatitude)
	{
		return new DistanceKilometers(kilometersPerRadianAtEquator * Math.cos(Math.toRadians(atLatitude.value)));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Degrees;
	}

	@Override
	public String toString()
	{
		return String.format("LongitudeDegrees [value=%s[%s], units=%s]", value, Math.toRadians(value), units.symbol);
	}
}
