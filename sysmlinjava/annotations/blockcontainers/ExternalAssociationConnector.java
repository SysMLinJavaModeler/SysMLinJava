package sysmlinjava.annotations.blockcontainers;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents the realization of a SysML
 * association, i.e. a "connector" with a block "part" that is external to the
 * current {@code BlockContainer}. The connector is defined by an instance of a
 * {@code SysMLAssociationBlock} within the context of the current
 * {@code SysMLBlock}. Upon initialization of the association, an
 * {@code @AssociationConnectorFunction} contained in the
 * {@code SysMLAssociationBlock} actually makes the connection(s) between
 * participants in the association that are also defined by the
 * {@code SysMLAssociationBlock}.
 * 
 * @author ModelerOne
 * 
 * @see sysmlinjava.connectors.SysMLExternalAssociationBlockConnectorFunction
 * @see sysmlinjava.connectors.SysMLExternalAssociationBlockConnector
 *
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface ExternalAssociationConnector
{

}
