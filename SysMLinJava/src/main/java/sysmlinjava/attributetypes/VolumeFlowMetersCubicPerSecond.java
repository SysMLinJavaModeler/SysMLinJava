package sysmlinjava.attributetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * Value type for the rate of flow of a volume of fluid
 * 
 * @author ModelerOne
 *
 */
public class VolumeFlowMetersCubicPerSecond extends RReal
{
	private static final long serialVersionUID = -5734213520114579329L;

	/**
	 * Constructor
	 * 
	 * @param value initial value
	 */
	public VolumeFlowMetersCubicPerSecond(double value)
	{
		super(value);
	}

	/**
	 * Copy constructor
	 * 
	 * @param copiedFrom value of which this value is to be copied from
	 */
	public VolumeFlowMetersCubicPerSecond(VolumeFlowMetersCubicPerSecond copiedFrom)
	{
		super(copiedFrom.value);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new VolumeFlowMetersCubicPerSecond(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.MetersCubicPerSecond;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("VolumeFlowMetersCubicPerSecond [value=");
		builder.append(value);
		builder.append(", units=");
		builder.append(units);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}
