package sysmlinjava.tests;

import java.util.Optional;
import sysmlinjava.blocks.SysMLBlock;
import sysmlinjava.common.SysMLActivity;

/**
 * SysMLinJava's representation of SysML's TestCase. The {@code SysMLTestCase}
 * contains the activities to be performed for a test case. Optional activities
 * are included for initialization and finalization activities before and after
 * the test execution activity.
 * <p>
 * Extension classes of the {@code SysMLTestCase} are typically parts of a class
 * that is an extension of {@code SysMLTest}. The activities of
 * the{@code SysMLTestCase} are executed in the context of the {@code SysMLTest}
 * as a series of test cases that make up the test. The extension of the
 * {@code SysMLTestCase} should instantiated and added to the set of test cases
 * in the {@code SysMLTest}. This instantiation of the test cases should be
 * performed within the overridable operation {@code createTestCases()}
 * operation of the {@code SysMLTest}.
 * <p>
 * <b>Note:</b>Realization of the initialization, test, and finalization
 * interfaces must each be instances of a lambda expression. These lambda
 * expressions for these activities must be declared in the
 * {@code createInitializeActivity()}, {@code createExecuteActivity()}, {@code
 * createFinalizeActivity()}. Tools and other elements of the current version of
 * SysMLinJava do not recognize class-based realizations of these activities. In
 * addition, the instantiations of these functional interfaces as lambda
 * expressions will have access to the {@code SysMLTest} class that includes the
 * system under test (SUT). Having no input parameters, the test case activity
 * lambda expressions have direct visibility to the test which provides access
 * to any ports, parts, values, or constraints that might be declared within the
 * test class and needed by the activities of the test case.
 * 
 * @author ModelerOne
 *
 * @see SysMLTest
 */
public abstract class SysMLTestCase extends SysMLBlock
{
	/**
	 * Test of which this is a test case. The test contains the system-under-test as
	 * well as any ports, parts, values, or constraints that might be needed by the
	 * test case activities.
	 */
	public SysMLTest test;

	/**
	 * Initialization behavior to be performed before the execution activity
	 */
	public Optional<SysMLActivity> initialize;
	/**
	 * Execution activity to be performed as the test case
	 */
	public SysMLActivity execute;
	/**
	 * Finalization behavior to be performed after the execution activity
	 */
	public Optional<SysMLActivity> finalize;
	/**
	 * Verdict of performance of the test case
	 */
	public SysMLVerdictKind verdict;

	/**
	 * Constructor
	 * 
	 * @param test The test of which this test case is a part of
	 */
	public SysMLTestCase(SysMLTest test)
	{
		super();
		this.test = test;
		this.initialize = Optional.empty();
		this.finalize = Optional.empty();
		createInitializeActivity();
		createExecuteActivity();
		createFinalizeActivity();
	}

	/**
	 * Creates the activity to be invoked prior to the execute activity ostensibly
	 * to initialize the test case. This operation should assign a lambda expression
	 * to the {@code initialize} variable. An example of the operation is as
	 * follows:
	 * 
	 * <pre>
	 * initialize = Optional.of(() ->
	 * {
	 * 	testVarA = 1.0;
	 * 	testVarB = getBetaValue();
	 * 	testVarC = List.of("ipso", "facto");
	 * 	verdict = SysMLVerdictKind.fail;
	 * });
	 * </pre>
	 */
	protected void createInitializeActivity()
	{
	}

	/**
	 * Mandatory operation to create the test case execution activity. This
	 * operation should assign a lambda expression to the {@code execute} variable.
	 * An example of the operation is as follows:
	 * 
	 * <pre>
	 * execute = () ->
	 * {
	 * 	for (int i = 0; i %lt; 1000; i++)
	 * 	{
	 * 		test.system.transmit(testVarA);
	 * 		int received = test.systemreceive();
	 * 		if (received != null &#64;&#64; received == testVarA)
	 * 			testVarA += i * Math.random();
	 * 	}
	 * };
	 * </pre>
	 */
	protected abstract void createExecuteActivity();

	/**
	 * Creates the activity to be invoked after the execute activity ostensibly to
	 * finalize the test case. This operation should assign a lambda expression to
	 * the {@code finalize} variable. An example of the operation is as follows:
	 * 
	 * <pre>
	 * finalize = Optional.of(() ->
	 * {
	 * 	if (testVarA == correctResult)
	 * 		verdict = SysMLVerdictKind.pass;
	 * 	storeResultsTo(resultsFile);
	 * });
	 * </pre>
	 */
	protected void createFinalizeActivity()
	{
	}

	/**
	 * Name of method to create initialize activity, used by SyMLinJava tools
	 */
	public static final String createInitializeActivityMethodName = "createInitializeActivity";
	/**
	 * Name of method to create execute activity, used by SyMLinJava tools
	 */
	public static final String createExecuteActivityMethodName = "createExecuteActivity";
	/**
	 * Name of method to create finalize activity, used by SyMLinJava tools
	 */
	public static final String createFinalizeActivityMethodName = "createFinalizeActivity";
}
