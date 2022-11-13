package sysmlinjava.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import sysmlinjava.kinds.SysMLFlowDirectionKind;

/**
 * Indicates that the field that follows represents a SysML "flow" property. The
 * field variable should be an instance of an extension to the
 * {@code SysMLValueType} and is created/initialized in a {@code createFlows()}
 * method. Optionally, the annotation can specify the flow direction, i.e. in,
 * out, inout, which defaults to in.
 * 
 * @author ModelerOne
 *
 * @see sysmlinjava.valuetypes.SysMLValueType
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface Flow
{
	/**
	 * Returns the kind of direction to be associated with the flow
	 * 
	 * @return direction: in or out or inout
	 */
	SysMLFlowDirectionKind direction() default SysMLFlowDirectionKind.in;
}
