package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for longitude in radians
 * 
 * @author ModelerOne
 *
 */
public class LongitudeRadians extends RReal
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant value for earth circumference in kilometers
	 */
	public static final double earthCircumferenceKilometers = 40_040;
	/**
	 * Constant value for earth circumference in miles
	 */
	public static final double earthCircumferenceMiles = 24_880;
	/**
	 * Constant value for kilometers distance per radian at the equator
	 */
	public static final double kilometersPerRadianAtEquator = earthCircumferenceKilometers / (2 * Math.PI);
	/**
	 * Constant value for kilometers distance per degree at the equator
	 */
	public static final double kilometersPerDegreeAtEquator = earthCircumferenceKilometers / 360;
	/**
	 * Constant value for converting radians to miles at the equatorkilometers
	 * distance per radian at the equator
	 */
	public static final double milesPerRadianAtEquator = earthCircumferenceMiles / (2 * Math.PI);
	/**
	 * Constant value for converting degrees to miles at the equator
	 */
	public static final double milesPerDegreeAtEquator = earthCircumferenceMiles / 360;
	/**
	 * Constant value for converting radians to degrees
	 */
	public static final double degreesPerRadian = 360 / (2 * Math.PI);
	/**
	 * Constant value for converting degrees to radians
	 */
	public static final double radiansPerDegree = (2 * Math.PI) / 360;

	/**
	 * Constant longitude radians for prime meridian
	 */
	public static final LongitudeDegrees primeMeridan = new LongitudeDegrees(0);

	/**
	 * Constructor for primitive double initial value
	 * 
	 * @param value initial longitude
	 */
	public LongitudeRadians(double value)
	{
		super(value);
	}

	/**
	 * Constructor for Real initial value
	 * 
	 * @param value initial longitude
	 */
	public LongitudeRadians(RReal value)
	{
		this(value.value);
	}

	/**
	 * Constructor for LongitudeDegrees initial value
	 * 
	 * @param value initial latitude
	 */
	public LongitudeRadians(LongitudeDegrees value)
	{
		this(Math.toRadians(value.value));
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied intance to be copied
	 */
	public LongitudeRadians(LongitudeRadians copied)
	{
		super(copied);
	}

	/**
	 * Constructor for DirectionDegrees initial value
	 * 
	 * @param value initial latitude
	 */
	public LongitudeRadians(DirectionDegrees value)
	{
		this(Math.toRadians(value.value));
	}

	/**
	 * Constructor for DirectionRadians initial value
	 * 
	 * @param value initial latitude
	 */
	public LongitudeRadians(DirectionRadians value)
	{
		this(value.value);
	}

	@Override
	public LongitudeRadians added(RReal real)
	{
		return new LongitudeRadians(value + real.value);
	}

	@Override
	public LongitudeRadians subtracted(RReal real)
	{
		return new LongitudeRadians(value - real.value);
	}

	@Override
	public LongitudeRadians multipliedBy(RReal real)
	{
		return new LongitudeRadians(value * real.value);
	}

	@Override
	public LongitudeRadians dividedBy(RReal real)
	{
		return new LongitudeRadians(value / real.value);
	}

	/**
	 * Returns the distance per one degree longitude at the specified latitude
	 * 
	 * @param atLatitude latitude of the distance to be returned
	 * @return miles distance per one degree longitude at the specified latitude
	 */
	public static DistanceMiles milesPerDegreeLongitude(LatitudeRadians atLatitude)
	{
		return new DistanceMiles(milesPerDegreeAtEquator * Math.cos(atLatitude.value));
	}

	/**
	 * Returns the distance per one radian longitude at the specified latitude
	 * 
	 * @param atLatitude latitude of the distance to be returned
	 * @return miles distance per one radian longitude at the specified latitude
	 */
	public static DistanceMiles milesPerRadianLongitude(LatitudeRadians atLatitude)
	{
		return new DistanceMiles(milesPerRadianAtEquator * Math.cos(atLatitude.value));
	}

	/**
	 * Returns the distance per one degree longitude at the specified latitude
	 * 
	 * @param atLatitude latitude of the distance to be returned
	 * @return kilometers distance per one degree longitude at the specified
	 *         latitude
	 */
	public static DistanceKilometers kilometersPerDegreeLongitude(LatitudeRadians atLatitude)
	{
		return new DistanceKilometers(kilometersPerDegreeAtEquator * Math.cos(atLatitude.value));
	}

	/**
	 * Returns the distance per one radian longitude at the specified latitude
	 * 
	 * @param atLatitude latitude of the distance to be returned
	 * @return kilometers distance per one radian longitude at the specified
	 *         latitude
	 */
	public static DistanceKilometers kilometersPerRadianLongitude(LatitudeRadians atLatitude)
	{
		return new DistanceKilometers(kilometersPerRadianAtEquator * Math.cos(atLatitude.value));
	}

	/**
	 * Returns the distance per one radian longitude at the specified latitude
	 * 
	 * @param atLatitudeRadians double value for the latitude of the distance to be
	 *                          returned
	 * @return kilometers distance per one radian longitude at the specified
	 *         latitude
	 */
	public static DistanceKilometers kilometersPerRadianLongitude(double atLatitudeRadians)
	{
		return new DistanceKilometers(kilometersPerRadianAtEquator * Math.cos(atLatitudeRadians));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Radians;
	}

	@Override
	public String toString()
	{
		return String.format("LongitudeRadians [value=%s[%s], units=%s]", value, Math.toDegrees(value), units.symbol);
	}
}
