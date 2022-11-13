package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "connector", i.e. a
 * realization of an association between its participants. The field variable
 * should be an instance of the {@code SysMLAssociationBlockConnector} and
 * should be created/initialized in the {@code createConnectors()} method.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.connectors.SysMLAssociationBlockConnector
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface AssociationConnector
{
}
