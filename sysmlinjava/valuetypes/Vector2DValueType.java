package sysmlinjava.valuetypes;

import java.util.List;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a vector in terms of a length (unspecified units)
 * and a direction in radians
 * 
 * @author ModelerOne
 *
 */
public class Vector2DValueType extends RReal
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
	public Vector2DValueType(double length, double directionRadians)
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
	public Vector2DValueType(double length, double directionRadians, long id)
	{
		this(length, directionRadians);
		this.id = id;
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied instance whose magnitude and direction values are to used as
	 *               initial values of this copy
	 */
	public Vector2DValueType(Vector2DValueType copied)
	{
		super(copied);
		this.direction = new DirectionRadians(copied.direction);
	}

	/**
	 * Returns the horizontal component of this force
	 * 
	 * @return vector value for the horizontal component of this vector
	 */
	public Vector2DValueType horizontalComponent()
	{
		double magnitude = value * Math.sin(direction.value);
		double direction = magnitude < 0 ? Math.toRadians(270) : Math.toRadians(90);
		return new Vector2DValueType(Math.abs(magnitude), direction);
	}

	/**
	 * Returns the vertical component of this force
	 * 
	 * @return vector value for the vertical component of this vector
	 */
	public Vector2DValueType verticalComponent()
	{
		double magnitude = value * Math.cos(direction.value);
		double direction = magnitude < 0 ? Math.toRadians(180) : Math.toRadians(0);
		return new Vector2DValueType(Math.abs(magnitude), direction);
	}

	/**
	 * Returns instance that is the vector sum of the specified set of vectors
	 * 
	 * @param vectors list of vector values to be summed
	 * @return vector sum of the specified vectors
	 */
	public static Vector2DValueType sum(List<Vector2DValueType> vectors)
	{
		Vector2DValueType result = new Vector2DValueType(0, 0);
		double horizontalComponents = 0;
		double verticalComponents = 0;
		for (Vector2DValueType force : vectors)
		{
			horizontalComponents += force.value * Math.sin(force.direction.value);
			verticalComponents += force.value * Math.cos(force.direction.value);
		}
		result.value = Math.hypot(horizontalComponents, verticalComponents);
		double direction = Math.atan(horizontalComponents / verticalComponents);
		if (direction < 0)
			direction += 2 * Math.PI;
		if (verticalComponents < 0)
			if (horizontalComponents < 0)
				direction += Math.PI;
			else
				direction -= Math.PI;
		result.direction.value = direction;
		return result;
	}

	/**
	 * Sets this value to the specified vector value
	 * 
	 * @param vector vector value to which this value is to be set
	 */
	public void setValue(Vector2DValueType vector)
	{
		direction.setValue(vector.direction.value);
		setValue(vector.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	@Override
	public String toString()
	{
		return String.format("Vector2DValueType [value=%s, units=%s, direction=%s]", value, units, direction);
	}
}
