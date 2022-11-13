package dbssystem.common;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLSignal;

public class DBSSignalSignal extends SysMLSignal
{
	@Attribute
	public DBSSignal value;

	public DBSSignalSignal(DBSSignal value)
	{
		super();
		this.value = value;
	}

	@Override
	public String toString()
	{
		return String.format("DBSSignalSignal [value=%s]", value);
	}
}
