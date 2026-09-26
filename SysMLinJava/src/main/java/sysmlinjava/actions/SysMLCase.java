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
package sysmlinjava.actions;

import java.util.List;
import java.util.Optional;

import sysmlinjava.javaannotations.actions.Objective;
import sysmlinjava.javaannotations.occurrences.Subject;
import sysmlinjava.javaannotations.parts.Actor;
import sysmlinjava.occurrences.SysMLOccurrence;
import sysmlinjava.parts.SysMLPart;
import sysmlinjava.requirements.SysMLRequirement;

/**
 * SysMLinJava representation of the SysML case. {@code SysMLCase} is a
 * {@code SysMLCalculation} that must be extended for the particular type of
 * case, e.g. verification, analysis, use, etc. Extensions must define a subject
 * and objective for the case and, optionally, one or more actors. The subject
 * and objective are declared in the base {@code SysMLCase} and should be
 * initialized/created in overrirdes of the respective {@code createSubject()}
 * and {@code createObjective()} methods. Actors must be declared as individual
 * &#64;{@code Actor} annotated fields and initialized/created in an override of
 * the {@code createActors()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.verifications.SysMLVerificationCase
 * @see sysmlinjava.analysis.SysMLAnalysisCase
 * @see sysmlinjava.analysis.ParametricAnalysisCase
 * @see sysmlinjava.analysis.ObjectiveFunctionAnalysisCase
 */
public abstract class SysMLCase extends SysMLCalculation
{
	/**
	 * Objective of the case specified as a requirement to be satisfied by
	 * performance of the case.
	 */
	@Objective
	public SysMLRequirement objective;

	/**
	 * Optional class of the subject of the case, i.e. any part, action, port, or
	 * other occurrence that is the subject of the case.
	 */
	@Subject
	public Optional<Class<? extends SysMLOccurrence>> subject;

	/**
	 * List of actors of the case, i.e. class of any part that interacts with the
	 * subject
	 */
	@Actor
	public List<Class<? extends SysMLPart>> actors;

	/**
	 * Constructor for use by extension classes that define the case in overrides of
	 * the {@code createSnapshots()}, {@code createIsIndividual()},
	 * {@code createFunction()}, {@code createSubActionFunctions()},
	 * {@code createSubActions()}, {@code createSubject()},
	 * {@code createObjective()}, and {@code createActors()} methods.
	 * 
	 * @param name unique name of the case
	 * @param id   unique numerical id of the case
	 */
	protected SysMLCase(String name, Long id)
	{
		super(name, id);

		createSubject();
		createObjective();
		createActors();
	}

	/**
	 * Creates the objective of the case. An example follows:
	 * 
	 * <pre>
	 * {@code
		public class MyCase extends SysMLCase
		{
				:
			&#64;Override
			protected void createObjective()
			{
				objective = new SysMLRequirement("1", "Interface Analysis", "Analysis shall verify protocol connections operate correctly");
			}
				:
		}
		}</pre>
	 */
	protected abstract void createObjective();

	/**
	 * Creates the subject of the case. An example follows:
	 * 
	 * <pre>
	 * {@code
		public class MyCase extends SysMLCase
		{
				:
			&#64;Override
			protected void createSubject()
			{
				subject = Optional.of(SystemAlpha.class);
			}
				:
		}
		}</pre>
	 */
	protected abstract void createSubject();

	/**
	 * Creates the list of parts that are actors of the case. An example follows:
	 * 
	 * <pre>
	 * {@code
		public class MyVerificationCase extends SysMLCase
		{
				:
			&#64;Override
			protected void createActors()
			{
				actors = List.of(SystemOperator.class, TargetSystem.class);
			}
				:
		}
		}</pre>
	 */
	protected abstract void createActors();

	/**
	 * Name of variable for case's objective, used by SysMLinJava tools, typically
	 * not needed for modeling
	 */
	public static final String objectiveVariableName = "objective";
	/**
	 * Name of variable for case's subject, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String subjectVariableName = "subject";
	/**
	 * Name of variable for case's actors, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String actorsVariableName = "actors";
	/**
	 * Name of method to create subject, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createSubjectMethodName = "createSubject";
	/**
	 * Name of method to create objective, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createObjectiveMethodName = "createObjective";
	/**
	 * Name of method to create actors, used by SysMLinJava tools, typically not
	 * needed for modeling
	 */
	public static final String createActorsMethodName = "createActors";
}
