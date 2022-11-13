package sysmlinjava.analysis.animatedareadisplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Optional;
import sysmlinjava.analysis.animatedareadisplay.AAObject.Action;
import sysmlinjava.analysis.animatedareadisplay.AAText.FontWeightEnum;
import sysmlinjava.analysis.common.ColorEnum;
import sysmlinjava.analysis.common.XYData;
import sysmlinjava.valuetypes.Point2D;
import sysmlinjava.valuetypes.Polyline2D;

/**
 * Definition of the objects to be used to update the area display, i.e.
 * updates/changes to be made for all text, image, and line objects in the area
 * display.
 * 
 * @author ModelerOne
 *
 */
public class AnimatedAreaDisplayData implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = 2094915479474631371L;

	/**
	 * List of the text objects to be in the display
	 */
	public ArrayList<AAText> texts;
	/**
	 * List of the iamge objects to be in the display
	 */
	public ArrayList<AAImage> images;
	/**
	 * List of the line objects to be in the display
	 */
	public ArrayList<AALine> lines;

	/**
	 * Constructor for default initial values, i.e. empty sets of text, image, and
	 * line objects
	 */
	public AnimatedAreaDisplayData()
	{
		super();
		this.texts = new ArrayList<>();
		this.images = new ArrayList<>();
		this.lines = new ArrayList<>();
	}

	/**
	 * Constructor for specifying any/all display objects to be updated/changed
	 * 
	 * @param texts  list of the text objects to be in the display
	 * @param images list of the image objects to be in the display
	 * @param lines  list of the line objects to be in the display
	 */
	public AnimatedAreaDisplayData(ArrayList<AAText> texts, ArrayList<AAImage> images, ArrayList<AALine> lines)
	{
		super();
		this.texts = texts != null ? texts : new ArrayList<>();
		this.images = images != null ? images : new ArrayList<>();
		this.lines = lines != null ? lines : new ArrayList<>();
	}

	/**
	 * Updates/changes the uniquely identified text display as specified by
	 * parameter values, with value {@code null} indicating no change
	 * 
	 * @param uid           unique identifier
	 * @param action        the action to be performed in the display of the text
	 * @param text          text to be displayed
	 * @param fontName      name of the font used to display the text
	 * @param fontSize      size of the font used to display the text
	 * @param fontWeight    weight of the font used to display the text
	 * @param fontColor     color of the font used to display the text
	 * @param fontItalics   whether the font is be italics (vs. regular)
	 * @param rotateDegrees degrees to rotate text
	 * @param position      X,Y pixel position of the lower/right corner of the text
	 *                      display
	 * @param zOrder        Order of the text in the layers of objects in the area
	 *                      display. Lower number is closer to viewer.
	 */
	public void updateText(String uid, Action action, String text, String fontName, Integer fontSize, FontWeightEnum fontWeight, ColorEnum fontColor, Boolean fontItalics, Integer rotateDegrees, Point2D position, Integer zOrder)
	{
		Optional<AAText> next = getText(uid);
		if (next.isPresent())
		{
			XYData xyPosition = position != null ? new XYData(position.xValue, position.yValue) : null;
			next.get().update(action, text, fontName, fontSize, fontWeight, fontColor, fontItalics, rotateDegrees, xyPosition, zOrder);
		}
	}

	/**
	 * Updates/changes the uniquely identified image display as specified by
	 * parameter values, with value {@code null} indicating no change
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
	 * @param position      X,Y position in the display of the center (half-width,
	 *                      half-height) of the image
	 * @param zOrder        order of the image in the layers of objects in the area
	 *                      display. Lower number is closer to viewer.
	 */
	public void updateImage(String uid, Action action, String imageFileURL, Integer resizeWidth, Integer resizeHeight, Integer rotateDegrees, Point2D position, Integer zOrder)
	{
		Optional<AAImage> aimage = getImage(uid);
		if (aimage.isPresent())
		{
			XYData xyPosition = position != null ? new XYData(position.xValue, position.yValue) : null;
			aimage.get().update(action, imageFileURL, resizeWidth, resizeHeight, rotateDegrees, xyPosition, zOrder);
		}
	}

	/**
	 * Updates/changes the line display as specified by parameter values, with value
	 * {@code null} indicating no change
	 * 
	 * @param uid        unique identifier
	 * @param action     action to be performed on the line
	 * @param polyline2D set of x,y points to be connected for the line
	 * @param color      Color of the line
	 * @param width      width, in points, of the line
	 * @param zOrder     order of the line in the layers of objects in the area
	 *                   display. Lower number is closer to viewer.
	 */
	public void updateLine(String uid, Action action, Polyline2D polyline2D, ColorEnum color, Integer width, Integer zOrder)
	{
		Optional<AALine> aline = getLine(uid);
		if (aline.isPresent())
			if (polyline2D != null)
			{
				ArrayList<XYData> xyPoints = new ArrayList<>();
				for (Point2D point2D : polyline2D.value)
					aline.get().points.add(new XYData(point2D.xValue, point2D.yValue));
				aline.get().update(action, xyPoints, color, width, zOrder);
			}
			else
				aline.get().update(action, null, color, width, zOrder);
	}

	/**
	 * Gets the AText object for the specified unique identifier
	 * 
	 * @param uid unique identifier of the object
	 * @return AText object with the specified unique identifier
	 */
	private Optional<AAText> getText(String uid)
	{
		Optional<AAText> result = Optional.empty();
		ListIterator<AAText> iter = texts.listIterator();
		while (result.isEmpty() && iter.hasNext())
		{
			AAText next = iter.next();
			if (next.uid.equals(uid))
				result = Optional.of(next);
		}
		return result;
	}

	/**
	 * Gets the AImage object for the specified unique identifier
	 * 
	 * @param uid unique identifier of the object
	 * @return AImage object with the specified unique identifier
	 */
	private Optional<AAImage> getImage(String uid)
	{
		Optional<AAImage> result = Optional.empty();
		ListIterator<AAImage> iter = images.listIterator();
		while (result.isEmpty() && iter.hasNext())
		{
			AAImage next = iter.next();
			if (next.uid.equals(uid))
				result = Optional.of(next);
		}
		return result;
	}

	/**
	 * Gets the ALine object for the specified unique identifier
	 * 
	 * @param uid unique identifier of the object
	 * @return ALine object with the specified unique identifier
	 */
	private Optional<AALine> getLine(String uid)
	{
		Optional<AALine> result = Optional.empty();
		ListIterator<AALine> iter = lines.listIterator();
		while (result.isEmpty() && iter.hasNext())
		{
			AALine next = iter.next();
			if (next.uid.equals(uid))
				result = Optional.of(next);
		}
		return result;
	}

	@Override
	public String toString()
	{
		return String.format("AnimatedAreaDisplayData [images=%s, lines=%s]", images, lines);
	}
}
