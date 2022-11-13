package sysmlinjava.valuetypes;

import static java.lang.Math.PI;
import sysmlinjava.annotations.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for direction (heading, bearing, etc.) in radians.
 * 
 * @author ModelerOne
 *
 */
public class DirectionRadians extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Consant direction for cardinal north direction
	 */
	public static final DirectionRadians north = new DirectionRadians(0);
	/**
	 * Consant direction for cardinal south direction
	 */
	public static final DirectionRadians south = new DirectionRadians(PI);
	/**
	 * Consant direction for cardinal east direction
	 */
	public static final DirectionRadians east = new DirectionRadians(PI / 2);
	/**
	 * Consant direction for cardinal west direction
	 */
	public static final DirectionRadians west = new DirectionRadians(1.5 * PI);
	/**
	 * Constant value of radians per degree
	 */
	public static final DirectionRadians radiansPerDegree = new DirectionRadians((2 * PI) / 360);

	/**
	 * Constructor with primitive double for initial value
	 * 
	 * @param value direction in radians
	 */
	public DirectionRadians(double value)
	{
		super(value);
	}

	/**
	 * Constructor with Real for initial value
	 * 
	 * @param real value in radians
	 */
	public DirectionRadians(RReal real)
	{
		super(real.value);
	}

	/**
	 * Constructor for conversion from degrees
	 * 
	 * @param degrees direction in degrees
	 */
	public DirectionRadians(DirectionDegrees degrees)
	{
		super(degrees.value * ((2 * PI) / 360));
	}

	/**
	 * Constructor with Real for initial value
	 * 
	 * @param copied direction in radians
	 */
	public DirectionRadians(DirectionRadians copied)
	{
		super(copied);
	}

	/**
	 * Constructor - default 0
	 */
	public DirectionRadians()
	{
		super(0);
	}

	/**
	 * Returns direction that is the specified direction subtracted from this
	 * direction. Valid for positive values only (0 to 2 * PI)
	 * 
	 * @param addedDirection direction to add
	 * @return direction that is the specified direction added to this direction
	 */
	public DirectionRadians added(DirectionRadians addedDirection)
	{
		DirectionRadians result = new DirectionRadians(value);
		result.value = value + addedDirection.value;
		if (result.value >= 2 * PI)
			result.value %= 2 * PI;
		return result;
	}

	/**
	 * Add the specified direction to this direction. Valid for positive values only
	 * (0 to 2 * PI)
	 * 
	 * @param addedDirection direction to add
	 */
	public void add(DirectionRadians addedDirection)
	{
		value += addedDirection.value;
		if (value >= 2 * PI)
			value %= 2 * PI;
	}

	/**
	 * Returns direction that is the specified direction subtracted from this
	 * direction. Valid for positive values only (0 to 2 * PI)
	 * 
	 * @param subtractedDirection direction to sutract
	 * @return direction that is the specified direction subtracted from this
	 *         direction
	 */
	public DirectionRadians subtracted(DirectionRadians subtractedDirection)
	{
		DirectionRadians result = new DirectionRadians(value);
		if (value > subtractedDirection.value)
			result.value = value - subtractedDirection.value;
		else
			result.value = value + 2 * PI - subtractedDirection.value;
		return result;
	}

	/**
	 * Subtract the specified direction from this direction. Valid for positive
	 * values only (0 to 2 * PI)
	 * 
	 * @param subtractedDirection direction to subtract
	 */
	public void subtract(DirectionRadians subtractedDirection)
	{
		if (value > subtractedDirection.value)
			value -= subtractedDirection.value;
		else
			value = value + 2 * PI - subtractedDirection.value;
	}

	/**
	 * Returns the opposite (180 degrees or PI radians greater) direction
	 * 
	 * @return opposite direction
	 */
	@Operation
	public DirectionRadians opposite()
	{
		return new DirectionRadians((value += PI) % (2 * PI));
	}

	/**
	 * Returns whether or not this direction is within the bounds (range) of the
	 * specified directions
	 * 
	 * @param leftHeading  leftmost direction in the range
	 * @param rightHeading rightmost direction in the range
	 * @return true if this direction is within the range of the specified
	 *         directions, false otherwise
	 */
	public boolean isWithin(DirectionRadians leftHeading, DirectionRadians rightHeading)
	{
		boolean result = false;
		if (leftHeading.lessThan(rightHeading))
			result = value >= leftHeading.value && value <= rightHeading.value;
		else
			result = (value >= leftHeading.value && value <= 2 * PI) || (value >= 0 && value <= rightHeading.value);
		return result;
	}

	/**
	 * Returns this direction as direction in degrees
	 * 
	 * @return direction in degrees
	 */
	public DirectionDegrees toDegrees()
	{
		return new DirectionDegrees(Math.toDegrees(value));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Radians;
	}

	@Override
	public java.lang.String toString()
	{
		return java.lang.String.format("DirectionRadians [value=%s(%s), units=%s]", value, Math.toDegrees(value), units.symbol);
	}
}
