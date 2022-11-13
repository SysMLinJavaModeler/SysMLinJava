package sysmlinjava.annotations.blockcontainers;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "connector", i.e. a
 * function that makes the connections between participants in the association
 * defined by a {@code SysMLAssociationBlock}. The initialization (constructor)
 * of a {@code SysMLAssociationBlockConnector} within the context of the current
 * SysMLBlock is invoked by a containing {@code SysMLBlockAssociationBlock}
 * during a {@code createConnectors} operation in the block to actually make the
 * connections that are defined by the initialization of the
 * {@code SysMLBlockAssociationBlock} that defines the connection.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.connectors.SysMLExternalAssociationBlockConnectorFunction
 * @see sysmlinjava.connectors.SysMLExternalAssociationBlockConnector
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface ExternalAssociationConnectorFunction
{

}
