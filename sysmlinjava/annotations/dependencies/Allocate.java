package sysmlinjava.annotations.dependencies;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a model element upon
 * which the current element has a SysML allocate dependency
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Allocate
{

}