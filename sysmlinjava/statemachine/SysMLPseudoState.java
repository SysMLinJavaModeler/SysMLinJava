package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;

/**
 * SysMLinJava representation of the SysML state machine's pseudoState. The
 * {@code SysMLPseudoState} is used solely as a base class for SysML
 * pseudo-state classes for junction, choice, initial state, etc. Modelers
 * should have no need to use this class.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLPseudoState extends SysMLVertex
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock block in whose context this pseudo-state resides
	 * @param name         unique name of the pseudo-state.
	 */
	public SysMLPseudoState(Optional<? extends SysMLBlock> contextBlock, String name)
	{
		super(contextBlock, name);
	}

}
