package sysmlinjava.analysis.htmldisplay;

import java.io.Serializable;

/**
 * Container for HTML as a {@code String}
 * 
 * @author ModelerOne
 *
 */
public class HTMLString implements Serializable
{
	/** Serializable ID*/private static final long serialVersionUID = -7567118199087274202L;
	/**
	 * String containing the HTML code
	 */
	public String html;

	/**
	 * Constructor
	 * 
	 * @param htmlString string containing the HTML code
	 */
	public HTMLString(String htmlString)
	{
		super();
		this.html = htmlString;
	}

	/**
	 * Returns the HTML string formatted for output as a log message string
	 * 
	 * @return string for output in log message
	 */
	public String logString()
	{
		return String.format("[HTML]%n%s", html);
	}

	@Override
	public String toString()
	{
		return String.format("HTMLString [htmlString=%n%s]", html);
	}
}