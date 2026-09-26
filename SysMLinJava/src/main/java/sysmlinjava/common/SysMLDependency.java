package sysmlinjava.common;

import java.util.List;

/**
 * SysMLinJavas representation of the SysML dependency. The dependency is
 * specified by variables that refer to the types of model element on which the
 * dependency exists
 */
public final class SysMLDependency extends SysMLAnything
{
	/**
	 * List of elements that are dependent on toElements
	 */
	public List<Class<? extends SysMLAnything>> fromElements;
	/**
	 * List of elements that fromElements are depended on
	 */
	public List<? extends SysMLAnything> toElements;

	/**
	 * Constructor
	 * 
	 * @param fromElements Class of element dependent on toElement
	 * @param toElements   Class of element depended on by fromElement
	 */
	public SysMLDependency(List<Class<? extends SysMLAnything>> fromElements, List<? extends SysMLAnything> toElements)
	{
		super();
		this.fromElements = fromElements;
		this.toElements = toElements;
	}
}
