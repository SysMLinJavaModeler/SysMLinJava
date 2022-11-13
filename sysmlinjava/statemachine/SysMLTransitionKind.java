package sysmlinjava.statemachine;

/**
 * SysMLinJava's representation of SysML's transition kind enumeration.
 * <b>Note:</b> SysMLinJava treats kinds {@code internal} and {@code local} as
 * equivalent.
 * 
 * @author ModelerOne
 *
 */
public enum SysMLTransitionKind
{
	/**
	 * External transition
	 */
	external,
	/**
	 * Internal transition
	 */
	internal,
	/**
	 * Local transition
	 */
	local;
}
