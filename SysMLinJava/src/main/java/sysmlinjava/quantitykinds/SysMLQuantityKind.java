/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.quantitykinds;

import sysmlinjava.common.SysMLAnything;

/**
 * SysMLinJava representation of SysML's quantityKind. {@code SysMLQuantityKind}
 * specifies the attributes of SysML's quantityKind.
 * <p>
 * Note that the {@code SysMLQuantityKind} is self described by its individual
 * field values, i.e. {@code description} and {@code definitionURI}.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLQuantityKind extends SysMLAnything
{
	/**
	 * Name of the quantity kind
	 */
	public final String name;
	/**
	 * Symbol of the quantity kind
	 */
	public final String symbol;
	/**
	 * Description of the quantity kind
	 */
	public final String description;
	/**
	 * URI for the definition of the quantity kind
	 */
	public final String definitionURI;

	/**
	 * Constructor for all attributes
	 * 
	 * @param name          name of the quantity kind
	 * @param symbol        symbol of the quantity kind
	 * @param description   description of the quantity kind
	 * @param definitionURI URI of the definition of the quantity kind
	 */
	public SysMLQuantityKind(String name, String symbol, String description, String definitionURI)
	{
		super();
		this.name = name;
		this.symbol = symbol;
		this.description = description;
		this.definitionURI = definitionURI;
	}

	@Override
	public String toString()
	{
		return String.format("SysMLQuantityKind [name=%s, symbol=%s, description=%s, definitionURI=%s]", name, symbol, description, definitionURI);
	}
}
