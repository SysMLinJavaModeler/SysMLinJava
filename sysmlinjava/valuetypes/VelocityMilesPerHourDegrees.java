package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for velocity in miles/hour speed and degrees direction
 * 
 * @author ModelerOne
 *
 */
public class VelocityMilesPerHourDegrees extends SpeedMilesPerHour
{
	/** Serializable ID*/private static final long serialVersionUID = -4254011173491711138L;

	/**
	 * Attribute for the heading/direction value of the velocity
	 */
	@Attribute
	public DirectionDegrees heading;

	/**
	 * Constant value for the conversion factor of miles per hour to meters per
	 * second
	 */
	public static final SpeedMilesPerHour milesPerHourPerMetersPerSecond = new SpeedMilesPerHour(2.236936);

	/**
	 * Constructor
	 * 
	 * @param speed   double value to be used for speed value of this initial value
	 * @param heading direction value to be used for the heading value of the this
	 *                intial value
	 */
	public VelocityMilesPerHourDegrees(SpeedMilesPerHour speed, DirectionDegrees heading)
	{
		super(speed.value);
		this.heading = new DirectionDegrees(heading.value);
	}

	/**
	 * Constructor
	 * 
	 * @param speedMilesPerHour double value to be used for speed value of this
	 *                          initial value
	 * @param directionDegrees  double value to be used for the heading value of the
	 *                          this intial value
	 */
	public VelocityMilesPerHourDegrees(double speedMilesPerHour, double directionDegrees)
	{
		super(speedMilesPerHour);
		heading = new DirectionDegrees(directionDegrees);
	}

	/**
	 * Constructor - default zero speed and direction
	 */
	public VelocityMilesPerHourDegrees()
	{
		super(0);
		this.heading = new DirectionDegrees(0);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied velocity whose values are to be initial values for this copy
	 */
	public VelocityMilesPerHourDegrees(VelocityMilesPerHourDegrees copied)
	{
		super(copied);
		this.heading = new DirectionDegrees(copied.heading);
	}

	/**
	 * Returns direction value for this heading
	 * 
	 * @return direction value for this heading
	 */
	public DirectionDegrees getHeading()
	{
		return heading;
	}

	/**
	 * Returns this velocity speed value
	 * 
	 * @return speed value
	 */
	public SpeedMilesPerHour getSpeed()
	{
		return this;
	}

	/**
	 * Sets this velocity's heading to the specified heading value, and notifies all
	 * value change observers of the change
	 * 
	 * @param heading direction value to be set as this heading value
	 */
	public void setHeading(DirectionDegrees heading)
	{
		this.value = 0;
		this.heading = heading;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's speed to the specified speed value, and notifies all
	 * value change observers of the change
	 * 
	 * @param speed speed value to be set as this speed value
	 */
	public void setSpeed(SpeedMilesPerHour speed)
	{
		this.value = speed.value;
		this.heading = new DirectionDegrees(0);
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's heading and speed to the specified heading and speed
	 * values, and notifies all value change observers of the change
	 * 
	 * @param speed   speed value to be set as this speed value
	 * @param heading direction value to be set as this heading value
	 */
	public void setSpeedAndHeading(SpeedMilesPerHour speed, DirectionRadians heading)
	{
		this.value = speed.value;
		this.heading.value = heading.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's heading and speed to the specified heading and speed
	 * values, and notifies all value change observers of the change
	 * 
	 * @param speedMilesPerHour double value to be set as this speed value
	 * @param headingDegrees    double value to be set as this heading value
	 */
	public void setValue(double speedMilesPerHour, double headingDegrees)
	{
		this.value = speedMilesPerHour;
		this.heading.value = headingDegrees;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's heading and speed to the specified heading and speed
	 * values, and notifies all value change observers of the change
	 * 
	 * @param speed   speed value to be set as this speed value
	 * @param heading direction value to be set as this heading value
	 */
	public void setValue(SpeedMilesPerHour speed, DirectionDegrees heading)
	{
		this.value = speed.value;
		this.heading.value = heading.value;
		notifyValueChangeObservers();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.MilesPerHourDegrees;
	}

	@Override
	public String toString()
	{
		return String.format("VelocityMilesPerHourDegrees [heading=%s, speed=%s]", heading, value);
	}
}
