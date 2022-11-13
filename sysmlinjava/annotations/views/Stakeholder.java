package sysmlinjava.annotations.views;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML stakeholder element of a
 * SysML viewpoint. The field variable should be an instance of the {@code SysMLStakeholder} and should be
 * created/initialized in the variable declaration.
 * 
 * @author ModelerOne
 *
 *@see sysmlinjava.views.SysMLViewpoint
 */
@Retention(CLASS)
@Target(FIELD)
public @interface Stakeholder
{

}
