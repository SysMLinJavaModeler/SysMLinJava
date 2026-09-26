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
package sysmlinjava.annotations;

import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava's representation of the SysML textual representation. The
 * {@code SysMLTextualRepresentation} is typically declared as a
 * {@code @TextualRepresentation} annotated field in the SysML element to which
 * the text applies and it is instantiated/initialized in the
 * {@code createTextualRepresentations()} method of the element. An example
 * follows:
 * 
 * <pre>{@code
	{
		&#64;TextualRepresentation
		SysMLTextualRepresentation supportingInformationURL;
		
		protected void createTextualRepresentations()
		{
			supportingInformationURL = new SysMLTextualRepresentation("URL", "https://DocServer.com/SystemInfo.pdf");
		}
	}}</pre>
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.common.SysMLAnything#createTextualRepresentations
 */
public class SysMLTextualRepresentation extends SysMLAnything
{
	/**
	 * Language of the text
	 */
	public String language;

	/**
	 * Body of the text
	 */
	public String body;

	/**
	 * Constructor
	 * 
	 * @param language language of the text
	 * @param body     body of the text
	 * @param name     unique name of the text
	 * @param id       unique ID of the text
	 */
	public SysMLTextualRepresentation(String language, String body, String name, Long id)
	{
		super(name, id);
		this.language = language;
		this.body = body;
	}

	/**
	 * Constructor
	 * 
	 * @param language language of the text
	 * @param body     body of the text
	 */
	public SysMLTextualRepresentation(String language, String body)
	{
		super("textRep", 0L);
		this.language = language;
		this.body = body;
	}
}
