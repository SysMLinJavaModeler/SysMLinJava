package dbssystem.common;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;

public class PulseValueSignal extends SysMLSignal implements Serializable
{
	private static final long serialVersionUID = -5210261239644951427L;

	@Attribute
	public PulseValue value;

	public PulseValueSignal(PulseValue value)
	{
		super();
		this.value = value;
	}
}
