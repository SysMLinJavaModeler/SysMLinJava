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
package sysmlinjava.javaannotations.ports;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "proxy" port. The
 * field variable should be an instance of an extension class of the
 * {@code SysMLProxyPort} and should be created/initialized in the
 * {@code createProxyPorts()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.ports.SysMLProxyPort
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE_USE)
public @interface ProxyPort
{
	/**
	 * Returns whether this port is a conjugate port
	 * 
	 * @return whether this port is a conjugate port
	 */
	boolean isConjugate() default false;

	/**
	 * Returns whether this port is a behavioral port, i.e. invokes a behavior on
	 * the parent part or port
	 * 
	 * @return whether this port is a behavioral port
	 */
	boolean isBehavior() default true;
}
