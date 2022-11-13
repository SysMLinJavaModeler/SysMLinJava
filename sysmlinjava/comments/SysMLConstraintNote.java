package sysmlinjava.comments;

import sysmlinjava.common.SysMLConstraint;

/**
 * SysMLinJava's representation of the SysML constraint note. The
 * {@code SysMLConstrainNote} is typically declared as a {@code @ConstraintNote}
 * annotated field in the SysML element to which the note applies and it is
 * instantiated/initialized in the {@code createConstraintNotes()} method of the
 * element.
 * 
 * @author ModelerOne
 *
 */
public final class SysMLConstraintNote extends SysMLComment
{
	/**
	 * Constraint (functional interface instance) that defines the constraint
	 */
	SysMLConstraint constraint;

	/**
	 * Constructor for the specified constraint
	 * 
	 * @param constraint constraint code (java lambda expression for instance of
	 *                   {@code SysMLConstraint})
	 */
	public SysMLConstraintNote(SysMLConstraint constraint)
	{
		super(constraint.toString());
		this.constraint = constraint;
	}

	/**
	 * Instance for a constraint that is to be determined.
	 */
	public static final SysMLConstraintNote tbd = new SysMLConstraintNote(() ->	{ });
}
