package sysmlinjava.analysis.common;

import java.io.Serializable;
import sysmlinjava.valuetypes.Point2D;

/**
 * X and Y values for a point in a 2D cartesian coordinate system, i.e. x and y
 * values on the X and Y axes. The directions and ranges of the x and y values
 * is undefined. Use for analysis displays is as needed for the particular
 * display. The {@code CoordinateTransform} class is provided to permit use of
 * this XY coordinate system as a transform of another, e.g. {@code Point2D}
 * coordinate system.
 * <p>
 * For example, a SysMLinJava model might use a typical cartesian coordinate
 * system with some distance per x,y increment and the {@code [0,0]} point at
 * the bottom left of the modeled space. An animated area display uses a pixel
 * coordinate system with the pixel as the increment and the {@code 0,0} pixel
 * at the upper left of the pixel space. The {@code CoordinateTransform} could
 * be used to transform {@code Point2D} positions in the model to {@code XY}
 * pixels in the display.
 * 
 * @see sysmlinjava.analysis.common.CartesianCoordinateTransform
 * 
 * @author ModelerOne
 *
 */
public class XY implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = 6019714086065572412L;

	/**
	 * x value for the point
	 */
	public double xValue;
	/**
	 * y value for the point
	 */
	public double yValue;

	/**
	 * Whether or not values are empty/not yet set
	 */
	public boolean isEmpty;

	/**
	 * Constructor for empty values
	 * 
	 */
	public XY()
	{
		super();
		isEmpty = true;
	}

	/**
	 * Constructor
	 * 
	 * @param xValue point's x axis value
	 * @param yValue point's y axis value
	 */
	public XY(double xValue, double yValue)
	{
		super();
		this.xValue = xValue;
		this.yValue = yValue;
		isEmpty = false;
	}

	/**
	 * Constructor for copy of another instance
	 * 
	 * @param copied instance whose values are to be copied to this
	 */
	public XY(XY copied)
	{
		super();
		xValue = copied.xValue;
		yValue = copied.yValue;
		isEmpty = false;
	}

	/**
	 * Constructor for copy of Point2D instance
	 * 
	 * @param copied point whose values are to be copied to this
	 */
	public XY(Point2D copied)
	{
		this(copied.xValue, copied.yValue);
	}

	/**
	 * Returns whether or not values are empty/not yet set
	 * 
	 * @return true of x and y values are not yet set, false otherwise
	 */
	public boolean isEmpty()
	{
		return isEmpty;
	}

	/**
	 * Returns instance whose x and y values are this x and y values offset by
	 * specified delta values
	 * 
	 * @param xDelta amount to change xValue by
	 * @param yDelta amount to change yValue by
	 * @return instance whose x and y values are this x and y values offset by
	 *         specified delta values
	 */
	public XY offset(int xDelta, int yDelta)
	{
		return new XY(xValue + xDelta, yValue + yDelta);
	}

	/**
	 * Returns whether this instance is equal to (same {@code xValue}s and
	 * {@code yValue}s) specified other instance
	 * 
	 * @param other instance to be compared with
	 * @return true if this instance is equal to other instance
	 */
	public boolean equals(XY other)
	{
		return xValue == other.xValue && yValue == other.yValue;
	}

	@Override
	public String toString()
	{
		return String.format("XY [xValue=%s, yValue=%s]", xValue, yValue);
	}

}