package dbssystem.common;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.PhaseShiftRadians;
import sysmlinjava.valuetypes.SysMLValueType;

public class DBSControl extends SysMLValueType implements Serializable
{
	private static final long serialVersionUID = -1434190168875364540L;

	@Attribute
	public FrequencyHertz frequency;
	@Attribute
	public PhaseShiftRadians phaseShift;
	
	public DBSControl(FrequencyHertz frequency, PhaseShiftRadians phaseShift)
	{
		super();
		this.frequency = frequency;
		this.phaseShift = phaseShift;
	}

	public void setValue(FrequencyHertz frequency, PhaseShiftRadians phaseShift)
	{
		this.frequency.value = frequency.value;
		this.phaseShift.value = phaseShift.value;
		notifyValueChangeObservers();
	}

	@Override
	public String toString()
	{
		return String.format("DBSControl [frequency=%s, phaseShift=%s]", frequency, phaseShift);
	}

	@Override
	public void createUnits()
	{
		units = SysMLinJavaUnits.Object;
	}
}
