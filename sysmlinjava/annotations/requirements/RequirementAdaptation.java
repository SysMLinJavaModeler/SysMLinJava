package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for an
 * adaptation. The annotated field should specify a variable that extends
 * {@code SysMLBlock, SysMLFullPort, or SysMLValueType} and represents an
 * adaptation to some aspect of the system being modeled. The field should also
 * include an annotation for the type of requirement that is the adaptation,
 * e.g. &#64;RequirementSystem, &#64;RequirementInterface,
 * &#64;RequirementPhysical, etc. The &#64;RequirementAdaptation annotation, in
 * essence, qualifies another annotation as being a required adaptation.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementAdaptation
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
