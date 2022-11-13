package sysmlinjava.statemachine;

/**
 * SysMLinJava representation of the SysML enumeration of the kinds of
 * pseudo-states that can be used in a state machine. SysMLinJava currently only
 * supports the initial, choice, and junction types of pseudo-states, as
 * reflected in this enumeration. To the extend that SysMLinJava's state machine
 * classes have no need for this enumeration, it is not used anywhere in
 * SysMLinJava and is provided only as a convenience for customized modeling.
 *
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLInitialState
 * @see sysmlinjava.statemachine.SysMLChoicePseudoState
 * @see sysmlinjava.statemachine.SysMLJunctionPseudoState
 */
public enum SysMLPseudoStateKind
{
	/** Initial pseudo-state */
	initial,
	/** Choice pseudo-state */
	choice,
	/** Junction pseudo-state */
	junction;
}
