package sysmlinjava.views;

import sysmlinjava.common.SysMLInterface;

/**
 * SysMLinJava representation of SysML's createView activity. The
 * {@code SysMLCreateView} activity, a functional interface realized by a lambda
 * expression, can be instantiated and executed in any model element to
 * programatically generate a SysML view of some or all of the model in which it
 * is embedded. Instances of the {@code SysMLCreateViews} activity could be used
 * to generate document views of the model in HTML, PDF, text, etc. It could
 * also be used to generate XMI views of the model for model export to other
 * SysML modeling tools. The SysMLinJava TaskMaster&trade; tool uses this
 * activity to generate a number of its views of SysMLinJava models.
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLCreateView extends SysMLInterface
{
	/**
	 * Creates the specified view
	 * 
	 * @param view the view to be created by the activity
	 */
	void create(SysMLView view);
}
