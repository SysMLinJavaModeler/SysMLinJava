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
import sysmlinjava.javaannotations.attributes.Attribute;

/**
 * SysMLinJava's representation of the SysML comment. The {@code SysMLComment}
 * is typically declared in a {@code @Comment} annotated field in the SysML
 * attribute type, item, part, port, or other element to which the comment
 * applies. It is instantiated/initialized with the comment text in the
 * {@code createComments()} method of the element.
 * <h2>Comment "libraries"</h2> Comments can also be declared as static
 * variables in extensions of the {@code SysMLAnnotationsCollection} that serve
 * as containers of comments and other annotatiuns. These comments can then
 * serve as a set of reusable comments that can be referenced by multiple
 * elements that need the same comment.
 * 
 * @author ModelerOne
 * @see sysmlinjava.common.SysMLAnything#createComments
 */
public final class SysMLComment extends SysMLAnything
{
	/**
	 * Locale of the comment. (Not yet supported by SysMLinJava)
	 */
	@Attribute
	public String locale;

	/**
	 * Text of the comment.
	 */
	@Attribute
	public String text;

	/**
	 * Constructor for the comment text
	 * 
	 * @param text Text of the comment
	 */
	public SysMLComment(String text)
	{
		super();
		this.text = text;
	}
}
