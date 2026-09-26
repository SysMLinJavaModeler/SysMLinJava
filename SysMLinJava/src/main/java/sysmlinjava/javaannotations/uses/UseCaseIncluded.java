/**
 *
 */
package sysmlinjava.javaannotations.uses;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target({ FIELD, METHOD })
/**
 * Indicates that the field or method that follows represents a SysML included
 * use case, i.e. an action that is included in another use case action. In the
 * case of a field annotation, the annotated field should contain a variable
 * that is an instance of the {@code SysMLUseCase} or extension thereof. The
 * action instance is created/initialized in a {@code createActions()} method of
 * the use case class in which the field is declared.
 * <p>
 * In the case of a method annotation, the annotated method should perform a use
 * case action that uses and/or operates on the subject and/or actors of the use
 * case and/or invokes other use case actions. Note the {@code UseCaseIncluded}
 * annotation is essentially just a specialization of the {@code UseCase}
 * annotation, which is itself a specialization of the {@code Calculation}
 * annotation, in accordance with the SysML standard.
 *
 * @author ModelerOne
 */
public @interface UseCaseIncluded
{

}
