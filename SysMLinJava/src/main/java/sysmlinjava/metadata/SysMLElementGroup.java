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
package sysmlinjava.metadata;

import java.util.List;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.Attribute;

/**
 * SysMLinJava's representation of SysML metadata for a specified group of model
 * elements. {@code SysMLElementGroup} is a named and described collection of
 * model elements which are of interest as a group. The
 * {@code SysMLElementGroup} is typically declared as a
 * &#64;{@code ElementGroup} annotated field in the SysML element in which the
 * element group is defined (typically in an element that has access to all of
 * the elements in the group, e.g. a domain, system, or subsystem type of
 * element). The field must be instantiated/initialized in an override of the
 * {@code createElementGroups()} method in an extension of the {@code SysMLItem}
 * in which it is declared.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.common.SysMLAnything#createElementFilters
 */
public final class SysMLElementGroup extends SysMLMetadata
{
	/**
	 * Static instance representing no elements, i.e. an empty group
	 */
	public static final SysMLElementGroup noElements = new SysMLElementGroup(true, List.of(), List.of(), "noElements", 0L);

	/**
	 * Name of the element group
	 */
	@Attribute
	public String name;
	/**
	 * Whether group is inclusion (true) or exclusion (false)
	 */
	@Attribute
	public Boolean inclusion;
	/**
	 * List of the types (classes) that are members of the element group
	 */
	@Attribute
	public List<Class<? extends SysMLAnything>> typeMembers;
	/**
	 * List of the class instances that are members of the element group
	 */
	@Attribute
	public List<? extends SysMLAnything> instanceMembers;

	/**
	 * Constructor
	 * 
	 * @param inclusion       true if all members are to be an inclusion, false if
	 *                        exclusion
	 * @param typeMembers     list of members as types (classes), empty list if none
	 * @param instanceMembers list of members as instances (objects), empty list if
	 *                        none
	 */
	public SysMLElementGroup(boolean inclusion, List<Class<? extends SysMLAnything>> typeMembers, List<? extends SysMLAnything> instanceMembers)
	{
		super("noname", 0L);
		this.inclusion = inclusion;
		this.typeMembers = typeMembers;
		this.instanceMembers = instanceMembers;
	}

	/**
	 * Constructor
	 * 
	 * @param inclusion       true if all members are to be an inclusion, false if
	 *                        exclusion
	 * @param typeMembers     list of members as types (classes), empty list if none
	 * @param instanceMembers list of members as instances (objects), empty list if
	 *                        none
	 * @param name            unique name of the element group
	 * @param id              unique ID of the element group
	 */
	public SysMLElementGroup(boolean inclusion, List<Class<? extends SysMLAnything>> typeMembers, List<? extends SysMLAnything> instanceMembers, String name, Long id)
	{
		super(name, id);
		this.inclusion = inclusion;
		this.typeMembers = typeMembers;
		this.instanceMembers = instanceMembers;
	}
}
