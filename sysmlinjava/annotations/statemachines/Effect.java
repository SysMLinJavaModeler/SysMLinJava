package sysmlinjava.annotations.statemachines;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML effect, i.e. an
 * instance of a {@code SysMLEffect} that conforms to the standard SysML effect
 * of a SysML transition of a SysML state machine. The effect is defined in a
 * {@code createEffects()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLEffect
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Effect
{
}
