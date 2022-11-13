package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;

/**
 * {@code SysMLInitialState} is SysMLinJava's representation of the SysML
 * initial state of a state machine. The {@code SysMLInitialState} extends the
 * {@code SysMLPseudoState} requiring specification of the context block and a
 * name.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLInitialState extends SysMLPseudoState
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock block in whose context the initial state is to reside
	 * @param name         unique name of the initial state
	 */
	public SysMLInitialState(Optional<? extends SysMLBlock> contextBlock, String name)
	{
		super(contextBlock, name);
	}

	@Override
	public void addTransition(SysMLTransition transition)
	{
		if (transitions.size() <= 1)
			super.addTransition(transition);
		else
			logger.warning("illegal attempt to add more than one transition from initial state; ignored: " + transition.getClass().getSimpleName());
	}
}