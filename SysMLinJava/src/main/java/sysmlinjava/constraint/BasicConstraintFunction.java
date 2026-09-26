package sysmlinjava.constraint;

/**
 * Simple functional interface for declaration in same scope as all needed
 * parameters. i.e. passed-in arguments are not needed. An example usage is as
 * follows:
 * 
 * <pre>{@code
	public class ConstrainedFlowSystem extends SysMLPart
	{
			:
		&#64;ConstraintFunction
		SysMLConstraintFunction constraintFunction;
		&#64;Constraint
		SysMLConstraint constraint;
			:
		&#64;Override
		protected void createFunctions()
		{
			constraintfunction = (BasicConstraintFunction)() ->
			{
				return resistance.value == (8 * viscosity.value * length.value) / (PI * pow(radius.value, 2.0)) &&
				       pressureDiff.value == opening2Pressure.value - opening1Pressure.value &&
				       fluidFlow.value == pressureDiff.value / resistance.value;
			};
		}
			:
		&#64;Override
		protected void createConstraints()
		{
			constraint = new SysMLConstraint(constraintFunction, "metrics OK", "MetricsOK", 0L);
		}
			:
	}}
 * </pre>
 * 
 * The above {@code constraint} would be applied elsewhere in the class as
 * follows:
 * 
 * <pre>{@code
 * {
 * 	boolean compliant = ((BasicConstraintFunction) constraint.function).test();
 * }
 * }
 * </pre>
 * 
 * In the case of verifying a requirement, the constraint would be applied in a
 * verification case. First, the requirement/constraint would be defined in a
 * collection of requirements as follows:
 * 
 * <pre>{@code
	public class SystemRequirements extends SysMLRequirementsCollection
	{
			:
		&#64;ConstraintFunction
		SysMLConstraintFunction configConstraintFunction;
		&#64;Constraint
		SysMLConstraint configConstraint;
			:
		&#64;Documentation
		SysMLDocumentaion configText;
			:
		&#64;Requirement
		SysMLRequirement rqmnt4_2_3;
			:
		&#64;Override
		protected void createFunctions()
		{
			configConstraintfunction = (BasicConstraintFunction)() ->
			{
				return resistance.value == (8 * viscosity.value * length.value) / (PI * pow(radius.value, 2.0)) &&
				       pressureDiff.value == opening2Pressure.value - opening1Pressure.value &&
				       fluidFlow.value == pressureDiff.value / resistance.value;
			};
		}
			:
		&#64;Override
		protected void createConstraints()
		{
			configConstraint = new SysMLConstraint(configConstraintFunction, "config OK", "Config OK", 0L);
		}
			:
		&#64;Override
		protected void createDocumenations()
		{
			configText = new SysMLDocumentation("Configuration is valid if resistance, pressure, flow all within limits ...");
		}
			:
		protected void createRequirements()
		{
			rqmnt4_2_3 = new SysMLRequirement("4.2.3", "Component Configuration",  configConstraint, configText, ...);
		}
			:
	}}
 * </pre>
 * 
 * In the case of verifying a requirement, the {@code constraint} would be
 * applied in the verification case as follows:
 * 
 * <pre>{@code
	public class ComponentVerificationCase extends SysMLVerificationCase
	{
			:
		protected void perform()
		{
				:
			boolean verified4_2_3 = SystemRequirements.rqmnt4_2_3.function;
				:
		}
			:
	}}
 * </pre>
 * 
 * @author ModelerOne
 */
@FunctionalInterface
public interface BasicConstraintFunction extends SysMLConstraintFunction
{
	/**
	 * Returns whether the constraint is satisfied
	 * <p>
	 * Note this method is provided to enable model execution. It is not part of the
	 * SysML standard, To the extent the SysML standard does not provide elements
	 * for executable modeling per se, SysMLinJava includes various model elements
	 * to enable model execution.
	 * @return true if constraint satisfied, false otherwise
	 */
	boolean satisfied();
}