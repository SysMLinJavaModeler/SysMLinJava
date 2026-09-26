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
package sysmlinjava.metadata;

import sysmlinjava.attributetypes.ProbabilityPercent;
import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava's representation of SysML's causation metadata.
 * {@code SysMLCausation} allows for the specification of additional metadata
 * about a cause-effect
 * 
 * @author ModelerOne
 */
public final class SysMLCausation extends SysMLMetadata
{
	/**
	 * The model element associated with the cause-effect relationship
	 */
	public Class<? extends SysMLAnything> associatedElement;
	/**
	 * Whether all the causes are necessary for all the effects to occur. If this is
	 * false (the default), then some or all of the effects may still have occurred
	 * even if some of the causes did not.
	 */
	public Boolean isNecessary;
	/**
	 * Whether the causes were sufficient for all the effects to occur. If this is
	 * false (the default), then it may be the case that some other occurrences were
	 * also necessary for some or all of the effects to have occurred.
	 */
	public Boolean isSufficient;
	/**
	 * The probability that the causes will actually result in effects occurring.
	 */
	public ProbabilityPercent probability;

	/**
	 * @param associatedElement The model element associated with the cause-effect
	 *                          relationship
	 * @param isNecessary       Whether all the causes are necessary for all the
	 *                          effects to occur. If this is false (the default),
	 *                          then some or all of the effects may still have
	 *                          occurred even if some of the causes did not.
	 * @param isSufficient      Whether the causes were sufficient for all the
	 *                          effects to occur. If this is false (the default),
	 *                          then it may be the case that some other occurrences
	 *                          were also necessary for some or all of the effects
	 *                          to have occurred.
	 * @param probability       The probability that the causes will actually result
	 *                          in effects occurring.
	 */
	public SysMLCausation(Class<? extends SysMLAnything> associatedElement, Boolean isNecessary, Boolean isSufficient, SysMLLevel probability)
	{
		super("noname", 0L);
		this.associatedElement = associatedElement;
		this.isNecessary = isNecessary;
		this.isSufficient = isSufficient;
		this.probability = probability;
	}

	/**
	 * @param associatedElement The model element associated with the cause-effect
	 *                          relationship
	 * @param isNecessary       Whether all the causes are necessary for all the
	 *                          effects to occur. If this is false (the default),
	 *                          then some or all of the effects may still have
	 *                          occurred even if some of the causes did not.
	 * @param isSufficient      Whether the causes were sufficient for all the
	 *                          effects to occur. If this is false (the default),
	 *                          then it may be the case that some other occurrences
	 *                          were also necessary for some or all of the effects
	 *                          to have occurred.
	 * @param probability       The probability that the causes will actually result
	 *                          in effects occurring.
	 * @param name              unique name
	 * @param id                unique identifier
	 */
	public SysMLCausation(Class<? extends SysMLAnything> associatedElement, Boolean isNecessary, Boolean isSufficient, SysMLLevel probability, String name, Long id)
	{
		super(name, id);
		this.associatedElement = associatedElement;
		this.isNecessary = isNecessary;
		this.isSufficient = isSufficient;
		this.probability = probability;
	}
}
