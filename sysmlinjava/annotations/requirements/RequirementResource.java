package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for a
 * resource. The field variable should be an extension of the {@code SysMLBlock}
 * that represents some type of resource that must be available to the system,
 * subsystem, or component
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD})
public @interface RequirementResource
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
