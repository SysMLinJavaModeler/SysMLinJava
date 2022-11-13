package sysmlinjava.valuetypes;

import sysmlinjava.annotations.Attribute;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava's representation of the value type for complex values, i.e. two
 * (real and imaginary) values of {@code double} type.
 * <p>
 * This representation of the complex number type is for a generic complex
 * number as used in SysML. Hence the use of two Java {@code
 * double}s for its value. This SysML complex number is not the same as two java
 * float, double, Float or Double types as SysML does not discriminate between
 * bit-lengths of real or floating- point numeric types as does the Java
 * language.
 * <p>
 * <b>Note:</b> as an {@code observableValueType} this value type can be
 * "observed" by other objects. {@code ValueObserver}s call the {@code
 * addValueObserver()} operation to be notiified (called-back) by the {@code
 * Complex} object of any change in its value. This notification will only occur
 * if and when the {@code Complex} {@code value} is changed by a call to the
 * {@code
 * setValue()} operation. So, while the {@code Complex value} is publicly
 * accessible and can be changed by direct assignment, the {@code setValue()}
 * operation must be used if {@code ValueObserver}s are to be notified of the
 * change.
 * 
 * @author ModelerOne
 *
 */
public class Complex extends SysMLValueType
{
	/**
	 * Attribute for the real part of the complex variable
	 */
	@Attribute
	public double valueReal;
	/**
	 * Attribute for the "imaginary" part of the complex variable
	 */
	@Attribute
	public double valueImaginary;

	/**
	 * Constructor
	 * 
	 * @param valueReal      initial value for the real part
	 * @param valueImaginary initial value of the imaginary part
	 */
	public Complex(double valueReal, double valueImaginary)
	{
		super();
		this.valueReal = valueReal;
		this.valueImaginary = valueImaginary;
	}

	/**
	 * Sets the value of the complex variable
	 * 
	 * @param valueReal      set value for the real part
	 * @param valueImaginary set value of the imaginary part
	 */
	public void setValues(double valueReal, double valueImaginary)
	{
		this.valueReal = valueReal;
		this.valueImaginary = valueImaginary;
		notifyValueChangeObservers();
	}

	/**
	 * Returns the real part of the complex variable
	 * 
	 * @return real part
	 */
	public double getValueReal()
	{
		return valueReal;
	}

	/**
	 * Returns the imaginary part of the complex variable
	 * 
	 * @return imaginary part
	 */
	public double getValueImaginary()
	{
		return valueImaginary;
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}
}
