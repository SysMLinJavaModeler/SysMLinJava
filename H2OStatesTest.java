package h2ostates;

import java.util.Optional;
import sysmlinjava.annotations.tests.SystemUnderTest;
import sysmlinjava.annotations.tests.TestCase;
import sysmlinjava.tests.SysMLTest;

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
public class H2OStatesTest extends SysMLTest
{
	@SystemUnderTest
	H2O h2o;

	@TestCase
	H2OStatesTestCase testCase;
	
	public H2OStatesTest()
	{
		super(Optional.empty(), "H2OStatesTest", 0L);
	}

	@Override
	protected void createSystemUnderTest()
	{
		systemUnderTest = new H2O();
		h2o = (H2O)systemUnderTest;
	}
	
	@Override
	protected void createTestCases()
	{
		testCase = new H2OStatesTestCase(this);
		testCases.add(testCase);
	}

	/**
	 * Console-based process that creates, initializes, executes, and finalizes the
	 * test.
	 * 
	 * @param args null arguments list
	 */
	public static void main(String[] args)
	{
		H2OStatesTest test = new H2OStatesTest();
		test.initialize();
		test.execute();
		test.finalize();
		System.exit(0);
	}
}
