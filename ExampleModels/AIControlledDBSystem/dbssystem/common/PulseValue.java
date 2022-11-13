package dbssystem.common;

import java.io.Serializable;
import sysmlinjava.valuetypes.IInteger;

public class PulseValue extends IInteger implements Serializable
{
	private static final long serialVersionUID = -5645764597295659619L;

	public PulseValue(IInteger value)
	{
		super(value.value);
	}
}
