package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "reference". The
 * field variable should be an instance of an extension of the
 * {@code SysMLBlock}, {@code SysMLInterfaceBlock}, {@code SysMLValueType}, or
 * {@code SysMLClass} and is assigned a reference to an instance in the
 * {@code createReferences()} method.
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Reference
{
}
