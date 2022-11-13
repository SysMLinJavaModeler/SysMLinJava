package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLInterface;

/**
 * Functional interface specifying the activity performed upon entry into the
 * state. That is, the activity to perform by the SysMLState's
 * {@code onEnterActivity()} operation.<br>
 * <p>
 * The {@code SysMLOnEnterActivity} should be declared as a field in the
 * extended {@code SysMLStateMachine} class. The field should be annotated with
 * the {@code OnEnterActivity} annotation. It should then be implemented as an
 * instance of a Lambda function in the override of the
 * {@code SysMLStateMachine}'s {@code createStateOnEnterActivities()} operation
 * to provide the function with access to the state machine's properties. An
 * example follows.
 * 
 * <pre>
		:
	&#64;OnEnterActivity
	public SysMLOnEnterActivity onEnterActivity;
		:
	&#64;Override
	protected void createStateOnEnterActivities()
	{
		super.createStateOnEnterActivities();
		onEnterActivity = (contextBlock) ->
		{
			((Vehicle)contextBlock.get()).transmitWeights();
		};
	}
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLOnEnterActivity extends SysMLInterface
{
	/**
	 * Specification of the activity to be performed upon entering an associated
	 * state, i.e. the activity to be performed by the state's
	 * {@code onEnterActivity} element. This function must be realized by an
	 * instance of a lambda expression.
	 * 
	 * @param contextBlock Optional block in whose context the state's associated
	 *                     state machine executes.
	 */
	void perform(Optional<? extends SysMLBlock> contextBlock);
}