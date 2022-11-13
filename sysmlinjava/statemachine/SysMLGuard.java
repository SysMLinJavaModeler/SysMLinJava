package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.events.SysMLEvent;

/**
 * SysMLinJava's representation of the SysML state machine transition's guard.
 * The {@code SysMLGuard} contains a {@code SysMLGuardCondition} which is a Java
 * statement of the logic that determines whether the guard allows the
 * transition to occur. Note the {@code SysMLGuard} cannot be extended and must
 * be used as-is to specify a SysML guard for a transition. An example
 * follows.
 * 
 * <pre>
		:
	&#64;Guard
	public SysMLGuard gasToLiquidGuard;
	&#64;Guard
	public SysMLGuard liquidToIceGuard;
	&#64;Guard
	public SysMLGuard iceToLiquidGuard;
	&#64;Guard
	public SysMLGuard liquidToGasGuard;
	&#64;Guard
	public SysMLGuard gasToDecomposedGuard;
		:
 	&#64;Override
	protected void createGuards()
	{
		gasToLiquidGuard = new SysMLGuard(contextBlock, gasToLiquidGuardCondition, "isGasToLiquid");
		liquidToIceGuard = new SysMLGuard(contextBlock, liquidToIceGuardCondition, "isLiquidToIce");
		iceToLiquidGuard = new SysMLGuard(contextBlock, iceToLiquidGuardCondition, "isIceToLiquid");
		liquidToGasGuard = new SysMLGuard(contextBlock, liquidToGasGuardCondition, "isLiquidToGas");
		gasToDecomposedGuard = new SysMLGuard(contextBlock, gasToDecomposedGuardCondition, "isGasToDecomposed");
	}
		:
 * </pre>
 * 
 * @author ModelerOne
 *
 * @see SysMLGuardCondition
 */
public final class SysMLGuard extends SysMLClass
{
	/**
	 * Optional contextBlock within whose context this guard is to perform.
	 */
	public Optional<? extends SysMLBlock> contextBlock;
	/**
	 * Optional specification of the condition to be satisfied for the guard to
	 * permit the hosting transition. Note an empty condition will be treated the
	 * same as an unsatisfied condition, i.e. {@code isSatisfied()} method will
	 * return false.
	 */
	public Optional<SysMLGuardCondition> condition;

	/**
	 * Constructor.
	 * 
	 * @param contextBlock The block in whose context this guard operates
	 * @param condition    The condition to be satisfied
	 * @param name         The name of the guard
	 */
	public SysMLGuard(Optional<? extends SysMLBlock> contextBlock, SysMLGuardCondition condition, String name)
	{
		super(name);
		this.contextBlock = contextBlock;
		this.condition = Optional.of(condition);
	}

	/**
	 * Boolean operation to be called by the state machine to determine if the guard
	 * condition is satisfied, therby enabling the hosting transition to occur. The
	 * operation will invoke the {@code SysMLGuardCondition}, if provided, or return
	 * false otherwise.
	 * 
	 * @param currentEvent Optional event that triggered the transition that hosts
	 *                     the guard
	 * @return Whether the condition is satisfied, i.e. the
	 *         {@code SysMLGuardCondition} is provided and satisfied or false
	 *         otherwise
	 */
	public boolean isSatisfied(Optional<? extends SysMLEvent> currentEvent)
	{
		boolean result = false;
		if (condition.isPresent())
			result = condition.get().isSatisfied(currentEvent, contextBlock);
		return result;
	}

	/**
	 * Provides the identityString for this guard, either the specified name, if
	 * provided, or the simple class name - {@code SysMLGuard}.
	 * 
	 * @return the guard's identity string.
	 */
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		return String.format("%s [name=%s, id=%s, condition=%n%s]", getClass().getSimpleName(), name, id, condition);
	}
}
