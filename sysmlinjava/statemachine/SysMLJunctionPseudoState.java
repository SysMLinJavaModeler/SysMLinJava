package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;

/**
 * {@code SysMLJunctionPseudoState} is SysMLinJava's representation of the SysML
 * junction pseudo-state. As an extension of the {@code SysMLVertex} it is
 * assigned its transitions into and out of the "junction" by
 * {@code SysMLTransition} constructors invoked in an override of the
 * {@code SysMLStateMachine}'s {@code createTransitions()} operation.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLTransition
 * @see sysmlinjava.statemachine.SysMLStateMachine#createTransitions()
 */
public final class SysMLJunctionPseudoState extends SysMLPseudoState
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock block in whose context the state machine executes
	 * @param name         name of the "junction" pseudo-state
	 */
	public SysMLJunctionPseudoState(Optional<? extends SysMLBlock> contextBlock, String name)
	{
		super(contextBlock, name);
	}
}
