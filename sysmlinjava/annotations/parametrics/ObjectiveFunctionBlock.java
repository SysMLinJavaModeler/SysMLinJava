package sysmlinjava.annotations.parametrics;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML objective function constraint block,
 * i.e. an instance of an extended {@code SysMLObjectiveFunctionBlock} that conforms to
 * the standard SysML objective function constraint block.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.constraintblocks.SysMLObjectiveFunctionBlock
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface ObjectiveFunctionBlock
{
}
