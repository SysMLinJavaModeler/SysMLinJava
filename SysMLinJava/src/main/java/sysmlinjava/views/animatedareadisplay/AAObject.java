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

/**
 * Base abstract class for representation of an object in an area display.
 * Contains the parameters needed to uniquely identify the object in the display
 * and to specify the action to be taken on the object in the area display.
 * 
 * @author ModelerOne
 *
 *
 */
public abstract class AAObject implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8621836558579485249L;

	/**
	 * Unique identifier for the area display object
	 */
	public String uid;
	/**
	 * Action to be taken on the area display object
	 */
	public AnimatedAreaActionEnum action;

	/**
	 * Constructor
	 * 
	 * @param uid    unique identifier for the area display object
	 * @param action action to be taken on the area display object
	 */
	public AAObject(String uid, AnimatedAreaActionEnum action)
	{
		this.uid = uid != null && !uid.isBlank() ? uid : this.toString();
		this.action = action != null ? action : AnimatedAreaActionEnum.create;
	}
}