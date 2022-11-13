package sysmlinjava.analysis.animatedareadisplay;

import java.io.Serializable;
import sysmlinjava.analysis.common.XYData;

/**
 * Representation of an image object in an area display. Contains the parameters
 * needed to display the object as an image in the display from a specified
 * image file and at a specified position, size, roation, and z-order in the
 * area display.
 * 
 * @author ModelerOne
 *
 */
public class AAImage extends AAObject implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -7826688565768235972L;
	/**
	 * String for default image file URL used only if null URL is provided in
	 * constructor
	 */
	public static final String defaultImageFileURL = "file:defaultImage.png";

	/**
	 * URL of the file that contains the image to be displayed in the area display.
	 * If the URL is for a file on the local file system, be sure to use an absolute
	 * path to the file. The file will be loaded from the "working" directory of the
	 * TaskMaster, so use of a relative path to the image file is likely to fail to
	 * locate it properly.
	 */
	public String imageFileURL;
	/**
	 * Width (pixels) to resize the image to for the display. Null or zero for no
	 * resize
	 */
	public Integer resizeWidth;
	/**
	 * Height (pixels) to resize the image to for the display. Null or zero for no
	 * resize
	 */
	public Integer resizeHeight;
	/**
	 * Degrees (clockwise) to rotate the image to for the display. Null or zero for
	 * no resize
	 */
	public Integer rotateDegrees;
	/**
	 * X,Y position in the display of the center of the image
	 */
	public XYData position;
	/**
	 * Order of the image in the layers of objects in the area display. Lower number
	 * is closer to viewer.
	 */
	public Integer zOrder;

	/**
	 * Constructor for all attributes
	 * 
	 * @param uid           unique identifier
	 * @param action        the action to be performed in the display of the text
	 * @param imageFileURL  URL for the file that contains the image to be
	 *                      displayed.
	 * @param resizeWidth   width (pixels) to resize the image to for the display.
	 *                      Null or zero for no resize
	 * @param resizeHeight  height (pixels) to resize the image to for the display.
	 *                      Null or zero for no resize
	 * @param rotateDegrees degrees (clockwise) to rotate the image to for the
	 *                      display. Null or zero for no resize
	 * @param position      X,Y position in the display of the center of the image
	 * @param zOrder        order of the image in the layers of objects in the area
	 *                      display. Lower number is closer to viewer.
	 */
	public AAImage(String uid, Action action, String imageFileURL, Integer resizeWidth, Integer resizeHeight, Integer rotateDegrees, XYData position, Integer zOrder)
	{
		super(uid, action);
		switch (action)
		{
		case create:
			this.position = position != null ? position : new XYData(0, 0);
			this.imageFileURL = imageFileURL != null ? imageFileURL : defaultImageFileURL;
			this.resizeWidth = resizeWidth != null ? resizeWidth : 0;
			this.resizeHeight = resizeHeight != null ? resizeHeight : 0;
			this.rotateDegrees = rotateDegrees != null ? rotateDegrees : 0;
			this.zOrder = zOrder != null ? zOrder : 0;
			break;
		case update:
			this.imageFileURL = imageFileURL;
			this.resizeWidth = resizeWidth;
			this.resizeHeight = resizeHeight;
			this.rotateDegrees = rotateDegrees;
			this.position = position;
			this.zOrder = zOrder;
			break;
		case delete:
			break;
		default:
			break;
		}
	}

	/**
	 * Updates/changes the image display as specified by parameter values, with
	 * value {@code null} indicating no change
	 * 
	 * @param action        the action to be performed in the display of the text
	 * @param imageFileURL  URL for the file that contains the image to be
	 *                      displayed.
	 * @param resizeWidth   width (pixels) to resize the image to for the display.
	 *                      Null or zero for no resize
	 * @param resizeHeight  height (pixels) to resize the image to for the display.
	 *                      Null or zero for no resize
	 * @param rotateDegrees degrees (clockwise) to rotate the image to for the
	 *                      display. Null or zero for no resize
	 * @param position      X,Y position in the display of the center of the image
	 * @param zOrder        order of the image in the layers of objects in the area
	 *                      display. Lower number is closer to viewer.
	 */
	public void update(Action action, String imageFileURL, Integer resizeWidth, Integer resizeHeight, Integer rotateDegrees, XYData position, Integer zOrder)
	{
		this.action = action != null ? action : Action.none;
		this.imageFileURL = imageFileURL;
		this.resizeWidth = imageFileURL != null ? (resizeWidth != null ? resizeWidth : 0) : null;
		this.resizeHeight = imageFileURL != null ? (resizeHeight != null ? resizeHeight : 0) : null;
		this.rotateDegrees = rotateDegrees;
		this.position = position;
		this.zOrder = zOrder;
	}

	@Override
	public String toString()
	{
		return String.format("AAImage [uid=%s, action=%s, imageFileURL=%s, resizeWidth=%s, resizeHeight=%s, rotateDegrees=%s, position=%s, zOrder=%s]", uid, action, imageFileURL, resizeWidth, resizeHeight, rotateDegrees, position, zOrder);
	}
}
