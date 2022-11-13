package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.annotations.Operation;
import sysmlinjava.units.SysMLinJavaUnits;
import static java.lang.Math.*;

/**
 * SysMLinJava value type for the geospatial point in terms of a latitude and
 * longitude in radians, and an altitude in meters.<br>
 * All calculations are IAW the Aviation Formulary V1.47 by Ed Williams. Note
 * that this formulary uses positive latitudes and longitudes for northern and
 * western hemisphers, respectively, and negative latitudes and longitudes for
 * southern and eastern hemisphers, respectively.
 * 
 * @author ModelerOne
 *
 */
public class PointGeospatial extends SysMLValueType
{
	/**
	 * Constant value for earth's radius in meters
	 */
	public static final double earthRadiusMeters = 6366.71 * 1000;
	/**
	 * Constant value for earth's circumference in meters
	 */
	public static final double earthCircumferenceMeters = 2 * PI * earthRadiusMeters;
	/**
	 * Constant value for earth's circumference in radians
	 */
	public static final double earthCircumferenceRadians = 2 * PI;
	/**
	 * Constant value for distance along earth's surface in meters per radian 
	 */
	public static final double metersPerRadian = earthCircumferenceMeters / earthCircumferenceRadians;

	/**
	 * Point's latitude
	 */
	@Attribute
	public LatitudeRadians latitude;
	/**
	 * Point's longitude
	 */
	@Attribute
	public LongitudeRadians longitude;
	/**
	 * Point's altitude
	 */
	@Attribute
	public AltitudeMeters altitude;

	/**
	 * Constructor - all initial values
	 * 
	 * @param latitude  point's latitude
	 * @param longitude point's longitude
	 * @param altitude  point's altitude
	 */
	public PointGeospatial(LatitudeRadians latitude, LongitudeRadians longitude, AltitudeMeters altitude)
	{
		super();
		this.latitude = new LatitudeRadians(latitude);
		this.longitude = new LongitudeRadians(longitude);
		this.altitude = new AltitudeMeters(altitude);
	}

	/**
	 * Constructor - most used fields initial values, default zero altitude
	 * 
	 * @param latitude  point's latitude
	 * @param longitude point's longitude
	 */
	public PointGeospatial(LatitudeRadians latitude, LongitudeRadians longitude)
	{
		super();
		this.latitude = new LatitudeRadians(latitude);
		this.longitude = new LongitudeRadians(longitude);
		this.altitude = new AltitudeMeters(0.0);
	}

	/**
	 * Constructor - default zero initial values
	 */
	public PointGeospatial()
	{
		super();
		this.latitude = new LatitudeRadians(0);
		this.longitude = new LongitudeRadians(0);
		this.altitude = new AltitudeMeters(0.0);
	}

	/**
	 * Constructor - most used fields initial values as radians, default zero altitude
	 * 
	 * @param latitudeRadians  point's latitude
	 * @param longitudeRadians point's longitude
	 */
	public PointGeospatial(double latitudeRadians, double longitudeRadians)
	{
		super();
		this.latitude = new LatitudeRadians(latitudeRadians);
		this.longitude = new LongitudeRadians(longitudeRadians);
		this.altitude = new AltitudeMeters(0.0);
	}

	/**
	 * Constructor - most used fields initial values as degrees, default zero altitude
	 * 
	 * @param latitudeDegrees  point's latitude
	 * @param longitudeDegrees point's longitude
	 */
	public PointGeospatial(LatitudeDegrees latitudeDegrees, LongitudeDegrees longitudeDegrees)
	{
		super();
		this.latitude = new LatitudeRadians(latitudeDegrees);
		this.longitude = new LongitudeRadians(longitudeDegrees);
		this.altitude = new AltitudeMeters(0.0);
	}

	/**
	 * Constructor - copy
	 * @param copied point whose values are to be used for this copy
	 */
	public PointGeospatial(PointGeospatial copied)
	{
		super(copied);
		this.latitude = new LatitudeRadians(copied.latitude);
		this.longitude = new LongitudeRadians(copied.longitude);
		this.altitude = new AltitudeMeters(copied.altitude);
	}

	/**
	 * Returns a point as the difference between this and another point.
	 * 
	 * @param otherPoint the other point to be differed with this point
	 * @return point of differences
	 */
	public PointGeospatial deltaOf(PointGeospatial otherPoint)
	{
		PointGeospatial result = new PointGeospatial();
		result.latitude.value = abs(latitude.value - otherPoint.latitude.value);
		result.longitude.value = abs(longitude.value - otherPoint.longitude.value);
		result.altitude.value = abs(altitude.value - otherPoint.altitude.value);
		return result;
	}

	/**
	 * Returns the distance, in meters, to the specified other point from this
	 * point. Uses the following formula to calculate distance in radiansm which is
	 * converted to meters, from the Aviation Formulary:<br>
	 * 
	 * <pre>
	 * {@code
	 * distanceRadians = 2 * asin(sqrt(pow(sin((thisLat - otherLat) / 2), 2.0) + cos(thisLat) * cos(otherLat) * pow(sin((thisLon - otherLon) / 2), 2.0)));
	 * }
	 * </pre>
	 * 
	 * @param otherPoint point to calculate the distance to.
	 * @return distance, in meters, from this point to other point.
	 */
	@Operation
	public DistanceMeters distanceTo(PointGeospatial otherPoint)
	{
		double thisLat = this.latitude.value;
		double thisLon = this.longitude.value;
		double otherLat = otherPoint.latitude.value;
		double otherLon = otherPoint.longitude.value;
		double latDiff = thisLat - otherLat;
		double lonDiff = thisLon - otherLon;
		double distanceRadians = 2 * asin(sqrt(pow(sin(latDiff / 2), 2.0) + cos(thisLat) * cos(otherLat) * pow(sin(lonDiff / 2), 2.0)));
		double distanceMeters = distanceRadians * metersPerRadian;
		return new DistanceMeters(distanceMeters);
	}

	/**
	 * Returns heading in radians from this point to specified point. Uses following
	 * formula from Aviation Formulary:<br>
	 * 
	 * <pre>
	 * {@code
	 	d = <see calculation of d (distanceRadians) above>
	 	
		if (sin(otherLon - thisLon) < 0)
			heading = acos((sin(otherLat) - sin(thisLat) * cos(d)) / (sin(d) * cos(thisLat)));
		else
			heading = 2 * PI - acos((sin(otherLat) - sin(thisLat) * cos(d)) / (sin(d) * cos(thisLat)));
	 * }
	 * </pre>
	 * 
	 * @param toPoint point to which heading is to be calculated from this point
	 * @return heading in radians from this point to specified point
	 */
	public DirectionRadians headingTo(PointGeospatial toPoint)
	{
		double thisLat = latitude.value;
		double thisLon = longitude.value;
		double otherLat = toPoint.latitude.value;
		double otherLon = toPoint.longitude.value;
		double d = 2 * asin(sqrt(pow(sin((thisLat - otherLat) / 2), 2) + cos(thisLat) * cos(otherLat) * pow(sin((thisLon - otherLon) / 2), 2)));
		double heading;
		if (sin(otherLon - thisLon) < 0)
			heading = acos((sin(otherLat) - sin(thisLat) * cos(d)) / (sin(d) * cos(thisLat)));
		else
			heading = 2 * PI - acos((sin(otherLat) - sin(thisLat) * cos(d)) / (sin(d) * cos(thisLat)));
		return new DirectionRadians(heading);
	}

	/**
	 * Returns point to which this point is moved to along specified direction at
	 * specified distance. Uses following formula from Aviation Formulary:<br>
	 * 
	 * <pre>
	 * {@code
		lat =asin(sin(lat1)*cos(d)+cos(lat1)*sin(d)*cos(tc))
	 	dlon=atan2(sin(tc)*sin(d)*cos(lat1),cos(d)-sin(lat1)*sin(lat))
	 	lon=mod( lon1-dlon +pi,2*pi )-pi
		}
	 * </pre>
	 * 
	 * @param direction direction (heading, bearing, ...) in radians along which to
	 *                  move this point.
	 * @param distance  distance in meters to move this point
	 * @return other point to which this point is moved to.
	 */
	public PointGeospatial movedTo(DirectionRadians direction, DistanceMeters distance)
	{
		PointGeospatial result = new PointGeospatial();

		double directionRadians = direction.value;
		double distanceRadians = distance.value / metersPerRadian;
		double lat1 = latitude.value;
		double lon1 = longitude.value;

		double lat = asin(sin(lat1) * cos(distanceRadians) + cos(lat1) * sin(distanceRadians) * cos(directionRadians));
		double lon;
		if (cos(lat) == 0)
			lon = lon1;
		else
			lon = ((lon1 - asin(sin(directionRadians) * sin(distanceRadians) / cos(lat)) + PI) % (2 * PI)) - PI;
		result.latitude.value = lat;
		result.longitude.value = lon;
		return result;
	}

	/**
	 * Returns a third (projected) point in a sequence projected from a first and
	 * second actual points for the case where the projected point is the same
	 * direction and distance from the first to the second point. This projection
	 * follows a "simple" algorithm.
	 * 
	 * @param firstPoint  first (actual) point in the sequence
	 * @param secondPoint second (actual) point in the sequence
	 * @return third (projected) point in the sequence
	 */
	public static PointGeospatial projected(PointGeospatial firstPoint, PointGeospatial secondPoint)
	{
		DistanceMeters distanceMeters = firstPoint.distanceTo(secondPoint);
		DirectionRadians directionRadians = firstPoint.headingTo(secondPoint);
		return secondPoint.movedTo(directionRadians, distanceMeters);
	}

	/**
	 * Returns whether or not this point is within the specified square.
	 * 
	 * @param centerPoint     point of the square's center
	 * @param distanceSquared length of each side of the square
	 * @return true if this point is within the specified square, false otherwise
	 */
	public boolean isWithinSquareOf(PointGeospatial centerPoint, DistanceMeters distanceSquared)
	{
		boolean result = false;
		PointGeospatial squareSideCenterN = centerPoint.movedTo(DirectionRadians.north, distanceSquared).normalized();
		PointGeospatial squareSideCenterS = centerPoint.movedTo(DirectionRadians.south, distanceSquared).normalized();
		PointGeospatial squareSideCenterE = centerPoint.movedTo(DirectionRadians.east, distanceSquared).normalized();
		PointGeospatial squareSideCenterW = centerPoint.movedTo(DirectionRadians.west, distanceSquared).normalized();

		if (latitude.lessThan(squareSideCenterN.latitude) && latitude.greaterThan(squareSideCenterS.latitude) && longitude.lessThan(squareSideCenterW.longitude) && longitude.greaterThan(squareSideCenterE.longitude))
			result = true;
		return result;
	}

	/**
	 * Returns a point that is "normalized", i.e. its longitude is within the range
	 * of 0 to 2*PI. Use this for positive-only longitudes, i.e. no negative
	 * longitudes for west of prime meridian.
	 * 
	 * @return point with normalized longitude
	 */
	public PointGeospatial normalized()
	{
		double normLongitude = (longitude.value + (2 * PI)) % (2 * PI);
		return new PointGeospatial(latitude.value, normLongitude);
	}

	/**
	 * Return whether or not this point is within the area of the specifed circle.
	 * 
	 * @param center             point of the center of the specified circle
	 * @param distanceFromCenter distance from center of the specified circle.
	 * @return true if this point is within the circle, false if not.
	 */
	public boolean isWithinCircle(PointGeospatial center, DistanceMeters distanceFromCenter)
	{
		return distanceTo(center).lessThan(distanceFromCenter);
	}

	/**
	 * Returns whether or not this point is within the specified radial section
	 * where the radial section is analogous to the segment on a dartboard.
	 * 
	 * @param apexLocation         point of the apex of the section (dartboard's
	 *                             target center)
	 * @param radialCenterLocation center of the radial section, i.e. half way
	 *                             between left and right sides and near and far
	 *                             arcs
	 * @param radialHalfDistance   half distance between near and far arcs of
	 *                             section
	 * @param radialHalfAngle      half angle between left and right sides of
	 *                             section
	 * @return true if this point is within the specified radial section, false
	 *         otherwise
	 */
	public boolean isWithinRadialSection(PointGeospatial apexLocation, PointGeospatial radialCenterLocation, DistanceMeters radialHalfDistance, DirectionRadians radialHalfAngle)
	{
		boolean result = false;
		DirectionRadians centerRadialHeading = apexLocation.headingTo(radialCenterLocation);
		DirectionRadians thisHeading = apexLocation.headingTo(this);
		DirectionRadians leftRadialHeading = centerRadialHeading.subtracted(radialHalfAngle);
		DirectionRadians rightRadialHeading = centerRadialHeading.added(radialHalfAngle);

		if (thisHeading.isWithin(leftRadialHeading, rightRadialHeading))
		{
			DistanceMeters apexToThisPoint = apexLocation.distanceTo(this);
			DistanceMeters apexToSectionCenter = apexLocation.distanceTo(radialCenterLocation);
			DistanceMeters apexToSectionFarArc = new DistanceMeters(apexToSectionCenter.value + radialHalfDistance.value);
			DistanceMeters apexToSectionNearArc = new DistanceMeters(apexToSectionCenter.value - radialHalfDistance.value);
			if (apexToThisPoint.isBetween(apexToSectionNearArc, apexToSectionFarArc))
				result = true;
		}
		return result;
	}

	/**
	 * Sets the value of the point with latitude and longitude specified as
	 * primative doubles and notifies value change observers, if any.
	 * 
	 * @param latitudeRadians  point's latitude
	 * @param longitudeRadians point's longitude
	 */
	public void setValue(double latitudeRadians, double longitudeRadians)
	{
		this.latitude.value = latitudeRadians;
		this.longitude.value = longitudeRadians;
		this.altitude.value = 0.0;
		notifyValueChangeObservers();
	}

	/**
	 * Sets the value of the point to same as specified other point and notifies
	 * value change observers, if any.
	 * 
	 * @param value point whose value is to be used to set this value
	 */
	public void setValue(PointGeospatial value)
	{
		this.latitude.value = value.latitude.value;
		this.longitude.value = value.longitude.value;
		this.altitude.value = value.altitude.value;
		notifyValueChangeObservers();
	}

	@Override
	public String toString()
	{
		return String.format("PointGeospatial [latitude=%s(%s), longitude=%s(%s), altitude=%s]", latitude.value, toDegrees(latitude.value), longitude.value, toDegrees(longitude.value), altitude.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Radians;
	}
}
