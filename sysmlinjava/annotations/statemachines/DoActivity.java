package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "do" activity, i.e.
 * an instance of a {@code SysMLDoActivity} that conforms to the standard SysML
 * activity for the "do" activity of a SysML state of a SysML state machine. The
 * activity is defined in a {@code createDoActivities()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLDoActivity
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface DoActivity
{
}
