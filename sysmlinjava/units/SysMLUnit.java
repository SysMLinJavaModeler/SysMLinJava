package sysmlinjava.units;

import java.util.Optional;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.quantitykinds.SysMLQuantityKind;

/**
 * SysMLinJava's representation of the SysML unit. {@code SysMLUnit} includes
 * the standard attributes of the standard SysML unit, i.e. {@code name},
 * {@code symbol}, {@code description}, {@code definitionURI}, and
 * {@code quantityKind}.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLUnit extends SysMLClass
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