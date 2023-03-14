package sysmlinjava.valuetypes;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.hypot;
import static java.lang.Math.toRadians;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.List;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a vector in terms of a length of any numeric units
 * and a direction in radians
 * 
 * @author ModelerOne
 *
 */
public class Vector2D extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Attribute for vector direction
	 */
	@Attribute
	public DirectionRadians direction;

	/**
	 * Constructor
	 * 
	 * @param length           double value for initial vector length
	 * @param directionRadians double value for vector direction
	 */
	public Vector2D(double length, double directionRadians)
	{
		super(length);
		this.direction = new DirectionRadians(directionRadians);
	}

	/**
	 * Constructor
	 * 
	 * @param length           RReal value for initial vector length
	 * @param directionRadians RReal value for vector direction
	 */
	public Vector2D(RReal length, RReal directionRadians)
	{
		super(length);
		this.direction = new DirectionRadians(directionRadians);
	}

	/**
	 * Constructor
	 * 
	 * @param length           double value for initial vector length
	 * @param directionRadians double value for vector direction
	 * @param id               unique ID for this instance, e.g. index into array of
	 *                         vectors
	 */
	public Vector2D(double length, double directionRadians, long id)
	{
		this(length, directionRadians);
		this.id = id;
	}

	public Vector2D(Point2D fromPoint, Point2D toPoint)
	{
		super(0);
		double xDelta = 0;
		double yDelta = 0;
		if(fromPoint.xValue >= toPoint.xValue)
		{
			xDelta = fromPoint.xValue - toPoint.xValue;
			if(fromPoint.yValue >= toPoint.yValue)
			{
				yDelta = fromPoint.yValue - toPoint.yValue;
				direction = new DirectionRadians(1.0 * PI + atan(xDelta/yDelta));
			}
			else
			{
				yDelta = toPoint.yValue - fromPoint.yValue;
				direction = new DirectionRadians(2.0 * PI - atan(xDelta/yDelta));
			}
		}
		else
		{
			xDelta = toPoint.xValue - fromPoint.xValue;
			if(fromPoint.yValue >= toPoint.yValue)
			{
				yDelta = fromPoint.yValue - toPoint.yValue;
				direction = new DirectionRadians(1.0 * PI - atan(xDelta/yDelta));
			}
			else
			{
				yDelta = toPoint.yValue - fromPoint.yValue;
				direction = new DirectionRadians(0.0 * PI + atan(xDelta/yDelta));
			}
		}
		value = sqrt(pow(xDelta, 2.0) + pow(yDelta, 2.0));
	}
	
	/**
	 * Constructor - copy
	 * 
	 * @param copied instance whose magnitude and direction values are to used as
	 *               initial values of this copy
	 */
	public Vector2D(Vector2D copied)
	{
		super(copied);
		this.direction = new DirectionRadians(copied.direction);
	}

	/**
	 * Returns the horizontal component of this force
	 * 
	 * @return vector value for the horizontal component of this vector
	 */
	public Vector2D horizontalComponent()
	{
		double magnitude = value * sin(direction.value);
		double direction = magnitude < 0 ? toRadians(270) : toRadians(90);
		return new Vector2D(Math.abs(magnitude), direction);
	}

	/**
	 * Returns the vertical component of this force
	 * 
	 * @return vector value for the vertical component of this vector
	 */
	public Vector2D verticalComponent()
	{
		double magnitude = value * cos(direction.value);
		double direction = magnitude < 0 ? toRadians(180) : toRadians(0);
		return new Vector2D(Math.abs(magnitude), direction);
	}

	/**
	 * Returns instance that is the vector sum of the specified set of vectors
	 * 
	 * @param vectors list of vector values to be summed
	 * @return vector sum of the specified vectors
	 */
	public static Vector2D sum(List<Vector2D> vectors)
	{
		Vector2D result = new Vector2D(0, 0);
		double horizontalComponents = 0;
		double verticalComponents = 0;
		for (Vector2D force : vectors)
		{
			horizontalComponents += force.value * sin(force.direction.value);
			verticalComponents += force.value * cos(force.direction.value);
		}
		result.value = hypot(horizontalComponents, verticalComponents);
		double direction = atan(horizontalComponents / verticalComponents);
		if (direction < 0)
			direction += 2 * PI;
		if (verticalComponents < 0)
			if (horizontalComponents < 0)
				direction += PI;
			else
				direction -= PI;
		result.direction.value = direction;
		return result;
	}

	/**
	 * Sets this vector's values to the values of the specified vector
	 * 
	 * @param vector vector from which this vector's values are to be set
	 */
	public void setValue(Vector2D vector)
	{
		value = vector.value;
		direction.value = vector.direction.value;
		notifyValueChangeObservers();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Vector2D [value=");
		builder.append(value);
		builder.append(", units=");
		builder.append(units);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", direction=");
		builder.append(direction);
		builder.append("]");
		return builder.toString();
	}
}
