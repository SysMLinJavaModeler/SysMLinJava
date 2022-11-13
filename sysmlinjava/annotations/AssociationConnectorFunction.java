package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML connector
 * "function", i.e. function that makes the connector that is a realization of
 * an association between its participants. The field variable should be an
 * instance the {@code SysMLAssociationBlockConnectorFunction} and should be
 * created/initialized in the {@code createConnectorFunctions()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.connectors.SysMLAssociationBlockConnectorFunction
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface AssociationConnectorFunction
{
}
