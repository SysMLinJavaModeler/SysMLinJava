/**
 * 
 */
package sysmlinjava.javaannotations.actions;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target(FIELD)
/**
 * Indicates that the field that follows represents a SysML use case action
 * function. The annotated field should contain a variable that is an instance
 * of the {@code SysMLCalculationFunction}. The use case action function
 * instance is created/initialized in a {@code createUseCaseActionFunctions()}
 * method of the part or port in which the field is declared.
 * 
 * @author ModelerOne
 */
public @interface UseCaseActionFunction
{

}
