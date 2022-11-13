package sysmlinjava.valuetypes;

import static java.lang.Math.PI;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for direction (heading, bearing, etc.) in degrees.
 * 
 * @author ModelerOne
 *
 */
public class DirectionDegrees extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = -1566489011777283872L;

	/**
	 * Constant direction for cardinal north direction
	 */
	public static final DirectionDegrees north = new DirectionDegrees(0);
	/**
	 * Constant direction for cardinal south direction
	 */
	public static final DirectionDegrees south = new DirectionDegrees(180);
	/**
	 * Constant direction for cardinal east direction
	 */
	public static final DirectionDegrees east = new DirectionDegrees(90);
	/**
	 * Constant direction for cardinal west direction
	 */
	public static final DirectionDegrees west = new DirectionDegrees(270);
	/**
	 * Constant value of degrees per radian
	 */
	public static final DirectionDegrees perRadian = new DirectionDegrees(360 / (2 * PI));

	/**
	 * Constructor with primitive double for initial value
	 * 
	 * @param value direction in degrees
	 */
	public DirectionDegrees(double value)
	{
		super(value);
	}

	/**
	 * Constructor with Real for initial value
	 * 
	 * @param value direction in degrees
	 */
	public DirectionDegrees(RReal value)
	{
		super(value.value);
	}

	/**
	 * Constructor with another DirectionDegrees for initial value
	 * 
	 * @param value direction in DirectionDegrees
	 */
	public DirectionDegrees(DirectionDegrees value)
	{
		super(value.value);
	}

	/**
	 * Constructor for conversion from radians
	 * 
	 * @param radians direction in radians
	 */
	public DirectionDegrees(DirectionRadians radians)
	{
		super(Math.toDegrees(radians.value));
	}

	/**
	 * Sets this direction to the specified value and notifies all current value
	 * change observers, if any.
	 * <p>
	 * <b>Note:</b>Use this method to change the direction value if/when
	 * notification of value change observers is needed.
	 * 
	 * @param value direction value as a DirectionDegrees
	 */
	public void setValue(DirectionDegrees value)
	{
		this.value = value.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this direction to the specified value and notifies all current value
	 * change observers, if any.
	 * <p>
	 * <b>Note:</b>Use this method to change the direction value if/when
	 * notification of value change observers is needed.
	 * 
	 * @param value direction value as a RReal
	 */
	public void setValue(RReal value)
	{
		this.value = value.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this direction to the specified value and notifies all current value
	 * change observers, if any.
	 * <p>
	 * <b>Note:</b>Use this method to change the direction value if/when
	 * notification of value change observers is needed.
	 * 
	 * @param value direction value as a double
	 */
	public void setValue(double value)
	{
		this.value = value;
		notifyValueChangeObservers();
	}

	/**
	 * Returns whether this direction is equal the specified direction
	 * 
	 * @param direction direction to be compared with this direction
	 * @return tru if this direction is equal to specified direction, false
	 *         otherwise
	 */
	public boolean equalTo(DirectionDegrees direction)
	{
		return this.value == direction.value;
	}

	/**
	 * Returns direction that is the specified direction subtracted from this
	 * direction. Valid for positive values only (0 to 360)
	 * 
	 * @param addedDirection direction to add
	 * @return direction that is the specified direction added to this direction
	 */
	public DirectionDegrees added(DirectionDegrees addedDirection)
	{
		DirectionDegrees result = new DirectionDegrees(value);
		result.value = value + addedDirection.value;
		if (value >= 360.0)
			result.value %= 360.0;
		return result;
	}

	/**
	 * Add the specified direction to this direction. Valid for positive values only
	 * (0 to 360)
	 * 
	 * @param addedDirection direction to add
	 */
	public void add(DirectionDegrees addedDirection)
	{
		value += addedDirection.value;
		if (value >= 360.0)
			value %= 360.0;
	}

	/**
	 * Returns direction that is the specified direction subtracted from this
	 * direction. Valid for positive values only (0 to 360)
	 * 
	 * @param subtractedDirection direction to sutract
	 * @return direction that is the specified direction subtracted from this
	 *         direction
	 */
	public DirectionDegrees subtracted(DirectionDegrees subtractedDirection)
	{
		DirectionDegrees result = new DirectionDegrees(value);
		if (value > subtractedDirection.value)
			result.value = value - subtractedDirection.value;
		else
			result.value = value + 360.0 - subtractedDirection.value;
		return result;
	}

	/**
	 * Subtract the specified direction from this direction. Valid for positive
	 * values only (0 to 360)
	 * 
	 * @param subtractedDirection direction to subtract
	 */
	public void subtract(DirectionDegrees subtractedDirection)
	{
		if (value > subtractedDirection.value)
			value -= subtractedDirection.value;
		else
			value = value + 360.0 - subtractedDirection.value;
	}

	/**
	 * Returns the opposite (180 degrees greater) direction
	 * 
	 * @return opposite direction
	 */
	public DirectionDegrees opposite()
	{
		return new DirectionDegrees((value += 180) % 360);
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
	public boolean isWithin(DirectionDegrees leftHeading, DirectionDegrees rightHeading)
	{
		boolean result = false;
		if (leftHeading.lessThan(rightHeading))
			result = value >= leftHeading.value && value <= rightHeading.value;
		else
			result = (value >= leftHeading.value && value <= 360) || (value >= 0 && value <= rightHeading.value);
		return result;
	}

	/**
	 * Returns this direction as direction in radians
	 * 
	 * @return direction in radians
	 */
	public DirectionRadians toRadians()
	{
		return new DirectionRadians(Math.toRadians(value));
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Degrees;
	}

	@Override
	public java.lang.String toString()
	{
		return java.lang.String.format("DirectionDegrees [value=%s(%s), units=%s]", value, Math.toRadians(value), units.symbol);
	}
}
