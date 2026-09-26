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

/**
 * SysMLinJava's representation of the SysML risk metadata
 * 
 * @author ModelerOne
 */
public final class SysMLRisk extends SysMLMetadata
{
	/**
	 * Level of cost risk
	 */
	public SysMLRiskLevel costRisk;
	/**
	 * Level of schedule risk
	 */
	public SysMLRiskLevel scheduleRisk;
	/**
	 * Level of technical risk
	 */
	public SysMLRiskLevel technicalRisk;
	/**
	 * Level of total risk
	 */
	public SysMLRiskLevel totalRisk;

	/**
	 * Constructor for initial values
	 * 
	 * @param costRisk      level of cost risk
	 * @param scheduleRisk  level of schedule risk
	 * @param technicalRisk level of technical risk
	 * @param totalRisk     level of total risk
	 */
	public SysMLRisk(SysMLRiskLevel costRisk, SysMLRiskLevel scheduleRisk, SysMLRiskLevel technicalRisk, SysMLRiskLevel totalRisk)
	{
		super("noname", 0L);
		this.costRisk = costRisk;
		this.scheduleRisk = scheduleRisk;
		this.technicalRisk = technicalRisk;
		this.totalRisk = totalRisk;
	}

	/**
	 * Constructor for initial values and name, ID
	 * 
	 * @param costRisk      level of cost risk
	 * @param scheduleRisk  level of schedule risk
	 * @param technicalRisk level of technical risk
	 * @param totalRisk     level of total risk
	 * @param name          unique name
	 * @param id            unique identifier
	 */
	public SysMLRisk(SysMLRiskLevel costRisk, SysMLRiskLevel scheduleRisk, SysMLRiskLevel technicalRisk, SysMLRiskLevel totalRisk, String name, long id)
	{
		super(name, id);
		this.costRisk = costRisk;
		this.scheduleRisk = scheduleRisk;
		this.technicalRisk = technicalRisk;
		this.totalRisk = totalRisk;
	}
}
