package sysmlinjava.valuetypes;

import java.io.Serializable;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the SysMl Real represented by a java
 * {@code double} value
 * <p>
 * This representation of the real number type is for a generic real as used in
 * SysML. Hence the use of a Java {@code double} for its value. This SysML real
 * is not the same as the java float, double, Float or Double types as SysML
 * does not discriminate between bit-lengths of real or floating- point numeric
 * types as the Java language does.
 * 
 * <b>Note:</b> as an {@code ObservableValue} this value type can be "observed"
 * by other objects. {@code ValueObserver}s call the {@code addValueObserver()}
 * operation to be notiified (called-back) by the {@code Real} object of any
 * change in its value. This notification will only occur if and when the
 * {@code RReal} {@code value} is changed by a call to the {@code setValue()}
 * operation. So, while the {@code RReal.value} is publicly accessible and can
 * be changed by direct assignment, the {@code setValue()} operation must be
 * used if {@code ValueObserver}s are to be automaticall notified of the change.
 * 
 * @author ModelerOne
 *
 */
public class RReal extends SysMLValueType implements Serializable
{
	/** Serializable ID */
	private static final long serialVersionUID = -1566489011777283872L;
	/**
	 * Constant double that can be used to assign an initial zero value that also
	 * indicates the actual value is assigned dynamically, i.e. sometime after
	 * initial creation.
	 */
	@Attribute
	public static final double dynamicallyAssignedValue = 0;
	/**
	 * Current value of the RReal
	 */
	public double value;

	/**
	 * Constructor - initial value. Units are assumed to be simple numeric units,
	 * i.e. simply a real value.
	 * 
	 * @param value double value of the RReal
	 */
	public RReal(double value)
	{
		super();
		this.value = value;
	}

	/**
	 * Constructor - copy
	 * 
	 * @param copied RReal instance whose value is to be the initial value of this
	 *               copy
	 */
	public RReal(RReal copied)
	{
		super(copied);
		this.value = copied.value;
	}

	/**
	 * Constructor specifying the real's value and a probability distribution for
	 * the value being a random variable. Units are assumed to be simple numeric
	 * units, i.e. simply a real value.
	 * 
	 * @param value        double value of the RReal
	 * @param distribution probability distribution for the value as a random
	 *                     variable
	 */
	public RReal(double value, SysMLProbabilityDistribution distribution)
	{
		super(distribution);
		this.value = value;
	}

	/**
	 * Sets the value to the specified double value and notifies all
	 * {@code ValueChangeObservers} of the change.
	 * 
	 * @param value double value that this value is to be set to
	 */
	public void setValue(double value)
	{
		this.value = value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets the value to the specified RReal value and notifies all
	 * {@code ValueChangeObservers} of the change.
	 * 
	 * @param value RReal whose value this value is to be set to
	 */
	public void setValue(RReal value)
	{
		setValue(value.value);
	}

	/**
	 * Returns this value
	 * 
	 * @return this value
	 */
	public double getValue()
	{
		if (probabilityDistribution.isPresent())
			value = probabilityDistribution.get().nextRandom();
		return value;
	}

	/**
	 * Returns this value changed to its value added to specified real value
	 * 
	 * @param value double value to add to this value
	 */
	public void add(double value)
	{
		this.value += value;
	}

	/**
	 * Returns this value changed to its value added to specified real value
	 * 
	 * @param real RReal value to add to this value
	 */
	public void add(RReal real)
	{
		this.value += real.value;
	}

	/**
	 * Returns RReal for this value added to specified RReal value
	 * 
	 * @param real RReal value to be added to this value
	 * @return RReal for this value added to specified RReal value
	 */
	public RReal added(RReal real)
	{
		return new RReal(this.value + real.value);
	}

	/**
	 * Returns RReal for specified RReal value subtracted from this value
	 * 
	 * @param real RReal value to be subtracted from this value
	 * @return RReal for specified RReal value subtracted from this value
	 */
	public RReal subtracted(RReal real)
	{
		return new RReal(this.value - real.value);
	}

	/**
	 * Changes this value to its value subtracted by specified double value
	 * 
	 * @param value double value to subtract from this value
	 */
	public void subtract(double value)
	{
		this.value -= value;
	}

	/**
	 * Changes this value to its value multiplied by specified double value
	 * 
	 * @param value double value to multiply this value by
	 */
	public void multiply(double value)
	{
		this.value *= value;
	}

	/**
	 * Changes this value to its value multiplied by specified real value
	 * 
	 * @param real RReal value to multiply this value by
	 */
	public void multiplyBy(RReal real)
	{
		this.value *= real.value;
	}

	/**
	 * Returns new instance that is this value multiplied by specified real value
	 * 
	 * @param real RReal value to multiply this value by
	 * @return instance of this value multiplied by specified value
	 */
	public RReal multipliedBy(RReal real)
	{
		return new RReal(this.value * real.value);
	}

	/**
	 * Returns new instance that is this value divided by specified real value
	 * 
	 * @param value double value to divide this value by
	 * @return instance of this value divided by specified value
	 */
	public RReal dividedBy(double value)
	{
		return new RReal(this.value / value);
	}

	/**
	 * Returns new instance that is this value divided by specified real value
	 * 
	 * @param real RReal value to divide this value by
	 * @return instance of this value divided by specified value
	 */
	public RReal dividedBy(RReal real)
	{
		return new RReal(this.value / real.value);
	}

	/**
	 * This value changed to its value divided by specified real value
	 * 
	 * @param real RReal value with which this value is to be divided by
	 */
	public void divide(RReal real)
	{
		this.value /= real.value;
	}

	/**
	 * This value changed to its value divided by specified real value
	 * 
	 * @param value double value with which this value is to be divided by
	 */
	public void divide(double value)
	{
		this.value /= value;
	}

	/**
	 * Returns new instance that is this value negated
	 * 
	 * @return instance of this value megated
	 */
	public RReal negated()
	{
		return new RReal(this.value *= -1.0);
	}

	/**
	 * This value changed to its absolute value
	 */
	public void abs()
	{
		value = Math.abs(value);
	}

	/**
	 * Returns new instance that is this value changed to its absolute value
	 * 
	 * @return instance that is this value changed to its absolute value
	 */
	public RReal absed()
	{
		return new RReal(Math.abs(value));
	}

	/**
	 * This value changed to its negtive value
	 */
	public void negate()
	{
		value *= -1.0;
	}

	/**
	 * Sets this value to zero
	 */
	public void zero()
	{
		value = 0.0;
	}

	/**
	 * Returns whether this value is less than specified value
	 * 
	 * @param real value to compare
	 * @return whether this value is less than specified value
	 */
	public boolean lessThan(RReal real)
	{
		return this.value < real.value;
	}

	/**
	 * Returns whether this value is less than specified value
	 * 
	 * @param value double value to compare
	 * @return whether this value is less than specified value
	 */
	public boolean lessThan(double value)
	{
		return this.value < value;
	}

	/**
	 * Returns whether this value is greater than specified value
	 * 
	 * @param real value to compare
	 * @return whether this value is greater than specified value
	 */
	public boolean greaterThan(RReal real)
	{
		return this.value > real.value;
	}

	/**
	 * Returns whether this value is greater than specified value
	 * 
	 * @param value double value to compare
	 * @return whether this value is greater than specified value
	 */
	public boolean greaterThan(double value)
	{
		return this.value > value;
	}

	/**
	 * Returns whether this value is less than or equal than specified value
	 * 
	 * @param real value to compare
	 * @return whether this value is less than or equal than specified value
	 */
	public boolean lessThanOrEqualTo(RReal real)
	{
		return this.value <= real.value;
	}

	/**
	 * Returns whether this value is greater than or equal than specified value
	 * 
	 * @param other double value to compare
	 * @return whether this value is greater than or equal than specified value
	 */
	public boolean greaterThanOrEqualTo(double other)
	{
		return this.value >= other;
	}

	/**
	 * Returns whether this value is greater than or equal than specified value
	 * 
	 * @param real RReal value to compare
	 * @return whether this value is greater than or equal than specified value
	 */
	public boolean greaterThanOrEqualTo(RReal real)
	{
		return this.value >= real.value;
	}

	/**
	 * Returns new instance that is the absolute value of this value
	 * 
	 * @return instance that is the absolute value of this value
	 */
	public RReal absoluted()
	{
		return new RReal(Math.abs(value));
	}

	/**
	 * Returns new instance that is the modulus value of this value for the
	 * specified value
	 * 
	 * @param value value of the divisor for the modulus
	 * @return instance that is the modulus value of this value for the specified
	 *         value
	 */
	public RReal moduloOf(RReal value)
	{
		return new RReal(this.value % value.value);
	}

	/**
	 * Returns whether this value is zero
	 * 
	 * @return true if this value is zero, false otherwise
	 */
	public boolean isZero()
	{
		return value == 0.0;
	}

	/**
	 * Returns new instance whose initial value is the specified value
	 * 
	 * @param value value to be the initial value
	 * @return instance whose initial value is the specified value
	 */
	public static RReal of(double value)
	{
		return new RReal(value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof RReal)
			return value == ((RReal)obj).value;
		else
			return false;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("RReal [value=");
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
