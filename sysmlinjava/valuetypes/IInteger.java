package sysmlinjava.valuetypes;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the SysML Integer.<br>
 * 
 * <p>
 * * Note that the {@code Integer} name clashes with the
 * {@code java.lang.Integer} name. Therefore, this SysMLinJava version uses the
 * double I spelling.
 * <p>
 * This representation of the integer type is for a generic integer as used in
 * SysML. Hence the use of a Java long for its value. This SysML integer is not
 * the same as the java int or Integer types as SysML does not discriminate
 * between bit-lengths of numeric types as does the Java language.
 * <p>
 * <b>Note:</b> as an {@code ObservableValue} implementation, this value type
 * can be "observed" by other objects. {@code ValueObserver}s call the
 * {@code addValueObserver()} operation to be notiified (called-back) by the
 * {@code Int} object of any change in its value. This notification will only
 * occur if and when the {@code IInteger value} is changed by a call to the
 * {@code setValue()}, {@code increment()}, or {@code decrement()} operations.
 * So, while the {@code IInteger value} is publicly accessible and can be
 * changed by direct assignment, one of the value changing operations must be
 * used if {@code ValueObserver}s are to be notified of the change.
 * 
 * @author ModelerOne
 *
 */
public class IInteger extends SysMLValueType implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -6005370778039259497L;
	/**
	 * Constant long that can be used to assign an initial zero value that also
	 * indicates the actual value is assigned dynamically, i.e. sometime after
	 * initial creation.
	 */
	public static final long dynamicallyAssignedValue = 0;
	/**
	 * Constant instance for the value of zero
	 */
	public static final IInteger zero = new IInteger(0);
	/**
	 * Attribute for the current value of the IInteger as a long
	 */
	@Attribute
	public long value;

	/**
	 * Constructor - initial value, units defaulted to Numeric
	 * 
	 * @param value long value to be used as initial value of the IInteger
	 */
	public IInteger(long value)
	{
		super();
		this.value = value;
	}

	/**
	 * Constructor - initial value, units defaulted to Numeric
	 * 
	 * @param value int value to be used as initial value of the IInteger
	 */
	public IInteger(int value)
	{
		super();
		this.value = value;
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied value to be used as initial value of copy of the IInteger
	 */
	public IInteger(IInteger copied)
	{
		super(copied);
		this.value = copied.value;
	}

	/**
	 * Constructor - initial value and probability distribution for random value,
	 * units defaulted to Numeric
	 * 
	 * @param value        int value to be used as initial value of this random
	 *                     IInteger
	 * @param distribution probability distribution for random values of this
	 *                     IInteger
	 */
	public IInteger(long value, SysMLProbabilityDistribution distribution)
	{
		super(distribution);
		this.value = value;
	}

	/**
	 * Returns instance of a java.lang.Integer-initialized value.
	 * 
	 * @param javaInteger java.lang.Integer value to be used for initial value of
	 *                    returned instance
	 * @return instance of an IInteger value initialized with the specified
	 *         java.lang.Integer value.
	 */
	public static IInteger of(Integer javaInteger)
	{
		return new IInteger(javaInteger);
	}

	/**
	 * Returns instance of a java.lang.Long-initialized value.
	 * 
	 * @param javaLong java.lang.Long value to be used for initial value of returned
	 *                 instance
	 * @return instance of an IInteger value initialized with the specified
	 *         java.lang.Long value.
	 */
	public static IInteger of(Long javaLong)
	{
		return new IInteger(javaLong);
	}

	/**
	 * Returns the current value as a long
	 * 
	 * @return value as a long
	 */
	public long getValue()
	{
		return value;
	}

	/**
	 * Returns the current value as a java.lang.Integer
	 * 
	 * @return current value as a java.lang.Integer
	 */
	public Integer toInteger()
	{
		return (int)value;
	}

	/**
	 * Sets value from specified IInteger and then notifies all value change
	 * observers, if any
	 * 
	 * @param value IInteger whose value is to be used to set this valuew
	 */
	public void setValue(IInteger value)
	{
		this.value = value.value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets the current value to the specified value and then notifies all value
	 * change observers, if any
	 * 
	 * @param value int value to used to set this value
	 */
	public void setValue(int value)
	{
		this.value = value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets the current value to the specified value and then notifies all value
	 * change observers, if any
	 * 
	 * @param value long value to used to set this value
	 */
	public void setValue(long value)
	{
		this.value = value;
		notifyValueChangeObservers();
	}

	/**
	 * Returns value that is this value multiplied by specified value
	 * 
	 * @param multiplier int value used as multiplier of this value
	 * @return integer that is this value multiplied by specified value
	 */
	public int multipliedBy(double multiplier)
	{
		return (int)(value * multiplier);
	}

	/**
	 * Returns value that is this value multiplied by specified value
	 * 
	 * @param multiplier IInteger value used as multiplier of this value
	 * @return IInteger that is this value multiplied by specified value
	 */
	public IInteger multipliedBy(IInteger multiplier)
	{
		return new IInteger(value * multiplier.value);
	}

	/**
	 * Returns value that is this value multiplied by specified value
	 * 
	 * @param multiplier RReal value used as multiplier of this value
	 * @return IInteger that is this value multiplied by specified value
	 */
	public IInteger multipliedBy(RReal multiplier)
	{
		return new IInteger((long)(value * multiplier.value));
	}

	/**
	 * Returns value that is this value divided by specified value
	 * 
	 * @param divisor IInteger value used as divisor of this value
	 * @return long that is this value divided by specified value
	 */
	public long dividedBy(IInteger divisor)
	{
		return value / divisor.value;
	}

	/**
	 * Returns value that is this value divided by specified value
	 * 
	 * @param divisor int value used as divisor of this value
	 * @return long that is this value divided by specified value
	 */
	public long dividedBy(int divisor)
	{
		return value / divisor;
	}

	/**
	 * Returns value that is modulo of this value divided by specified value
	 * 
	 * @param modulus IInteger value used as modulos (divisor) of this value
	 * @return long that is modulo of this value divided by specified modulus value
	 */
	public long moduloOf(IInteger modulus)
	{
		return value % modulus.value;
	}

	/**
	 * Returns value that is modulo of this value divided by specified value
	 * 
	 * @param modulus long value used as modulos (divisor) of this value
	 * @return long that is modulo of this value divided by specified modulus value
	 */
	public long moduloOf(long modulus)
	{
		return value % modulus;
	}

	/**
	 * Increments this value by one and then notifies all value change observers, if
	 * any
	 */
	public void increment()
	{
		value++;
		notifyValueChangeObservers();
	}

	/**
	 * Decrements this value by one and then notifies all value change observers, if
	 * any
	 */
	public void decrement()
	{
		value--;
		notifyValueChangeObservers();
	}

	/**
	 * Returns whether this value is less than specified value
	 * 
	 * @param other IInteger value to compare
	 * @return whether this value is less than specified value
	 */
	public boolean lessThan(IInteger other)
	{
		return value < other.value;
	}

	/**
	 * Returns whether this value is less than specified value
	 * 
	 * @param other int value to compare
	 * @return whether this value is less than specified value
	 */
	public boolean lessThan(int other)
	{
		return value < other;
	}

	/**
	 * Returns whether this value is less than specified value
	 * 
	 * @param other long value to compare
	 * @return whether this value is less than specified value
	 */
	public boolean lessThan(long other)
	{
		return value < other;
	}

	/**
	 * Returns whether this value is greater than specified value
	 * 
	 * @param other IInteger value to compare
	 * @return whether this value is greater than specified value
	 */
	public boolean greaterThan(IInteger other)
	{
		return value > other.value;
	}

	/**
	 * Returns whether this value is greater than specified value
	 * 
	 * @param other int value to compare
	 * @return whether this value is greater than specified value
	 */
	public boolean greaterThan(int other)
	{
		return value > other;
	}

	/**
	 * Returns whether this value is greater than specified value
	 * 
	 * @param other long value to compare
	 * @return whether this value is greater than specified value
	 */
	public boolean greaterThan(long other)
	{
		return value > other;
	}

	/**
	 * Returns whether this value is equal to specified value
	 * 
	 * @param other IInteger value to compare
	 * @return whether this value is equal to specified value
	 */
	public boolean equalTo(IInteger other)
	{
		return value == other.value;
	}

	/**
	 * Returns whether this value is equal to specified value
	 * 
	 * @param other int value to compare
	 * @return whether this value is equal to specified value
	 */
	public boolean equalTo(int other)
	{
		return value == other;
	}

	/**
	 * Returns whether this value is equal to specified value
	 * 
	 * @param other long value to compare
	 * @return whether this value is equal to specified value
	 */
	public boolean equalTo(long other)
	{
		return value == other;
	}

	/**
	 * Returns whether this value is less than or equal to specified value
	 * 
	 * @param other IInteger value to compare
	 * @return whether this value is less than or equal to specified value
	 */
	public boolean lessThanOrEqualTo(IInteger other)
	{
		return value <= other.value;
	}

	/**
	 * Returns whether this value is less than or equal to specified value
	 * 
	 * @param other int value to compare
	 * @return whether this value is less than or equal to specified value
	 */
	public boolean lessThanOrEqualTo(int other)
	{
		return value <= other;
	}

	/**
	 * Returns whether this value is less than or equal to specified value
	 * 
	 * @param other long value to compare
	 * @return whether this value is less than or equal to specified value
	 */
	public boolean lessThanOrEqualTo(long other)
	{
		return value <= other;
	}

	/**
	 * Returns whether this value is greater than or equal than specified value
	 * 
	 * @param other IInteger value to compare
	 * @return whether this value is greater than or equal than specified value
	 */
	public boolean greaterThanOrEqualTo(IInteger other)
	{
		return value >= other.value;
	}

	/**
	 * Returns whether this value is greater than or equal than specified value
	 * 
	 * @param other int value to compare
	 * @return whether this value is greater than or equal than specified value
	 */
	public boolean greaterThanOrEqualTo(int other)
	{
		return value >= other;
	}

	/**
	 * Returns whether this value is greater than or equal than specified value
	 * 
	 * @param other long value to compare
	 * @return whether this value is greater than or equal than specified value
	 */
	public boolean greaterThanOrEqualTo(long other)
	{
		return value >= other;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof IInteger)
			return value == ((IInteger)obj).value;
		else
			return false;
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("IInteger [value=");
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