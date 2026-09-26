package sysmlinjava.attributetypes;

/**
 * Value type for viscous resistance (drag) for a fluid, which is simply a type
 * of force.
 * 
 * @author ModelerOne
 *
 */
public class ViscousResistanceNewtons extends ForceNewtons
{
	private static final long serialVersionUID = -556839566475312102L;

	/**
	 * Constructor
	 * 
	 * @param value initial value
	 */
	public ViscousResistanceNewtons(double value)
	{
		super(value);
	}

	/**
	 * Copy constructor
	 * 
	 * @param copyFrom value from which this value is to be a copy of
	 */
	public ViscousResistanceNewtons(ViscousResistanceNewtons copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new ViscousResistanceNewtons(value);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ViscousResistanceNewtons [direction=");
		builder.append(direction);
		builder.append(", value=");
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
