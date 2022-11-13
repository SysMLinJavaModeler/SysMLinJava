package dbssystem.common;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;

public class PressureSignalSignal extends SysMLSignal implements Serializable
{
	private static final long serialVersionUID = -8964924125773791601L;

	@Attribute
	public PressureSignal value;

	public PressureSignalSignal(PressureSignal value)
	{
		super();
		this.value = value;
	}
}
