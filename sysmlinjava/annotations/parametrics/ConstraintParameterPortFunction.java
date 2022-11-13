package sysmlinjava.annotations.parametrics;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a function of a SysML
 * constraint parameter port, i.e. an instance of a
 * {@code SysMLConstraintParameterPortFunction} that is invoked by a
 * {@code SysMLConstraintParameterPort} to obtain the new value of the
 * constraint parameter that is "bound" to this port.
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface ConstraintParameterPortFunction
{

}
