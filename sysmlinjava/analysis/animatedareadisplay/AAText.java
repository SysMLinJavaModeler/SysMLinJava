package sysmlinjava.analysis.animatedareadisplay;

import java.io.Serializable;
import sysmlinjava.analysis.common.ColorEnum;
import sysmlinjava.analysis.common.XY;

/**
 * Representation of a text object in an area display. Contains the parameters
 * needed to display text in the display at a specified position, font, color,
 * etc.
 * 
 * @author ModelerOne
 *
 */
public class AAText extends AAObject implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -7617924614610509305L;

	/**
	 * Enumerated values for font weight of the text display.
	 */
	public enum FontWeightEnum
	{
		/**
		 * A normal (light) weight
		 */
		normal,
		/**
		 * A medium (a bit thicker) weight
		 */
		medium,
		/**
		 * A bold (as in typical bold font) weight
		 */
		bold
	};

	/**
	 * String of text to be displayed
	 */
	public String text;
	/**
	 * Name of the font used to display the text, e.g. Arial, System, Courier, etc.
	 * Note name must be recognized by font system on host operating system.
	 */
	public String fontName;
	/**
	 * Size of the font of the text to be displayed
	 */
	public Integer fontSize;
	/**
	 * Size of the font of the text to be displayed
	 */
	public FontWeightEnum fontWeight;
	/**
	 * Whether the font of the text to be displayed is italics (vs regular if false)
	 */
	public Boolean fontItalics;
	/**
	 * Color of the text to be displayed
	 */
	public ColorEnum fontColor;
	/**
	 * Clockwise degrees to rotate the text
	 */
	public Integer rotateDegrees;
	/**
	 * X,Y pixel position of the lower/right corner of the text display
	 */
	public XY xyPosition;
	/**
	 * Order of the text in the layers of objects in the area display. Lower number
	 * is closer to viewer.
	 */
	public Integer zOrder;

	/**
	 * Constructor for all attributes
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
	public AAText(String uid, Action action, String text, String fontName, Integer fontSize, FontWeightEnum fontWeight, ColorEnum fontColor, Boolean fontItalics, Integer rotateDegrees, XY position, Integer zOrder)
	{
		super(uid, action);
		switch (action)
		{
		case create:
			this.text = text != null ? text : "<missing text>";
			this.fontName = fontName != null ? fontName : "Arial";
			this.fontSize = fontSize != null ? fontSize : 12;
			this.fontWeight = fontWeight != null ? fontWeight : FontWeightEnum.normal;
			this.fontColor = fontColor != null ? fontColor : ColorEnum.BLACK;
			this.fontItalics = fontItalics != null ? fontItalics : false;
			this.rotateDegrees = rotateDegrees != null ? rotateDegrees : 0;
			this.xyPosition = position != null ? position : new XY(10, 10);
			this.zOrder = zOrder != null ? zOrder : 0;
			break;
		case delete:
			break;
		case update:
			this.text = text;
			this.fontName = fontName;
			this.fontSize = fontSize;
			this.fontWeight = fontWeight;
			this.fontItalics = fontItalics;
			this.fontColor = fontColor;
			this.rotateDegrees = rotateDegrees;
			this.xyPosition = position;
			this.zOrder = zOrder;
			break;
		default:
			break;

		}
	}

	/**
	 * Updates/changes the text display as specified by parameter values, with value
	 * {@code null} indicating no change
	 * 
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
	public void update(Action action, String text, String fontName, Integer fontSize, FontWeightEnum fontWeight, ColorEnum fontColor, Boolean fontItalics, Integer rotateDegrees, XY position, Integer zOrder)
	{
		this.action = action != null ? action : Action.none;
		this.text = text;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.fontWeight = fontWeight;
		this.fontColor = fontColor;
		this.fontItalics = fontItalics;
		this.rotateDegrees = rotateDegrees;
		this.xyPosition = position;
		this.zOrder = zOrder;
	}

	@Override
	public String toString()
	{
		return String.format("AAText [uid=%s, action=%s, text=%s, fontName=%s, fontSize=%s, fontWeight=%s, fontItalics=%s, fontColor=%s, rotateDegrees=%s, position=%s, zOrder=%s]", uid, action, text, fontName, fontSize, fontWeight,
			fontItalics, fontColor, rotateDegrees, xyPosition, zOrder);
	}
}
