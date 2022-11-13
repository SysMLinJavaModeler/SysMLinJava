package sysmlinjava.annotations.dependencies;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a model element upon which
 * the current element has a SysML dependency
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(CLASS)
@Target(FIELD)
public @interface Dependency
{
	/**
	 * Return whether this side of the dependency is the dependent (on the other
	 * side)
	 * 
	 * @return true if this side of the dependency is the dependent, false otherwise
	 */
	boolean isDependent() default true;

	/**
	 * Minimum of the multiplicity
	 * 
	 * @return minimum of multiplicity
	 */
	int multiplicityMin() default 1;

	/**
	 * Maximum of the multiplicity
	 * 
	 * @return maximum of multiplicity
	 */
	int multiplicityMax() default 1;

}
