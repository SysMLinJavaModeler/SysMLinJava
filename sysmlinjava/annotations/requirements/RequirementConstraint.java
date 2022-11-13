package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for a
 * constraint. The annotation should be on a field whose variable is of the
 * {@code SysMLConstraint} type. The constraint value should be specified in the
 * {@code createConstraints()} operation as statements of a lambda function that
 * initializes the variable.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(CLASS)
@Target(FIELD)
public @interface RequirementConstraint
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
