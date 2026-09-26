/*
 * Copyright (C) 2026 SysMLinJava, LLC.
 *
 * This file is part of the SysMLinJava framework.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package sysmlinjava.attributetypes;

import sysmlinjava.javaannotations.attributes.Attribute;
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
public class Complex extends SysMLAttributeType
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
		notifyAttributeObservers();
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

	/** Compute the magnitude of a complex number
	 * 
	 * @return magnitude value
	 */
	public double magnitude()
	{
		return Math.sqrt(valueReal * valueReal + valueImaginary * valueImaginary);
	}

	/**
	 * Returns complex that is the addition of this and an other complex
	 * 
	 * @param other the other complex to add to this complex to produce new complex
	 * @return complex that is the addtion of this and the other complex values
	 */
	public Complex added(Complex other)
	{
		return new Complex(this.valueReal + other.valueReal, this.valueImaginary + other.valueImaginary);
	}

	/**
	 * Adds an other complex to this complex
	 * 
	 * @param other the other complex to add to this complex
	 */
	public void add(Complex other)
	{
		valueReal += other.valueReal;
		valueImaginary += other.valueImaginary;
	}

	/**
	 * Returns complex that is multiplacation of this and an other complex
	 * 
	 * @param other the other complex to multiply this complex by to produce new
	 *              complex
	 * @return complex that is the product of this and the other complex values
	 */
	public Complex multipliedBy(Complex other)
	{
		return new Complex(this.valueReal * other.valueReal - this.valueImaginary * other.valueImaginary, this.valueReal * other.valueImaginary + this.valueImaginary * other.valueReal);
	}

	/**
	 * Multiplies this complex by specified comples
	 * 
	 * @param other the other complex to multiply this complex by
	 */
	public void multiplyBy(Complex other)
	{
		valueReal = valueReal * other.valueReal - valueImaginary * other.valueImaginary;
		valueImaginary = valueReal * other.valueImaginary + valueImaginary * other.valueReal;
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Numeric;
	}

}
