package sysmlinjava.connectors;

import sysmlinjava.common.SysMLInterface;

/**
 * {@code SysMLBindingConnectorFunction} is part of the SysMLinJava
 * representation of the SysML binding connector. In SysMLinJava, connectors are
 * simply Java references to other classes and blocks, so the
 * {@code SysMLBindingConnectorFunction} is a function that
 * instantiates/initializes the reference. {@code SysMLBindingConnectorFunction}
 * is invoked by the {@code SysMLBindingConnector} during the latter connector's
 * construction/initialization.
 * <p>
 * The {@code SysMLBindingConnectorFunction} should be declared as a field in
 * the extended {@code SysMLBlock} class that contains both of the connected
 * elements. In most circomastances, one of the connected elements will be a
 * parameter port of a {@code SysMLConstraintBlock} connected to a
 * {@code SysMLBlock} that contains the bound parameter. In any case, the
 * {@code SysMLBindingConnectorFunction} field should be annotated with the
 * {@code BindingConnectorFunction} annotation. It should then be
 * initialized/implemented as an instance of a Lambda function in the override
 * of the SysMLBlock's {@code createConnectorFunctions()} and/or
 * {@code createConstraintParameterConnectorFunctions()} operations, the latter
 * being for connection of constraint parameters to bound parameter blocks. An
 * example for connection of constraint parameters to bound parameter blocks
 * follows.
 * 
 * <pre>
 * {@code
	public class SystemsDomain extends SysMLBlock
	{
			:
		&#64;BindingConnectorFunction
		public SysMLBindingConnectorFunction speedConnectorFunction;
			:
		&#64;Override
		protected void createConstraintParameterConnectorFunctions()
		{
				:
			speedConnectorFunction = (constraintParameterPort, contextBlock) ->
			{
				try
				{
					SpeedKilometersPerHour parameter = ((Vehicle)contextBlock).speedSensorPort;
					constraintParameterPort.queuedParameterValues.put(parameter);
					constraintParameterPort.constraintBlock.valueChanged();
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			};
				:
		}
			:
	}
 * }
 * </pre>
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLBindingConnectorFunction extends SysMLInterface
{
	/**
	 * Method to be implemented, i.e. as a parameter lambda function expression
	 */
	void connect();
}