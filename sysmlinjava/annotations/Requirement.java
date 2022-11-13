package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "requirement". The
 * field variable should be an instance of a {@code SysMLRequirement} and should
 * be created/initialized in the {@code createRequirements()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.requirements.SysMLRequirement
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface Requirement
{
}
