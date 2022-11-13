package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the method that follows initializes the {@code SysMLBlock}'s
 * {@code stateMachine} with an instance of an extension to the
 * {@code SysMLStateMachine}. The state machine should specify the requirement
 * for "states and modes", i.e. the behavior of the system, subsystem, or
 * component in the context of its state machine. The method should assign a
 * {@code new} instance of a {@code SysMLStateMachine} that repesents the
 * required state-based behavior of the system to the {@code stateMachine}
 * variable.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface RequirementStateMachine
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
