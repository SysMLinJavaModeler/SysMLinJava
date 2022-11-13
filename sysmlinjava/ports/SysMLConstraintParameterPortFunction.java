package sysmlinjava.ports;

import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLInterface;

/**
 * Functional interface specification for a function that is invoked by an
 * instance of the {@code SysMLConstraintParameterPort}. The function retrieves
 * the "bound" value for the constraint parameter from a specified context
 * block, and enqueues the value to the specified
 * {@code SysMLConstraintParameterPort}.
 * <p>
 * The {@code SysMLConstraintParameterPortFunction} should be declared as a
 * field in the extended {@code SysMLConstraintBlock} class. The field should be
 * annotated with the {@code ConstraintParameterPortFunction} annotation. It
 * should then be implemented as an instance of a Lambda function in the
 * override of the SysMLBlock's {@code createConstraintParameterPortFunctions()}
 * operation.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.ports.SysMLConstraintParameterPort
 * @see <a href=
 *      "https://docs.oracle.com/javase/specs/jls/se12/html/jls-17.html#jls-17.27">LambdaExpressions</a>
 */
@FunctionalInterface
public interface SysMLConstraintParameterPortFunction extends SysMLInterface
{
	/**
	 * Method to retrieve the value that is bound to the specified constraint
	 * parameter port from the specified parameter context block. As a functional
	 * interface, this method is typically implemented as a 2-parameter lambda
	 * function expression.
	 * 
	 * @param constraintParameterPort port to which the changed value is to be
	 *                                queued
	 * @param parameterContextBlock   block in whose context the bound value is
	 *                                located
	 */
	void onValueChange(SysMLConstraintParameterPort constraintParameterPort, SysMLBlock parameterContextBlock);
}