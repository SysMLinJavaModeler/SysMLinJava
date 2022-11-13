package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the field that follows represents a SysML "containment"
 * relationship i.e. a relationship whereby the current object (requirement,
 * block, etc.) either contains or is contained by the object(s) specified by
 * the fields. The containment relationship conforms to the SysML containment or
 * "nested" relationship.<br>
 * Elements include:<br>
 * {@code isContainer}: whether the field object is the container (true) or
 * containee (false)<br>
 * {@code multiplicityMin}: minimum multiplicity of the flow<br>
 * {@code multiplicityMax}: maximum multiplicity of the flow<br>
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.requirements.SysMLRequirement
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Containment
{
	/**
	 * Returns whether this is the container side of the containment
	 * 
	 * @return true if this is the container side, false otherwise
	 */
	boolean isContainer() default true;

	/**
	 * Multiplicity minimum of the containment
	 * 
	 * @return minimum count
	 */
	int multiplicityMin() default 1;

	/**
	 * Multiplicity maximum of the containment
	 * 
	 * @return maximum count
	 */
	int multiplicityMax() default 1;
}
