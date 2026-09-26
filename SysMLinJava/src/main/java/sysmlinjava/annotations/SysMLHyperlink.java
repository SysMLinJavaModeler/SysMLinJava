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
package sysmlinjava.annotations;

import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava's implementation of the SysML textual representation for a
 * hyperlink. It typically provides a hyperlink to supporting information that
 * is associated with the SysML element of which it is a member. Fields of
 * variable type {@code SysMLHyperlink} should be annotated with the
 * {@code &#64;Hyperlink} annotation and initialized as done for the
 * {@code SysMLTextualRepresentation}.
 * 
 * @author ModelerOne
 */
public final class SysMLHyperlink extends SysMLAnything
{
	/**
	 * Title of the hyperlink
	 */
	public String title;

	/**
	 * Body of the text
	 */
	public String uri;
	
	/**
	 * Constructor - initial values
	 * 
	 * @param title title of the hyperlink
	 * @param uri   URI (as String) for the hyperlink
	 * @param name  unique name of the hyperlink
	 * @param id    unique ID of the hyperlink
	 */
	public SysMLHyperlink(String title, String uri, String name, Long id)
	{
		super(name, id);
		this.title = title;
		this.uri = uri;
	}

	/**
	 * Constructor - initial values
	 * 
	 * @param title title of the hyperlink
	 * @param uri   URI (as String) for the hyperlink
	 */
	public SysMLHyperlink(String title, String uri)
	{
		super("hyperlink", 0L);
		this.title = title;
		this.uri = uri;
	}

	/**
	 * Returns this title as string
	 * 
	 * @return this title as string
	 */
	public String title()
	{
		return title;
	}

	/**
	 * Returns this URI as string
	 * 
	 * @return this URI as string
	 */
	public String uri()
	{
		return uri;
	}
}
