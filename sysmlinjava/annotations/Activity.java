package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML activity. The field
 * variable should be an instance of the {@code SysMLActivity} and should be
 * created/initialized in the {@code createActivities()} method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.common.SysMLActivity
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface Activity
{

}
