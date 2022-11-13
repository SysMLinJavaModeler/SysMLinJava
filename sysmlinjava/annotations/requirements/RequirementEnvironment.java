package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for the
 * environment in which the system exists. The annotation should be on a field
 * whose variable is an extension of the {@code SysMLBlock}. The
 * {@code SysMLBlock} extension should be the environment's model. In essence,
 * the environment is another "system" with which the &#64;RequirementSystem system must interface.
 * So the SysMLinJava model should model the system environment similarly to how
 * it models systems, subsystem, and components, modeling interfaces between
 * these elements and the environment model.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD})
public @interface RequirementEnvironment
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
