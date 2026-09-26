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
package sysmlinjava.connectors;

import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

import sysmlinjava.attributetypes.AttributeObserver;
import sysmlinjava.attributetypes.SysMLAttributeType;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.states.StateBehaviorContext;

/**
 * SysMLinJava's representation of the SysML binding connector. The
 * {@code SysMLBindingConnector} enables the "binding" of attributes in parts or
 * ports to analysis parameters in parametric analysis cases or other binding
 * contexts. In order to bind attributes in an executable model, the
 * {@code SysMLBindingConnector} operates as a multi-threaded object that
 * "observes" the bound attribute and quees up the changed value of the
 * attribure to the context that binds to the attribute.
 * <p>
 * The {@code SysMLBindingConnector} provides for a thread-safe implementation
 * of the binding connector - a capability typically not available in
 * traditional diagram-based SysML modeling applications. It performs this
 * multiple thread connection by using a thread-safe queue for the passing of
 * updated attribute values to the {@code bindingContext}. When an attribute in
 * a part or port in a thread is changed, it invokes the connector to queue its
 * new value to this thread-safe queue. The binding context, which is likely in
 * another thread. is notified of the change to the bound attribute. The context
 * then retrieves the changed attribute from the thread-safe queue. In this way
 * the binding connector can be used to bind to attributes located in virtually
 * any thread configuration of multi-threaded models.
 * 
 * @author ModelerOne
 */
public final class SysMLBindingConnector extends SysMLAnything implements AttributeObserver
{
	/**
	 * Queue of changed attributes. The queue is a thread-safe queue whereby a call
	 * of the {@code attributeChanged()} operation by any thread causes a changed
	 * parameter value to be enqued, and a call of the {@code getAttribute()}
	 * operation by any Observer's thread causes the latest parameter value to be
	 * dequeud.
	 */
	public ArrayBlockingQueue<SysMLAttributeType> queuedAttributes;
	/**
	 * The attribute of a part, port, or analysis case which is bound to by the
	 * binding context.
	 */
	public SysMLAttributeType boundAttribute;
	/**
	 * The part, port, or analysis case that is binding to the attribute in the
	 * bound context
	 */
	public StateBehaviorContext bindingContext;

	/**
	 * Constructor that specifies the attribute that is to be "bound" by the
	 * connector. This constructor assumes a standard function will be used to
	 * retrieve the bound attribute when its value changes, i.e. connector will
	 * queue up a copy of the changed attribute to the {@code bindingContext} so it
	 * can update the binding parameter
	 * 
	 * @param boundAttribute the attribute type instance to which this connector is
	 *                       to bind
	 * @param bindingContext state behavior context whose parameter is binding to
	 *                       the attribute
	 * @param name           unique name of the port
	 */
	public SysMLBindingConnector(SysMLAttributeType boundAttribute, StateBehaviorContext bindingContext, String name)
	{
		super(name, 0L);
		this.queuedAttributes = new ArrayBlockingQueue<SysMLAttributeType>(10);
		this.boundAttribute = boundAttribute;
		this.bindingContext = bindingContext;
		boundAttribute.addAttributeObserver(this);
	}

	/**
	 * Updates the parameter value for the bound value by queuing the current bound
	 * value to the connector's attribute values queue and then submitting a
	 * {@code SysMLChangeEvent} to the {@code bindingContext}'s state machine.
	 * <p>
	 * This method should be invoked by the {@code onAttributeChangedFunction}.
	 * 
	 * @param boundAttribute updated value for the attribute bound by this connector
	 */
	public void update(SysMLAttributeType boundAttribute)
	{
		try
		{
			if (queuedAttributes.remainingCapacity() < 1)
				logger.warning("queue in binding connector for attribute " + name + " is full.  Maybe missing/insufficient calls to \"getAttribute()\" ?");

			queuedAttributes.put(boundAttribute);
			((AttributeObserver) bindingContext).attributeChanged(name.isPresent() ? Optional.of(name.get()) : Optional.empty());
		} catch (InterruptedException e)
		{
			logger.warning("Interrupted exception: " + e.getMessage());
		}
	}

	@Override
	public void attributeChanged(Optional<String> attributeID)
	{
		if (boundAttribute != null)
			update(boundAttribute.copy());
		else
			logger.warning("no bound attribute for binding connector " + getName() + " to update");
	}

	/**
	 * Retrieves the latest changed attribute value from the connectors's attributes
	 * queue. This operation is typically called by the {@code bindingContext} of
	 * the connector after being notified of the value change by the
	 * {@code update()} operation.
	 * 
	 * @return a {@code SysMLAttributeType} object that represents the
	 *         latest/changed value of the attribute bound by this connector.
	 */
	public SysMLAttributeType getAttribute()
	{
		SysMLAttributeType result = null;
		if (!queuedAttributes.isEmpty())
		{
			while (queuedAttributes.size() > 1)
			{
				try
				{
					result = queuedAttributes.take();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			result = queuedAttributes.peek();
		}
		else
			logger.warning("unexpected empty parameters queue");
		return result;
	}
}
