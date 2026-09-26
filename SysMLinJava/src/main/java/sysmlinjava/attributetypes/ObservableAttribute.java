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

/**
 * Interface specification for an observable attribute, i.e. an
 * {@code SysMLAttributeType} that is observable by {@code AttributeObserver}
 * implementations. Similar to the familiar Observer/Observable pattern, the
 * {@code ObservableAttribute} implementation maintains a list of
 * {@code AttributeObserver}s and notifies them when the observed attribute
 * changes. All {@code SysMLAttributeType}s implement this interface making them
 * observable for use as end values in "binding connectors" as well as other
 * change-notification applications.
 * 
 * @author ModelerOne
 * @see sysmlinjava.attributetypes.AttributeObserver
 */
public interface ObservableAttribute
{
	/**
	 * Adds the specified {@code AttributeObserver} to list of observers of this
	 * value which are to be notified of a change to the attribute.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @param observer value observer to be added
	 */
	public void addAttributeObserver(AttributeObserver observer);

	/**
	 * Causes all {@code AttributeObserver}s in the list of observers to be notified
	 * of the change of attribute, i.e. it invokes each of the
	 * {@code AttributeObserver}s {@code attributeChanged()} method.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 */
	public void notifyAttributeObservers();
}
