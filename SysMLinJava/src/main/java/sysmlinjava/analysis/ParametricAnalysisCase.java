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
package sysmlinjava.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

import sysmlinjava.attributetypes.AttributeObserver;
import sysmlinjava.attributetypes.KeyValueMap;
import sysmlinjava.attributetypes.ObservableAttribute;
import sysmlinjava.attributetypes.SysMLAttributeType;
import sysmlinjava.connectors.SysMLBindingConnector;
import sysmlinjava.events.SysMLChangeEvent;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.javaannotations.analysis.parametrics.Parameter;
import sysmlinjava.javaannotations.connectors.BindingConnector;
import sysmlinjava.states.StateBehaviorContext;
import sysmlinjava.states.SysMLStateMachine;

/**
 * Abstract base class for the parametric analysis case, i.e. a SysMLv2
 * compliant representation of the SysMLv1 constraint block.
 * <h2>SysML parametric analysis case base class</h2> Extensions of the
 * {@code ParametricAnalysisCase} class declare fields and methods that
 * represent SysMLv1 constraint block properties and operations. These include
 * the analysis (constraint) parameters, the binding connectors (constraint
 * parameter ports), and the binding connector functions (constraint parameter
 * port functions), as well as features of the SysML part (block).
 * <h3>ObservableAttribute and AttributeObserver interfaces</h3> The
 * {@code ParametricAnalysisCase} is also an {@code ObservableAttribute} as well
 * as a {@code AttributeObserver}. That is, the parameters of the parametric
 * analysis case may be observed by other analysis cases and/or the case may
 * observe other analysis case's values. In either case, the observed values can
 * be parameters of the current or other analysis cases.
 * <h3>Parameters</h3> As in the SysMLv1 constraint block, the
 * {@code ParametricAnalysisCase} includes analysis parameters. These parameters
 * correspond to the variables used in the analysis actions specified in the
 * analysis case. Parameter fields are instances of {@code SysMLAttributeTypes}
 * and annotated by the &#64;{@code Parameter} annotation.
 * <h3>Parameter Ports</h3> Parameters may be "bound" to attributes in parts
 * by the {@code SysMLBindingConnector}. The {@code SysMLBindingConnector}
 * serves as the bound reference end used in the SysML parametric diagram to
 * "bind" parameters to attributes of parts, ports, or other parametric analysis
 * cases. These parameter bindings are specified by fields of the
 * {@code SysMLBindingConnector} type and are annotated with the
 * &#64;{@code BindingConnector} annotation and created/initialized in the
 * {@code createBindingConnector()} operation.
 * <h3>Multi-Threaded Parametric Analyses</h3> The
 * {@code ParametricAnalysisCase} is executable as a Java thread and capable of
 * performing multi-threaded parametric analysis during an actual model
 * execution or simulation. This capability is typically not available in
 * conventional diagram-based SysML tools. However, in SysMLinJava, the
 * parametric analysis case can leverage the multi-threaded features of the Java
 * concurrency features and of the {@code SysMLBindingConnector} to input
 * parameters that reside in different threads of execution to "bind" values in
 * other parts or ports to the paramtric anslysis case's parameters in a
 * thread-safe manner. The default configuration of the
 * {@code ParametricAnalysisCase} is to support asynchronous analysis actions in
 * multi-threaded parts models. If a fully synchronous (single threaded) model
 * is used, however, then the {@code createStateMachine} operation can be
 * overridden to set the {@code stateMachine = Optional.empty()} if desired.
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.SysMLPart#createAnalysisCases
 */
public abstract class ParametricAnalysisCase extends SysMLAnalysisCase implements StateBehaviorContext, ObservableAttribute, AttributeObserver
{
	protected Logger logger;

	/**
	 * Map of the analysis parameter IDs to the parameters. Analysis parameters are
	 * updated in the map when their "bound" attribute values change and can be
	 * retrieved from the map using the parameter IDs. All analysis params are
	 * retrieved via the parameter connectors and stored in the map as basic
	 * {@code SysMLAttributeType}s. Therefore, parameter values retrieved from the
	 * map should (must) be cast to the correct type needed for operations on the
	 * parameters, e.g in analysis calculations. Note that the parameters could be
	 * stored to/retrieved from individual instance variables in an extended
	 * parametric analysis case instead of as the mapped variables provided by this
	 * base class. However, if stored as individual instance variables, the extended
	 * case must override and implement the {@code retrieveParameters()} operation
	 * to store the retrieved parameters into these alternate variable values,
	 * because the default {@code retrieveParameters()} operation retrieves the
	 * parameters into this map.
	 */
	@Parameter
	public KeyValueMap<String, SysMLAttributeType> params;

	/**
	 * Collection of binding connectors that bind the parameters to attributes to
	 * obtain their latest values. Map keys are the paramIDs for the parameters and
	 * the map values are the binding connectors. The {@code paramConnectors} must
	 * be created/initialized/put into the map in an override of the
	 * {@code createBindingConnectors()} operation in a {@code SysMLPart} class that
	 * has access to both this case and the attribute that is bound to it's
	 * parameter
	 */
	@BindingConnector
	public KeyValueMap<String, SysMLBindingConnector> paramConnectors;

	/**
	 * Optional identification of the current (last updated) parameter. This
	 * identifier is updated for every parameter retrieval by a binding connector
	 * and can be used by the {@code retrieveParameters()} and {@code perform()}
	 * operation to identify the currently changed parameter.
	 */
	public Optional<String> currentParamID;

	/**
	 * Optional identification of the previous (last one before current) parameter.
	 * This identifier is updated for every parameter retrieval by a binding
	 * connector and can be used by the {@code retrieveParameters()} and
	 * {@code perform()} operation to identify the previously changed parameter (to
	 * perhaps reset objects that are dependent on its changes, etc.)
	 */
	public Optional<String> previousParamID;

	/**
	 * Current/latest parameter that has been updated/changed and as identified by
	 * the {@code currentParamID}
	 */
	public SysMLAttributeType currentParam;

	/**
	 * Previous/last parameter that has been updated/changed and as identified by
	 * the {@code previousParamID}
	 */
	public SysMLAttributeType previousParam;

	/**
	 * List of {@code AttributeObserver}s of this analysis case that are to be
	 * notified of some event that takes place in this analysis case. Note this
	 * implementation of the {@code ObservableAttribute} is in addition to the
	 * {@code AttributeObserver}s that may have been added to the analysis
	 * parameters of the {@code SysMLAttributeType} type, which also implements the
	 * {@code ObservableAttribute} interface.
	 */
	public List<AttributeObserver> attributeObservers;

	/**
	 * Optional value of a "parent" parametric analysis case}, i.e. an extension of
	 * the {@code ParametricAnalysisCase} that is above this one in a hierarchy of
	 * parametric analysis cases. The parent may be an {@code AttibuteObserver} to
	 * this case's analysis parameters and/or to the {@code ParametricAnalysisCase}
	 * itself if it needs to react to changes to this
	 * {@code ParametricAnalysisCase}.
	 */
	public Optional<? extends ParametricAnalysisCase> parent;

	/**
	 * Optional SysML state machine for this {@code ParametricAnalysisCase}. The A
	 * {@code ParametricAnalysisStateMachine} is presumed to be used as execution of
	 * the analysis conforms to this type of state machine.
	 */
	public Optional<ParametricAnalysisStateMachine> stateMachine;

	/**
	 * Instance of the Java API's {@code ScheduledThreadPoolExecutor} used to
	 * execute the optional state machine's {@code Runnable}.
	 */
	public ScheduledThreadPoolExecutor concurrentExecutionThreads;

	/**
	 * Constructor
	 * 
	 * @param parent Optional parent parametric analysis case in the hierarchy (if
	 *               any) of cases.
	 * @param name   Unique name of the parametric analysis case.
	 * @param id     Unique ID for the parametric analysis case
	 */
	public ParametricAnalysisCase(Optional<? extends ParametricAnalysisCase> parent, String name, Long id)
	{
		super(name, id);
		logger = Logger.getLogger(getClass().getSimpleName());
		this.parent = parent;
		concurrentExecutionThreads = new ScheduledThreadPoolExecutor(10);
		params = new KeyValueMap<>();
		paramConnectors = new KeyValueMap<>();
		currentParam = null;
		previousParam = null;
		currentParamID = Optional.empty();
		previousParamID = Optional.empty();
		attributeObservers = new ArrayList<>();
		createStateMachine();
		createParameters();
	}

	/**
	 * Overridable method that performs this parametric analysis case. This method
	 * should simply perform the analysis within its body, invoking other analysis
	 * action methods as needed. The analysis actions may also be performed by a
	 * functional interface that is assigned to the {@code function} variable. The
	 * functional interface should be declared in the extended class and the
	 * {@code function} assignment should be performed in an override of the
	 * {@code createFunction()} method in the extended class. The assigned function
	 * is then invoked by this {@code perform} action, optionally invoking other
	 * methods/actions/analyses in the case as "steps" in the analysis. An example
	 * is as follows:
	 * 
	 * <pre>{@code
		class MyParametricAnalysis extends ParametricAnalysisCase
		{
			&#64;AnalysisCaseAction
			&#64;Override
			protected void perform()
			{
				collectParameterValues();
				calculateResult();
				evaluateResult();
				updateAnalysisDisplay();
			}
	
			&#64;Action
			private void collectParameterValues()
			{
				:
			}
	
			&#64;Action
			private void calculateResult();
			{
				:
			}
	
			&#64;Action
			private void evaluateResult();
			{
				:
			}
	
			&#64;Action
			private void updateAnalysisDisplay();
			{
				:
			}
	 }}</pre>
	 * 
	 * ---OR---
	 * <p>
	 * Just create the calculation/case function to be called by inherited
	 * {@code perform()}
	 * 
	 * <pre>{@code
		class MyParametricAnalysis extends ParametricAnalysisCase
		{
				:
	
			&#64;Override
			protected void createFunction()
			{
				function = (PerformedActionFunction)() ->
				{
					collectParameterValues();
					calculateResult();
					evaluateResult();
					updateAnalysisDisplay();
				};
			}
				:
		}}</pre>
	 */

	@Override
	public void addAttributeObserver(AttributeObserver observer)
	{
		attributeObservers.add(observer);
	}

	/**
	 * Reacts to notification by a bound parameter or parametric analysis case
	 * (which is an {@code ObservableValue}) that the observed parameter value or
	 * parametric analysis case has changed. This implements the concept of SysML's
	 * "binding connector" for a parameter or parametric analysis case for an
	 * executable SysML model.
	 * 
	 * @param paramID unique ID of the binding parameter whose bound attribute
	 *                changed.
	 */
	public synchronized void valueChanged(Optional<String> paramID)
	{
		if (stateMachine.isPresent())
			acceptEvent(new SysMLChangeEvent("ParameterChange", paramID.isPresent() ? paramID.get() : "notSpecified", 0L));
		else
		{
			onParameterChange(paramID.isPresent() ? paramID.get() : "notSpecified");
			perform();
			notifyAttributeObservers();
		}
	}

	/**
	 * Reacts to notification by an {@code ObservableValue}) of which this analysis
	 * is an {@code AttributeObserver} that the observed attribute has changed. This
	 * is typically called by a binding connector of type
	 * {@code SysMLBindingConnector}) when for a parametric analysis case.
	 */
	@Override
	public void attributeChanged(Optional<String> attributeID)
	{
		valueChanged(attributeID);
	}

	@Override
	public void notifyAttributeObservers()
	{
		attributeObservers.forEach(attributeObserver -> attributeObserver.attributeChanged(currentParamID));
	}

	/**
	 * Starts the parametric analysis case's state machine behavior, if a state
	 * machine is present. The operation submits an {@code InitialEvent} to the
	 * state machine's event queue to thereby start its execution thread. The state
	 * machine will execute in accordance with the states and transitions that are
	 * defined in the {@code stateMachine}.
	 */
	@Override
	public void start()
	{
		if (stateMachine.isPresent())
			stateMachine.get().start();
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to start");
	}

	/**
	 * Accepts and queues a specified event into the state machine's event queue.
	 * Whereas the event queue is a thread safe object, this method can be called to
	 * inject an event into the state machine from any thread.
	 * 
	 * @param event event to be queued to the state machine
	 */
	@Override
	public void acceptEvent(SysMLEvent event)
	{
		if (stateMachine.isPresent())
			stateMachine.get().queueEvent(event);
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to accept event: " + event.getClass().getSimpleName());
	}

	/**
	 * Stops the part's state machine-based behavior, if a state machine is present
	 * in the part. The operation simply stops the state machine execution. Note if
	 * this part's state machine is asynchronous, i.e. executes in its own thread,
	 * then this operation imposes a "hard stop" where the thread is canceled
	 * regardless of what state it's in. If the part's state machine is synchronous,
	 * this operation can only queue up a {@code FinalEvent} to the event queue, on
	 * the assumption that the state machine is capable of handling the
	 * {@code FinalEvent} at the point in which the {@code stop()} method is
	 * invoked. Therefore, if a part is synchronous and this stop method is used,
	 * it's state machine should be modeled to handle the {@code FinalEvent}
	 * accordingly.
	 */
	@Override
	public void stop()
	{
		if (stateMachine.isPresent())
			stateMachine.get().stop();
		else
			logger.warning(getClass().getSimpleName() + ": no state machine to stop");
	}

	/**
	 * Delays the calling thread (sleeps) for the specified seconds of time.
	 * 
	 * @param seconds time to sleep in seconds. Use up to 3 decimal places for
	 *                fractions of a second, i.e. the delay is capable of the
	 *                milliseconds precision provided by java's
	 *                {@code Thread.sleep(<millis>)} operation.
	 */
	@Override
	public void delay(double seconds)
	{
		try
		{
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getIdentityString()
	{
		return identityString();
	}

	@Override
	public ScheduledThreadPoolExecutor getExecutionThreads()
	{
		return concurrentExecutionThreads;
	}

	@Override
	public Optional<? extends SysMLStateMachine> getStateMachine()
	{
		return stateMachine;
	}

	/**
	 * Overridable operation that responds to bound attribute change. This default
	 * method retrieves the changed attribute from the binding connector and stores
	 * it in the corresponding parameter. By default, the parameters are assumed to
	 * reside in the {@code params} key-value map and are updated there accordingly,
	 * but the subordinate {@code onParameterChange()} method may be overridden to
	 * update the parameter in accordance with another location and/or method as
	 * needed.
	 * 
	 * @param paramID unique ID of the parameter bound attribute that was
	 *                changed/updated
	 */
	protected void onParameterChange(String paramID)
	{
		if (!paramID.isBlank())
		{
			previousParamID = currentParamID;
			currentParamID = Optional.of(paramID);
			SysMLBindingConnector paramConnector = paramConnectors.get(currentParamID.get());
			if (paramConnector != null)
			{
				if (currentParam != null)
					previousParam = currentParam;
				SysMLAttributeType boundParam = paramConnector.getAttribute();
				if (boundParam != null)
				{
					currentParam = boundParam;
					onParameterChange(currentParamID.get(), currentParam);
				}
				else
					logger.severe("bound parameter value not retrieved from its binding connector: " + paramID);
			}
			else
				logger.severe("binding connector not found for parameter: " + paramID);
		}
		else
			currentParamID = Optional.empty();
	}

	/**
	 * Overridable operation to handle a change to an attribute that is bound to an
	 * analysis parameter. Default operation simply replaces the parameter's current
	 * value in the params map with the attribute's new value. Overrides may also or
	 * only update an other instance(s) of a binding parameter that may be declared
	 * in the analysis case.
	 * <p>
	 * Note that the {@code paramValue} provided is a copy of (not a reference to)
	 * the attribute at the time the attribute was changed. This enables use of the
	 * changed attribute in multi-threaded models ensuring that the attribute used
	 * for the analysis parameter will not be changed by another thread of execution
	 * during the analysis {@code perform()} operation.
	 * 
	 * @param paramID    ID of the bound attribute/parameter that changed
	 * @param paramValue new instance/copy of the bound attribute/parameter
	 */
	protected void onParameterChange(String paramID, SysMLAttributeType paramValue)
	{
		params.put(paramID, paramValue);
	}

	/**
	 * Overridable operation to handle occurrence of a time event. This operation
	 * will be invoked only if a timer was specified for the state machine created
	 * in the {@code createStateMachine()} operation.
	 */
	protected void onTimeEvent()
	{
		logger.warning("time event not handled, may need to override operation to handle event");
	}

	/**
	 * Overridable operation that instantiates the analysis case state machine, if
	 * any, for this analysis case. By default, the {@code ParametricAnalysisCase}
	 * creates its state machine to be an instance of the
	 * {@code ParametricAnalysisStateMachine}. Also created is a thread pool used to
	 * "run" the state machine in its own thread.
	 * <p>
	 * The {@code ParametricAnalysisStateMachine} is designed to handle
	 * {@code SysMLChangeEvent}s produced by {@code BindingConnector}s whose bound
	 * parameters are updated in the same or different thread(s) from the thread of
	 * the analysis action of the analysis case. It handles the events in accordance
	 * with SysML standard parametric analysis operation.<br>
	 * However, if a different state machine is needed for the
	 * {@code ParametricAnalysisCase}, this {@code createStateMachine()} operation
	 * may be overridden to assign another type of {@code SysMLStateMachine} to the
	 * analysis case's {@code stateMachine}, or to assign none at all, i.e.
	 * {@code stateMachine = Optional.empty()}. If another type of
	 * {@code SysMLStateMachine} is needed, it can be installed by assigning the
	 * {@code stateMachine} variable with the instance of the custom state machine
	 * in the overriding operation. If no state machine is needed, then the
	 * overriding operation need only be set to {@code Optional.empty()}.
	 */
	protected void createStateMachine()
	{
		stateMachine = Optional.of(new ParametricAnalysisStateMachine(this, name.get() + "StateMachine"));
	}

	/**
	 * Overridable abstract operation that should create the analysis's parameters
	 * initializing them and the "put"-ing the initialized instances of each into
	 * the {@code params} map with their appropriate "key"s, i.e. their parameter
	 * IDs. An example follows:
	 * 
	 * <pre>
	 * {@code
	 		:
		&#64;Override
		protected void createParameters()
		{
			params = KeyValueMap.of(
				List.of("paramA", "paramB", "paramC"),
				List.of(new FirstParamType(), new NextParamType(), new LastParamType()));
		}
	 * }
	 * </pre>
	 * 
	 * Note each parameter must be a type of {@code SysMLAttributeType} as the
	 * {@code SysMLAttributeType} provides the features needed for binding of
	 * analysis parameters to attributes.
	 * 
	 * @see sysmlinjava.attributetypes.SysMLAttributeType
	 */
	protected void createParameters()
	{
	}

	/**
	 * Name of variable for case's params, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String paramsVariableName = "params";
	/**
	 * Name of variable for case's binding connectors, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String paramConnectorsVariableName = "paramConnectors";
	/**
	 * Name of method to create the analysis state machine, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createStateMachineMethodName = "createStateMachine";
	/**
	 * Name of method to create the analysis parameters, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createParametersMethodName = "createParameters";
}
