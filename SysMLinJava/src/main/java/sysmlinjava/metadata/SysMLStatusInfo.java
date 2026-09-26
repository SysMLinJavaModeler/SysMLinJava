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

import sysmlinjava.requirements.SysMLRiskKind;

/**
 * SysMLinJava's representation of the SysML status information metadata.
 * {@code SysMLStatusInfo} is a specialized type of {@code SysMLMetadata}. It
 * consists of a textual description of the status as well as the owner and
 * originator of the status and risk and status enumerations.
 * 
 * @author ModelerOne
 */
public final class SysMLStatusInfo extends SysMLMetadata
{
	/** text specification of the status */
	public String text;
	/** originator of status */
	public String originator;
	/** owner of the status */
	public String owner;
	/** current enumeration risk */
	public SysMLRiskKind risk;
	/** current status enumeration */
	public SysMLStatusKind status;

	/**
	 * Constructor for initial values
	 * 
	 * @param text       text specification of the status
	 * @param originator originator of status
	 * @param owner      owner of the status
	 * @param risk       current enumeration risk
	 * @param status     current status enumeration
	 */
	public SysMLStatusInfo(String text, String originator, String owner, SysMLRiskKind risk, SysMLStatusKind status)
	{
		super("noname", 0L);
		this.text = text;
		this.originator = originator;
		this.owner = owner;
		this.risk = risk;
		this.status = status;
	}
	/**
	 * Constructor for initial values and name, ID
	 * 
	 * @param text       text specification of the status
	 * @param originator originator of status
	 * @param owner      owner of the status
	 * @param risk       current enumeration risk
	 * @param status     current status enumeration
	 * @param name       unique name of status
	 * @param id         unique identifier of status
	 */
	public SysMLStatusInfo(String text, String originator, String owner, SysMLRiskKind risk, SysMLStatusKind status, String name, Long id)
	{
		super(name, id);
		this.text = text;
		this.originator = originator;
		this.owner = owner;
		this.risk = risk;
		this.status = status;
	}
}
