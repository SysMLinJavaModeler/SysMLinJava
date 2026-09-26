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
import java.util.List;
import java.util.StringJoiner;

/**
 * Axis for the states to be displayed by a timeing diagram
 * 
 * @author ModelerOne
 *
 */
public class StatesAxis implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8854669832371302383L;

	/**
	 * List of names of the states on the axis
	 */
	public List<String> states;

	/**
	 * Constructor
	 * 
	 * @param states list of names of the states on the axis
	 */
	public StatesAxis(List<String> states)
	{
		super();
		this.states = states;
	}

	@Override
	public String toString()
	{
		StringJoiner joiner = new StringJoiner(", ");
		states.forEach(state -> joiner.add(state));
		return String.format("StatesAxis [states=%s]", joiner.toString());
	}
}