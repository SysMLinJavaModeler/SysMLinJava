package sysmlinjava.analysis.common;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

/**
 * Axis definition for a categorical axis in a chart display, e.g. bar chart.
 * 
 * @author ModelerOne
 *
 */
public class CategoriesAxis implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -2225302723021161670L;

	/**
	 * Title of the axis, which need not include any parenthetical "units" portion
	 */
	public String title;
	/**
	 * List of the categories (string names) on the axis
	 */
	public List<String> categories;

	/**
	 * Consructor
	 * 
	 * @param title      title of the axis
	 * @param categories list of categories on the axis
	 */
	public CategoriesAxis(String title, List<String> categories)
	{
		super();
		this.title = title;
		this.categories = categories;
	}

	@Override
	public String toString()
	{
		StringJoiner joiner = new StringJoiner(", ");
		categories.forEach(state -> joiner.add(state));
		return String.format("CategoriesAxis [title=%s, categories=%s]", title, joiner.toString());
	}
}