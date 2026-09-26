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

/**
 * SysMLinJava's representation of the SysMLv1 rationale metadata.
 * {@code SysMLRationale} is a specialized type of {@code SysMLMetadata}. It
 * consists of text that specifies the rationale associated with the SysML model
 * element it is a member of.
 * 
 * @author ModelerOne
 */
public final class SysMLRationale extends SysMLMetadata
{
	/**
	 * Textspecification of the rationale
	 */
	public String text;

	/**
	 * Constructor - initial value
	 * 
	 * @param text text specification of the rationale
	 */
	public SysMLRationale(String text)
	{
		super("noname", 0L);
		this.text = text;
	}

	/**
	 * Constructor - initial value and name, ID
	 * 
	 * @param text text specification of the rationale
	 * @param name unique name of the rationale
	 * @param id   unique id of the rationale
	 */
	public SysMLRationale(String text, String name, Long id)
	{
		super(name, id);
		this.text = text;
	}
}
