package sysmlinjava.annotations.requirements;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a link to a requirement
 * specification. In essence this link defines a SysML requirement that implies
 * the specification provides other requirements not necessarily specified in
 * the SysML requirement format, but requirements none the less. The requirement
 * specification link allows for other sources requirements to be indirectly
 * included in the SysML requirements model.
 * 
 * @author ModelerOne
 *
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface RequirementSpecificationLink
{

}
