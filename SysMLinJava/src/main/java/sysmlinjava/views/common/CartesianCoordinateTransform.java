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
package sysmlinjava.views.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import sysmlinjava.attributetypes.Point2D;
import sysmlinjava.attributetypes.Polyline2D;

/**
 * Transform operations for specified translation and scale values
 * 
 * @author ModelerOne
 *
 */
public class CartesianCoordinateTransform implements Serializable
{
	/** Serial version id */
	private static final long serialVersionUID = 7445686295101109355L;

	/**
	 * Optional value for translation of coordinate
	 */
	Optional<Double> translated;
	/**
	 * Optional value for scaling of coordinate
	 */
	Optional<Double> scaled;
	/**
	 * Optional value for inverting the y axis
	 */
	Optional<Double> yInverted;

	/**
	 * Constructor
	 * 
	 * @param translated Optional value for translation of coordinates
	 * @param scaled     Optional value for scaling of coordinates
	 * @param yInverted  Optional value for inverting y axis of coordinates
	 */
	public CartesianCoordinateTransform(Optional<Double> translated, Optional<Double> scaled, Optional<Double> yInverted)
	{
		super();
		this.translated = translated;
		this.scaled = scaled;
		this.yInverted = yInverted;
	}

	/**
	 * Returns an XY coordinate that is the translation, scaling, and y inverions of
	 * the specified Point2D
	 * 
	 * @param point2D point to be transformed into XY
	 * @return XY that is transformation of Point2D
	 */
	public XY xyValueOf(Point2D point2D)
	{
		XY result = new XY(point2D);
		if (translated.isPresent())
		{
			result.xValue += translated.get();
			result.yValue += translated.get();
		}
		if (scaled.isPresent())
		{
			result.xValue *= scaled.get();
			result.yValue *= scaled.get();
		}
		if (yInverted.isPresent())
		{
			result.yValue = yInverted.get() - result.yValue;
		}
		return result;
	}

	/**
	 * Returns an array of XY coordinates that are the translation, scaling, and y
	 * inverions of the specified polyline of Point2Ds
	 * 
	 * @param polyline polyline whose points are to be transformed into XY array of
	 *                 XY coordinates that are the transformation.
	 * 
	 * @return XY that is transformation of Point2D
	 */
	public ArrayList<XY> xyListValueOf(Polyline2D polyline)
	{
		ArrayList<XY> result = new ArrayList<>();
		for (Point2D point2D : polyline.value)
			result.add(xyValueOf(point2D));
		return result;
	}
}