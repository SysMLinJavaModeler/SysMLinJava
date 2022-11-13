package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "unit". The field
 * variable should be an instance of the {@code SysMLUnit} and should be
 * created/initialized in the field declaration.
 * 
 * @author ModelerOne
 * @see sysmlinjava.units.SysMLUnit
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface Unit
{
}
