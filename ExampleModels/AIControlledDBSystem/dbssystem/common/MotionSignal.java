package dbssystem.common;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.common.SysMLClass;
import sysmlinjava.valuetypes.JerkMetersPerSecondCubed;

public class MotionSignal extends SysMLClass  implements Serializable
{
	private static final long serialVersionUID = 1851159395194130147L;

	@Attribute
	public JerkMetersPerSecondCubed jerk;

	public MotionSignal(JerkMetersPerSecondCubed value)
	{
		super();
		this.jerk = value;
	}

	@Override
	public String toString()
	{
		return String.format("MotionSignal [value=%s]", jerk);
	}
}
