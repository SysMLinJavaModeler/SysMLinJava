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
package sysmlinjava.views.barcharts;

import java.io.Serializable;

/**
 * Data for a single category value in a bar chart display
 * 
 * @author ModelerOne
 *
 */
public class CatYData implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 6019714086065572412L;

	/**
	 * Category of the data
	 */
	public String category;
	/**
	 * Category (y) value
	 */
	public double yValue;

	/**
	 * Constructor
	 * 
	 * @param category category of the data
	 * @param yValue   category (y) value
	 */
	public CatYData(String category, double yValue)
	{
		super();
		this.category = category;
		this.yValue = yValue;
	}

	@Override
	public String toString()
	{
		return String.format("CatYData [category=%s, yValue=%s]", category, yValue);
	}
}