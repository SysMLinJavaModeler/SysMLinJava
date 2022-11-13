package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a geospatial rectangle in terms of a upper left
 * and a lower right {@code PointGeospatial}. Rectangle assumes its east and
 * west edges lie on great circles.
 * 
 * @author ModelerOne
 *
 */
public class RectangleGeospatial extends SysMLValueType
{
	/**
	 * Rectangle's upper left position
	 */
	@Attribute
	public PointGeospatial upperLeft;
	/**
	 * Rectangle's lower right left position
	 */
	@Attribute
	public PointGeospatial lowerRight;

	/**
	 * Constructor - initial value
	 * 
	 * @param upperLeft  point value for the upper left corner of this geospatial
	 *                   rectangle
	 * @param lowerRight point value for the lower right corner of this geospatial
	 *                   rectangle
	 */
	public RectangleGeospatial(PointGeospatial upperLeft, PointGeospatial lowerRight)
	{
		super();
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied instance whose values are to be used for the initial values of
	 *               this copy
	 */
	public RectangleGeospatial(RectangleGeospatial copied)
	{
		super(copied);
		this.upperLeft = new PointGeospatial(copied.upperLeft);
		this.lowerRight = new PointGeospatial(copied.lowerRight);
	}

	/**
	 * Sets the value of the rectangle to specified points
	 * 
	 * @param upperLeft  point for upper left corner of the rectangle
	 * @param lowerRight point for lower right corner of the rectangle
	 */
	public void setValue(PointGeospatial upperLeft, PointGeospatial lowerRight)
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
	public void setValue(RectangleGeospatial rectangle)
	{
		setValue(rectangle.upperLeft, rectangle.lowerRight);
		notifyValueChangeObservers();
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
