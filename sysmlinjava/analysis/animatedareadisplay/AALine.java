package sysmlinjava.analysis.animatedareadisplay;

import java.io.Serializable;
import java.util.ArrayList;
import sysmlinjava.analysis.common.ColorEnum;
import sysmlinjava.analysis.common.XY;

/**
 * Representation of a line object in an area display. Contains the parameters
 * needed to display the object as a "polyline" in the display that connects a
 * specified set of x,y points in the area display.
 * 
 * @author ModelerOne
 *
 */
public class AALine extends AAObject implements Serializable
{
	/** Serializable ID*/
	/** Serializable ID*/private static final long serialVersionUID = 4525775059514772367L;

	/**
	 * List of the x,y points to be connected for the line
	 */
	public ArrayList<XY> points;
	/**
	 * Color of the line
	 */
	public ColorEnum color;
	/**
	 * Width (in points) of the line
	 */
	public Integer width;
	/**
	 * Order of the line in the layers of objects in the area display. Lower number
	 * is closer to viewer.
	 */
	public Integer zOrder;

	/**
	 * Constructor for all attributes
	 * 
	 * @param uid    unique identifier
	 * @param action action to be performed on the line
	 * @param points set of x,y points to be connected for the line
	 * @param color  Color of the line
	 * @param width  width, in points, of the line
	 * @param zOrder order of the line in the layers of objects in the area display.
	 *               Lower number is closer to viewer.
	 */
	public AALine(String uid, Action action, ArrayList<XY> points, ColorEnum color, Integer width, Integer zOrder)
	{
		super(uid, action);
		switch (action)
		{
		case create:
			this.points = points != null ? points : new ArrayList<>();
			this.color = color != null ? color : ColorEnum.BLACK;
			this.width = width != null ? width : 2;
			this.zOrder = zOrder != null ? zOrder : 0;
			break;
		case delete:
			break;
		case update:
			this.points = points;
			this.color = color;
			this.width = width;
			this.zOrder = zOrder;
			break;
		default:
			break;

		}
	}

	/**
	 * Updates/changes the line display as specified by parameter values, with value
	 * {@code null} indicating no change
	 * 
	 * @param action action to be performed on the line
	 * @param points set of x,y points to be connected for the line
	 * @param color  Color of the line
	 * @param width  width, in points, of the line
	 * @param zOrder order of the line in the layers of objects in the area display.
	 *               Lower number is closer to viewer.
	 */
	public void update(Action action, ArrayList<XY> points, ColorEnum color, Integer width, Integer zOrder)
	{
		this.action = action != null ? action : Action.none;
		this.points = points;
		this.color = color;
		this.width = width;
		this.zOrder = zOrder;
	}

	@Override
	public String toString()
	{
		return String.format("AALine [uid=%s, action=%s, points=%s, color=%s, width=%s, zOrder=%s, getClass()=%s, hashCode()=%s, toString()=%s]", uid, action, points, color, width, zOrder, getClass(), hashCode(), super.toString());
	}
}
