package sysmlinjava.annotations.comments;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML problem comment,
 * i.e. an instance of a {@code SysMLProblem}
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target({FIELD})
public @interface Problem
{

}
