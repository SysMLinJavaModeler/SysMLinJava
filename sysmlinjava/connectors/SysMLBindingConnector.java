package sysmlinjava.connectors;

import java.util.ArrayList;
import java.util.List;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.constraintblocks.SysMLConstraintBlock;

/**
 * SysMLinJava representation of the SysML binding connector.
 * <h2>SysML connector as java reference</h2>In SysMLinJava, connectors are
 * simply Java references to other classes and blocks, so the
 * {@code SysMLBindingConnector} invokes a {@code SysMLBindingConnectorFunction}
 * during construction/initialization that makes this connection by setting the
 * reference.
 * <h3></h3> The {@code SysMLBindingConnector} should be declared as a field in
 * the extended {@code SysMLBlock} class that contains both of the connected
 * elements. In most circumstances, one of the connected elements will be a
 * parameter port of a {@code SysMLConstraintBlock} connected to a
 * {@code SysMLBlock} that contains the bound parameter. In any case, the
 * {@code SysMLBindingConnector} field should be annotated with the
 * {@code BindingConnector} annotation. It should then be
 * initialized/implemented as an instance of a Lambda function in the override
 * of the SysMLBlock's {@code createConstraintParameterConnectors()} operations
 * for connection of constraint parameters to bound parameter blocks.
 * 
 * @author ModelerOne
 *
 */
public class SysMLBindingConnector
{
	/**
	 * List of "participants0" (blocks) in the SysML binding of their values to
	 * constraint parameters
	 */
	public List<SysMLBlock> participants0;
	/**
	 * The "participants1" (constraint blocks) in the SysML binding of their
	 * constraint parameters to block values
	 */
	public List<SysMLConstraintBlock> participants1;

	/**
	 * Constructor for a single participant0
	 * 
	 * @param participant0 block that is holds the valueType that is the "bound"
	 *                     constraint parameter
	 * @param participant1 constraint block that holds the parameter port that binds
	 *                     to the constraint parameter
	 * @param connector    function that performs the connection, i.e. initializes
	 *                     the reference to the parameter
	 */
	public SysMLBindingConnector(SysMLBlock participant0, SysMLConstraintBlock participant1, SysMLBindingConnectorFunction connector)
	{
		super();
		this.participants0 = new ArrayList<>();
		this.participants1 = new ArrayList<>();
		this.participants0.add(participant0);
		this.participants1.add(participant1);
		connector.connect();
	}

	/**
	 * Constructor for multiple participants0, single constraint block
	 * 
	 * @param participants0 list of blocks that holds valueTypes that are is the
	 *                      "bound" constraint parameters
	 * @param participant1  constraint block that holds the parameter port that
	 *                      binds to the constraint parameters
	 * @param connector     function that performs the connection, i.e. initializes
	 *                      the references to the parameters
	 */
	public SysMLBindingConnector(List<? extends SysMLBlock> participants0, SysMLConstraintBlock participant1, SysMLBindingConnectorFunction connector)
	{
		super();
		this.participants0 = new ArrayList<>();
		this.participants1 = new ArrayList<>();
		this.participants0.addAll(participants0);
		this.participants1.add(participant1);
		connector.connect();
	}

	/**
	 * Constructor for multiple participants0, multiple constraint blocks
	 * 
	 * @param participants0 list of blocks that holds valueTypes that are is the
	 *                      "bound" constraint parameters
	 * @param participants1 list of constraint blocks that hold the parameter ports
	 *                      that bind to the constraint parameters
	 * @param connector     function that performs the connection, i.e. initializes
	 *                      the references to the parameters
	 */
	public SysMLBindingConnector(List<? extends SysMLBlock> participants0, List<? extends SysMLConstraintBlock> participants1, SysMLBindingConnectorFunction connector)
	{
		super();
		this.participants0 = new ArrayList<>();
		this.participants1 = new ArrayList<>();
		this.participants0.addAll(participants0);
		this.participants1.addAll(participants1);
		connector.connect();
	}
}
