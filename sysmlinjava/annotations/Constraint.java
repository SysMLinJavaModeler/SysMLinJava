package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML constraint. The
 * field variable should be an instance of the {@code SysMLConstraint} that is
 * created/initialized (as a lambda expression) in a {@code createConstraints()}
 * method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.common.SysMLConstraint
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface Constraint
{
}
