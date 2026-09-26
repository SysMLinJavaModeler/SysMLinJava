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
import java.time.Instant;

/**
 * Definition of a state transition to be displayed on a timing diagram
 * 
 * @author ModelerOne
 *
 */
public class StateTransitionTiming implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -7567118199087274202L;

	/**
	 * ID of the timing diagram to display the transition
	 */
	public String timingDiagramID;
	/**
	 * Name of the state transitioned from
	 */
	public String fromState;
	/**
	 * Name of the state transitioned to
	 */
	public String toState;
	/**
	 * Time of the transition
	 */
	public Instant time;

	/**
	 * Constructor
	 * 
	 * @param timingDiagramID ID of the timing diagram to display the transition
	 * @param fromState       name of the state transitioned from
	 * @param toState         name of the state transitioned to
	 * @param time            time of the transition
	 */
	public StateTransitionTiming(String timingDiagramID, String fromState, String toState, Instant time)
	{
		super();
		this.timingDiagramID = timingDiagramID;
		this.fromState = fromState;
		this.toState = toState;
		this.time = time;
	}

	/**
	 * Determines whether this data if valid for the diagram
	 * 
	 * @param diagram definition of the diagram
	 * @return if valid true, false otherwise
	 */
	public boolean isValidFor(TimingDiagramDefinition diagram)
	{
		boolean result = true;
		return result;
	}

	/**
	 * Log-type representation of the transition timing data
	 * 
	 * @return log-type string
	 * 
	 */
	public String toLogString()
	{
		return String.format("[TD] timingDiagramID=%s, fromState=%s, toState=%s, time=%s]", timingDiagramID, fromState, toState, time);
	}

	@Override
	public String toString()
	{
		return String.format("StateTransitionTiming [timingDiagramID=%s, fromState=%s, toState=%s, time=%s]", timingDiagramID, fromState, toState, time);
	}
}