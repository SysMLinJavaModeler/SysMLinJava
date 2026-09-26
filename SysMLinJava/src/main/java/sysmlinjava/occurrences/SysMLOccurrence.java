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
package sysmlinjava.occurrences;

import sysmlinjava.common.SysMLAnything;
import sysmlinjava.javaannotations.occurrences.Individual;

/**
 * SysMLinJava representation of the SysML occurrence. The
 * {@code SysMLOccurrence} extends the {@code SysMLAnything} with optional
 * life-cycle information, i.e. sets of time slices and snapshots, and and
 * indication whether this occurrence is an individual, i.e. a single real or
 * perceived object with a unique identity.
 * <h2>Create/initialize methods</h2> The {@code SysMLOccurrence} provides 2
 * overrideable method calls to create the start and done times (time slices)
 * and/or instantaneous times (snapshots) of the occurence. It provides a 3rd
 * operation to indicate whether this occurence is an individual one. The
 * {@code SysMLOccurrence} constructor, presumably invoked by extending
 * classesd, automatically invokes these methods to create the times and boolean
 * so that extensions to the {@code SysMLOccurrence} need only override the
 * {@code createXxxx()} methods for the applicable property (fields) declared in
 * the class to ensure the properties are created/initialized in the correct and
 * complete sequence needed. The overridable {@code createXxxx()} methods
 * provide a "reminder" to create and initialize the class's attributes and
 * comments as well as a framework for their complete definition.
 *
 * @author ModelerOne
 */
public abstract class SysMLOccurrence extends SysMLAnything
{
	/**
	 * Whether the occurrence is an indidual one, or not.
	 */
	@Individual
	public Boolean isIndividual;

	/**
	 * Constructor for name, id only
	 * 
	 * @param name name for the class
	 * @param id   unique identifier of the class
	 */
	public SysMLOccurrence(String name, Long id)
	{
		super(name, id);
		isIndividual = true;

		createTimeSlices();
		createSnapshots();
		createIsIndividual();
	}

	/**
	 * Constructor
	 */
	public SysMLOccurrence()
	{
		super();
		isIndividual = true;

		createTimeSlices();
		createSnapshots();
		createIsIndividual();
	}

	/**
	 * Overridable operation that creates and initializes the occurrence's lifetime,
	 * i.e. its start and done times. An example follows:
	 * 
	 * <pre>{@code
		{
			&#64;TimeSlice
			public SysMLTimeSlice preflight;
			&#64;TimeSlice
			public SysMLTimeSlice inflight;
			&#64;TimeSlice
			public SysMLTimeSlice postflight;
			
	 			:
			protected void createTimeSlices()
			{
				preflight = new SysMLTimeSlice(Instant.now(), Instant.now().plus( 30, MINUTES),  "preflight", 1L),
				inflight = new new SysMLTimeSlice(Instant.now(), Instant.now().plus(120, MINUTES),   "inflight", 2L),
				postflight = new new SysMLTimeSlice(Instant.now(), Instant.now().plus( 30, MINUTES), "postflight", 3L));
			}
	 			:
		}}</pre>
	 */
	protected void createTimeSlices()
	{
	}

	/**
	 * Overridable operation that creates and initializes the occurrence's lifetime
	 * snapshots. An example is as follows.
	 * 
	 * <pre>{@code
	 	{
			&#64;Snapshot
			public SysMLSnapshot departGate;
			&#64;Snapshot
			public SysMLSnapshot takeoff;
			&#64;Snapshot
			public SysMLSnapshot land;
			&#64;Snapshot
			public SysMLSnapshot arriveGate;
	 			:
			protected void createSnapshots()
			{
				departGate = new SysMLSnapshot("departGate", 1L, Instant.now()),
				takeoff = new SysMLSnapshot("takeOff", 2L, Instant.now().plus(15, MINUTES)),
				land = new SysMLSnapshot("land", 3L, Instant.now().plus(120, MINUTES)),
				arriveGate = new SysMLSnapshot("arriveGate", 4L, Instant.now().plus(10, MINUTES)));
			}
	 			:
	 	}}</pre>
	 */
	protected void createSnapshots()
	{
	}

	/**
	 * Overridable operation that creates and initializes the occurrence's
	 * "individual" status, i.e. its status as an individual occurrence with no
	 * others before or after. If the value is to be true, no override is necessary.
	 * An example follows:
	 * 
	 * <pre>{@code
		{
				:
			protected void createIsIndividual()
			{
				isIndividual = false;
			}
				:
		}}</pre>
	 */
	protected void createIsIndividual()
	{
	}

	/**
	 * Name of is-individual variable, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String isIndividualVariableName = "isIndividual";

	/**
	 * Name of method to create lifetime time slices, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createTimeSlicesMethodName = "createTimeSlices";
	/**
	 * Name of method to create lifetime snapshots, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createSnapshotsMethodName = "createSnapshots";
	/**
	 * Name of method to create individual indication, used by SysMLinJava tools,
	 * typically not needed for modeling
	 */
	public static final String createIsIndividualMethodName = "createIsIndividual";
}
