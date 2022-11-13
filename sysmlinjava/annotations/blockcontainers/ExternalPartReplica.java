package sysmlinjava.annotations.blockcontainers;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a replica of a SysML block
 * "part" that is external to the context of the current {@code SysMLBlock}
 * container. The {@code ExternalPartReplica} is used to create connectors to
 * the actual part when the part is external to the current
 * {@code BlockContainer}, i.e. internal to another {@code BlockContainer}. The
 * {@code ExternalPartReplica} provides the information needed to make the
 * connection to the actual part that is external to the container, e.g. the
 * replica includes the external part's fullPort's, their IP addresses and UDP
 * ports, contextBlock, etc. These elements and values are typically used to
 * connect parts in the container to parts in other containers.
 * 
 * @author ModelerOne
 * 
 * @see sysmlinjava.blocks.BlockContainer
 */
@Documented
@Retention(SOURCE)
@Target(FIELD)
public @interface ExternalPartReplica
{

}
