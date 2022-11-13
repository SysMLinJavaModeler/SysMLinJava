package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "state machine",
 * i.e. an instance of a {@code SysMLStateMachine} within the context of the
 * current {@code SysMLBlock} that conforms to the SysML state machine that is
 * typically defined by a state diagram. The state machine is initialized in a
 * {@code createStateMachine()} method.
 * <p>
 * <b>Note:</b>The {@code SysMLBlock} contains a field variable name
 * {@code stateMachine} of type {@code SysMLStateMachine} which should be used
 * for defining the block's state machine. However, if additional state machines
 * must be used by the block and/or by the block's primary state machine (as
 * part of compound states, sub-state machines, etc.), then this annotation may
 * be used to denote such declarations accordingly.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLStateMachine
 * @see sysmlinjava.blocks.SysMLBlock#stateMachine
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface StateMachine
{
}
