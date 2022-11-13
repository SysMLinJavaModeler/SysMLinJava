package sysmlinjava.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "proxy" port. The
 * field variable should be an instance of an extension class of the
 * {@code SysMLProxyPort} and should be created/initialized in the
 * {@code createProxyPorts()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.ports.SysMLProxyPort
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE_USE)
public @interface ProxyPort
{
	/**
	 * Returns whether this port is a conjugate port
	 * 
	 * @return whether this port is a conjugate port
	 */
	boolean isConjugate() default false;

	/**
	 * Returns whether this port is a behavioral port, i.e. invokes a behavior on
	 * the parent block
	 * 
	 * @return whether this port is a behavioral port
	 */
	boolean isBehavior() default true;
}
