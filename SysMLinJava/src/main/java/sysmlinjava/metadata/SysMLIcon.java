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

import java.util.Optional;

import sysmlinjava.javaannotations.attributes.Attribute;

/**
 * SysMLinJava's representation of the SysML icon metadata. {@code SysMLIcon} is
 * a specialized type of {@code SysMLMetadata} used to annotate a model element
 * with an image to be used to render the element on a diagram and/or a small
 * image to be used as an adornment on a graphical or textual rendering.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLIcon extends SysMLMetadata
{
	/**
	 * A full-sized image that can be used to render the annotated element on a
	 * graphical view, potentially as an alternative to its standard rendering.
	 */
	@Attribute
	public Optional<SysMLImage> imageFull;
	/**
	 * A smaller image that can be used as an adornment on the graphical rendering
	 * of the annotated element or as a marker in a textual rendering.
	 */
	@Attribute
	public Optional<SysMLImage> imageSmall;

	/**
	 * Constructor
	 * @param imageFull  A full-sized image that can be used to render the annotated
	 *                   element on a graphical view, potentially as an alternative
	 *                   to its standard rendering.
	 * @param imageSmall A smaller image that can be used as an adornment on the
	 *                   graphical rendering of the annotated element or as a marker
	 *                   in a textual rendering.
	 */
	public SysMLIcon(Optional<SysMLImage> imageFull, Optional<SysMLImage> imageSmall)
	{
		super("noname", 0L);
		this.imageFull = imageFull;
		this.imageSmall = imageSmall;
	}

	/**
	 * @param imageFull  A full-sized image that can be used to render the annotated
	 *                   element on a graphical view, potentially as an alternative
	 *                   to its standard rendering.
	 * @param imageSmall A smaller image that can be used as an adornment on the
	 *                   graphical rendering of the annotated element or as a marker
	 *                   in a textual rendering.
	 * @param name       unique name
	 * @param id         unique ID
	 */
	public SysMLIcon(Optional<SysMLImage> imageFull, Optional<SysMLImage> imageSmall, String name, Long id)
	{
		super(name, id);
		this.imageFull = imageFull;
		this.imageSmall = imageSmall;
	}

	/**
	 * Name of variable for small image variable, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String smallImageVariableName = "imageSmall";
	/**
	 * Name of variable for full image variable, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String fulllImageVariableName = "imageFull";
}
