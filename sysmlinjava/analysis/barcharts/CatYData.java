package sysmlinjava.analysis.barcharts;

import java.io.Serializable;

/**
 * Data for a single category value in a bar chart display
 * 
 * @author ModelerOne
 *
 */
/**
 * 
 */
public class CatYData implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 6019714086065572412L;

	/**
	 * Category of the data
	 */
	public String category;
	/**
	 * Category (y) value
	 */
	public double yValue;

	/**
	 * Constructor
	 * 
	 * @param category category of the data
	 * @param yValue   category (y) value
	 */
	public CatYData(String category, double yValue)
	{
		super();
		this.category = category;
		this.yValue = yValue;
	}

	@Override
	public String toString()
	{
		return String.format("CatYData [category=%s, yValue=%s]", category, yValue);
	}
}