package sysmlinjava.analysis.bom.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates the annotated field declares a block that is to appear as a line
 * item in a bill-of-materials (BOM). The line item will be entitled by the type
 * name of the field variable. The multiplicity of the variable will be used to
 * specify the quantity of the line item in the BOM. The block class will be
 * used to identify the other information that will be used for the line item,
 * e.g. values for the line item, e.g. size, weigth, etc. and comments
 * containing defintion of other columns of information for the line item,
 * e.g.description, sourcing, version, etc. An example declaration is as
 * follows.
 * 
 * <pre>
 * {@code

	&#64;BOMLineItem
	&#64;Part
	VoltageSource voltageSource;

		:

	&#64;Override
	protected void createParts()
	{
		voltageSource = new VoltageSource("VoltageSource");
	}

  }
 * </pre>
 * 
 * @author ModelerOne
 *
 * @see BOMLineItemValue
 * @see BOMLineItemComment
 */
@Retention(SOURCE)
@Target(FIELD)
public @interface BOMLineItem
{

}
