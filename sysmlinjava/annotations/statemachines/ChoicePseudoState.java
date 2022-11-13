package sysmlinjava.annotations.statemachines;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML choice pseudo-state,
 * i.e. an instance of a {@code SysMLChoicePseudoState} that conforms to the
 * standard SysML choice pseudo-state of a SysML state machine. The pseudo-state
 * is defined in a {@code createPseudoStates()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLChoicePseudoState
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface ChoicePseudoState
{

}
