package sysmlinjava.analysis.animatedareadisplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import sysmlinjava.analysis.common.ColorEnum;

/**
 * Definition of the objects to be used to initialized the area display, i.e.
 * title, backgound, and all text, image, and line objects to initialize the
 * area display.
 * 
 * @author ModelerOne
 *
 */
public class AnimatedAreaDisplayDefinition implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -834069785828317519L;

	/**
	 * Title of the area display to be displayed on the top bar of the display pane
	 */
	public String title;
	/**
	 * URL of the file that contains the image to be used as the background of the
	 * area display. If the URL is for a file on the local file system, be sure to
	 * use an absolute path to the file. The file will be loaded from the "working"
	 * directory of the TaskMaster, so use of a relative path to the image file is
	 * likely to fail to locate it properly.
	 */
	public String backgroundImageFileURL;
	/**
	 * Color to fill the background if no background image file is specified
	 */
	public ColorEnum backgroundFill;
	/**
	 * Width (pixels) of the background (fill or image)
	 */
	public Integer backgroundWidth;
	/**
	 * Height (pixels) of the background (fill or image)
	 */
	public Integer backgroundHeight;
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
	 * Constructor for default initial values, i.e. blank (white-smoke color)
	 * background, 500x500 pixels, no objects displayed
	 */
	public AnimatedAreaDisplayDefinition()
	{
		super();
		title = "<Animated Area Display>";
		backgroundImageFileURL = null;
		backgroundFill = ColorEnum.WHITESMOKE;
		backgroundWidth = 500;
		backgroundHeight = 500;
		texts = new ArrayList<>();
		images = new ArrayList<>();
		lines = new ArrayList<>();
	}

	/**
	 * Constructor for specifying all attributes for an image-background based
	 * display
	 * 
	 * @param title            Title of the area display to be displayed on the top
	 *                         bar of the display pane
	 * @param backgroundImage  URL of the file that contains the image to be used as
	 *                         the background of the area display. If the URL is for
	 *                         a file on the local file system, be sure to use an
	 *                         absolute path to the file. The file will be loaded
	 *                         from the "working" directory of the TaskMaster, so
	 *                         use of a relative path to the image file is likely to
	 *                         fail to locate it properly.
	 * @param backgroundWidth  width (pixels) of the background image
	 * @param backgroundHeight height (pixels) of the background image
	 * @param areaTexts        list of the text objects to be in the display
	 * @param areaImages       list of the image objects to be in the display
	 * @param areaLines        list of the line objects to be in the display
	 */
	public AnimatedAreaDisplayDefinition(String title, String backgroundImage, Optional<Integer> backgroundWidth, Optional<Integer> backgroundHeight, ArrayList<AAText> areaTexts, ArrayList<AAImage> areaImages, ArrayList<AALine> areaLines)
	{
		super();
		this.title = title != null ? title : "<title>";
		this.backgroundImageFileURL = backgroundImage;
		this.backgroundFill = null;
		this.backgroundWidth = (backgroundWidth != null && backgroundWidth.isPresent()) ? backgroundWidth.get() : null;
		this.backgroundHeight = (backgroundHeight != null && backgroundHeight.isPresent()) ? backgroundHeight.get() : null;
		this.texts = areaTexts != null ? areaTexts : new ArrayList<>();
		this.images = areaImages != null ? areaImages : new ArrayList<>();
		this.lines = areaLines != null ? areaLines : new ArrayList<>();
	}

	/**
	 * Constructor for specifying all attributes for a filled-background display
	 * 
	 * @param title            Title of the area display to be displayed on the top
	 *                         bar of the display pane
	 * @param backgroundFill   color of the fill to be used as the background of the
	 *                         area display
	 * @param backgroundWidth  width (pixels) of the background fill
	 * @param backgroundHeight height (pixels) of the background fill
	 * @param areaTexts        list of the text objects to be in the display
	 * @param areaImages       list of the image objects to be in the display
	 * @param areaLines        list of the line objects to be in the display
	 */
	public AnimatedAreaDisplayDefinition(String title, ColorEnum backgroundFill, Optional<Integer> backgroundWidth, Optional<Integer> backgroundHeight, ArrayList<AAText> areaTexts, ArrayList<AAImage> areaImages, ArrayList<AALine> areaLines)
	{
		super();
		this.title = title != null ? title : "<title>";
		this.backgroundImageFileURL = null;
		this.backgroundFill = backgroundFill != null ? backgroundFill : ColorEnum.WHITESMOKE;
		this.backgroundWidth = (backgroundWidth != null && backgroundWidth.isPresent()) ? backgroundWidth.get() : 500;
		this.backgroundHeight = (backgroundHeight != null && backgroundHeight.isPresent()) ? backgroundHeight.get() : 500;
		this.texts = areaTexts != null ? areaTexts : new ArrayList<>();
		this.images = areaImages != null ? areaImages : new ArrayList<>();
		this.lines = areaLines != null ? areaLines : new ArrayList<>();
	}

	@Override
	public String toString()
	{
		return String.format("AnimatedAreaDisplayDefinition [title=%s, backgroundImageFileURL=%s, backgroundFill=%s, backgroundWidth=%s, backgroundHeight=%s, texts=%s, images=%s, lines=%s]", title, backgroundImageFileURL, backgroundFill,
			backgroundWidth, backgroundHeight, texts, images, lines);
	}
}
