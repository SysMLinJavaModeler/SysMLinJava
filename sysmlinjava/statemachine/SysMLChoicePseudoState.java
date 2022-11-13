package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;

/**
 * SysMLinJava's representation of the SysML choice pseudo-state. As an
 * extension of the {@code SysMLPseudoState} which is an extension of the
 * {@code SysMLVertex} it is assigned its transitions into and out of the "choice"
 * pseudo-state by {@code SysMLTransition} constructors invoked in an override
 * of the {@code SysMLStateMachine}'s {@code createTransitions()} operation.
 * 
 * @author ModelerOne
 * @see SysMLTransition
 * @see SysMLStateMachine#createTransitions()
 */
public final class SysMLChoicePseudoState extends SysMLPseudoState
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock block in whose context the state machine executes
	 * @param name         name of the "choice" pseudo-state
	 */
	public SysMLChoicePseudoState(Optional<? extends SysMLBlock> contextBlock, String name)
	{
		super(contextBlock, name);
	}
}
