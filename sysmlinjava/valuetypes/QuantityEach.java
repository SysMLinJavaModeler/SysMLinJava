package sysmlinjava.valuetypes;

import sysmlinjava.probability.SysMLProbabilityDistribution;
import sysmlinjava.units.SysMLinJavaUnits;

/**
 * SysMLinJava value type for quantity in terms of number of each item in
 * quantity
 * 
 * @author ModelerOne
 *
 */
public class QuantityEach extends IInteger
{
	/** Serializable ID*/private static final long serialVersionUID = 3184701119252268777L;

	/**
	 * Constant value for a zero quantity
	 */
	public static final QuantityEach none = new QuantityEach(0);

	/**
	 * Constructor
	 * 
	 * @param value long value to be used as initial value
	 */
	public QuantityEach(long value)
	{
		super(value);
	}

	/**
	 * Constructor for initial value and probability distribution
	 * 
	 * @param value        initial value
	 * @param distribution probability distribution used to produce random values
	 *                     via consecutive {@code getValue()} calls.
	 */
	public QuantityEach(long value, SysMLProbabilityDistribution distribution)
	{
		super(value, distribution);
	}

	@Override
	protected void createUnits()
	{
		units = SysMLinJavaUnits.Each;
	}
}
