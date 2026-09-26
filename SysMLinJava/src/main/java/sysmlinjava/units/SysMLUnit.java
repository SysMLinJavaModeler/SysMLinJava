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
package sysmlinjava.units;

import java.util.Optional;
import sysmlinjava.common.SysMLAnything;
import sysmlinjava.quantitykinds.SysMLQuantityKind;

/**
 * SysMLinJava's representation of the SysML unit. {@code SysMLUnit} includes
 * the standard attributes of the standard SysML unit, i.e. {@code name},
 * {@code symbol}, {@code description}, {@code definitionURI}, and
 * {@code quantityKind}.
 * <p>
 * The unit is typically used by the SysML value type to define the units
 * associated with the specified value type.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.attributetypes.SysMLAttributeType#units
 * @see sysmlinjava.attributetypes.SysMLAttributeType#createUnits
 */
public final class SysMLUnit extends SysMLAnything
{
	/**
	 * Unit's name
	 */
	public final String name;
	/**
	 * Unit's symbol
	 */
	public final String symbol;
	/**
	 * Unit's description
	 */
	public final String description;
	/**
	 * Unit's definition URI
	 */
	public final String definitionURI;
	/**
	 * Unit's quantity kind (optional)
	 */
	public final Optional<SysMLQuantityKind> quantityKind;

	/**
	 * Constructor for all attributes
	 * 
	 * @param name          name of unit
	 * @param symbol        symbol of unit
	 * @param description   description of unit
	 * @param definitionURI URI of definition of unit
	 * @param quantityKind  Optional quantity kind of the unit
	 */
	public SysMLUnit(String name, String symbol, String description, String definitionURI, Optional<SysMLQuantityKind> quantityKind)
	{
		super();
		this.name = name;
		this.symbol = symbol;
		this.description = description;
		this.definitionURI = definitionURI;
		this.quantityKind = quantityKind;
	}

	@Override
	public String toString()
	{
		return String.format("SysMLUnit [name=%s, symbol=%s, description=%s, definitionURI=%s, quantityKind=%s]", name, symbol, description, definitionURI, quantityKind);
	}
}