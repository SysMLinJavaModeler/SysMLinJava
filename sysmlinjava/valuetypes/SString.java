package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the SysML String implemented with a standard Java
 * String.
 * 
 * @author ModelerOne
 *
 */
public class SString extends SysMLValueType
{
	/**
	 * Attribute for the value of the string as a java String type
	 */
	@Attribute
	public String value;

	/**
	 * Constructor
	 * 
	 * @param value java string to be used as initial value
	 */
	public SString(String value)
	{
		super();
		this.value = value;
	}

	/**
	 * Constructor - copied
	 * 
	 * @param copied instance to be used for initial value of copuy
	 */
	public SString(SString copied)
	{
		super(copied);
		this.value = copied.value;
	}

	/**
	 * Returns the java string value of this instance
	 * 
	 * @return value as a java string
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets this value to the specified java value
	 * 
	 * @param value java string value to be set as this value
	 */
	public void setValue(String value)
	{
		this.value = value;
		notifyValueChangeObservers();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof SString)
			return value.equals(((SString)obj).value);
		else
			return false;
	}

	/**
	 * Returns new instance that is value of the specified java string
	 * 
	 * @param string java string value used as initial value of new instance
	 * @return new instance that is value of the specified java string
	 */
	public static SString valueOf(String string)
	{
		return new SString(string);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Characters;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("SString [value=");
		builder.append(value);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}