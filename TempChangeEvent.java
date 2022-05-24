package h2ostates;

import sysmlinjava.common.SysMLChangeEvent;

/**
 * Event for the change of temperature.
 * 
 * @author ModelerOne
 *
 */
public class TempChangeEvent extends SysMLChangeEvent
{
	public TempChangeEvent()
	{
		super("Temp change");
	}

	@Override
	protected void createChangeExpression()
	{
		changeExpression = "TempChange";
	}
}
