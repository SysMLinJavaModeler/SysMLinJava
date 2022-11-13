package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows is a SysML element group specifying the
 * systems (parts) that represent the requirements for a system, i.e. parts
 * whose model represents a top level requirement for a system requirements
 * specification.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementSystemsGroup
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
