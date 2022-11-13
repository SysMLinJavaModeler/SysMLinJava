package sysmlinjava.constraintblocks;

import java.util.Optional;

/**
 * SysMLinJava representation of the SysML objective function constraint block.
 * <h2>Specialized constraint block</h2> A specialized type of constraint block,
 * the {@code SysMLObjectiveFunctionBlock} simply extends the
 * {@code SysMLConstraintBlock} with parameters that represent measures of
 * effectiveness, performance, and/or suitability, and with one or more
 * constraints that declare the objective function to be satisfied by the
 * parameters.
 * <h3>Constraint parameters as MOP, MOE, MOS</h3> As an abstract class, the
 * {@code SysMLObjectiveFunctionBlock} is specialized for a particular type of
 * analysis that involves the calculation of a constraint defining a SysML
 * objective function using constraint parameters for the SysML {@code moe}s,
 * {@code mop}s, and/or {@code mos}es. Constraint parameters should be annotated
 * in accordance with their purpose in this regard, i.e. as a<br>
 * <ul>
 * <li>{@code @MeasureOfEffectiveness},</li>
 * <li>{@code @MeasureOfPerformance}, or</li>
 * <li>{@code @MeasureOfSuitability}.</li>
 * </ul>
 * The {@code mop}, {@code moe}, and {@code mos} constraint parameters for the
 * {@code SysMLObjectiveFunctionBlock} should be declared locally in the
 * extended class. As with any extension of the {@code SysMLConstraintBlock},
 * the constraint (objective function) must first retrieve the current values of
 * the bound values for the {@code mop}, {@code moe}, and {@code mos} constraint
 * parameters from the {@code constraintParams} map.
 * <h2></h2> In the rare case where more than one constraint is needed to define
 * the objective function, the additional constraints can be declared in the
 * objective function block. These specialized constraints should be annotated
 * with {@code @ObjectiveFunction} to identify them as such.
 * <h2>Constraint as objective function</h2>The inherited constraint will be
 * assumed to be the objective function for the
 * {@code SysMLObjectiveFunctionBlock} and the constraint should be defined in
 * the {@code createConstraints()} method as with any other
 * {@code SysMLConstraintBlock} implementation.
 * <h2>Objective function block creation same as for typical constraint
 * block</h2> Other aspects of the specialized
 * {@code SysMLObjectiveFunctionBlock} are essentially the same as for any
 * {@code SysMLConstraintBlock}, i.e. creating the constraint parameters,
 * constraint parameter ports, and the constraints for the objective function.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.annotations.parametrics.ObjectiveFunction
 * @see sysmlinjava.annotations.parametrics.MeasureOfEffectiveness
 * @see sysmlinjava.annotations.parametrics.MeasureOfPerformance
 * @see sysmlinjava.annotations.parametrics.MeasureOfSuitability
 */
public abstract class SysMLObjectiveFunctionBlock extends SysMLConstraintBlock
{
	/**
	 * Constructor - initial values
	 * 
	 * @param parent optional constraint block of which this objective function
	 *               block is a part
	 * @param name   unique name
	 */
	public SysMLObjectiveFunctionBlock(Optional<? extends SysMLConstraintBlock> parent, String name)
	{
		super(parent, name);
	}
}
