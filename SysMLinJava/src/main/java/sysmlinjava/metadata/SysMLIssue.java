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

/**
 * SysMLinJava's representation of the SysML issue. {@code SysMLIssue} is a
 * specialized type of {@code SysMLMetadata}. It consists of text that specifies
 * the issue/problem associated with the SysML model element in which it is
 * declared.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLIssue extends SysMLMetadata
{
	/**
	 * Text specification of the issue
	 */
	public String text;

	/**
	 * Constructor - initial value
	 * 
	 * @param text text specification of the issue
	 */
	public SysMLIssue(String text)
	{
		super("noname", 0L);
		this.text = text;
	}

	/**
	 * Constructor - initial value
	 * 
	 * @param text text specification of the issue
	 * @param name unique name of the issue
	 * @param id   unique id of the issue
	 */
	public SysMLIssue(String text, String name, Long id)
	{
		super(name, id);
		this.text = text;
	}
}
