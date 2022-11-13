package sysmlinjava.annotations.views;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML method element of a
 * SysML viewpoint, i.e. one or more instances of a {@code String} that
 * specifies the methods that apply to the viewpoint.
 * 
 * @author ModelerOne
 *
 */
@Retention(CLASS)
@Target(FIELD)
public @interface Method
{

}
