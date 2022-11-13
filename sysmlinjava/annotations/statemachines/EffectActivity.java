package sysmlinjava.annotations.statemachines;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML effect activity,
 * i.e. an instance of a {@code SysMLEffectActivity} that conforms to the
 * standard SysML activity for the effect of a SysML transition of a SysML state
 * machine. The effect activity is defined in a {@code createEffectActivities()}
 * method.
 * 
 * @author ModelerOne
 * @see sysmlinjava.statemachine.SysMLEffectActivity
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface EffectActivity
{

}
