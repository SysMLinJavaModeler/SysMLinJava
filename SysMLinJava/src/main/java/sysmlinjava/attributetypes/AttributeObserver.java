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
package sysmlinjava.attributetypes;

import java.util.Optional;

/**
 * The {@code AttributeObserver} is a functional interface for a value observer,
 * i.e. an object that wants to be notified of a change in the value of
 * {@code ObservableAttribute} implementations. Similar to the deprecated
 * {@code Observer} in the Java API, the {@code AttributeObserver}
 * implementation provides an implementation of a {@code attributeChanged()}
 * operation that is called by the {@code ObservableAttribute} whose
 * {@code addAttributeObserver()} operation was previously invoked. All
 * {@code SysMLAttributeTypes} are {@code ObservableAttribute}s thereby enabling
 * their use as ends of the {@code SysMLBindingConnector} used to bind
 * attributes in parts to attributes/parameters in analysis cases.
 * 
 * @author ModelerOne
 * @see sysmlinjava.attributetypes.ObservableAttribute
 * @see sysmlinjava.connectors.SysMLBindingConnector
 */
@FunctionalInterface
public interface AttributeObserver
{
	/**
	 * Invoked by an {@code ObservableAttribute} to notify this
	 * {@code AttributeObserver} of a change in the observed value.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param attributeID optional identifier of the attribute that changed
	 */
	public void attributeChanged(Optional<String> attributeID);
}
