package sysmlinjava.kinds;

/**
 * SysMLinJava's representation of SysML's enumeration of the flow directions,
 * i.e. the {@code flowDirectionKind}
 * 
 * @author ModelerOne
 *
 */
public enum SysMLFlowDirectionKind
{
	/**
	 * Flow is in to the object of the flow
	 */
	in,
	/**
	 * Flow is out of the object of the flow
	 */
	out,
	/**
	 * Flow is both into and out of the object of the flow
	 */
	inout;
}
