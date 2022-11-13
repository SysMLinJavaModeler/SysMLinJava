package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML signal, The
 * field variable should be an instance an extension to the
 * {@code SysMLSignal}and should be created/initialized in the
 * {@code createAttributes()} method.
 * <p>
 * Note to be compliant with SysML, this annotation should not be used for a
 * field in a {@code SysMLBlock} as the SysML block does une "attributes" in the
 * sense that the class does. Instead, annotations in the block should be
 * {@code &#64;Value}, {@code &#64;FullPort}, {@code &#64;Reference},
 * {@code &#64;Part}, {@code &#64;Constraint}, etc.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.common.SysMLClass
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Signal
{

}
