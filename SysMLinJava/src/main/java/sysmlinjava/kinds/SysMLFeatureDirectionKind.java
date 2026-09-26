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
package sysmlinjava.kinds;

/**
 * SysMLinJava representation of the enumeration of the kinds of feature
 * direction available under SysML, i.e. SysMLinJava's version of SysML's
 * {@code FeatureDirectionKind}
 * 
 * @author ModelerOne
 *
 */
public enum SysMLFeatureDirectionKind
{
	/**
	 * Feature is provided (can be invoked by others)
	 */
	provided,
	/**
	 * Feature is required (will be invoked by this)
	 */
	required,
	/**
	 * Feature is both provided and required
	 */
	providedrequired;
}
