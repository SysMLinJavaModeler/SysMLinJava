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
package sysmlinjava.parts;

/**
 * SysMLinJava representation of the SysML stakeholder. The
 * {@code SysMLStakeholder} is a specialized {@code SysMLPart} that specifies
 * the stakeholder's name and a list of concerns associated with the
 * stakeholder.
 * <p>
 * {@code SysMLStakeholder} is used by the @{@code SysMLRequirement} and
 * {@code SysMLViewpoint}. Instances of the {@code SysMLStakeholder} should be
 * declared in extensions of the {@code SysMLRequirements} and
 * {@code SysMLViewPoints} class. See those classes for more info on how to
 * declare the {@code SysMLStakeholder} instances.
 * 
 * @author ModelerOne
 * @see sysmlinjava.requirements.SysMLRequirementsCollection
 * @see sysmlinjava.viewpoints.SysMLViewpointsCollection
 */
public abstract class SysMLStakeholder extends SysMLPart
{
	/**
	 * Constructor
	 * 
	 * @param name unique name of this stakeholder
	 * @param id   unique identifier of this stakeholder
	 */
	public SysMLStakeholder(String name, Long id)
	{
		super(name, id);
		createConcerns();
	}

	/**
	 * Creates the concerns associated with this stakeholdr. Concerns must be
	 * created/initialized as references to {@code SysMLConcern} instances in
	 * extensions of {@code SysMLConcernCollections}. An example follows:
	 * 
	 * <pre>
		public class UserStakeholder extends SysMLStakeholder
		{
			&#64;Concern
			SysMLConcern usabilityConcern;
			&#64;Concern
			SysMLConcern performanceConcern;
			&#64;Concern
			SysMLConcern qualityConcern;
				:
			&#64;Override
			protected void createConcerns()
			{
				usabilityConcern = SystemConcerns.usabilityConcern;
				performanceConcern = SystemConcerns.performanceConcern;
				qualityConcern = SystemConcerns.qualityConcerns;
			}
				:
		}
	 * </pre>
	 */
	protected void createConcerns()
	{
	}

	/**
	 * Name of method to create concerns of the stakeholder, used by SysMLinJava
	 * tools, typically not needed for modeling
	 */
	public static final String createConcernsMethodName = "createConcerns";
}
