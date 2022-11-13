package sysmlinjava.views;

import java.util.List;
import sysmlinjava.common.SysMLClass;

/**
 * Indicates that the field that follows represents a SysML "view". The field
 * variable should be an instance of the {@code SysMLView} and should be
 * created/initialized in the variable declaration.
 * <p>
 * Instances of the {@code SysMLView} should be declared in an extension of the
 * {@code SysMLViews} class. See the {@code SysMLViews} class for more info on
 * declaring instances of this class.
 * 
 * @author ModelerOne
 * @see sysmlinjava.views.SysMLView
 * @see sysmlinjava.views.SysMLViews
 */
public final class SysMLView extends SysMLClass
{
	/**
	 * Viewpoint that is contained by this view
	 */
	public SysMLViewpoint viewpoint;
	/**
	 * List of other views that are also parts of this view
	 */
	public List<SysMLView> views;

	/**
	 * Constructor of all attributes
	 * 
	 * @param name      name of the view
	 * @param viewpoint viewpoint contained by the view
	 * @param views     other view that are part of this view
	 */
	public SysMLView(String name, SysMLViewpoint viewpoint, List<SysMLView> views)
	{
		super(name);
		this.viewpoint = viewpoint;
		this.views = views;
	}
}
