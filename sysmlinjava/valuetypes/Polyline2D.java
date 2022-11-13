package sysmlinjava.valuetypes;

import java.io.Serializable;
import java.util.Arrays;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for a collection (array) of {@code Point2D}s, i.e. of
 * cartesian coordinates in two dimensional euclidean space.
 * <p>
 * <b>Note:</b> as an {@code ObservableValue} this value type can be "observed"
 * by other objects. {@code ValueObserver}s call the {@code addValueObserver()}
 * operation to be notified (called-back) by the {@code Point2D} object of any
 * change in its value. This notification will only occur if and when the
 * {@code Point2D} {@code value} is changed by a call to the {@code setValue()}
 * operation. So, while the {@code Points2D.value} is publicly accessible and
 * can be changed by direct assignment, either of the {@code setValue()}
 * operations must be used if {@code ValueObserver}s are to be automatically
 * notified of any change. Of course, changes to the value array can be made
 * directly and the {@code notifyValueChangeObservers()} method can be directly
 * invoked from outside this class to notify {@code ValueObserver}s of the
 * change.
 * 
 * @author ModelerOne
 *
 */
public class Polyline2D extends SysMLValueType implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 790350123208690812L;

	/**
	 * Current value of the Points2D
	 */
	@Attribute
	public Point2D[] value;

	/**
	 * No-args constructor leaving value as null, not initialized. Units are assumed
	 * to be simple {@code Point} units.
	 */
	public Polyline2D()
	{
		super();
	}

	/**
	 * Minimal constructor specifying the point values. Units are assumed to be
	 * simple {@code Point} units.
	 * 
	 * @param value array of 2D points
	 */
	public Polyline2D(Point2D[] value)
	{
		super();
		this.value = value;
		this.createUnits();
	}

	/**
	 * Constructor for copy
	 * 
	 * @param copied instance to be copied
	 */
	public Polyline2D(Polyline2D copied)
	{
		super(copied);
		value = new Point2D[copied.value.length];
		for (int i = 0; i < copied.value.length; i++)
			value[i] = new Point2D(copied.value[i]);
	}

	/**
	 * Sets values to specified values and notifies all change observers
	 * 
	 * @param index index of point in array
	 * @param point point to be set at index
	 */
	public void setValue(int index, Point2D point)
	{
		if (index < value.length)
		{
			value[index] = point;
			notifyValueChangeObservers();
		}
	}

	/**
	 * Sets values to values of specified point and notifies all change observers
	 * 
	 * @param points another {@code Points2D} whose points to copy to this
	 *               {@code Points2D}
	 */
	public void setValue(Polyline2D points)
	{
		value = points.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets values to points and notifies all change observers
	 * 
	 * @param points an array of {@code Point2D} to copy to this {@code Points2D}
	 */
	public void setValue(Point2D[] points)
	{
		value = points;
		notifyValueChangeObservers();
	}

	/**
	 * Returns point at specified index in the array of points
	 * 
	 * @param index index of the point
	 * @return point at the index
	 */
	public Point2D getValue(int index)
	{
		return value[index];
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Point;
	}

	@Override
	public String toString()
	{
		return String.format("Polyline2D [value=%s]", Arrays.toString(value));
	}
}
