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
package sysmlinjava.views;

import java.util.List;

import sysmlinjava.javaannotations.metadata.ElementFilter;
import sysmlinjava.javaannotations.viewpoints.Viewpoint;
import sysmlinjava.javaannotations.views.Exposed;
import sysmlinjava.javaannotations.views.Rendering;
import sysmlinjava.javaannotations.views.View;
import sysmlinjava.metadata.SysMLElementGroup;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.viewpoints.SysMLViewpoint;

/**
 * SysMLinJava representation of the abstract SysML "view". Views must be
 * declared as extensions of this {@code SysMLView} which override the
 * {@code create...} methods to create the types of elements that define the
 * view.
 * 
 * @author ModelerOne
 */
public abstract class SysMLView extends SysMLPart
{
	/**
	 * List of viewpoints that satisfy this view
	 */
	@Viewpoint
	public List<SysMLViewpoint> satisfiedViewpoints;
	/**
	 * List of types of parts that are exposed by this view
	 */
	@Exposed
	public List<Class<? extends SysMLPart>> exposedPartTypes;
	/**
	 * Group of model elements that are filtered out of (excluded from) this view.
	 */
	@ElementFilter
	public SysMLElementGroup filteredOutElements;
	/**
	 * Group of model elements that are filtered into (included in) this view.
	 */
	@ElementFilter
	public SysMLElementGroup filteredInElements;
	/**
	 * List of types of renderings that realize this view
	 */
	@Rendering
	public List<Class<? extends SysMLRendering>> renderings;
	/**
	 * List of types of views that are sub-views (components, parts) of this view
	 */
	@View
	public List<Class<? extends SysMLView>> subViews;

	/**
	 * Constructor for extended types.
	 * 
	 * @param name name of the view
	 * @param id   unique ID for the view
	 */

	public SysMLView(String name, Long id)
	{
		super(name, id);
	}

	/**
	 * Creates the viewpoints that are satisfied by this view. An exampe follows:
	 * 
	 * <pre>{@code
			public class MyView extends SysMLView
			{
					:
				protected void createViewpoints()
				{
					satisfiedViewpoints = List.of(MyViewpointsCollection.architectViewpoint, MyViewpointsCollection.userViewpoint);
				}
			}
			}</pre>
	 */
	protected abstract void createSatisfiedViewpoints();

	/**
	 * Creates the types of parts that are exposed by this view. An example follows:
	 * 
	 * <pre>{@code
			public class MyView extends SysMLView
			{
					:
				protected void createExposedPartTypes()
				{
					exposedPartTypes = List.of(CommsComponent.class, SensorComponent.class);
				}
			}
			}</pre>
	 */
	protected abstract void createExposedPartTypes();

	/**
	 * Creates the element group that represents those model elements that are
	 * included in the view.
	 * 
	 * <pre>{@code
			public class MyView extends SysMLView
			{
					:
				protected void createElemenFilters()
				{
					filteredInElements=new SysMLElementGroup(...)
					filteredOutElements=new SysMLElementGroup(...)
				}
			}
			}</pre>
	 */
	@Override
	protected abstract void createElementFilters();

	/**
	 * Creates the types of renderings that realize the view.
	 * 
	 * <pre>{@code
			public class MyView extends SysMLView
			{
					:
				protected void createFilteredPartTypes()
				{
					renderings = List.of(ElementTable.class, ElementDiagram.class);
				}
			}
			}</pre>
	 */
	protected abstract void createRenderings();

	/**
	 * Creates the types of views that are subviews of the view.
	 * 
	 * <pre>{@code
			public class MyView extends SysMLView
			{
					:
				protected void createSubviews()
				{
					subViews = List.of(TableView.class, DiagramView.class);
				}
			}
			}</pre>
	 */
	protected abstract void createSubviews();

	/**
	 * Name of variable for satisfied viewpoints, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String satisfiedViewpointsVariableName = "satisfiedViewpoints";
	/**
	 * Name of variable for exposed part types, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String exposedPartTypesVariableName = "exposedPartTypes";
	/**
	 * Name of variable for filtered-in elements, used by SysMLinJava tools,
	 * typically not needed for modeling,
	 */
	public static final String filteredInElementsVariableName = "filteredInElements";
	/**
	 * Name of variable for filtered-out elements, used by SysMLinJava tools,
	 * typically not needed for modeling,
	 */
	public static final String filteredOutElementsVariableName = "filteredOutElements";
	/**
	 * Name of variable for renderings, used by SysMLinJava tools,
	 * typically not needed for modeling,
	 */
	public static final String renderingsVariableName = "renderings";
	/**
	 * Name of variable for sub-views, used by SysMLinJava tools,
	 * typically not needed for modeling,
	 */
	public static final String subViewsVariableName = "subViews";
	/**
	 * Name of method to create satisfied viewpoints, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createSatisfiedViewpointsMethodName = "createSatisfiedViewpoints";
	/**
	 * Name of method to create exposed part types, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createExposedPartTypesMethodName = "createExposedPartTypes";
	/**
	 * Name of method to create element filters, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createElementFiltersMethodName = "createElementFilters";
	/**
	 * Name of method to create renderings, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createRenderingsMethodName = "createRenderings";
	/**
	 * Name of method to create sub-view, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createSubviewsMethodName = "createSubviews";
}
