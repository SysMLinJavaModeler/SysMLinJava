package sysmlinjava.actions;

/**
 * Functional interface for use in setting the {@code function} to be a simple
 * performed action, i.e. an action performed by its owner - similar to a class
 * method. This functional interface defines a performed action that uses
 * variables for input and output that are in the scope of the class in which it
 * is defined/initialized, i.e. no argument nor returned variables are specified
 * by the interface.
 * <p>
 * This functional interface is typically used for defining the action's
 * {@code function} when creating executable models. Initializing the
 * {@code function} of the action with a lambda expression (in and override of
 * thethe {@code createFunction()} method) that performs the action will allow
 * the action to execute as part of the overall executable model.
 * 
 * @author ModelerOne
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-9.html#jls-9.8">lamda function spec</a>
 */
@FunctionalInterface
public interface PerformedActionFunction extends SysMLActionFunction
{
	/**
	 * Performs the action function
	 */
	void perform();
}