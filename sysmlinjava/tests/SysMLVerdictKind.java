package sysmlinjava.tests;

/**
 * SysMLinJava's representation of SysML's verdictKind.
 * 
 * @author ModelerOne
 *
 */
public enum SysMLVerdictKind
{
	/**
	 * System passed the test
	 */
	pass,
	/**
	 * System failed the test
	 */
	fail,
	/**
	 * Test was inconclusive
	 */
	inconclusive,
	/**
	 * Test experienced an error
	 */
	error;
}
