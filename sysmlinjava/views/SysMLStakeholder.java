package sysmlinjava.views;

import java.util.List;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava representation of the SysML stakeholder. The
 * {@code SysMLStakeholder} specifies the stakeholder's name and a list of
 * concerns associated with the stakeholder.
 * <p>
 * {@code SysMLStakeholder} is used by the {@code SysMLViewpoint}. Instances of
 * the {@code SysMLStakeholder} should be declared in an extension of the
 * {@code SysMLViews} class. See that class for more info on how to declare the
 * {@code SysMLStakeholder} instances.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLStakeholder extends SysMLClass
{
	/**
	 * List of the concerns associated with this stakeholder
	 */
	public List<String> concernList;

	/**
	 * Constructor
	 * 
	 * @param name        stakeholder's name
	 * @param concernList stakeholder's concerns as list of strings
	 */
	public SysMLStakeholder(String name, List<String> concernList)
	{
		super(name);
		this.concernList = concernList;
	}
}
