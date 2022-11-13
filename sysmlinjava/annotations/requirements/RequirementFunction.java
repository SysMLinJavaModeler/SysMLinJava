package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the method that follows represents a SysML
 * requirement for a function.  The method should include inputs (method
 * arguments) and outputs (method arguments and/or return type) to/from the
 * function with the function specified by the statements in the method's
 * body.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface RequirementFunction
{
	/**
	 * Returns a set of kinds of verification methods that apply to the annotated
	 * requirement
	 * 
	 * @return a set of kinds of verification methods that apply to the annotated
	 *         requirement
	 */
	SysMLVerificationMethodKind[] requirementVerificationMethod() default SysMLVerificationMethodKind.Demonstration;
}
