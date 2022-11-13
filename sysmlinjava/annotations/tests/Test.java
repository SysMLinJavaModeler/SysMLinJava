package sysmlinjava.annotations.tests;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "testCase", i.e. an
 * instance of a {@code SysMLTestCase} class that defines the activities that
 * comprise a standard SysML "Test Case".
 * 
 * @author ModelerOne
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface Test
{

}
