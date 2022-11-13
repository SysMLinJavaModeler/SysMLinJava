package sysmlinjava.comments;

/**
 * SysMLinJava's representation of the SysML rationale comment.
 * {@code SysMLRationale} is a specialized type of {@code SysMLComment}. It
 * consists of text that specifies the rationale associated with the SysML
 * element it is connected to.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLRationale extends SysMLComment
{
	/**
	 * Constructor - initial value
	 * 
	 * @param text text specification of the rationale
	 */
	public SysMLRationale(String text)
	{
		super(text);
	}

	@Override
	public String toString()
	{
		return String.format("SysMLRationale [text=%s]", text);
	}
}
