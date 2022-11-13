package sysmlinjava.constraintblocks;

import sysmlinjava.events.SysMLChangeEvent;

/**
 * {@code SysMLParameterChangeEvent} is SysMLinJava's representation of the
 * SysML change event for the change of a bound parameter's value. The
 * {@code SysMLParameterChangeEvent} is typically submitted to the
 * {@code SysMLConstraintBlockStateMachine} to invoke reaction by the
 * {@code SysMLConstraintBlock} to the change of one or more of its bound
 * parameters.
 * 
 * @author ModelerOne
 *
 */
public class SysMLParameterChangeEvent extends SysMLChangeEvent
{
	/**
	 * Constructor
	 * 
	 */
	public SysMLParameterChangeEvent()
	{
		super("ParameterChange");
	}

	@Override
	protected void createChangeExpression()
	{
		changeExpression = "paramID";
	}
}