package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "full" port. The
 * field variable should be an instance of an extension class of the
 * {@code SysMLFullPort} and should be created/initialized in the
 * {@code createFullPorts()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.ports.SysMLFullPort
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({FIELD})
public @interface FullPort
{
}
