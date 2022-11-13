package sysmlinjava.annotations.parametrics;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML constraint parameter
 * port, i.e. an instance of a {@code SysMLConstraintParameterPort} that
 * conforms to the standard SysML constraint parameter port.
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface ConstraintParameterPort
{

}
