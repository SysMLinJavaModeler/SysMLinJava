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
 * SysMLinJava's representation of SysML's enumeration of the flow directions,
 * i.e. the {@code flowDirectionKind}
 * 
 * @author ModelerOne
 *
 */
public enum SysMLFlowDirectionKind
{
	/**
	 * Flow is in to the object of the flow
	 */
	in,
	/**
	 * Flow is out of the object of the flow
	 */
	out,
	/**
	 * Flow is both into and out of the object of the flow
	 */
	inout;
}
