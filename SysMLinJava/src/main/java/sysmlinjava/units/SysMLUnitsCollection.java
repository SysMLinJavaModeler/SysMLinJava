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
package sysmlinjava.units;

import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava's representation of a collection (library) of {@code SysMLUnit}s
 * which are SysMLinJava's representations of the SysML unit. Whereas units in
 * SysMLinJava are instances of the {@code SysMLUnit}, this
 * {@code SysMLUnitsCollection} class provides a base library of
 * {@code SysMLUnit} instances.
 * <p>
 * Each {@code SysMLUnit} instance must be declared as a static final instance,
 * i.e. each unit is declared as a unique object within the scope of the
 * containing class. This {@code SysMLUnitsCollection} class provides the base
 * for classes that define units as {@code SysMLUnit} instances. Extensions of
 * the {@code SysMLUnitsCollection} class should exclusively contain unit
 * declarations, i.e. {@code SysMLUnit} instances, and not other objects or
 * operations. An example of a use of this class to declare units is as follows.
 * 
 * <pre>
	public class SysMLinJavaUnits extends SysMLUnitsCollection
	{
			:
		&#64;Unit
		public static final SysMLUnit FeetCubic = new SysMLUnit("feet-cubic", "ft3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
		&#64;Unit
		public static final SysMLUnit MilesCubic = new SysMLUnit("miles-cubic", "mi3", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
		&#64;Unit
		public static final SysMLUnit Liters = new SysMLUnit("liters", "ltr", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
		&#64;Unit
		public static final SysMLUnit Gallons = new SysMLUnit("gallons", "gal", "", "", Optional.of(SysMLinJavaQuantityKinds.Volume));
		&#64;Unit
		public static final SysMLUnit Nanoseconds = new SysMLUnit("nanoseconds", "ns", "", "", Optional.of(SysMLinJavaQuantityKinds.Time));
			:
	}
	</pre>
 * 
 * The units can be referenced in the SysMLinJava model code by simply
 * referencing the containing class scope with the unit name. An example from a
 * units declaration for a {@code SysMLAttributeType} is as follows:
 * 
 * <pre>
	public class VolumeCubicFeet extends SysMLAttributeType
	{
			:
		&#64;Override
		protected void createUnits()
		{
			units = SysMLinJavaUnits.FeetCubic;
		}
			:
	}
	</pre>
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLUnitsCollection extends SysMLAnything
{
}