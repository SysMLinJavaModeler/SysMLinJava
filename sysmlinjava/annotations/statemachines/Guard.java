package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML guard, i.e. an
 * instance of a {@code SysMLGuard} that conforms to the standard SysML guard of
 * a SysML transition of a SysML state machine. The guard is defined in a
 * {@code createGuards()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLGuard
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Guard
{

}
