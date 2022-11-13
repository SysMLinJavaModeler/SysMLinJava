package sysmlinjava.comments;

import java.net.URI;

/**
 * SysMLinJava's representation of the SysML hyperlink comment. It typically
 * provides a hyperlink to information that is associated with the SysML element
 * of which it is a member.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLHyperlink extends SysMLComment
{
	/**
	 * Format string for the hyperlink text in the comment string
	 */
	public static final String textFormat = "title=%s URI=%s";
	/**
	 * Format string for the hyperlink text in HTML strings
	 */
	private static final String htmlFormat = "title=%s <a href=\"%s\">%s</a>";

	/**
	 * Constructor - initial values
	 * 
	 * @param title title of the hyperlink
	 * @param uri   URI for the hyperlink
	 */
	public SysMLHyperlink(String title, String uri)
	{
		super(String.format(textFormat, title, uri));
	}

	/**
	 * Returns value of {@code SysMLHyperlink} for specified title and URI
	 * 
	 * @param title title of the hyperlink
	 * @param uri   URI for the hyperlink
	 * @return instance of {@code SysMLHyperlink}
	 */
	public static SysMLHyperlink valueOf(String title, URI uri)
	{
		return new SysMLHyperlink(title, uri.toString());
	}

	/**
	 * Returns value of {@code SysMLHyperlink} for specified title and URI
	 * 
	 * @param formattedText title and URI strings in accordance with
	 *                      {@code textFormat} variable above
	 * @return instance of {@code SysMLHyperlink}
	 */
	public static SysMLHyperlink valueOf(String formattedText)
	{
		String title = "unknown";
		String uri = "file://unknown";
		int titleIndex = formattedText.indexOf("title=");
		int uriIndex = formattedText.indexOf(" URI=");
		if (titleIndex >= 0)
		{
			String textTitle = formattedText.substring(titleIndex + 6, uriIndex);
			if (!textTitle.isBlank())
				title = textTitle;
		}
		if (uriIndex >= 6)
		{
			String textURI = formattedText.substring(uriIndex + 5, formattedText.length());
			if (!textURI.isBlank())
				uri = textURI;
		}
		return new SysMLHyperlink(title, uri);
	}

	/**
	 * Returns this title as string
	 * 
	 * @return this title as string
	 */
	public String title()
	{
		int beginIndex = text.indexOf("title=") + 6;
		int endIndex = text.indexOf(" URI=");
		return text.substring(beginIndex, endIndex);
	}

	/**
	 * Returns this URI as string
	 * 
	 * @return this URI as string
	 */
	public String uri()
	{
		int beginIndex = text.indexOf("URI=") + 4;
		int endIndex = text.length();
		return text.substring(beginIndex, endIndex);
	}

	/**
	 * Returns this title and URI in accordance with {@code htmlFormat} variable
	 * above
	 * 
	 * @param titleAndURI title and URI strings in accordance with
	 *                    {@code textFormat} variable above
	 * @return this title and URI in accordance with {@code htmlFormat} variable
	 *         above
	 */
	public static String titleAndLinkHTML(String titleAndURI)
	{
		SysMLHyperlink sysMLHyperlink = SysMLHyperlink.valueOf(titleAndURI);
		String title = sysMLHyperlink.title();
		String uri = sysMLHyperlink.uri();
		return String.format(htmlFormat, title, uri, uri);
	}
}
