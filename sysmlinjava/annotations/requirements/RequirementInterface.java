package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.requirements.SysMLVerificationMethodKind;

/**
 * Indicates that the field that follows represents a SysML requirement for an
 * interface. The annotation should be on a field whose variable is of the
 * {@code SysMLAssociationBlockConnector} type. The connector represented by the
 * variable defines the interface between the &#64;RequirementSystem system and
 * another &#64;RequirementSystem system or &#64;RequirementEnvironment
 * environment.
 * <p>
 * The variable that represents the interface connector should be initialized in
 * the {@code createConnectors()} operation by an object creation/initialization
 * statement. The initialization must include the argument for the connector
 * function whose value is specified in the {@code createConnectorFunctions()}
 * operation. The connector function should be initialized with a lambda
 * function whose body contains a statement that invokes the
 * {@code SysMLFullPort} operation {@code addVirtualConnectedPortPeer()}
 * operation.
 * <p>
 * The scope of the invocation should be the variable name sequence that
 * accesses the "end0" port of the connector. The argument of the invocation
 * should be the variable name sequence that accesses the "end1" port of the
 * connector. The end0 and end1 are used to fully specify the requirements of
 * the interface to include any protocol stacks and/or details of the protocols
 * that make up the required interface.
 * 
 * @author ModelerOne
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementInterface
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
