/**
 * SysMLinJava representation of SysML attribute types.
 * <p>
 * Numerous commonly used attribute types are provided. As in SysML, base
 * attribute types for real, integer, string, and boolean are provided as are many
 * specializations of these value types.
 * <p>
 * Each value type is an {@code ObservableValue} which is observable by
 * {@code AttributeObserver}s. The observable value enables emulation of the "binding"
 * connector for the attributes used for parameter bindings and other models
 * requiring attribute change notification.
 * <p>
 * Attribute types in SysMLinJava models are not limited to the types in this
 * package. Additional attribute types may be created by simply extending the
 * {@code SysMLAttributeType}, or any of the specializations of the
 * {@code SysMLAttributeType} contained in this package.
 */
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
package sysmlinjava.attributetypes;