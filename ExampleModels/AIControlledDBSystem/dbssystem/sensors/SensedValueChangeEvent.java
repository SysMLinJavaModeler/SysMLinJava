package dbssystem.sensors;

import sysmlinjava.events.SysMLChangeEvent;

public class SensedValueChangeEvent extends SysMLChangeEvent
{
	public SensedValueChangeEvent(String changeExpression, String name)
	{
		super("SensedValueChange");
	}

	@Override
	protected void createChangeExpression()
	{
		changeExpression = "Sensed value change";
	}
}
