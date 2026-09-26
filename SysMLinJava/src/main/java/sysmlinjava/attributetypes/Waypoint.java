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
package sysmlinjava.attributetypes;

import sysmlinjava.javaannotations.attributes.Attribute;
import sysmlinjava.javaannotations.attributes.Operation;

/**
 * SysMLinJava value type for a waypoint. A point in 2D space and time typically
 * used as a point "along the way" of a navigational plan or route
 * 
 * @author ModelerOne
 *
 */
public class Waypoint extends Point2D
{
	/**
	 * Serial version identifier
	 */
	private static final long serialVersionUID = 6469686008432453465L;

	/**
	 * Attribute for the waypoint's time, i.e. a time to be associated with the
	 * point, such as planned or actual time at the waypoint
	 */
	@Attribute
	public InstantMilliseconds time;

	/**
	 * Constructor - default values
	 */
	public Waypoint()
	{
		super();
		time = InstantMilliseconds.now();
	}

	/**
	 * Constructor
	 * 
	 * @param xValue double value for x
	 * @param yValue double value for y
	 * @param time   InstantMilliseconds value for time
	 */
	public Waypoint(double xValue, double yValue, InstantMilliseconds time)
	{
		super(xValue, yValue);
		this.time = new InstantMilliseconds(time);
	}

	/**
	 * Constructor
	 * 
	 * @param copied waypoint which is to be copied to this waypoint
	 */
	public Waypoint(Waypoint copied)
	{
		super(copied);
		this.time = new InstantMilliseconds(copied.time);
	}

	/**
	 * Constructor
	 * 
	 * @param point 2D point of the waypoint
	 * @param time  time associated with the point
	 */
	public Waypoint(Point2D point, InstantMilliseconds time)
	{
		super(point);
		this.time = new InstantMilliseconds(time);
	}

	/**
	 * Sets the waypoint to the values specified
	 * 
	 * @param xValue double value for x
	 * @param yValue double value for y
	 * @param time   InstantMilliseconds value for time
	 */
	@Operation
	public void setValue(double xValue, double yValue, InstantMilliseconds time)
	{
		this.xValue = xValue;
		this.yValue = yValue;
		this.time.value = time.value;
		notifyAttributeObservers();
	}

	/**
	 * Sets the waypoint values as a copy of the specified waypoint
	 * 
	 * @param copied waypoint which is to be copied
	 */
	@Operation
	public void setValue(Waypoint copied)
	{
		setValue(copied.xValue, copied.yValue, copied.time);
	}

	/**
	 * Returns a new waypoint that is this the movement from this waypoint at the
	 * specified velocity for the specified duration. Moved value will optionally
	 * reflect random errors in speed and direction IAW settings of probability
	 * distributions set for heading and/or speed.
	 * 
	 * @see sysmlinjava.probability.SysMLProbabilityDistribution
	 * 
	 * @param atVelocity  velocity at which this waypoint is moved
	 * @param forDuration duration of the move
	 * @return waypoint for movement from this waypoint at specified velocity and
	 *         duration
	 */
	@Operation
	public Waypoint moved(VelocityMetersPerSecondRadians atVelocity, DurationSeconds forDuration)
	{
		double distanceError = atVelocity.probabilityDistribution.isPresent() ? atVelocity.probabilityDistribution.get().nextRandom().value : 0.0;
		double directionError = atVelocity.heading.probabilityDistribution.isPresent() ? atVelocity.heading.probabilityDistribution.get().nextRandom().value : 0.0;
		double distance = atVelocity.value * forDuration.value + distanceError;
		double direction = (atVelocity.heading.value + directionError) % rad360;
		return new Waypoint(moved(distance, direction), time.add(forDuration));
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new Waypoint(xValue, yValue, new InstantMilliseconds(time.millis()));
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Waypoint [xValue=");
		builder.append(xValue);
		builder.append(", yValue=");
		builder.append(yValue);
		builder.append(", units=");
		builder.append(units);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", time=");
		builder.append(time);
		builder.append("]");
		return builder.toString();
	}
}
