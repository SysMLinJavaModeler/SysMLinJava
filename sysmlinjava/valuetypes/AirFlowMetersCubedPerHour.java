package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for air flow in cubic-meters/hour
 * 
 * @author ModelerOne
 *
 */
public class AirFlowMetersCubedPerHour extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = 7667078754166139009L;

	/**
	 * Constructor
	 * 
	 * @param value value to be used for initial value
	 */
	public AirFlowMetersCubedPerHour(double value)
	{
		super(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.MetersCubicPerHour;
	}
}
