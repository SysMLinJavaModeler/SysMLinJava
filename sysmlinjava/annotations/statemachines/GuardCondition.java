package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML guard condition,
 * i.e. an instance of a {@code SysMLGuardCondition} that conforms to the
 * standard SysML condition constraint for the guard of a SysML transition of a
 * SysML state machine. The guard condition is defined in a
 * {@code createGuardConditions()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLGuardCondition
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface GuardCondition
{

}
