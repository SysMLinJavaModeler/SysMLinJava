package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a hexahedron (6-sides) in terms of its height,
 * width, and length in meters
 * 
 * @author ModelerOne
 *
 */
public class HexahedronMeters extends SysMLValueType
{
	/**
	 * Height of the hexahedron
	 */
	@Attribute
	public double height;
	/**
	 * Width of the hexahedron
	 */
	@Attribute
	public double width;
	/**
	 * Length of the hexahedron
	 */
	@Attribute
	public double length;

	/**
	 * Constructor for all attributes
	 * 
	 * @param height height of the hexahedron
	 * @param width  width of the hexahedron
	 * @param length length of the hexahedron
	 */
	public HexahedronMeters(double height, double width, double length)
	{
		super();
		this.height = height;
		this.width = width;
		this.length = length;
	}

	/**
	 * Returns the volume of the hexahedron
	 * 
	 * @return volume in cubic meters
	 */
	public VolumeMetersCubic volumeOf()
	{
		return new VolumeMetersCubic(height * width * length);
	}

	/**
	 * Returns the hexahedron's height
	 * 
	 * @return height (in meters)
	 */
	public double getHeight()
	{
		return height;
	}

	/**
	 * Sets the hexahedron's height
	 * 
	 * @param height double value in meters to use as height
	 */
	public void setHeight(double height)
	{
		this.height = height;
		notifyValueChangeObservers();
	}

	/**
	 * Returns the hexahedron's width
	 * 
	 * @return width (in meters)
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * Sets the hexahedron's width
	 * 
	 * @param width double value in meters to use as width
	 */
	public void setWidth(double width)
	{
		this.width = width;
		notifyValueChangeObservers();
	}

	/**
	 * Returns the hexahedron's length
	 * 
	 * @return length (in meters)
	 */
	public double getLength()
	{
		return length;
	}

	/**
	 * Sets the hexahedron's length
	 * 
	 * @param length double value in meters to use as length
	 */
	public void setLength(double length)
	{
		this.length = length;
		notifyValueChangeObservers();
	}

	/**
	 * Sets the attributes of the hexahedron
	 * 
	 * @param height height of the hexahedron
	 * @param width  width of the hexahedron
	 * @param length length of the hexahedron
	 */
	public void setHeightWidthLength(double height, double width, double length)
	{
		this.height = height;
		this.width = width;
		this.length = length;
		notifyValueChangeObservers();
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Meters;
	}

}
