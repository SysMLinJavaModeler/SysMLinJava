package sysmlinjava.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the method that follows represents a SysML "operation", i.e. a
 * declaration of a java method within the context of the current
 * {@code SysMLBlock} that conforms to the SysML operation.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Operation
{
}
