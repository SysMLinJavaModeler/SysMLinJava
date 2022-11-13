package dbssystem.common;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.valuetypes.IInteger;

public class PressureSignal extends SysMLClass implements Serializable
{
	private static final long serialVersionUID = -8304480747719490359L;

	@Attribute
	public IInteger rate;
	
	public PressureSignal(IInteger rate)
	{
		super();
		this.rate = rate;
	}

	public PulseValue toPulse()
	{
		return new PulseValue(rate);
	}

	@Override
	public String toString()
	{
		return String.format("PressureSignal [rate=%s]", rate);
	}
}
