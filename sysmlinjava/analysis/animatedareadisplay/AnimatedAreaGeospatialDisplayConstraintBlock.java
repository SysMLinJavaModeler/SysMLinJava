package sysmlinjava.analysis.animatedareadisplay;

import java.util.Optional;
import sysmlinjava.analysis.common.XYData;
import sysmlinjava.annotations.Constraint;
import sysmlinjava.annotations.Value;
import sysmlinjava.common.SysMLConstraint;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;
import sysmlinjava.valuetypes.LongitudeRadians;
import sysmlinjava.valuetypes.Point2D;
import sysmlinjava.valuetypes.Polyline2D;
import sysmlinjava.valuetypes.PolylineGeospatial;
import sysmlinjava.valuetypes.RReal;
import sysmlinjava.valuetypes.PointGeospatial;

/**
 * {@code AnimatedAreaGeospatialDisplayConstraintBlock} is a SysMLinJava model
 * of a SysML constraint block that produces an area display of objects whose
 * states and positions are represented by the current values of its constraint
 * parameters, i.e. the constraint block produces the area display from
 * constraint parameters.
 * <p>
 * {@code AnimatedAreaGeospatialDisplayConstraintBlock} extends the basic
 * {@code SysMLConstraintBlock}. As such, the constraint block defines a set of
 * constraint parameters, the constraint parameter ports that are "bound" to the
 * block values that correspond to the constraint parameters, and the constraint
 * that "calculates" the area display from the constraint parameters.
 * <p>
 * As an abstract class, the
 * {@code AnimatedAreaGeospatialDisplayConstraintBlock} must be
 * extended/specialized for the particular area display that is to be generated.
 * Specializations may use the constraint parameters defined in this base class,
 * i.e. the PointGeospatial-type and PolylineGeospatial-type parameters for the
 * most commonly used parameters for an area display, i.e. object positions and
 * paths/waypoints, but may also define other constraint parameters in the
 * specialized class as needed to further specialize the area display. In any
 * case, the specialized constraint block must also define the constraint
 * parameter ports that are "bound" to the block values as well as the
 * constraint that "calculates" the area display from the constraint parameters.
 * <p>
 * As described in the {@code SysMLConstraintBlock} comments, the constraint
 * parameter ports must be connected (bound) to the block values the correspond
 * to the constraint parameters. These connectors must be created in the
 * {@code createConstraintParameterConnectors()} method in a block that contains
 * the {@code AnimatedAreaGeospatialDisplayConstraintBlock} specialization and
 * the blocks of the bound values.}
 * 
 * @author ModelerOne
 *
 */
public abstract class AnimatedAreaGeospatialDisplayConstraintBlock extends SysMLConstraintBlock
{
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
	public RReal kilometersPerMile;
	/**
	 * Value for converting radians of latitude to kilometers
	 */
	@Value
	public RReal kilometerPerRadianLat;
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

	/**
	 * Constraint function that calculates and updates the area display from the
	 * constraint parameter values. The constraint function is invoked everytime a
	 * parameter port obtains a new value of a bound parameter and updates one or
	 * more of the constraint parameters accordingly.
	 */
	@Constraint
	public SysMLConstraint constraint;

	/**
	 * Definition of the area display to be calculated/constrained and displayed
	 */
	public AnimatedAreaDisplayDefinition displayDefinition;

	/**
	 * Definition of the area display data be calculated/constrained and displayed
	 */
	public AnimatedAreaDisplayData displayData;

	/**
	 * Transmitter of the area display's definition and data to the area display
	 */
	private AnimatedAreaDisplayTransmitter displayTransmitter;

	/**
	 * Constructor
	 * 
	 * @param udpPort      number of UDP port on which to receive the display
	 *                     definition and data
	 * @param logToConsole whether to send all {@code transmit()} logs to console.
	 *                     Note: {@code transmit()} operation can log every object's
	 *                     {@code toString()} string to the console. The more
	 *                     frequently log messages are sent to console, the greater
	 *                     the CPU resources are needed. This can cause noticable
	 *                     slowing of the console display and related applications.
	 */
	public AnimatedAreaGeospatialDisplayConstraintBlock(int udpPort, boolean logToConsole)
	{
		super(Optional.empty(), "AnimatedAreaGeospatialDisplay");
		displayDefinition = new AnimatedAreaDisplayDefinition();
		displayData = new AnimatedAreaDisplayData();

		createDisplayDefinition();
		createDisplayData();

		displayTransmitter = new AnimatedAreaDisplayTransmitter(udpPort, logToConsole);
		displayTransmitter.transmitDefinition(displayDefinition);
	}

	@Override
	public void stop()
	{
		displayTransmitter.stop();
		super.stop();
	}

	/**
	 * Operation that performs the constraint specified by the {@code constraint}
	 * field variable. Constraint consists mainly of calculating/updating the
	 * display data based on the changed constraint parameter(s) and then
	 * transmiting it to the area display. This operation can be overidden to
	 * perform additional/different constraint(s), but the overiding operation
	 * should lastly invoke the {@code transmitDisplayData()} operation to transmit
	 * the area display data for display.
	 */
	@Override
	protected void performConstraints()
	{
		constraint.apply();
		transmitDisplayData();
	}

	/**
	 * Transmits the display data for the area display
	 */
	protected void transmitDisplayData()
	{
		displayTransmitter.transmitData(displayData);
	}

	@Override
	protected void createValues()
	{
		kilometersPerMile = new RReal(1.609344);
		kilometerPerRadianLat = new RReal(LongitudeRadians.kilometersPerRadianAtEquator);
	}

	/**
	 * Overridable abstract operation that creates/constructs the area display
	 * definition. Specializations should override (and invoke) this method to
	 * initialize the values of the {@code displayDefinition} for tranmission to the
	 * area display.
	 */
	protected abstract void createDisplayDefinition();

	/**
	 * Overridable abstract operation that creates/constructs the area display data.
	 * Specializations should override (and invoke) this method to initialize the
	 * values of the {@code displayData} for tranmission to the area display.
	 */
	protected abstract void createDisplayData();

	/**
	 * Returns a poly line in this display's 2D cartesian space translated from a
	 * polyline in geo-space
	 * 
	 * @param geoPolyline the polyline in geo-space
	 * @return the polyline converted to 2D cartesian space
	 */
	protected Polyline2D toPolyLine2D(PolylineGeospatial geoPolyline)
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
	protected Point2D toPoint2D(PointGeospatial geoPoint)
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
	protected XYData toXYData(PointGeospatial geoPoint)
	{
		double xValue = Math.abs(geospatialUpperLeft.longitude.value - geoPoint.longitude.value) * pointsPerRadianLongitude.value;
		double yValue = Math.abs(geospatialUpperLeft.latitude.value - geoPoint.latitude.value) * pointsPerRadianLatitude.value;
		return new XYData(xValue, yValue);
	}
}
