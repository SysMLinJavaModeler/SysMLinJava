package sysmlinjava.annotations.parametrics;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "objectiveFunction",
 * i.e. a specialization of a {@code SysMLConstraint} that conforms to the
 * SysML objective function.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface ObjectiveFunction
{

}
