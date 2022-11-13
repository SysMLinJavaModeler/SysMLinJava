package sysmlinjava.quantitykinds;

import sysmlinjava.common.SysMLClass;

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
public final class SysMLQuantityKind extends SysMLClass
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
