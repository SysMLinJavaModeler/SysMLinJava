package sysmlinjava.comments;

import sysmlinjava.common.SysMLClass;

/**
 * SysMLinJava's representation of the SysML comment.
 * <h2>Basic comment</h2>The {@code SysMLComment} is a simple (extendable) class
 * for a textual comment. The {@code SysMLComment} is typically declared in a
 * {@code @Comment} annotated field in the SysML element, such as a block,
 * interfaceBlock, valueType, etc. to which the comment applies. It is
 * instantiated/initialized with the comment text in the
 * {@code createComments()} method of the element.
 * <h3>Comment "libraries"</h3> Comments can also be declared as static
 * variables in block elements that serve as a container of comments. These
 * comments can then serve as a set of reusable comments that can be referenced
 * by multiple elements that need the same comment.
 * <h3>Comment extensions - problems, hyperlinks, etc.</h3> Consistent with
 * SysML, the {@code SysMLComment} is extended for specialized types of comments
 * by other SysMLinJava classes such as the {@code SysMLHyperlink},
 * {@code SysMLProblem}, and {@code SysMLRationale}.
 * 
 * @author ModelerOne
 *
 */
public class SysMLComment extends SysMLClass
{
	/**
	 * Text of the comment. Extensions to the {@code SysMLComment} may use formatted
	 * strings here to represent their structured comment.
	 */
	public String text;

	/**
	 * Constructor for the comment text
	 * 
	 * @param text Text of the comment
	 */
	public SysMLComment(String text)
	{
		super();
		this.text = text;
	}

	@Override
	public String toString()
	{
		return String.format("SysMLComment [text=%s]", text);
	}
}
