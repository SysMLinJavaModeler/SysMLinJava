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
package sysmlinjava.javaannotations.parts;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a replica of a SysML part
 * that is external to the context of the current {@code SysMLPart} container.
 * The {@code ExternalPartReplica} is used to create connectors to the actual
 * part when the part is external to the current {@code PartContainer}, i.e.
 * internal to another {@code PartContainer}. The {@code ExternalPartReplica}
 * provides the information needed to make the connection to the actual part
 * that is external to the container, e.g. the replica includes the external
 * part's port's, their IP addresses and UDP ports, context part, etc. These
 * elements and values are typically used to connect parts in the container to
 * parts in other containers.
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.PartContainer
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface ExternalPartReplica
{

}
