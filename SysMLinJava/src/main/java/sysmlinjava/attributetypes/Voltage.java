package sysmlinjava.attributetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * Value type for electical voltage with units volts. This value type is
 * redundant to the {@code PotentialElectricVolts} value type in the SysMLinJava
 * API, but was implemented separately to be an identical model element to the current
 * value type used in the electrical circuit model described in "SysML Extension
 * for Physical Interaction and Signal Flow Simulation", Object Management
 * Group, Inc., 2018.
 * 
 * @see <a href="https://www.omg.org/spec/SysPhS/1.0/PDF">SysML Extension for
 *      Physical Interaction and Signal Flow Simulation</a>
 * 
 * @see PotentialElectricalVolts
 * 
 * @author ModelerOne
 *
 */
public class Voltage extends RReal
{
	private static final long serialVersionUID = -1329599791502360545L;

	/**
	 * Constructor
	 * 
	 * @param value initial value, in volts, of the voltage
	 */
	public Voltage(double value)
	{
		super(value);
	}

	/**
	 * Copy constructor
	 * 
	 * @param copiedFrom initial value to be copied from
	 */
	public Voltage(Voltage copiedFrom)
	{
		super(copiedFrom.value);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new Voltage(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Volt;
	}
}
