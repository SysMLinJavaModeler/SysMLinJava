package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "quantityKind". The
 * field variable should be an instance of the {@code SysMLQuantityKind} and is
 * created/initialized in the field declaration.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.quantitykinds.SysMLQuantityKind
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface QuantityKind
{
}
