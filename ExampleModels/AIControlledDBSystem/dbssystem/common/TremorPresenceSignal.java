package dbssystem.common;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;
import sysmlinjava.valuetypes.BBoolean;

public class TremorPresenceSignal extends SysMLSignal
{
	@Attribute
	public BBoolean isPresent;

	public TremorPresenceSignal(BBoolean isPresent)
	{
		super();
		this.isPresent = isPresent;
	}
}
