package sysmlinjava.annotations.events;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "completion event"
 * property, i.e. an instance of an extension of the
 * {@code SysMLCOmpletionEvent} within the context of the current
 * {@code SysMLBlock}. Instances of the {@code SysMLCompletionEvent} are
 * typically created dynamically, as the event happens, as opposed to as a
 * declared object, in which case this annotation should be seldom, if ever used
 * in SysMLinJava models.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.events.SysMLCompletionEvent
 */
@Retention(CLASS)
@Target(FIELD)
public @interface CompletionEvent
{

}
