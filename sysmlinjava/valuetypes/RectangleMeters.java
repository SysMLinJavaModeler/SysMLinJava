package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.annotations.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a rectangle whose sides measure in meters.
 * 
 * @author ModelerOne
 *
 */
public class RectangleMeters extends SysMLValueType
{
	/**
	 * Attribute for rectangle length
	 */
	@Attribute
	public double length;
	/**
	 * Attribute for rectangle width
	 */
	@Attribute
	public double width;

	/**
	 * Constructor - all initial values
	 * 
	 * @param length double value for length
	 * @param width  double value for width
	 */
	public RectangleMeters(double length, double width)
	{
		super();
		this.length = length;
		this.width = width;
	}

	/**
	 * Returns the area of the rectangle
	 * 
	 * @return area of the rectangle
	 */
	@Operation
	public AreaMetersSquare areaMetersSquare()
	{
		return new AreaMetersSquare(width * length);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Meters;
	}
}
