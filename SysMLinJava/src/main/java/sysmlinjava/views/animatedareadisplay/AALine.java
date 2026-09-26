/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.views.animatedareadisplay;

import java.io.Serializable;
import java.util.ArrayList;

import sysmlinjava.views.common.ColorEnum;
import sysmlinjava.views.common.XY;

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
	public AALine(String uid, AnimatedAreaActionEnum action, ArrayList<XY> points, ColorEnum color, Integer width, Integer zOrder)
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
		case none:
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
	public void update(AnimatedAreaActionEnum action, ArrayList<XY> points, ColorEnum color, Integer width, Integer zOrder)
	{
		this.action = action != null ? action : AnimatedAreaActionEnum.none;
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
