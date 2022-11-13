package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML state, i.e. an
 * instance of a {@code SysMLState} that conforms to the standard SysML state of
 * a SysML state machine. The state is defined in a {@code createStates()}
 * method
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLState
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface State
{
}
