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
package sysmlinjava.views;


/**
 * SysMLinJava representation of SysML's createView activity. The
 * {@code SysMLCreateView} activity, a functional interface realized by a lambda
 * expression, can be instantiated and executed in any model element to
 * programatically generate a SysML view of some or all of the model in which it
 * is embedded. Instances of the {@code SysMLCreateViews} activity could be used
 * to generate document views of the model in HTML, PDF, text, etc. It could
 * also be used to generate XMI views of the model for model export to other
 * SysML modeling tools. The SysMLinJava TaskMaster&trade; tool uses this
 * activity to generate a number of its views of SysMLinJava models.
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLCreateView
{
	/**
	 * Creates the specified view
	 * 
	 * @param view the view to be created by the activity
	 */
	void create(SysMLView view);
}
