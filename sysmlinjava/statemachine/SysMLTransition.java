package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.events.SysMLEvent;
import sysmlinjava.events.SysMLSignalEvent;

/**
 * SysMLinJava's representation of the SysML state machine's transition. The
 * SysMLTransition defines the trigger, guard, and effect of a transition
 * between specified states or within a state.
 * <p>
 * The SysMLTransition automatically adds itself to it's previous state, i.e.
 * the state it is to transition from. It also provides a boolean
 * {@code guardConditionSatisfied()} operation that determines if the transition
 * is enabled under the current trigger and other conditions. The
 * SysMLStateMachine uses this transition membership in the state and the
 * {@code guardConditionSatisfied()} operation to determine which, if any,
 * transition to use to transition the state machine to the next state. An
 * example follows.
 * 
 * <pre>{@code
		:
	&#64;Transition
	public InitialTransition initialTransition;
	&#64;Transition
	public SysMLTransition initialAsGasTransition;
	&#64;Transition
	public SysMLTransition initialAsLiquidTransition;
	&#64;Transition
	public SysMLTransition initialAsIceTransition;
	&#64;Transition
	public SysMLTransition gasToLiquidTransition;
	&#64;Transition
	public SysMLTransition liquidToIceTransition;
	&#64;Transition
	public SysMLTransition iceToLiquidTransition;
	&#64;Transition
	public SysMLTransition liquidToGasTransition;
	&#64;Transition
	public SysMLTransition gasToDecomposedTransition;
	&#64;Transition
	public SysMLTransition decomposedToFinalTransition;
		:
	&#64;Override
	protected void createTransitions()
	{
		super.createTransitions();
		initialTransition = new InitialTransition(contextBlock, initialState, choice, "initial");
		initialAsGasTransition = new SysMLTransition(contextBlock, choice, gas, Optional.of(initialAsGasGuard), Optional.empty(), "InitialAsGas");
		initialAsLiquidTransition = new SysMLTransition(contextBlock, choice, liquid, Optional.of(initialAsLiquidGuard), Optional.empty(), "InitialAsLiquid");
		initialAsIceTransition = new SysMLTransition(contextBlock, choice, ice, Optional.of(initialAsIceGuard), Optional.empty(), "InitialAsIce");

		gasToLiquidTransition = new SysMLTransition(contextBlock, gas, liquid, Optional.of(TempChangeEvent.class), Optional.of(gasToLiquidGuard), Optional.empty(), "GasToLiquid", SysMLTransitionKind.external);
		liquidToIceTransition = new SysMLTransition(contextBlock, liquid, ice, Optional.of(TempChangeEvent.class), Optional.of(liquidToIceGuard), Optional.empty(), "LiquidToIce", SysMLTransitionKind.external);
		iceToLiquidTransition = new SysMLTransition(contextBlock, ice, liquid, Optional.of(TempChangeEvent.class), Optional.of(iceToLiquidGuard), Optional.empty(), "IceToLiquid", SysMLTransitionKind.external);
		liquidToGasTransition = new SysMLTransition(contextBlock, liquid, gas, Optional.of(TempChangeEvent.class), Optional.of(liquidToGasGuard), Optional.empty(), "LiquidToGas", SysMLTransitionKind.external);
		gasToDecomposedTransition = new SysMLTransition(contextBlock, gas, decomposed, Optional.of(TempChangeEvent.class), Optional.of(gasToDecomposedGuard), Optional.empty(), "GasToDecomposed", SysMLTransitionKind.external);
		decomposedToFinalTransition = new FinalTransition(contextBlock, decomposed, finalState, "DecomposedToFinal");
	}
	}
 * </pre>
 * 
 * @author ModelerOne
 */
public class SysMLTransition extends SysMLClass
{
	/**
	 * Optional block in whose context the transition resides
	 */
	public Optional<? extends SysMLBlock> contextBlock;
	/**
	 * Vertex representing the previous (source) state of the transition
	 */
	public SysMLVertex prevState;
	/**
	 * Vertex representing the next (destination) state of the transition
	 */
	public SysMLVertex nextState;
	/**
	 * Optional event representing the trigger for the transition
	 */
	public Optional<Class<? extends SysMLEvent>> trigger;
	/**
	 * Optional guard whose condition will enable or disable the transition
	 */
	public Optional<? extends SysMLGuard> guard;
	/**
	 * Optional effect to be invoked upon performance of the transition
	 */
	public Optional<? extends SysMLEffect> effect;
	/**
	 * The kind of transition
	 */
	public SysMLTransitionKind transitionKind;

	/**
	 * Constructor for maximum specification from state to state, i.e. optionally
	 * specified triggering event, guard, and effect. The transition kind is also
	 * specified.
	 * 
	 * @param contextBlock   Optional SysMLBlock that provides the context of the
	 *                       state machine
	 * @param prevState      The previous state of the transition, i.e. state to be
	 *                       transitioned from.
	 * @param nextState      The next state of the transition, i.e. state to be
	 *                       transitioned to.
	 * @param trigger        Optional trigger for the transition, i.e. the type of
	 *                       SysMLEvent that will trigger transition.
	 * @param guard          Optional guard for the transition, i.e. the condition
	 *                       to be satisfied to allow the transition.
	 * @param effect         Optional effect of the transition, i.e. the activity to
	 *                       be performed upon performance of the transition.
	 * @param name           The transition's name
	 * @param transitionKind The kind of transition, i.e. internal or external
	 */
	public SysMLTransition(Optional<? extends SysMLBlock> contextBlock, SysMLState prevState, SysMLState nextState, Optional<Class<? extends SysMLEvent>> trigger, Optional<SysMLGuard> guard, Optional<SysMLEffect> effect, String name, SysMLTransitionKind transitionKind)
	{
		super();
		this.contextBlock = contextBlock;
		this.prevState = prevState;
		this.prevState.addTransition(this);
		this.nextState = nextState;
		this.trigger = trigger;
		this.guard = guard;
		this.effect = effect;
		this.name = Optional.of(name);
		this.transitionKind = transitionKind;
	}

	/**
	 * Constructor for maximum specification from state to pseudo-state, i.e.
	 * optionally specified triggering event, guard, and effect. The transition kind
	 * is also specified.
	 * 
	 * @param contextBlock Optional SysMLBlock that provides the context of the
	 *                     state machine
	 * @param prevState    The previous state of the transition, i.e. state to be
	 *                     transitioned from.
	 * @param nextState    The next (pseudo)state of the transition, i.e.
	 *                     pseudo-state to be transitioned to.
	 * @param trigger      Optional trigger for the transition, i.e. the type of
	 *                     SysMLEvent that will trigger transition.
	 * @param guard        Optional guard for the transition, i.e. the condition to
	 *                     be satisfied to allow the transition.
	 * @param effect       Optional effect of the transition, i.e. the activity to
	 *                     be performed upon performance of the transition.
	 * @param name         The transition's name
	 */
	public SysMLTransition(Optional<? extends SysMLBlock> contextBlock, SysMLState prevState, SysMLPseudoState nextState, Optional<Class<? extends SysMLEvent>> trigger, Optional<SysMLGuard> guard, Optional<SysMLEffect> effect, String name)
	{
		super();
		this.contextBlock = contextBlock;
		this.prevState = prevState;
		this.prevState.addTransition(this);
		this.nextState = nextState;
		this.trigger = trigger;
		this.guard = guard;
		this.effect = effect;
		this.name = Optional.of(name);
		this.transitionKind = SysMLTransitionKind.external;
	}

	/**
	 * Constructor for maximum specification from pseudo-state to state, i.e.
	 * optionally specified triggering event, guard, and effect. The transition kind
	 * is also specified.
	 * 
	 * @param contextBlock Optional SysMLBlock that provides the context of the
	 *                     state machine
	 * @param prevState    The previous (pseudo)state of the transition, i.e.
	 *                     pseudo-state to be transitioned from.
	 * @param nextState    The next state of the transition, i.e. state to be
	 *                     transitioned to.
	 * @param guard        Optional guard for the transition, i.e. the condition to
	 *                     be satisfied to allow the transition.
	 * @param effect       Optional effect of the transition, i.e. the activity to
	 *                     be performed upon performance of the transition.
	 * @param name         The transition's name
	 */
	public SysMLTransition(Optional<? extends SysMLBlock> contextBlock, SysMLPseudoState prevState, SysMLState nextState, Optional<SysMLGuard> guard, Optional<SysMLEffect> effect, String name)
	{
		super();
		this.contextBlock = contextBlock;
		this.prevState = prevState;
		this.prevState.addTransition(this);
		this.nextState = nextState;
		this.trigger = Optional.empty();
		this.guard = guard;
		this.effect = effect;
		this.name = Optional.of(name);
		this.transitionKind = SysMLTransitionKind.external;
	}

	/**
	 * Constructor for maximum specification from pseudo-state to pseudo-state, i.e.
	 * optionally guard, and effect (no trigger event). specified.
	 * 
	 * @param contextBlock Optional SysMLBlock that provides the context of the
	 *                     state machine
	 * @param prevState    The previous (pseudo)state of the transition, i.e.
	 *                     pseudo-state to be transitioned from.
	 * @param nextState    The next state of the transition, i.e. state to be
	 *                     transitioned to.
	 * @param guard        Optional guard for the transition, i.e. the condition to
	 *                     be satisfied to allow the transition.
	 * @param effect       Optional effect of the transition, i.e. the activity to
	 *                     be performed upon performance of the transition.
	 * @param name         The transition's name
	 */
	public SysMLTransition(Optional<? extends SysMLBlock> contextBlock, SysMLPseudoState prevState, SysMLPseudoState nextState, Optional<SysMLGuard> guard, Optional<SysMLEffect> effect, String name)
	{
		super();
		this.contextBlock = contextBlock;
		this.prevState = prevState;
		this.prevState.addTransition(this);
		this.nextState = nextState;
		this.trigger = Optional.empty();
		this.guard = guard;
		this.effect = effect;
		this.name = Optional.of(name);
		this.transitionKind = SysMLTransitionKind.external;
	}

	/**
	 * Constructor for specification from initial state to state, i.e. optionally
	 * specified effect, but no trigger event nor guard. specified.
	 * 
	 * @param contextBlock Optional SysMLBlock that provides the context of the
	 *                     state machine
	 * @param prevState    The initial state of the transition, i.e. initial state
	 *                     to be transitioned from.
	 * @param nextState    The next state of the transition, i.e. state to be
	 *                     transitioned to.
	 * @param effect       Optional effect of the transition, i.e. the activity to
	 *                     be performed upon performance of the transition.
	 * @param name         The transition's name
	 */
	public SysMLTransition(Optional<? extends SysMLBlock> contextBlock, SysMLInitialState prevState, SysMLState nextState, Optional<SysMLEffect> effect, String name)
	{
		super();
		this.contextBlock = contextBlock;
		this.prevState = prevState;
		this.prevState.addTransition(this);
		this.nextState = nextState;
		this.trigger = Optional.empty();
		this.guard = Optional.empty();
		this.effect = effect;
		this.name = Optional.of(name);
		this.transitionKind = SysMLTransitionKind.external;
	}

	/**
	 * Constructor for specification from initial state to a pseudo-state, i.e.
	 * optionally specified effect, but no trigger event nor guard. specified.
	 * 
	 * @param contextBlock Optional SysMLBlock that provides the context of the
	 *                     state machine
	 * @param prevState    The initial state of the transition, i.e. initial state
	 *                     to be transitioned from.
	 * @param nextState    The next (pseudo) state of the transition, i.e.
	 *                     pseudo-state to be transitioned to.
	 * @param effect       Optional effect of the transition, i.e. the activity to
	 *                     be performed upon performance of the transition.
	 * @param name         The transition's name
	 */
	public SysMLTransition(Optional<? extends SysMLBlock> contextBlock, SysMLInitialState prevState, SysMLPseudoState nextState, Optional<SysMLEffect> effect, String name)
	{
		super();
		this.contextBlock = contextBlock;
		this.prevState = prevState;
		this.prevState.addTransition(this);
		this.nextState = nextState;
		this.trigger = Optional.empty();
		this.guard = Optional.empty();
		this.effect = effect;
		this.name = Optional.of(name);
		this.transitionKind = SysMLTransitionKind.external;
	}

	/**
	 * Constructor for automatic transition, i.e. the transition occurs without a
	 * trigger or guard condition. The transition kind is defaulted to "external".
	 * 
	 * @param contextBlock Optional SysMLBlock that provides the context of the
	 *                     state machine
	 * @param prevState    The previous state of the transition, i.e. state to be
	 *                     transitioned from.
	 * @param nextState    The next state of the transition, i.e. state to be
	 *                     transitioned to.
	 * @param name         The transition's name
	 */
	public SysMLTransition(Optional<? extends SysMLBlock> contextBlock, SysMLState prevState, SysMLState nextState, String name)
	{
		super();
		this.contextBlock = contextBlock;
		this.prevState = prevState;
		this.prevState.addTransition(this);
		this.nextState = nextState;
		this.trigger = Optional.empty();
		this.guard = Optional.empty();
		this.effect = Optional.empty();
		this.name = Optional.of(name);
		this.transitionKind = SysMLTransitionKind.external;
	}

	/**
	 * Boolean operation indicating whether the transition's guard condition is
	 * satisfied for the current event.
	 * 
	 * @param currentEvent The current event.
	 * @return Whether the guard condition is satisfied for the current event.
	 */
	public boolean guardConditionSatisfied(Optional<? extends SysMLSignalEvent> currentEvent)
	{
		boolean result = true;
		if (trigger.isPresent())
			if (guard.isPresent())
				result = (currentEvent.getClass().equals(trigger.get())) && guard.get().isSatisfied(currentEvent);
			else
				result = (currentEvent.getClass().equals(trigger.get()));
		else if (guard.isPresent())
			result = guard.get().isSatisfied(currentEvent);
		return result;
	}

	/**
	 * Returns the identity string for this transition. The identity string is
	 * either the optional name set via the constructor or, if not set, the name of
	 * the transition class.
	 * 
	 * @return The transitions identity String
	 */
	public String identityString()
	{
		return name.isPresent() ? name.get() : getClass().getSimpleName();
	}

	@Override
	public String toString()
	{
		return String.format("SysMLTransition [contextBlock=%s, trigger=%s, guard=%s, effect=%s, prevState=%s, nextState=%s, transitionKind=%s, name=%s, id=%s]", contextBlock, trigger, guard, effect, prevState, nextState, transitionKind,
			name, id);
	}

}
