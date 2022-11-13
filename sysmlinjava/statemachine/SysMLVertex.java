package sysmlinjava.statemachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava representation of the SysML state machine's vertex. The
 * {@code SysMLVertex} is used solely as a base class for SysML state and
 * pseudo-state classes. Modelers should have no use for this class.
 * 
 * @author ModelerOne
 *
 */
public abstract class SysMLVertex extends SysMLClass
{
	/**
	 * Optional context block for which the state machine, and hence the state,
	 * executes. Activities performed while in the state may access the context
	 * block's properties as needed.
	 */
	public Optional<? extends SysMLBlock> contextBlock;
	/**
	 * Transitions out of and/or within the state.
	 */
	public List<SysMLTransition> transitions;

	/**
	 * Maximum specified constructor of the SysMLState object.
	 * 
	 * @param contextBlock Optional SysMLBlock based context in which the state must
	 *                     operate.
	 * @param name         Optional name of the state.
	 */
	public SysMLVertex(Optional<? extends SysMLBlock> contextBlock, String name)
	{
		super();
		this.contextBlock = contextBlock;
		this.transitions = new ArrayList<>();
		this.name = Optional.of(name);
	}

	/**
	 * Adds the specified transition to the set of transitions out of and within the
	 * state
	 * 
	 * @param transition transition to be added
	 */
	public void addTransition(SysMLTransition transition)
	{
		transitions.add(transition);
	}

	/**
	 * Provides the identity string for this state, either the specified name, if
	 * provided, or the simple class name - SysMLState, if not.
	 * 
	 * @return the state's identity string.
	 */
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}
}
