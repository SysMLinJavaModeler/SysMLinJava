package sysmlinjava.annotations.parametrics;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a function that creates a
 * SysML binding connector, i.e. an instance of a
 * {@code SysMLBindingConnectorFunction} that creates a connections that
 * conforms to the standard SysML binding connector.
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface BindingConnectorFunction
{
}
