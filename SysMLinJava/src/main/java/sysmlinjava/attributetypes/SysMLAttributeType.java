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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.attributes.ProbabilityDistribution;
import sysmlinjava.javaannotations.attributes.Unit;
import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLUnit;

/**
 * SysMLinJava's representation of the SysML attribute type. The
 * {@code SysMLAttributeType} is an abstract class that serves as the base class
 * for specialized attribute types. It extends the {@code SysMLAnything} with a
 * {@code SysMLUnit}, a {@code SysMLProbabilityDistribution} (as prescribed by
 * SysML), and the functions of an {@code ObservableAttribute}. The SysMLinJava
 * implementation of the {@code ObservableAttribute} interface by the
 * {@code SysMLAttributeType} makes the attribute "observable", i.e. it can
 * notify (call) associated {@code AttributeObserver} objects if/when the value
 * of the {@code SysMLAttributeType} changes. This abstract class essentially is
 * the SysMLinJava application of the "Observable/Observer" pattern to achieve
 * the binding connector that is used by the analysis case, ports, parts, etc.
 * to bind to attributes. This feature of the {@code SysMLAttributeType} can
 * also be used for other operations in which the "Observable/Observer" pattern
 * might be needed.
 * <p>
 * Note that the SysML specification calls for inclusion of SysML's quantityKind
 * in the properties of the attribute type. It also calls for the inclusion of
 * the SysML unit in the attribute type as well. Since the unit contains the
 * quantityKind, the SysMLinJava implementation does not include the
 * quantityKind in the attribute type so as to prevent duplication of
 * information.
 * 
 * @author ModelerOne
 * @see sysmlinjava.parts.SysMLPart#createAttributes
 * @see sysmlinjava.connectors.SysMLBindingConnector
 * @see sysmlinjava.analysis.ParametricAnalysisCase
 */
public abstract class SysMLAttributeType extends SysMLAnything implements ObservableAttribute
{
	/**
	 * The units for this attribute type. Must be set in override of abstract method
	 * {@code createUnits()}
	 */
	@Unit
	public SysMLUnit units;
	/**
	 * List of {@code AttributeObserver}s to be notified if/when this
	 * {@code SysMLAttributeType} instance's value changes. Note that extension
	 * classes of the {@code SysMLAttributeType} that need to use the
	 * {@code ObservableAttribute} interface and this list of
	 * {@code AttributeObservers} must implement a "{@code setValue(<value>)}
	 * operation that, in addition to setting the attribute's value, also invokes
	 * the {@code notifyAttributeObservers()} operation in order to notify observing
	 * objects of the change.
	 */
	public List<AttributeObserver> observers;
	/**
	 * Optional probability distribution of the attribute type Set via method
	 * {@code createProbabilityDistribution()}
	 */
	@ProbabilityDistribution
	public Optional<SysMLProbabilityDistribution> probabilityDistribution;

	/**
	 * Constructor for attribute with name and ID
	 * 
	 * @param name unique name of the attribute
	 * @param id   unique identifier of the attribute
	 */
	public SysMLAttributeType(String name, long id)
	{
		super(name, id);
		observers = new ArrayList<>();

		createUnits();
		createProbabilityDistribution();
		createAttributes();
	}

	/**
	 * Constructor - basic
	 */
	public SysMLAttributeType()
	{
		super();
		observers = new ArrayList<>();

		createUnits();
		createProbabilityDistribution();
		createAttributes();
	}

	/**
	 * Constructor for deep copy of base class attributes
	 * 
	 * @param copied instance which is to be copied
	 */
	public SysMLAttributeType(SysMLAttributeType copied)
	{
		super(copied);
		units = copied.units;
		observers = new ArrayList<>(copied.observers);
		probabilityDistribution = copied.probabilityDistribution;
	}

	/**
	 * Returns a copy of this attribute
	 * 
	 * @return copy of attribute
	 */
	public SysMLAttributeType copy()
	{
		logger.warning("returning null for copy of abstract SysMLAttributeType - override for copy of sub-class");
		return null;
	}

	/**
	 * Binds this attribute to the specified attribute observer where binding
	 * consists of adding the observer to the attribute's set of observers and
	 * notifying the observers of a change thereby providing the observer an
	 * opportunity to initialize its attribute with this attribute, thereby
	 * initializing the "binding".
	 * <p>
	 * This {@code bindTo()} operation can be used to create "binding" connectors of
	 * attributes in parts or ports to parameters in analysis cases. Specifically,
	 * it can be used in {@code SysMLBindingConnectorFunction}s to complete the
	 * bound connection when invoked by the {@code SysMLBindingConnector}.
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard. To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * 
	 * @see sysmlinjava.connectors.SysMLBindingConnector
	 * @param observer the observer of this attribute that is to be bound to this
	 *                 attribute
	 */
	public void bindTo(AttributeObserver observer)
	{
		addAttributeObserver(observer);
		notifyAttributeObservers();
	}

	@Override
	public void addAttributeObserver(AttributeObserver observer)
	{
		observers.add(observer);
	}

	@Override
	public void notifyAttributeObservers()
	{
		observers.forEach(observer ->
		{
			observer.attributeChanged(Optional.of(name.isPresent() ? name.get() : "notSpecified"));
		});
	}

	/**
	 * Required operation to create/assign the units attribute for this attribute
	 * type. The units variable should be assigned with the name of a
	 * {@code SysMLUnit} instance located in an extension of the {@code SysMLUnits}
	 * class. An example is as follows:
	 * 
	 * <pre>
	 * &#64;Override
	 * protected void createUnits()
	 * {
	 * 	units = SysMLinJavaUnits.FeetCubic;
	 * }
	 * </pre>
	 * 
	 * @see sysmlinjava.units.SysMLinJavaUnits
	 */
	protected abstract void createUnits();

	/**
	 * Overridable operation to create the probability distribution of the values of
	 * this attribute type. The base class operation specifies an empty or no
	 * probability distribution. If a new or different probability distributionAn is
	 * desired from the one declared for this base class or for a specific value
	 * type, then a subclass will have to be declared that invokes this method to
	 * specify the different probability distribution, i.e. each specialization of
	 * the {@code SysMLAttributeType} should be associated with one and only one
	 * type of probability distribution to avoid mixing of distributions. An example
	 * of the overridden operation is as follows.
	 * 
	 * <pre>
		&#64;Override
		protected void createProbabilityDistribution()
		{
			probabilityDistribution = Optional.of(new SysMLUniformProbabilityDistribution(0.0, 100.0);
		}
	 * </pre>
	 */
	protected void createProbabilityDistribution()
	{
		probabilityDistribution = Optional.empty();
	}

	/**
	 * Creates the attributes (if any) of the attribute type, i.e. creates any
	 * sub-attributes.
	 */
	protected void createAttributes()
	{
	}

	/**
	 * Name of field that contains the probability distribution of the value type,
	 * used by SyMLinJava tools, typically not used for modeling
	 */
	public static final String probabilityDistributionFieldName = "probabilityDistribution";

	/**
	 * Name of field that contains the units of the value type, used by SyMLinJava
	 * tools, typically not used for modeling
	 */
	public static final String unitsFieldName = "units";

	/**
	 * Name of method to create the probability distribution of the attribute type,
	 * used by SyMLinJava tools, typically not used for modeling
	 */
	public static final String createProbabilityDistributionMethodName = "createProbabilityDistribution";

	/**
	 * Name of method to create (assign) sub-attributes, used by
	 * SyMLinJava tools, typically not used for modeling
	 */
	public static final String createAttributesMethodName = "createAttributes";
	/**
	 * Name of method to create (assign) units to the attribute type, used by
	 * SyMLinJava tools, typically not used for modeling
	 */
	public static final String createUnitsMethodName = "createUnits";
}
