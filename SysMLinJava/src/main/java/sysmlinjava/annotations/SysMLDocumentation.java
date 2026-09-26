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
import sysmlinjava.javaannotations.annotations.Documentation;
import sysmlinjava.javaannotations.attributes.Attribute;

/**
 * SysMLinJava's representation of the SysML documentation comment.
 * {@code SysMLDocumentaion} is a simple (final) class for specifying
 * documentation of a model element. Fields of type {@code SysMLDocumentation}
 * should be annotated with the {@code &#64;Documentation} annotation and
 * initialized with the constructor in an override of the
 * {@code createDocumentations()} method. An example follows:
 * 
 * <pre>{@code
	public class MySystemComponent extends SysMLPart
	{
			:
		&#64;Documentation
		public SysMLDocumentation explained;
			:
		protected void createDocumentation()
		{
			explained = new SysMLDocumentation("Component is used to control both sensors and the single actuator");
		}
			:
	}}
 * </pre>
 * 
 * In the case of a documentation instance in a collection class, the instance
 * can be initialized within the field itself, e.g.
 * 
 * <pre>{@code
	public class MySystemRequirements extends SysMLRequirementsCollection
	{
			:
		&#64;Documentation
		public SysMLDocumentation explained = new SysMLDocumentation("Component is used to control both sensors and the single actuator");
			:
	}}
 * </pre>
 * 
 * @author ModelerOne
 * @see Documentation
 * @see sysmlinjava.common.SysMLAnything#createDocumentations
 */
public final class SysMLDocumentation extends SysMLAnything
{
	/**
	 * Value for unspecified documentation
	 */
	public static final SysMLDocumentation notSpecified = new SysMLDocumentation("not specified");

	/**
	 * Locale of the documentation. (Not yet supported by SysMLinJava)
	 */
	@Attribute
	public String locale;

	/**
	 * Text of the documentation. Formatted string to represent structured
	 * documentation of associated model element.
	 */
	@Attribute
	public String text;

	/**
	 * Constructor for the documentation text
	 * 
	 * @param text Text of the documenation
	 */
	public SysMLDocumentation(String text)
	{
		super();
		this.text = text;
	}
}
