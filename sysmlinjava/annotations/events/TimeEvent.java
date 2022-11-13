package sysmlinjava.annotations.events;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "time event"
 * property, i.e. an instance of a {@code SysMLTimeEvent} within the context of
 * the current {@code SysMLBlock}. The {@code SysMLTimeEvent} is typically
 * created dynamically, as the event happens, as opposed to as a declared
 * object, in which case this annotation should be seldom, if ever used in
 * SysMLinJava models.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.events.SysMLTimeEvent
 */
@Retention(CLASS)
@Target(FIELD)
public @interface TimeEvent
{

}
