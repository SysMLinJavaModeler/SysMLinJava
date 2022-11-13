package sysmlinjava.comments;

import java.util.List;
import java.util.Optional;
import sysmlinjava.quantitykinds.SysMLinJavaQuantityKinds;
import sysmlinjava.units.SysMLinJavaUnits;
import sysmlinjava.valuetypes.BBoolean;
import sysmlinjava.valuetypes.Complex;
import sysmlinjava.valuetypes.IInteger;
import sysmlinjava.valuetypes.RReal;
import sysmlinjava.valuetypes.SString;

/**
 * SysMLinJava's representation of the SysML element group.
 * {@code SysMLElementGroup} is a named and described collection of model
 * elements which are of interest as a group. The {@code SysMLElementGroup} is
 * typically declared as a &#64;{@code ElementGroup} annotated field in the
 * SysML element in which the element group is defined (typically in an element
 * that has access to all of the elements in the group, e.g. a domain, system,
 * or subsystem type of element). The field must be instantiated/initialized in
 * the {@code createElementGroups()} method of the extended {@code SysMLBlock}
 * in which it is declared.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLElementGroup extends SysMLComment
{
	/**
	 * Name of the element group
	 */
	public String name;
	/**
	 * Criterion for membership in the element group
	 */
	public String criterion;
	/**
	 * List of the types (classes) that are members of the element group
	 */
	public Optional<List<Class<?>>> typeMembers;
	/**
	 * List of the class instances that are members of the element group
	 */
	public Optional<List<? extends Object>> instanceMembers;

	/**
	 * Constructor
	 * 
	 * @param name            name of the element group
	 * @param text            text description of the element group
	 * @param criterion       criterion for membership in the element group
	 * @param typeMembers     optional list of members as types (classes)
	 * @param instanceMembers optional list of members as instances (objects)
	 */
	public SysMLElementGroup(String name, String text, String criterion, Optional<List<Class<?>>> typeMembers, Optional<List<? extends Object>> instanceMembers)
	{
		super(text);
		this.name = name;
		this.criterion = criterion;
		this.typeMembers = typeMembers;
		this.instanceMembers = instanceMembers;
	}

	/**
	 * Constant instance for the elements in SysMLinJava that represent the SysML
	 * primitive types
	 */
	public static final SysMLElementGroup sysMLinJavaBaseValueTypes = new SysMLElementGroup("SysMLinJavaBaseValueTypes", "SysMLinJava's base valueTypes", "Classes for basic types (extensions) of SysMLValueTypes",
		Optional.of(List.of(RReal.class, IInteger.class, BBoolean.class, SString.class, Complex.class)), Optional.empty());
	/**
	 * Constant instance for the elements in the {@code SysMLinJavaUnits} class that
	 * represent the set SysML units provided by SysMLinJava
	 */
	public static final SysMLElementGroup sysMLinJavaUnits = new SysMLElementGroup("SysMLinJavaUnits", "SysMLinJava's units", "Instance of the SysMLUnit", Optional.empty(), Optional.of(SysMLinJavaUnits.instances));
	/**
	 * Constant instance for the elements in the {@code SysMLinJavaQuantityKinds}
	 * class that represent the set SysML quantityKinds provided by SysMLinJava
	 */
	public static final SysMLElementGroup sysMLinJavaQuantityKinds = new SysMLElementGroup("SysMLinJavaQuantityKinds", "SysMLinJava's quantityKinds", "Instance of the SysMLQuantityKind", Optional.empty(),
		Optional.of(SysMLinJavaQuantityKinds.instances));
}
