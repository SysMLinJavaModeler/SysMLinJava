package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML final state, i.e. an
 * instance of a {@code SysMLFinalState} that conforms to the standard SysML
 * final state of a SysML state machine. The final state is defined in a
 * {@code createStates()} method. Note that a {@code SysMLFinalState} instance
 * is declared and created in the {@code SysMLStateMachine} which is inherited
 * by all state machines.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLFinalState
 * @see sysmlinjava.statemachine.SysMLStateMachine#finalState
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface FinalState
{
}
