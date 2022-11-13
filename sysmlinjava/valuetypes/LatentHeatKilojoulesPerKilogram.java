package sysmlinjava.valuetypes;

import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for latent-heat in kilojoules-per-kilogram
 * 
 * @author ModelerOne
 *
 */
public class LatentHeatKilojoulesPerKilogram extends RReal
{
	/** Serializable ID*/private static final long serialVersionUID = 5906656002048836337L;

	/**
	 * Constant value for latent heat for water condensation
	 */
	public static final LatentHeatKilojoulesPerKilogram waterCondensationLH = new LatentHeatKilojoulesPerKilogram(334.0);
	/**
	 * Constant value for latent heat for water vaporization
	 */
	public static final LatentHeatKilojoulesPerKilogram waterVaporizationLH = new LatentHeatKilojoulesPerKilogram(2264.705);

	/**
	 * Constructor
	 * 
	 * @param value double value to be the initial value
	 */
	public LatentHeatKilojoulesPerKilogram(double value)
	{
		super(value);
		units = SysMLinJavaUnits.KilojoulesPerKilogram;
	}

	@Override
	public String toString()
	{
		return String.format("LatentHeatKilojoulesPerKilogram [value=%s, units=%s]", value, units);
	}
}
