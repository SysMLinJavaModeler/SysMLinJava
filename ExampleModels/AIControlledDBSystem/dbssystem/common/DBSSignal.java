package dbssystem.common;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;
import sysmlinjava.valuetypes.FrequencyHertz;
import sysmlinjava.valuetypes.PhaseShiftRadians;
import sysmlinjava.valuetypes.PotentialElectricalVolts;
import sysmlinjava.valuetypes.SysMLValueType;

public class DBSSignal extends SysMLValueType
{
	@Attribute
	public FrequencyHertz frequency;
	@Attribute
	public PotentialElectricalVolts amplitude;
	@Attribute
	public PhaseShiftRadians phaseShift;
	
	public DBSSignal(FrequencyHertz frequency, PotentialElectricalVolts amplitude, PhaseShiftRadians phaseShift)
	{
		super();
		this.frequency = frequency;
		this.amplitude = amplitude;
		this.phaseShift = phaseShift;
	}

	public void setValue(DBSSignal signal)
	{
		frequency.value = signal.frequency.value;
		amplitude.value = signal.amplitude.value;
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
		return String.format("DBSSignal [frequency=%s, amplitude=%s, phaseShift=%s]", frequency, amplitude, phaseShift);
	}
}
