package sysmlinjava.annotations.statemachines;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML junction pseudo-state, i.e.
 * an instance of a {@code SysMLJunctionPseudoState} that conforms to the standard SysML junction pseudo-state of a SysML state machine. The
 * pseudo-state is defined in a {@code createPseudoStates()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLJunctionPseudoState
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface JunctionPseudoState
{

}
