package sysmlinjava.comments;

/**
 * SysMLinJava's representation of the SysML problem. {@code SysMLProblem} is a
 * specialized type of {@code SysMLComment}. It consists of text that specifies
 * the problem associated with the SysML element it is a member of.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLProblem extends SysMLComment
{
	/**
	 * Constructor - initial value
	 * 
	 * @param text text specification of the problem
	 */
	public SysMLProblem(String text)
	{
		super(text);
	}

	@Override
	public String toString()
	{
		return String.format("SysMLProblem [text=%s]", text);
	}
}
