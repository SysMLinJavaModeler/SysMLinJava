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
package sysmlinjava.items;

import java.util.Optional;

import sysmlinjava.javaannotations.statemachines.StateMachine;
import sysmlinjava.occurrences.SysMLOccurrence;
import sysmlinjava.states.SysMLStateMachine;

/**
 * SysMLinJava's represention of the SysML item.
 * <h2>The item in SysMLinJava</h2>{@code SysMLItem} is an abstract class that
 * provides a base class for for all SysML item representations. Extensions of
 * the {@code SysMLItem} class declare features (as fields) and owned actions
 * (as methods). Features may include SysML attributes, items, parts,
 * requirerements, and dependencies.
 * <h2>Creating/initializing features</h2>The {@code SysMLItem} provides a
 * series of overrideable method calls to create all the features (the Java
 * fields) of the represented SysML item (a Java class). The {@code SysMLItem}
 * constructor automatically invokes methods to create/initialize the
 * attributes, items, parts, requirements, and dependencies. This automatice invocation
 * of methods allows extensions to the {@code SysMLItem} to simply override the
 * {@code createXxxx()} methods for the applicable features (fields) declared in
 * the item (class) and perform the creations/initializations in the create
 * methods. This ensures the features are created/initialized in the correct and
 * complete sequence needed. The overridable {@code createXxxx()} methods
 * provide a framework for the features' complete definitions as well as a
 * "reminder" to create and initialize the item's features.
 * <p>
 * A simplified example of code for an item model follows:
 * 
 * <pre>
 * public class InputPower extends SysMLItem
 * {
 * 	&#64;Attribute
 * 	public ElectricPotentialVolts voltage;
 * 	&#64;Attribute
 * 	public ElectricCurrentAmps current;
 * 
 * 	&#64;Requirement
 * 	public SysMLRequirement standardPowerRequirement;
 * 
 * 	public InputPower(ElectricPotentialVolts voltage, ElectricCurrentAmps current, String name, Long id)
 * 	{
 * 		super(name, id);
 * 	}
 * 
 * 	&#64;Override
 * 	public void createAttributes()
 * 	{
 * 		voltage = new ElectricPotentialVolts(120);
 * 		current = new ElectricCurrentAmps(15);
 * 	}
 * 
 * 	&#64;Override
 * 	protected void createRequirements()
 * 	{
 * 		standardPowerRequirement = SystemRequirements.id3_2_1;
 * 	}
 * }
 * </pre>
 * 
 * @author ModelerOne
 */
public abstract class SysMLItem extends SysMLOccurrence
{
	/**
	 * Optional state machine that immplements the part's SysML state behavior. The
	 * {@code stateMachine} is created in an override of the
	 * {@code createStateMachine()} method.
	 */
	@StateMachine
	public Optional<? extends SysMLStateMachine> stateMachine;

	/**
	 * Constructor for specified name and ID
	 * 
	 * @param name name to be associated with the item
	 * @param id   unique identifier for this item
	 */
	protected SysMLItem(String name, Long id)
	{
		super(name, id);
		stateMachine = Optional.empty();

		createAttributes();
		createItems();
		createParts();
		createSpatialExtent();
		createEvents();
		createStateMachine();
		createConstraintFunctions();
		createConstraintTexts();
		createConstraints();
		createRequirements();
		createCustomMetadatas();
	}

	/**
	 * Constructor for no initial values and ID
	 */
	protected SysMLItem()
	{
		super();
		stateMachine = Optional.empty();

		createAttributes();
		createItems();
		createParts();
		createSpatialExtent();
		createEvents();
		createStateMachine();
		createConstraintFunctions();
		createConstraints();
		createRequirements();
		createCustomMetadatas();
	}

	/**
	 * Overridable operation that creates and initializes the part's attributes.
	 * <p>
	 * Code format:
	 * 
	 * <pre>
	 	&#64;Attribute
		LubricantTypeAFlow inlet!Flow;
	 	&#64;Attribute
		LubricantTypeBFlow inlet2Flow;
	 	&#64;Attribute
	 	VolumeMetersCubed size;
	 	&#64;Attribute
		WeightNewtons weight;
		&#64;Attribute
		PowerWatts powerIn;
		&#64;Attribute
		HeatWatts heatOut;
			:
		protected void createAttributes()
		{
			inlet1Flow = new LubricantTypeAFlow(25);
			inlet2Flow = new LubricantTypeBFlow(2);
		 	VolumeMetersCubed size = new VolumeMetersCubed(0.588;
			WeightNewtons weight = new WeightNewtons(0.55);
			PowerWatts powerIn = new PowerWatts(65);
			HeatWatts heatOut = new HeatWatts(45);
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.attributetypes.SysMLAttributeType
	 */
	protected void createAttributes()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's (sub-)items. An
	 * exampe is as follows:
	 * 
	 * <pre>
		&#64;Item
		ItemTypeA myFirstItem;
		&#64;Item
		ItemTypeA myNextItem;
	
		&#64;Override
		protected void createItems()
		{
			myFirstItem = new ItemTypeA(&lt;initializer&gt;);
			myNextItem = new ItemTypeB(&lt;initializer&gt;);
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.items.SysMLItem
	 */
	protected void createItems()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's parts. An
	 * exampe is as follows:
	 * 
	 * <pre>
		&#64;Part
		PartTypeA myFirstPart;
		&#64;Part
		PartTypeA myNextPart;
	
		&#64;Override
		protected void createParts()
		{
			myFirstPart = new PartTypeA(&lt;initializer&gt;);
			myNextPart = new PartTypeB(&lt;initializer&gt;);
		}
	 * </pre>
	 * 
	 * @see sysmlinjava.parts.SysMLPart
	 */
	protected void createParts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's spatial extent.
	 * An example follows: <pre>{@code public class BigPipe extends SysMLPart {
	 * &#64;SpatialExtent SysMLSpatialExtent spatialExtent; : &#64Override;
	 * protected void createSpatialExtent() { spatialExtent = new Cylinder(new
	 * Point(0, 0, 0), new Vector(0, 0, 0), 5, 250); } : }}</pre> @see
	 * sysmlinjava.items.SysMLSpatialExtent.Box @see
	 * sysmlinjava.items.SysMLSpatialExtent.Cylinder @see
	 * sysmlinjava.items.SysMLSpatialExtent.Sphere @see
	 * sysmlinjava.items.SysMLSpatialExtent.Mesh
	 */
	protected void createSpatialExtent()
	{
	}

	/**
	 * Overridable operation that creates and initializes the part's declared
	 * events.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
		&#64;TimeEvent
		SysMLTimeEvent myTimeEvent;
		&#64;CallEvent
		MyCallEvent myCallEvent;
	
		protected void createEvents()
		{
		   myTimeEvent = new SysMLTimeEvent(&lt;initializer&gt;);
		   myCallEvent = new MyCallEvent(&lt;initializer&gt;);
		}
	 * </pre>
	 * <p>
	 * where the the targets of the assignment operations are the names of fields
	 * with the {@code &#64;TimeEvent} and {@code &#64;CallEvent} annotations and
	 * {@code new SysMLTimeEvent} and {@code MyCallEvent} are constructors of a
	 * specific time event and an extended/specialized call event, respectively.
	 * <p>
	 * <b>Note</b> this operation is likely seldom used as events are typically
	 * instantiated when the event occurs rather than as part of part construction -
	 * the exception being the {@code SysMLTimeEvent}, which is used to start timers
	 * as well as to indicate timer expiration.
	 * 
	 * @see sysmlinjava.events.SysMLEvent
	 * @see sysmlinjava.events.SysMLCallEvent
	 * @see sysmlinjava.events.SysMLChangeEvent
	 * @see sysmlinjava.events.SysMLCompletionEvent
	 * @see sysmlinjava.events.SysMLSignalEvent
	 * @see sysmlinjava.events.SysMLTimeEvent
	 */
	protected void createEvents()
	{
	}

	/**
	 * Overridable operation that creates/initializes the part's state machine, if
	 * any. (The default is for the part to have no state machine.) The {@code
	 * stateMachine} is declared as a variable of (@code SysMLItem}. This variable
	 * should be created/initialized in an override of the {@code
	 * createStateMachine()} method. An example follows: <pre>{@code public class
	 * MyItem extends SysMLItem { : &#64;Override protected void
	 * createStateMachine() { stateMachine = Optional.of(new
	 * MyItemsStateMachine(this)); } : }}</pre> @see
	 * sysmlinjava.states.SysMLStateMachine
	 */
	protected void createStateMachine()
	{
	}

	/**
	 * Overridable operation that creates and initializes the constraint function
	 * (lambda expressiuns) for the item's constraints. An example is as follows:
	 * <pre>{@code { &#64;Attribute int totalPower; &#64;Attribute int
	 * electricalPower; &#64;Attribute int mechanicalPower; &#64;Attribute int
	 * totalWeight; &#64;Attribute int componentWeight; &#64;Attribute int
	 * containerWeight; &#64;FunctionalInterface public interface NextConstraint
	 * extends SysMLAction.ActionBody { int totalWeight(int waterWeight, int
	 * containerWeight); } &#64;ConstraintFunction BasicConstraintFunction
	 * totalPowerOKFunction; &#64;ConstraintFunction NextConstraint
	 * totalWeightFunction; void createConstraintFunctions() { totalPowerOKFunction
	 * = (BasicConstraintFunction)() -> return totalPower > electricalPower +
	 * mechanicalPower); totalWeightFunction = (NextConstraint)(componentsWeight,
	 * containerWeight) -> { return componentsWeight + containerWeight;}); }
	 * }}</pre> @see sysmlinjava.constraint.SysMLConstraint
	 */
	protected void createConstraintFunctions()
	{
	}

	/**
	 * Overridable operation that creates and initializes the constraint texts
	 * (documentations) for the item's constraints. An example is as follows:
	 * <pre>{@code { &#64;Attribute int totalPower; &#64;Attribute int
	 * electricalPower; &#64;Attribute int mechanicalPower; &#64;Attribute int
	 * totalWeight; &#64;Attribute int componentWeight; &#64;Attribute int
	 * containerWeight; &#64;ConstraintText SysMLDocumentation totalPowerOKDoc;
	 * &#64;ConstraintText SysMLConstraintText totalWeightDoc; void
	 * createConstraintTexts() { totalPowerOKDoc = new
	 * SysMLDocumentation("totalPower > electricalPower + mechanicalPower");
	 * totalWeightDoc = new SysMLDocumentation("totalWeight = componentsWeight +
	 * containerWeight"); } }}</pre> @see sysmlinjava.constraint.SysMLConstraint
	 */
	protected void createConstraintTexts()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's constraints.
	 * <p> Code format: <pre>{@code { &#64;Constraint SysMLConstraint totalPowerOK;
	 * &#64;Constraint SysMLConstraint totalWeight; void createConstraints() {
	 * totalPowerOK = new SysMLConstraint(Optional.of(totalPowerOKFunction),
	 * totalPowerOKDoc, "TotalPowerOK", 0L); totalWeight = new
	 * SysMLConstraint(Optional.of(totalWeightFunction), totalWeightDoc,
	 * TotalWeight", oL }); } }}</pre> @see
	 * sysmlinjava.constraint.SysMLConstraintFunction @see
	 * sysmlinjava.annotations.SysMLDocumentation
	 */
	protected void createConstraints()
	{
	}

	/**
	 * Overridable operation that initializes the item's requirements. An example
	 * follows:
	 * 
	 * <pre>
		&#64;SatisfyRequirement
		SysMLRequirement itemsFirstRequirement;
		&#64;SatisfyRequirement
		SysMLRequirement itemsNextRequirement;
			:
		protected void createRequirements()
		{
			itemsFirstRequirement = SystemRequirements.1_3_2_4;
			itemsNextRequirement = SystemRequirements.1_3_2_5;
		}
	 * </pre>
	 * 
	 * <b>Note</b> that item requirements should be declared as described above,
	 * i.e. as references to instantiations of {@code SysMLRequirement}s declared
	 * and initialized in classes that extend/specialize the
	 * {@code SysMLRequirements} class. Requirements should <b>not</b> be
	 * defined/specified in the item itself. Requirement specifications are in the
	 * form of instances of the {@code SysMLRequirement} or
	 * specializations/extensions thereof. Specializations/extensions should be
	 * declared in a requirements package in the model, as suggested by the SysML
	 * standard. Instances of all requirements, be it of basic
	 * {@code SysMLRequirement}s or of extensions/specializations, should be
	 * declared as {@code static} instances in a class that is an extension of the
	 * {@code SysMLRequirements} class. Declaring all requirement instances in a
	 * single class conforms to the SysML standard of declaring requirement in a
	 * single collection/package. It also enables SysMLinJava tools to be used to
	 * manage and query the requirements.
	 * 
	 * @see sysmlinjava.requirements.SysMLRequirement
	 * @see sysmlinjava.requirements.SysMLRequirementsCollection
	 */

	protected void createRequirements()
	{
	}

	/**
	 * Overridable operation that creates and initializes the item's dependencies,
	 * if any. An example follows:
	 * 
	 * <pre>
		{
			&#64;Dependency
			SysMLDependency usage;
				:
			protected void createDependencies()
			{
				usage = new SysMLDependency(Optional.of(this.getClass()), SensorA.class);
			}
		}
	 * </pre>
	 */
	protected void createDependencies()
	{
	}

	/**
	 * Overridable operation that creates and initializes custom item metadata, i.e.
	 * metadata that is not one of the standard SysaML metadata types. (Standard
	 * metadata elements should be created in overrides of {@code create...()}
	 * methods in the {@code SysMLAnything} class. An example of creating custom
	 * metadata instances is as follows:
	 * 
	 * <pre>
		{
			&#64;Metadata
			SWAPMetadata swap;
				:
			protected void createCustomMetadata()
			{
				swap = new SWAPMetadata(25, 15, 15);
			}
		}
	 * </pre>
	 */
	protected void createCustomMetadatas()
	{
	}

	/**
	 * Name of state machine variable, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String stateMachineVariableName = "stateMachine";
	/**
	 * Name of method to create events, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createEventsMethodName = "createEvents";
	/**
	 * Name of method to create state machine, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createStateMachineMethodName = "createStateMachine";
	/**
	 * Name of method to create items's attributes, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createAttributesMethodName = "createAttributes";
	/**
	 * Name of method to create item's (sub)items, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createItemsMethodName = "createItems";
	/**
	 * Name of method to create parts, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createPartsMethodName = "createParts";
	/**
	 * Name of method to create spatial extent, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createSpatialExtentMethodName = "createSpatialExtent";
	/**
	 * Name of method to create constraint functions, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintFunctionsMethodName = "createConstraintFunctions";
	/**
	 * Name of method to create constraints, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String createConstraintTextsMethodName = "createConstraintTexts";
	/**
	 * Name of method to create constraint texts, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createConstraintsMethodName = "createConstraints";
	/**
	 * Name of method to create items's requirements, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createRequirementsMethodName = "createRequirements";
	/**
	 * Name of method to create items's dependencies, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createDependenciesMethodName = "createDependencies";
	/**
	 * Name of method to create items's customized metadata, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createCustomMetadataMethodName = "createCustomMetadatas";
}
