package sysmlinjava.requirements;

/**
 * SysMLinJava representation of SysML's requirement statuses for formal system
 * requirements specifications.
 * 
 * @author ModelerOne
 *
 */
public enum RequirementStatusEnum
{
	/** Requirement is proposed for inclusion in the set of requirements */
	Proposed,
	/** Requirement has been reviewed for inclusion in the set of requirements */
	Reviewed,
	/**
	 * Requirement has undergone an iteration of change before or after inclusion in
	 * the set of requirements
	 */
	Iterated,
	/** Requirement is approved for and is included in the set of requirements */
	Approved;
}
