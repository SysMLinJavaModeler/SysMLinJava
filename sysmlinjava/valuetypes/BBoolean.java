package sysmlinjava.valuetypes;

import java.util.Optional;
import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for the SysML Boolean.<br>
 * <p>
 * Note that the {@code Boolean} name clashes with the {@code java.lang.Boolean}
 * name. Therefore, this SysMLinJava version uses the double B spelling
 * <p>
 * <b>Note:</b> as an {@code ObservableValue} implementation, this value type
 * can be "observed" by other objects. {@code ValueObserver}s call the
 * {@code addValueObserver()} operation to be notiified (called-back) by the
 * {@code Boolean} object of any change in its value. This notification will
 * only occur if and when the {@code Boolean value} is changed by a call to the
 * {@code setValue()} operation. So, while the {@code Boolean value} is publicly
 * accessible and can be changed by direct assignment, the {@code setValue()}
 * operation must be used if/when {@code ValueObserver}s are to be notified of
 * the change.<br>
 * 
 * @author ModelerOne
 *
 */
public class BBoolean extends SysMLValueType
{
	/**
	 * Value for true. Use this only for values that won't change.
	 */
	public static final BBoolean True = new BBoolean(true);
	/**
	 * Value for false. Use this only for values that won't change.
	 */
	public static final BBoolean False = new BBoolean(false);

	/**
	 * Attribute for the java boolean value to represent the {@code BBoolean}
	 * instance
	 */
	@Attribute
	public boolean value;

	/**
	 * Constructor
	 * 
	 * @param value boolean value that is to be the initial value
	 */
	public BBoolean(boolean value)
	{
		super();
		this.value = value;
	}

	/**
	 * Constructor
	 * 
	 * @param value boolean value that is to be the initial value
	 * @param name  unique name of the {@code BBoolean} instance
	 */
	public BBoolean(boolean value, String name)
	{
		super();
		this.value = value;
		this.name = Optional.of(name);
	}

	/**
	 * Constructor - copy
	 * 
	 * @param value {@code BBoolean} instance whose value is to be the initial/copy
	 *              value
	 */
	public BBoolean(BBoolean value)
	{
		super(value);
		this.value = value.value;
	}

	/**
	 * Returns {@code BBoolean} instance whose value is that of the specified
	 * boolean
	 * 
	 * @param value boolean value that is to be the initial value
	 * @return instance with specified initial value
	 */
	public static BBoolean of(boolean value)
	{
		return new BBoolean(value);
	}

	/**
	 * Returns the current boolean value
	 * 
	 * @return current value
	 */
	public boolean getValue()
	{
		return value;
	}

	/**
	 * Sets this BBoolean to the specified value and notifies all current value
	 * change observers, if any.
	 * <p>
	 * <b>Note:</b>Use this method to change the BBoolean value if/when notification
	 * of value change observers is needed.
	 * 
	 * @param value value to be set as a boolean
	 */
	public void setValue(boolean value)
	{
		this.value = value;
		notifyValueChangeObservers();
	}

	/**
	 * Sets this BBoolean to the specified value and notifies all current value
	 * change observers, if any.
	 * <p>
	 * <b>Note:</b>Use this method to change the BBoolean value if/when notification
	 * of value change observers is needed.
	 * 
	 * @param value value to be set as a BBoolean
	 */
	public void setValue(BBoolean value)
	{
		this.value = value.value;
		notifyValueChangeObservers();
	}

	/**
	 * Returns whether current value is true
	 * 
	 * @return true if value is true, false otherwise
	 */
	public boolean isTrue()
	{
		return value;
	}

	/**
	 * Returns whether current value is false
	 * 
	 * @return true if value is false, false otherwise
	 */
	public boolean isFalse()
	{
		return !value;
	}

	/**
	 * Returns whether this value is equal to specified value
	 * 
	 * @param bool value to compare
	 * @return true if bool is equal to this value, false otherwise
	 */
	public boolean equalTo(BBoolean bool)
	{
		return value == bool.value;
	}

	/**
	 * Returns whether this value is equal to specified value
	 * 
	 * @param bool value to compare
	 * @return true if bool is equal to this value, false otherwise
	 */
	public boolean equalTo(boolean bool)
	{
		return value == bool;
	}

	/**
	 * Returns logical negation (NOT) of this value
	 * 
	 * @return NOT of this value
	 */
	public BBoolean not()
	{
		return new BBoolean(!value);
	}

	/**
	 * Returns logical conjunction (AND) of this value with specified value
	 * 
	 * @param bool value to be ANDed with this
	 * @return true if this value AND bool value is true, false otherwise
	 */
	public BBoolean and(BBoolean bool)
	{
		return new BBoolean(value && bool.value);
	}

	/**
	 * Returns logical disjunction (OR) of this value with specified value
	 * 
	 * @param bool value to be ORed with this
	 * @return true if this value OR bool value is true, false otherwise
	 */
	public BBoolean or(BBoolean bool)
	{
		return new BBoolean(value || bool.value);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Logical;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("BBoolean [value=");
		builder.append(value);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
}