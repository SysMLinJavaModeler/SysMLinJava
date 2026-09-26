package sysmlinjava.requirements;

import sysmlinjava.constraint.SysMLConstraintFunction;
import sysmlinjava.occurrences.SysMLOccurrence;

/**
 * Functional interface for constraint function for requirement
 */
@FunctionalInterface
public interface SysMLRequirementConstraintFunction extends SysMLConstraintFunction
{
	/**
	 * Performs constraint on subject
	 * @param subject occurrence to which constraint is applied
	 * @return true if constraint is satisfied, false otherwise
	 */
	boolean constrained(Class<? extends SysMLOccurrence> subject);
}