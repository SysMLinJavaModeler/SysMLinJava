package sysmlinjava.annotations.views;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML viewpoint element,
 * i.e. an instance of a {@code SysMLViewpoint}.
 * 
 * @author ModelerOne
 *
 */
@Retention(CLASS)
@Target(FIELD)
public @interface Viewpoint
{

}
