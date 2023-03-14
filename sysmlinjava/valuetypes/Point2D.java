package sysmlinjava.valuetypes;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.io.Serializable;
import sysmlinjava.annotations.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the cartesian coordinate in two dimensional
 * euclidean space in terms of an x and y coordinates expressed as Java
 * {@code double} values<br>
 * <p>
 * <b>Note:</b> as an {@code ObservableValue} this value type can be "observed"
 * by other objects. {@code ValueObserver}s call the {@code addValueObserver()}
 * operation to be notified (called-back) by the {@code Point2D} object of any
 * change in its value. This notification will only occur if and when the
 * {@code Point2D} {@code value} is changed by a call to the {@code setValue()}
 * operation. So, while the {@code Point2D.value} is publicly accessible and can
 * be changed by direct assignment, the {@code setValue()} operation must be
 * used if {@code ValueObserver}s are to be automatically notified of any
 * change.
 * 
 * @author ModelerOne
 *
 */
public class Point2D extends SysMLValueType implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = 790350123208690812L;

	/**
	 * x-value of the Point2D
	 */
	public double xValue;
	/**
	 * y-value of the Point2D
	 */
	public double yValue;
	/**
	 * Whether or not values are empty/not yet set
	 */
	public boolean isEmpty;

	/**
	 * Constructor for empty values
	 */
	public Point2D()
	{
		super();
		this.isEmpty = true;
		this.createUnits();
	}

	/**
	 * Minimal constructor specifying the point's x and y values. Units are assumed
	 * to be simple Point units.
	 * 
	 * @param xValue double value of the Point2D's x corrdinate
	 * @param yValue double value of the Point2D's y corrdinate
	 */
	public Point2D(double xValue, double yValue)
	{
		super();
		this.xValue = xValue;
		this.yValue = yValue;
		this.isEmpty = false;
		this.createUnits();
	}

	/**
	 * Copy constructor
	 * 
	 * @param copied instance to be copied
	 */
	public Point2D(Point2D copied)
	{
		super();
		this.xValue = copied.xValue;
		this.yValue = copied.yValue;
		this.isEmpty = false;
	}

	/**
	 * Returns whether or not values are empty/not yet set
	 * 
	 * @return true if point's x and y values are missing/not yet set, false
	 *         otherwise
	 */
	public boolean isEmpty()
	{
		return isEmpty;
	}

	/**
	 * Sets values to specified values and notifies all change observers
	 * 
	 * @param xValue point's x value
	 * @param yValue point's y value
	 */
	public void setValue(double xValue, double yValue)
	{
		this.xValue = xValue;
		this.yValue = yValue;
		notifyValueChangeObservers();
	}

	/**
	 * Sets x and y values to values of specified point and notifies all change
	 * observers
	 * 
	 * @param point point whose values are to be used
	 */
	public void setValue(Point2D point)
	{
		setValue(point.xValue, point.yValue);
	}

	/**
	 * Returns the value of X
	 * 
	 * @return value of X
	 */
	public double getXValue()
	{
		return xValue;
	}

	/**
	 * Returns the value of Y
	 * 
	 * @return value of Y
	 */
	public double getYValue()
	{
		return yValue;
	}

	/**
	 * Adds the specified points x and y values to the current points x and y values
	 * 
	 * @param value values to be added
	 */
	public void add(Point2D value)
	{
		xValue += value.xValue;
		yValue += value.yValue;
	}

	/**
	 * Scales (multiplies) this point by the specified value
	 * 
	 * @param multipliedBy value to multiply this by
	 */
	public void scale(double multipliedBy)
	{
		xValue *= multipliedBy;
		yValue *= multipliedBy;
	}

	/**
	 * Returns whether or not this point is within rectangle having specifed
	 * upper-right and lower-left corners where right/left is x and upper/lower is
	 * y.
	 * 
	 * @param upperRightCorner    point of rectangles upper right corner
	 * @param lowerLeftCorner     point of rectangles lower left corner
	 * @param upperRightExclusive whether point is to be exclusively inside the
	 *                            upper right rectangle bounds, i.e. not on the
	 *                            bounds specified by the upper right corner
	 * @param lowerLeftExclusive  whether point is to be exclusively inside the
	 *                            lower left rectangle bounds, i.e. not on the
	 *                            bounds specified by the lower left corner
	 * 
	 * @return whether this point is in the rectangle
	 */
	public boolean isWithinRectangle(Point2D upperRightCorner, Point2D lowerLeftCorner, boolean upperRightExclusive, boolean lowerLeftExclusive)
	{
		if (upperRightExclusive)
			if (lowerLeftExclusive)
				return xValue < upperRightCorner.xValue && xValue > lowerLeftCorner.xValue && yValue < upperRightCorner.yValue && yValue > lowerLeftCorner.yValue;
			else
				return xValue < upperRightCorner.xValue && xValue >= lowerLeftCorner.xValue && yValue < upperRightCorner.yValue && yValue >= lowerLeftCorner.yValue;
		else if (lowerLeftExclusive)
			return xValue <= upperRightCorner.xValue && xValue > lowerLeftCorner.xValue && yValue <= upperRightCorner.yValue && yValue > lowerLeftCorner.yValue;
		else
			return xValue <= upperRightCorner.xValue && xValue >= lowerLeftCorner.xValue && yValue <= upperRightCorner.yValue && yValue >= lowerLeftCorner.yValue;
	}

	/**
	 * Constant value for 90 degrees in radians
	 */
	static final double rad90 = 0.5 * PI;
	/**
	 * Constant value for 180 degrees in radians
	 */
	static final double rad180 = 1.0 * PI;
	/**
	 * Constant value for 270 degrees in radians
	 */
	static final double rad270 = 1.5 * PI;
	/**
	 * Constant value for 360 degrees in radians
	 */
	static final double rad360 = 2.0 * PI;

	/**
	 * Returns a new point that is this point moved at the specified direction for
	 * the specified distance (length).
	 * 
	 * @param lengthNumeric    numeric (no units of) length of vector
	 * @param directionRadians radian direction of vector
	 * 
	 * @return point for this point moved specified length and direction
	 */
	@Operation
	public Point2D moved(double lengthNumeric, double directionRadians)
	{
		double xDelta = 0;
		double yDelta = 0;

		if (directionRadians < rad90)
		{
			xDelta = cos(rad90 - directionRadians) * lengthNumeric;
			yDelta = sin(rad90 - directionRadians) * lengthNumeric;
		}
		else if (directionRadians < rad180)
		{
			xDelta = sin(rad180 - directionRadians) * lengthNumeric;
			yDelta = -(cos(rad180 - directionRadians) * lengthNumeric);
		}
		else if (directionRadians < rad270)
		{
			xDelta = -(cos(rad270 - directionRadians) * lengthNumeric);
			yDelta = -(sin(rad270 - directionRadians) * lengthNumeric);
		}
		else if (directionRadians < rad360)
		{
			xDelta = -(sin(rad360 - directionRadians) * lengthNumeric);
			yDelta = cos(rad360 - directionRadians) * lengthNumeric;
		}
		return new Point2D(xValue + xDelta, yValue + yDelta);
	}

	/**
	 * Return an instance that is this multiplied by the specified value
	 * 
	 * @param multipliedBy value to multiply this by
	 * @return instance that is this multiplied by the specified value
	 */
	public Point2D scaled(double multipliedBy)
	{
		return new Point2D(xValue * multipliedBy, yValue * multipliedBy);
	}

	/**
	 * Returns instance with specified values
	 * 
	 * @param xValue initial x value
	 * @param yValue initial y value
	 * @return instance with initial x, y values
	 */
	public static Point2D of(double xValue, double yValue)
	{
		return new Point2D(xValue, yValue);
	}

	/**
	 * Returns instance with an empty point
	 * 
	 * @return instance with an empty point
	 */
	public static Point2D empty()
	{
		return new Point2D();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Point;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Point2D [xValue=");
		builder.append(xValue);
		builder.append(", yValue=");
		builder.append(yValue);
		builder.append(", isEmpty=");
		builder.append(isEmpty);
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
