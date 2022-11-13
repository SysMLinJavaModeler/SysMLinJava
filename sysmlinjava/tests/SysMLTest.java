package sysmlinjava.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;

/**
 * SysMLinJava representation of the SysML test which is presumably a set of
 * SysML test cases, along with an optional hierarchy of tests. The
 * {@code SysMLTest} represents the tests/test cases that are to be performed on
 * the modeled system-under-test. As part of the executable model, however, the
 * test and test cases can invoke activities on the SysMLinJava model, in which
 * case these executable test and test cases serve as models of the test and
 * test cases on the actual system.
 * <p>
 * {@code SysMLTest} is an extension of the {@code SysMLBlock}. As such, any and
 * all of the features of the block can utilized for the test and test cases as
 * desired, e.g. state-machine for test behavior, full and proxy ports for test
 * interfaces, values and constraints for test parameters and logic, etc.
 * <p>
 * The {@code SysMLTest} is an abstract class in which extension classes specify
 * test cases and other tests to be executed against a "systemUnderTest". Each
 * extended {@code SysMLTest} consists of zero or more {@code SysMLTestCase}s
 * and zero or more {@code SysMLTest}s, i.e. the {@code SysMLTest} is a
 * collection of test cases and/or a hierarchy of tests. The {@code SysMLTest}
 * is an extension of the {@code SysMLBlock} adding test cases and other tests
 * as needed. Test cases and tests to be executed are declared via the
 * {@code SysMLTest} operations {@code createTestCases()} and
 * {@code createTests()}, respecitively. The model to be tested (SysML's
 * {@code systemUnderTest}) is specified by the {@code createSystemUnderTest()}
 * operation. {@code SysMLTest} mandates implementation of the
 * {@code createTestCases()} and the {@code createSystemUnderTest()}, as a
 * minimum.
 * <p>
 * The {@code SysMLTestCase}s and {@code SysMLTest}s declared in the
 * {@code SysMLTest} are automatically executed as part of the test execution.
 * Test execution is performed by a {@code main()} method declared in the
 * top-level {@code SysMLTest} class which simply invokes the
 * {@code initialize()}, {@code execute()}, and {@code finalize()} operations of
 * the {@code SysMLTest}. An example of this {@code main()} implementation is as
 * follows:
 * 
 * <pre>
 * public static void main(String[] args)
 * {
 * 	DeltaSystemTest test = new DeltaSystemTest();
 * 
 * 	test.initialize();
 * 	test.execute();
 * 	test.finalize();
 * 
 * 	System.exit(0);
 * }
 * </pre>
 * 
 * @author ModelerOne
 *
 * @see SysMLTestCase
 */
public abstract class SysMLTest extends SysMLBlock
{
	/**
	 * Test verdict, i.e. the combined result of the execution of all contained
	 * tests and test cases, as determined by specified algorithm that is performed
	 * after performance of all test cases and tests.
	 * <p>
	 * Determination of the test verdict defaults to essentially just the worst case
	 * verdict of each of the individual contained tests and test cases, as
	 * calculated by the {@code calculateTestVerdict()} operation. This operation
	 * can be overriden by extensions to the SysMLTest to implement custom
	 * algorithms for determining test verdicts.
	 */
	public SysMLVerdictKind verdict;
	/**
	 * The system under test, i.e. the {@code SysMLBlock} extension whose structure
	 * and behaviors will be tested by the tests and test cases.
	 */
	public SysMLBlock systemUnderTest;
	/**
	 * The test cases that constitute the test.
	 */
	public List<SysMLTestCase> testCases;
	/**
	 * The (nested) tests, if any, that are also to be executed as part of the test
	 */
	public List<SysMLTest> tests;

	/**
	 * Constructor of the test.
	 * 
	 * @param parentTest Optional parent test for the case where this test is nested
	 *                   within another test.
	 * @param name       Name of the test
	 * @param id         Unique numerical ID of the test
	 */
	public SysMLTest(Optional<? extends SysMLTest> parentTest, String name, Long id)
	{
		super(name, id);
		this.contextBlock = parentTest;
		this.verdict = null;
		testCases = new ArrayList<>();
		tests = new ArrayList<>();
		createTestCases();
		createTests();

		enableInteractionMessageTransmissions();
	}

	/**
	 * Overridable operation to enable the transmission of interaction messages to
	 * an interactions sequence display (sequence diagram). The sequence diagram is
	 * oftentimes quite useful for test analysis. Each {@code SysMLFullPort}
	 * extended port whose interaction messages (signals) are to be displayed in the
	 * sequence diagram must have its
	 * {@code enableInteractionMessageTransmissions()} operation called before the
	 * interaction messages are to be displayed. Calls to the ports to enable the
	 * interaction messages should be made here. An example implementation is as
	 * follows:
	 * 
	 * <pre>
	 * InteractionMessageTransmitter transmitter = new InteractionMessageTransmitter(InteractionMessageSequenceDisplay.udpPort);
	 * InteractionMessageTransmitters interactionMessageTransmitters = new InteractionMessageTransmitters(transmitter);
	 * 
	 * systemUnderTest.alphaSubsystem.modemRadio.packetTransmitter.messageUtility = Optional.of(interactionMessageTransmitters);
	 * systemUnderTest.bravoSubsystem.modemRadio.packetTransmitter.messageUtility = Optional.of(interactionMessageTransmitters);
	 * systemUnderTest.charlieSubsystem.modemRadio.packetTransmitter.messageUtility = Optional.of(interactionMessageTransmitters);
	 * </pre>
	 * 
	 * @see sysmlinjava.analysis.interactionsequence.InteractionMessageSequenceDisplay
	 * @see sysmlinjava.analysis.interactionsequence.InteractionMessageTransmitter
	 * @see sysmlinjava.analysis.interactionmessagetransmitter.InteractionMessageTransmitters
	 */
	public void enableInteractionMessageTransmissions()
	{
	}

	/**
	 * Enables the transmission from the test to a state transitions table display
	 * of the states, events, transitions, guards, effects, and next states for each
	 * state transition, and/or to a state transitions timing diagram of the state
	 * transition times. The state transitions table and timing diagram are
	 * oftentimes quite useful for model debugging and test analysis. Each state
	 * machine whose state transitions table and/or timing diagram is to be
	 * displayed has its {@code enableStateTransitionStringsTransmission()}
	 * operation called here. For example:
	 * 
	 * <pre>
	 * systemUnderTest.stateMachine.get().transitionsUtility = Optional
	 * 	.of(new StateTransitionsTransmitters(Optional.of(new StateTransitionTablesTransmitter(StateTransitionsDisplay.udpPort)), Optional.of(new TimingDiagramsTransmitter(TimingDiagramsDisplay.udpPort))));
	 * systemUnderTest.partA.stateMachine.get().transitionsUtility = Optional
	 * 	.of(new StateTransitionsTransmitters(Optional.of(new StateTransitionTablesTransmitter(StateTransitionsDisplay.udpPort)), Optional.of(new TimingDiagramsTransmitter(TimingDiagramsDisplay.udpPort))));
	 * </pre>
	 * 
	 * Note that the {@code transitionUtility} could be set locally in the
	 * extension(s) of the {@code SysMLStateMachine}(s) in the model instead of in
	 * this {@code SysMLTest}. Also note that the {@code TimingDiagramsDisplay}
	 * requires the transmission via the {@code TimingDiagramsTransmitter} of the
	 * {@code TimingDiagramDefinition} to the display prior to the transmission by
	 * the state machine of the state transition timing data. This initial
	 * transmission could/should be performed as part of test case initialization,
	 * for example as follows:
	 * 
	 * <pre>
	 * StatesAxis statesAxis = new StatesAxis(List.of("FirstState", "SecondState", "FinalState"));
	 * TimeAxis timeAxis = new TimeAxis(120, 10, 10);
	 * TimingDiagramDefinition definition = new TimingDiagramDefinition("MyTD", statesAxis, timeAxis);
	 * StateTransitionsTransmitters transmitters = (StateTransitionsTransmitters)systemUnderTest.stateMachine.get().transitionsUtility.get();
	 * transmitters.transmit(definition);
	 * </pre>
	 * 
	 * @see sysmlinjava.analysis.statetransitions.StateTransitionsDisplay
	 * @see sysmlinjava.analysis.timingdiagrams.TimingDiagramsDisplay
	 * @see sysmlinjava.analysis.statetransitions.StateTransitionTablesTransmitter
	 * @see sysmlinjava.analysis.timingdiagrams.TimingDiagramsTransmitter
	 * @see sysmlinjava.analysis.statetransitionstransmitters.StateTransitionsTransmitters
	 */
	public void createStateTransitionsUtilities()
	{
	}

	/**
	 * Overridable operation to initialize the test. This operation should be
	 * invoked just before execution of the test cases and tests.
	 */
	public void initialize()
	{
	}

	/**
	 * Executes the test by executing each of the test cases and each of the nested
	 * tests that constitute the test.
	 * <p>
	 * The execution performs each of the test cases followed by performance of each
	 * of the nested tests. The execution then calculates and returns the test
	 * verdict.
	 * 
	 * @return The test verdict
	 */
	public SysMLVerdictKind execute()
	{
		logger.info(String.format("Test: %s execution started", identityString()));
		for (SysMLTestCase testCase : testCases)
		{
			if (testCase.initialize.isPresent())
			{
				logger.info(String.format("TestCase: %s initializing...", identityString()));
				testCase.initialize.get().perform();
			}

			logger.info(String.format("TestCase: %s executing...", identityString()));
			testCase.execute.perform();
			logger.info(String.format("TestCase: %s -> %s", identityString(), testCase.verdict.toString()));

			if (testCase.finalize.isPresent())
			{
				logger.info(String.format("TestCase: %s finalizing...", identityString()));
				testCase.finalize.get().perform();
			}
		}

		for (SysMLTest test : tests)
		{
			test.initialize();
			SysMLVerdictKind verdict = test.execute();
			test.verdict = verdict;
			test.finalize();
		}
		logger.info(String.format("Test: %s execution completed", identityString()));
		return calculateTestVerdict();
	}

	/**
	 * Overridable operation to finalize the test. The overridden operation should
	 * be invoked just after execution of the test cases and tests.
	 */
	public void finalize()
	{
	}

	/**
	 * Overridable operation to calculate the test verdict, i.e. the combined
	 * verdicts of the execution of all contained tests and test cases.
	 * <p>
	 * Determination of the test verdict defaults to essentially just the worst case
	 * verdict of each of the individual contained tests and test cases. This
	 * operation can be overriden to implement custom algorithms for determining the
	 * test verdict.
	 * 
	 * @return Test verdict
	 */
	protected SysMLVerdictKind calculateTestVerdict()
	{
		SysMLVerdictKind result = SysMLVerdictKind.pass;
		for (SysMLTestCase testCase : testCases)
			if (testCase.verdict.ordinal() > result.ordinal())
				result = testCase.verdict;
		for (SysMLTest test : tests)
			if (test.verdict.ordinal() > result.ordinal())
				result = test.verdict;
		return result;
	}

	/**
	 * Overridable operation to create any parts needed by the test. Overrides
	 * should invoke this operation so as to ensure the {@code systemUnderTest} is
	 * created and that the {@code systemUnderTest} is created prior to the test
	 * cases, tests, ports and connectors are created.
	 */
	@Override
	protected void createParts()
	{
		createSystemUnderTest();
	}

	/**
	 * Mandatory overridable operation to create the system to be tested (SysML's
	 * systemUnderTest). This operation should create the
	 * system/subsystem/component/domain that is to the target of the test and its
	 * test cases. An example implementation is as follows:
	 * 
	 * <pre>
	 * systemUnderTest = new AlphaSystem();
	 * </pre>
	 * 
	 */
	protected abstract void createSystemUnderTest();

	/**
	 * Overridable operation that creates and initializes the test cases. The
	 * operation should instantiate each of the test cases to be executed for the
	 * test and should add each instantiated test case to the list of test cases so
	 * that they are automatically executed by the test. An example implementation
	 * is as follows:
	 * 
	 * <pre>
	 * alphaTestCase = new AlphaTestCase(this);
	 * testCases.add(alphaTestCase);
	 * 
	 * bravoTestCase = new BravoTestCase(this);
	 * testCases.add(bravoTestCase);
	 * </pre>
	 * 
	 * @see sysmlinjava.tests.SysMLTestCase
	 */
	protected void createTestCases()
	{
	}

	/**
	 * Overridable operation that creates and initializes the nested tests.
	 * <p>
	 * Code format:<br>
	 * 
	 * <pre>
	 * alphaTest = new AlphaTest(Optional.empty(), "Alpha Test", 1);
	 * tests.add(alphaTest);
	 * 
	 * bravoTest = new BravoTest(Optional.empty(), "Bravo Test", 1);
	 * tests.add(bravoTest);
	 * </pre>
	 * 
	 * where the scope of the "put" call is the tests variable and the argument is
	 * the {@code SysMLTest} constructor whose arguments include this, as the parent
	 * of the test, the name of the test, a numberical index of the test, and the
	 * current system under test.
	 * 
	 */
	protected void createTests()
	{
	}

	/**
	 * Constant string name of method that creates/initializes the system under
	 * test, used by SysMLinJava tools, typically not needed for modeling.
	 */
	public static final String createSystemUnderTestMethodName = "createSystemUnderTest";
	/**
	 * Constant string names of method that creates/initializes the test cases, used
	 * by SysMLinJava tools, typically not needed for modeling.
	 */
	public static final String createTestCasesMethodName = "createTestCases";
	/**
	 * Constant string names of method that creates /initializes the tests, used by
	 * SysMLinJava tools, typically not needed for modeling.
	 */
	public static final String createTestsMethodName = "createTests";
	/**
	 * Constant string name of variable that declares the system under test, used by
	 * SysMLinJava tools, typically not needed for modeling.
	 */
	public static final String systemUnderTestVariableName = "systemUnderTest";
}