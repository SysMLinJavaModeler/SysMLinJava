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
package sysmlinjava.javaannotations.metadata;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents customized SysML metadata,
 * i.e. an instance of an extesnion to the {@code SysMLMetadata} type.
 * <p>
 * Note that this annotation should be used only for fields declaring customized
 * metadata that is not one of the standard SysML types of metadata such as
 * {@code SysMLIssue}, {@code SysMLRationale}, {@code SysMLStatusInfo}, etc.
 * Fields for standard types of metadata should be annotated with their
 * applicable annotations such as &#64;{@code Issue}, &#64;{@code Rationale},
 * &#64;{@code StatusInfo}, etc.
 * 
 * @author ModelerOne
 */
@Retention(SOURCE)
@Target({ FIELD })
public @interface Metadata
{

}
