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
package sysmlinjava.quantitykinds;

import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava representation of a collection of instances of SysML's
 * quantityKinds. {@code SysMLQuantityKindsCollection} extension classes declare
 * static instances of the {@code SysMLQuantityKind} and a list of these
 * instances.
 * <p>
 * Each {@code SysMLQuantityKind} instance is a static final instance in the
 * extended class of this abstract class. An example follows.
 * 
 * <pre>
	public class MyQuantityKinds extends SysMLQuantityKindsCollection
	{
			:
		&#64;QuantityKind
		public static final SysMLQuantityKind Acceleration = new SysMLQuantityKind("acceleration", "a", "Acceleration", "");
		&#64;QuantityKind
		public static final SysMLQuantityKind Area = new SysMLQuantityKind("area", "area", "Area", "");
			:
	}</pre>
 * The quantityKinds can be referenced in other elements of the SysMLinJava
 * model code by simply referencing the class scope with the unit name. An
 * example follows.
 * 
 * <pre>{@code
quantity = SysMLinJavaQuantityKinds.Acceleration;
}</pre>
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLQuantityKindsCollection extends SysMLAnything
{
}
