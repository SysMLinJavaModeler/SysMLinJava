package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field or method that follows represents a SysML
 * requirement for a capability.
 * <p>
 * If annotating a field, the field should include a variable of type
 * {@code SysMLActivity} with the capability specified by the statements in the
 * body of the lambda function assigned to the activity variable in the class's
 * {@code createActivities()} operation.
 * <p>
 * If annotating a method, the method should should include inputs (method
 * arguments) and outputs (method arguments and/or return type) to/from the
 * capability. The capability should be specified by the statements in the
 * method's body.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD})
public @interface RequirementCapability
{
	/**
	 * Returns a set of kinds of verification methods that apply to the annotated
	 * requirement
	 * 
	 * @return a set of kinds of verification methods that apply to the annotated
	 *         requirement
	 */
	SysMLVerificationMethodKind[] requirementVerificationMethod() default SysMLVerificationMethodKind.Test;
}
