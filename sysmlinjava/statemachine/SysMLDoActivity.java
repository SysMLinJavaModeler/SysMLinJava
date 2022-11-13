package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLInterface;

/**
 * Functional interface specifying the activity performed while in the state.
 * That is, the activity to perform by the {@code SysMLState}'s
 * {@code doActivity()} operation.<br>
 * <p>
 * The {@code SysMLDoActivity} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code DoActivity} annotation. It should then be implemented as an instance
 * of a functional interface - a lambda function - in the override of the
 * {@code SysMLStateMachine}'s {@code createStateDoActivities()} operation to
 * provide the function with access to the state machine's properties. An
 * example follows.
 * 
 * <pre>
 * {@code 
     public SysMLDoActivity developPlan;
     :
     public void createStateDoActivities()
     {
          :
          developPlan = (contextBlock) -> 
          {
               gatherIntelligence();
               developOptions();
               selectOption();
               optionIntoPlan();
               reviewPlan()
          };
          :
     }
    }
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLDoActivity extends SysMLInterface
{
	/**
	 * Specification of the activity to be performed while state machine is in the
	 * associated state, i.e. the activity to be performed by the state's
	 * {@code doActivity} element. This function must be realized by an instance of
	 * a lambda expression.
	 * 
	 * @param contextBlock Optional block in whose context the state's associated
	 *                     state machine executes.
	 */
	void perform(Optional<? extends SysMLBlock> contextBlock);
}