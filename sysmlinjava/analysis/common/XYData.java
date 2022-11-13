package sysmlinjava.analysis.common;

import java.io.Serializable;

/**
 * Data for a point in a 2D cartesian coordinate system in terms of x and y
 * points on the X and Y axes
 * 
 * @author ModelerOne
 *
 */
public class XYData implements Serializable
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
	public XYData()
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
	public XYData(double xValue, double yValue)
	{
		super();
		this.xValue = xValue;
		this.yValue = yValue;
		isEmpty = false;
	}

	/**
	 * Constructor for copy of another instance
	 * 
	 * @param copyied data to be copied
	 */
	public XYData(XYData copyied)
	{
		xValue = copyied.xValue;
		yValue = copyied.yValue;
		isEmpty = false;
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
	public XYData offset(int xDelta, int yDelta)
	{
		return new XYData(xValue + xDelta, yValue + yDelta);
	}

	/**
	 * Returns whether this instance is equal to (same {@code xValue}s and
	 * {@code yValue}s) specified other instance
	 * 
	 * @param other instance to be compared with
	 * @return true if this instance is equal to other instance
	 */
	public boolean equals(XYData other)
	{
		return xValue == other.xValue && yValue == other.yValue;
	}

	@Override
	public String toString()
	{
		return String.format("XYData [xValue=%s, yValue=%s]", xValue, yValue);
	}

}