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
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the SysML String implemented with a standard Java
 * String.
 * <p>
 * Note that the {@code String} name clashes with the {@code java.lang.String}
 * name. Therefore, this SysMLinJava version uses the double S spelling.
 * 
 * @author ModelerOne
 *
 */
@SuppressWarnings("javadoc")
public class SString extends SysMLAttributeType
{
	/**
	 * Attribute for the value of the string as a java String type
	 */
	@Attribute
	public String value;

	/**
	 * Constructor
	 * 
	 * @param value java string to be used as initial value
	 */
	public SString(String value)
	{
		super();
		this.value = value;
	}

	/**
	 * Constructor - copied
	 * 
	 * @param copied instance to be used for initial value of copuy
	 */
	public SString(SString copied)
	{
		super(copied);
		this.value = copied.value;
	}

	/**
	 * Returns the java string value of this instance
	 * 
	 * @return value as a java string
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets this value to the specified java value
	 * 
	 * @param value java string value to be set as this value
	 */
	public void setValue(String value)
	{
		this.value = value;
		notifyAttributeObservers();
	}

	@Operation
	public boolean equals(SString string)
	{
		return value.equals(string.value);
	}

	/**
	 * Returns new instance that is value of the specified java string
	 * 
	 * @param string java string value used as initial value of new instance
	 * @return new instance that is value of the specified java string
	 */
	public static SString valueOf(String string)
	{
		return new SString(string);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new SString(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Characters;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SString [value=");
		builder.append(value);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}