package sysmlinjava.valuetypes;

import java.io.Serializable;
import java.util.Arrays;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type of a collection (array) of
 * {@code PositionGeospatial}s, i.e. geodesic coordinates in geo-space, that
 * make up a geodesic poly-line - a line defined by an array of geodesic
 * coordinates.
 * <p>
 * <b>Note:</b> as an {@code ObservableValue} this value type can be "observed"
 * by other objects. {@code ValueObserver}s call the {@code addValueObserver()}
 * operation to be notified (called-back) by the {@code PositionsGeospatial}
 * object of any change in its value. This notification will only occur if and
 * when the {@code PositionsGeospatial} {@code value} is changed by a call to
 * the {@code setValue()} operation. So, while the
 * {@code PositionsGeospatial.value} field is publicly accessible and can be
 * changed by direct assignment, only invocation of the {@code setValue()}
 * operation will cause the {@code ValueObserver}s to be automatically notified
 * of any change. Of course, changes to the value array can be made directly and
 * the {@code notifyValueChangeObservers()} method can be directly invoked from
 * outside this class to notify {@code ValueObserver}s of the change.
 * 
 * @author ModelerOne
 *
 */
public class PolylineGeospatial extends SysMLValueType implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 790350123208690812L;

	/**
	 * Current value of the array of positions
	 */
	@Attribute
	public PointGeospatial[] value;

	/**
	 * No-args constructor leaving value as null, not initialized. Units are assumed
	 * to be simple {@code Point} units.
	 */
	public PolylineGeospatial()
	{
		super();
	}

	/**
	 * Minimal constructor specifying the position values. Units are assumed to be
	 * simple {@code Point} units.
	 * 
	 * @param value array of positions. At least 2 positions must be provided.
	 */
	public PolylineGeospatial(PointGeospatial[] value)
	{
		super();
		this.value = value;
		this.createUnits();
	}

	/**
	 * Sets an indexed position to specified values and notifies all change
	 * observers
	 * 
	 * @param index index into array of points
	 * @param point point to be inserted at index
	 */
	public void setValue(int index, PointGeospatial point)
	{
		if (index < value.length)
		{
			value[index] = point;
			notifyValueChangeObservers();
		}
	}

	/**
	 * Sets the array of positions to the specifed {@code PositionsGeospatial}s
	 * array of positions and notifies all change observers
	 * 
	 * @param positions another {@code PositionsGeospatial} whose positions are to
	 *                  be used as the value of this {@code PositionsGeospatial}
	 */
	public void setValue(PolylineGeospatial positions)
	{
		value = positions.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets values to the specified array of positions and notifies all change
	 * observers
	 * 
	 * @param positions an array of {@code PositionGeospatial}s that are to be used
	 *                  as the value of this {@code PositionsGeospatial}
	 */
	public void setValue(PointGeospatial[] positions)
	{
		value = positions;
		notifyValueChangeObservers();
	}

	/**
	 * Returns the position at the specified index in the array of positions
	 * 
	 * @param index index in array of positions
	 * @return position at index
	 */
	public PointGeospatial getValue(int index)
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
		return String.format("PositionsGeospatial [value=%s]", Arrays.toString(value));
	}
}
