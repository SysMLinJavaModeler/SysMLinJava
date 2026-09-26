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
package sysmlinjava.views.timingdiagrams;

import java.io.Serializable;

/**
 * Definition of the timing diagram, i.e. its states axis and its time axis, and
 * it ID
 * 
 * @author ModelerOne
 *
 */
public class TimingDiagramDefinition implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8945635974734237468L;

	/**
	 * ID of the diagram
	 */
	public String timingDiagramID;
	/**
	 * Axis (vertical) for the states
	 */
	public StatesAxis statesAxis;
	/**
	 * Axis (horizontal) for the time
	 */
	public TimeAxis timeAxis;

	/**
	 * Constructor
	 * 
	 * @param timingDiagramID diagram ID
	 * @param statesAxis      axis for states
	 * @param timeAxis        axis for times
	 */
	public TimingDiagramDefinition(String timingDiagramID, StatesAxis statesAxis, TimeAxis timeAxis)
	{
		super();
		this.timingDiagramID = timingDiagramID;
		this.statesAxis = statesAxis;
		this.timeAxis = timeAxis;
	}

	/**
	 * Log-type representation of the diagram definition
	 * 
	 * @return log-type string
	 */
	public String toLogString()
	{
		return String.format("[TD][timingDiagramID=%s, statesAxis=%s, timeAxis=%s]", timingDiagramID, statesAxis.toString(), timeAxis.toString());
	}

	@Override
	public String toString()
	{
		return String.format("TimingDiagramDefinition [timingDiagramID=%s, statesAxis=%s, timeAxis=%s]", timingDiagramID, statesAxis.toString(), timeAxis.toString());
	}
}
