package sysmlinjava.annotations.tests;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "system-under-test" as part of a SysML test, The
 * field variable should be an instance of an extension class of the
 * {@code SysMLBlock} and should be created/initialized in the
 * {@code createSystemUnderTest()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.blocks.SysMLBlock
 * @see sysmlinjava.test.SysMLTest
 */
/**
 * Indicates that the field that follows represents the system-under-test of a
 * SysML "testCase", i.e. an instance of an extension of a {@code SysMLBlock}
 * that defines the system/object that is to be tested by the enclosing SysML
 * "Test Case".
 * 
 * @author ModelerOne
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface SystemUnderTest
{

}
