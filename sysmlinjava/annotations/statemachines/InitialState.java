package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML initial state, i.e.
 * an instance of a {@code SysMLInitialState} that conforms to the standard
 * SysML initial state of a SysML state machine. The initial state is defined in
 * a {@code createPseudoStates()} method. Note that a {@code SysMLInitialState}
 * instance is declared and created in the {@code SysMLStateMachine} which is
 * inherited by all state machines.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLInitialState
 * @see sysmlinjava.statemachine.SysMLStateMachine#initialState
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface InitialState
{
}
