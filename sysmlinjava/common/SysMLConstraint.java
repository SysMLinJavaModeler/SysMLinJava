package sysmlinjava.common;

/**
 * SysMLinJava representation of the SysML constraint. {@code SysMLConstraint}
 * is specified as a functional interface enabling the constraint being
 * specified by lambda expression. The calculate() operation invokes the lambda
 * expression that defines the constraint within the context of the SysMLBlock
 * that contains the implementation of the interface.
 * <p>
 * The {@code SysMLConstraint} should be declared as a field in the extended
 * {@code SysMLBlock} class. The field should be annotated with the
 * {@code @Constraint} annotation. It should then be implemented as an
 * instance of a functional interface (lambda expression) in the override of the
 * {@code SysMLBlock}'s {@code createConstraints()} operation.
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLConstraint extends SysMLInterface
{
	/**
	 * Performs the calculation that is the constraint in the context of the
	 * {@code SysMLBlock} that contains the implementation of the functional
	 * interface.
	 */
	void apply();
}
