package sysmlinjava.views.animatedareadisplay;

/**
 * Enumeration of the action to be performed on the {@code AAObject}, i.e.
 * create it, update it, or delete it from the area display. Or do nothing
 * (none) if no operation to be performed.
 * 
 * @author ModelerOne
 *
 */
public enum AnimatedAreaActionEnum
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
}