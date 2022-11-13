package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for a
 * physical attribute. The field variable should be of a {@code SysMLValueType}
 * (one of many, perhaps) that specifies a value for some physical attribute
 * such as weight, hardness, size, etc.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementPhysical
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
