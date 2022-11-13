package sysmlinjava.valuetypes;

import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for electrical power in terms of frequency, potential,
 * and current. It can be used to represent power flows into electrical power
 * sinks (computers, moters, other circuits) and out of electrical power sources
 * (generators, batteries, etc.) or similar. The class also provides class-level
 * constants for frequently used standard values of frequency, voltage, and
 * current. The {@code watts()} method is provided to convert the attributes
 * into power as watts.
 * 
 * @author ModelerOne
 *
 */
public class ElectricalPower extends SysMLValueType implements StackedProtocolObject
{
	/**
	 * Attribute for the frequency of the voltage/current
	 */
	@Attribute
	public FrequencyHertz frequency;
	/**
	 * Attribute for the potential (voltage)
	 */
	@Attribute
	public PotentialElectricalVolts potential;
	/**
	 * Attribute for the current
	 */
	@Attribute
	public CurrentAmps current;

	/**
	 * Constant value for 50 Hz standard frequency
	 */
	public static final FrequencyHertz standard50Hz = new FrequencyHertz(50);
	/**
	 * Constant value for 60 Hz standard frequency
	 */
	public static final FrequencyHertz standard60Hz = new FrequencyHertz(60);
	/**
	 * Constant value for 110 v standard voltage
	 */
	public static final PotentialElectricalVolts standard110V = new PotentialElectricalVolts(110);
	/**
	 * Constant value for 220 v standard voltage
	 */
	public static final PotentialElectricalVolts standard220V = new PotentialElectricalVolts(110);
	/**
	 * Constant value for 10 amp standard current
	 */
	public static final CurrentAmps standard10A = new CurrentAmps(10);
	/**
	 * Constant value for 15 amp standard current
	 */
	public static final CurrentAmps standard15A = new CurrentAmps(15);
	/**
	 * Constant value for 20 amp standard current
	 */
	public static final CurrentAmps standard20A = new CurrentAmps(20);
	/**
	 * Constant value for 25 amp standard current
	 */
	public static final CurrentAmps standard25A = new CurrentAmps(25);

	/**
	 * Constructor - all initial values
	 * 
	 * @param frequency initial frequency value
	 * @param potential initial voltage value
	 * @param current   initial current value
	 */
	public ElectricalPower(FrequencyHertz frequency, PotentialElectricalVolts potential, CurrentAmps current)
	{
		super();
		this.frequency = frequency;
		this.potential = potential;
		this.current = current;
	}

	/**
	 * Constructor - default values: 60 Hz, 110 volts, 15 amps
	 */
	public ElectricalPower()
	{
		super();
		this.frequency = new FrequencyHertz(60);
		this.potential = new PotentialElectricalVolts(110);
		this.current = new CurrentAmps(15);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copiedFrom power value of which this is to be a copy of
	 */
	public ElectricalPower(ElectricalPower copiedFrom)
	{
		super(copiedFrom);
		this.frequency = new FrequencyHertz(copiedFrom.frequency);
		this.potential = new PotentialElectricalVolts(copiedFrom.potential);
		this.current = new CurrentAmps(copiedFrom.current);
	}

	/**
	 * Returns the power value of the current potential and current values
	 * 
	 * @return this instances power value
	 */
	public PowerWatts watts()
	{
		return PowerWatts.of(potential, current);
	}

	@Override
	public String stackNamesString()
	{
		return String.format("%s(watts=%8.4f)", getClass().getSimpleName(), watts().value);
	}

	@Override
	public String toString()
	{
		return String.format("ElectricalPower [id=%s, frequency=%s, potential=%s, current=%s]", id, frequency, potential, current);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Watts;
	}
}
