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
package sysmlinjava.views.htmldisplay;

import java.io.Serializable;

/**
 * Container for HTML as a {@code String}
 * 
 * @author ModelerOne
 *
 */
public class HTMLString implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -7567118199087274202L;
	/**
	 * String containing the HTML code
	 */
	public String html;

	/**
	 * Constructor
	 * 
	 * @param htmlString string containing the HTML code
	 */
	public HTMLString(String htmlString)
	{
		super();
		this.html = htmlString;
	}

	/**
	 * Returns the HTML string formatted for output as a log message string
	 * 
	 * @return string for output in log message
	 */
	public String logString()
	{
		return String.format("[HTML]%n%s", html);
	}

	@Override
	public String toString()
	{
		return String.format("HTMLString [htmlString=%n%s]", html);
	}
}