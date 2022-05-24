package h2ostates;

import java.util.Optional;
import sysmlinjava.tests.SysMLTestCase;
import sysmlinjava.tests.SysMLVerdictKind;

/**
 * {@code H2OStatesTest} is a SysMLinJava example model of a SysML test and test
 * case. It demonstrates how the SysML test is implemented in SysMLinJava. The
 * test includes a single test case which executes the {@code H2O} block and its
 * {@code H2OStateMachine}.
 * <p>
 * The tested {@code H2OStateMachine} is a SysMLinJava implementation of the
 * SysML state machine for H2O found in the book "A Practical Guide to SysML -
 * The Systems Modeling Language 3rd edition" by Sanford Friedenthal, et al;
 * Object Management Group; Morgan Kaufman publisher; copyright 2015.
 * 
 * @author ModelerOne
 *
 */
public class H2OStatesTestCase extends SysMLTestCase
{
	public H2OStatesTestCase(H2OStatesTest test)
	{
		super(test);
	}

	@Override
	protected void createInitializeActivity()
	{
		initialize = Optional.of(() -> ((H2OStatesTest)test).h2o.start());
	}
	
	@Override
	protected void createExecuteActivity()
	{
		execute = () ->
		{
			verdict = SysMLVerdictKind.fail;
			for (int i = -50; i <= 100; i += 10)
				changeTemp(i);
			for (int i = 200; i <= 2200; i += 200)
				changeTemp(i);
			H2O h2O = ((H2OStatesTest)test).h2o;
			if (h2O.temp.greaterThanOrEqualTo(h2O.decomposedTemp) &&
				h2O.stateMachine.get().currentState.isPresent() &&
				h2O.stateMachine.get().currentState.get() == h2O.stateMachine.get().finalState)
				verdict = SysMLVerdictKind.pass;
		};
	}

	@Override
	protected void createFinalizeActivity()
	{
		finalize = Optional.of(() -> ((H2OStatesTest)test).h2o.stop());
	}

	private void changeTemp(int i)
	{
		logger.info(String.format("temperature=%dC", i));
		H2O h2O = ((H2OStatesTest)test).h2o;
		h2O.temp.value = i;
		h2O.acceptEvent(new TempChangeEvent());
		h2O.delay(1.0);
	}

}
