package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for a
 * interface protocol connector. The annotation should be on a field whose
 * variable is of type {@code SysMLAssociationBlockConnector} and is
 * created/initialized in the {@code createConnectors()} method. The variable
 * and its initialization are used to identify a system, subsystem, or component
 * with which the requirement system, subsystem, or component is required to
 * interface.
 * 
 * @author ModelerOne
 *
 *@see sysmlinjava.connectors.SysMLAssociationBlockConnector
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementInterfaceProtocolConnector
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
