package sysmlinjava.analysis.animatedareadisplay;

import java.io.Serializable;

/**
 * Base abstract class for representation of an object in an area display.
 * Contains the parameters needed to uniquely identify the object in the display
 * and to specify the action to be taken on the object in the area display.
 * 
 * @author ModelerOne
 *
 *
 */
public abstract class AAObject implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 8621836558579485249L;

	/**
	 * Enumeration of actions that can be performed on the object in the display
	 * 
	 * @author ModelerOne
	 *
	 */
	/**
	 * Enumeration of the action to be performed on the {@code AAObject}, i.e.
	 * create it, update it, or delete it from the area display. Or do nothing
	 * (none) if no operation to be performed.
	 * 
	 * @author ModelerOne
	 *
	 */
	public enum Action
	{
		/**
		 * Create the object in the display
		 */
		create,
		/**
		 * Update (move, rotate, change image of, etc.) the object in the display
		 */
		update,
		/**
		 * Delete (remove) the object from the display
		 */
		delete,
		/**
		 * Do nothing to the object in the display
		 */
		none
	};

	/**
	 * Unique identifier for the area display object
	 */
	public String uid;
	/**
	 * Action to be taken on the area display object
	 */
	public Action action;

	/**
	 * Constructor
	 * 
	 * @param uid    unique identifier for the area display object
	 * @param action action to be taken on the area display object
	 */
	public AAObject(String uid, Action action)
	{
		this.uid = uid != null && !uid.isBlank() ? uid : this.toString();
		this.action = action != null ? action : Action.create;
	}
}