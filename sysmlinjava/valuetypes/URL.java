package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for uniform resource locator (URL) as a specialized
 * string
 * 
 * @author ModelerOne
 *
 */
public class URL extends SString
{
	/**
	 * Constructor
	 * 
	 * @param url string value for the initial value of the URL
	 */
	public URL(String url)
	{
		super(url);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Characters;
	}
}
