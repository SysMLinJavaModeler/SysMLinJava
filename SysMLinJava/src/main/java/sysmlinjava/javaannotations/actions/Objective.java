package sysmlinjava.javaannotations.actions;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML case objective. The
 * annotated field should contain a variable that is an instance of the
 * {@code SysMLRequirement}. The objective instance is created/initialized in a
 * {@code createObjective()} method of the case in which the field is declared.
 * 
 * @author ModelerOne
 * @see sysmlinjava.requirements.SysMLRequirement
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Objective
{

}
