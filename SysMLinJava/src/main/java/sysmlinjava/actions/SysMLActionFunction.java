package sysmlinjava.actions;

/**
 * Interface to be extended to a functional interface for the function of the
 * action, i.e. interface to be extended for the lambda function that defines
 * the action. An example usage is as follows:
 * 
 * <pre>
	public class MyPart extends SysMLPart
	{
		&#64;FunctionalInterface
		public interface LinearSlopeFunction extends SysMLActionFunction
		{
			Integer perform(Integer x, Integer intercept, Float slope);
		}
		
		&#64;ActionFunction
		LinearSlopeFunction linearSlopeFunction;
		&#64;Action
		SysMLAction getY;
		
		&#64;Override
		public void createActionFunctions()
		{
			linearSloptFunction = (x, intercept, slope) -> {return slope * x + intercept};
		}

		&#64;Override
		public void createActions()
		{
			getY = new SysMLAction(linearSlopeFunction, "MyAction", 0L};
		}
	}
 * </pre>
 * 
 * @author ModelerOne
 *
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se25/html/jls-9.html#jls-9.8">lamda function spec</a>
 */
public interface SysMLActionFunction
{
}