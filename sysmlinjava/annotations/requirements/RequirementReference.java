package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for a
 * reference. The field variable should be an extension of a {@code SysMLBlock}
 * or {@code SysMLBlockInterface} and its assignment (in the applicable
 * {@code create...()} method) should be a field/object access expression that
 * points the referenced object. The reference should not be initialized by an
 * object creation expression, i.e. not a {@code new <constructor>}
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementReference
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
