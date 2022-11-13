package dbssystem.common;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;

public class MotionSignalSignal extends SysMLSignal implements Serializable
{
	private static final long serialVersionUID = -950252377931186103L;

	@Attribute
	public MotionSignal value;

	public MotionSignalSignal(MotionSignal value)
	{
		super();
		this.value = value;
	}
}
