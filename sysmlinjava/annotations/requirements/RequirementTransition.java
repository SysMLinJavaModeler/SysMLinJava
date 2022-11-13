package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for
 * transition (from/to previous/next states). The field variable should be an
 * extension of the {@code SysMLStateMachine} which specifies the transition in
 * terms of state transitions of the state machine. The system transitions state
 * machine can, of course, be as simple or complex as needed to specify what
 * system transitions are required to take place, the states of the system
 * before and after transitions, and the events and/or conditions that must be
 * present before the transitions take place.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target({FIELD})
public @interface RequirementTransition
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
