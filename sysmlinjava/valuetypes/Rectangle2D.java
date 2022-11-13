package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a 2D rectangle in terms of a upper left and a
 * lower right {@code Point2D}s. Rectangle is assumed to be edge-parallel to
 * cartesian axes.
 * 
 * @author ModelerOne
 *
 */
public class Rectangle2D extends SysMLValueType
{
	/**
	 * Rectangle's upper left point
	 */
	@Attribute
	public Point2D upperLeft;
	/**
	 * Rectangle's lower right left point
	 */
	@Attribute
	public Point2D lowerRight;

	/**
	 * Constructor
	 * 
	 * @param upperLeft  point for upper left corner of the rectangle
	 * @param lowerRight point for lower right corner of the rectangle
	 */
	public Rectangle2D(Point2D upperLeft, Point2D lowerRight)
	{
		super();
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;
	}

	/**
	 * Sets the value of the rectangle to specified points
	 * 
	 * @param upperLeft  point for upper left corner of the rectangle
	 * @param lowerRight point for lower right corner of the rectangle
	 */
	public void setValue(Point2D upperLeft, Point2D lowerRight)
	{
		this.upperLeft.setValue(upperLeft);
		this.lowerRight.setValue(lowerRight);
		notifyValueChangeObservers();
	}

	/**
	 * Sets the value of the rectangle to that of specified other point
	 * 
	 * @param rectangle rectangle whose value is to be set for this value
	 */
	public void setValue(Rectangle2D rectangle)
	{
		setValue(rectangle.upperLeft, rectangle.lowerRight);
		notifyValueChangeObservers();
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied instance whose values are to be used for this copy
	 */
	public Rectangle2D(Rectangle2D copied)
	{
		super(copied);
		this.upperLeft = new Point2D(copied.upperLeft);
		this.lowerRight = new Point2D(copied.lowerRight);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Point;
	}

	@Override
	public String toString()
	{
		return String.format("RectangleGeospatial [name=%s, id=%s, units=%s, upperLeft=%s, lowerRight=%s]", name, id, units, upperLeft, lowerRight);
	}
}
