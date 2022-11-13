package sysmlinjava.valuetypes;

import java.util.List;
import sysmlinjava.analysis.common.StackedProtocolObject;
import sysmlinjava.annotations.Operation;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for force in newtons
 * 
 * @author ModelerOne
 *
 */
public class ForceNewtons extends Vector2DValueType implements StackedProtocolObject
{
	/** Serializable ID*/private static final long serialVersionUID = -4223420272722664905L;

	/**
	 * Constructor - magnitude value, zero direction
	 * 
	 * @param value double value to be used for initial value
	 */
	public ForceNewtons(double value)
	{
		super(value, 0);
	}

	/**
	 * Constructor - magnitude value, zero direction
	 * 
	 * @param real RReal value to be used for initial value
	 */
	public ForceNewtons(RReal real)
	{
		super(real.value, 0);
	}

	/**
	 * Constructor - mass to force magnitude conversion, zero direction
	 * 
	 * @param mass mass to be converted to force
	 */
	public ForceNewtons(MassKilograms mass)
	{
		this(mass.value * AccelerationMetersPerSecondPerSecond.gravity.value);
	}

	/**
	 * Constructor
	 * 
	 * @param value     double value to be used for initial value
	 * @param direction double value for direction of force vector
	 */
	public ForceNewtons(double value, double direction)
	{
		super(value, direction);
	}

	/**
	 * Constructor
	 * 
	 * @param value     double value to be used for initial value
	 * @param direction double value for direction of force vector
	 * @param id        unique ID, e.g. index into array of values
	 */
	public ForceNewtons(double value, double direction, long id)
	{
		super(value, direction);
		this.id = id;
	}

	/**
	 * Constructor - magnitude and direction as simple 2D vector type
	 * 
	 * @param vector2D vector whose magnitude and direction are used for initial
	 *                 values
	 */
	public ForceNewtons(Vector2DValueType vector2D)
	{
		this(vector2D.value, vector2D.direction.value);
	}

	/**
	 * Constructor - default zero values
	 */
	public ForceNewtons()
	{
		super(0, 0);
	}

	@Operation
	@Override
	public ForceNewtons horizontalComponent()
	{
		return new ForceNewtons(super.horizontalComponent());
	}

	@Operation
	@Override
	public ForceNewtons verticalComponent()
	{
		return new ForceNewtons(super.verticalComponent());
	}

	/**
	 * Returns instance that is the vector sum of the specified set of vectors
	 * 
	 * @param forces list of force values (as vector values) to be summed
	 * @return force as sum of the specified forces
	 */
	@Operation
	public static ForceNewtons sum(List<Vector2DValueType> forces)
	{
		ForceNewtons result = new ForceNewtons(0, 0);
		result.setValue(Vector2DValueType.sum(forces));
		return result;
	}

	/**
	 * Sets this force values to the values of the specified force
	 * 
	 * @param force force whose values are to be used to set this force values
	 */
	public void setValue(ForceNewtons force)
	{
		super.setValue(force);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Newtons;
	}

	@Override
	public String stackNamesString()
	{
		return String.format("%s(value=%8.4f", getClass().getSimpleName(), value);
	}

	@Override
	public String toString()
	{
		return String.format("ForceNewtons [value=%s, units=%s, direction=%s]", value, units, direction);
	}
}
