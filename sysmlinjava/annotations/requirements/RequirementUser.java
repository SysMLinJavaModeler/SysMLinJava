package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for a
 * user of the system. The annotation should be on a field whose variable is an
 * extension of the {@code SysMLBlock}. The {@code SysMLBlock} extension should
 * be the model of the user in terms of his state-machine, capabilities,
 * interfaces, and values. In essence, the user is another type of "system" with
 * which the &#64;RequirementSystem system must interface. So the SysMLinJava
 * model should model the system user similarly to how it models systems,
 * subsystems, and components, modeling interfaces between these elements and
 * the user element.
 * <p>
 * Note that the &#64;RequirementUser annotation may be considered to be
 * redundant to the &#64;RequirementPersonnel annotation to the extent that both
 * annotations can be used to identify model elements as system users.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementUser
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
