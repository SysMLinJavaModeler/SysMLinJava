package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML transition, i.e. an
 * instance of a {@code SysMLTransition} that conforms to the standard SysML
 * transition of a SysML state machine. The transition is defined in a
 * {@code createTransitions()} method
 * 
 * @author ModelerOne
 * 
 * @see sysmlinjava.statemachine.SysMLTransition
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Transition
{
}
