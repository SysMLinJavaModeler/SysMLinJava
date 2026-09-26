package sysmlinjava.attributetypes;

/**
 * Value type for pressure which is simply an extension of a force value type.
 * @author ModelerOne
 *
 */
public class PressureNewtonsPerMeterSquare extends ForceNewtonsPerMeterSquare
{
	private static final long serialVersionUID = -1538795321322592505L;

	/**
	 * Constructor
	 * @param value initial value
	 */
	public PressureNewtonsPerMeterSquare(double value)
	{
		super(value);
	}

	/**
	 * Copy constructor
	 * @param copyFrom pressure from which this value is to be a copy
	 */
	public PressureNewtonsPerMeterSquare(PressureNewtonsPerMeterSquare copyFrom)
	{
		super(copyFrom.value);
	}

	@Override
	public SysMLAttributeType copy()
	{
		return new PressureNewtonsPerMeterSquare(value);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("PressureNewtonsPerMeterSquare [value=");
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
