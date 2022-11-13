package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "part", The
 * field variable should be an instance of an extension class of the
 * {@code SysMLBlock} and should be created/initialized in the
 * {@code createParts()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.blocks.SysMLBlock
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface Part
{
}
