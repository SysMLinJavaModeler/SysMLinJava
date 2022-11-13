package sysmlinjava.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "value", i.e. The
 * field variable should be an instance of an extension class of the
 * {@code SysMLValueType} and should be created/initialized in the
 * {@code createValues()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.valuetypes.SysMLValueType
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Value
{
}
