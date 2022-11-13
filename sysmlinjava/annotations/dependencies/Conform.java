package sysmlinjava.annotations.dependencies;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a model element on which the
 * current element has a SysML conform dependency
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Conform
{

}