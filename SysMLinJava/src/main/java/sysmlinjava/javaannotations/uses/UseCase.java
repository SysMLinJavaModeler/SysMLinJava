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
 * Indicates that the field or method that follows represents a SysML use case
 * action. In the case of a field annotation, the annotated field should contain
 * a variable that is an instance of the {@code SysMLAction} or extension
 * thereof. The action instance is created/initialized in a
 * {@code createActions()} method of the part or port in which the field is
 * declared.
 * <p>
 * In the case of a method annotation, the annotated method should perform an
 * analysis action that uses and/or operates on other features of the analysis
 * case and/or other elements such as its attributes, items, parts, or ports,
 * uses input parameters, and/or invokes other actions, optionally returning a
 * result, i.e. executes a Java method. The annotated method should return a
 * void or a instance value. The method's lifetime occurrence(s) will be assumed
 * to be the same as that of the analysis case in which it performs. Note the
 * {@code AnalysisAction} annotation is essentially just a specialization of the
 * {@code Action} annotation, in accordance with the SysML standard.
 *
 * @author ModelerOne
 */
public @interface UseCase
{

}
