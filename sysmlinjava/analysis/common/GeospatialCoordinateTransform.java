package sysmlinjava.analysis.common;

import java.io.Serializable;
import sysmlinjava.annotations.Value;
import sysmlinjava.valuetypes.LongitudeRadians;
import sysmlinjava.valuetypes.Point2D;
import sysmlinjava.valuetypes.PointGeospatial;
import sysmlinjava.valuetypes.Polyline2D;
import sysmlinjava.valuetypes.PolylineGeospatial;
import sysmlinjava.valuetypes.RReal;

public class GeospatialCoordinateTransform implements Serializable
{
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 823646528010005640L;
	/**
	 * Geospatial point that corresponds to the upper left corner of the geospatial
	 * area display. This parameter, along with {@code pointsPerRadianLatitude} and
	 * {@code pointsPerRadianLongitude} below, is used to translate all geospatial
	 * points (lats/lons) to cartesian points (X/Y) in the area display.
	 */
	@Value
	public PointGeospatial geospatialUpperLeft;
	/**
	 * Value of the number of X,Y points ({@code XYData}s) in a radian of latitude.
	 * This parameter, along with {@code geospatialUpperLeft} is used to translate
	 * all geospatial points (lats/lons) to cartesian points (X/Y) in the area
	 * display.
	 */
	@Value
	public RReal pointsPerRadianLatitude;
	/**
	 * Value of the number of X,Y points ({@code XYData}s) in a radian of longitude.
	 * Note this is a derived value and, along with {@code geospatialUpperLeft} is
	 * used to translate all geospatial points (lats/lons) to cartesian points (X/Y)
	 * in the area display.
	 */
	@Value
	public RReal pointsPerRadianLongitude;
	/**
	 * Value for converting miles to kilometers
	 */
	@Value
	public static RReal kilometersPerMile = new RReal(1.609344);
	/**
	 * Value for converting radians of latitude to kilometers
	 */
	@Value
	public static RReal kilometerPerRadianLat = new RReal(LongitudeRadians.kilometersPerRadianAtEquator);
	/**
	 * Value for converting radians of longitude (for a specified latitude) to
	 * kilometers
	 */
	@Value
	public RReal kilometerPerRadianLon;
	/**
	 * Value for converting kilometers into cartesian points (x or y) for the
	 * current area display
	 */
	@Value
	public RReal pointsCartesianPerKilometer;

	public GeospatialCoordinateTransform(PointGeospatial geospatialUpperLeft, RReal pointsPerRadianLatitude, RReal pointsPerRadianLongitude, RReal kilometerPerRadianLon, RReal pointsCartesianPerKilometer)
	{
		super();
		this.geospatialUpperLeft = geospatialUpperLeft;
		this.pointsPerRadianLatitude = pointsPerRadianLatitude;
		this.pointsPerRadianLongitude = pointsPerRadianLongitude;
		this.kilometerPerRadianLon = kilometerPerRadianLon;
		this.pointsCartesianPerKilometer = pointsCartesianPerKilometer;
	}

	/**
	 * Returns a poly line in this display's 2D cartesian space translated from a
	 * polyline in geo-space
	 * 
	 * @param geoPolyline the polyline in geo-space
	 * @return the polyline converted to 2D cartesian space
	 */
	public Polyline2D toPolyLine2D(PolylineGeospatial geoPolyline)
	{
		Point2D[] points = new Point2D[geoPolyline.value.length];
		for (int i = 0; i < geoPolyline.value.length; i++)
			points[i] = toPoint2D(geoPolyline.value[i]);
		return new Polyline2D(points);
	}

	/**
	 * Returns a point (x,y) in this display's 2D cartesian space translated from a
	 * point (lat, lon) in geo-space
	 * 
	 * @param geoPoint the point in geo-space
	 * @return the geoPoint converted to 2D cartesian space
	 */
	public Point2D toPoint2D(PointGeospatial geoPoint)
	{
		double xValue = (geospatialUpperLeft.longitude.value - geoPoint.longitude.value) * pointsPerRadianLongitude.value;
		double yValue = (geospatialUpperLeft.latitude.value - geoPoint.latitude.value) * pointsPerRadianLatitude.value;
		return new Point2D(xValue, yValue);
	}

	/**
	 * Returns a point (x,y) in this display's X-Y pixel space translated from a
	 * point (lat, lon) in geo-space
	 * 
	 * @param geoPoint the point in geo-space
	 * @return the geoPoint converted to X-Y pixel space
	 */
	public XY toXYData(PointGeospatial geoPoint)
	{
		double xValue = Math.abs(geospatialUpperLeft.longitude.value - geoPoint.longitude.value) * pointsPerRadianLongitude.value;
		double yValue = Math.abs(geospatialUpperLeft.latitude.value - geoPoint.latitude.value) * pointsPerRadianLatitude.value;
		return new XY(xValue, yValue);
	}
}