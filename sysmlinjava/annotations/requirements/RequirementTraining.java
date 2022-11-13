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
 * requirement for training. A field variable-based definition of the training
 * requirement should be an instance of an extension of the {@code SysMLBlock},
 * where the training is modeled as a block with values, parts, methods,
 * functions, and/or state machines (behaviors), as needed. This is the prefered
 * method of modeling the training requirement
 * <p>
 * The method-based definition of the training should be an operation(s) that
 * contains the logic and objects for the process or procedures for the
 * training.
 * <p>
 * Of course, the training requirements could be modeled as one or more block
 * instances and/or one or more methods. In any case, each property with this
 * annotation will generate a corresponding training requirement.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD, METHOD})
public @interface RequirementTraining
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
