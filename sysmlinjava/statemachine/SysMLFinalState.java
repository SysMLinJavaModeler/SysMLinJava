package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;

/**
 * SysMLinJava's representation of the SysML final state of a state machine. The
 * {@code SysMLFinalState} extends the {@code SysMLState} requiring
 * specification of the context block and a name. Transitions to the
 * {@code SysMLFinalState} must be declared in the state machine's transition
 * fields and initialized with the {@code SysMLFinalState} as the nextState in
 * the state machines {@code createTransitions()} method.
 * <p>
 * Note that transitions out of and within the {@code SysMLState} as well as
 * elements such as the {@code onEnterActivity}, {@code doActivity}, and
 * {@code onExitActivity}, are not applicable for the FinalState.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLFinalState extends SysMLState
{
	/**
	 * Constructor
	 * 
	 * @param contextBlock the block in whose context the final state is to reside
	 * @param name         unique name for the final state
	 */
	public SysMLFinalState(Optional<? extends SysMLBlock> contextBlock, String name)
	{
		super(contextBlock, name);
	}

	@Override
	public void addTransition(SysMLTransition transition)
	{
		logger.warning("illegal attempt to add transition from final state; ignored: " + transition.identityString());
	}
}