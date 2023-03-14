package sysmlinjava.comments;

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
	 * String representation of the URI of the link
	 */
	public String uri;
	
	/**
	 * Constructor - initial values
	 * 
	 * @param title title of the hyperlink
	 * @param uri   URI for the hyperlink
	 */
	public SysMLHyperlink(String title, String uri)
	{
		super(title);
		this.uri = uri;
	}

	/**
	 * Returns this title as string
	 * 
	 * @return this title as string
	 */
	public String title()
	{
		return text;
	}

	/**
	 * Returns this URI as string
	 * 
	 * @return this URI as string
	 */
	public String uri()
	{
		return uri;
	}
}
