package sysmlinjava.actions;

/**
 * Generalized functional interface for definition of a calculation action that
 * takes no arguments and returns no result, i.e. a calculation that is
 * performed entirely within the context of the class object in which it is
 * defined. An example use of this functional interface for defining a
 * calculation is as follows:
 * 
 * <pre>{@code
	public class MyCalculator extends SysMLPart
	{
			:
		&#64;Attribute
		IInteger calcResult;
		&#64;Attribute
		IInteger calcParam1;
		&#64;Attribute
		IInteger calcParam2;
			:
		&#64;CalculationFunction
		SysMLCalculationFunction myCalculationFunction;
		&#64;Calculation
		SysMLCalculation myCalculation;

		public MyCalculator(IInteger calcParam1, IInteger calcParam2, IInteger calcResult)
		{
			this.calcParm1 = calcParam1;
			this.calcParam2 = calcParam2;
			this.calcResult = calcResult;
		}
			:
		protected void createFunction()
		{
			myCalculationFunction = () ->
			{
				calcResult = calcParam1.addedTo(calcParam2);
			});
		}
			:
		protected void createCalculations()
		{
			myCalculation = new SysMLCalculation(myCalculationFunction, "myCalc", 0L);
		}
			:
	}
	}</pre>
 * 
 * @author ModelerOne
 */
@FunctionalInterface
public interface SysMLCalculationFunction extends SysMLActionFunction
{
	/**
	 * Performs the calculation function
	 */
	void perform();
}