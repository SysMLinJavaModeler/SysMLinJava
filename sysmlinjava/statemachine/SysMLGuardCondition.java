package sysmlinjava.statemachine;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLInterface;
import sysmlinjava.events.SysMLEvent;

/**
 * Functional interface specifying whether the guard allows the transition to
 * occur. This is the condition function to be performed by the transition's
 * {@code SysMLGuard} element.
 * <p>
 * The {@code SysMLGuardCondition} should be declared as a field in the extended
 * {@code SysMLStateMachine} class. The field should be annotated with the
 * {@code GuardCondition} annotation. It should then be implemented as an
 * instance of a Lambda function in the override of the
 * {@code SysMLStateMachine}'s {@code createGuardConditions()} operation to
 * provide the function with access to the state machine's properties. An
 * example follows.
 * 
 * <pre>
		:
	&#64;GuardCondition
	public SysMLGuardCondition gasToLiquidGuardCondition;
	&#64;GuardCondition
	public SysMLGuardCondition liquidToIceGuardCondition;
	&#64;GuardCondition
	public SysMLGuardCondition iceToLiquidGuardCondition;
	&#64;GuardCondition
	public SysMLGuardCondition liquidToGasGuardCondition;
	&#64;GuardCondition
	public SysMLGuardCondition gasToDecomposedGuardCondition;
		:
	&#64;Override
	protected void createGuardConditions()
	{
		gasToLiquidGuardCondition = (event, contextBlock) ->
		{
			TemperatureDegreesC temp = ((H2O)contextBlock.get()).temp;
			TemperatureDegreesC gasTemp = (((H2O)contextBlock.get()).gasTemp);
			return temp.lessThan(gasTemp);
		};
		liquidToIceGuardCondition = (event, contextBlock) ->
		{
			TemperatureDegreesC temp = ((H2O)contextBlock.get()).temp;
			TemperatureDegreesC iceTemp = (((H2O)contextBlock.get()).iceTemp);
			return temp.lessThanOrEqualTo(iceTemp);
		};
		iceToLiquidGuardCondition = (event, contextBlock) ->
		{
			TemperatureDegreesC temp = ((H2O)contextBlock.get()).temp;
			TemperatureDegreesC iceTemp = ((H2O)contextBlock.get()).iceTemp;
			LatentHeatKilojoulesPerKilogram latentHeat = ((H2O)contextBlock.get()).latentHeat;
			LatentHeatKilojoulesPerKilogram minLatentHeat = ((H2O)contextBlock.get()).minLatentHeatCondensation;
			return temp.greaterThan(iceTemp) &#38;&#38; latentHeat.greaterThanOrEqualTo(minLatentHeat);
		};
		liquidToGasGuardCondition = (event, contextBlock) ->
		{
			TemperatureDegreesC temp = ((H2O)contextBlock.get()).temp;
			TemperatureDegreesC gasTemp = ((H2O)contextBlock.get()).gasTemp;
			LatentHeatKilojoulesPerKilogram latentHeat = ((H2O)contextBlock.get()).latentHeat;
			LatentHeatKilojoulesPerKilogram minLatentHeat = ((H2O)contextBlock.get()).minLatentHeatEvaporation;
			return temp.greaterThanOrEqualTo(gasTemp) &#38;&#38; latentHeat.greaterThanOrEqualTo(minLatentHeat);
		};
		gasToDecomposedGuardCondition = (event, contextBlock) ->
		{
			TemperatureDegreesC temp = ((H2O)contextBlock.get()).temp;
			TemperatureDegreesC maxGasTemp = ((H2O)contextBlock.get()).decomposedTemp;
			return temp.greaterThanOrEqualTo(maxGasTemp);
		};
	}
		:
 * </pre>
 * 
 * 
 * @author ModelerOne
 *
 */
@FunctionalInterface
public interface SysMLGuardCondition extends SysMLInterface
{
	/**
	 * Returns whether or not the guard's condition is satisfied for the specified
	 * event and context
	 * 
	 * @param currentEvent the event to be used in the condition
	 * @param contextBlock the context to be used in the condition
	 * @return whether or not the condition is satisfied
	 */
	boolean isSatisfied(Optional<? extends SysMLEvent> currentEvent, Optional<? extends SysMLBlock> contextBlock);
}