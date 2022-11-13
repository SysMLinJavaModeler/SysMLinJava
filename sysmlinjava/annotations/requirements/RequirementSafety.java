package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for
 * safety.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD})
public @interface RequirementSafety
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
