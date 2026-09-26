/*
 * Copyright (C) 2026 SysMLinJava, LLC. This file is part of the SysMLinJava
 * framework. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.metadata;

import java.util.HashMap;
import java.util.Map;

import sysmlinjava.items.SysMLItem;
import sysmlinjava.javaannotations.metadata.NameValueList;

/**
 * SysMLRepresentation of the basic SysML metadata item. This abstract class is
 * the base of all metadata items.
 */
public abstract class SysMLMetadata extends SysMLItem
{
	/**
	 * Map of the name-value pairs that specify the metadata as names and values.
	 */
	@NameValueList
	Map<String, String> nameValueList;

	/**
	 * Constructor for name and ID for use by extensions of the metadata class
	 * 
	 * @param name unique name
	 * @param id   unique ID
	 */
	protected SysMLMetadata(String name, Long id)
	{
		super(name, id);
		nameValueList = new HashMap<>();
	}

	/**
	 * Creates the feature name-value map of any metadata that are specified as
	 * name-value. An example follows.
	 * 
	 * <pre>
			:
		protected void createNameValueList()
		{
			nameValueList.put("tolerance", "15mm");
			nameValueList.put("source", "Static inventory");
		}
			:
	 * </pre>
	 */
	protected void createNameValueList()
	{
	}

	/**
	 * Name of method to create metadata's name-values list, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createNameValueListMethodName = "createNameValueList";
}
