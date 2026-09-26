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

import sysmlinjava.common.SysMLDependency;
import sysmlinjava.javaannotations.dependencies.Dependency;

/**
 * SysMLinJava's representation of the SysML refinement. {@code SysMLRefinement}
 * is a specialized type of {@code SysMLMetadata}. It consists of a dependency
 * that specifies the element that is refined by the dependency and the element
 * that is the refinement.
 * 
 * @author ModelerOne
 */
public final class SysMLRefinement extends SysMLMetadata
{
	/**
	 * Dependency that defines the refinement
	 */
	@Dependency
	public SysMLDependency dependency;

	/**
	 * Constructor - initial value
	 * 
	 * @param dependency dependency that defines the refined/refined by elements
	 */
	public SysMLRefinement(SysMLDependency dependency)
	{
		super("noname", 0L);
		this.dependency = dependency;
	}

	/**
	 * Constructor - initial value and name, ID
	 * 
	 * @param dependency dependency that defines the refined/refined by elements
	 * @param name       unique name of the issue
	 * @param id         unique id of the issue
	 */
	public SysMLRefinement(SysMLDependency dependency, String name, Long id)
	{
		super(name, id);
		this.dependency = dependency;
	}
}
