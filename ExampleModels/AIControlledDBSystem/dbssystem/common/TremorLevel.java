package dbssystem.common;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;
import sysmlinjava.valuetypes.DistanceMillimeters;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.SysMLValueType;

public class TremorLevel extends SysMLValueType implements Serializable
{
	private static final long serialVersionUID = -44622084523635911L;

	@Attribute
	public FrequencyHertz frequency;
	@Attribute
	public DistanceMillimeters amplitude;
	
	public TremorLevel(FrequencyHertz frequency, DistanceMillimeters amplitude)
	{
		super();
		this.frequency = frequency;
		this.amplitude = amplitude;
	}

	public void setValue(TremorLevel level)
	{
		frequency.value = level.frequency.value;
		amplitude.value = level.amplitude.value;
		notifyValueChangeObservers();
	}

	@Override
	public void createUnits()
	{
		units = SysMLinJavaUnits.Object;
	}

	@Override
	public String toString()
	{
		return String.format("TremorLevel [frequency=%s, amplitude=%s]", frequency, amplitude);
	}
}
