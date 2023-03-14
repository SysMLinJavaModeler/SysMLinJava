package sysmlinjava.valuetypes;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.probability.SysMLProbabilityDistribution;

/**
 * SysMLinJava value type for velocity in meters/second speed and radians
 * direction
 * 
 * @author ModelerOne
 *
 */
public class VelocityMetersPerSecondRadians extends SpeedMetersPerSecond
{
	/** Serializable ID */
	private static final long serialVersionUID = -4254011173491711138L;

	/**
	 * Attribute for the heading/direction value of the velocity
	 */
	@Attribute
	public DirectionRadians heading;

	/**
	 * Constant value for the conversion factor of meters per second to miles per
	 * hour
	 */
	public static final SpeedMetersPerSecond metersPerSecondPerMilesPerHour = new SpeedMetersPerSecond(0.44704);

	/**
	 * Constructor
	 * 
	 * @param speed   double value to be used for speed value of this initial value
	 * @param heading direction value to be used for the heading value of the this
	 *                intial value
	 */
	public VelocityMetersPerSecondRadians(SpeedMetersPerSecond speed, DirectionRadians heading)
	{
		super(speed.value);
		this.heading = new DirectionRadians(heading.value);
	}

	/**
	 * Constructor
	 * 
	 * @param speedMetersPerSecond double value to be used for speed value of this
	 *                             initial value
	 * @param directionRadians     direction value to be used for the heading value
	 *                             of the this intial value
	 */
	public VelocityMetersPerSecondRadians(double speedMetersPerSecond, double directionRadians)
	{
		super(speedMetersPerSecond);
		heading = new DirectionRadians(directionRadians);
	}

	/**
	 * Constructor
	 * 
	 * @param speedMetersPerSecond double value to be used for speed value of this
	 *                             initial value
	 * @param directionRadians     direction value to be used for the heading value
	 *                             of the this intial value
	 * @param speedDistribution    probability distribution to be set as this
	 *                             speed's value distribution
	 * @param headingDistribution  probability distribution to be set as this
	 *                             heading's value distribution
	 */
	public VelocityMetersPerSecondRadians(double speedMetersPerSecond, double directionRadians, SysMLProbabilityDistribution speedDistribution, SysMLProbabilityDistribution headingDistribution)
	{
		super(speedMetersPerSecond);
		heading = new DirectionRadians(directionRadians);
		probabilityDistribution = Optional.of(speedDistribution);
		heading.probabilityDistribution = Optional.of(headingDistribution);
	}

	/**
	 * Constructor
	 * 
	 * @param speedMilesPerHour speed mph value to be used for speed value of this
	 *                          initial value
	 * @param directionDegrees  direction degrees value to be used for the heading
	 *                          value of the this intial value
	 */
	public VelocityMetersPerSecondRadians(SpeedMilesPerHour speedMilesPerHour, DirectionDegrees directionDegrees)
	{
		super(speedMilesPerHour.multipliedBy(metersPerSecondPerMilesPerHour));
		heading = new DirectionRadians(directionDegrees);
	}

	/**
	 * Constructor - default to zero values
	 */
	public VelocityMetersPerSecondRadians()
	{
		super(0);
		this.heading = new DirectionRadians(0);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied velocity whose values are to be initial values for this copy
	 */
	public VelocityMetersPerSecondRadians(VelocityMetersPerSecondRadians copied)
	{
		super(copied);
		this.heading = new DirectionRadians(copied.heading);
	}

	/**
	 * Returns direction value for this heading
	 * 
	 * @return direction value for this heading
	 */
	public DirectionRadians getHeading()
	{
		return heading;
	}

	/**
	 * Returns this velocity speed value
	 * 
	 * @return speed value
	 */
	public SpeedMetersPerSecond getSpeed()
	{
		return this;
	}

	/**
	 * Sets this velocity's heading to the specified heading value, and notifies all
	 * value change observers of the change
	 * 
	 * @param heading direction value to be set as this heading value
	 */
	public void setHeading(DirectionRadians heading)
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
	public void setSpeed(SpeedMetersPerSecond speed)
	{
		this.value = speed.value;
		this.heading = new DirectionRadians(0);
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's heading and speed to the specified heading and speed
	 * values, and notifies all value change observers of the change
	 * 
	 * @param speed   speed value to be set as this speed value
	 * @param heading direction value to be set as this heading value
	 */
	public void setSpeedAndHeading(SpeedMetersPerSecond speed, DirectionRadians heading)
	{
		this.value = speed.value;
		this.heading.value = heading.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's probability distributions for heading and speed to the
	 * specified distributions, and notifies all value change observers of the
	 * change
	 * 
	 * @param speedDistribution   probability distribution to be set as this speed's
	 *                            value distribution
	 * @param headingDistribution probability distribution to be set as this
	 *                            heading's value distribution
	 */
	public void setProbabilityDistributions(SysMLProbabilityDistribution speedDistribution, SysMLProbabilityDistribution headingDistribution)
	{
		this.probabilityDistribution = Optional.of(speedDistribution);
		this.heading.probabilityDistribution = Optional.of(headingDistribution);
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's heading and speed to the specified heading and speed
	 * values, and notifies all value change observers of the change
	 * 
	 * @param speedMetersPerSecond double value to be set as this speed value
	 * @param headingRadians       double value to be set as this heading value
	 */
	public void setValue(double speedMetersPerSecond, double headingRadians)
	{
		this.value = speedMetersPerSecond;
		this.heading.value = headingRadians;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's heading and speed to the specified heading and speed
	 * values, and notifies all value change observers of the change
	 * 
	 * @param speed   speed value to be set as this speed value
	 * @param heading direction value to be set as this heading value
	 */
	public void setValue(SpeedMetersPerSecond speed, DirectionRadians heading)
	{
		this.value = speed.value;
		this.heading.value = heading.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this velocity's heading and speed to the heading and speed values in the
	 * specified velocity, and notifies all value change observers of the change
	 * 
	 * @param fromVelocity velocity whose values are to be used for this velocity
	 */
	public void setValue(VelocityMetersPerSecondRadians fromVelocity)
	{
		this.value = fromVelocity.value;
		this.heading.value = fromVelocity.heading.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets the values for speed and heading to random values IAW probability
	 * distributions of values set for each, i.e.
	 * {@code value = probabilityDistribution.nextRandom()}
	 */
	public void toNextRandom()
	{
		if (probabilityDistribution.isPresent())
			value = probabilityDistribution.get().nextRandom();
		if (heading.probabilityDistribution.isPresent())
			heading.value = heading.probabilityDistribution.get().nextRandom();
	}

	/**
	 * Sets the values for speed and heading to random variations (deltas) of
	 * currentvalues IAW probability distributions of delta values set for each,
	 * i.e. {@code value = value + probabilityDistribution.nextRandom()}
	 */
	public void toNextRandomDelta()
	{
		if (probabilityDistribution.isPresent())
			value += probabilityDistribution.get().nextRandom();
		if (heading.probabilityDistribution.isPresent())
			heading.value += heading.probabilityDistribution.get().nextRandom();
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("VelocityMetersPerSecondRadians [heading=");
		builder.append(heading);
		builder.append(", speed=");
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
}
