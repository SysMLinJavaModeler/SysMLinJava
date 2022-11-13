package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLInterface;

/**
 * Functional interface specifying the activity performed upon exit from the
 * state. That is, the activity to be performed by the SysMLState's
 * {@code onExitActivity()} operation.
 * <p>
 * The {@code SysMLOnExitActivity} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code OnExitActivity} annotation. It should then be implemented as an
 * instance of a Lambda function in the override of the
 * {@code SysMLStateMachine}'s {@code createStateOnExitActivities()} operation
 * to provide the function with access to the state machine's properties. An example
 * follows.
 * 
 * <pre>
		:
	&#64;@OnExitActivity
	public SysMLOnExitActivity f2t2ScanningStateOnExitActivity;
	&#64;@OnExitActivity
	public SysMLOnExitActivity eaScanningStateOnExitActivity;
		:
	&#64;Override
	protected void createStateOnExitActivities()
	{
		super.createStateOnExitActivities();
		f2t2ScanningStateOnExitActivity = (contextBlock) ->
		{
			RadarSystem radarSystem = (RadarSystem)contextBlock.get();
			radarSystem.stopF2T2Scanning();
		};
		eaScanningStateOnExitActivity = (contextBlock) ->
		{
			RadarSystem radarSystem = (RadarSystem)contextBlock.get();
			radarSystem.stopEAScanning();
		};
	}
 * </pre>

 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLOnExitActivity extends SysMLInterface
{
	/**
	 * Specification of the activity to be performed upon exiting an associated
	 * state, i.e. the activity to be performed by the state's
	 * {@code onExitActivity} element. This function must be realized by an instance
	 * of a lambda expression.
	 * 
	 * @param contextBlock Optional block in whose context the state's associated
	 *                     state machine executes.
	 */
	void perform(Optional<? extends SysMLBlock> contextBlock);
}