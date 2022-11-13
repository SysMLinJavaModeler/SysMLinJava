package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for a
 * protocol that is part of a required interface. The annotation should be on a
 * field whose variable is an extension of the {@code SysMLFullPort}.
 * <p>
 * The {@code SysMLFullPort} extension should be the protocol's model. The model
 * could be nothing more than a full-port class that specifies a
 * &#64;RequirementSpecificationLink hyperlink for a specification of the
 * protocol. Or, it could be a complete executable model of the protocol with
 * opreations, receptions, state-machine, values, etc. Use of this annotation on
 * the full-port field designates the modeled port as a required protocol of an
 * interface.
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementInterfaceProtocol
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
