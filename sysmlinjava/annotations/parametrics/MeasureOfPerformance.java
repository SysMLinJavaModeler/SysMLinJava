package sysmlinjava.annotations.parametrics;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "mop", i.e. an
 * instance of an extension of the {@code SysMLValueType} that conforms to the
 * SysML Measure Of Performance.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface MeasureOfPerformance
{

}
